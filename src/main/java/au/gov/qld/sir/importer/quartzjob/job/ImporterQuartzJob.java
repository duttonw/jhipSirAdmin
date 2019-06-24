package au.gov.qld.sir.importer.quartzjob.job;

import au.gov.qld.sir.entity.ApiNotification;
import au.gov.qld.sir.service.*;
import au.gov.qld.sir.service.dto.*;
import au.gov.qld.sir.importer.quartzjob.service.ImporterUtil;
import au.gov.qld.sir.entity.RecordValidation;
import au.gov.qld.sir.importer.quartzjob.service.RequestType;
import au.gov.qld.sir.importer.quartzjob.service.RestClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import au.gov.qld.sir.entity.OpenDataImporterRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ImporterQuartzJob extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImporterQuartzJob.class);

	//number of results to bring back from Open Data using the package search (defaults to 10)
	public static final int OPENDATA_RESULT_ROWS = 1000;

	@Autowired
	private IntegrationMappingService IntegrationMappingService;

	@Autowired
	private ServiceRecordService ServiceRecordService;

	@Autowired
	private ServiceFranchiseService ServiceFranchiseService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private ServiceDeliveryService ServiceDeliveryService;

	@Autowired
	private DeliveryChannelService deliveryChannelService;

	@Autowired
	private ApplicationServiceOverrideService ApplicationServiceOverrideService;

	@Value("${importer_api}")
	private String importApi;

	@Value("${importer_keyword}")
	private String importKeyword;

	@Value("${importer_trigger}")
	private int importTrigger;

	private String notificationApi = "";

	private String notificationKey = "";

	@Value("${support_email}")
	private String sirSupportEmail;

	public void executeInternal(JobExecutionContext context) {
		// Gracefully handle properties not being available.
		if (this.importApi == null || this.importKeyword == null || this.notificationApi == null ||
			this.notificationKey == null) {
			LOGGER.error("OpenData importer values are not set.");
			return;
		}

		LOGGER.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

		importerApi(this.importApi, this.importKeyword, this.importTrigger);

		LOGGER.info("Next job scheduled @ {}", context.getNextFireTime());
	}

	/**
	 * Fetch data from the OpenData API and pass it on for processing
	 *
	 * @param importerApi     API to hit
	 * @param importerKeyword The keyword that matches the dataset that will be searched for
	 * @param importTrigger
	 */
	public void importerApi(String importerApi, String importerKeyword, int importTrigger) {
		String keywords[] = importerKeyword.split(",");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

		for (String keyword : keywords) {
			//by default opendata will only show the top 10 matches.  If we're retriving results this way we need to set rows
			String api = String.format("%s%s%s%s", importerApi, keyword, "&rows=", OPENDATA_RESULT_ROWS);
			RestClient rc = new RestClient(RequestType.GET);
			rc.setUrl(api);

			try {
				String result = rc.execute();
				JSONObject importerResult = new JSONObject(result);
				importerResult = (JSONObject) importerResult.get("result");
				JSONArray resultSet = new JSONArray(importerResult.get("results").toString());
				Integer totalResultCount = Integer.parseInt(importerResult.get("count").toString());

				LOGGER.info("Processing {} out of {} Results set from '{}' OpenDataImporterRecord", resultSet.length(), totalResultCount, keyword);

				for (int i = 0; i < resultSet.length(); i++) {
					importerResult = resultSet.getJSONObject(i);
					String maintainerEmail = importerResult.get("maintainer").toString();
					JSONArray resources = new JSONArray(importerResult.get("resources").toString());
					JSONObject resource;

					for (int j = 0; j < resources.length(); j++) {

						resource = resources.getJSONObject(j);
						String resourceName = resource.get("name").toString();
						String lastModifiedDateString = ImporterUtil.getOpenDataResourceLastModified(resource.get("revision_id").toString(), importerApi);
						if (ImporterUtil.hasUpdated(lastModifiedDateString, importTrigger)) {
							LOGGER.info("Importing modified resource: {}", resourceName);
							String format = resource.get("format").toString();

							if (format.equalsIgnoreCase("csv")) {
								rc.setUrl(resource.get("url").toString());
								String csvData = rc.execute();
								JSONArray importerArray = new JSONArray(ImporterUtil.CSVtoJSON(csvData, maintainerEmail));

								//Create generic list so you can use for importer and grantuser
								//Call Importer Logic
								List<OpenDataImporterRecord> openDataImporterRecords  = mapper.readValue(importerArray.toString(),
								                                           TypeFactory.defaultInstance()
									                                           .constructCollectionType(List.class,
									                                                                    OpenDataImporterRecord.class));

								LOGGER.info(String.format("Total records from OpenDataImporterRecord :%d", openDataImporterRecords.size()));
								for (OpenDataImporterRecord openDataImporterRecord : openDataImporterRecords) {
									LOGGER.info("*******************************************");
									LOGGER.info(openDataImporterRecord.toString());
								}
								LOGGER.info("*******************************************");
								updateDatabase(openDataImporterRecords);
							}
						} else {
							LOGGER.info("{}: not modified", resourceName);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Failed to fetch data", e);
			}
		}
	}

	/**
	 * Create new delivery channel
	 *
	 * @param virtualDetail
	 * @param deliveryType
	 * @param ServiceRecordDTO
	 * @return
	 */
	public DeliveryChannelDTO createDeliveryChannel(String virtualDetail, String deliveryType, ServiceRecordDTO ServiceRecordDTO) {
		DeliveryChannelDTO deliveryChannel = new DeliveryChannelDTO();
		ServiceDeliveryDTO serviceDelivery = new ServiceDeliveryDTO();
		serviceDelivery.setServiceDeliveryChannelType(deliveryType);
		deliveryChannel.setDeliveryChannelType(deliveryType);
		serviceDelivery.setServiceRecordId(ServiceRecordDTO.getId());
		deliveryChannel.setVirtualDeliveryDetails(virtualDetail);
		deliveryChannel.setServiceDeliveryId(serviceDelivery.getId());
		return deliveryChannel;
	}

	public DeliveryChannelDTO addDeliveryChannel(String virtualDetail, String deliveryType, ServiceDeliveryDTO serviceDelivery) {
		DeliveryChannelDTO deliveryChannel = new DeliveryChannelDTO();
		deliveryChannel.setDeliveryChannelType(deliveryType);
		deliveryChannel.setVirtualDeliveryDetails(virtualDetail);
		deliveryChannel.setServiceDeliveryId(serviceDelivery.getId());
		deliveryChannel.setServiceDeliveryId(serviceDelivery.getId());
		return deliveryChannel;
	}

	// Update delivery channel
	public DeliveryChannelDTO updateDeliveryChannel(String virtualDetail, DeliveryChannelDTO deliveryChannel, ServiceDeliveryDTO serviceDelivery) {
		deliveryChannel.setVirtualDeliveryDetails(virtualDetail);
		deliveryChannel.setServiceDeliveryId(serviceDelivery.getId());
		return deliveryChannel;
	}

	public ServiceFranchiseDTO createFranchise(String Franchisename) {
		ServiceFranchiseDTO ServiceFranchise = new ServiceFranchiseDTO();
		ServiceFranchise.setFranchiseName(Franchisename);
        return ServiceFranchiseService.save(ServiceFranchise);
	}

	public List<String> checkValidationService(String agencyServiceId, ServiceRecordDTO service) {
		ArrayList<String> validationFailure = new ArrayList<String>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ServiceRecordDTO>> constraintViolations = validator.validate(service);
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation errorString : constraintViolations) {
				validationFailure.add("Agency service ID: " + agencyServiceId + " : " + errorString.getPropertyPath().toString() + ": " + errorString.getMessage());
				LOGGER.info(errorString.getPropertyPath() + ":" + errorString.getMessage());
			}
			return validationFailure;

		}
		return null;

	}

	public ArrayList<String> checkValidationMapping(IntegrationMappingDTO mapping) {
		ArrayList<String> validationFailure = new ArrayList<String>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<IntegrationMappingDTO>> constraintViolations = validator.validate(mapping);
		if (constraintViolations.size() != 0) {
			validationFailure.add("Integration Mapping Id - " + mapping.getAgencyServiceId());
			for (ConstraintViolation errorString : constraintViolations) {
				validationFailure.add(errorString.getPropertyPath().toString());
				LOGGER.info("Error : " + errorString.getPropertyPath() + ":" + errorString.getMessage());
			}
			return validationFailure;

		}
		return null;
	}

	public String getServiceNameFromImporter(String contextName, String interactionName, String serviceName) {
		if (contextName != null) {
			return contextName.trim();
		} else if (interactionName != null) {
			return interactionName.trim();
		} else {
			return serviceName.trim();
		}
	}

	public List<AgencyDTO> listAgency(String agencyCode) {
		return agencyService.searchAgencysHQL(" agency_code ='" + agencyCode + "'");
	}

	/**
	 * Persist the valid ServiceRecordDTO details from the Imported CSV file.
	 * Maintainer email will be used to email persist success report.
	 *
	 * @param validationState Current state of the validation for the current set
	 * @param totalServices   The total number of records in the open data resource.
	 */
	public void saveOpendataImport(RecordValidation validationState, int totalServices) {
		Map<String, String> updateService = new HashMap<String, String>();
		Map<String, String> newService = new HashMap<String, String>();
		Map<String, String> applicationEmail = new HashMap<String, String>();
		Map<String, String> applicationServiceMap = new HashMap<>();
		Map<String, Set<String>> notificationMap = new HashMap<String, Set<String>>();
		Map<String, String> failedService = new HashMap<String, String>();
		Map<String, String> failedServiceDelivery = new HashMap<String, String>();

		Long serviceId = 0L;
		// Save all interaction
		if (validationState.getImportInteractionServices() != null && !validationState.getImportInteractionServices().isEmpty()) {
			ServiceRecordService.importService(
				validationState.getImportInteractionServices().toArray
					(new ServiceRecordDTO[validationState.getImportInteractionServices().size()]));
		}

		// Save all service
		if (validationState.getImportServices() != null && !validationState.getImportServices().isEmpty()) {
			int serviceIndex = 0;
			for (RecordValidation.ImportService importItem : validationState.getImportServices()) {
				ServiceRecordDTO importService = importItem.getService();

				// Handle the case where the parent is new and in the current file
				if (!importItem.isMappedParent()) {
					// Map the parent
					List<IntegrationMappingDTO> mappings =
						IntegrationMappingService
							.searchIntegrationMappingsHQL("agency_service_id="
								                                                + importItem.getParentServiceAgencyId());
					if (mappings != null && !mappings.isEmpty()) {
						importService.setParentServiceId(mappings.get(0).getServiceId());
					} else {
						LOGGER.error("Integration mapping parent service cannot be found.");
					}
				}

				boolean update = false, newCreate = false;
				if (importService.getId() != null) {
					ObjectMapper mapper = new ObjectMapper();
					JSONObject serviceObject = ServiceRecordService.get(importService.getId(), "ServiceDeliveryOrganisation");
					ServiceRecordDTO oldService = null;
					try {
						oldService = mapper.readValue(serviceObject.toString(), ServiceRecordDTO.class);

						// For some reason the above mapper is not recursing correctly for this child so we will do it
						// manually here.
						ServiceDeliveryOrganisationDTO serviceDelivery = mapper.readValue(
							serviceObject.get("serviceDeliveryOrganisation").toString(),
							ServiceDeliveryOrganisationDTO.class);

						// Given that this importer should not update the service delivery organisation, we just reset it.
						importService.setServiceDeliveryOrganisation(serviceDelivery);
						oldService.setServiceDeliveryOrganisation(serviceDelivery);
					} catch (IOException e) {
						LOGGER.error("Failed to map service to object in update grants data.");
						e.printStackTrace();
					}

					if (importService.compareOpenData(oldService) || update) {
						serviceId = ServiceRecordService.saveOrUpdate(importService);
						if(serviceId != -1) {
							importService.setId(serviceId);
							update = true;
						} else {
							update = false;
							IntegrationMappingDTO mcIm = validationState.getImportIntegrationMapping().stream().filter(im -> importService.getId().equals(im.getServiceId())).findFirst().orElse(null);
							failedService.put(String.valueOf(importService.getId()), mcIm.getAgency().getAgencyName() + ": " + mcIm.getAgencyServiceId());
						}
					} else {
						serviceId = importService.getId();
					}

				} else {
					serviceId = ServiceRecordService.saveOrUpdate(importService);
					importService.setId(serviceId);
					newCreate = true;
					newService.put(String.valueOf(importService.getId()), "1");
				}
				importItem.setService(importService);

				// Persist Integration Mappings
				if (validationState.getImportIntegrationMapping() != null && !validationState.getImportIntegrationMapping().isEmpty()) {

					IntegrationMappingDTO importIntegration = validationState.getImportIntegrationMapping().get(serviceIndex);
					// Fetch the matching service - this should have been persisted in the previous step if it is new
					importIntegration.setServiceId(importService.getId());
					importIntegration.setService(importService);

					try {
						IntegrationMappingService.saveOrUpdate(importIntegration);
					} catch (Exception e) {
						LOGGER.error("Failed to persist integration mapping " + e.getMessage());
					}
				}

				// Persist delivery channels
				if (validationState.getImportDeliveryChannel() != null && !validationState.getImportDeliveryChannel().isEmpty()) {
					int importDeliveryChannelIndex = 0;
					for (DeliveryChannelDTO importChannel : validationState.getImportDeliveryChannel()) {
						ServiceDeliveryDTO serviceDelivery = importChannel.getServiceDelivery();

						// Fetch the matching service - this should have been persisted in the previous step if it is new
						if (serviceDelivery.getService().getName().equals(importService.getName())) {
							if (importChannel.getServiceDeliveryId() == null) {
								serviceDelivery.setService(importService);
								Long id = ServiceDeliveryService.saveOrUpdate(serviceDelivery);
								importChannel.setServiceDeliveryId(id);
								// TODO: Not convinced this is accurate
								if (!newCreate && !update) {
									update = true;
								}
							}
							if(importChannel.getServiceDeliveryId() != -1) {
								deliveryChannelService.saveOrUpdate(importChannel);
							} else {
								IntegrationMappingDTO mcIm = validationState.getImportIntegrationMapping().stream().filter(im -> serviceDelivery.getService().getId().equals(im.getServiceId())).findFirst().orElse(null);
								failedServiceDelivery.put(String.valueOf(serviceDelivery.getId()), mcIm.getAgency().getAgencyName() + ":" + mcIm.getAgencyServiceId());
							}
						}
					}
				}

				// When updates have occurred, send notification
				if (update) {
					List<ApplicationServiceOverrideDTO> applicationServiceOverrides;
					String querySearchString = "service_id = " + serviceId;
					applicationServiceOverrides = ApplicationServiceOverrideService.searchApplicationServiceOverridesHQL(querySearchString);
					Set<String> emailSet = new HashSet<String>();
					if (applicationServiceOverrides != null) {
						for (ApplicationServiceOverrideDTO applicationServiceOverride : applicationServiceOverrides) {
							emailSet.add(applicationServiceOverride.getApplication().getContactEmail());
							applicationEmail.put(
								applicationServiceOverride.getApplication().getContactEmail(), applicationServiceOverride.getApplication().getName());
							applicationServiceMap.put(
								applicationServiceOverride.getApplication().getName(), String.valueOf(importService.getId()) + " - " + importService.getName());
						}
					}
					if (applicationServiceOverrides == null) {
						LOGGER.info("ServiceRecordDTO is not associated with an application ******* " + serviceId);
					}
					notificationMap.put(String.valueOf(importService.getId()), emailSet);
					updateService.put(String.valueOf(importService.getId()), "1");
				}

				serviceIndex++;
			}
		}

		StringBuilder result = new StringBuilder();

		// Generate email based on updated services
		result.append("Validation Report (Services): \n");
		result.append("\nThe file that you uploaded through OpenData on " + new Date());
		result.append(" has been successfully uploaded: \n");
		result.append("\n  - Total Services: " + totalServices);
		result.append("\n  - Updated Services: " + updateService.size());
		result.append("\n  - New Services: " + newService.size());
		result.append("\n\nThank You.");

		if (validationState.getMaintainerEmail() != null) {
			setNotification(validationState.getMaintainerEmail(), result.toString());
		}

		// Send notification to application owner on change of underlying service details
		for (Map.Entry<String, String> entry : applicationEmail.entrySet()) {
			result = new StringBuilder();
			result.append("\n\nThe following services that have been overridden for <")
				.append(entry.getValue())
				.append("> have been updated\n\n\n");
			for (Map.Entry<String, String> entryService : applicationServiceMap.entrySet()) {
				if (entryService.getKey().equals(entry.getValue())) {
					result.append("\n").append(entryService.getValue());
				}
			}
			if (updateService.isEmpty()) {
				result.append("No any updates available");
			} else {
				result.append("\n\nThank You");
				String emailAddress = entry.getKey();
				if (emailAddress != null) {
					setNotification(emailAddress, result.toString());
				} else {
					LOGGER.info("No contact email set for application");
					break;
				}
			}
		}

		LOGGER.info("****" + result + "****");

		// Send notification to SIR Support if a records fail to save
		StringBuilder failureResult = new StringBuilder();
		failureResult.append("Failure Report (Services): \n");
		failureResult.append("\nA file obtained through OpenData on " + new Date());
		failureResult.append(" has been successfully uploaded but the following records were not save due to errors.");
		failureResult.append(" Manual correction using the SIR Management Console may be possible by referencing the agency codes listed below with records in Open Data");
		failureResult.append("\nPlease review the logs associated with the import of this file to determine the specific cause of errors");
		if(failedService.size() > 0) {
			failureResult.append("\n\n Services");
			failedService.forEach((k,v) -> failureResult.append(String.format("\n - %s (SIR ServiceRecordDTO Id: %s)",v, k)));
		}
		if(failedServiceDelivery.size() > 0) {
			failureResult.append("\n\n ServiceRecordDTO Delivery");
			failedServiceDelivery.forEach((k,v) -> failureResult.append(String.format("\n - %s (SIR ServiceDelivery Id: %s)",v, k)));
		}
		LOGGER.info("ServiceRecordDTO failures: \n" + failureResult);

		if (sirSupportEmail != null && (failedService.size() > 0 || failedServiceDelivery.size() > 0)) {
			setNotification(sirSupportEmail, failureResult.toString());
		}
	}


	/**
	 * Validate a single record
	 *
	 * @param record          The record to validate
	 * @param validationState State of the current validation
	 * @return List of validation errors, empty list represents a valid record
	 */
	public RecordValidation validateRecord(OpenDataImporterRecord record, RecordValidation validationState) {

		// Set the maintainer email
		validationState.setMaintainerEmail(record.getMaintainerEmail());

		// Validate that the CSV row is valid and well formed
		// Return early if there are validation errors
		if (!record.validate()) {
			// Add error with detail for validation failure
			validationState.addValidationError("Agency service ID: " + record.getAgencyServiceId() + ", Error : " + record.validateError());
			return validationState;
		}

		// Find the agency
		List<AgencyDTO> Agency = null;
		if (record.getAgencyCode() != null && !record.getAgencyCode().equals("")) {
			Agency = agencyService.searchAgencysHQL(" agency_code ='" + record.getAgencyCode() + "'");
		}
		// Return early if there are validation errors
		if (Agency == null || Agency.isEmpty()) {
			// We do not create a new agency if a match is not found. Add error with detail.
			validationState.addValidationError("Agency service ID: " + record.getAgencyServiceId() + ", Error : Invalid Agency acronym {" + record.getAgencyCode() + "}");
			return validationState;
		}

		ServiceRecordDTO serviceRecord = new ServiceRecordDTO();

		// Get the name (this needs to happen before integration mapping)
		String name = getServiceNameFromImporter(record.getServiceContext(), record.getServiceInteractionName(), record.getServiceName());

		// Get integration mapping
		IntegrationMappingDTO integrationMapping;
		String queryString = "agency_service_id=" + record.getAgencyServiceId();
		List<IntegrationMappingDTO> integrationMappings =
			IntegrationMappingService.searchIntegrationMappingsHQL(queryString);

		if (integrationMappings != null) {
			// There should only ever be one integration mapping available from the query due to database constraints
			integrationMapping = integrationMappings.get(0);
			integrationMapping.setServiceName(record.getServiceName().trim());
			Long serviceId = integrationMapping.getServiceId();
			LOGGER.info("************** " + serviceId + "-" + integrationMapping.getServiceId());

			ObjectMapper mapper = new ObjectMapper();
			JSONObject serviceObject = ServiceRecordService.get(serviceId, null);
			try {
				serviceRecord = mapper.readValue(serviceObject.toString(), ServiceRecordDTO.class);
			} catch (IOException e) {
				LOGGER.error("Failed to map service back to object.");
				e.printStackTrace();
			}
		} else {
			integrationMapping = new IntegrationMappingDTO();
			integrationMapping.setAgencyServiceId(record.getAgencyServiceId());
			integrationMapping.setService(serviceRecord);
			integrationMapping.setServiceName(record.getServiceName().trim());
			integrationMapping.setAgency(Agency.get(0));
		}
		if (checkValidationMapping(integrationMapping) == null) {
			validationState.addImportValidationMapping(integrationMapping);
		} else {
			validationState.addValidationErrors(checkValidationMapping(integrationMapping));
		}

		// Map the fields
		serviceRecord.setServiceFranchise(getFranchise(record));
		serviceRecord.setActive(getStatus(record));
		serviceRecord.setLongDescription(record.getLongDescription());
		serviceRecord.setDescription(record.getShortDescription());
		serviceRecord.setEligibilityNew(record.getEligibilityNew());
		serviceRecord.setPreRequisitesNew(record.getPrerequisiteNew());
		serviceRecord.setHowTo(record.getHowTo());
		serviceRecord.setFees(record.getFees());
		serviceRecord.setReferenceUrlNew(record.getReferenceUrlNew());
		serviceRecord.setKeywords(record.getKeywords());
		serviceRecord.setStartDate(formatDate(record.getServiceStartDate()));
		serviceRecord.setEndDate(formatDate(record.getServiceEndDate()));
		serviceRecord.setName(name);

		// Set delivery channels
		validationState = setDeliveryChannels(serviceRecord, record, validationState);

		// Set parent service ID
		RecordValidation.ImportService importItem = new RecordValidation.ImportService();
		// If it is a context
		if (record.getServiceContext() != null) {

			// Find parent interaction
			ServiceRecordDTO interactionService = null;
			List<ServiceRecordDTO> interactionServices = ServiceRecordService.getServiceByName(record.getServiceInteractionName());
			if (interactionServices != null && !interactionServices.isEmpty()) {
				interactionService = interactionServices.get(0);
				serviceRecord.setParentServiceId(interactionService.getId());
			} else {
				validationState = validateParentChildRelationship(record, validationState,
				                                                  "Agency service ID: "
					                                                  + record.getAgencyServiceId()
					                                                  + ", Error : Interaction Parent ServiceRecordDTO not found");
				// We set the parent id as null as it is unknown
				importItem = setParentDetails(record, validationState, serviceRecord, importItem);
			}

			List<ServiceRecordDTO> services = ServiceRecordService.getServiceByName(record.getServiceName());
			if (services != null && !services.isEmpty() && interactionService != null) {
				LOGGER.info("interaction " + services.get(0).getId());
				if (interactionService.getParentServiceId() == null || services.get(0).getId() != interactionService.getParentServiceId()) {
					interactionService.setParentServiceId(services.get(0).getId());

					if (checkValidationService(record.getAgencyServiceId(), interactionService) == null) {
						// Update parent service
						validationState.addImportInteractionService(interactionService);
					} else {
						validationState.addValidationErrors(checkValidationService(record.getAgencyServiceId(), interactionService));
					}
				}
			} else {
				validationState = validateParentChildRelationship(record, validationState,
				                                                  "Agency service ID: " + record.getAgencyServiceId() + ", Error : Parent ServiceRecordDTO not found");
				// We set the parent id as null as it is unknown
				importItem = setParentDetails(record, validationState, serviceRecord, importItem);

			}
			// If it is an interaction
		} else if (record.getServiceInteractionName() != null) {

			// Find parent service
			List<ServiceRecordDTO> services = ServiceRecordService.getServiceByName(record.getServiceName());
			if (services != null && !services.isEmpty()) {
				if (serviceRecord.getParentServiceId() == null || services.get(0).getId() != serviceRecord.getParentServiceId()) {
					// Update service
					serviceRecord.setParentServiceId(services.get(0).getId());
				}
			} else {
				validationState = validateParentChildRelationship(record, validationState,
				                                                  "Agency service ID: " + record.getAgencyServiceId() + ", Error : Parent ServiceRecordDTO not found");
				// We set the parent id as null as it is unknown
				importItem = setParentDetails(record, validationState, serviceRecord, importItem);
			}
		}
		if (checkValidationService(record.getAgencyServiceId(), serviceRecord) == null) {
			importItem.setService(serviceRecord);
			validationState.addImportService(importItem);
		} else {
			validationState.addValidationErrors(checkValidationService(record.getAgencyServiceId(), serviceRecord));
			LOGGER.info("----- Validation Fail" + record.getServiceName());
		}

		return validationState;
	}

	private RecordValidation.ImportService setParentDetails(OpenDataImporterRecord record,
		RecordValidation validationState, ServiceRecordDTO ServiceRecordDTO, RecordValidation.ImportService importItem) {

		ServiceRecordDTO.setParentServiceId(null);
		importItem.setMappedParent(false);

		OpenDataImporterRecord parent = getParentRecord(record, validationState.getValidationSet());
		String parentServiceName = getParentServiceName(parent);

		if (parentServiceName == null) {
			return importItem;
		}

		importItem.setServiceParentName(parentServiceName);
		importItem.setParentServiceAgencyId(parent.getAgencyServiceId());

		return importItem;
	}


	/**
	 * Return the name of the service in the open data row
	 *
	 * @param parent
	 * @return
	 */
	private String getParentServiceName(OpenDataImporterRecord parent) {
		if (parent == null) {
			return null;
		}
		if (parent.getServiceContext() != null && !parent.getServiceContext().isEmpty()) {
			return parent.getServiceContext();
		} else if (parent.getServiceInteractionName() != null && !parent.getServiceInteractionName().isEmpty()) {
			return parent.getServiceInteractionName();
		}

		return parent.getServiceName();
	}


	/**
	 * Check if a parent exists and is valid, if parent exists, reorder to ensure parent occurs before the child
	 * so persistance works.
	 *
	 * @param record          The current record to check the parent for
	 * @param validationState The current state of the validation
	 * @param message         Error message on validation failure
	 * @return
	 */
	public RecordValidation validateParentChildRelationship(OpenDataImporterRecord record, RecordValidation validationState,
		String message) {
		// Does the parent service exist in the current data set?
		OpenDataImporterRecord parentRecord = getParentRecord(record, validationState.getValidationSet());

		if (parentRecord == null) {
			validationState.addValidationError(message);
			return validationState;
		}

		// Update the order of the result
		int indexOfParent = validationState.getValidationSet().indexOf(parentRecord);
		List<OpenDataImporterRecord> newValidationSet = validationState.getValidationSet();
		int recordIndex = validationState.getValidationSet().indexOf(record);
		newValidationSet.remove(indexOfParent);
		newValidationSet.add(recordIndex, parentRecord);

		// We don't want to do a full validation here as that will be caught later. It is good enough to check
		// If the parent exists

		return validationState;
	}

	/**
	 * Validate the imported objects
	 *
	 * @param jsonObjects
	 */
	public void updateDatabase(List<OpenDataImporterRecord> jsonObjects) {

		RecordValidation validationState = new RecordValidation();
		validationState.setValidationSet(new ArrayList<>(jsonObjects));

		for (OpenDataImporterRecord importObject : jsonObjects) {
			// Validate a single record and update the validation state based on any validation issues
			validationState = this.validateRecord(importObject, validationState);
		}
		String maintainerEmail = validationState.getMaintainerEmail();

		StringBuilder result = new StringBuilder();

		if (!validationState.getValidationErrors().isEmpty()) {
			result.append("Validation Report: \n");
			result.append("The file that you uploaded on ").append(new Date()).append(" has failed for the following reason: \n");
			result.append("\n\n");
			for (String s : validationState.getValidationErrors()) {
				result.append("- ").append(s).append("\n");
			}
			if (maintainerEmail != null) {
				result.append("\n\nPlease fix these issues and re-upload to OpenData\n");
				result.append("\n\n");
				result.append("Thank You");
				setNotification(maintainerEmail, result.toString());
			}

			LOGGER.info("****" + result + "****");

		} else {
			saveOpendataImport(validationState, jsonObjects.size());
		}
	}

	/**
	 * Get the parent if it exists in the dataset
	 *
	 * @param child   The child of the parent to fetch
	 * @param records Collection of records to search
	 * @return The parent record, null if not found
	 */
	public OpenDataImporterRecord getParentRecord(OpenDataImporterRecord child, List<OpenDataImporterRecord> records) {
		if ((child.getServiceInteractionName() == null) || child.getServiceInteractionName().isEmpty() &&
			(child.getServiceContext() == null || child.getServiceContext().isEmpty())) {
			// Child is service, has no parent
			return null;
		}

		for (OpenDataImporterRecord record : records) {
			if (child.equals(record)) {
				continue;
			}
			if (child.getServiceContext() == null) {
				// Interaction
				if (record.getServiceName().trim().equals(child.getServiceName().trim()) &&
					(record.getServiceInteractionName() == null || record.getServiceInteractionName().isEmpty())) {
					return record;
				}
			} else {
				// Context
				if (record.getServiceInteractionName() != null && record.getServiceInteractionName().trim().equals(child.getServiceInteractionName().trim())) {
					return record;
				}
			}
		}
		return null;
	}

	// Format Date using 'dd/MM/yyyy'
	public Date formatDate(String d) {
		if (d != null && !d.equals("")) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return formatter.parse(d);
			} catch (java.text.ParseException e) {
				LOGGER.error("Failed to format date", e);
			}
		}
		return null;
	}

	/**
	 * Compile and send an email to the given person with the results provided.
	 *
	 * @param email Email address to send email to
	 * @param content The body of the email
	 */
	public void setNotification(String email, String content) {
		// Date string
		Date notificationDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String notificationDateFormat = formatter.format(notificationDate);

		ApiNotification notification = new ApiNotification();
		notification.setScheduledDate(notificationDateFormat);
		notification.setEmail(email.toLowerCase());
		notification.setMessageSubject("Import API report");
		notification.setMessageText(content);
		notification.setMessageHtml("");
		notification.setSendEmail(true);
		notification.setSendSMS(false);
		notification.setSource("string");

		String notificationResult = ImporterUtil.sendNotification(notification, notificationApi, notificationKey);
		LOGGER.info(notificationResult);
	}

	public ServiceFranchiseDTO getFranchise(OpenDataImporterRecord record) {
		String queryString = " franchise_name = '" + record.getFranchiseName() + "'";
		List<ServiceFranchiseDTO> franchises = ServiceFranchiseService.searchServiceFranchisesHQL(queryString);
		if (franchises != null && !franchises.isEmpty()) {
			// @TODO: Do we really need to iterate through this, doesn't the above query find a matching
			// franchise, and if there is exactly 1 result, then that should be the franchise?
			for (ServiceFranchiseDTO franchise : franchises) {
				if (franchise.getFranchiseName().equals(record.getFranchiseName())) {
					return franchise;
				}
			}
		}
		return createFranchise(record.getFranchiseName());
	}

	public String getStatus(OpenDataImporterRecord record) {
		if (record.getStatus().equals("0")) {
			return OpenDataImporterRecord.Status.INACTIVE.getPlaceholder();
		} else {
			return OpenDataImporterRecord.Status.ACTIVE.getPlaceholder();
		}
	}

	public RecordValidation setDeliveryChannels(ServiceRecordDTO ServiceRecordDTO, OpenDataImporterRecord record, RecordValidation validationState) {
		List<ServiceDeliveryDTO> serviceDeliveries = null;
		if ((Long) ServiceRecordDTO.getId() != null) {
			String queryString = "service_id = " + ServiceRecordDTO.getId();
			serviceDeliveries = ServiceDeliveryService.searchServiceDelivery(queryString);
		}
		boolean update = false;
		if (serviceDeliveries != null) {
			// Update delivery channel
			// Loop through all existing service deliveries
			outerloop:
			for (ServiceDeliveryDTO serviceDelivery : serviceDeliveries) {
				// Loop through all existing delivery channels
				for (DeliveryChannelDTO deliveryChannel : serviceDelivery.getDeliveryChannels()) {
					if (serviceDelivery.getServiceDeliveryChannelType().equals("ONLINE")) {
						if (record.getOnlineDeliveryChannel() != null && !deliveryChannel.getVirtualDeliveryDetails().equals(record.getOnlineDeliveryChannel())) {
							validationState.addImportDeliveryChannel(updateDeliveryChannel(record.getOnlineDeliveryChannel(), deliveryChannel, serviceDelivery));
							update = true;
							break outerloop;
						}
					} else if (serviceDelivery.getServiceDeliveryChannelType().equals("PHONE")) {
						if (record.getPhoneDeliveryChannel() != null && !deliveryChannel.getVirtualDeliveryDetails().equals(record.getPhoneDeliveryChannel())) {
							validationState.addImportDeliveryChannel(updateDeliveryChannel(record.getPhoneDeliveryChannel(), deliveryChannel, serviceDelivery));
							update = true;
							break outerloop;
						}
					}
				}
				// If there is already a service delivery channel but no delivery channel
				if (!update) {
					if (serviceDelivery.getServiceDeliveryChannelType().equals("ONLINE") && record.getOnlineDeliveryChannel() != null) {
						validationState.addImportDeliveryChannel(addDeliveryChannel(record.getOnlineDeliveryChannel(), "ONLINE", serviceDelivery));
						update = true;
					}
					if (serviceDelivery.getServiceDeliveryChannelType().equals("PHONE") && record.getPhoneDeliveryChannel() != null) {
						validationState.addImportDeliveryChannel(addDeliveryChannel(record.getPhoneDeliveryChannel(), "PHONE", serviceDelivery));
						update = true;
					}
				}
			}
		}
		// If there is neither a service delivery or delivery channel
		if (!update) {
			// Create new delivery channels
			if (record.getOnlineDeliveryChannel() != null) {
				validationState.addImportDeliveryChannel(createDeliveryChannel(record.getOnlineDeliveryChannel(), "ONLINE", ServiceRecordDTO));
			}
			if (record.getPhoneDeliveryChannel() != null) {
				validationState.addImportDeliveryChannel(createDeliveryChannel(record.getPhoneDeliveryChannel(), "PHONE", ServiceRecordDTO));
			}
		}
		return validationState;
	}
}
