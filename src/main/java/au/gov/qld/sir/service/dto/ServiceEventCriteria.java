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
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceEvent} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private IntegerFilter serviceEventSequence;

    private LongFilter serviceRecordId;

    private LongFilter serviceEventTypeId;

    public ServiceEventCriteria(){
    }

    public ServiceEventCriteria(ServiceEventCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.serviceEventSequence = other.serviceEventSequence == null ? null : other.serviceEventSequence.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
        this.serviceEventTypeId = other.serviceEventTypeId == null ? null : other.serviceEventTypeId.copy();
    }

    @Override
    public ServiceEventCriteria copy() {
        return new ServiceEventCriteria(this);
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

    public IntegerFilter getServiceEventSequence() {
        return serviceEventSequence;
    }

    public void setServiceEventSequence(IntegerFilter serviceEventSequence) {
        this.serviceEventSequence = serviceEventSequence;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public LongFilter getServiceEventTypeId() {
        return serviceEventTypeId;
    }

    public void setServiceEventTypeId(LongFilter serviceEventTypeId) {
        this.serviceEventTypeId = serviceEventTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceEventCriteria that = (ServiceEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(serviceEventSequence, that.serviceEventSequence) &&
            Objects.equals(serviceRecordId, that.serviceRecordId) &&
            Objects.equals(serviceEventTypeId, that.serviceEventTypeId);
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
        serviceEventSequence,
        serviceRecordId,
        serviceEventTypeId
        );
    }

    @Override
    public String toString() {
        return "ServiceEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (serviceEventSequence != null ? "serviceEventSequence=" + serviceEventSequence + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
                (serviceEventTypeId != null ? "serviceEventTypeId=" + serviceEventTypeId + ", " : "") +
            "}";
    }

}
