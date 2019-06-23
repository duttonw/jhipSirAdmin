package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceGroup} and its DTO {@link ServiceGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CategoryTypeMapper.class, ServiceRecordMapper.class})
public interface ServiceGroupMapper extends EntityMapper<ServiceGroupDTO, ServiceGroup> {

    @Mapping(source = "serviceGroupCategory.id", target = "serviceGroupCategoryId")
    @Mapping(source = "serviceGroupCategory.category", target = "serviceGroupCategoryCategory")
    @Mapping(source = "serviceGroupCategoryType.id", target = "serviceGroupCategoryTypeId")
    @Mapping(source = "serviceGroupCategoryType.categoryType", target = "serviceGroupCategoryTypeCategoryType")
    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRecord.serviceName", target = "serviceRecordServiceName")
    ServiceGroupDTO toDto(ServiceGroup serviceGroup);

    @Mapping(source = "serviceGroupCategoryId", target = "serviceGroupCategory")
    @Mapping(source = "serviceGroupCategoryTypeId", target = "serviceGroupCategoryType")
    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    ServiceGroup toEntity(ServiceGroupDTO serviceGroupDTO);

    default ServiceGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceGroup serviceGroup = new ServiceGroup();
        serviceGroup.setId(id);
        return serviceGroup;
    }
}
