package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.CategoryType} entity.
 */
public class CategoryTypeDTO implements Serializable {

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
    private String categoryType;

    @Size(max = 1)
    private String migrated;

    @Size(max = 10)
    private String migratedBy;


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

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryTypeDTO categoryTypeDTO = (CategoryTypeDTO) o;
        if (categoryTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryTypeDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", categoryType='" + getCategoryType() + "'" +
            ", migrated='" + getMigrated() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            "}";
    }
}
