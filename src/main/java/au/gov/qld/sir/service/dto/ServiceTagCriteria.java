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
 * Criteria class for the {@link au.gov.qld.sir.domain.ServiceTag} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.ServiceTagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceTagCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter parentId;

    private LongFilter serviceTagId;

    private LongFilter serviceTagItemsId;

    public ServiceTagCriteria(){
    }

    public ServiceTagCriteria(ServiceTagCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.serviceTagId = other.serviceTagId == null ? null : other.serviceTagId.copy();
        this.serviceTagItemsId = other.serviceTagItemsId == null ? null : other.serviceTagItemsId.copy();
    }

    @Override
    public ServiceTagCriteria copy() {
        return new ServiceTagCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getServiceTagId() {
        return serviceTagId;
    }

    public void setServiceTagId(LongFilter serviceTagId) {
        this.serviceTagId = serviceTagId;
    }

    public LongFilter getServiceTagItemsId() {
        return serviceTagItemsId;
    }

    public void setServiceTagItemsId(LongFilter serviceTagItemsId) {
        this.serviceTagItemsId = serviceTagItemsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceTagCriteria that = (ServiceTagCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(serviceTagId, that.serviceTagId) &&
            Objects.equals(serviceTagItemsId, that.serviceTagItemsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        createdBy,
        createdDateTime,
        modifiedBy,
        modifiedDateTime,
        parentId,
        serviceTagId,
        serviceTagItemsId
        );
    }

    @Override
    public String toString() {
        return "ServiceTagCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (serviceTagId != null ? "serviceTagId=" + serviceTagId + ", " : "") +
                (serviceTagItemsId != null ? "serviceTagItemsId=" + serviceTagItemsId + ", " : "") +
            "}";
    }

}
