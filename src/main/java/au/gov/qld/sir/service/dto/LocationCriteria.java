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
 * Criteria class for the {@link au.gov.qld.sir.domain.Location} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.LocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter accessibilityFacilities;

    private StringFilter additionalInformation;

    private StringFilter locationName;

    private LongFilter agencyId;

    private LongFilter locationHoursId;

    private LongFilter locationTypeId;

    private LongFilter deliveryChannelId;

    private LongFilter locationAddressId;

    private LongFilter locationEmailId;

    private LongFilter locationPhoneId;

    public LocationCriteria(){
    }

    public LocationCriteria(LocationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.accessibilityFacilities = other.accessibilityFacilities == null ? null : other.accessibilityFacilities.copy();
        this.additionalInformation = other.additionalInformation == null ? null : other.additionalInformation.copy();
        this.locationName = other.locationName == null ? null : other.locationName.copy();
        this.agencyId = other.agencyId == null ? null : other.agencyId.copy();
        this.locationHoursId = other.locationHoursId == null ? null : other.locationHoursId.copy();
        this.locationTypeId = other.locationTypeId == null ? null : other.locationTypeId.copy();
        this.deliveryChannelId = other.deliveryChannelId == null ? null : other.deliveryChannelId.copy();
        this.locationAddressId = other.locationAddressId == null ? null : other.locationAddressId.copy();
        this.locationEmailId = other.locationEmailId == null ? null : other.locationEmailId.copy();
        this.locationPhoneId = other.locationPhoneId == null ? null : other.locationPhoneId.copy();
    }

    @Override
    public LocationCriteria copy() {
        return new LocationCriteria(this);
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

    public StringFilter getAccessibilityFacilities() {
        return accessibilityFacilities;
    }

    public void setAccessibilityFacilities(StringFilter accessibilityFacilities) {
        this.accessibilityFacilities = accessibilityFacilities;
    }

    public StringFilter getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(StringFilter additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public StringFilter getLocationName() {
        return locationName;
    }

    public void setLocationName(StringFilter locationName) {
        this.locationName = locationName;
    }

    public LongFilter getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(LongFilter agencyId) {
        this.agencyId = agencyId;
    }

    public LongFilter getLocationHoursId() {
        return locationHoursId;
    }

    public void setLocationHoursId(LongFilter locationHoursId) {
        this.locationHoursId = locationHoursId;
    }

    public LongFilter getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(LongFilter locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public LongFilter getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public void setDeliveryChannelId(LongFilter deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    public LongFilter getLocationAddressId() {
        return locationAddressId;
    }

    public void setLocationAddressId(LongFilter locationAddressId) {
        this.locationAddressId = locationAddressId;
    }

    public LongFilter getLocationEmailId() {
        return locationEmailId;
    }

    public void setLocationEmailId(LongFilter locationEmailId) {
        this.locationEmailId = locationEmailId;
    }

    public LongFilter getLocationPhoneId() {
        return locationPhoneId;
    }

    public void setLocationPhoneId(LongFilter locationPhoneId) {
        this.locationPhoneId = locationPhoneId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCriteria that = (LocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(accessibilityFacilities, that.accessibilityFacilities) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(locationName, that.locationName) &&
            Objects.equals(agencyId, that.agencyId) &&
            Objects.equals(locationHoursId, that.locationHoursId) &&
            Objects.equals(locationTypeId, that.locationTypeId) &&
            Objects.equals(deliveryChannelId, that.deliveryChannelId) &&
            Objects.equals(locationAddressId, that.locationAddressId) &&
            Objects.equals(locationEmailId, that.locationEmailId) &&
            Objects.equals(locationPhoneId, that.locationPhoneId);
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
        accessibilityFacilities,
        additionalInformation,
        locationName,
        agencyId,
        locationHoursId,
        locationTypeId,
        deliveryChannelId,
        locationAddressId,
        locationEmailId,
        locationPhoneId
        );
    }

    @Override
    public String toString() {
        return "LocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (accessibilityFacilities != null ? "accessibilityFacilities=" + accessibilityFacilities + ", " : "") +
                (additionalInformation != null ? "additionalInformation=" + additionalInformation + ", " : "") +
                (locationName != null ? "locationName=" + locationName + ", " : "") +
                (agencyId != null ? "agencyId=" + agencyId + ", " : "") +
                (locationHoursId != null ? "locationHoursId=" + locationHoursId + ", " : "") +
                (locationTypeId != null ? "locationTypeId=" + locationTypeId + ", " : "") +
                (deliveryChannelId != null ? "deliveryChannelId=" + deliveryChannelId + ", " : "") +
                (locationAddressId != null ? "locationAddressId=" + locationAddressId + ", " : "") +
                (locationEmailId != null ? "locationEmailId=" + locationEmailId + ", " : "") +
                (locationPhoneId != null ? "locationPhoneId=" + locationPhoneId + ", " : "") +
            "}";
    }

}
