package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.AgencySupportRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AgencySupportRole} and its DTO {@link AgencySupportRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgencyMapper.class, ServiceRoleTypeMapper.class, AgencySupportRoleContextTypeMapper.class})
public interface AgencySupportRoleMapper extends EntityMapper<AgencySupportRoleDTO, AgencySupportRole> {

    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "agency.agencyCode", target = "agencyAgencyCode")
    @Mapping(source = "agencyRoleType.id", target = "agencyRoleTypeId")
    @Mapping(source = "agencyRoleType.serviceRole", target = "agencyRoleTypeServiceRole")
    @Mapping(source = "agencySupportContextType.id", target = "agencySupportContextTypeId")
    @Mapping(source = "agencySupportContextType.context", target = "agencySupportContextTypeContext")
    AgencySupportRoleDTO toDto(AgencySupportRole agencySupportRole);

    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "agencyRoleTypeId", target = "agencyRoleType")
    @Mapping(source = "agencySupportContextTypeId", target = "agencySupportContextType")
    AgencySupportRole toEntity(AgencySupportRoleDTO agencySupportRoleDTO);

    default AgencySupportRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgencySupportRole agencySupportRole = new AgencySupportRole();
        agencySupportRole.setId(id);
        return agencySupportRole;
    }
}
