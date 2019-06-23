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
 * Criteria class for the {@link au.gov.qld.sir.domain.Application} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactEmail;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private StringFilter name;

    private StringFilter migratedBy;

    private LongFilter version;

    private LongFilter applicationServiceOverrideId;

    public ApplicationCriteria(){
    }

    public ApplicationCriteria(ApplicationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.migratedBy = other.migratedBy == null ? null : other.migratedBy.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.applicationServiceOverrideId = other.applicationServiceOverrideId == null ? null : other.applicationServiceOverrideId.copy();
    }

    @Override
    public ApplicationCriteria copy() {
        return new ApplicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getMigratedBy() {
        return migratedBy;
    }

    public void setMigratedBy(StringFilter migratedBy) {
        this.migratedBy = migratedBy;
    }

    public LongFilter getVersion() {
        return version;
    }

    public void setVersion(LongFilter version) {
        this.version = version;
    }

    public LongFilter getApplicationServiceOverrideId() {
        return applicationServiceOverrideId;
    }

    public void setApplicationServiceOverrideId(LongFilter applicationServiceOverrideId) {
        this.applicationServiceOverrideId = applicationServiceOverrideId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicationCriteria that = (ApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(name, that.name) &&
            Objects.equals(migratedBy, that.migratedBy) &&
            Objects.equals(version, that.version) &&
            Objects.equals(applicationServiceOverrideId, that.applicationServiceOverrideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contactEmail,
        createdBy,
        createdDateTime,
        modifiedBy,
        modifiedDateTime,
        name,
        migratedBy,
        version,
        applicationServiceOverrideId
        );
    }

    @Override
    public String toString() {
        return "ApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (migratedBy != null ? "migratedBy=" + migratedBy + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (applicationServiceOverrideId != null ? "applicationServiceOverrideId=" + applicationServiceOverrideId + ", " : "") +
            "}";
    }

}
