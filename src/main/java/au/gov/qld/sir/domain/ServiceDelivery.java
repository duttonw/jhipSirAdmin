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
 * A ServiceDelivery.
 */
@Entity
@Table(name = "service_delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceDelivery implements Serializable {

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

    @NotNull
    @Size(max = 255)
    @Column(name = "service_delivery_channel_type", length = 255, nullable = false)
    private String serviceDeliveryChannelType;

    @Size(max = 255)
    @Column(name = "status", length = 255)
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("serviceDeliveries")
    @JoinColumn(name = "service_id")
    private ServiceRecord serviceRecord;

    @OneToMany(mappedBy = "serviceDelivery")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryChannel> deliveryChannels = new HashSet<>();

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

    public ServiceDelivery createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceDelivery createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceDelivery modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceDelivery modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceDelivery version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getServiceDeliveryChannelType() {
        return serviceDeliveryChannelType;
    }

    public ServiceDelivery serviceDeliveryChannelType(String serviceDeliveryChannelType) {
        this.serviceDeliveryChannelType = serviceDeliveryChannelType;
        return this;
    }

    public void setServiceDeliveryChannelType(String serviceDeliveryChannelType) {
        this.serviceDeliveryChannelType = serviceDeliveryChannelType;
    }

    public String getStatus() {
        return status;
    }

    public ServiceDelivery status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceRecord getServiceRecord() {
        return serviceRecord;
    }

    public ServiceDelivery serviceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
        return this;
    }

    public void setServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public Set<DeliveryChannel> getDeliveryChannels() {
        return deliveryChannels;
    }

    public ServiceDelivery deliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
        return this;
    }

    public ServiceDelivery addDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.add(deliveryChannel);
        deliveryChannel.setServiceDelivery(this);
        return this;
    }

    public ServiceDelivery removeDeliveryChannel(DeliveryChannel deliveryChannel) {
        this.deliveryChannels.remove(deliveryChannel);
        deliveryChannel.setServiceDelivery(null);
        return this;
    }

    public void setDeliveryChannels(Set<DeliveryChannel> deliveryChannels) {
        this.deliveryChannels = deliveryChannels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDelivery)) {
            return false;
        }
        return id != null && id.equals(((ServiceDelivery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceDelivery{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", serviceDeliveryChannelType='" + getServiceDeliveryChannelType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
