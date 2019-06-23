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
 * Criteria class for the {@link au.gov.qld.sir.domain.DeliveryChannel} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.DeliveryChannelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delivery-channels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeliveryChannelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter additionalDetails;

    private StringFilter deliveryChannelType;

    private IntegerFilter deliveryChannelId;

    private StringFilter virtualDeliveryDetails;

    private LongFilter deliveryHoursId;

    private LongFilter locationId;

    private LongFilter serviceDeliveryId;

    public DeliveryChannelCriteria(){
    }

    public DeliveryChannelCriteria(DeliveryChannelCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.additionalDetails = other.additionalDetails == null ? null : other.additionalDetails.copy();
        this.deliveryChannelType = other.deliveryChannelType == null ? null : other.deliveryChannelType.copy();
        this.deliveryChannelId = other.deliveryChannelId == null ? null : other.deliveryChannelId.copy();
        this.virtualDeliveryDetails = other.virtualDeliveryDetails == null ? null : other.virtualDeliveryDetails.copy();
        this.deliveryHoursId = other.deliveryHoursId == null ? null : other.deliveryHoursId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.serviceDeliveryId = other.serviceDeliveryId == null ? null : other.serviceDeliveryId.copy();
    }

    @Override
    public DeliveryChannelCriteria copy() {
        return new DeliveryChannelCriteria(this);
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

    public StringFilter getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(StringFilter additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public StringFilter getDeliveryChannelType() {
        return deliveryChannelType;
    }

    public void setDeliveryChannelType(StringFilter deliveryChannelType) {
        this.deliveryChannelType = deliveryChannelType;
    }

    public IntegerFilter getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public void setDeliveryChannelId(IntegerFilter deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    public StringFilter getVirtualDeliveryDetails() {
        return virtualDeliveryDetails;
    }

    public void setVirtualDeliveryDetails(StringFilter virtualDeliveryDetails) {
        this.virtualDeliveryDetails = virtualDeliveryDetails;
    }

    public LongFilter getDeliveryHoursId() {
        return deliveryHoursId;
    }

    public void setDeliveryHoursId(LongFilter deliveryHoursId) {
        this.deliveryHoursId = deliveryHoursId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getServiceDeliveryId() {
        return serviceDeliveryId;
    }

    public void setServiceDeliveryId(LongFilter serviceDeliveryId) {
        this.serviceDeliveryId = serviceDeliveryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeliveryChannelCriteria that = (DeliveryChannelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(additionalDetails, that.additionalDetails) &&
            Objects.equals(deliveryChannelType, that.deliveryChannelType) &&
            Objects.equals(deliveryChannelId, that.deliveryChannelId) &&
            Objects.equals(virtualDeliveryDetails, that.virtualDeliveryDetails) &&
            Objects.equals(deliveryHoursId, that.deliveryHoursId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(serviceDeliveryId, that.serviceDeliveryId);
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
        additionalDetails,
        deliveryChannelType,
        deliveryChannelId,
        virtualDeliveryDetails,
        deliveryHoursId,
        locationId,
        serviceDeliveryId
        );
    }

    @Override
    public String toString() {
        return "DeliveryChannelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (additionalDetails != null ? "additionalDetails=" + additionalDetails + ", " : "") +
                (deliveryChannelType != null ? "deliveryChannelType=" + deliveryChannelType + ", " : "") +
                (deliveryChannelId != null ? "deliveryChannelId=" + deliveryChannelId + ", " : "") +
                (virtualDeliveryDetails != null ? "virtualDeliveryDetails=" + virtualDeliveryDetails + ", " : "") +
                (deliveryHoursId != null ? "deliveryHoursId=" + deliveryHoursId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (serviceDeliveryId != null ? "serviceDeliveryId=" + serviceDeliveryId + ", " : "") +
            "}";
    }

}
