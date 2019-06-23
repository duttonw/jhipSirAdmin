package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceDeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceDelivery} and its DTO {@link ServiceDeliveryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class})
public interface ServiceDeliveryMapper extends EntityMapper<ServiceDeliveryDTO, ServiceDelivery> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRecord.serviceName", target = "serviceRecordServiceName")
    ServiceDeliveryDTO toDto(ServiceDelivery serviceDelivery);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    @Mapping(target = "deliveryChannels", ignore = true)
    @Mapping(target = "removeDeliveryChannel", ignore = true)
    ServiceDelivery toEntity(ServiceDeliveryDTO serviceDeliveryDTO);

    default ServiceDelivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceDelivery serviceDelivery = new ServiceDelivery();
        serviceDelivery.setId(id);
        return serviceDelivery;
    }
}
