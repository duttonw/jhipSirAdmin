package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.AvailabilityHoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AvailabilityHours} and its DTO {@link AvailabilityHoursDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AvailabilityHoursMapper extends EntityMapper<AvailabilityHoursDTO, AvailabilityHours> {


    @Mapping(target = "deliveryChannels", ignore = true)
    @Mapping(target = "removeDeliveryChannel", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "removeLocation", ignore = true)
    @Mapping(target = "openingHoursSpecifications", ignore = true)
    @Mapping(target = "removeOpeningHoursSpecification", ignore = true)
    AvailabilityHours toEntity(AvailabilityHoursDTO availabilityHoursDTO);

    default AvailabilityHours fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvailabilityHours availabilityHours = new AvailabilityHours();
        availabilityHours.setId(id);
        return availabilityHours;
    }
}
