package au.gov.qld.sir.service.mapper;

import au.gov.qld.sir.domain.*;
import au.gov.qld.sir.service.dto.ServiceRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRecord} and its DTO {@link ServiceRecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceDeliveryOrganisationMapper.class, ServiceFranchiseMapper.class})
public interface ServiceRecordMapper extends EntityMapper<ServiceRecordDTO, ServiceRecord> {

    @Mapping(source = "deliveryOrg.id", target = "deliveryOrgId")
    @Mapping(source = "deliveryOrg.businessUnitName", target = "deliveryOrgBusinessUnitName")
    @Mapping(source = "parentService.id", target = "parentServiceId")
    @Mapping(source = "parentService.serviceName", target = "parentServiceServiceName")
    @Mapping(source = "serviceFranchise.id", target = "serviceFranchiseId")
    @Mapping(source = "serviceFranchise.franchiseName", target = "serviceFranchiseFranchiseName")
    ServiceRecordDTO toDto(ServiceRecord serviceRecord);

    @Mapping(source = "deliveryOrgId", target = "deliveryOrg")
    @Mapping(source = "parentServiceId", target = "parentService")
    @Mapping(source = "serviceFranchiseId", target = "serviceFranchise")
    @Mapping(target = "applicationServiceOverrides", ignore = true)
    @Mapping(target = "removeApplicationServiceOverride", ignore = true)
    @Mapping(target = "integrationMappings", ignore = true)
    @Mapping(target = "removeIntegrationMapping", ignore = true)
    @Mapping(target = "serviceRecords", ignore = true)
    @Mapping(target = "removeServiceRecord", ignore = true)
    @Mapping(target = "serviceDeliveries", ignore = true)
    @Mapping(target = "removeServiceDelivery", ignore = true)
    @Mapping(target = "serviceDeliveryForms", ignore = true)
    @Mapping(target = "removeServiceDeliveryForm", ignore = true)
    @Mapping(target = "serviceDescriptions", ignore = true)
    @Mapping(target = "removeServiceDescription", ignore = true)
    @Mapping(target = "serviceEvents", ignore = true)
    @Mapping(target = "removeServiceEvent", ignore = true)
    @Mapping(target = "serviceEvidences", ignore = true)
    @Mapping(target = "removeServiceEvidence", ignore = true)
    @Mapping(target = "serviceGroups", ignore = true)
    @Mapping(target = "removeServiceGroup", ignore = true)
    @Mapping(target = "serviceNames", ignore = true)
    @Mapping(target = "removeServiceName", ignore = true)
    @Mapping(target = "fromServices", ignore = true)
    @Mapping(target = "removeFromService", ignore = true)
    @Mapping(target = "toServices", ignore = true)
    @Mapping(target = "removeToService", ignore = true)
    @Mapping(target = "serviceSupportRoles", ignore = true)
    @Mapping(target = "removeServiceSupportRole", ignore = true)
    @Mapping(target = "serviceTagItems", ignore = true)
    @Mapping(target = "removeServiceTagItems", ignore = true)
    ServiceRecord toEntity(ServiceRecordDTO serviceRecordDTO);

    default ServiceRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setId(id);
        return serviceRecord;
    }
}
