package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceEvent} and its DTO {@link ServiceEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class, ServiceEventTypeMapper.class})
public interface ServiceEventMapper extends EntityMapper<ServiceEventDTO, ServiceEvent> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceEventType.id", target = "serviceEventTypeId")
    ServiceEventDTO toDto(ServiceEvent serviceEvent);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    @Mapping(source = "serviceEventTypeId", target = "serviceEventType")
    ServiceEvent toEntity(ServiceEventDTO serviceEventDTO);

    default ServiceEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceEvent serviceEvent = new ServiceEvent();
        serviceEvent.setId(id);
        return serviceEvent;
    }
}
