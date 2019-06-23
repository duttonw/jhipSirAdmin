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
 * service_support_role_contextType
 */
@Entity
@Table(name = "service_support_role_cont_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceSupportRoleContextType implements Serializable {

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

    @NotNull
    @Size(max = 255)
    @Column(name = "context", length = 255, nullable = false)
    private String context;

    @OneToMany(mappedBy = "serviceSupportContextType")
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

    public ServiceSupportRoleContextType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceSupportRoleContextType createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceSupportRoleContextType modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceSupportRoleContextType modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceSupportRoleContextType version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContext() {
        return context;
    }

    public ServiceSupportRoleContextType context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Set<ServiceSupportRole> getServiceSupportRoles() {
        return serviceSupportRoles;
    }

    public ServiceSupportRoleContextType serviceSupportRoles(Set<ServiceSupportRole> serviceSupportRoles) {
        this.serviceSupportRoles = serviceSupportRoles;
        return this;
    }

    public ServiceSupportRoleContextType addServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.add(serviceSupportRole);
        serviceSupportRole.setServiceSupportContextType(this);
        return this;
    }

    public ServiceSupportRoleContextType removeServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.remove(serviceSupportRole);
        serviceSupportRole.setServiceSupportContextType(null);
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
        if (!(o instanceof ServiceSupportRoleContextType)) {
            return false;
        }
        return id != null && id.equals(((ServiceSupportRoleContextType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceSupportRoleContextType{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", context='" + getContext() + "'" +
            "}";
    }
}
