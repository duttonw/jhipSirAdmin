package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.LocationPhoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationPhone} and its DTO {@link LocationPhoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface LocationPhoneMapper extends EntityMapper<LocationPhoneDTO, LocationPhone> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.locationName", target = "locationLocationName")
    LocationPhoneDTO toDto(LocationPhone locationPhone);

    @Mapping(source = "locationId", target = "location")
    LocationPhone toEntity(LocationPhoneDTO locationPhoneDTO);

    default LocationPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationPhone locationPhone = new LocationPhone();
        locationPhone.setId(id);
        return locationPhone;
    }
}
