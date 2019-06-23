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
 * Criteria class for the {@link au.gov.qld.sir.domain.Agency} entity. This class is used
 * in {@link au.gov.qld.sir.web.rest.AgencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AgencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDateTime;

    private StringFilter modifiedBy;

    private InstantFilter modifiedDateTime;

    private LongFilter version;

    private StringFilter agencyCode;

    private StringFilter agencyName;

    private StringFilter agencyUrl;

    private LongFilter agencyTypeId;

    private LongFilter agencySupportRoleId;

    private LongFilter integrationMappingId;

    private LongFilter locationId;

    private LongFilter serviceDeliveryOrganisationId;

    public AgencyCriteria(){
    }

    public AgencyCriteria(AgencyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDateTime = other.createdDateTime == null ? null : other.createdDateTime.copy();
        this.modifiedBy = other.modifiedBy == null ? null : other.modifiedBy.copy();
        this.modifiedDateTime = other.modifiedDateTime == null ? null : other.modifiedDateTime.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.agencyCode = other.agencyCode == null ? null : other.agencyCode.copy();
        this.agencyName = other.agencyName == null ? null : other.agencyName.copy();
        this.agencyUrl = other.agencyUrl == null ? null : other.agencyUrl.copy();
        this.agencyTypeId = other.agencyTypeId == null ? null : other.agencyTypeId.copy();
        this.agencySupportRoleId = other.agencySupportRoleId == null ? null : other.agencySupportRoleId.copy();
        this.integrationMappingId = other.integrationMappingId == null ? null : other.integrationMappingId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.serviceDeliveryOrganisationId = other.serviceDeliveryOrganisationId == null ? null : other.serviceDeliveryOrganisationId.copy();
    }

    @Override
    public AgencyCriteria copy() {
        return new AgencyCriteria(this);
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

    public StringFilter getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(StringFilter agencyCode) {
        this.agencyCode = agencyCode;
    }

    public StringFilter getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(StringFilter agencyName) {
        this.agencyName = agencyName;
    }

    public StringFilter getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(StringFilter agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public LongFilter getAgencyTypeId() {
        return agencyTypeId;
    }

    public void setAgencyTypeId(LongFilter agencyTypeId) {
        this.agencyTypeId = agencyTypeId;
    }

    public LongFilter getAgencySupportRoleId() {
        return agencySupportRoleId;
    }

    public void setAgencySupportRoleId(LongFilter agencySupportRoleId) {
        this.agencySupportRoleId = agencySupportRoleId;
    }

    public LongFilter getIntegrationMappingId() {
        return integrationMappingId;
    }

    public void setIntegrationMappingId(LongFilter integrationMappingId) {
        this.integrationMappingId = integrationMappingId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getServiceDeliveryOrganisationId() {
        return serviceDeliveryOrganisationId;
    }

    public void setServiceDeliveryOrganisationId(LongFilter serviceDeliveryOrganisationId) {
        this.serviceDeliveryOrganisationId = serviceDeliveryOrganisationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgencyCriteria that = (AgencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDateTime, that.createdDateTime) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDateTime, that.modifiedDateTime) &&
            Objects.equals(version, that.version) &&
            Objects.equals(agencyCode, that.agencyCode) &&
            Objects.equals(agencyName, that.agencyName) &&
            Objects.equals(agencyUrl, that.agencyUrl) &&
            Objects.equals(agencyTypeId, that.agencyTypeId) &&
            Objects.equals(agencySupportRoleId, that.agencySupportRoleId) &&
            Objects.equals(integrationMappingId, that.integrationMappingId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(serviceDeliveryOrganisationId, that.serviceDeliveryOrganisationId);
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
        agencyCode,
        agencyName,
        agencyUrl,
        agencyTypeId,
        agencySupportRoleId,
        integrationMappingId,
        locationId,
        serviceDeliveryOrganisationId
        );
    }

    @Override
    public String toString() {
        return "AgencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDateTime != null ? "createdDateTime=" + createdDateTime + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDateTime != null ? "modifiedDateTime=" + modifiedDateTime + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (agencyCode != null ? "agencyCode=" + agencyCode + ", " : "") +
                (agencyName != null ? "agencyName=" + agencyName + ", " : "") +
                (agencyUrl != null ? "agencyUrl=" + agencyUrl + ", " : "") +
                (agencyTypeId != null ? "agencyTypeId=" + agencyTypeId + ", " : "") +
                (agencySupportRoleId != null ? "agencySupportRoleId=" + agencySupportRoleId + ", " : "") +
                (integrationMappingId != null ? "integrationMappingId=" + integrationMappingId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (serviceDeliveryOrganisationId != null ? "serviceDeliveryOrganisationId=" + serviceDeliveryOrganisationId + ", " : "") +
            "}";
    }

}
