package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceDelivery} entity.
 */
public class ServiceDeliveryDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @NotNull
    @Size(max = 255)
    private String serviceDeliveryChannelType;

    @Size(max = 255)
    private String status;


    private Long serviceRecordId;

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

    public String getServiceDeliveryChannelType() {
        return serviceDeliveryChannelType;
    }

    public void setServiceDeliveryChannelType(String serviceDeliveryChannelType) {
        this.serviceDeliveryChannelType = serviceDeliveryChannelType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(Long serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceDeliveryDTO serviceDeliveryDTO = (ServiceDeliveryDTO) o;
        if (serviceDeliveryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceDeliveryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceDeliveryDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", serviceDeliveryChannelType='" + getServiceDeliveryChannelType() + "'" +
            ", status='" + getStatus() + "'" +
            ", serviceRecord=" + getServiceRecordId() +
            "}";
    }
}
