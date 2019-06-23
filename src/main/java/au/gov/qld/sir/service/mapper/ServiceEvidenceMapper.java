package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceEvidenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceEvidence} and its DTO {@link ServiceEvidenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ServiceRecordMapper.class})
public interface ServiceEvidenceMapper extends EntityMapper<ServiceEvidenceDTO, ServiceEvidence> {

    @Mapping(source = "displayedForCategory.id", target = "displayedForCategoryId")
    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    ServiceEvidenceDTO toDto(ServiceEvidence serviceEvidence);

    @Mapping(source = "displayedForCategoryId", target = "displayedForCategory")
    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    ServiceEvidence toEntity(ServiceEvidenceDTO serviceEvidenceDTO);

    default ServiceEvidence fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceEvidence serviceEvidence = new ServiceEvidence();
        serviceEvidence.setId(id);
        return serviceEvidence;
    }
}
