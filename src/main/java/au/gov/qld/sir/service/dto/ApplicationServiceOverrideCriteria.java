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

/**
 * Criteria class for the {@link au.gov.qld.sir.domain.ApplicationServiceOverride} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ApplicationServiceOverrideResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /application-service-overrides?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationServiceOverrideCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter eligibility;

    private StringFilter keywords;

    private StringFilter longDescription;

    private StringFilter name;

    private StringFilter preRequisites;

    private StringFilter fees;

    private StringFilter active;

    private StringFilter referenceUrl;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private StringFilter migratedBy;

    private LongFilter version;

    private StringFilter howTo;

    private LongFilter serviceRecordId;

    private LongFilter applicationId;

    private LongFilter applicationServiceOverrideAttributeId;

    private LongFilter applicationServiceOverrideTagItemsId;

    public ApplicationServiceOverrideCriteria(){
    }

    public ApplicationServiceOverrideCriteria(ApplicationServiceOverrideCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.eligibility = other.eligibility == null ? null : other.eligibility.copy();
        this.keywords = other.keywords == null ? null : other.keywords.copy();
        this.longDescription = other.longDescription == null ? null : other.longDescription.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.preRequisites = other.preRequisites == null ? null : other.preRequisites.copy();
        this.fees = other.fees == null ? null : other.fees.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.referenceUrl = other.referenceUrl == null ? null : other.referenceUrl.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.migratedBy = other.migratedBy == null ? null : other.migratedBy.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.howTo = other.howTo == null ? null : other.howTo.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
        this.applicationServiceOverrideAttributeId = other.applicationServiceOverrideAttributeId == null ? null : other.applicationServiceOverrideAttributeId.copy();
        this.applicationServiceOverrideTagItemsId = other.applicationServiceOverrideTagItemsId == null ? null : other.applicationServiceOverrideTagItemsId.copy();
    }

    @Override
    public ApplicationServiceOverrideCriteria copy() {
        return new ApplicationServiceOverrideCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getEligibility() {
        return eligibility;
    }

    public void setEligibility(StringFilter eligibility) {
        this.eligibility = eligibility;
    }

    public StringFilter getKeywords() {
        return keywords;
    }

    public void setKeywords(StringFilter keywords) {
        this.keywords = keywords;
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

    public StringFilter getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(StringFilter preRequisites) {
        this.preRequisites = preRequisites;
    }

    public StringFilter getFees() {
        return fees;
    }

    public void setFees(StringFilter fees) {
        this.fees = fees;
    }

    public StringFilter getActive() {
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public StringFilter getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(StringFilter referenceUrl) {
        this.referenceUrl = referenceUrl;
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

    public StringFilter getMigratedBy() {
        return migratedBy;
    }

    public void setMigratedBy(StringFilter migratedBy) {
        this.migratedBy = migratedBy;
    }

    public LongFilter getVersion() {
        return version;
    }

    public void setVersion(LongFilter version) {
        this.version = version;
    }

    public StringFilter getHowTo() {
        return howTo;
    }

    public void setHowTo(StringFilter howTo) {
        this.howTo = howTo;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
    }

    public LongFilter getApplicationServiceOverrideAttributeId() {
        return applicationServiceOverrideAttributeId;
    }

    public void setApplicationServiceOverrideAttributeId(LongFilter applicationServiceOverrideAttributeId) {
        this.applicationServiceOverrideAttributeId = applicationServiceOverrideAttributeId;
    }

    public LongFilter getApplicationServiceOverrideTagItemsId() {
        return applicationServiceOverrideTagItemsId;
    }

    public void setApplicationServiceOverrideTagItemsId(LongFilter applicationServiceOverrideTagItemsId) {
        this.applicationServiceOverrideTagItemsId = applicationServiceOverrideTagItemsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicationServiceOverrideCriteria that = (ApplicationServiceOverrideCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(eligibility, that.eligibility) &&
            Objects.equals(keywords, that.keywords) &&
            Objects.equals(longDescription, that.longDescription) &&
            Objects.equals(name, that.name) &&
            Objects.equals(preRequisites, that.preRequisites) &&
            Objects.equals(fees, that.fees) &&
            Objects.equals(active, that.active) &&
            Objects.equals(referenceUrl, that.referenceUrl) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(migratedBy, that.migratedBy) &&
            Objects.equals(version, that.version) &&
            Objects.equals(howTo, that.howTo) &&
            Objects.equals(serviceRecordId, that.serviceRecordId) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(applicationServiceOverrideAttributeId, that.applicationServiceOverrideAttributeId) &&
            Objects.equals(applicationServiceOverrideTagItemsId, that.applicationServiceOverrideTagItemsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        eligibility,
        keywords,
        longDescription,
        name,
        preRequisites,
        fees,
        active,
        referenceUrl,
        createdBy,
        createdDateTime,
        modifiedBy,
        modifiedDateTime,
        migratedBy,
        version,
        howTo,
        serviceRecordId,
        applicationId,
        applicationServiceOverrideAttributeId,
        applicationServiceOverrideTagItemsId
        );
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverrideCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (eligibility != null ? "eligibility=" + eligibility + ", " : "") +
                (keywords != null ? "keywords=" + keywords + ", " : "") +
                (longDescription != null ? "longDescription=" + longDescription + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (preRequisites != null ? "preRequisites=" + preRequisites + ", " : "") +
                (fees != null ? "fees=" + fees + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (referenceUrl != null ? "referenceUrl=" + referenceUrl + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (migratedBy != null ? "migratedBy=" + migratedBy + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (howTo != null ? "howTo=" + howTo + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (applicationServiceOverrideAttributeId != null ? "applicationServiceOverrideAttributeId=" + applicationServiceOverrideAttributeId + ", " : "") +
                (applicationServiceOverrideTagItemsId != null ? "applicationServiceOverrideTagItemsId=" + applicationServiceOverrideTagItemsId + ", " : "") +
            "}";
    }

}
