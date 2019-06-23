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
 * Criteria class for the {@link au.gov.qld.sir.domain.AvailabilityHours} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.AvailabilityHoursResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /availability-hours?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AvailabilityHoursCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter available;

    private IntegerFilter availabilityHoursId;

    private StringFilter comments;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter deliveryChannelId;

    private LongFilter locationId;

    private LongFilter openingHoursSpecificationId;

    public AvailabilityHoursCriteria(){
    }

    public AvailabilityHoursCriteria(AvailabilityHoursCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.available = other.available == null ? null : other.available.copy();
        this.availabilityHoursId = other.availabilityHoursId == null ? null : other.availabilityHoursId.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.deliveryChannelId = other.deliveryChannelId == null ? null : other.deliveryChannelId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.openingHoursSpecificationId = other.openingHoursSpecificationId == null ? null : other.openingHoursSpecificationId.copy();
    }

    @Override
    public AvailabilityHoursCriteria copy() {
        return new AvailabilityHoursCriteria(this);
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

    public StringFilter getAvailable() {
        return available;
    }

    public void setAvailable(StringFilter available) {
        this.available = available;
    }

    public IntegerFilter getAvailabilityHoursId() {
        return availabilityHoursId;
    }

    public void setAvailabilityHoursId(IntegerFilter availabilityHoursId) {
        this.availabilityHoursId = availabilityHoursId;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public void setDeliveryChannelId(LongFilter deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getOpeningHoursSpecificationId() {
        return openingHoursSpecificationId;
    }

    public void setOpeningHoursSpecificationId(LongFilter openingHoursSpecificationId) {
        this.openingHoursSpecificationId = openingHoursSpecificationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AvailabilityHoursCriteria that = (AvailabilityHoursCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(available, that.available) &&
            Objects.equals(availabilityHoursId, that.availabilityHoursId) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(deliveryChannelId, that.deliveryChannelId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(openingHoursSpecificationId, that.openingHoursSpecificationId);
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
        available,
        availabilityHoursId,
        comments,
        validFrom,
        validTo,
        deliveryChannelId,
        locationId,
        openingHoursSpecificationId
        );
    }

    @Override
    public String toString() {
        return "AvailabilityHoursCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (available != null ? "available=" + available + ", " : "") +
                (availabilityHoursId != null ? "availabilityHoursId=" + availabilityHoursId + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (deliveryChannelId != null ? "deliveryChannelId=" + deliveryChannelId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (openingHoursSpecificationId != null ? "openingHoursSpecificationId=" + openingHoursSpecificationId + ", " : "") +
            "}";
    }

}
