package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.AgencySupportRole} entity.
 */
public class AgencySupportRoleDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 255)
    private String contactEmail;


    private Long agencyId;

    private String agencyAgencyCode;

    private Long agencyRoleTypeId;

    private String agencyRoleTypeServiceRole;

    private Long agencySupportContextTypeId;

    private String agencySupportContextTypeContext;

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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyAgencyCode() {
        return agencyAgencyCode;
    }

    public void setAgencyAgencyCode(String agencyAgencyCode) {
        this.agencyAgencyCode = agencyAgencyCode;
    }

    public Long getAgencyRoleTypeId() {
        return agencyRoleTypeId;
    }

    public void setAgencyRoleTypeId(Long serviceRoleTypeId) {
        this.agencyRoleTypeId = serviceRoleTypeId;
    }

    public String getAgencyRoleTypeServiceRole() {
        return agencyRoleTypeServiceRole;
    }

    public void setAgencyRoleTypeServiceRole(String serviceRoleTypeServiceRole) {
        this.agencyRoleTypeServiceRole = serviceRoleTypeServiceRole;
    }

    public Long getAgencySupportContextTypeId() {
        return agencySupportContextTypeId;
    }

    public void setAgencySupportContextTypeId(Long agencySupportRoleContextTypeId) {
        this.agencySupportContextTypeId = agencySupportRoleContextTypeId;
    }

    public String getAgencySupportContextTypeContext() {
        return agencySupportContextTypeContext;
    }

    public void setAgencySupportContextTypeContext(String agencySupportRoleContextTypeContext) {
        this.agencySupportContextTypeContext = agencySupportRoleContextTypeContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgencySupportRoleDTO agencySupportRoleDTO = (AgencySupportRoleDTO) o;
        if (agencySupportRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agencySupportRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgencySupportRoleDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", contactEmail='" + getContactEmail() + "'" +
            ", agency=" + getAgencyId() +
            ", agency='" + getAgencyAgencyCode() + "'" +
            ", agencyRoleType=" + getAgencyRoleTypeId() +
            ", agencyRoleType='" + getAgencyRoleTypeServiceRole() + "'" +
            ", agencySupportContextType=" + getAgencySupportContextTypeId() +
            ", agencySupportContextType='" + getAgencySupportContextTypeContext() + "'" +
            "}";
    }
}
