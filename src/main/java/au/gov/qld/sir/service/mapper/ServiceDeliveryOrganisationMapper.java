package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceDeliveryOrganisation} and its DTO {@link ServiceDeliveryOrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgencyMapper.class})
public interface ServiceDeliveryOrganisationMapper extends EntityMapper<ServiceDeliveryOrganisationDTO, ServiceDeliveryOrganisation> {

    @Mapping(source = "agency.id", target = "agencyId")
    ServiceDeliveryOrganisationDTO toDto(ServiceDeliveryOrganisation serviceDeliveryOrganisation);

    @Mapping(source = "agencyId", target = "agency")
    @Mapping(target = "serviceRecords", ignore = true)
    @Mapping(target = "removeServiceRecord", ignore = true)
    ServiceDeliveryOrganisation toEntity(ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO);

    default ServiceDeliveryOrganisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceDeliveryOrganisation serviceDeliveryOrganisation = new ServiceDeliveryOrganisation();
        serviceDeliveryOrganisation.setId(id);
        return serviceDeliveryOrganisation;
    }
}
