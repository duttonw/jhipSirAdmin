package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationServiceOverrideAttribute} and its DTO {@link ApplicationServiceOverrideAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationServiceOverrideMapper.class})
public interface ApplicationServiceOverrideAttributeMapper extends EntityMapper<ApplicationServiceOverrideAttributeDTO, ApplicationServiceOverrideAttribute> {

    @Mapping(source = "applicationServiceOverride.id", target = "applicationServiceOverrideId")
    ApplicationServiceOverrideAttributeDTO toDto(ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute);

    @Mapping(source = "applicationServiceOverrideId", target = "applicationServiceOverride")
    ApplicationServiceOverrideAttribute toEntity(ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO);

    default ApplicationServiceOverrideAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute = new ApplicationServiceOverrideAttribute();
        applicationServiceOverrideAttribute.setId(id);
        return applicationServiceOverrideAttribute;
    }
}
