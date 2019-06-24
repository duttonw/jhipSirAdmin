package au.gov.qld.sir.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrantImporterRecord {

	@JsonProperty("Funding agency")
	private String agencyAcronym;

	@JsonProperty("Program title")
	private String serviceName;

	@JsonProperty("Sub-program title")
	private String subProgramTitle;

	@JsonProperty("Website")
	private String referenceUrl;

	@JsonProperty("Eligibility")
	private String eligibility;

	@JsonProperty("Purpose")
	private String longDescription;
	
	private String serviceId;

	@JsonProperty("Service ID")
	private String agencyServiceId;

	@JsonProperty("maintainer")
	private String maintainerEmail;

	@JsonProperty("Category1")
	private String category1;

	@JsonProperty("Category2")
	private String category2;

	@JsonProperty("Category3")
	private String category3;

	@JsonProperty("Applicant type1")
	private String applicantType1;

	@JsonProperty("Applicant type2")
	private String applicantType2;

	@JsonProperty("Applicant type3")
	private String applicantType3;

	@JsonProperty("Client group1")
	private String clientGroup1;

	@JsonProperty("Client group2")
	private String clientGroup2;

	@JsonProperty("Client group3")
	private String clientGroup3;

	@JsonProperty("Client group4")
	private String clientGroup4;

	@JsonProperty("Client group5")
	private String clientGroup5;

	@JsonProperty("Client group6")
	private String clientGroup6;

	@JsonProperty("Business specific activity1")
	private String businessSpecificActivity1;

	@JsonProperty("Business specific activity2")
	private String businessSpecificActivity2;

	@JsonProperty("Business specific activity3")
	private String businessSpecificActivity3;

	@JsonProperty("Business specific activity4")
	private String businessSpecificActivity4;

	@JsonProperty("Business specific activity5")
	private String businessSpecificActivity5;

	@JsonProperty("Assistance type1")
	private String assistanceType1;

	@JsonProperty("Assistance type2")
	private String assistanceType2;

	@JsonProperty("Assistance type3")
	private String assistanceType3;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Opening date (date format)")
	private String openingDateDateFormat;

	@JsonProperty("Closing date (date format)")
	private String closingDateDateFormat;

	@JsonProperty("Opening date (text)")
	private String openingDateText;

	@JsonProperty("Closing date (text)")
	private String closingDateText;

	@JsonProperty("Financial year budget")
	private String financialYearBudget;

	@JsonProperty("Additional budget information")
	private String additionalYearBudget;

	@JsonProperty("Maximum (indicative) grant amount (dollar amount)")
	private String maxGrantAmountDollar;

	@JsonProperty("Maximum (indicative) grant amount (text)")
	private String maxGrantAmountText;

	@JsonProperty("How will I know if I'm successful?")
	private String howWillIKnowIfImSuccessful;

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getApplicantType1() {
		return applicantType1;
	}

	public void setApplicantType1(String applicantType1) {
		this.applicantType1 = applicantType1;
	}

	public String getApplicantType2() {
		return applicantType2;
	}

	public void setApplicantType2(String applicantType2) {
		this.applicantType2 = applicantType2;
	}

	public String getApplicantType3() {
		return applicantType3;
	}

	public void setApplicantType3(String applicantType3) {
		this.applicantType3 = applicantType3;
	}

	public String getClientGroup1() {
		return clientGroup1;
	}

	public void setClientGroup1(String clientGroup1) {
		this.clientGroup1 = clientGroup1;
	}

	public String getClientGroup2() {
		return clientGroup2;
	}

	public void setClientGroup2(String clientGroup2) {
		this.clientGroup2 = clientGroup2;
	}

	public String getClientGroup3() {
		return clientGroup3;
	}

	public void setClientGroup3(String clientGroup3) {
		this.clientGroup3 = clientGroup3;
	}

	public String getClientGroup4() {
		return clientGroup4;
	}

	public void setClientGroup4(String clientGroup4) {
		this.clientGroup4 = clientGroup4;
	}

	public String getClientGroup5() {
		return clientGroup5;
	}

	public void setClientGroup5(String clientGroup5) {
		this.clientGroup5 = clientGroup5;
	}

	public String getClientGroup6() {
		return clientGroup6;
	}

	public void setClientGroup6(String clientGroup6) {
		this.clientGroup6 = clientGroup6;
	}

	public String getBusinessSpecificActivity1() {
		return businessSpecificActivity1;
	}

	public void setBusinessSpecificActivity1(String businessSpecificActivity1) {
		this.businessSpecificActivity1 = businessSpecificActivity1;
	}

	public String getBusinessSpecificActivity2() {
		return businessSpecificActivity2;
	}

	public void setBusinessSpecificActivity2(String businessSpecificActivity2) {
		this.businessSpecificActivity2 = businessSpecificActivity2;
	}

	public String getBusinessSpecificActivity3() {
		return businessSpecificActivity3;
	}

	public void setBusinessSpecificActivity3(String businessSpecificActivity3) {
		this.businessSpecificActivity3 = businessSpecificActivity3;
	}

	public String getBusinessSpecificActivity4() {
		return businessSpecificActivity4;
	}

	public void setBusinessSpecificActivity4(String businessSpecificActivity4) {
		this.businessSpecificActivity4 = businessSpecificActivity4;
	}

	public String getBusinessSpecificActivity5() {
		return businessSpecificActivity5;
	}

	public void setBusinessSpecificActivity5(String businessSpecificActivity5) {
		this.businessSpecificActivity5 = businessSpecificActivity5;
	}

	public String getAssistanceType1() {
		return assistanceType1;
	}

	public void setAssistanceType1(String assistanceType1) {
		this.assistanceType1 = assistanceType1;
	}

	public String getAssistanceType2() {
		return assistanceType2;
	}

	public void setAssistanceType2(String assistanceType2) {
		this.assistanceType2 = assistanceType2;
	}

	public String getAssistanceType3() {
		return assistanceType3;
	}

	public void setAssistanceType3(String assistanceType3) {
		this.assistanceType3 = assistanceType3;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpeningDateDateFormat() {
		return openingDateDateFormat;
	}

	public void setOpeningDateDateFormat(String openingDateDateFormat) {
		this.openingDateDateFormat = openingDateDateFormat;
	}

	public String getClosingDateDateFormat() {
		return closingDateDateFormat;
	}

	public void setClosingDateDateFormat(String closingDateDateFormat) {
		this.closingDateDateFormat = closingDateDateFormat;
	}

	public String getOpeningDateText() {
		return openingDateText;
	}

	public void setOpeningDateText(String openingDateText) {
		this.openingDateText = openingDateText;
	}

	public String getClosingDateText() {
		return closingDateText;
	}

	public void setClosingDateText(String closingDateText) {
		this.closingDateText = closingDateText;
	}

	public String getFinancialYearBudget() {
		return financialYearBudget;
	}

	public void setFinancialYearBudget(String financialYearBudget) {
		this.financialYearBudget = financialYearBudget;
	}

	public String getAdditionalYearBudget() {
		return additionalYearBudget;
	}

	public void setAdditionalYearBudget(String additionalYearBudget) {
		this.additionalYearBudget = additionalYearBudget;
	}

	public String getMaxGrantAmountDollar() {
		return maxGrantAmountDollar;
	}

	public void setMaxGrantAmountDollar(String maxGrantAmountDollar) {
		this.maxGrantAmountDollar = maxGrantAmountDollar;
	}

	public String getMaxGrantAmountText() {
		return maxGrantAmountText;
	}

	public void setMaxGrantAmountText(String maxGrantAmountText) {
		this.maxGrantAmountText = maxGrantAmountText;
	}

	public String getHowWillIKnowIfImSuccessful() {
		return howWillIKnowIfImSuccessful;
	}

	public void setHowWillIKnowIfImSuccessful(String howWillIKnowIfImSuccessful) {
		this.howWillIKnowIfImSuccessful = howWillIKnowIfImSuccessful;
	}

	public String getAgencyAcronym() {
		return agencyAcronym;
	}

	public String getMaintainerEmail() {
		return maintainerEmail;
	}

	public void setMaintainerEmail(String maintainerEmail) {
		this.maintainerEmail = maintainerEmail;
	}

	public void setAgencyAcronym(String agencyAcronym) {
		this.agencyAcronym = agencyAcronym;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@Override
	public String toString() {
		return "GrantImporterRecord [agencyAcronym=" + agencyAcronym + ", serviceName=" + serviceName + ", referenceUrl="
				+ referenceUrl + ", eligibility=" + eligibility + ", longDescription=" + longDescription
				+ ", serviceId=" + serviceId + "]";
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAgencyServiceId() {
		return agencyServiceId;
	}

	public void setAgencyServiceId(String agencyServiceId) {
		this.agencyServiceId = agencyServiceId;
	}

	public String getSubProgramTitle() {
		return subProgramTitle;
	}

	public void setSubProgramTitle(String subProgramTitle) {
		this.subProgramTitle = subProgramTitle;
	}
}

