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
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceDeliveryOrganisation} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceDeliveryOrganisationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-delivery-organisations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceDeliveryOrganisationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter businessUnitName;

    private LongFilter agencyId;

    private LongFilter serviceRecordId;

    public ServiceDeliveryOrganisationCriteria(){
    }

    public ServiceDeliveryOrganisationCriteria(ServiceDeliveryOrganisationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.businessUnitName = other.businessUnitName == null ? null : other.businessUnitName.copy();
        this.agencyId = other.agencyId == null ? null : other.agencyId.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
    }

    @Override
    public ServiceDeliveryOrganisationCriteria copy() {
        return new ServiceDeliveryOrganisationCriteria(this);
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

    public StringFilter getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(StringFilter businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public LongFilter getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(LongFilter agencyId) {
        this.agencyId = agencyId;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceDeliveryOrganisationCriteria that = (ServiceDeliveryOrganisationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(businessUnitName, that.businessUnitName) &&
            Objects.equals(agencyId, that.agencyId) &&
            Objects.equals(serviceRecordId, that.serviceRecordId);
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
        businessUnitName,
        agencyId,
        serviceRecordId
        );
    }

    @Override
    public String toString() {
        return "ServiceDeliveryOrganisationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (businessUnitName != null ? "businessUnitName=" + businessUnitName + ", " : "") +
                (agencyId != null ? "agencyId=" + agencyId + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
            "}";
    }

}
