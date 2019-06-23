package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationServiceOverrideTagItems} and its DTO {@link ApplicationServiceOverrideTagItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationServiceOverrideMapper.class, ApplicationServiceOverrideTagMapper.class})
public interface ApplicationServiceOverrideTagItemsMapper extends EntityMapper<ApplicationServiceOverrideTagItemsDTO, ApplicationServiceOverrideTagItems> {

    @Mapping(source = "applicationServiceOverride.id", target = "applicationServiceOverrideId")
    @Mapping(source = "applicationServiceOverrideTag.id", target = "applicationServiceOverrideTagId")
    ApplicationServiceOverrideTagItemsDTO toDto(ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems);

    @Mapping(source = "applicationServiceOverrideId", target = "applicationServiceOverride")
    @Mapping(source = "applicationServiceOverrideTagId", target = "applicationServiceOverrideTag")
    ApplicationServiceOverrideTagItems toEntity(ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO);

    default ApplicationServiceOverrideTagItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = new ApplicationServiceOverrideTagItems();
        applicationServiceOverrideTagItems.setId(id);
        return applicationServiceOverrideTagItems;
    }
}
