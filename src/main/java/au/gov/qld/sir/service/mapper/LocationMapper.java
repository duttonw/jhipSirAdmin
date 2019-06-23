package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgencyMapper.class, AvailabilityHoursMapper.class, LocationTypeMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "agency.agencyCode", target = "agencyAgencyCode")
    @Mapping(source = "locationHours.id", target = "locationHoursId")
    @Mapping(source = "locationType.id", target = "locationTypeId")
    LocationDTO toDto(Location location);

    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "locationHoursId", target = "locationHours")
    @Mapping(source = "locationTypeId", target = "locationType")
    @Mapping(target = "deliveryChannels", ignore = true)
    @Mapping(target = "removeDeliveryChannel", ignore = true)
    @Mapping(target = "locationAddresses", ignore = true)
    @Mapping(target = "removeLocationAddress", ignore = true)
    @Mapping(target = "locationEmails", ignore = true)
    @Mapping(target = "removeLocationEmail", ignore = true)
    @Mapping(target = "locationPhones", ignore = true)
    @Mapping(target = "removeLocationPhone", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
