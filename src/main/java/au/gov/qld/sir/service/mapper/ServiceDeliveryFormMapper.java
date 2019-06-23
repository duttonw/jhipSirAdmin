package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceDeliveryForm} and its DTO {@link ServiceDeliveryFormDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class})
public interface ServiceDeliveryFormMapper extends EntityMapper<ServiceDeliveryFormDTO, ServiceDeliveryForm> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    ServiceDeliveryFormDTO toDto(ServiceDeliveryForm serviceDeliveryForm);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    ServiceDeliveryForm toEntity(ServiceDeliveryFormDTO serviceDeliveryFormDTO);

    default ServiceDeliveryForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceDeliveryForm serviceDeliveryForm = new ServiceDeliveryForm();
        serviceDeliveryForm.setId(id);
        return serviceDeliveryForm;
    }
}
