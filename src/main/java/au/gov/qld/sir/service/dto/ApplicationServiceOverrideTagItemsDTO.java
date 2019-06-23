package au.gov.qld.sir.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems} entity.
 */
@ApiModel(description = "application_service_override_tags")
public class ApplicationServiceOverrideTagItemsDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    @Size(max = 10)
    private String migratedBy;

    private Long version;


    private Long applicationServiceOverrideId;

    private Long applicationServiceOverrideTagId;

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

    public String getMigratedBy() {
        return migratedBy;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getApplicationServiceOverrideId() {
        return applicationServiceOverrideId;
    }

    public void setApplicationServiceOverrideId(Long applicationServiceOverrideId) {
        this.applicationServiceOverrideId = applicationServiceOverrideId;
    }

    public Long getApplicationServiceOverrideTagId() {
        return applicationServiceOverrideTagId;
    }

    public void setApplicationServiceOverrideTagId(Long applicationServiceOverrideTagId) {
        this.applicationServiceOverrideTagId = applicationServiceOverrideTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO = (ApplicationServiceOverrideTagItemsDTO) o;
        if (applicationServiceOverrideTagItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationServiceOverrideTagItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverrideTagItemsDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            ", applicationServiceOverride=" + getApplicationServiceOverrideId() +
            ", applicationServiceOverrideTag=" + getApplicationServiceOverrideTagId() +
            "}";
    }
}
