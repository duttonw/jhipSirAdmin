package au.gov.qld.sir.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceRecord} entity.
 */
public class ServiceRecordDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 1)
    private String active;

    @Size(max = 255)
    private String eligibility;

    @Size(max = 255)
    private String fees;

    @Size(max = 255)
    private String groupHeader;

    @Size(max = 255)
    private String groupId;

    @Size(max = 255)
    private String interactionId;

    @Size(max = 255)
    private String keywords;

    @Size(max = 255)
    private String preRequisites;

    @Size(max = 255)
    private String qgsServiceId;

    @Size(max = 255)
    private String referenceUrl;

    @Size(max = 255)
    private String serviceName;

    private Instant validatedDate;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String preRequisitesNew;

    @Size(max = 255)
    private String referenceUrlNew;

    @Size(max = 255)
    private String eligibilityNew;

    @Size(max = 255)
    private String serviceContext;

    @Size(max = 255)
    private String longDescription;

    @NotNull
    @Size(max = 255)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 1)
    private String roadmapLoginRequired;

    @Size(max = 1)
    private String roadmapCustomerIdRequired;

    @Size(max = 1)
    private String roadmapCustomerDetails;

    @Size(max = 1)
    private String roadmapImproveIntention;

    @Size(max = 1)
    private String roadmapImproveFuture;

    @Size(max = 255)
    private String roadmapImproveType;

    @Size(max = 255)
    private String roadmapImproveWhen;

    @Size(max = 255)
    private String roadmapImproveHow;

    @Size(max = 255)
    private String roadmapMaturityCurrent;

    @Size(max = 255)
    private String roadmapMaturityDesired;

    @Size(max = 255)
    private String roadmapComments;

    @Size(max = 255)
    private String howTo;


    private Long deliveryOrgId;

    private Long parentServiceId;

    private Long serviceFranchiseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getGroupHeader() {
        return groupHeader;
    }

    public void setGroupHeader(String groupHeader) {
        this.groupHeader = groupHeader;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getQgsServiceId() {
        return qgsServiceId;
    }

    public void setQgsServiceId(String qgsServiceId) {
        this.qgsServiceId = qgsServiceId;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Instant getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(Instant validatedDate) {
        this.validatedDate = validatedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEligibilityNew() {
        return eligibilityNew;
    }

    public void setEligibilityNew(String eligibilityNew) {
        this.eligibilityNew = eligibilityNew;
    }

    public String getServiceContext() {
        return serviceContext;
    }

    public void setServiceContext(String serviceContext) {
        this.serviceContext = serviceContext;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getRoadmapImproveIntention() {
        return roadmapImproveIntention;
    }

    public void setRoadmapImproveIntention(String roadmapImproveIntention) {
        this.roadmapImproveIntention = roadmapImproveIntention;
    }

    public String getRoadmapImproveFuture() {
        return roadmapImproveFuture;
    }

    public void setRoadmapImproveFuture(String roadmapImproveFuture) {
        this.roadmapImproveFuture = roadmapImproveFuture;
    }

    public String getRoadmapImproveType() {
        return roadmapImproveType;
    }

    public void setRoadmapImproveType(String roadmapImproveType) {
        this.roadmapImproveType = roadmapImproveType;
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

    public String getRoadmapMaturityCurrent() {
        return roadmapMaturityCurrent;
    }

    public void setRoadmapMaturityCurrent(String roadmapMaturityCurrent) {
        this.roadmapMaturityCurrent = roadmapMaturityCurrent;
    }

    public String getRoadmapMaturityDesired() {
        return roadmapMaturityDesired;
    }

    public void setRoadmapMaturityDesired(String roadmapMaturityDesired) {
        this.roadmapMaturityDesired = roadmapMaturityDesired;
    }

    public String getRoadmapComments() {
        return roadmapComments;
    }

    public void setRoadmapComments(String roadmapComments) {
        this.roadmapComments = roadmapComments;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public Long getDeliveryOrgId() {
        return deliveryOrgId;
    }

    public void setDeliveryOrgId(Long serviceDeliveryOrganisationId) {
        this.deliveryOrgId = serviceDeliveryOrganisationId;
    }

    public Long getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(Long serviceRecordId) {
        this.parentServiceId = serviceRecordId;
    }

    public Long getServiceFranchiseId() {
        return serviceFranchiseId;
    }

    public void setServiceFranchiseId(Long serviceFranchiseId) {
        this.serviceFranchiseId = serviceFranchiseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRecordDTO serviceRecordDTO = (ServiceRecordDTO) o;
        if (serviceRecordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRecordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRecordDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", active='" + getActive() + "'" +
            ", eligibility='" + getEligibility() + "'" +
            ", fees='" + getFees() + "'" +
            ", groupHeader='" + getGroupHeader() + "'" +
            ", groupId='" + getGroupId() + "'" +
            ", interactionId='" + getInteractionId() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", preRequisites='" + getPreRequisites() + "'" +
            ", qgsServiceId='" + getQgsServiceId() + "'" +
            ", referenceUrl='" + getReferenceUrl() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", validatedDate='" + getValidatedDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", preRequisitesNew='" + getPreRequisitesNew() + "'" +
            ", referenceUrlNew='" + getReferenceUrlNew() + "'" +
            ", eligibilityNew='" + getEligibilityNew() + "'" +
            ", serviceContext='" + getServiceContext() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", roadmapLoginRequired='" + getRoadmapLoginRequired() + "'" +
            ", roadmapCustomerIdRequired='" + getRoadmapCustomerIdRequired() + "'" +
            ", roadmapCustomerDetails='" + getRoadmapCustomerDetails() + "'" +
            ", roadmapImproveIntention='" + getRoadmapImproveIntention() + "'" +
            ", roadmapImproveFuture='" + getRoadmapImproveFuture() + "'" +
            ", roadmapImproveType='" + getRoadmapImproveType() + "'" +
            ", roadmapImproveWhen='" + getRoadmapImproveWhen() + "'" +
            ", roadmapImproveHow='" + getRoadmapImproveHow() + "'" +
            ", roadmapMaturityCurrent='" + getRoadmapMaturityCurrent() + "'" +
            ", roadmapMaturityDesired='" + getRoadmapMaturityDesired() + "'" +
            ", roadmapComments='" + getRoadmapComments() + "'" +
            ", howTo='" + getHowTo() + "'" +
            ", deliveryOrg=" + getDeliveryOrgId() +
            ", parentService=" + getParentServiceId() +
            ", serviceFranchise=" + getServiceFranchiseId() +
            "}";
    }
}
