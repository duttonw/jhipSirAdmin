package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.IntegrationMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegrationMapping} and its DTO {@link IntegrationMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgencyMapper.class, ServiceRecordMapper.class})
public interface IntegrationMappingMapper extends EntityMapper<IntegrationMappingDTO, IntegrationMapping> {

    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "agency.agencyCode", target = "agencyAgencyCode")
    @Mapping(source = "serviceRecord.id", target = "serviceRecordId")
    @Mapping(source = "serviceRecord.serviceName", target = "serviceRecordServiceName")
    IntegrationMappingDTO toDto(IntegrationMapping integrationMapping);

    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "serviceRecordId", target = "serviceRecord")
    IntegrationMapping toEntity(IntegrationMappingDTO integrationMappingDTO);

    default IntegrationMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        IntegrationMapping integrationMapping = new IntegrationMapping();
        integrationMapping.setId(id);
        return integrationMapping;
    }
}
