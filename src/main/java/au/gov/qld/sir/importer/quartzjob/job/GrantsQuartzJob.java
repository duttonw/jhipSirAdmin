package au.gov.qld.sir.importer.quartzjob.job;

import au.gov.qld.sir.entity.ApiNotification;
import au.gov.qld.sir.entity.Node;
import au.gov.qld.sir.importer.quartzjob.service.ImporterUtil;
import au.gov.qld.sir.service.*;
import au.gov.qld.sir.entity.RecordValidation;
import au.gov.qld.sir.service.dto.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import au.gov.qld.sir.entity.GrantImporterRecord;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class GrantsQuartzJob extends QuartzJobBean {

	@Autowired
	private IntegrationMappingService integrationMappingService;

	@Autowired
	private ServiceRecordService serviceRecordService;

	@Autowired
	private ServiceDeliveryOrganisationService serviceDeliveryOrganisationService;

	@Autowired
	private ApplicationServiceOverrideTagService applicationServiceOverrideTagService;

	@Autowired
	private ApplicationServiceOverrideService applicationServiceOverrideService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationServiceOverrideTagItemsService applicationServiceOverrideTagsService;

	@Autowired
	private ApplicationServiceOverrideAttributeService applicationServiceOverrideAttributeService;

	@Autowired
	private AgencyService agencyService;

	private ImporterUtil importerUtil = new ImporterUtil();
	Logger log = LoggerFactory.getLogger(getClass());

	@Value("${importer_api}")
	private String importApi;

	@Value("${grant_keyword}")
	private String importKeyword;

	@Value("${importer_trigger}")
	private int importTrigger;


	private String notificationApi = "";
	private String notificationKey = "";

	public void executeInternal(JobExecutionContext context) {
		// Gracefully handle properties not being available.
		if (this.importApi == null || this.importKeyword == null || this.notificationApi == null ||
			this.notificationKey == null) {
			log.error("OpenData importer values are not set.");
			return;
		}

		log.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
		//Process of Grants Importer call the process function
		try {
			grantsProcess();
		} catch (IOException e) {
			log.error("Failed to process grants {}",  e.getMessage(), e);
		}
		log.info("Next job scheduled @ {}", context.getNextFireTime());
	}


	public void grantsProcess() throws JsonParseException, JsonMappingException, IOException {
		JSONArray importerArray = importerUtil.importerApi(this.importApi, this.importKeyword, this.importTrigger);

		if (importerArray != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

			List<GrantImporterRecord> grantImporterRecords = mapper.readValue(
				importerArray.toString(), TypeFactory.defaultInstance().constructCollectionType(
					List.class, GrantImporterRecord.class));

			log.info("Total records from OpenDataImporterRecord : {}", grantImporterRecords.size());

			updateDatabase(grantImporterRecords);
		}
	}


	// Create new Service Delivery Organisation
	public ServiceDeliveryOrganisationDTO createOrganisation(String agencyCode, Long agencyId) {
        ServiceDeliveryOrganisationDTO organisation = new ServiceDeliveryOrganisationDTO();
		organisation.setBusinessUnitName(agencyCode);
		organisation.setAgencyId(agencyId);
        return serviceDeliveryOrganisationService.save(organisation);
	}


	public List<String> checkValidationService(String agencyServiceId, ServiceRecordDTO service) {
		ArrayList<String> validationFailure = new ArrayList<String>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ServiceRecordDTO>> constraintViolations = validator.validate(service);
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation errorString : constraintViolations) {
				validationFailure.add("Agency service ID: " + agencyServiceId + " : " + errorString.getPropertyPath().toString() + ": " + errorString.getMessage());
				log.info(errorString.getPropertyPath() + ":" + errorString.getMessage());
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
				log.info("Error : " + errorString.getPropertyPath() + ":" + errorString.getMessage());
			}
			return validationFailure;

		}
		return null;
	}

	/**
	 * Persist the valid Service details from the Imported CSV file.
	 * Maintainer email will be used to email persist success report.
	 *
	 * @param validationState Current state of the validation for the current set
	 * @param totalServices   The total number of records in the open data resource.
	 */
	public void saveGrantdataImport(RecordValidation validationState, int totalServices) {
		Map<String, String> updateService = new HashMap<String, String>();
		Map<String, String> applicationEmail = new HashMap<String, String>();
		Map<String, String> applicationServiceMap = new HashMap<String, String>();
		Map<String, Set<String>> notificationMap = new HashMap<String, Set<String>>();

		List<RecordValidation.ImportService> unmapped = new ArrayList<>();

		Long serviceId = 0L;

		ApplicationDTO grantsApplication = validationState.getGrantsApplication();

		// Save all services
		if (validationState.getImportServices() != null && !validationState.getImportServices().isEmpty()) {

			// The ordering of the services is important as it is used to map to the integration mappings. For new services
			// these don't exist before we achieve the following persistence.
			int position = 0;
			for (RecordValidation.ImportService importItem : validationState.getImportServices()) {
				ServiceRecordDTO importService = importItem.getService();

				boolean update = false;
				if (importService.getId() != null) {

                    ServiceRecordDTO oldService = serviceRecordService.findOne(importService.getId()).orElse(null);

					if (oldService.getServiceDeliveryOrganisation() != null && importService.getServiceDeliveryOrganisation() != null) {
						if (oldService.getServiceDeliveryOrganisation().getId() != importService.getServiceDeliveryOrganisation().getId()) {
							update = true;
						}
					}

					if (importService.compareOpenData(oldService) || update) {
						serviceId = serviceService.saveOrUpdate(importService);
						importService.setId(serviceId);
						update = true;
					} else {
						serviceId = importService.getId();
					}
				} else {
					// Create the service as it doesn't currently exist in the database
					serviceId = serviceService.saveOrUpdate(importService);
					importService.setId(serviceId);
				}

				// This is where the importance of the position comes into play when we attempt to persist the mapping for this service.
				if (serviceId != null) {
					IntegrationMappingDTO importIntegrationService = validationState.getImportIntegrationMapping().get(position);

					// Set the service to account for services added in the previous step
					importIntegrationService.setService(importService);
					importIntegrationService.setServiceId(serviceId); // May seem redundant but is required as it cannot be null for new services

					// Persist integration mapping
					integrationMappingService.saveOrUpdate(importIntegrationService);

					// Persist override
					ApplicationServiceOverrideDTO override = persistServiceOverride(grantsApplication, importService);

					// Persist the tags for this service.
					validationState = persistTags(validationState, importService, position, override);

					// Persist attributes
					validationState = persistAttributes(validationState, importService, position, override);

					position++;
				} else {
					log.error("Failure to persist service " + importService.toString());
				}

				// When updates have occurred, send notification
				if (update) {
					List<ApplicationServiceOverrideDTO> applicationServiceOverrides;
					String querySearchString = "service_id = " + serviceId;
					applicationServiceOverrides = applicationServiceOverrideService.searchApplicationServiceOverridesHQL(querySearchString);
					Set<String> emailSet = new HashSet<String>();
					if (applicationServiceOverrides != null) {
						for (ApplicationServiceOverrideDTO applicationServiceOverride : applicationServiceOverrides) {
							emailSet.add(applicationServiceOverride.getApplication().getContactEmail());
							applicationEmail.put(applicationServiceOverride.getApplication().getContactEmail(), applicationServiceOverride.getApplication().getName());
							applicationServiceMap.put(applicationServiceOverride.getApplication().getName(), String.valueOf(importService.getId()) + " - " + importService.getName());
						}
					}
					if (applicationServiceOverrides == null) {
						log.info("No application available with *************" + serviceId);
					}
					notificationMap.put(String.valueOf(importService.getId()), emailSet);
					updateService.put(String.valueOf(importService.getId()), "1");
				}
			}
		}

		StringBuilder result = new StringBuilder();

		// Generate email based on updated services
		result.append("Validation Report (Grants): \n");
		result.append("\nThe file that you uploaded through OpenData on ");
		result.append(new Date());
		result.append(" has been successfully uploaded which had ");
		String updateMessage = (updateService.size() > 1) ? "were" : "was";
		result.append(totalServices).append(" services, of which ").append(updateService.size()).append(" ")
			.append(updateMessage).append(" updated.");
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
				setNotification(emailAddress, result.toString());
			}
		}

		log.info("****" + result + "****");
	}

	/**
	 * Persist the attributes
	 *
	 * @param validationState The state of the import
	 * @param importService   The service the attributes belong to
	 * @param position        The position in the service list
	 * @param override        The current override
	 * @return The updated state.
	 */
	private RecordValidation persistAttributes(RecordValidation validationState, ServiceRecordDTO importService,
		int position, ApplicationServiceOverrideDTO override) {

		// Get the list of attributes for the current service
		List<ApplicationServiceOverrideAttributeDTO> attributes = validationState.getApplicationServiceOverrideAttributes(position);

		for (ApplicationServiceOverrideAttributeDTO attribute : attributes) {
			attribute.setApplicationServiceOverrideId(override.getId());
			applicationServiceOverrideAttributeService.saveOrUpdate(attribute);
		}

		return validationState;
	}

	/**
	 * Persist the service override for grants
	 *
	 * @param grantsApplication The g`rants application
	 * @param importService     The current service that the override is for
	 * @return The persisted override
	 */
	private ApplicationServiceOverrideDTO persistServiceOverride(ApplicationDTO grantsApplication,
		ServiceRecordDTO importService) {
		List<ApplicationServiceOverrideDTO> overrides = applicationServiceOverrideService
			.searchApplicationServiceOverridesHQL("service_id="
				                                                        + importService.getId()
				                                                        + " and "
				                                                        + grantsApplication.getId());

		ApplicationServiceOverrideDTO override;
		if (overrides == null) {
			// None found create one
			override = new ApplicationServiceOverrideDTO();
			override.setServiceId(importService.getId());
			override.setApplicationId(grantsApplication.getId());

			Long overrideId = applicationServiceOverrideService.saveOrUpdate(override);
			override.setId(overrideId);
			return override;
		}
		// There can only be one
		return overrides.get(0);
	}

	/**
	 * Persist tags for the given service
	 *
	 * @param validationState The current import state that holds the tags.
	 * @param service         The service that the tags belong to
	 * @param index           The index of the current service in the validation state
	 */
	private RecordValidation persistTags(RecordValidation validationState, ServiceRecordDTO service, int index,
		ApplicationServiceOverrideDTO override) {
		Map<String, Node<String>> tagTreeMap = validationState.getTagTreeMap(index);

		Node<String> grantsNode = tagTreeMap.get("Grants");
		ApplicationServiceOverrideTagDTO grantsTag = validationState.getKnownTags().get(generateMapLabel(grantsNode));
		Long grantsId = applicationServiceOverrideTagService.saveOrUpdate(grantsTag);
		grantsTag.setId(grantsId);
		validationState.addKnownTag(grantsTag, generateMapLabel(grantsNode));

		List<Node<String>> children = grantsNode.getChildren();
		for (Node<String> childNode : children) {
			ApplicationServiceOverrideTagDTO childTag = validationState.getKnownTags().get(generateMapLabel(childNode));
			childTag.setParentId(grantsId);
			Long childTagId = applicationServiceOverrideTagService.saveOrUpdate(childTag);
			childTag.setId(childTagId);

			validationState.addKnownTag(childTag, generateMapLabel(childNode));

			List<Node<String>> grandChildren = childNode.getChildren();
			for (Node<String> grandChildNode : grandChildren) {
				ApplicationServiceOverrideTagDTO grandChildTag = validationState.getKnownTags().get(generateMapLabel(grandChildNode));

				// We persist the tag first to get the ID
				grandChildTag.setParentId(childTagId);
				Long grandChildTagId = applicationServiceOverrideTagService.saveOrUpdate(grandChildTag);
				grandChildTag.setId(grandChildTagId);

				validationState.addKnownTag(grandChildTag, generateMapLabel(grandChildNode));

				/* Persist the relationship with the given service */
				List<ApplicationServiceOverrideTagsDTO> ApplicationServiceOverrideTagsList = applicationServiceOverrideTagsService
						.searchApplicationServiceOverrideTagssHQL("application_service_override_id="
								+ override.getId()
								+ " and application_service_override_tag_id=" + grandChildTagId);

				// For grants we only want to associate the lowest level tag to the service.
				ApplicationServiceOverrideTagsDTO overrideTags;
				if (ApplicationServiceOverrideTagsList == null) {
					overrideTags = new ApplicationServiceOverrideTags();
					overrideTags.setApplicationServiceOverrideId(override.getId());
					overrideTags.setApplicationServiceOverrideTag(grandChildTag);
				} else {
					// Should only be one
					overrideTags = ApplicationServiceOverrideTagsList.get(0);
				}
				applicationServiceOverrideTagsService.saveOrUpdate(overrideTags);
			}
		}

		return validationState;
	}

	/**
	 * Create a label for the map that is based on the relationships the current node has with its parent
	 * The label traverses the tree upwards
	 *
	 * @param node
	 * @return
	 */
	private String generateMapLabel(Node<String> node) {
		// Create a unique name that maps to to all the parents to enforce relationships in the known tag map
		Node<String> nodeCopy = node;
		StringBuilder labelBuilder = new StringBuilder();
		labelBuilder.append(nodeCopy.getData());
		while (nodeCopy.getParent() != null) {
			labelBuilder.insert(0, ">");
			labelBuilder.insert(0, nodeCopy.getParent().getData());
			nodeCopy = nodeCopy.getParent();
		}

		return labelBuilder.toString();
	}

	/**
	 * Validate a single record
	 *
	 * @param record          The record to validate
	 * @param validationState State of the current validation
	 * @return List of validation errors, empty list represents a valid record
	 */
	public RecordValidation validateRecord(GrantImporterRecord record, RecordValidation validationState) {

		Long serviceId = 0L;
		ServiceRecordDTO service = new ServiceRecordDTO();

		// Find Maintenace Console service
		IntegrationMappingDTO IntegrationMapping = null;

		String queryString = null;
		RecordValidation.ImportService importItem = new RecordValidation.ImportService();

		List<AgencyDTO> Agency = null;
		if (record.getAgencyAcronym() != null && !record.getAgencyAcronym().equals("")) {
			Agency = agencyService.searchAgencysHQL(" agency_code ='" + record.getAgencyAcronym() + "'");
		}

		String agencyCode = null;
		// Add new object into integrationMapping
		// Find mapping from the database if exists then update service name and fetch service
		if (Agency != null) {
			// Agency Name
			if (Agency.get(0).getAgencyCode().equals(record.getAgencyAcronym())) {
				agencyCode = Agency.get(0).getAgencyCode();
			}
			if (agencyCode != null) {
				// Fetch Integration mapping against the agency
				IntegrationMapping = null;
				List<IntegrationMapping> integrationMappings = null;
				if (!Agency.isEmpty()) {
					queryString = "agency_id = " + Agency.get(0).getId() + " and agency_service_id = '" + record.getAgencyServiceId() + "'";
					integrationMappings = integrationMappingService.searchIntegrationMappingsHQL(queryString);
				}

				if (integrationMappings != null) {
					// Now there should only ever be one integration mapping available from the query due to database constraints
					IntegrationMapping = integrationMappings.get(0);
					IntegrationMapping.setServiceName(record.getServiceName());
					serviceId = IntegrationMapping.getServiceId();
					log.info("************** " + serviceId + IntegrationMapping.getServiceId());
					record.setServiceId(String.valueOf(serviceId));
					ObjectMapper mapper = new ObjectMapper();
					JSONObject serviceObject = serviceService.get(serviceId, null);
					try {
						service = mapper.readValue(serviceObject.toString(), ServiceRecordDTO.class);
					} catch (IOException e) {
						log.error("Failed to map service back to object.");
						e.printStackTrace();
					}
				}

				// Check whether integration mapping is available or not, create one if not found
				if (IntegrationMapping == null) {
					IntegrationMapping = new IntegrationMapping();
					IntegrationMapping.setAgencyServiceId(record.getAgencyServiceId());
					IntegrationMapping.setServiceName(record.getServiceName());
					IntegrationMapping.setAgency(Agency.get(0));
				}

				if (checkValidationMapping(IntegrationMapping) == null) {
					validationState.addImportValidationMapping(IntegrationMapping);
				} else {
					validationState.addValidationErrors(checkValidationMapping(IntegrationMapping));
				}

				service.setName(record.getServiceName());

				if ((Long) service.getId() != null) {
					serviceId = service.getId();
				}

				service.setLongDescription(record.getLongDescription());
				service.setReferenceUrlNew(record.getReferenceUrl());
				service.setEligibilityNew(record.getEligibility());

				// As it is required as per validation rules but will always be absent, set all grants active to Y
				service.setActive("Y");

				// Same as with active, set description as a truncated version of long description to meet validation requirements
				service.setDescription(
					record.getLongDescription().substring(
						0, Math.min(record.getLongDescription().length(), 497)) + "..."
				                                        );

				if (checkValidationService(record.getAgencyServiceId(), service) == null) {
					importItem.setService(service);
					validationState.addImportService(importItem);
				} else {
					validationState.addValidationErrors(checkValidationService(record.getAgencyServiceId(), service));
					log.info("----- Validation Fail" + record.getServiceName());
				}

				// Delivery Organisation Check in the database
				// IF new service
				// Create Business Organisation
				ServiceDeliveryOrganisationDTO ServiceDeliveryOrganisation = new ServiceDeliveryOrganisationDTO();
				queryString = " agency_id = '" + Agency.get(0).getId() + "'";
				List<ServiceDeliveryOrganisation> ServiceOrganisations = serviceDeliveryOrganisationService.searchServiceDeliveryOrganisationsHQL(queryString);
				ServiceDeliveryOrganisation businessOrganisation = null;
				Long businessId = null;
				if (ServiceOrganisations != null && !ServiceOrganisations.isEmpty()) {
					// Business unit name
					for (ServiceDeliveryOrganisation organisation : ServiceOrganisations) {
						if (organisation.getBusinessUnitName() != null && organisation.getAgency().getAgencyCode().equals(record.getAgencyAcronym())) {
							businessOrganisation = organisation;
							businessId = businessOrganisation.getId();
						}
					}
				}
				if (businessId == null) {
					businessOrganisation = createOrganisation(record.getAgencyAcronym(), Agency.get(0).getId());
				}

				service.setServiceDeliveryOrganisation(businessOrganisation);

				// Process tags
				validationState = validateTags(validationState, record);

				// Process Attributes
				validationState = validateAttributes(validationState, record);
			}
		} else {
			// We do not create a new agency if a match is not found. Add error with detail.
			validationState.addValidationError("Agency service ID: " + record.getAgencyServiceId() + ", Error : Invalid Agency acronym {" + record.getAgencyAcronym() + "}");
			validationState.setMaintainerEmail(record.getMaintainerEmail());
		}

		if (validationState.getMaintainerEmail() == null) {
			validationState.setMaintainerEmail(record.getMaintainerEmail());
		}

		return validationState;
	}

	/**
	 * Process and validate the attributes of the dataset
	 *
	 * @param validationState The state of the import
	 * @param record          The current import record
	 * @return updated ValidationState
	 */
	private RecordValidation validateAttributes(RecordValidation validationState, GrantImporterRecord record) {
		List<ApplicationServiceOverrideAttributeDTO> attributes = new ArrayList<>();

		if (record.getSubProgramTitle() != null) {
			attributes.add(getAttribute("Sub-program title", record.getSubProgramTitle(), record));
		}

		// Validate status
		// Must be either "Open" or "Closed"
		if (record.getStatus() != null) {
			switch (record.getStatus()) {
				case "Open":
				case "Closed":
					break;
				default:
					validationState.addValidationError("Status must be either 'Open' or 'Closed' for service " + record.getAgencyServiceId());
					validationState.setMaintainerEmail(record.getMaintainerEmail());
			}
			attributes.add(getAttribute("Status", record.getStatus(), record));
		}

		// Date must of the format DD/MM/YYY
		if (record.getOpeningDateDateFormat() != null) {
			if (validateDate(record.getOpeningDateDateFormat())) {
				attributes.add(getAttribute("Opening date (date format)", record.getOpeningDateDateFormat(), record));
			} else {
				validationState.addValidationError("Opening date (date format) must formatted DD/MM/YYYY for service" + record.getAgencyServiceId());
			}
		}

		// Date must of the format DD/MM/YYY
		if (record.getClosingDateDateFormat() != null) {
			if (validateDate(record.getClosingDateDateFormat())) {
				attributes.add(getAttribute("Closing date (date format)", record.getClosingDateDateFormat(), record));
			} else {
				validationState.addValidationError("Closing date (date format) must formatted DD/MM/YYYY for service" + record.getAgencyServiceId());
			}
		}

		if (record.getOpeningDateText() != null) {
			attributes.add(getAttribute("Opening date (text)", record.getOpeningDateText(), record));
		}

		if (record.getClosingDateText() != null) {
			attributes.add(getAttribute("Closing date (text)", record.getClosingDateText(), record));
		}

		if (record.getFinancialYearBudget() != null) {
			attributes.add(getAttribute("Financial year budget", record.getFinancialYearBudget(), record));
		}

		if (record.getAdditionalYearBudget() != null) {
			attributes.add(getAttribute("Additional budget information", record.getAdditionalYearBudget(), record));
		}

		if (record.getMaxGrantAmountDollar() != null) {
			attributes.add(getAttribute("Maximum (indicative) grant amount (dollar amount)", record.getMaxGrantAmountDollar(), record));
		}

		if (record.getMaxGrantAmountText() != null) {
			attributes.add(getAttribute("Maximum (indicative) grant amount (text)", record.getMaxGrantAmountText(), record));
		}

		if (record.getHowWillIKnowIfImSuccessful() != null) {
			attributes.add(getAttribute("How will I know if I'm successful?", record.getHowWillIKnowIfImSuccessful(), record));
		}

		validationState.addApplicationServiceOverrideAttributes(attributes);

		return validationState;
	}

	/**
	 * Create validation error if the date is not formatted as DD/MM/YYYY
	 *
	 * @param attribute       The date string
	 * @return true on match, false otherwise
	 */
	private boolean validateDate(String attribute) {
		// Matches date format string DD/MM/YYY
		Pattern pattern = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$");
		Matcher matcher = pattern.matcher(attribute);
		return matcher.matches();
	}

	/**
	 * Get the application_service_override if it exists. For it to exist the service must also exist
	 * If the override doesn't already exist then the override attribute cannot exist for the given service
	 *
	 * @param attributeName
	 * @param attributeValue
	 * @param record
	 * @return
	 */
	private ApplicationServiceOverrideAttributeDTO getAttribute(String attributeName, String attributeValue,
		GrantImporterRecord record) {
		if (record.getServiceId() != null) {
			List<ApplicationServiceOverrideDTO> overrides = applicationServiceOverrideService
				.searchApplicationServiceOverridesHQL("service_id=" + record.getServiceId());

			if (overrides != null) {
				// There should only be one
				Long overrideId = overrides.get(0).getId();

				// Check if the attribute exists
				// TODO: Some strings have an apostrophe, there is probably way to escape this but i didn't have time to investigate.
				List<ApplicationServiceOverrideAttributeDTO> attributes = applicationServiceOverrideAttributeService
					.searchApplicationServiceOverrideAttributesHQL("application_service_override_id="
						                                                                 + overrideId + " and name="
						                                                                 + attributeName);

				if (attributes != null) {
					// There will only ever be one
					return attributes.get(0);
				}
			}
		}
		ApplicationServiceOverrideAttributeDTO attribute = new ApplicationServiceOverrideAttributeDTO();
		attribute.setName(attributeName);
		attribute.setValue(attributeValue);

		return attribute;
	}

	/**
	 * Process and validate the tags that exist as part of a record.
	 * Validation involves checking if they exist and creating the necessary mappings
	 * <p>
	 * Tags have a hierarchy and if a parent is missing from the data we assume the children also do not exist
	 *
	 * @param validationState
	 * @return
	 */
	private RecordValidation validateTags(RecordValidation validationState, GrantImporterRecord record) {
		Map<String, Node<String>> tagMap = new HashMap<>();
		ApplicationServiceOverrideTagDTO grantsTag = findTagByNameAndParent("Grants", null);
		Node<String> grantsNode = new Node<>(grantsTag.getName());

		ApplicationServiceOverrideTagDTO categoryTag = findTagByNameAndParent(TagType.CATEGORY.toString(), grantsTag.getId());
		Node<String> categoryNode = new Node<>(categoryTag.getName());
		categoryNode.setParent(grantsNode);
		categoryNode = addTag(validationState, record.getCategory1(), categoryTag, categoryNode);
		categoryNode = addTag(validationState, record.getCategory2(), categoryTag, categoryNode);
		categoryNode = addTag(validationState, record.getCategory3(), categoryTag, categoryNode);
		validationState.addKnownTag(categoryTag, generateMapLabel(categoryNode));
		grantsNode.addChild(categoryNode);

		ApplicationServiceOverrideTagDTO applicantTypeTag = findTagByNameAndParent(TagType.APPLICANT_TYPE.toString(), grantsTag.getId());
		Node<String> applicantTypeNode = new Node<>(applicantTypeTag.getName());
		applicantTypeNode.setParent(grantsNode);
		applicantTypeNode = addTag(validationState, record.getApplicantType1(), applicantTypeTag, applicantTypeNode);
		applicantTypeNode = addTag(validationState, record.getApplicantType2(), applicantTypeTag, applicantTypeNode);
		applicantTypeNode = addTag(validationState, record.getApplicantType3(), applicantTypeTag, applicantTypeNode);
		validationState.addKnownTag(applicantTypeTag, generateMapLabel(applicantTypeNode));
		grantsNode.addChild(applicantTypeNode);

		ApplicationServiceOverrideTagDTO clientGroupTag = findTagByNameAndParent(TagType.CLIENT_GROUP.toString(), grantsTag.getId());
		Node<String> clientGroupNode = new Node<>(clientGroupTag.getName());
		clientGroupNode.setParent(grantsNode);
		clientGroupNode = addTag(validationState, record.getClientGroup1(), clientGroupTag, clientGroupNode);
		clientGroupNode = addTag(validationState, record.getClientGroup2(), clientGroupTag, clientGroupNode);
		clientGroupNode = addTag(validationState, record.getClientGroup3(), clientGroupTag, clientGroupNode);
		clientGroupNode = addTag(validationState, record.getClientGroup4(), clientGroupTag, clientGroupNode);
		clientGroupNode = addTag(validationState, record.getClientGroup5(), clientGroupTag, clientGroupNode);
		clientGroupNode = addTag(validationState, record.getClientGroup6(), clientGroupTag, clientGroupNode);
		validationState.addKnownTag(clientGroupTag, generateMapLabel(clientGroupNode));
		grantsNode.addChild(clientGroupNode);

		ApplicationServiceOverrideTagDTO businessSpecificActivityTag = findTagByNameAndParent(TagType.BUSINESS_SPECIFIC_ACTIVITY.toString(), grantsTag.getId());
		Node<String> businessSpecificActivityNode = new Node<>(businessSpecificActivityTag.getName());
		businessSpecificActivityNode.setParent(grantsNode);
		businessSpecificActivityNode = addTag(validationState, record.getBusinessSpecificActivity1(), businessSpecificActivityTag, businessSpecificActivityNode);
		businessSpecificActivityNode = addTag(validationState, record.getBusinessSpecificActivity2(), businessSpecificActivityTag, businessSpecificActivityNode);
		businessSpecificActivityNode = addTag(validationState, record.getBusinessSpecificActivity3(), businessSpecificActivityTag, businessSpecificActivityNode);
		businessSpecificActivityNode = addTag(validationState, record.getBusinessSpecificActivity4(), businessSpecificActivityTag, businessSpecificActivityNode);
		businessSpecificActivityNode = addTag(validationState, record.getBusinessSpecificActivity5(), businessSpecificActivityTag, businessSpecificActivityNode);
		validationState.addKnownTag(businessSpecificActivityTag, generateMapLabel(businessSpecificActivityNode));
		grantsNode.addChild(businessSpecificActivityNode);

		ApplicationServiceOverrideTagDTO assistanceTypeTag = findTagByNameAndParent(TagTypeDTO.ASSISTANCE_TYPE.toString(), grantsTag.getId());
		Node<String> assistanceTypeNode = new Node<>(assistanceTypeTag.getName());
		assistanceTypeNode.setParent(grantsNode);
		assistanceTypeNode = addTag(validationState, record.getAssistanceType1(), assistanceTypeTag, assistanceTypeNode);
		assistanceTypeNode = addTag(validationState, record.getAssistanceType2(), assistanceTypeTag, assistanceTypeNode);
		assistanceTypeNode = addTag(validationState, record.getAssistanceType3(), assistanceTypeTag, assistanceTypeNode);
		validationState.addKnownTag(assistanceTypeTag, generateMapLabel(assistanceTypeNode));
		grantsNode.addChild(assistanceTypeNode);

		validationState.addKnownTag(grantsTag, generateMapLabel(grantsNode));
		tagMap.put("Grants", grantsNode);
		validationState.addTagTreeMap(tagMap);
		return validationState;
	}

	private Node<String> addTag(RecordValidation validationState, String tagName, ApplicationServiceOverrideTagDTO parentTag, Node<String> parentNode) {
		if (tagName == null) {
			return parentNode;
		}
		ApplicationServiceOverrideTagDTO tag = findTagByNameAndParent(tagName, parentTag.getId());
		Node<String> thisNode = new Node<>(tagName);
		thisNode.setParent(parentNode);
		validationState.addKnownTag(tag, generateMapLabel(thisNode));
		parentNode.addChild(thisNode);
		return parentNode;
	}

	/**
	 * Find the tag by name and parent
	 *
	 * @param tagName
	 * @param parentId The id of the parent tag
	 * @return The found tag, or a new tag instance of the given name
	 */
	private ApplicationServiceOverrideTagDTO findTagByNameAndParent(String tagName, Long parentId) {
		List<ApplicationServiceOverrideTagDTO> tags = applicationServiceOverrideTagService
			.searchApplicationServiceOverrideTagsHQL("name='" + tagName
				                                                           + "' and parent_id" + ((parentId == null) ? " is null" : "=" + parentId));

		ApplicationServiceOverrideTagDTO tag;
		if (tags == null) {
			tag = new ApplicationServiceOverrideTagDTO();
			tag.setName(tagName);
		} else {
			// There should only ever by one
			tag = tags.get(0);
		}
		return tag;
	}

	/**
	 * Validate the imported objects
	 *
	 * @param jsonObjects
	 */
	public void updateDatabase(List<GrantImporterRecord> jsonObjects) {

		RecordValidation validationState = new RecordValidation();
		validationState.setGrantValidationSet(new ArrayList<>(jsonObjects));

		// Check that a grants application exists
		// Get grants application
		List<ApplicationDTO> applications = applicationService.searchApplicationsHQL("name='Grants'");
		if (applications == null) {
			log.error("No grants application found, cannot continue import");
			return;
		}

		validationState.setGrantsApplication(applications.get(0));

		log.debug("Validating " + jsonObjects.size() + " records");

		for (GrantImporterRecord importObject : jsonObjects) {
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

			log.info("****" + result + "****");

		} else {
			saveGrantdataImport(validationState, jsonObjects.size());
		}
	}

	public void setNotification(String email, String result) {
		// Date string
		Date notificationDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String notificationDateFormat = formatter.format(notificationDate);

		ApiNotification notification = new ApiNotification();
		notification.setScheduledDate(notificationDateFormat);
		notification.setEmail(email.toLowerCase());
		notification.setMessageSubject("Import API report");
		notification.setMessageText(result);
		notification.setMessageHtml("");
		notification.setSendEmail(true);
		notification.setSendSMS(false);
		notification.setSource("string");
		log.info(importerUtil.sendNotification(notification, this.notificationApi, this.notificationKey));
	}
}
