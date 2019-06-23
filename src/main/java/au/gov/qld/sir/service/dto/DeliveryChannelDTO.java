package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.DeliveryChannel} entity.
 */
public class DeliveryChannelDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 255)
    private String additionalDetails;

    @NotNull
    @Size(max = 255)
    private String deliveryChannelType;

    private Integer deliveryChannelId;

    @Size(max = 255)
    private String virtualDeliveryDetails;


    private Long deliveryHoursId;

    private Long locationId;

    private Long serviceDeliveryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getDeliveryChannelType() {
        return deliveryChannelType;
    }

    public void setDeliveryChannelType(String deliveryChannelType) {
        this.deliveryChannelType = deliveryChannelType;
    }

    public Integer getDeliveryChannelId() {
        return deliveryChannelId;
    }

    public void setDeliveryChannelId(Integer deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    public String getVirtualDeliveryDetails() {
        return virtualDeliveryDetails;
    }

    public void setVirtualDeliveryDetails(String virtualDeliveryDetails) {
        this.virtualDeliveryDetails = virtualDeliveryDetails;
    }

    public Long getDeliveryHoursId() {
        return deliveryHoursId;
    }

    public void setDeliveryHoursId(Long availabilityHoursId) {
        this.deliveryHoursId = availabilityHoursId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getServiceDeliveryId() {
        return serviceDeliveryId;
    }

    public void setServiceDeliveryId(Long serviceDeliveryId) {
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

        DeliveryChannelDTO deliveryChannelDTO = (DeliveryChannelDTO) o;
        if (deliveryChannelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryChannelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryChannelDTO{" +
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
            ", deliveryHours=" + getDeliveryHoursId() +
            ", location=" + getLocationId() +
            ", serviceDelivery=" + getServiceDeliveryId() +
            "}";
    }
}
