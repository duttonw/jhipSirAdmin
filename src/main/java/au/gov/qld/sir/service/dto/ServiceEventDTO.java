package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceEvent} entity.
 */
public class ServiceEventDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    private Integer serviceEventSequence;


    private Long serviceRecordId;

    private String serviceRecordServiceName;

    private Long serviceEventTypeId;

    private String serviceEventTypeServiceEvent;

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

    public Integer getServiceEventSequence() {
        return serviceEventSequence;
    }

    public void setServiceEventSequence(Integer serviceEventSequence) {
        this.serviceEventSequence = serviceEventSequence;
    }

    public Long getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(Long serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public String getServiceRecordServiceName() {
        return serviceRecordServiceName;
    }

    public void setServiceRecordServiceName(String serviceRecordServiceName) {
        this.serviceRecordServiceName = serviceRecordServiceName;
    }

    public Long getServiceEventTypeId() {
        return serviceEventTypeId;
    }

    public void setServiceEventTypeId(Long serviceEventTypeId) {
        this.serviceEventTypeId = serviceEventTypeId;
    }

    public String getServiceEventTypeServiceEvent() {
        return serviceEventTypeServiceEvent;
    }

    public void setServiceEventTypeServiceEvent(String serviceEventTypeServiceEvent) {
        this.serviceEventTypeServiceEvent = serviceEventTypeServiceEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceEventDTO serviceEventDTO = (ServiceEventDTO) o;
        if (serviceEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceEventDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", serviceEventSequence=" + getServiceEventSequence() +
            ", serviceRecord=" + getServiceRecordId() +
            ", serviceRecord='" + getServiceRecordServiceName() + "'" +
            ", serviceEventType=" + getServiceEventTypeId() +
            ", serviceEventType='" + getServiceEventTypeServiceEvent() + "'" +
            "}";
    }
}
