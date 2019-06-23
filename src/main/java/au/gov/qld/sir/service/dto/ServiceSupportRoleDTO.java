package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceSupportRole} entity.
 */
public class ServiceSupportRoleDTO implements Serializable {

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

    @Size(max = 255)
    private String contactName;

    @Size(max = 255)
    private String contactPhoneNumber;


    private Long serviceRecordId;

    private Long serviceRoleTypeId;

    private Long serviceSupportContextTypeId;

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public Long getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(Long serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public Long getServiceRoleTypeId() {
        return serviceRoleTypeId;
    }

    public void setServiceRoleTypeId(Long serviceRoleTypeId) {
        this.serviceRoleTypeId = serviceRoleTypeId;
    }

    public Long getServiceSupportContextTypeId() {
        return serviceSupportContextTypeId;
    }

    public void setServiceSupportContextTypeId(Long serviceSupportRoleContextTypeId) {
        this.serviceSupportContextTypeId = serviceSupportRoleContextTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceSupportRoleDTO serviceSupportRoleDTO = (ServiceSupportRoleDTO) o;
        if (serviceSupportRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceSupportRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceSupportRoleDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            ", serviceRecord=" + getServiceRecordId() +
            ", serviceRoleType=" + getServiceRoleTypeId() +
            ", serviceSupportContextType=" + getServiceSupportContextTypeId() +
            "}";
    }
}
