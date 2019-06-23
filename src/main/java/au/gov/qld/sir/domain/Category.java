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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

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
    @Column(name = "category", length = 255, nullable = false)
    private String category;

    @Size(max = 1)
    @Column(name = "migrated", length = 1)
    private String migrated;

    @Size(max = 10)
    @Column(name = "migrated_by", length = 10)
    private String migratedBy;

    @OneToMany(mappedBy = "displayedForCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceEvidence> serviceEvidences = new HashSet<>();

    @OneToMany(mappedBy = "serviceGroupCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceGroup> serviceGroups = new HashSet<>();

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

    public Category createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public Category createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Category modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public Category modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public Category version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public Category category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMigrated() {
        return migrated;
    }

    public Category migrated(String migrated) {
        this.migrated = migrated;
        return this;
    }

    public void setMigrated(String migrated) {
        this.migrated = migrated;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public Category migratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
        return this;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Set<ServiceEvidence> getServiceEvidences() {
        return serviceEvidences;
    }

    public Category serviceEvidences(Set<ServiceEvidence> serviceEvidences) {
        this.serviceEvidences = serviceEvidences;
        return this;
    }

    public Category addServiceEvidence(ServiceEvidence serviceEvidence) {
        this.serviceEvidences.add(serviceEvidence);
        serviceEvidence.setDisplayedForCategory(this);
        return this;
    }

    public Category removeServiceEvidence(ServiceEvidence serviceEvidence) {
        this.serviceEvidences.remove(serviceEvidence);
        serviceEvidence.setDisplayedForCategory(null);
        return this;
    }

    public void setServiceEvidences(Set<ServiceEvidence> serviceEvidences) {
        this.serviceEvidences = serviceEvidences;
    }

    public Set<ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public Category serviceGroups(Set<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
        return this;
    }

    public Category addServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.add(serviceGroup);
        serviceGroup.setServiceGroupCategory(this);
        return this;
    }

    public Category removeServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.remove(serviceGroup);
        serviceGroup.setServiceGroupCategory(null);
        return this;
    }

    public void setServiceGroups(Set<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", category='" + getCategory() + "'" +
            ", migrated='" + getMigrated() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            "}";
    }
}
