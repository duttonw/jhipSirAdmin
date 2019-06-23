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
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agency implements Serializable {

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

    @Size(max = 255)
    @Column(name = "agency_code", length = 255)
    private String agencyCode;

    @Size(max = 255)
    @Column(name = "agency_name", length = 255)
    private String agencyName;

    @Size(max = 255)
    @Column(name = "agency_url", length = 255)
    private String agencyUrl;

    @ManyToOne
    @JsonIgnoreProperties("agencies")
    private AgencyType agencyType;

    @OneToMany(mappedBy = "agency")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgencySupportRole> agencySupportRoles = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IntegrationMapping> integrationMappings = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceDeliveryOrganisation> serviceDeliveryOrganisations = new HashSet<>();

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

    public Agency createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public Agency createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Agency modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public Agency modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public Agency version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public Agency agencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
        return this;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public Agency agencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public Agency agencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
        return this;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public AgencyType getAgencyType() {
        return agencyType;
    }

    public Agency agencyType(AgencyType agencyType) {
        this.agencyType = agencyType;
        return this;
    }

    public void setAgencyType(AgencyType agencyType) {
        this.agencyType = agencyType;
    }

    public Set<AgencySupportRole> getAgencySupportRoles() {
        return agencySupportRoles;
    }

    public Agency agencySupportRoles(Set<AgencySupportRole> agencySupportRoles) {
        this.agencySupportRoles = agencySupportRoles;
        return this;
    }

    public Agency addAgencySupportRole(AgencySupportRole agencySupportRole) {
        this.agencySupportRoles.add(agencySupportRole);
        agencySupportRole.setAgency(this);
        return this;
    }

    public Agency removeAgencySupportRole(AgencySupportRole agencySupportRole) {
        this.agencySupportRoles.remove(agencySupportRole);
        agencySupportRole.setAgency(null);
        return this;
    }

    public void setAgencySupportRoles(Set<AgencySupportRole> agencySupportRoles) {
        this.agencySupportRoles = agencySupportRoles;
    }

    public Set<IntegrationMapping> getIntegrationMappings() {
        return integrationMappings;
    }

    public Agency integrationMappings(Set<IntegrationMapping> integrationMappings) {
        this.integrationMappings = integrationMappings;
        return this;
    }

    public Agency addIntegrationMapping(IntegrationMapping integrationMapping) {
        this.integrationMappings.add(integrationMapping);
        integrationMapping.setAgency(this);
        return this;
    }

    public Agency removeIntegrationMapping(IntegrationMapping integrationMapping) {
        this.integrationMappings.remove(integrationMapping);
        integrationMapping.setAgency(null);
        return this;
    }

    public void setIntegrationMappings(Set<IntegrationMapping> integrationMappings) {
        this.integrationMappings = integrationMappings;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Agency locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Agency addLocation(Location location) {
        this.locations.add(location);
        location.setAgency(this);
        return this;
    }

    public Agency removeLocation(Location location) {
        this.locations.remove(location);
        location.setAgency(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<ServiceDeliveryOrganisation> getServiceDeliveryOrganisations() {
        return serviceDeliveryOrganisations;
    }

    public Agency serviceDeliveryOrganisations(Set<ServiceDeliveryOrganisation> serviceDeliveryOrganisations) {
        this.serviceDeliveryOrganisations = serviceDeliveryOrganisations;
        return this;
    }

    public Agency addServiceDeliveryOrganisation(ServiceDeliveryOrganisation serviceDeliveryOrganisation) {
        this.serviceDeliveryOrganisations.add(serviceDeliveryOrganisation);
        serviceDeliveryOrganisation.setAgency(this);
        return this;
    }

    public Agency removeServiceDeliveryOrganisation(ServiceDeliveryOrganisation serviceDeliveryOrganisation) {
        this.serviceDeliveryOrganisations.remove(serviceDeliveryOrganisation);
        serviceDeliveryOrganisation.setAgency(null);
        return this;
    }

    public void setServiceDeliveryOrganisations(Set<ServiceDeliveryOrganisation> serviceDeliveryOrganisations) {
        this.serviceDeliveryOrganisations = serviceDeliveryOrganisations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agency)) {
            return false;
        }
        return id != null && id.equals(((Agency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Agency{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", agencyCode='" + getAgencyCode() + "'" +
            ", agencyName='" + getAgencyName() + "'" +
            ", agencyUrl='" + getAgencyUrl() + "'" +
            "}";
    }
}
