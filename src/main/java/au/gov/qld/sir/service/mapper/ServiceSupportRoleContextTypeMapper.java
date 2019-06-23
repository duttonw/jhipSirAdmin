package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceSupportRoleContextType} and its DTO {@link ServiceSupportRoleContextTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceSupportRoleContextTypeMapper extends EntityMapper<ServiceSupportRoleContextTypeDTO, ServiceSupportRoleContextType> {


    @Mapping(target = "serviceSupportRoles", ignore = true)
    @Mapping(target = "removeServiceSupportRole", ignore = true)
    ServiceSupportRoleContextType toEntity(ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO);

    default ServiceSupportRoleContextType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceSupportRoleContextType serviceSupportRoleContextType = new ServiceSupportRoleContextType();
        serviceSupportRoleContextType.setId(id);
        return serviceSupportRoleContextType;
    }
}
