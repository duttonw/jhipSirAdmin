package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.OpeningHoursSpecification} entity.
 */
public class OpeningHoursSpecificationDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;

    private Long version;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String closes;

    @Size(max = 255)
    private String dayOfWeek;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String opens;

    private Instant validFrom;

    private Instant validTo;


    private Long availabilityHoursId;

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

    public String getCloses() {
        return closes;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
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

    public Long getAvailabilityHoursId() {
        return availabilityHoursId;
    }

    public void setAvailabilityHoursId(Long availabilityHoursId) {
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

        OpeningHoursSpecificationDTO openingHoursSpecificationDTO = (OpeningHoursSpecificationDTO) o;
        if (openingHoursSpecificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), openingHoursSpecificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OpeningHoursSpecificationDTO{" +
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
            ", availabilityHours=" + getAvailabilityHoursId() +
            "}";
    }
}
