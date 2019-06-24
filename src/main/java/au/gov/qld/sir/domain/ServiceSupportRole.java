package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ServiceSupportRole.
 */
@Entity
@Table(name = "service_support_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceSupportRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Size(max = 255)
    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "modified_date_time")
    private Instant modifiedDateTime;

    @Column(name = "version")
    private Long version;

    @Size(max = 255)
    @Column(name = "contact_email", length = 255)
    private String contactEmail;

    @Size(max = 255)
    @Column(name = "contact_name", length = 255)
    private String contactName;

    @Size(max = 255)
    @Column(name = "contact_phone_number", length = 255)
    private String contactPhoneNumber;

    @ManyToOne
    @JsonIgnoreProperties("serviceSupportRoles")
    @JoinColumn(name = "service_id")
    private ServiceRecord serviceRecord;

    @ManyToOne
    @JsonIgnoreProperties("serviceSupportRoles")
    private ServiceRoleType serviceRoleType;

    @ManyToOne
    @JsonIgnoreProperties("serviceSupportRoles")
    private ServiceSupportRoleContextType serviceSupportContextType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ServiceSupportRole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceSupportRole createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceSupportRole modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceSupportRole modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceSupportRole version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public ServiceSupportRole contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public ServiceSupportRole contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public ServiceSupportRole contactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
        return this;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public ServiceRecord getServiceRecord() {
        return serviceRecord;
    }

    public ServiceSupportRole serviceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
        return this;
    }

    public void setServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public ServiceRoleType getServiceRoleType() {
        return serviceRoleType;
    }

    public ServiceSupportRole serviceRoleType(ServiceRoleType serviceRoleType) {
        this.serviceRoleType = serviceRoleType;
        return this;
    }

    public void setServiceRoleType(ServiceRoleType serviceRoleType) {
        this.serviceRoleType = serviceRoleType;
    }

    public ServiceSupportRoleContextType getServiceSupportContextType() {
        return serviceSupportContextType;
    }

    public ServiceSupportRole serviceSupportContextType(ServiceSupportRoleContextType serviceSupportRoleContextType) {
        this.serviceSupportContextType = serviceSupportRoleContextType;
        return this;
    }

    public void setServiceSupportContextType(ServiceSupportRoleContextType serviceSupportRoleContextType) {
        this.serviceSupportContextType = serviceSupportRoleContextType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceSupportRole)) {
            return false;
        }
        return id != null && id.equals(((ServiceSupportRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceSupportRole{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            "}";
    }
}
