package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationServiceOverrideTag} and its DTO {@link ApplicationServiceOverrideTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationServiceOverrideTagMapper extends EntityMapper<ApplicationServiceOverrideTagDTO, ApplicationServiceOverrideTag> {

    @Mapping(source = "parent.id", target = "parentId")
    ApplicationServiceOverrideTagDTO toDto(ApplicationServiceOverrideTag applicationServiceOverrideTag);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "applicationServiceOverrideTagItems", ignore = true)
    @Mapping(target = "removeApplicationServiceOverrideTagItems", ignore = true)
    ApplicationServiceOverrideTag toEntity(ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO);

    default ApplicationServiceOverrideTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationServiceOverrideTag applicationServiceOverrideTag = new ApplicationServiceOverrideTag();
        applicationServiceOverrideTag.setId(id);
        return applicationServiceOverrideTag;
    }
}
