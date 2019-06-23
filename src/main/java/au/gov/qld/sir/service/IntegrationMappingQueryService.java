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

import au.gov.qld.sir.domain.IntegrationMapping;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.IntegrationMappingRepository;
import au.gov.qld.sir.service.dto.IntegrationMappingCriteria;
import au.gov.qld.sir.service.dto.IntegrationMappingDTO;
import au.gov.qld.sir.service.mapper.IntegrationMappingMapper;

/**
 * Service for executing complex queries for {@link IntegrationMapping} entities in the database.
 * The main input is a {@link IntegrationMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntegrationMappingDTO} or a {@link Page} of {@link IntegrationMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntegrationMappingQueryService extends QueryService<IntegrationMapping> {

    private final Logger log = LoggerFactory.getLogger(IntegrationMappingQueryService.class);

    private final IntegrationMappingRepository integrationMappingRepository;

    private final IntegrationMappingMapper integrationMappingMapper;

    public IntegrationMappingQueryService(IntegrationMappingRepository integrationMappingRepository, IntegrationMappingMapper integrationMappingMapper) {
        this.integrationMappingRepository = integrationMappingRepository;
        this.integrationMappingMapper = integrationMappingMapper;
    }

    /**
     * Return a {@link List} of {@link IntegrationMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntegrationMappingDTO> findByCriteria(IntegrationMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IntegrationMapping> specification = createSpecification(criteria);
        return integrationMappingMapper.toDto(integrationMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IntegrationMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntegrationMappingDTO> findByCriteria(IntegrationMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IntegrationMapping> specification = createSpecification(criteria);
        return integrationMappingRepository.findAll(specification, page)
            .map(integrationMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntegrationMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IntegrationMapping> specification = createSpecification(criteria);
        return integrationMappingRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<IntegrationMapping> createSpecification(IntegrationMappingCriteria criteria) {
        Specification<IntegrationMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), IntegrationMapping_.id));
            }
            if (criteria.getAgencyServiceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgencyServiceId(), IntegrationMapping_.agencyServiceId));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), IntegrationMapping_.serviceName));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), IntegrationMapping_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), IntegrationMapping_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), IntegrationMapping_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), IntegrationMapping_.modifiedDateTime));
            }
            if (criteria.getAgencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyId(),
                    root -> root.join(IntegrationMapping_.agency, JoinType.LEFT).get(Agency_.id)));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(IntegrationMapping_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
