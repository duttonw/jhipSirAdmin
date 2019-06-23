package au.gov.qld.sir.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link au.gov.qld.sir.domain.ServiceTagItems} entity.
 */
public class ServiceTagItemsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String source;

    @Size(max = 255)
    private String createdBy;

    private Instant createdDateTime;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDateTime;


    private Long serviceRecordId;

    private Long serviceTagId;

    private String serviceTagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Long getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(Long serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public Long getServiceTagId() {
        return serviceTagId;
    }

    public void setServiceTagId(Long serviceTagId) {
        this.serviceTagId = serviceTagId;
    }

    public String getServiceTagName() {
        return serviceTagName;
    }

    public void setServiceTagName(String serviceTagName) {
        this.serviceTagName = serviceTagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceTagItemsDTO serviceTagItemsDTO = (ServiceTagItemsDTO) o;
        if (serviceTagItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceTagItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceTagItemsDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", serviceRecord=" + getServiceRecordId() +
            ", serviceTag=" + getServiceTagId() +
            ", serviceTag='" + getServiceTagName() + "'" +
            "}";
    }
}
