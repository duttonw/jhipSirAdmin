package au.gov.qld.sir.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkImporterRecord {
	@JsonProperty("New SIR ID")
	private long serviceId;
	
	@JsonProperty("Agency acronym")
	private String agencyCode;
	
    @JsonProperty("Agency Website URL")
    private String agencyUrl;
    
    @JsonProperty("Agency Type")
    private String agencyTypeName;
    
	@JsonProperty("Service Info Validated Date")
	private String validateDate;
	
	@JsonProperty("Service Start Date")
	private String startDate;
	
	@JsonProperty("Service End Date")
	private String endDate;
	
	@JsonProperty("Agency Service ID")
	private String agencyServiceId;
	
	@JsonProperty("OLD SIR ID")
	private long oldSirId;
	
	@JsonProperty("OLD QGS ID")
	private String oldQgsId;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Service name")
	private String serviceName;
	
    @JsonProperty("Service interaction name")
    private String serviceInteractionName;
    
	@JsonProperty("Primary franchise")
	private String primaryFranchise;
	
	@JsonProperty("Business unit name")
	private String businessUnitName;
	
	@JsonProperty("Long description")
	private String longDescription;
	
	@JsonProperty("Short description")
	private String shortDescription;
	
	@JsonProperty("Who is eligible?")
	private String eligibilityNew;
	
	@JsonProperty("What you will need (prerequisites)")
	private String preRequisitesNew;
	
	@JsonProperty("More information URL")
	private String referenceUrlNew;
	
	@JsonProperty("Agency name")
	private String agencyName;
	
	@JsonProperty("Do it online URL")
	private String onlineChannel;
	
	@JsonProperty("Who do I call?")
	private String phoneChannel;
	
	@JsonProperty("Service owner name")
	private String serviceContactName;
	
	@JsonProperty("Service Owner role")
	private String serviceRoleType;
	
	@JsonProperty("Service owner email")
	private String serviceContactEmail;
	
	@JsonProperty("Service owner phone")
	private String serviceOwnerPhone;
	
	@JsonProperty("GovNet owner Name")
	private String govOwnerName;
	
	@JsonProperty("GovNet Role")
	private String govRoleType;
	
	@JsonProperty("GovNet email")
	private String govEmail;
	
	@JsonProperty("GovNet phone")
	private String govPhone;
	
	@JsonProperty("Service context Name")
	private String serviceContextName;
	
	@JsonProperty("Fees")
	private String fees;
	
	@JsonProperty("Keywords")
	private String keywords;
	
	@JsonProperty("PDF Form Name")
	private String pdfFormName;
	
	@JsonProperty("URL")
	private String pdfUrl;
	
	@JsonProperty("Source")
	private String pdfSource;
	
	@JsonProperty("SD Channel type")
	private String sdChannelType;
	
	@JsonProperty("SD Channel detail")
	private String sdChannelDetail;
	
	@JsonProperty("Do you customers log-in to access this services?")
	private String roadmapLoginRequired;
	
	@JsonProperty("Do your customers need to prove their ID?")
	private String roadmapCustomerIdRequired;
	
	@JsonProperty("Do you maintain customer details")
	private String roadmapCustomerDetails;
	
	@JsonProperty("Are you intending to improve this service?")
	private String roadmapImproveIntension;
	
	@JsonProperty("If yes, what type of improvement?")
	private String roadMapImproveType;
	
	@JsonProperty("If yes - When planned?")
	private String roadmapImproveWhen;
	
	@JsonProperty("If yes - How will it be improved?")
	private String roadmapImproveHow;
	
    @JsonProperty("If no - Would you like to improve service at some point?")
    private String roadmapImproveFuture;
    
    @JsonProperty("Current Maturity Level")
    private String roadmapCurrentMaturity;
    
    @JsonProperty("Desired Maturity Level")
    private String roadmapDesiredMaturity;
    
    @JsonProperty("Comment")
	private String roadmapComment;

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public String getAgencyTypeName() {
		return agencyTypeName;
	}

	public void setAgencyTypeName(String agencyTypeName) {
		this.agencyTypeName = agencyTypeName;
	}

	public String getValidateDate() {
		return validateDate;
	}

	public void setValidateDate(String validateDate) {
		this.validateDate = validateDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAgencyServiceId() {
		return agencyServiceId;
	}

	public void setAgencyServiceId(String agencyServiceId) {
		this.agencyServiceId = agencyServiceId;
	}

	public long getOldSirId() {
		return oldSirId;
	}

	public void setOldSirId(long oldSirId) {
		this.oldSirId = oldSirId;
	}

	public String getOldQgsId() {
		return oldQgsId;
	}

	public void setOldQgsId(String oldQgsId) {
		this.oldQgsId = oldQgsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPrimaryFranchise() {
		return primaryFranchise;
	}

	public void setPrimaryFranchise(String primaryFranchise) {
		this.primaryFranchise = primaryFranchise;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
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

	public String getPreRequisitesNew() {
		return preRequisitesNew;
	}

	public void setPreRequisitesNew(String preRequisitesNew) {
		this.preRequisitesNew = preRequisitesNew;
	}

	public String getReferenceUrlNew() {
		return referenceUrlNew;
	}

	public void setReferenceUrlNew(String referenceUrlNew) {
		this.referenceUrlNew = referenceUrlNew;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getOnlineChannel() {
		return onlineChannel;
	}

	public void setOnlineChannel(String onlineChannel) {
		this.onlineChannel = onlineChannel;
	}

	public String getPhoneChannel() {
		return phoneChannel;
	}

	public void setPhoneChannel(String phoneChannel) {
		this.phoneChannel = phoneChannel;
	}

	public String getServiceContactName() {
		return serviceContactName;
	}

	public void setServiceContactName(String serviceContactName) {
		this.serviceContactName = serviceContactName;
	}

	public String getServiceRoleType() {
		return serviceRoleType;
	}

	public void setServiceRoleType(String serviceRoleType) {
		this.serviceRoleType = serviceRoleType;
	}

	public String getServiceContactEmail() {
		return serviceContactEmail;
	}

	public void setServiceContactEmail(String serviceContactEmail) {
		this.serviceContactEmail = serviceContactEmail;
	}

	public String getServiceOwnerPhone() {
		return serviceOwnerPhone;
	}

	public void setServiceOwnerPhone(String serviceOwnerPhone) {
		this.serviceOwnerPhone = serviceOwnerPhone;
	}

	public String getGovOwnerName() {
		return govOwnerName;
	}

	public void setGovOwnerName(String govOwnerName) {
		this.govOwnerName = govOwnerName;
	}

	public String getGovRoleType() {
		return govRoleType;
	}

	public void setGovRoleType(String govRoleType) {
		this.govRoleType = govRoleType;
	}

	public String getGovEmail() {
		return govEmail;
	}

	public void setGovEmail(String govEmail) {
		this.govEmail = govEmail;
	}

	public String getGovPhone() {
		return govPhone;
	}

	public void setGovPhone(String govPhone) {
		this.govPhone = govPhone;
	}

	public String getServiceContextName() {
		return serviceContextName;
	}

	public void setServiceContextName(String serviceContextName) {
		this.serviceContextName = serviceContextName;
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

	public String getPdfFormName() {
		return pdfFormName;
	}

	public void setPdfFormName(String pdfFormName) {
		this.pdfFormName = pdfFormName;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public String getPdfSource() {
		return pdfSource;
	}

	public void setPdfSource(String pdfSource) {
		this.pdfSource = pdfSource;
	}

	public String getSdChannelType() {
		return sdChannelType;
	}

	public void setSdChannelType(String sdChannelType) {
		this.sdChannelType = sdChannelType;
	}

	public String getSdChannelDetail() {
		return sdChannelDetail;
	}

	public void setSdChannelDetail(String sdChannelDetail) {
		this.sdChannelDetail = sdChannelDetail;
	}

	public String getRoadmapLoginRequired() {
		return roadmapLoginRequired;
	}

	public void setRoadmapLoginRequired(String roadmapLoginRequired) {
		this.roadmapLoginRequired = roadmapLoginRequired;
	}

	public String getRoadmapCustomerIdRequired() {
		return roadmapCustomerIdRequired;
	}

	public void setRoadmapCustomerIdRequired(String roadmapCustomerIdRequired) {
		this.roadmapCustomerIdRequired = roadmapCustomerIdRequired;
	}

	public String getRoadmapCustomerDetails() {
		return roadmapCustomerDetails;
	}

	public void setRoadmapCustomerDetails(String roadmapCustomerDetails) {
		this.roadmapCustomerDetails = roadmapCustomerDetails;
	}

	public String getRoadmapImproveIntension() {
		return roadmapImproveIntension;
	}

	public void setRoadmapImproveIntension(String roadmapImproveIntension) {
		this.roadmapImproveIntension = roadmapImproveIntension;
	}

	public String getRoadMapImproveType() {
		return roadMapImproveType;
	}

	public void setRoadMapImproveType(String roadMapImproveType) {
		this.roadMapImproveType = roadMapImproveType;
	}

	public String getRoadmapImproveWhen() {
		return roadmapImproveWhen;
	}

	public void setRoadmapImproveWhen(String roadmapImproveWhen) {
		this.roadmapImproveWhen = roadmapImproveWhen;
	}

	public String getRoadmapImproveHow() {
		return roadmapImproveHow;
	}

	public void setRoadmapImproveHow(String roadmapImproveHow) {
		this.roadmapImproveHow = roadmapImproveHow;
	}

	public String getRoadmapImproveFuture() {
		return roadmapImproveFuture;
	}

	public void setRoadmapImproveFuture(String roadmapImproveFuture) {
		this.roadmapImproveFuture = roadmapImproveFuture;
	}

	public String getRoadmapCurrentMaturity() {
		return roadmapCurrentMaturity;
	}

	public void setRoadmapCurrentMaturity(String roadmapCurrentMaturity) {
		this.roadmapCurrentMaturity = roadmapCurrentMaturity;
	}

	public String getRoadmapDesiredMaturity() {
		return roadmapDesiredMaturity;
	}

	public void setRoadmapDesiredMaturity(String roadmapDesiredMaturity) {
		this.roadmapDesiredMaturity = roadmapDesiredMaturity;
	}

	public String getRoadmapComment() {
		return roadmapComment;
	}

	public void setRoadmapComment(String roadmapComment) {
		this.roadmapComment = roadmapComment;
	}

	@Override
	public String toString() {
		return "BulkImporterRecordRecord [serviceId=" + serviceId + ", agencyCode=" + agencyCode + ", agencyUrl=" + agencyUrl
				+ ", agencyTypeName=" + agencyTypeName + ", validateDate=" + validateDate + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", agencyServiceId=" + agencyServiceId + ", oldSirId=" + oldSirId
				+ ", oldQgsId=" + oldQgsId + ", status=" + status + ", serviceName=" + serviceName
				+ ", serviceInteractionName=" + serviceInteractionName + ", primaryFranchise=" + primaryFranchise
				+ ", businessUnitName=" + businessUnitName + ", longDescription=" + longDescription
				+ ", shortDescription=" + shortDescription + ", eligibilityNew=" + eligibilityNew
				+ ", preRequisitesNew=" + preRequisitesNew + ", referenceUrlNew=" + referenceUrlNew + ", agencyName="
				+ agencyName + ", onlineChannel=" + onlineChannel + ", phoneChannel=" + phoneChannel
				+ ", serviceContactName=" + serviceContactName + ", serviceRoleType=" + serviceRoleType
				+ ", serviceContactEmail=" + serviceContactEmail + ", serviceOwnerPhone=" + serviceOwnerPhone
				+ ", govOwnerName=" + govOwnerName + ", govRoleType=" + govRoleType + ", govEmail=" + govEmail
				+ ", govPhone=" + govPhone + ", serviceContextName=" + serviceContextName + ", fees=" + fees
				+ ", keywords=" + keywords + ", pdfFormName=" + pdfFormName + ", pdfUrl=" + pdfUrl + ", pdfSource="
				+ pdfSource + ", sdChannelType=" + sdChannelType + ", sdChannelDetail=" + sdChannelDetail
				+ ", roadmapLoginRequired=" + roadmapLoginRequired + ", roadmapCustomerIdRequired="
				+ roadmapCustomerIdRequired + ", roadmapCustomerDetails=" + roadmapCustomerDetails
				+ ", roadmapImproveIntension=" + roadmapImproveIntension + ", roadMapImproveType=" + roadMapImproveType
				+ ", roadmapImproveWhen=" + roadmapImproveWhen + ", roadmapImproveHow=" + roadmapImproveHow
				+ ", roadmapImproveFuture=" + roadmapImproveFuture + ", roadmapCurrentMaturity="
				+ roadmapCurrentMaturity + ", roadmapDesiredMaturity=" + roadmapDesiredMaturity + ", roadmapComment="
				+ roadmapComment + "]";
	}

	
	
}
