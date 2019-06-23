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
 * A ServiceDeliveryOrganisation.
 */
@Entity
@Table(name = "service_delivery_organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceDeliveryOrganisation implements Serializable {

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
    @Column(name = "business_unit_name", length = 255)
    private String businessUnitName;

    @ManyToOne
    @JsonIgnoreProperties("serviceDeliveryOrganisations")
    private Agency agency;

    @OneToMany(mappedBy = "deliveryOrg")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRecord> serviceRecords = new HashSet<>();

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

    public ServiceDeliveryOrganisation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceDeliveryOrganisation createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceDeliveryOrganisation modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceDeliveryOrganisation modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceDeliveryOrganisation version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public ServiceDeliveryOrganisation businessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
        return this;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public Agency getAgency() {
        return agency;
    }

    public ServiceDeliveryOrganisation agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Set<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public ServiceDeliveryOrganisation serviceRecords(Set<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
        return this;
    }

    public ServiceDeliveryOrganisation addServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecords.add(serviceRecord);
        serviceRecord.setDeliveryOrg(this);
        return this;
    }

    public ServiceDeliveryOrganisation removeServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecords.remove(serviceRecord);
        serviceRecord.setDeliveryOrg(null);
        return this;
    }

    public void setServiceRecords(Set<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDeliveryOrganisation)) {
            return false;
        }
        return id != null && id.equals(((ServiceDeliveryOrganisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceDeliveryOrganisation{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", businessUnitName='" + getBusinessUnitName() + "'" +
            "}";
    }
}
