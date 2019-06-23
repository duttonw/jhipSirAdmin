package au.gov.qld.sir.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link au.gov.qld.sir.domain.CategoryType} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.CategoryTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /category-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter categoryType;

    private StringFilter migrated;

    private StringFilter migratedBy;

    private LongFilter serviceGroupId;

    public CategoryTypeCriteria(){
    }

    public CategoryTypeCriteria(CategoryTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.categoryType = other.categoryType == null ? null : other.categoryType.copy();
        this.migrated = other.migrated == null ? null : other.migrated.copy();
        this.migratedBy = other.migratedBy == null ? null : other.migratedBy.copy();
        this.serviceGroupId = other.serviceGroupId == null ? null : other.serviceGroupId.copy();
    }

    @Override
    public CategoryTypeCriteria copy() {
        return new CategoryTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(InstantFilter createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public InstantFilter getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(InstantFilter modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public LongFilter getVersion() {
        return version;
    }

    public void setVersion(LongFilter version) {
        this.version = version;
    }

    public StringFilter getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(StringFilter categoryType) {
        this.categoryType = categoryType;
    }

    public StringFilter getMigrated() {
        return migrated;
    }

    public void setMigrated(StringFilter migrated) {
        this.migrated = migrated;
    }

    public StringFilter getMigratedBy() {
        return migratedBy;
    }

    public void setMigratedBy(StringFilter migratedBy) {
        this.migratedBy = migratedBy;
    }

    public LongFilter getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(LongFilter serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryTypeCriteria that = (CategoryTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(categoryType, that.categoryType) &&
            Objects.equals(migrated, that.migrated) &&
            Objects.equals(migratedBy, that.migratedBy) &&
            Objects.equals(serviceGroupId, that.serviceGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        createdDateTime,
        modifiedBy,
        modifiedDateTime,
        version,
        categoryType,
        migrated,
        migratedBy,
        serviceGroupId
        );
    }

    @Override
    public String toString() {
        return "CategoryTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (categoryType != null ? "categoryType=" + categoryType + ", " : "") +
                (migrated != null ? "migrated=" + migrated + ", " : "") +
                (migratedBy != null ? "migratedBy=" + migratedBy + ", " : "") +
                (serviceGroupId != null ? "serviceGroupId=" + serviceGroupId + ", " : "") +
            "}";
    }

}
