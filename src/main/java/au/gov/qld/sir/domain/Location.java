package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

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
    @Column(name = "accessibility_facilities", length = 255)
    private String accessibilityFacilities;

    @Size(max = 255)
    @Column(name = "additional_information", length = 255)
    private String additionalInformation;

    @NotNull
    @Size(max = 255)
    @Column(name = "location_name", length = 255, nullable = false)
    private String locationName;

    @ManyToOne
    @JsonIgnoreProperties("locations")
    private Agency agency;

    @ManyToOne
    @JsonIgnoreProperties("locations")
    private AvailabilityHours locationHours;

    @ManyToOne
    @JsonIgnoreProperties("locations")
    private LocationType locationType;

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryChannel> deliveryChannels = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocationAddress> locationAddresses = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocationEmail> locationEmails = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocationPhone> locationPhones = new HashSet<>();

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

    public Location createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public Location createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Location modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public Location modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public Location version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAccessibilityFacilities() {
        return accessibilityFacilities;
    }

    public Location accessibilityFacilities(String accessibilityFacilities) {
        this.accessibilityFacilities = accessibilityFacilities;
        return this;
    }

    public void setAccessibilityFacilities(String accessibilityFacilities) {
        this.accessibilityFacilities = accessibilityFacilities;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Location additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getLocationName() {
        return locationName;
    }

    public Location locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Agency getAgency() {
        return agency;
    }

    public Location agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public AvailabilityHours getLocationHours() {
        return locationHours;
    }

    public Location locationHours(AvailabilityHours availabilityHours) {
        this.locationHours = availabilityHours;
        return this;
    }

    public void setLocationHours(AvailabilityHours availabilityHours) {
        this.locationHours = availabilityHours;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public Location locationType(LocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Set<DeliveryChannel> getDeliveryChannels() {
        return deliveryChannels;
    }

    public Location deliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
        return this;
    }

    public Location addDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.add(deliveryChannel);
        deliveryChannel.setLocation(this);
        return this;
    }

    public Location removeDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.remove(deliveryChannel);
        deliveryChannel.setLocation(null);
        return this;
    }

    public void setDeliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
    }

    public Set<LocationAddress> getLocationAddresses() {
        return locationAddresses;
    }

    public Location locationAddresses(Set<LocationAddress> locationAddresses) {
        this.locationAddresses = locationAddresses;
        return this;
    }

    public Location addLocationAddress(LocationAddress locationAddress) {
        this.locationAddresses.add(locationAddress);
        locationAddress.setLocation(this);
        return this;
    }

    public Location removeLocationAddress(LocationAddress locationAddress) {
        this.locationAddresses.remove(locationAddress);
        locationAddress.setLocation(null);
        return this;
    }

    public void setLocationAddresses(Set<LocationAddress> locationAddresses) {
        this.locationAddresses = locationAddresses;
    }

    public Set<LocationEmail> getLocationEmails() {
        return locationEmails;
    }

    public Location locationEmails(Set<LocationEmail> locationEmails) {
        this.locationEmails = locationEmails;
        return this;
    }

    public Location addLocationEmail(LocationEmail locationEmail) {
        this.locationEmails.add(locationEmail);
        locationEmail.setLocation(this);
        return this;
    }

    public Location removeLocationEmail(LocationEmail locationEmail) {
        this.locationEmails.remove(locationEmail);
        locationEmail.setLocation(null);
        return this;
    }

    public void setLocationEmails(Set<LocationEmail> locationEmails) {
        this.locationEmails = locationEmails;
    }

    public Set<LocationPhone> getLocationPhones() {
        return locationPhones;
    }

    public Location locationPhones(Set<LocationPhone> locationPhones) {
        this.locationPhones = locationPhones;
        return this;
    }

    public Location addLocationPhone(LocationPhone locationPhone) {
        this.locationPhones.add(locationPhone);
        locationPhone.setLocation(this);
        return this;
    }

    public Location removeLocationPhone(LocationPhone locationPhone) {
        this.locationPhones.remove(locationPhone);
        locationPhone.setLocation(null);
        return this;
    }

    public void setLocationPhones(Set<LocationPhone> locationPhones) {
        this.locationPhones = locationPhones;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", accessibilityFacilities='" + getAccessibilityFacilities() + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            ", locationName='" + getLocationName() + "'" +
            "}";
    }
}
