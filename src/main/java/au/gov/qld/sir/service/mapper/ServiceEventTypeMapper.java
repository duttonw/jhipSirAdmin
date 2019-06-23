package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceEventTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceEventType} and its DTO {@link ServiceEventTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceEventTypeMapper extends EntityMapper<ServiceEventTypeDTO, ServiceEventType> {


    @Mapping(target = "serviceEvents", ignore = true)
    @Mapping(target = "removeServiceEvent", ignore = true)
    ServiceEventType toEntity(ServiceEventTypeDTO serviceEventTypeDTO);

    default ServiceEventType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceEventType serviceEventType = new ServiceEventType();
        serviceEventType.setId(id);
        return serviceEventType;
    }
}
