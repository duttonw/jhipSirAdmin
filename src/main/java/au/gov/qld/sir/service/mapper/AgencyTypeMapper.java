package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.AgencyTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AgencyType} and its DTO {@link AgencyTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyTypeMapper extends EntityMapper<AgencyTypeDTO, AgencyType> {


    @Mapping(target = "agencies", ignore = true)
    @Mapping(target = "removeAgency", ignore = true)
    AgencyType toEntity(AgencyTypeDTO agencyTypeDTO);

    default AgencyType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgencyType agencyType = new AgencyType();
        agencyType.setId(id);
        return agencyType;
    }
}
