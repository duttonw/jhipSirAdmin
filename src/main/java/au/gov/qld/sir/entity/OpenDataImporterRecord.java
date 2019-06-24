package au.gov.qld.sir.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpenDataImporterRecord {

	@JsonProperty("Agency service ID")
	private String agencyServiceId;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Service name")
	private String serviceName;

	@JsonProperty("Service interaction name")
	private String serviceInteractionName;

	@JsonProperty("Service context")
	private String serviceContext;

	@JsonProperty("Primary franchise")
	private String franchiseName;

	@JsonProperty("Long description")
	private String longDescription;

	@JsonProperty("Short description")
	private String shortDescription;

	@JsonProperty("Who is eligible?")
	private String eligibilityNew;

	@JsonProperty("What you will need (prerequisites)")
	private String prerequisiteNew;

	@JsonProperty("How to")
	private String howTo;

	@JsonProperty("More information URL")
	private String referenceUrlNew;

	@JsonProperty("Do it online URL")
	private String onlineDeliveryChannel;

	@JsonProperty("Who do I call?")
	private String phoneDeliveryChannel;

	@JsonProperty("Fees")
	private String fees;

	@JsonProperty("Keywords")
	private String keywords;

	@JsonProperty("Agency acronym")
	private String agencyCode;

	@JsonProperty("maintainer")
	private String maintainerEmail;

	@JsonProperty("Service start date")
	private String serviceStartDate;

	@JsonProperty("Service end date")
	private String serviceEndDate;

	public String getAgencyServiceId() {
		return agencyServiceId;
	}

	public void setAgencyServiceId(String agencyServiceId) {
		this.agencyServiceId = agencyServiceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		// Convert to int (boolean)
		if (status == null) {
			this.status = "";
			return;
		}

		switch (status) {
			case "Active":
				this.status = "1";
				break;
			case "Inactive":
				this.status = "0";
				break;
			default:
				// Default to empty string as it is invalid
				this.status = "";
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceInteractionName() {
		return serviceInteractionName;
	}

	public void setServiceInteractionName(String serviceInteractionName) {
		this.serviceInteractionName = serviceInteractionName;
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getEligibilityNew() {
		return eligibilityNew;
	}

	public void setEligibilityNew(String eligibilityNew) {
		this.eligibilityNew = eligibilityNew;
	}

	public String getPrerequisiteNew() {
		return prerequisiteNew;
	}

	public void setPrerequisiteNew(String prerequisiteNew) {
		this.prerequisiteNew = prerequisiteNew;
	}

	public String getHowTo() {
		return howTo;
	}

	public void setHowTo(String howTo) {
		this.howTo = howTo;
	}

	public String getReferenceUrlNew() {
		return referenceUrlNew;
	}

	public void setReferenceUrlNew(String referenceUrlNew) {
		this.referenceUrlNew = referenceUrlNew;
	}

	public String getOnlineDeliveryChannel() {
		return onlineDeliveryChannel;
	}

	public void setOnlineDeliveryChannel(String onlineDeliveryChannel) {
		this.onlineDeliveryChannel = onlineDeliveryChannel;
	}

	public String getPhoneDeliveryChannel() {
		return phoneDeliveryChannel;
	}

	public void setPhoneDeliveryChannel(String phoneDeliveryChannel) {
		this.phoneDeliveryChannel = phoneDeliveryChannel;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getMaintainerEmail() {
		return maintainerEmail;
	}

	public void setMaintainerEmail(String maintainerEmail) {
		this.maintainerEmail = maintainerEmail;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}


	private static Logger log = LogManager.getRootLogger();

	public boolean requiredParentService() {
		if (this.serviceContext != null && this.serviceContext != "") {
			if ((this.serviceInteractionName == null && this.serviceInteractionName == "") || (this.serviceName == null && this.serviceName == "")) {
				return false;
			}
		}
		return (this.serviceInteractionName == null && this.serviceInteractionName == "") || this.serviceName != null;
	}

	/**
	 * Validate required fields and field length
	 * @return True if valid, false otherwise
	 */
	public boolean validate() {

		List<Object> requiredFields = new ArrayList<>();
		requiredFields.add(agencyServiceId);
		requiredFields.add(status);
		requiredFields.add(serviceName);
		requiredFields.add(shortDescription);
		requiredFields.add(franchiseName);
		requiredFields.add(agencyCode);

		if (requiredParentService()) {
			return checkNotNull(requiredFields) && checkTextLength(agencyServiceId, 20, true) && checkTextLength(status, 10, true)
					&& checkTextLength(serviceName, 120, true) && checkTextLength(serviceInteractionName, 120, false)
					&& checkTextLength(serviceContext, 120, false) && checkTextLength(shortDescription, 500, true)
					&& checkTextLength(phoneDeliveryChannel, 50, false) && checkTextLength(franchiseName, 50, true)
					&& checkTextLength(agencyCode, 10, true);
		}
		return false;
	}

	private boolean checkNotNull(List<Object> list) {
		for (Object obj : list) {
			if (obj == null) {
				return false;
			}
		}
		return true;
	}

	private boolean checkTextLength(String str, int length, boolean required) {
		if (required && str == null) {
			return false;
		} else if (str == null) {
			return true;
		}

		return str.trim().length() <= length;
	}

	public Object validateError() {
		ArrayList<String> validationFailure = new ArrayList<String>();
		List<Object> requiredFields = new ArrayList<>();
		requiredFields.add(agencyServiceId);
		requiredFields.add(status);
		requiredFields.add(serviceName);
		requiredFields.add(shortDescription);
		requiredFields.add(franchiseName);
		requiredFields.add(agencyCode);


		if (!checkNotNull(requiredFields)) {
			validationFailure.add("Agency service ID, Status, Service name, Primary franchise and Agency acronym are required");
		}

		if (!checkTextLength(agencyServiceId, 20, true)) {
			validationFailure.add("Agency service ID must be between 1 and 20 characters");
		}

		if (!checkTextLength(status, 10, true)) {
			validationFailure.add("Status must be Active or Inactive");
		}

		if (!checkTextLength(serviceName, 120, true)) {
			validationFailure.add("Service name must be between 2 and 120 characters");
		}

		if (!checkTextLength(serviceInteractionName, 120, false)) {
			validationFailure.add("Interaction name must be less than 120 characters");
		}

		if (!checkTextLength(serviceContext, 120, false)) {
			validationFailure.add("Service context must be less than 120 characters");
		}

		if (!checkTextLength(shortDescription, 500, true)) {
			validationFailure.add("Short description must be between 1 and 500 characters");
		}

		if (!checkTextLength(phoneDeliveryChannel, 50, false)) {
			validationFailure.add("Who do I call? must be between 6 and 50 characters");
		}

		if (!checkTextLength(franchiseName, 50, true)) {
			validationFailure.add("Primary franchise name must be between 2 and 50 characters");
		}

		if (!checkTextLength(agencyCode, 10, true)) {
			validationFailure.add("Agency acronym must be between 2 and 10 characters");
		}

		return validationFailure;
	}

	public String toString() {
		String str = "";
		str += "\nAgency service ID: " + this.agencyServiceId;
		str += "\nStatus: " + this.status;
		str += "\nService name: " + this.serviceName;
		str += "\nService interaction name: " + this.serviceInteractionName;
		str += "\nService context: " + this.serviceContext;
		str += "\nShort description: " + this.shortDescription;
		str += "\nWho is eligible?: " + this.eligibilityNew;
		str += "\nWhat you will need (prerequisites): " + this.prerequisiteNew;
		str += "\nHow to: " + this.howTo;
		str += "\nMore information URL: " + this.referenceUrlNew;
		str += "\nDo it online URL: " + this.onlineDeliveryChannel;
		str += "\nWho do I call?: " + this.phoneDeliveryChannel;
		str += "\nFees: " + this.fees;
		str += "\nKeywords: " + this.keywords;
		str += "\nPrimary franchise: " + this.franchiseName;
		str += "\nAgency acronym: " + this.agencyCode;
		str += "\nService start date: " + this.serviceStartDate;
		str += "\nService end date: " + this.serviceEndDate;

		return str;

	}

	/**
	 * Enum to represent the status that a service can have.
	 */
	public enum Status {
		INACTIVE("N"),
		ACTIVE("Y");

		private String placeholder;

		Status(String placeholder) {
			this.placeholder = placeholder;
		}

		public String getPlaceholder() {
			return this.placeholder;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OpenDataImporterRecord that = (OpenDataImporterRecord) o;
		return Objects.equals(agencyServiceId, that.agencyServiceId) &&
			Objects.equals(status, that.status) &&
			Objects.equals(serviceName, that.serviceName) &&
			Objects.equals(serviceInteractionName, that.serviceInteractionName) &&
			Objects.equals(serviceContext, that.serviceContext) &&
			Objects.equals(franchiseName, that.franchiseName) &&
			Objects.equals(longDescription, that.longDescription) &&
			Objects.equals(shortDescription, that.shortDescription) &&
			Objects.equals(eligibilityNew, that.eligibilityNew) &&
			Objects.equals(prerequisiteNew, that.prerequisiteNew) &&
			Objects.equals(howTo, that.howTo) &&
			Objects.equals(referenceUrlNew, that.referenceUrlNew) &&
			Objects.equals(onlineDeliveryChannel, that.onlineDeliveryChannel) &&
			Objects.equals(phoneDeliveryChannel, that.phoneDeliveryChannel) &&
			Objects.equals(fees, that.fees) &&
			Objects.equals(keywords, that.keywords) &&
			Objects.equals(agencyCode, that.agencyCode) &&
			Objects.equals(maintainerEmail, that.maintainerEmail) &&
			Objects.equals(serviceStartDate, that.serviceStartDate) &&
			Objects.equals(serviceEndDate, that.serviceEndDate);
	}

	@Override
	public int hashCode() {

		return Objects.hash(agencyServiceId, status, serviceName, serviceInteractionName, serviceContext, franchiseName, longDescription, shortDescription, eligibilityNew, prerequisiteNew, howTo, referenceUrlNew, onlineDeliveryChannel, phoneDeliveryChannel, fees, keywords, agencyCode, maintainerEmail, serviceStartDate, serviceEndDate);
	}
}

