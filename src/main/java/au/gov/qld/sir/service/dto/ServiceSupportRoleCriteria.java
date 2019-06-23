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
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceSupportRole} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceSupportRoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-support-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceSupportRoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter contactEmail;

    private StringFilter contactName;

    private StringFilter contactPhoneNumber;

    private LongFilter serviceRecordId;

    private LongFilter serviceRoleTypeId;

    private LongFilter serviceSupportContextTypeId;

    public ServiceSupportRoleCriteria(){
    }

    public ServiceSupportRoleCriteria(ServiceSupportRoleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.contactPhoneNumber = other.contactPhoneNumber == null ? null : other.contactPhoneNumber.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
        this.serviceRoleTypeId = other.serviceRoleTypeId == null ? null : other.serviceRoleTypeId.copy();
        this.serviceSupportContextTypeId = other.serviceSupportContextTypeId == null ? null : other.serviceSupportContextTypeId.copy();
    }

    @Override
    public ServiceSupportRoleCriteria copy() {
        return new ServiceSupportRoleCriteria(this);
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

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(StringFilter contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public LongFilter getServiceRoleTypeId() {
        return serviceRoleTypeId;
    }

    public void setServiceRoleTypeId(LongFilter serviceRoleTypeId) {
        this.serviceRoleTypeId = serviceRoleTypeId;
    }

    public LongFilter getServiceSupportContextTypeId() {
        return serviceSupportContextTypeId;
    }

    public void setServiceSupportContextTypeId(LongFilter serviceSupportContextTypeId) {
        this.serviceSupportContextTypeId = serviceSupportContextTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceSupportRoleCriteria that = (ServiceSupportRoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(contactPhoneNumber, that.contactPhoneNumber) &&
            Objects.equals(serviceRecordId, that.serviceRecordId) &&
            Objects.equals(serviceRoleTypeId, that.serviceRoleTypeId) &&
            Objects.equals(serviceSupportContextTypeId, that.serviceSupportContextTypeId);
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
        contactEmail,
        contactName,
        contactPhoneNumber,
        serviceRecordId,
        serviceRoleTypeId,
        serviceSupportContextTypeId
        );
    }

    @Override
    public String toString() {
        return "ServiceSupportRoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
                (contactName != null ? "contactName=" + contactName + ", " : "") +
                (contactPhoneNumber != null ? "contactPhoneNumber=" + contactPhoneNumber + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
                (serviceRoleTypeId != null ? "serviceRoleTypeId=" + serviceRoleTypeId + ", " : "") +
                (serviceSupportContextTypeId != null ? "serviceSupportContextTypeId=" + serviceSupportContextTypeId + ", " : "") +
            "}";
    }

}
