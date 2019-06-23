package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OpeningHoursSpecification} and its DTO {@link OpeningHoursSpecificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AvailabilityHoursMapper.class})
public interface OpeningHoursSpecificationMapper extends EntityMapper<OpeningHoursSpecificationDTO, OpeningHoursSpecification> {

    @Mapping(source = "availabilityHours.id", target = "availabilityHoursId")
    OpeningHoursSpecificationDTO toDto(OpeningHoursSpecification openingHoursSpecification);

    @Mapping(source = "availabilityHoursId", target = "availabilityHours")
    OpeningHoursSpecification toEntity(OpeningHoursSpecificationDTO openingHoursSpecificationDTO);

    default OpeningHoursSpecification fromId(Long id) {
        if (id == null) {
            return null;
        }
        OpeningHoursSpecification openingHoursSpecification = new OpeningHoursSpecification();
        openingHoursSpecification.setId(id);
        return openingHoursSpecification;
    }
}
