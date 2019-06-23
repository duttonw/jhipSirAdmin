package au.gov.qld.sir.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceRoleType.
 */
@Entity
@Table(name = "service_role_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceRoleType implements Serializable {

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
    @Column(name = "service_role", length = 255)
    private String serviceRole;

    @OneToMany(mappedBy = "agencyRoleType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgencySupportRole> agencySupportRoles = new HashSet<>();

    @OneToMany(mappedBy = "serviceRoleType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceSupportRole> serviceSupportRoles = new HashSet<>();

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

    public ServiceRoleType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceRoleType createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceRoleType modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceRoleType modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceRoleType version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public ServiceRoleType serviceRole(String serviceRole) {
        this.serviceRole = serviceRole;
        return this;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public Set<AgencySupportRole> getAgencySupportRoles() {
        return agencySupportRoles;
    }

    public ServiceRoleType agencySupportRoles(Set<AgencySupportRole> agencySupportRoles) {
        this.agencySupportRoles = agencySupportRoles;
        return this;
    }

    public ServiceRoleType addAgencySupportRole(AgencySupportRole agencySupportRole) {
        this.agencySupportRoles.add(agencySupportRole);
        agencySupportRole.setAgencyRoleType(this);
        return this;
    }

    public ServiceRoleType removeAgencySupportRole(AgencySupportRole agencySupportRole) {
        this.agencySupportRoles.remove(agencySupportRole);
        agencySupportRole.setAgencyRoleType(null);
        return this;
    }

    public void setAgencySupportRoles(Set<AgencySupportRole> agencySupportRoles) {
        this.agencySupportRoles = agencySupportRoles;
    }

    public Set<ServiceSupportRole> getServiceSupportRoles() {
        return serviceSupportRoles;
    }

    public ServiceRoleType serviceSupportRoles(Set<ServiceSupportRole> serviceSupportRoles) {
        this.serviceSupportRoles = serviceSupportRoles;
        return this;
    }

    public ServiceRoleType addServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.add(serviceSupportRole);
        serviceSupportRole.setServiceRoleType(this);
        return this;
    }

    public ServiceRoleType removeServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.remove(serviceSupportRole);
        serviceSupportRole.setServiceRoleType(null);
        return this;
    }

    public void setServiceSupportRoles(Set<ServiceSupportRole> serviceSupportRoles) {
        this.serviceSupportRoles = serviceSupportRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRoleType)) {
            return false;
        }
        return id != null && id.equals(((ServiceRoleType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceRoleType{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", serviceRole='" + getServiceRole() + "'" +
            "}";
    }
}
