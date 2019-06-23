package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AgencySupportRole.
 */
@Entity
@Table(name = "agency_support_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgencySupportRole implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("agencySupportRoles")
    private Agency agency;

    @ManyToOne
    @JsonIgnoreProperties("agencySupportRoles")
    private ServiceRoleType agencyRoleType;

    @ManyToOne
    @JsonIgnoreProperties("agencySupportRoles")
    private AgencySupportRoleContextType agencySupportContextType;

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

    public AgencySupportRole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public AgencySupportRole createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public AgencySupportRole modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public AgencySupportRole modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public AgencySupportRole version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public AgencySupportRole contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Agency getAgency() {
        return agency;
    }

    public AgencySupportRole agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public ServiceRoleType getAgencyRoleType() {
        return agencyRoleType;
    }

    public AgencySupportRole agencyRoleType(ServiceRoleType serviceRoleType) {
        this.agencyRoleType = serviceRoleType;
        return this;
    }

    public void setAgencyRoleType(ServiceRoleType serviceRoleType) {
        this.agencyRoleType = serviceRoleType;
    }

    public AgencySupportRoleContextType getAgencySupportContextType() {
        return agencySupportContextType;
    }

    public AgencySupportRole agencySupportContextType(AgencySupportRoleContextType agencySupportRoleContextType) {
        this.agencySupportContextType = agencySupportRoleContextType;
        return this;
    }

    public void setAgencySupportContextType(AgencySupportRoleContextType agencySupportRoleContextType) {
        this.agencySupportContextType = agencySupportRoleContextType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgencySupportRole)) {
            return false;
        }
        return id != null && id.equals(((AgencySupportRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgencySupportRole{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", contactEmail='" + getContactEmail() + "'" +
            "}";
    }
}
