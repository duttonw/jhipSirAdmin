package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.LocationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationType} and its DTO {@link LocationTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationTypeMapper extends EntityMapper<LocationTypeDTO, LocationType> {


    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "removeLocation", ignore = true)
    LocationType toEntity(LocationTypeDTO locationTypeDTO);

    default LocationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationType locationType = new LocationType();
        locationType.setId(id);
        return locationType;
    }
}
