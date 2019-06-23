package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceSupportRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceSupportRole} and its DTO {@link ServiceSupportRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class, ServiceRoleTypeMapper.class, ServiceSupportRoleContextTypeMapper.class})
public interface ServiceSupportRoleMapper extends EntityMapper<ServiceSupportRoleDTO, ServiceSupportRole> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRoleType.id", target = "serviceRoleTypeId")
    @Mapping(source = "serviceSupportContextType.id", target = "serviceSupportContextTypeId")
    ServiceSupportRoleDTO toDto(ServiceSupportRole serviceSupportRole);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    @Mapping(source = "serviceRoleTypeId", target = "serviceRoleType")
    @Mapping(source = "serviceSupportContextTypeId", target = "serviceSupportContextType")
    ServiceSupportRole toEntity(ServiceSupportRoleDTO serviceSupportRoleDTO);

    default ServiceSupportRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceSupportRole serviceSupportRole = new ServiceSupportRole();
        serviceSupportRole.setId(id);
        return serviceSupportRole;
    }
}
