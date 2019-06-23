package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * application_service_override_tag
 */
@Entity
@Table(name = "application_service_override_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationServiceOverrideTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

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

    @Size(max = 10)
    @Column(name = "migrated_by", length = 10)
    private String migratedBy;

    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JsonIgnoreProperties("applicationServiceOverrideTags")
    private ApplicationServiceOverrideTag parent;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverrideTag> applicationServiceOverrideTags = new HashSet<>();

    @OneToMany(mappedBy = "applicationServiceOverrideTag")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApplicationServiceOverrideTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ApplicationServiceOverrideTag createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ApplicationServiceOverrideTag createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ApplicationServiceOverrideTag modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ApplicationServiceOverrideTag modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public ApplicationServiceOverrideTag migratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
        return this;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Long getVersion() {
        return version;
    }

    public ApplicationServiceOverrideTag version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ApplicationServiceOverrideTag getParent() {
        return parent;
    }

    public ApplicationServiceOverrideTag parent(ApplicationServiceOverrideTag applicationServiceOverrideTag) {
        this.parent = applicationServiceOverrideTag;
        return this;
    }

    public void setParent(ApplicationServiceOverrideTag applicationServiceOverrideTag) {
        this.parent = applicationServiceOverrideTag;
    }

    public Set<ApplicationServiceOverrideTag> getApplicationServiceOverrideTags() {
        return applicationServiceOverrideTags;
    }

    public ApplicationServiceOverrideTag applicationServiceOverrideTags(Set<ApplicationServiceOverrideTag> applicationServiceOverrideTags) {
        this.applicationServiceOverrideTags = applicationServiceOverrideTags;
        return this;
    }

    public ApplicationServiceOverrideTag addApplicationServiceOverrideTag(ApplicationServiceOverrideTag applicationServiceOverrideTag) {
        this.applicationServiceOverrideTags.add(applicationServiceOverrideTag);
        applicationServiceOverrideTag.setParent(this);
        return this;
    }

    public ApplicationServiceOverrideTag removeApplicationServiceOverrideTag(ApplicationServiceOverrideTag applicationServiceOverrideTag) {
        this.applicationServiceOverrideTags.remove(applicationServiceOverrideTag);
        applicationServiceOverrideTag.setParent(null);
        return this;
    }

    public void setApplicationServiceOverrideTags(Set<ApplicationServiceOverrideTag> applicationServiceOverrideTags) {
        this.applicationServiceOverrideTags = applicationServiceOverrideTags;
    }

    public Set<ApplicationServiceOverrideTagItems> getApplicationServiceOverrideTagItems() {
        return applicationServiceOverrideTagItems;
    }

    public ApplicationServiceOverrideTag applicationServiceOverrideTagItems(Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
        return this;
    }

    public ApplicationServiceOverrideTag addApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems.add(applicationServiceOverrideTagItems);
        applicationServiceOverrideTagItems.setApplicationServiceOverrideTag(this);
        return this;
    }

    public ApplicationServiceOverrideTag removeApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems.remove(applicationServiceOverrideTagItems);
        applicationServiceOverrideTagItems.setApplicationServiceOverrideTag(null);
        return this;
    }

    public void setApplicationServiceOverrideTagItems(Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationServiceOverrideTag)) {
            return false;
        }
        return id != null && id.equals(((ApplicationServiceOverrideTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverrideTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
