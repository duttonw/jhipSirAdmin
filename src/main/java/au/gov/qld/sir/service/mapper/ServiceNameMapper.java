package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceNameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceName} and its DTO {@link ServiceNameDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRecordMapper.class})
public interface ServiceNameMapper extends EntityMapper<ServiceNameDTO, ServiceName> {

    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRecord.serviceName", target = "serviceRecordServiceName")
    ServiceNameDTO toDto(ServiceName serviceName);

    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    ServiceName toEntity(ServiceNameDTO serviceNameDTO);

    default ServiceName fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceName serviceName = new ServiceName();
        serviceName.setId(id);
        return serviceName;
    }
}
