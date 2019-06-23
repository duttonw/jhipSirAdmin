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

import au.gov.qld.sir.domain.ServiceRelationship;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceRelationshipRepository;
import au.gov.qld.sir.service.dto.ServiceRelationshipCriteria;
import au.gov.qld.sir.service.dto.ServiceRelationshipDTO;
import au.gov.qld.sir.service.mapper.ServiceRelationshipMapper;

/**
 * Service for executing complex queries for {@link ServiceRelationship} entities in the database.
 * The main input is a {@link ServiceRelationshipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceRelationshipDTO} or a {@link Page} of {@link ServiceRelationshipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceRelationshipQueryService extends QueryService<ServiceRelationship> {

    private final Logger log = LoggerFactory.getLogger(ServiceRelationshipQueryService.class);

    private final ServiceRelationshipRepository serviceRelationshipRepository;

    private final ServiceRelationshipMapper serviceRelationshipMapper;

    public ServiceRelationshipQueryService(ServiceRelationshipRepository serviceRelationshipRepository, ServiceRelationshipMapper serviceRelationshipMapper) {
        this.serviceRelationshipRepository = serviceRelationshipRepository;
        this.serviceRelationshipMapper = serviceRelationshipMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRelationshipDTO> findByCriteria(ServiceRelationshipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceRelationship> specification = createSpecification(criteria);
        return serviceRelationshipMapper.toDto(serviceRelationshipRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRelationshipDTO> findByCriteria(ServiceRelationshipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceRelationship> specification = createSpecification(criteria);
        return serviceRelationshipRepository.findAll(specification, page)
            .map(serviceRelationshipMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceRelationshipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceRelationship> specification = createSpecification(criteria);
        return serviceRelationshipRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceRelationship> createSpecification(ServiceRelationshipCriteria criteria) {
        Specification<ServiceRelationship> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceRelationship_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceRelationship_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceRelationship_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceRelationship_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceRelationship_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceRelationship_.version));
            }
            if (criteria.getRelationship() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelationship(), ServiceRelationship_.relationship));
            }
            if (criteria.getFromServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromServiceId(),
                    root -> root.join(ServiceRelationship_.fromService, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getToServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getToServiceId(),
                    root -> root.join(ServiceRelationship_.toService, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
