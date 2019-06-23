package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A LocationAddress.
 */
@Entity
@Table(name = "location_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocationAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Size(max = 255)
    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "modified_date_time")
    private Instant modifiedDateTime;

    @Column(name = "version")
    private Long version;

    @Size(max = 255)
    @Column(name = "additional_information", length = 255)
    private String additionalInformation;

    @Size(max = 255)
    @Column(name = "address_line_1", length = 255)
    private String addressLine1;

    @Size(max = 255)
    @Column(name = "address_line_2", length = 255)
    private String addressLine2;

    @NotNull
    @Size(max = 255)
    @Column(name = "address_type", length = 255, nullable = false)
    private String addressType;

    @Size(max = 2)
    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Size(max = 255)
    @Column(name = "locality_name", length = 255)
    private String localityName;

    @Column(name = "location_point")
    private String locationPoint;

    @Size(max = 4)
    @Column(name = "postcode", length = 4)
    private String postcode;

    @Size(max = 3)
    @Column(name = "state_code", length = 3)
    private String stateCode;

    @ManyToOne
    @JsonIgnoreProperties("locationAddresses")
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocationAddress createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public LocationAddress createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public LocationAddress modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public LocationAddress modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public LocationAddress version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public LocationAddress additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public LocationAddress addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public LocationAddress addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressType() {
        return addressType;
    }

    public LocationAddress addressType(String addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public LocationAddress countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocalityName() {
        return localityName;
    }

    public LocationAddress localityName(String localityName) {
        this.localityName = localityName;
        return this;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getLocationPoint() {
        return locationPoint;
    }

    public LocationAddress locationPoint(String locationPoint) {
        this.locationPoint = locationPoint;
        return this;
    }

    public void setLocationPoint(String locationPoint) {
        this.locationPoint = locationPoint;
    }

    public String getPostcode() {
        return postcode;
    }

    public LocationAddress postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public LocationAddress stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Location getLocation() {
        return location;
    }

    public LocationAddress location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationAddress)) {
            return false;
        }
        return id != null && id.equals(((LocationAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LocationAddress{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", addressType='" + getAddressType() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", localityName='" + getLocalityName() + "'" +
            ", locationPoint='" + getLocationPoint() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            "}";
    }
}
