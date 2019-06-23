package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A OpeningHoursSpecification.
 */
@Entity
@Table(name = "opening_hours_specification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpeningHoursSpecification implements Serializable {

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

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "closes", length = 8)
    private String closes;

    @Size(max = 255)
    @Column(name = "day_of_week", length = 255)
    private String dayOfWeek;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "opens", length = 8)
    private String opens;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties("openingHoursSpecifications")
    private AvailabilityHours availabilityHours;

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

    public OpeningHoursSpecification createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public OpeningHoursSpecification createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public OpeningHoursSpecification modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public OpeningHoursSpecification modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public OpeningHoursSpecification version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCloses() {
        return closes;
    }

    public OpeningHoursSpecification closes(String closes) {
        this.closes = closes;
        return this;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public OpeningHoursSpecification dayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getOpens() {
        return opens;
    }

    public OpeningHoursSpecification opens(String opens) {
        this.opens = opens;
        return this;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public OpeningHoursSpecification validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public OpeningHoursSpecification validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public AvailabilityHours getAvailabilityHours() {
        return availabilityHours;
    }

    public OpeningHoursSpecification availabilityHours(AvailabilityHours availabilityHours) {
        this.availabilityHours = availabilityHours;
        return this;
    }

    public void setAvailabilityHours(AvailabilityHours availabilityHours) {
        this.availabilityHours = availabilityHours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpeningHoursSpecification)) {
            return false;
        }
        return id != null && id.equals(((OpeningHoursSpecification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OpeningHoursSpecification{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", closes='" + getCloses() + "'" +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", opens='" + getOpens() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
