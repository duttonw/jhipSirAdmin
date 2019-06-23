package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.LocationEmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationEmail} and its DTO {@link LocationEmailDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface LocationEmailMapper extends EntityMapper<LocationEmailDTO, LocationEmail> {

    @Mapping(source = "location.id", target = "locationId")
    LocationEmailDTO toDto(LocationEmail locationEmail);

    @Mapping(source = "locationId", target = "location")
    LocationEmail toEntity(LocationEmailDTO locationEmailDTO);

    default LocationEmail fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationEmail locationEmail = new LocationEmail();
        locationEmail.setId(id);
        return locationEmail;
    }
}
