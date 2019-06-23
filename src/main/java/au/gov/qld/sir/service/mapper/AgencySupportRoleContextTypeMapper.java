package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AgencySupportRoleContextType} and its DTO {@link AgencySupportRoleContextTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencySupportRoleContextTypeMapper extends EntityMapper<AgencySupportRoleContextTypeDTO, AgencySupportRoleContextType> {


    @Mapping(target = "agencySupportRoles", ignore = true)
    @Mapping(target = "removeAgencySupportRole", ignore = true)
    AgencySupportRoleContextType toEntity(AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO);

    default AgencySupportRoleContextType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgencySupportRoleContextType agencySupportRoleContextType = new AgencySupportRoleContextType();
        agencySupportRoleContextType.setId(id);
        return agencySupportRoleContextType;
    }
}
