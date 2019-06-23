package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DeliveryChannel.
 */
@Entity
@Table(name = "delivery_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryChannel implements Serializable {

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
    @Column(name = "additional_details", length = 255)
    private String additionalDetails;

    @NotNull
    @Size(max = 255)
    @Column(name = "delivery_channel_type", length = 255, nullable = false)
    private String deliveryChannelType;

    @Column(name = "delivery_channel_id")
    private Integer deliveryChannelId;

    @Size(max = 255)
    @Column(name = "virtual_delivery_details", length = 255)
    private String virtualDeliveryDetails;

    @ManyToOne
    @JsonIgnoreProperties("deliveryChannels")
    private AvailabilityHours deliveryHours;

    @ManyToOne
    @JsonIgnoreProperties("deliveryChannels")
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties("deliveryChannels")
    private ServiceDelivery serviceDelivery;

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

    public DeliveryChannel createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public DeliveryChannel createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DeliveryChannel modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public DeliveryChannel modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public DeliveryChannel version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public DeliveryChannel additionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
        return this;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getDeliveryChannelType() {
        return deliveryChannelType;
    }

    public DeliveryChannel deliveryChannelType(String deliveryChannelType) {
        this.deliveryChannelType = deliveryChannelType;
        return this;
    }

    public void setDeliveryChannelType(String deliveryChannelType) {
        this.deliveryChannelType = deliveryChannelType;
    }

    public Integer getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public DeliveryChannel deliveryChannelId(Integer deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
        return this;
    }

    public void setDeliveryChannelId(Integer deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    public String getVirtualDeliveryDetails() {
        return virtualDeliveryDetails;
    }

    public DeliveryChannel virtualDeliveryDetails(String virtualDeliveryDetails) {
        this.virtualDeliveryDetails = virtualDeliveryDetails;
        return this;
    }

    public void setVirtualDeliveryDetails(String virtualDeliveryDetails) {
        this.virtualDeliveryDetails = virtualDeliveryDetails;
    }

    public AvailabilityHours getDeliveryHours() {
        return deliveryHours;
    }

    public DeliveryChannel deliveryHours(AvailabilityHours availabilityHours) {
        this.deliveryHours = availabilityHours;
        return this;
    }

    public void setDeliveryHours(AvailabilityHours availabilityHours) {
        this.deliveryHours = availabilityHours;
    }

    public Location getLocation() {
        return location;
    }

    public DeliveryChannel location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ServiceDelivery getServiceDelivery() {
        return serviceDelivery;
    }

    public DeliveryChannel serviceDelivery(ServiceDelivery serviceDelivery) {
        this.serviceDelivery = serviceDelivery;
        return this;
    }

    public void setServiceDelivery(ServiceDelivery serviceDelivery) {
        this.serviceDelivery = serviceDelivery;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryChannel)) {
            return false;
        }
        return id != null && id.equals(((DeliveryChannel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeliveryChannel{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", additionalDetails='" + getAdditionalDetails() + "'" +
            ", deliveryChannelType='" + getDeliveryChannelType() + "'" +
            ", deliveryChannelId=" + getDeliveryChannelId() +
            ", virtualDeliveryDetails='" + getVirtualDeliveryDetails() + "'" +
            "}";
    }
}
