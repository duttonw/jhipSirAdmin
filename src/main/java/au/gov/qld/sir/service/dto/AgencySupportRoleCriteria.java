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
 * Criteria class for the {@link au.gov.qld.sir.domain.AgencySupportRole} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.AgencySupportRoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agency-support-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AgencySupportRoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter contactEmail;

    private LongFilter agencyId;

    private LongFilter agencyRoleTypeId;

    private LongFilter agencySupportContextTypeId;

    public AgencySupportRoleCriteria(){
    }

    public AgencySupportRoleCriteria(AgencySupportRoleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.agencyId = other.agencyId == null ? null : other.agencyId.copy();
        this.agencyRoleTypeId = other.agencyRoleTypeId == null ? null : other.agencyRoleTypeId.copy();
        this.agencySupportContextTypeId = other.agencySupportContextTypeId == null ? null : other.agencySupportContextTypeId.copy();
    }

    @Override
    public AgencySupportRoleCriteria copy() {
        return new AgencySupportRoleCriteria(this);
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

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LongFilter getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(LongFilter agencyId) {
        this.agencyId = agencyId;
    }

    public LongFilter getAgencyRoleTypeId() {
        return agencyRoleTypeId;
    }

    public void setAgencyRoleTypeId(LongFilter agencyRoleTypeId) {
        this.agencyRoleTypeId = agencyRoleTypeId;
    }

    public LongFilter getAgencySupportContextTypeId() {
        return agencySupportContextTypeId;
    }

    public void setAgencySupportContextTypeId(LongFilter agencySupportContextTypeId) {
        this.agencySupportContextTypeId = agencySupportContextTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgencySupportRoleCriteria that = (AgencySupportRoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(agencyId, that.agencyId) &&
            Objects.equals(agencyRoleTypeId, that.agencyRoleTypeId) &&
            Objects.equals(agencySupportContextTypeId, that.agencySupportContextTypeId);
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
        contactEmail,
        agencyId,
        agencyRoleTypeId,
        agencySupportContextTypeId
        );
    }

    @Override
    public String toString() {
        return "AgencySupportRoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
                (agencyId != null ? "agencyId=" + agencyId + ", " : "") +
                (agencyRoleTypeId != null ? "agencyRoleTypeId=" + agencyRoleTypeId + ", " : "") +
                (agencySupportContextTypeId != null ? "agencySupportContextTypeId=" + agencySupportContextTypeId + ", " : "") +
            "}";
    }

}
