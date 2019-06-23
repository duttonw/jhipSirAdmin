package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceName} entity.
 */
public class ServiceNameDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 255)
    private String context;

    @Size(max = 255)
    private String serviceName;

    @Size(max = 1)
    private String migrated;

    @Size(max = 10)
    private String migratedBy;


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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMigrated() {
        return migrated;
    }

    public void setMigrated(String migrated) {
        this.migrated = migrated;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
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

        ServiceNameDTO serviceNameDTO = (ServiceNameDTO) o;
        if (serviceNameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceNameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceNameDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", context='" + getContext() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", migrated='" + getMigrated() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", serviceRecord=" + getServiceRecordId() +
            "}";
    }
}
