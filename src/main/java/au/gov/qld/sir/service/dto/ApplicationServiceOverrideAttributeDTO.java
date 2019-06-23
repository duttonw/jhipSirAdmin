package au.gov.qld.sir.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute} entity.
 */
@ApiModel(description = "application_service_override_attribute")
public class ApplicationServiceOverrideAttributeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String value;

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

    private String applicationServiceOverrideName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getApplicationServiceOverrideName() {
        return applicationServiceOverrideName;
    }

    public void setApplicationServiceOverrideName(String applicationServiceOverrideName) {
        this.applicationServiceOverrideName = applicationServiceOverrideName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = (ApplicationServiceOverrideAttributeDTO) o;
        if (applicationServiceOverrideAttributeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationServiceOverrideAttributeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverrideAttributeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            ", applicationServiceOverride=" + getApplicationServiceOverrideId() +
            ", applicationServiceOverride='" + getApplicationServiceOverrideName() + "'" +
            "}";
    }
}
