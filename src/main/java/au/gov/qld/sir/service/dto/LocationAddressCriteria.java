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
 * Criteria class for the {@link au.gov.qld.sir.domain.LocationAddress} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.LocationAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter additionalInformation;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter addressType;

    private StringFilter countryCode;

    private StringFilter localityName;

    private StringFilter locationPoint;

    private StringFilter postcode;

    private StringFilter stateCode;

    private LongFilter locationId;

    public LocationAddressCriteria(){
    }

    public LocationAddressCriteria(LocationAddressCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.additionalInformation = other.additionalInformation == null ? null : other.additionalInformation.copy();
        this.addressLine1 = other.addressLine1 == null ? null : other.addressLine1.copy();
        this.addressLine2 = other.addressLine2 == null ? null : other.addressLine2.copy();
        this.addressType = other.addressType == null ? null : other.addressType.copy();
        this.countryCode = other.countryCode == null ? null : other.countryCode.copy();
        this.localityName = other.localityName == null ? null : other.localityName.copy();
        this.locationPoint = other.locationPoint == null ? null : other.locationPoint.copy();
        this.postcode = other.postcode == null ? null : other.postcode.copy();
        this.stateCode = other.stateCode == null ? null : other.stateCode.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
    }

    @Override
    public LocationAddressCriteria copy() {
        return new LocationAddressCriteria(this);
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

    public StringFilter getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(StringFilter additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getAddressType() {
        return addressType;
    }

    public void setAddressType(StringFilter addressType) {
        this.addressType = addressType;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getLocalityName() {
        return localityName;
    }

    public void setLocalityName(StringFilter localityName) {
        this.localityName = localityName;
    }

    public StringFilter getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(StringFilter locationPoint) {
        this.locationPoint = locationPoint;
    }

    public StringFilter getPostcode() {
        return postcode;
    }

    public void setPostcode(StringFilter postcode) {
        this.postcode = postcode;
    }

    public StringFilter getStateCode() {
        return stateCode;
    }

    public void setStateCode(StringFilter stateCode) {
        this.stateCode = stateCode;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationAddressCriteria that = (LocationAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(addressType, that.addressType) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(localityName, that.localityName) &&
            Objects.equals(locationPoint, that.locationPoint) &&
            Objects.equals(postcode, that.postcode) &&
            Objects.equals(stateCode, that.stateCode) &&
            Objects.equals(locationId, that.locationId);
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
        additionalInformation,
        addressLine1,
        addressLine2,
        addressType,
        countryCode,
        localityName,
        locationPoint,
        postcode,
        stateCode,
        locationId
        );
    }

    @Override
    public String toString() {
        return "LocationAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (additionalInformation != null ? "additionalInformation=" + additionalInformation + ", " : "") +
                (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
                (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
                (addressType != null ? "addressType=" + addressType + ", " : "") +
                (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
                (localityName != null ? "localityName=" + localityName + ", " : "") +
                (locationPoint != null ? "locationPoint=" + locationPoint + ", " : "") +
                (postcode != null ? "postcode=" + postcode + ", " : "") +
                (stateCode != null ? "stateCode=" + stateCode + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
            "}";
    }

}
