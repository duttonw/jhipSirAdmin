package au.gov.qld.sir.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A AvailabilityHours.
 */
@Entity
@Table(name = "availability_hours")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AvailabilityHours implements Serializable {

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

    @Size(max = 1)
    @Column(name = "available", length = 1)
    private String available;

    @Column(name = "availability_hours_id")
    private Integer availabilityHoursId;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @OneToMany(mappedBy = "deliveryHours")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryChannel> deliveryChannels = new HashSet<>();

    @OneToMany(mappedBy = "locationHours")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "availabilityHours")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OpeningHoursSpecification> openingHoursSpecifications = new HashSet<>();

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

    public AvailabilityHours createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public AvailabilityHours createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public AvailabilityHours modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public AvailabilityHours modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public AvailabilityHours version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAvailable() {
        return available;
    }

    public AvailabilityHours available(String available) {
        this.available = available;
        return this;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Integer getAvailabilityHoursId() {
        return availabilityHoursId;
    }

    public AvailabilityHours availabilityHoursId(Integer availabilityHoursId) {
        this.availabilityHoursId = availabilityHoursId;
        return this;
    }

    public void setAvailabilityHoursId(Integer availabilityHoursId) {
        this.availabilityHoursId = availabilityHoursId;
    }

    public String getComments() {
        return comments;
    }

    public AvailabilityHours comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public AvailabilityHours validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public AvailabilityHours validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Set<DeliveryChannel> getDeliveryChannels() {
        return deliveryChannels;
    }

    public AvailabilityHours deliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
        return this;
    }

    public AvailabilityHours addDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.add(deliveryChannel);
        deliveryChannel.setDeliveryHours(this);
        return this;
    }

    public AvailabilityHours removeDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.remove(deliveryChannel);
        deliveryChannel.setDeliveryHours(null);
        return this;
    }

    public void setDeliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public AvailabilityHours locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public AvailabilityHours addLocation(Location location) {
        this.locations.add(location);
        location.setLocationHours(this);
        return this;
    }

    public AvailabilityHours removeLocation(Location location) {
        this.locations.remove(location);
        location.setLocationHours(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<OpeningHoursSpecification> getOpeningHoursSpecifications() {
        return openingHoursSpecifications;
    }

    public AvailabilityHours openingHoursSpecifications(Set<OpeningHoursSpecification> openingHoursSpecifications) {
        this.openingHoursSpecifications = openingHoursSpecifications;
        return this;
    }

    public AvailabilityHours addOpeningHoursSpecification(OpeningHoursSpecification openingHoursSpecification) {
        this.openingHoursSpecifications.add(openingHoursSpecification);
        openingHoursSpecification.setAvailabilityHours(this);
        return this;
    }

    public AvailabilityHours removeOpeningHoursSpecification(OpeningHoursSpecification openingHoursSpecification) {
        this.openingHoursSpecifications.remove(openingHoursSpecification);
        openingHoursSpecification.setAvailabilityHours(null);
        return this;
    }

    public void setOpeningHoursSpecifications(Set<OpeningHoursSpecification> openingHoursSpecifications) {
        this.openingHoursSpecifications = openingHoursSpecifications;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvailabilityHours)) {
            return false;
        }
        return id != null && id.equals(((AvailabilityHours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AvailabilityHours{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", available='" + getAvailable() + "'" +
            ", availabilityHoursId=" + getAvailabilityHoursId() +
            ", comments='" + getComments() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
