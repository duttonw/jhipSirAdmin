package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceDescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceDescription} and its DTO {@link ServiceDescriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class})
public interface ServiceDescriptionMapper extends EntityMapper<ServiceDescriptionDTO, ServiceDescription> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    ServiceDescriptionDTO toDto(ServiceDescription serviceDescription);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    ServiceDescription toEntity(ServiceDescriptionDTO serviceDescriptionDTO);

    default ServiceDescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setId(id);
        return serviceDescription;
    }
}
