package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceTag} and its DTO {@link ServiceTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceTagMapper extends EntityMapper<ServiceTagDTO, ServiceTag> {

    @Mapping(source = "parent.id", target = "parentId")
    ServiceTagDTO toDto(ServiceTag serviceTag);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "serviceTags", ignore = true)
    @Mapping(target = "removeServiceTag", ignore = true)
    @Mapping(target = "serviceTagItems", ignore = true)
    @Mapping(target = "removeServiceTagItems", ignore = true)
    ServiceTag toEntity(ServiceTagDTO serviceTagDTO);

    default ServiceTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceTag serviceTag = new ServiceTag();
        serviceTag.setId(id);
        return serviceTag;
    }
}
