package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceRoleTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRoleType} and its DTO {@link ServiceRoleTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceRoleTypeMapper extends EntityMapper<ServiceRoleTypeDTO, ServiceRoleType> {


    @Mapping(target = "agencySupportRoles", ignore = true)
    @Mapping(target = "removeAgencySupportRole", ignore = true)
    @Mapping(target = "serviceSupportRoles", ignore = true)
    @Mapping(target = "removeServiceSupportRole", ignore = true)
    ServiceRoleType toEntity(ServiceRoleTypeDTO serviceRoleTypeDTO);

    default ServiceRoleType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRoleType serviceRoleType = new ServiceRoleType();
        serviceRoleType.setId(id);
        return serviceRoleType;
    }
}
