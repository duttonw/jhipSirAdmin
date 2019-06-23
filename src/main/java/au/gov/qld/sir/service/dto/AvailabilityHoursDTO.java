package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.AvailabilityHours} entity.
 */
public class AvailabilityHoursDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(max = 1)
    private String available;

    private Integer availabilityHoursId;

    @Size(max = 255)
    private String comments;

    private Instant validFrom;

    private Instant validTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Integer getAvailabilityHoursId() {
        return availabilityHoursId;
    }

    public void setAvailabilityHoursId(Integer availabilityHoursId) {
        this.availabilityHoursId = availabilityHoursId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AvailabilityHoursDTO availabilityHoursDTO = (AvailabilityHoursDTO) o;
        if (availabilityHoursDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), availabilityHoursDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvailabilityHoursDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", available='" + getAvailable() + "'" +
            ", availabilityHoursId=" + getAvailabilityHoursId() +
            ", comments='" + getComments() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
