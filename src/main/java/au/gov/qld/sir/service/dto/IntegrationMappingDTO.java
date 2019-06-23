package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.IntegrationMapping} entity.
 */
public class IntegrationMappingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String agencyServiceId;

    @NotNull
    @Size(max = 255)
    private String serviceName;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;


    private Long agencyId;

    private Long serviceRecordId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyServiceId() {
        return agencyServiceId;
    }

    public void setAgencyServiceId(String agencyServiceId) {
        this.agencyServiceId = agencyServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
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

        IntegrationMappingDTO integrationMappingDTO = (IntegrationMappingDTO) o;
        if (integrationMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), integrationMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntegrationMappingDTO{" +
            "id=" + getId() +
            ", agencyServiceId='" + getAgencyServiceId() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", agency=" + getAgencyId() +
            ", serviceRecord=" + getServiceRecordId() +
            "}";
    }
}
