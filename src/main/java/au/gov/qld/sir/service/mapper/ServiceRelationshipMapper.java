package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceRelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRelationship} and its DTO {@link ServiceRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class})
public interface ServiceRelationshipMapper extends EntityMapper<ServiceRelationshipDTO, ServiceRelationship> {

    @Mapping(source = "fromService.id", target = "fromServiceId")
    @Mapping(source = "fromService.serviceName", target = "fromServiceServiceName")
    @Mapping(source = "toService.id", target = "toServiceId")
    @Mapping(source = "toService.serviceName", target = "toServiceServiceName")
    ServiceRelationshipDTO toDto(ServiceRelationship serviceRelationship);

    @Mapping(source = "fromServiceId", target = "fromService")
    @Mapping(source = "toServiceId", target = "toService")
    ServiceRelationship toEntity(ServiceRelationshipDTO serviceRelationshipDTO);

    default ServiceRelationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRelationship serviceRelationship = new ServiceRelationship();
        serviceRelationship.setId(id);
        return serviceRelationship;
    }
}
