package au.gov.qld.sir.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceRecord} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceRecordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceRecordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter active;

    private StringFilter eligibility;

    private StringFilter fees;

    private StringFilter groupHeader;

    private StringFilter groupId;

    private StringFilter interactionId;

    private StringFilter keywords;

    private StringFilter preRequisites;

    private StringFilter qgsServiceId;

    private StringFilter referenceUrl;

    private StringFilter serviceName;

    private InstantFilter validatedDate;

    private StringFilter description;

    private StringFilter preRequisitesNew;

    private StringFilter referenceUrlNew;

    private StringFilter eligibilityNew;

    private StringFilter serviceContext;

    private StringFilter longDescription;

    private StringFilter name;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter roadmapLoginRequired;

    private StringFilter roadmapCustomerIdRequired;

    private StringFilter roadmapCustomerDetails;

    private StringFilter roadmapImproveIntention;

    private StringFilter roadmapImproveFuture;

    private StringFilter roadmapImproveType;

    private StringFilter roadmapImproveWhen;

    private StringFilter roadmapImproveHow;

    private StringFilter roadmapMaturityCurrent;

    private StringFilter roadmapMaturityDesired;

    private StringFilter roadmapComments;

    private StringFilter howTo;

    private LongFilter deliveryOrgId;

    private LongFilter parentServiceId;

    private LongFilter serviceFranchiseId;

    private LongFilter applicationServiceOverrideId;

    private LongFilter integrationMappingId;

    private LongFilter serviceRecordId;

    private LongFilter serviceDeliveryId;

    private LongFilter serviceDeliveryFormId;

    private LongFilter serviceDescriptionId;

    private LongFilter serviceEventId;

    private LongFilter serviceEvidenceId;

    private LongFilter serviceGroupId;

    private LongFilter serviceNameId;

    private LongFilter fromServiceId;

    private LongFilter toServiceId;

    private LongFilter serviceSupportRoleId;

    private LongFilter serviceTagItemsId;

    public ServiceRecordCriteria(){
    }

    public ServiceRecordCriteria(ServiceRecordCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.eligibility = other.eligibility == null ? null : other.eligibility.copy();
        this.fees = other.fees == null ? null : other.fees.copy();
        this.groupHeader = other.groupHeader == null ? null : other.groupHeader.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
        this.interactionId = other.interactionId == null ? null : other.interactionId.copy();
        this.keywords = other.keywords == null ? null : other.keywords.copy();
        this.preRequisites = other.preRequisites == null ? null : other.preRequisites.copy();
        this.qgsServiceId = other.qgsServiceId == null ? null : other.qgsServiceId.copy();
        this.referenceUrl = other.referenceUrl == null ? null : other.referenceUrl.copy();
        this.serviceName = other.serviceName == null ? null : other.serviceName.copy();
        this.validatedDate = other.validatedDate == null ? null : other.validatedDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.preRequisitesNew = other.preRequisitesNew == null ? null : other.preRequisitesNew.copy();
        this.referenceUrlNew = other.referenceUrlNew == null ? null : other.referenceUrlNew.copy();
        this.eligibilityNew = other.eligibilityNew == null ? null : other.eligibilityNew.copy();
        this.serviceContext = other.serviceContext == null ? null : other.serviceContext.copy();
        this.longDescription = other.longDescription == null ? null : other.longDescription.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.roadmapLoginRequired = other.roadmapLoginRequired == null ? null : other.roadmapLoginRequired.copy();
        this.roadmapCustomerIdRequired = other.roadmapCustomerIdRequired == null ? null : other.roadmapCustomerIdRequired.copy();
        this.roadmapCustomerDetails = other.roadmapCustomerDetails == null ? null : other.roadmapCustomerDetails.copy();
        this.roadmapImproveIntention = other.roadmapImproveIntention == null ? null : other.roadmapImproveIntention.copy();
        this.roadmapImproveFuture = other.roadmapImproveFuture == null ? null : other.roadmapImproveFuture.copy();
        this.roadmapImproveType = other.roadmapImproveType == null ? null : other.roadmapImproveType.copy();
        this.roadmapImproveWhen = other.roadmapImproveWhen == null ? null : other.roadmapImproveWhen.copy();
        this.roadmapImproveHow = other.roadmapImproveHow == null ? null : other.roadmapImproveHow.copy();
        this.roadmapMaturityCurrent = other.roadmapMaturityCurrent == null ? null : other.roadmapMaturityCurrent.copy();
        this.roadmapMaturityDesired = other.roadmapMaturityDesired == null ? null : other.roadmapMaturityDesired.copy();
        this.roadmapComments = other.roadmapComments == null ? null : other.roadmapComments.copy();
        this.howTo = other.howTo == null ? null : other.howTo.copy();
        this.deliveryOrgId = other.deliveryOrgId == null ? null : other.deliveryOrgId.copy();
        this.parentServiceId = other.parentServiceId == null ? null : other.parentServiceId.copy();
        this.serviceFranchiseId = other.serviceFranchiseId == null ? null : other.serviceFranchiseId.copy();
        this.applicationServiceOverrideId = other.applicationServiceOverrideId == null ? null : other.applicationServiceOverrideId.copy();
        this.integrationMappingId = other.integrationMappingId == null ? null : other.integrationMappingId.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
        this.serviceDeliveryId = other.serviceDeliveryId == null ? null : other.serviceDeliveryId.copy();
        this.serviceDeliveryFormId = other.serviceDeliveryFormId == null ? null : other.serviceDeliveryFormId.copy();
        this.serviceDescriptionId = other.serviceDescriptionId == null ? null : other.serviceDescriptionId.copy();
        this.serviceEventId = other.serviceEventId == null ? null : other.serviceEventId.copy();
        this.serviceEvidenceId = other.serviceEvidenceId == null ? null : other.serviceEvidenceId.copy();
        this.serviceGroupId = other.serviceGroupId == null ? null : other.serviceGroupId.copy();
        this.serviceNameId = other.serviceNameId == null ? null : other.serviceNameId.copy();
        this.fromServiceId = other.fromServiceId == null ? null : other.fromServiceId.copy();
        this.toServiceId = other.toServiceId == null ? null : other.toServiceId.copy();
        this.serviceSupportRoleId = other.serviceSupportRoleId == null ? null : other.serviceSupportRoleId.copy();
        this.serviceTagItemsId = other.serviceTagItemsId == null ? null : other.serviceTagItemsId.copy();
    }

    @Override
    public ServiceRecordCriteria copy() {
        return new ServiceRecordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(InstantFilter createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public InstantFilter getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(InstantFilter modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public LongFilter getVersion() {
        return version;
    }

    public void setVersion(LongFilter version) {
        this.version = version;
    }

    public StringFilter getActive() {
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public StringFilter getEligibility() {
        return eligibility;
    }

    public void setEligibility(StringFilter eligibility) {
        this.eligibility = eligibility;
    }

    public StringFilter getFees() {
        return fees;
    }

    public void setFees(StringFilter fees) {
        this.fees = fees;
    }

    public StringFilter getGroupHeader() {
        return groupHeader;
    }

    public void setGroupHeader(StringFilter groupHeader) {
        this.groupHeader = groupHeader;
    }

    public StringFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(StringFilter groupId) {
        this.groupId = groupId;
    }

    public StringFilter getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(StringFilter interactionId) {
        this.interactionId = interactionId;
    }

    public StringFilter getKeywords() {
        return keywords;
    }

    public void setKeywords(StringFilter keywords) {
        this.keywords = keywords;
    }

    public StringFilter getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(StringFilter preRequisites) {
        this.preRequisites = preRequisites;
    }

    public StringFilter getQgsServiceId() {
        return qgsServiceId;
    }

    public void setQgsServiceId(StringFilter qgsServiceId) {
        this.qgsServiceId = qgsServiceId;
    }

    public StringFilter getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(StringFilter referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public StringFilter getServiceName() {
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public InstantFilter getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(InstantFilter validatedDate) {
        this.validatedDate = validatedDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getPreRequisitesNew() {
        return preRequisitesNew;
    }

    public void setPreRequisitesNew(StringFilter preRequisitesNew) {
        this.preRequisitesNew = preRequisitesNew;
    }

    public StringFilter getReferenceUrlNew() {
        return referenceUrlNew;
    }

    public void setReferenceUrlNew(StringFilter referenceUrlNew) {
        this.referenceUrlNew = referenceUrlNew;
    }

    public StringFilter getEligibilityNew() {
        return eligibilityNew;
    }

    public void setEligibilityNew(StringFilter eligibilityNew) {
        this.eligibilityNew = eligibilityNew;
    }

    public StringFilter getServiceContext() {
        return serviceContext;
    }

    public void setServiceContext(StringFilter serviceContext) {
        this.serviceContext = serviceContext;
    }

    public StringFilter getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(StringFilter longDescription) {
        this.longDescription = longDescription;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getRoadmapLoginRequired() {
        return roadmapLoginRequired;
    }

    public void setRoadmapLoginRequired(StringFilter roadmapLoginRequired) {
        this.roadmapLoginRequired = roadmapLoginRequired;
    }

    public StringFilter getRoadmapCustomerIdRequired() {
        return roadmapCustomerIdRequired;
    }

    public void setRoadmapCustomerIdRequired(StringFilter roadmapCustomerIdRequired) {
        this.roadmapCustomerIdRequired = roadmapCustomerIdRequired;
    }

    public StringFilter getRoadmapCustomerDetails() {
        return roadmapCustomerDetails;
    }

    public void setRoadmapCustomerDetails(StringFilter roadmapCustomerDetails) {
        this.roadmapCustomerDetails = roadmapCustomerDetails;
    }

    public StringFilter getRoadmapImproveIntention() {
        return roadmapImproveIntention;
    }

    public void setRoadmapImproveIntention(StringFilter roadmapImproveIntention) {
        this.roadmapImproveIntention = roadmapImproveIntention;
    }

    public StringFilter getRoadmapImproveFuture() {
        return roadmapImproveFuture;
    }

    public void setRoadmapImproveFuture(StringFilter roadmapImproveFuture) {
        this.roadmapImproveFuture = roadmapImproveFuture;
    }

    public StringFilter getRoadmapImproveType() {
        return roadmapImproveType;
    }

    public void setRoadmapImproveType(StringFilter roadmapImproveType) {
        this.roadmapImproveType = roadmapImproveType;
    }

    public StringFilter getRoadmapImproveWhen() {
        return roadmapImproveWhen;
    }

    public void setRoadmapImproveWhen(StringFilter roadmapImproveWhen) {
        this.roadmapImproveWhen = roadmapImproveWhen;
    }

    public StringFilter getRoadmapImproveHow() {
        return roadmapImproveHow;
    }

    public void setRoadmapImproveHow(StringFilter roadmapImproveHow) {
        this.roadmapImproveHow = roadmapImproveHow;
    }

    public StringFilter getRoadmapMaturityCurrent() {
        return roadmapMaturityCurrent;
    }

    public void setRoadmapMaturityCurrent(StringFilter roadmapMaturityCurrent) {
        this.roadmapMaturityCurrent = roadmapMaturityCurrent;
    }

    public StringFilter getRoadmapMaturityDesired() {
        return roadmapMaturityDesired;
    }

    public void setRoadmapMaturityDesired(StringFilter roadmapMaturityDesired) {
        this.roadmapMaturityDesired = roadmapMaturityDesired;
    }

    public StringFilter getRoadmapComments() {
        return roadmapComments;
    }

    public void setRoadmapComments(StringFilter roadmapComments) {
        this.roadmapComments = roadmapComments;
    }

    public StringFilter getHowTo() {
        return howTo;
    }

    public void setHowTo(StringFilter howTo) {
        this.howTo = howTo;
    }

    public LongFilter getDeliveryOrgId() {
        return deliveryOrgId;
    }

    public void setDeliveryOrgId(LongFilter deliveryOrgId) {
        this.deliveryOrgId = deliveryOrgId;
    }

    public LongFilter getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(LongFilter parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public LongFilter getServiceFranchiseId() {
        return serviceFranchiseId;
    }

    public void setServiceFranchiseId(LongFilter serviceFranchiseId) {
        this.serviceFranchiseId = serviceFranchiseId;
    }

    public LongFilter getApplicationServiceOverrideId() {
        return applicationServiceOverrideId;
    }

    public void setApplicationServiceOverrideId(LongFilter applicationServiceOverrideId) {
        this.applicationServiceOverrideId = applicationServiceOverrideId;
    }

    public LongFilter getIntegrationMappingId() {
        return integrationMappingId;
    }

    public void setIntegrationMappingId(LongFilter integrationMappingId) {
        this.integrationMappingId = integrationMappingId;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public LongFilter getServiceDeliveryId() {
        return serviceDeliveryId;
    }

    public void setServiceDeliveryId(LongFilter serviceDeliveryId) {
        this.serviceDeliveryId = serviceDeliveryId;
    }

    public LongFilter getServiceDeliveryFormId() {
        return serviceDeliveryFormId;
    }

    public void setServiceDeliveryFormId(LongFilter serviceDeliveryFormId) {
        this.serviceDeliveryFormId = serviceDeliveryFormId;
    }

    public LongFilter getServiceDescriptionId() {
        return serviceDescriptionId;
    }

    public void setServiceDescriptionId(LongFilter serviceDescriptionId) {
        this.serviceDescriptionId = serviceDescriptionId;
    }

    public LongFilter getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(LongFilter serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public LongFilter getServiceEvidenceId() {
        return serviceEvidenceId;
    }

    public void setServiceEvidenceId(LongFilter serviceEvidenceId) {
        this.serviceEvidenceId = serviceEvidenceId;
    }

    public LongFilter getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(LongFilter serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public LongFilter getServiceNameId() {
        return serviceNameId;
    }

    public void setServiceNameId(LongFilter serviceNameId) {
        this.serviceNameId = serviceNameId;
    }

    public LongFilter getFromServiceId() {
        return fromServiceId;
    }

    public void setFromServiceId(LongFilter fromServiceId) {
        this.fromServiceId = fromServiceId;
    }

    public LongFilter getToServiceId() {
        return toServiceId;
    }

    public void setToServiceId(LongFilter toServiceId) {
        this.toServiceId = toServiceId;
    }

    public LongFilter getServiceSupportRoleId() {
        return serviceSupportRoleId;
    }

    public void setServiceSupportRoleId(LongFilter serviceSupportRoleId) {
        this.serviceSupportRoleId = serviceSupportRoleId;
    }

    public LongFilter getServiceTagItemsId() {
        return serviceTagItemsId;
    }

    public void setServiceTagItemsId(LongFilter serviceTagItemsId) {
        this.serviceTagItemsId = serviceTagItemsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceRecordCriteria that = (ServiceRecordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(active, that.active) &&
            Objects.equals(eligibility, that.eligibility) &&
            Objects.equals(fees, that.fees) &&
            Objects.equals(groupHeader, that.groupHeader) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(interactionId, that.interactionId) &&
            Objects.equals(keywords, that.keywords) &&
            Objects.equals(preRequisites, that.preRequisites) &&
            Objects.equals(qgsServiceId, that.qgsServiceId) &&
            Objects.equals(referenceUrl, that.referenceUrl) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(validatedDate, that.validatedDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(preRequisitesNew, that.preRequisitesNew) &&
            Objects.equals(referenceUrlNew, that.referenceUrlNew) &&
            Objects.equals(eligibilityNew, that.eligibilityNew) &&
            Objects.equals(serviceContext, that.serviceContext) &&
            Objects.equals(longDescription, that.longDescription) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(roadmapLoginRequired, that.roadmapLoginRequired) &&
            Objects.equals(roadmapCustomerIdRequired, that.roadmapCustomerIdRequired) &&
            Objects.equals(roadmapCustomerDetails, that.roadmapCustomerDetails) &&
            Objects.equals(roadmapImproveIntention, that.roadmapImproveIntention) &&
            Objects.equals(roadmapImproveFuture, that.roadmapImproveFuture) &&
            Objects.equals(roadmapImproveType, that.roadmapImproveType) &&
            Objects.equals(roadmapImproveWhen, that.roadmapImproveWhen) &&
            Objects.equals(roadmapImproveHow, that.roadmapImproveHow) &&
            Objects.equals(roadmapMaturityCurrent, that.roadmapMaturityCurrent) &&
            Objects.equals(roadmapMaturityDesired, that.roadmapMaturityDesired) &&
            Objects.equals(roadmapComments, that.roadmapComments) &&
            Objects.equals(howTo, that.howTo) &&
            Objects.equals(deliveryOrgId, that.deliveryOrgId) &&
            Objects.equals(parentServiceId, that.parentServiceId) &&
            Objects.equals(serviceFranchiseId, that.serviceFranchiseId) &&
            Objects.equals(applicationServiceOverrideId, that.applicationServiceOverrideId) &&
            Objects.equals(integrationMappingId, that.integrationMappingId) &&
            Objects.equals(serviceRecordId, that.serviceRecordId) &&
            Objects.equals(serviceDeliveryId, that.serviceDeliveryId) &&
            Objects.equals(serviceDeliveryFormId, that.serviceDeliveryFormId) &&
            Objects.equals(serviceDescriptionId, that.serviceDescriptionId) &&
            Objects.equals(serviceEventId, that.serviceEventId) &&
            Objects.equals(serviceEvidenceId, that.serviceEvidenceId) &&
            Objects.equals(serviceGroupId, that.serviceGroupId) &&
            Objects.equals(serviceNameId, that.serviceNameId) &&
            Objects.equals(fromServiceId, that.fromServiceId) &&
            Objects.equals(toServiceId, that.toServiceId) &&
            Objects.equals(serviceSupportRoleId, that.serviceSupportRoleId) &&
            Objects.equals(serviceTagItemsId, that.serviceTagItemsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        createdDateTime,
        modifiedBy,
        modifiedDateTime,
        version,
        active,
        eligibility,
        fees,
        groupHeader,
        groupId,
        interactionId,
        keywords,
        preRequisites,
        qgsServiceId,
        referenceUrl,
        serviceName,
        validatedDate,
        description,
        preRequisitesNew,
        referenceUrlNew,
        eligibilityNew,
        serviceContext,
        longDescription,
        name,
        startDate,
        endDate,
        roadmapLoginRequired,
        roadmapCustomerIdRequired,
        roadmapCustomerDetails,
        roadmapImproveIntention,
        roadmapImproveFuture,
        roadmapImproveType,
        roadmapImproveWhen,
        roadmapImproveHow,
        roadmapMaturityCurrent,
        roadmapMaturityDesired,
        roadmapComments,
        howTo,
        deliveryOrgId,
        parentServiceId,
        serviceFranchiseId,
        applicationServiceOverrideId,
        integrationMappingId,
        serviceRecordId,
        serviceDeliveryId,
        serviceDeliveryFormId,
        serviceDescriptionId,
        serviceEventId,
        serviceEvidenceId,
        serviceGroupId,
        serviceNameId,
        fromServiceId,
        toServiceId,
        serviceSupportRoleId,
        serviceTagItemsId
        );
    }

    @Override
    public String toString() {
        return "ServiceRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (eligibility != null ? "eligibility=" + eligibility + ", " : "") +
                (fees != null ? "fees=" + fees + ", " : "") +
                (groupHeader != null ? "groupHeader=" + groupHeader + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (interactionId != null ? "interactionId=" + interactionId + ", " : "") +
                (keywords != null ? "keywords=" + keywords + ", " : "") +
                (preRequisites != null ? "preRequisites=" + preRequisites + ", " : "") +
                (qgsServiceId != null ? "qgsServiceId=" + qgsServiceId + ", " : "") +
                (referenceUrl != null ? "referenceUrl=" + referenceUrl + ", " : "") +
                (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
                (validatedDate != null ? "validatedDate=" + validatedDate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (preRequisitesNew != null ? "preRequisitesNew=" + preRequisitesNew + ", " : "") +
                (referenceUrlNew != null ? "referenceUrlNew=" + referenceUrlNew + ", " : "") +
                (eligibilityNew != null ? "eligibilityNew=" + eligibilityNew + ", " : "") +
                (serviceContext != null ? "serviceContext=" + serviceContext + ", " : "") +
                (longDescription != null ? "longDescription=" + longDescription + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (roadmapLoginRequired != null ? "roadmapLoginRequired=" + roadmapLoginRequired + ", " : "") +
                (roadmapCustomerIdRequired != null ? "roadmapCustomerIdRequired=" + roadmapCustomerIdRequired + ", " : "") +
                (roadmapCustomerDetails != null ? "roadmapCustomerDetails=" + roadmapCustomerDetails + ", " : "") +
                (roadmapImproveIntention != null ? "roadmapImproveIntention=" + roadmapImproveIntention + ", " : "") +
                (roadmapImproveFuture != null ? "roadmapImproveFuture=" + roadmapImproveFuture + ", " : "") +
                (roadmapImproveType != null ? "roadmapImproveType=" + roadmapImproveType + ", " : "") +
                (roadmapImproveWhen != null ? "roadmapImproveWhen=" + roadmapImproveWhen + ", " : "") +
                (roadmapImproveHow != null ? "roadmapImproveHow=" + roadmapImproveHow + ", " : "") +
                (roadmapMaturityCurrent != null ? "roadmapMaturityCurrent=" + roadmapMaturityCurrent + ", " : "") +
                (roadmapMaturityDesired != null ? "roadmapMaturityDesired=" + roadmapMaturityDesired + ", " : "") +
                (roadmapComments != null ? "roadmapComments=" + roadmapComments + ", " : "") +
                (howTo != null ? "howTo=" + howTo + ", " : "") +
                (deliveryOrgId != null ? "deliveryOrgId=" + deliveryOrgId + ", " : "") +
                (parentServiceId != null ? "parentServiceId=" + parentServiceId + ", " : "") +
                (serviceFranchiseId != null ? "serviceFranchiseId=" + serviceFranchiseId + ", " : "") +
                (applicationServiceOverrideId != null ? "applicationServiceOverrideId=" + applicationServiceOverrideId + ", " : "") +
                (integrationMappingId != null ? "integrationMappingId=" + integrationMappingId + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
                (serviceDeliveryId != null ? "serviceDeliveryId=" + serviceDeliveryId + ", " : "") +
                (serviceDeliveryFormId != null ? "serviceDeliveryFormId=" + serviceDeliveryFormId + ", " : "") +
                (serviceDescriptionId != null ? "serviceDescriptionId=" + serviceDescriptionId + ", " : "") +
                (serviceEventId != null ? "serviceEventId=" + serviceEventId + ", " : "") +
                (serviceEvidenceId != null ? "serviceEvidenceId=" + serviceEvidenceId + ", " : "") +
                (serviceGroupId != null ? "serviceGroupId=" + serviceGroupId + ", " : "") +
                (serviceNameId != null ? "serviceNameId=" + serviceNameId + ", " : "") +
                (fromServiceId != null ? "fromServiceId=" + fromServiceId + ", " : "") +
                (toServiceId != null ? "toServiceId=" + toServiceId + ", " : "") +
                (serviceSupportRoleId != null ? "serviceSupportRoleId=" + serviceSupportRoleId + ", " : "") +
                (serviceTagItemsId != null ? "serviceTagItemsId=" + serviceTagItemsId + ", " : "") +
            "}";
    }

}
