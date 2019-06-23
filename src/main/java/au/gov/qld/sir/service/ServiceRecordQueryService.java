package au.gov.qld.sir.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceRecordRepository;
import au.gov.qld.sir.service.dto.ServiceRecordCriteria;
import au.gov.qld.sir.service.dto.ServiceRecordDTO;
import au.gov.qld.sir.service.mapper.ServiceRecordMapper;

/**
 * Service for executing complex queries for {@link ServiceRecord} entities in the database.
 * The main input is a {@link ServiceRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceRecordDTO} or a {@link Page} of {@link ServiceRecordDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceRecordQueryService extends QueryService<ServiceRecord> {

    private final Logger log = LoggerFactory.getLogger(ServiceRecordQueryService.class);

    private final ServiceRecordRepository serviceRecordRepository;

    private final ServiceRecordMapper serviceRecordMapper;

    public ServiceRecordQueryService(ServiceRecordRepository serviceRecordRepository, ServiceRecordMapper serviceRecordMapper) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.serviceRecordMapper = serviceRecordMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRecordDTO> findByCriteria(ServiceRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceRecord> specification = createSpecification(criteria);
        return serviceRecordMapper.toDto(serviceRecordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRecordDTO> findByCriteria(ServiceRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceRecord> specification = createSpecification(criteria);
        return serviceRecordRepository.findAll(specification, page)
            .map(serviceRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceRecord> specification = createSpecification(criteria);
        return serviceRecordRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceRecord> createSpecification(ServiceRecordCriteria criteria) {
        Specification<ServiceRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceRecord_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceRecord_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceRecord_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceRecord_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceRecord_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceRecord_.version));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), ServiceRecord_.active));
            }
            if (criteria.getEligibility() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEligibility(), ServiceRecord_.eligibility));
            }
            if (criteria.getFees() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFees(), ServiceRecord_.fees));
            }
            if (criteria.getGroupHeader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupHeader(), ServiceRecord_.groupHeader));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupId(), ServiceRecord_.groupId));
            }
            if (criteria.getInteractionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInteractionId(), ServiceRecord_.interactionId));
            }
            if (criteria.getKeywords() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeywords(), ServiceRecord_.keywords));
            }
            if (criteria.getPreRequisites() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreRequisites(), ServiceRecord_.preRequisites));
            }
            if (criteria.getQgsServiceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQgsServiceId(), ServiceRecord_.qgsServiceId));
            }
            if (criteria.getReferenceUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceUrl(), ServiceRecord_.referenceUrl));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), ServiceRecord_.serviceName));
            }
            if (criteria.getValidatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidatedDate(), ServiceRecord_.validatedDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ServiceRecord_.description));
            }
            if (criteria.getPreRequisitesNew() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreRequisitesNew(), ServiceRecord_.preRequisitesNew));
            }
            if (criteria.getReferenceUrlNew() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceUrlNew(), ServiceRecord_.referenceUrlNew));
            }
            if (criteria.getEligibilityNew() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEligibilityNew(), ServiceRecord_.eligibilityNew));
            }
            if (criteria.getServiceContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceContext(), ServiceRecord_.serviceContext));
            }
            if (criteria.getLongDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongDescription(), ServiceRecord_.longDescription));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ServiceRecord_.name));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ServiceRecord_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ServiceRecord_.endDate));
            }
            if (criteria.getRoadmapLoginRequired() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapLoginRequired(), ServiceRecord_.roadmapLoginRequired));
            }
            if (criteria.getRoadmapCustomerIdRequired() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapCustomerIdRequired(), ServiceRecord_.roadmapCustomerIdRequired));
            }
            if (criteria.getRoadmapCustomerDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapCustomerDetails(), ServiceRecord_.roadmapCustomerDetails));
            }
            if (criteria.getRoadmapImproveIntention() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapImproveIntention(), ServiceRecord_.roadmapImproveIntention));
            }
            if (criteria.getRoadmapImproveFuture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapImproveFuture(), ServiceRecord_.roadmapImproveFuture));
            }
            if (criteria.getRoadmapImproveType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapImproveType(), ServiceRecord_.roadmapImproveType));
            }
            if (criteria.getRoadmapImproveWhen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapImproveWhen(), ServiceRecord_.roadmapImproveWhen));
            }
            if (criteria.getRoadmapImproveHow() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapImproveHow(), ServiceRecord_.roadmapImproveHow));
            }
            if (criteria.getRoadmapMaturityCurrent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapMaturityCurrent(), ServiceRecord_.roadmapMaturityCurrent));
            }
            if (criteria.getRoadmapMaturityDesired() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapMaturityDesired(), ServiceRecord_.roadmapMaturityDesired));
            }
            if (criteria.getRoadmapComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoadmapComments(), ServiceRecord_.roadmapComments));
            }
            if (criteria.getHowTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHowTo(), ServiceRecord_.howTo));
            }
            if (criteria.getDeliveryOrgId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryOrgId(),
                    root -> root.join(ServiceRecord_.deliveryOrg, JoinType.LEFT).get(ServiceDeliveryOrganisation_.id)));
            }
            if (criteria.getParentServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentServiceId(),
                    root -> root.join(ServiceRecord_.parentService, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getServiceFranchiseId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceFranchiseId(),
                    root -> root.join(ServiceRecord_.serviceFranchise, JoinType.LEFT).get(ServiceFranchise_.id)));
            }
            if (criteria.getApplicationServiceOverrideId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideId(),
                    root -> root.join(ServiceRecord_.applicationServiceOverrides, JoinType.LEFT).get(ApplicationServiceOverride_.id)));
            }
            if (criteria.getIntegrationMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getIntegrationMappingId(),
                    root -> root.join(ServiceRecord_.integrationMappings, JoinType.LEFT).get(IntegrationMapping_.id)));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceRecord_.serviceRecords, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getServiceDeliveryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceDeliveryId(),
                    root -> root.join(ServiceRecord_.serviceDeliveries, JoinType.LEFT).get(ServiceDelivery_.id)));
            }
            if (criteria.getServiceDeliveryFormId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceDeliveryFormId(),
                    root -> root.join(ServiceRecord_.serviceDeliveryForms, JoinType.LEFT).get(ServiceDeliveryForm_.id)));
            }
            if (criteria.getServiceDescriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceDescriptionId(),
                    root -> root.join(ServiceRecord_.serviceDescriptions, JoinType.LEFT).get(ServiceDescription_.id)));
            }
            if (criteria.getServiceEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceEventId(),
                    root -> root.join(ServiceRecord_.serviceEvents, JoinType.LEFT).get(ServiceEvent_.id)));
            }
            if (criteria.getServiceEvidenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceEvidenceId(),
                    root -> root.join(ServiceRecord_.serviceEvidences, JoinType.LEFT).get(ServiceEvidence_.id)));
            }
            if (criteria.getServiceGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceGroupId(),
                    root -> root.join(ServiceRecord_.serviceGroups, JoinType.LEFT).get(ServiceGroup_.id)));
            }
            if (criteria.getServiceNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceNameId(),
                    root -> root.join(ServiceRecord_.serviceNames, JoinType.LEFT).get(ServiceName_.id)));
            }
            if (criteria.getFromServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromServiceId(),
                    root -> root.join(ServiceRecord_.fromServices, JoinType.LEFT).get(ServiceRelationship_.id)));
            }
            if (criteria.getToServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getToServiceId(),
                    root -> root.join(ServiceRecord_.toServices, JoinType.LEFT).get(ServiceRelationship_.id)));
            }
            if (criteria.getServiceSupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceSupportRoleId(),
                    root -> root.join(ServiceRecord_.serviceSupportRoles, JoinType.LEFT).get(ServiceSupportRole_.id)));
            }
            if (criteria.getServiceTagItemsId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceTagItemsId(),
                    root -> root.join(ServiceRecord_.serviceTagItems, JoinType.LEFT).get(ServiceTagItems_.id)));
            }
        }
        return specification;
    }
}
