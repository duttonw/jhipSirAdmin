package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.AgencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agency} and its DTO {@link AgencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgencyTypeMapper.class})
public interface AgencyMapper extends EntityMapper<AgencyDTO, Agency> {

    @Mapping(source = "agencyType.id", target = "agencyTypeId")
    @Mapping(source = "agencyType.agencyTypeName", target = "agencyTypeAgencyTypeName")
    AgencyDTO toDto(Agency agency);

    @Mapping(source = "agencyTypeId", target = "agencyType")
    @Mapping(target = "agencySupportRoles", ignore = true)
    @Mapping(target = "removeAgencySupportRole", ignore = true)
    @Mapping(target = "integrationMappings", ignore = true)
    @Mapping(target = "removeIntegrationMapping", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "removeLocation", ignore = true)
    @Mapping(target = "serviceDeliveryOrganisations", ignore = true)
    @Mapping(target = "removeServiceDeliveryOrganisation", ignore = true)
    Agency toEntity(AgencyDTO agencyDTO);

    default Agency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agency agency = new Agency();
        agency.setId(id);
        return agency;
    }
}
