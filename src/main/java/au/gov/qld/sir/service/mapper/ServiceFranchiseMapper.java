package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceFranchiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceFranchise} and its DTO {@link ServiceFranchiseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceFranchiseMapper extends EntityMapper<ServiceFranchiseDTO, ServiceFranchise> {


    @Mapping(target = "serviceRecords", ignore = true)
    @Mapping(target = "removeServiceRecord", ignore = true)
    ServiceFranchise toEntity(ServiceFranchiseDTO serviceFranchiseDTO);

    default ServiceFranchise fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceFranchise serviceFranchise = new ServiceFranchise();
        serviceFranchise.setId(id);
        return serviceFranchise;
    }
}
