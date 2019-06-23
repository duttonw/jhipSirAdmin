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

import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceDeliveryOrganisationRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationCriteria;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryOrganisationMapper;

/**
 * Service for executing complex queries for {@link ServiceDeliveryOrganisation} entities in the database.
 * The main input is a {@link ServiceDeliveryOrganisationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceDeliveryOrganisationDTO} or a {@link Page} of {@link ServiceDeliveryOrganisationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceDeliveryOrganisationQueryService extends QueryService<ServiceDeliveryOrganisation> {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryOrganisationQueryService.class);

    private final ServiceDeliveryOrganisationRepository serviceDeliveryOrganisationRepository;

    private final ServiceDeliveryOrganisationMapper serviceDeliveryOrganisationMapper;

    public ServiceDeliveryOrganisationQueryService(ServiceDeliveryOrganisationRepository serviceDeliveryOrganisationRepository, ServiceDeliveryOrganisationMapper serviceDeliveryOrganisationMapper) {
        this.serviceDeliveryOrganisationRepository = serviceDeliveryOrganisationRepository;
        this.serviceDeliveryOrganisationMapper = serviceDeliveryOrganisationMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceDeliveryOrganisationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDeliveryOrganisationDTO> findByCriteria(ServiceDeliveryOrganisationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceDeliveryOrganisation> specification = createSpecification(criteria);
        return serviceDeliveryOrganisationMapper.toDto(serviceDeliveryOrganisationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceDeliveryOrganisationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryOrganisationDTO> findByCriteria(ServiceDeliveryOrganisationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceDeliveryOrganisation> specification = createSpecification(criteria);
        return serviceDeliveryOrganisationRepository.findAll(specification, page)
            .map(serviceDeliveryOrganisationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceDeliveryOrganisationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceDeliveryOrganisation> specification = createSpecification(criteria);
        return serviceDeliveryOrganisationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceDeliveryOrganisation> createSpecification(ServiceDeliveryOrganisationCriteria criteria) {
        Specification<ServiceDeliveryOrganisation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceDeliveryOrganisation_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceDeliveryOrganisation_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceDeliveryOrganisation_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceDeliveryOrganisation_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceDeliveryOrganisation_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceDeliveryOrganisation_.version));
            }
            if (criteria.getBusinessUnitName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessUnitName(), ServiceDeliveryOrganisation_.businessUnitName));
            }
            if (criteria.getAgencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyId(),
                    root -> root.join(ServiceDeliveryOrganisation_.agency, JoinType.LEFT).get(Agency_.id)));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceDeliveryOrganisation_.serviceRecords, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
