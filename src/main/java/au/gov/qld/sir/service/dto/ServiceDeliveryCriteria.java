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
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceDelivery} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceDeliveryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-deliveries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceDeliveryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter serviceDeliveryChannelType;

    private StringFilter status;

    private LongFilter serviceRecordId;

    private LongFilter deliveryChannelId;

    public ServiceDeliveryCriteria(){
    }

    public ServiceDeliveryCriteria(ServiceDeliveryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.serviceDeliveryChannelType = other.serviceDeliveryChannelType == null ? null : other.serviceDeliveryChannelType.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.serviceRecordId = other.serviceRecordId == null ? null : other.serviceRecordId.copy();
        this.deliveryChannelId = other.deliveryChannelId == null ? null : other.deliveryChannelId.copy();
    }

    @Override
    public ServiceDeliveryCriteria copy() {
        return new ServiceDeliveryCriteria(this);
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

    public StringFilter getServiceDeliveryChannelType() {
        return serviceDeliveryChannelType;
    }

    public void setServiceDeliveryChannelType(StringFilter serviceDeliveryChannelType) {
        this.serviceDeliveryChannelType = serviceDeliveryChannelType;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(LongFilter serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public LongFilter getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public void setDeliveryChannelId(LongFilter deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceDeliveryCriteria that = (ServiceDeliveryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(serviceDeliveryChannelType, that.serviceDeliveryChannelType) &&
            Objects.equals(status, that.status) &&
            Objects.equals(serviceRecordId, that.serviceRecordId) &&
            Objects.equals(deliveryChannelId, that.deliveryChannelId);
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
        serviceDeliveryChannelType,
        status,
        serviceRecordId,
        deliveryChannelId
        );
    }

    @Override
    public String toString() {
        return "ServiceDeliveryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (serviceDeliveryChannelType != null ? "serviceDeliveryChannelType=" + serviceDeliveryChannelType + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (serviceRecordId != null ? "serviceRecordId=" + serviceRecordId + ", " : "") +
                (deliveryChannelId != null ? "deliveryChannelId=" + deliveryChannelId + ", " : "") +
            "}";
    }

}
