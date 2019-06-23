package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Application} and its DTO {@link ApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {


    @Mapping(target = "applicationServiceOverrides", ignore = true)
    @Mapping(target = "removeApplicationServiceOverride", ignore = true)
    Application toEntity(ApplicationDTO applicationDTO);

    default Application fromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
