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
 * Criteria class for the {@link au.gov.qld.sir.domain.OpeningHoursSpecification} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.OpeningHoursSpecificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opening-hours-specifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OpeningHoursSpecificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter closes;

    private StringFilter dayOfWeek;

    private StringFilter opens;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter availabilityHoursId;

    public OpeningHoursSpecificationCriteria(){
    }

    public OpeningHoursSpecificationCriteria(OpeningHoursSpecificationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.closes = other.closes == null ? null : other.closes.copy();
        this.dayOfWeek = other.dayOfWeek == null ? null : other.dayOfWeek.copy();
        this.opens = other.opens == null ? null : other.opens.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.availabilityHoursId = other.availabilityHoursId == null ? null : other.availabilityHoursId.copy();
    }

    @Override
    public OpeningHoursSpecificationCriteria copy() {
        return new OpeningHoursSpecificationCriteria(this);
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

    public StringFilter getCloses() {
        return closes;
    }

    public void setCloses(StringFilter closes) {
        this.closes = closes;
    }

    public StringFilter getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(StringFilter dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public StringFilter getOpens() {
        return opens;
    }

    public void setOpens(StringFilter opens) {
        this.opens = opens;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getAvailabilityHoursId() {
        return availabilityHoursId;
    }

    public void setAvailabilityHoursId(LongFilter availabilityHoursId) {
        this.availabilityHoursId = availabilityHoursId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OpeningHoursSpecificationCriteria that = (OpeningHoursSpecificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(closes, that.closes) &&
            Objects.equals(dayOfWeek, that.dayOfWeek) &&
            Objects.equals(opens, that.opens) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(availabilityHoursId, that.availabilityHoursId);
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
        closes,
        dayOfWeek,
        opens,
        validFrom,
        validTo,
        availabilityHoursId
        );
    }

    @Override
    public String toString() {
        return "OpeningHoursSpecificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (closes != null ? "closes=" + closes + ", " : "") +
                (dayOfWeek != null ? "dayOfWeek=" + dayOfWeek + ", " : "") +
                (opens != null ? "opens=" + opens + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (availabilityHoursId != null ? "availabilityHoursId=" + availabilityHoursId + ", " : "") +
            "}";
    }

}
