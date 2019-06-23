package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceTagItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceTagItems} and its DTO {@link ServiceTagItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class, ServiceTagMapper.class})
public interface ServiceTagItemsMapper extends EntityMapper<ServiceTagItemsDTO, ServiceTagItems> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceTag.id", target = "serviceTagId")
    ServiceTagItemsDTO toDto(ServiceTagItems serviceTagItems);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    @Mapping(source = "serviceTagId", target = "serviceTag")
    ServiceTagItems toEntity(ServiceTagItemsDTO serviceTagItemsDTO);

    default ServiceTagItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceTagItems serviceTagItems = new ServiceTagItems();
        serviceTagItems.setId(id);
        return serviceTagItems;
    }
}
