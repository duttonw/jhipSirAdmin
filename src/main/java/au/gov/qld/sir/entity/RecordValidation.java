package au.gov.qld.sir.entity;

import au.gov.qld.sir.service.dto.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data class for storing the state of the validation of a given record
 */
public class RecordValidation {


	private List<ImportService> importServices = new ArrayList<>();
	private List<ServiceRecordDTO> importInteractionServices = new ArrayList<>();
	private List<IntegrationMappingDTO> importIntegrationMapping = new ArrayList<>();
	private List<DeliveryChannelDTO> importDeliveryChannel = new ArrayList<>();
	private List<ServiceDeliveryOrganisationDTO> importServiceDeliveryOrganisation = new ArrayList<>();
	private List<String> validationErrors = new ArrayList<>();

	private List<OpenDataImporterRecord> validationSet;
	private List<GrantImporterRecord> grantValidationSet;
	private ApplicationDTO grantsApplication;

	// A tree of tag labels based on their hierarchy
	private List<Map<String, Node<String>>> grantsTags = new ArrayList<>();

	private Map<String, ApplicationServiceOverrideTagDTO> knownTags = new HashMap<>();

	private List<List<ApplicationServiceOverrideAttributeDTO>> applicationServiceOverrideAttributes = new ArrayList<>();

	private String maintainerEmail;

	public RecordValidation() {
	}

	public List<List<ApplicationServiceOverrideAttributeDTO>> getApplicationServiceOverrideAttributes() {
		return applicationServiceOverrideAttributes;
	}

	public List<ApplicationServiceOverrideAttributeDTO> getApplicationServiceOverrideAttributes(int position) {
		return applicationServiceOverrideAttributes.get(position);
	}

	public void addApplicationServiceOverrideAttributes(List<ApplicationServiceOverrideAttributeDTO> applicationServiceOverrideAttributes) {
		this.applicationServiceOverrideAttributes.add(applicationServiceOverrideAttributes);
	}


	public List<Map<String, Node<String>>> getGrantsTags() {
		return grantsTags;
	}

	public void setGrantsTags(List<Map<String, Node<String>>> grantsTags) {
		this.grantsTags = grantsTags;
	}

	public List<ImportService> getImportServices() {
		return importServices;
	}

	public void setImportServices(List<ImportService> importServices) {
		this.importServices = importServices;
	}

	public List<ServiceRecordDTO> getImportInteractionServices() {
		return importInteractionServices;
	}

	public void setImportInteractionServices(List<ServiceRecordDTO> importInteractionServices) {
		this.importInteractionServices = importInteractionServices;
	}

	public List<IntegrationMappingDTO> getImportIntegrationMapping() {
		return importIntegrationMapping;
	}

	public void setImportIntegrationMapping(List<IntegrationMappingDTO> importIntegrationMapping) {
		this.importIntegrationMapping = importIntegrationMapping;
	}

	public List<DeliveryChannelDTO> getImportDeliveryChannel() {
		return importDeliveryChannel;
	}

	public void setImportDeliveryChannel(List<DeliveryChannelDTO> importDeliveryChannel) {
		this.importDeliveryChannel = importDeliveryChannel;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public List<OpenDataImporterRecord> getValidationSet() {
		return validationSet;
	}

	public void setValidationSet(List<OpenDataImporterRecord> validationSet) {
		this.validationSet = validationSet;
	}

	public void setGrantValidationSet(List<GrantImporterRecord> validationSet) {
		this.grantValidationSet = validationSet;
	}

	public void addImportValidationMapping(IntegrationMappingDTO mapping) {
		this.importIntegrationMapping.add(mapping);
	}

	public void addValidationErrors(List<String> errors) {
		this.validationErrors.addAll(errors);
	}

	public void addValidationError(String error) {
		this.validationErrors.add(error);
	}

	public void addImportInteractionService(ServiceRecordDTO interaction) {
		this.importInteractionServices.add(interaction);
	}

	public void addImportService(ImportService service) {
		this.importServices.add(service);
	}

	public void addImportDeliveryChannel(DeliveryChannelDTO deliveryChannel) {
		this.importDeliveryChannel.add(deliveryChannel);
	}


	public void addServiceDeliveryOrganisation(ServiceDeliveryOrganisationDTO serviceDeliveryOrganisation) {
		this.importServiceDeliveryOrganisation.add(serviceDeliveryOrganisation);
	}

	public String getMaintainerEmail() {
		return maintainerEmail;
	}

	public void setMaintainerEmail(String maintainerEmail) {
		this.maintainerEmail = maintainerEmail;
	}

	/**
	 * A tree is used to represent the hierarchy of tags. A map is used to store the various types of tags and we have
	 * a list of these maps to represent the tags for all services.  This method adds one of these maps to the list.
	 * The map must be fully formed before adding.
	 *
	 * @param tagTreeMap The map of the tag trees.
	 */
	public void addTagTreeMap(Map<String, Node<String>> tagTreeMap) {
		this.grantsTags.add(tagTreeMap);
	}

	/**
	 * Get the map of the tag tree at the provided index
	 *
	 * @param index
	 * @return The tag tree map
	 */
	public Map<String, Node<String>> getTagTreeMap(int index) {
		return this.grantsTags.get(index);
	}

	public Map<String, ApplicationServiceOverrideTagDTO> getKnownTags() {
		return knownTags;
	}

	public void setKnownTags(Map<String, ApplicationServiceOverrideTagDTO> knownTags) {
		this.knownTags = knownTags;
	}

	public void addKnownTag(ApplicationServiceOverrideTagDTO tag, String labelName) {
		// We can have duplicate names but not duplicate names with the same parent
		this.knownTags.put(labelName, tag);
	}

	public ApplicationDTO getGrantsApplication() {
		return grantsApplication;
	}

	public void setGrantsApplication(ApplicationDTO grantsApplication) {
		this.grantsApplication = grantsApplication;
	}

	public static class ImportService {
		private ServiceRecordDTO service;
		private String serviceParentName;
		private boolean mappedParent = true;
		private String parentServiceAgencyId;

		public String getParentServiceAgencyId() {
			return parentServiceAgencyId;
		}

		public void setParentServiceAgencyId(String parentServiceAgencyId) {
			this.parentServiceAgencyId = parentServiceAgencyId;
		}

		public ServiceRecordDTO getService() {
			return service;
		}

		public void setService(ServiceRecordDTO service) {
			this.service = service;
		}

		public String getServiceParentName() {
			return serviceParentName;
		}

		public void setServiceParentName(String serviceParentName) {
			this.serviceParentName = serviceParentName;
		}

		public boolean isMappedParent() {
			return mappedParent;
		}

		public void setMappedParent(boolean mappedParent) {
			this.mappedParent = mappedParent;
		}
	}
}
