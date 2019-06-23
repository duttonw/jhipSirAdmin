package au.gov.qld.sir.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceEventType.
 */
@Entity
@Table(name = "service_event_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceEventType implements Serializable {

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

    @NotNull
    @Size(max = 255)
    @Column(name = "service_event", length = 255, nullable = false)
    private String serviceEvent;

    @OneToMany(mappedBy = "serviceEventType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceEvent> serviceEvents = new HashSet<>();

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

    public ServiceEventType createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceEventType createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceEventType modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceEventType modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceEventType version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getServiceEvent() {
        return serviceEvent;
    }

    public ServiceEventType serviceEvent(String serviceEvent) {
        this.serviceEvent = serviceEvent;
        return this;
    }

    public void setServiceEvent(String serviceEvent) {
        this.serviceEvent = serviceEvent;
    }

    public Set<ServiceEvent> getServiceEvents() {
        return serviceEvents;
    }

    public ServiceEventType serviceEvents(Set<ServiceEvent> serviceEvents) {
        this.serviceEvents = serviceEvents;
        return this;
    }

    public ServiceEventType addServiceEvent(ServiceEvent serviceEvent) {
        this.serviceEvents.add(serviceEvent);
        serviceEvent.setServiceEventType(this);
        return this;
    }

    public ServiceEventType removeServiceEvent(ServiceEvent serviceEvent) {
        this.serviceEvents.remove(serviceEvent);
        serviceEvent.setServiceEventType(null);
        return this;
    }

    public void setServiceEvents(Set<ServiceEvent> serviceEvents) {
        this.serviceEvents = serviceEvents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceEventType)) {
            return false;
        }
        return id != null && id.equals(((ServiceEventType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceEventType{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", serviceEvent='" + getServiceEvent() + "'" +
            "}";
    }
}
