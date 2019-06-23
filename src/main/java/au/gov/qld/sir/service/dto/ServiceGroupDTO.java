package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceGroup} entity.
 */
public class ServiceGroupDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 1)
    private String migrated;

    @Size(max = 10)
    private String migratedBy;


    private Long serviceGroupCategoryId;

    private Long serviceGroupCategoryTypeId;

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

    public Long getServiceGroupCategoryId() {
        return serviceGroupCategoryId;
    }

    public void setServiceGroupCategoryId(Long categoryId) {
        this.serviceGroupCategoryId = categoryId;
    }

    public Long getServiceGroupCategoryTypeId() {
        return serviceGroupCategoryTypeId;
    }

    public void setServiceGroupCategoryTypeId(Long categoryTypeId) {
        this.serviceGroupCategoryTypeId = categoryTypeId;
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

        ServiceGroupDTO serviceGroupDTO = (ServiceGroupDTO) o;
        if (serviceGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceGroupDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", migrated='" + getMigrated() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", serviceGroupCategory=" + getServiceGroupCategoryId() +
            ", serviceGroupCategoryType=" + getServiceGroupCategoryTypeId() +
            ", serviceRecord=" + getServiceRecordId() +
            "}";
    }
}
