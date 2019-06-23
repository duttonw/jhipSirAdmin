package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.DeliveryChannelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryChannel} and its DTO {@link DeliveryChannelDTO}.
 */
@Mapper(componentModel = "spring", uses = {AvailabilityHoursMapper.class, LocationMapper.class, ServiceDeliveryMapper.class})
public interface DeliveryChannelMapper extends EntityMapper<DeliveryChannelDTO, DeliveryChannel> {

    @Mapping(source = "deliveryHours.id", target = "deliveryHoursId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "serviceDelivery.id", target = "serviceDeliveryId")
    DeliveryChannelDTO toDto(DeliveryChannel deliveryChannel);

    @Mapping(source = "deliveryHoursId", target = "deliveryHours")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "serviceDeliveryId", target = "serviceDelivery")
    DeliveryChannel toEntity(DeliveryChannelDTO deliveryChannelDTO);

    default DeliveryChannel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryChannel deliveryChannel = new DeliveryChannel();
        deliveryChannel.setId(id);
        return deliveryChannel;
    }
}
