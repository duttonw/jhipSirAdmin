package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ApplicationServiceOverride} entity.
 */
public class ApplicationServiceOverrideDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String eligibility;

    @Size(max = 255)
    private String keywords;

    @Size(max = 255)
    private String longDescription;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String preRequisites;

    @Size(max = 255)
    private String fees;

    @Size(max = 1)
    private String active;

    @Size(max = 255)
    private String referenceUrl;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    @Size(max = 10)
    private String migratedBy;

    private Long version;

    @Size(max = 255)
    private String howTo;


    private Long serviceRecordId;

    private String serviceRecordServiceName;

    private Long applicationId;

    private String applicationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
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

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationServiceOverrideDTO applicationServiceOverrideDTO = (ApplicationServiceOverrideDTO) o;
        if (applicationServiceOverrideDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationServiceOverrideDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverrideDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", eligibility='" + getEligibility() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", name='" + getName() + "'" +
            ", preRequisites='" + getPreRequisites() + "'" +
            ", fees='" + getFees() + "'" +
            ", active='" + getActive() + "'" +
            ", referenceUrl='" + getReferenceUrl() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            ", howTo='" + getHowTo() + "'" +
            ", serviceRecord=" + getServiceRecordId() +
            ", serviceRecord='" + getServiceRecordServiceName() + "'" +
            ", application=" + getApplicationId() +
            ", application='" + getApplicationName() + "'" +
            "}";
    }
}
