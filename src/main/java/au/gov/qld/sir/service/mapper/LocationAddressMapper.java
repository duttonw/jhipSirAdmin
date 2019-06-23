package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.LocationAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationAddress} and its DTO {@link LocationAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface LocationAddressMapper extends EntityMapper<LocationAddressDTO, LocationAddress> {

    @Mapping(source = "location.id", target = "locationId")
    LocationAddressDTO toDto(LocationAddress locationAddress);

    @Mapping(source = "locationId", target = "location")
    LocationAddress toEntity(LocationAddressDTO locationAddressDTO);

    default LocationAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.setId(id);
        return locationAddress;
    }
}
