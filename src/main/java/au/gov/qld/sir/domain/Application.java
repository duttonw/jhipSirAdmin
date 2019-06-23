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
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "contact_email", length = 255)
    private String contactEmail;

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

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 10)
    @Column(name = "migrated_by", length = 10)
    private String migratedBy;

    @Column(name = "version")
    private Long version;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverride> applicationServiceOverrides = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Application contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Application createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public Application createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Application modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public Application modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getName() {
        return name;
    }

    public Application name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public Application migratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
        return this;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Long getVersion() {
        return version;
    }

    public Application version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<ApplicationServiceOverride> getApplicationServiceOverrides() {
        return applicationServiceOverrides;
    }

    public Application applicationServiceOverrides(Set<ApplicationServiceOverride> applicationServiceOverrides) {
        this.applicationServiceOverrides = applicationServiceOverrides;
        return this;
    }

    public Application addApplicationServiceOverride(ApplicationServiceOverride applicationServiceOverride) {
        this.applicationServiceOverrides.add(applicationServiceOverride);
        applicationServiceOverride.setApplication(this);
        return this;
    }

    public Application removeApplicationServiceOverride(ApplicationServiceOverride applicationServiceOverride) {
        this.applicationServiceOverrides.remove(applicationServiceOverride);
        applicationServiceOverride.setApplication(null);
        return this;
    }

    public void setApplicationServiceOverrides(Set<ApplicationServiceOverride> applicationServiceOverrides) {
        this.applicationServiceOverrides = applicationServiceOverrides;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", contactEmail='" + getContactEmail() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", name='" + getName() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
