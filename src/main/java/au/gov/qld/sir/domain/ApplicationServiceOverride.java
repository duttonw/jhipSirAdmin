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
 * A ApplicationServiceOverride.
 */
@Entity
@Table(name = "application_service_override")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationServiceOverride implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "eligibility", length = 255)
    private String eligibility;

    @Size(max = 255)
    @Column(name = "keywords", length = 255)
    private String keywords;

    @Size(max = 255)
    @Column(name = "long_description", length = 255)
    private String longDescription;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "pre_requisites", length = 255)
    private String preRequisites;

    @Size(max = 255)
    @Column(name = "fees", length = 255)
    private String fees;

    @Size(max = 1)
    @Column(name = "active", length = 1)
    private String active;

    @Size(max = 255)
    @Column(name = "reference_url", length = 255)
    private String referenceUrl;

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

    @Size(max = 10)
    @Column(name = "migrated_by", length = 10)
    private String migratedBy;

    @Column(name = "version")
    private Long version;

    @Size(max = 255)
    @Column(name = "how_to", length = 255)
    private String howTo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("applicationServiceOverrides")
    private ServiceRecord serviceRecord;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("applicationServiceOverrides")
    private Application application;

    @OneToMany(mappedBy = "applicationServiceOverride")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributes = new HashSet<>();

    @OneToMany(mappedBy = "applicationServiceOverride")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ApplicationServiceOverride description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEligibility() {
        return eligibility;
    }

    public ApplicationServiceOverride eligibility(String eligibility) {
        this.eligibility = eligibility;
        return this;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getKeywords() {
        return keywords;
    }

    public ApplicationServiceOverride keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public ApplicationServiceOverride longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getName() {
        return name;
    }

    public ApplicationServiceOverride name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public ApplicationServiceOverride preRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
        return this;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getFees() {
        return fees;
    }

    public ApplicationServiceOverride fees(String fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getActive() {
        return active;
    }

    public ApplicationServiceOverride active(String active) {
        this.active = active;
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public ApplicationServiceOverride referenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ApplicationServiceOverride createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ApplicationServiceOverride createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ApplicationServiceOverride modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ApplicationServiceOverride modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getMigratedBy() {
        return migratedBy;
    }

    public ApplicationServiceOverride migratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
        return this;
    }

    public void setMigratedBy(String migratedBy) {
        this.migratedBy = migratedBy;
    }

    public Long getVersion() {
        return version;
    }

    public ApplicationServiceOverride version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getHowTo() {
        return howTo;
    }

    public ApplicationServiceOverride howTo(String howTo) {
        this.howTo = howTo;
        return this;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public ServiceRecord getServiceRecord() {
        return serviceRecord;
    }

    public ApplicationServiceOverride serviceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
        return this;
    }

    public void setServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationServiceOverride application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<ApplicationServiceOverrideAttribute> getApplicationServiceOverrideAttributes() {
        return applicationServiceOverrideAttributes;
    }

    public ApplicationServiceOverride applicationServiceOverrideAttributes(Set<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributes) {
        this.applicationServiceOverrideAttributes = applicationServiceOverrideAttributes;
        return this;
    }

    public ApplicationServiceOverride addApplicationServiceOverrideAttribute(ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute) {
        this.applicationServiceOverrideAttributes.add(applicationServiceOverrideAttribute);
        applicationServiceOverrideAttribute.setApplicationServiceOverride(this);
        return this;
    }

    public ApplicationServiceOverride removeApplicationServiceOverrideAttribute(ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute) {
        this.applicationServiceOverrideAttributes.remove(applicationServiceOverrideAttribute);
        applicationServiceOverrideAttribute.setApplicationServiceOverride(null);
        return this;
    }

    public void setApplicationServiceOverrideAttributes(Set<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributes) {
        this.applicationServiceOverrideAttributes = applicationServiceOverrideAttributes;
    }

    public Set<ApplicationServiceOverrideTagItems> getApplicationServiceOverrideTagItems() {
        return applicationServiceOverrideTagItems;
    }

    public ApplicationServiceOverride applicationServiceOverrideTagItems(Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
        return this;
    }

    public ApplicationServiceOverride addApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems.add(applicationServiceOverrideTagItems);
        applicationServiceOverrideTagItems.setApplicationServiceOverride(this);
        return this;
    }

    public ApplicationServiceOverride removeApplicationServiceOverrideTagItems(ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems.remove(applicationServiceOverrideTagItems);
        applicationServiceOverrideTagItems.setApplicationServiceOverride(null);
        return this;
    }

    public void setApplicationServiceOverrideTagItems(Set<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItems) {
        this.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationServiceOverride)) {
            return false;
        }
        return id != null && id.equals(((ApplicationServiceOverride) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationServiceOverride{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", eligibility='" + getEligibility() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", name='" + getName() + "'" +
            ", preRequisites='" + getPreRequisites() + "'" +
            ", fees='" + getFees() + "'" +
            ", active='" + getActive() + "'" +
            ", referenceUrl='" + getReferenceUrl() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", migratedBy='" + getMigratedBy() + "'" +
            ", version=" + getVersion() +
            ", howTo='" + getHowTo() + "'" +
            "}";
    }
}
