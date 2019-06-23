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
 * A ServiceTag.
 */
@Entity
@Table(name = "service_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

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

    @ManyToOne
    @JsonIgnoreProperties("serviceTags")
    private ServiceTag parent;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceTag> serviceTags = new HashSet<>();

    @OneToMany(mappedBy = "serviceTag")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceTagItems> serviceTagItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ServiceTag createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceTag createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceTag modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceTag modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public ServiceTag getParent() {
        return parent;
    }

    public ServiceTag parent(ServiceTag serviceTag) {
        this.parent = serviceTag;
        return this;
    }

    public void setParent(ServiceTag serviceTag) {
        this.parent = serviceTag;
    }

    public Set<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public ServiceTag serviceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags;
        return this;
    }

    public ServiceTag addServiceTag(ServiceTag serviceTag) {
        this.serviceTags.add(serviceTag);
        serviceTag.setParent(this);
        return this;
    }

    public ServiceTag removeServiceTag(ServiceTag serviceTag) {
        this.serviceTags.remove(serviceTag);
        serviceTag.setParent(null);
        return this;
    }

    public void setServiceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags;
    }

    public Set<ServiceTagItems> getServiceTagItems() {
        return serviceTagItems;
    }

    public ServiceTag serviceTagItems(Set<ServiceTagItems> serviceTagItems) {
        this.serviceTagItems = serviceTagItems;
        return this;
    }

    public ServiceTag addServiceTagItems(ServiceTagItems serviceTagItems) {
        this.serviceTagItems.add(serviceTagItems);
        serviceTagItems.setServiceTag(this);
        return this;
    }

    public ServiceTag removeServiceTagItems(ServiceTagItems serviceTagItems) {
        this.serviceTagItems.remove(serviceTagItems);
        serviceTagItems.setServiceTag(null);
        return this;
    }

    public void setServiceTagItems(Set<ServiceTagItems> serviceTagItems) {
        this.serviceTagItems = serviceTagItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceTag)) {
            return false;
        }
        return id != null && id.equals(((ServiceTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            "}";
    }
}
