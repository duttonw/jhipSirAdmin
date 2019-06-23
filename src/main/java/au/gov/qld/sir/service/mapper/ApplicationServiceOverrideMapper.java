package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationServiceOverride} and its DTO {@link ApplicationServiceOverrideDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class, ApplicationMapper.class})
public interface ApplicationServiceOverrideMapper extends EntityMapper<ApplicationServiceOverrideDTO, ApplicationServiceOverride> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRecord.serviceName", target = "serviceRecordServiceName")
    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "application.name", target = "applicationName")
    ApplicationServiceOverrideDTO toDto(ApplicationServiceOverride applicationServiceOverride);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    @Mapping(source = "applicationId", target = "application")
    @Mapping(target = "applicationServiceOverrideAttributes", ignore = true)
    @Mapping(target = "removeApplicationServiceOverrideAttribute", ignore = true)
    @Mapping(target = "applicationServiceOverrideTagItems", ignore = true)
    @Mapping(target = "removeApplicationServiceOverrideTagItems", ignore = true)
    ApplicationServiceOverride toEntity(ApplicationServiceOverrideDTO applicationServiceOverrideDTO);

    default ApplicationServiceOverride fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationServiceOverride applicationServiceOverride = new ApplicationServiceOverride();
        applicationServiceOverride.setId(id);
        return applicationServiceOverride;
    }
}
