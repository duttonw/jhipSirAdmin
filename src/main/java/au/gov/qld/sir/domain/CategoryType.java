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
 * A CategoryType.
 */
@Entity
@Table(name = "category_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoryType implements Serializable {

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
    @Column(name = "category_type", length = 255, nullable = false)
    private String categoryType;

    @Size(max = 1)
    @Column(name = "migrated", length = 1)
    private String migrated;

    @Size(max = 10)
    @Column(name = "migrated_by", length = 10)
    private String migratedBy;

    @OneToMany(mappedBy = "serviceGroupCategoryType")
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

    public CategoryType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public CategoryType createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public CategoryType modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public CategoryType modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public CategoryType version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public CategoryType categoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getMigrated() {
        return migrated;
    }

    public CategoryType migrated(String migrated) {
        this.migrated = migrated;
        return this;
    }

    public void setMigrated(String migrated) {
        this.migrated = migrated;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public CategoryType migratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
        return this;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Set<ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public CategoryType serviceGroups(Set<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
        return this;
    }

    public CategoryType addServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.add(serviceGroup);
        serviceGroup.setServiceGroupCategoryType(this);
        return this;
    }

    public CategoryType removeServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.remove(serviceGroup);
        serviceGroup.setServiceGroupCategoryType(null);
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
        if (!(o instanceof CategoryType)) {
            return false;
        }
        return id != null && id.equals(((CategoryType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoryType{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", categoryType='" + getCategoryType() + "'" +
            ", migrated='" + getMigrated() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            "}";
    }
}
