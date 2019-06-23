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

import au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ApplicationServiceOverrideAttributeRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeCriteria;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideAttributeMapper;

/**
 * Service for executing complex queries for {@link ApplicationServiceOverrideAttribute} entities in the database.
 * The main input is a {@link ApplicationServiceOverrideAttributeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationServiceOverrideAttributeDTO} or a {@link Page} of {@link ApplicationServiceOverrideAttributeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationServiceOverrideAttributeQueryService extends QueryService<ApplicationServiceOverrideAttribute> {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideAttributeQueryService.class);

    private final ApplicationServiceOverrideAttributeRepository applicationServiceOverrideAttributeRepository;

    private final ApplicationServiceOverrideAttributeMapper applicationServiceOverrideAttributeMapper;

    public ApplicationServiceOverrideAttributeQueryService(ApplicationServiceOverrideAttributeRepository applicationServiceOverrideAttributeRepository, ApplicationServiceOverrideAttributeMapper applicationServiceOverrideAttributeMapper) {
        this.applicationServiceOverrideAttributeRepository = applicationServiceOverrideAttributeRepository;
        this.applicationServiceOverrideAttributeMapper = applicationServiceOverrideAttributeMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationServiceOverrideAttributeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationServiceOverrideAttributeDTO> findByCriteria(ApplicationServiceOverrideAttributeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideAttribute> specification = createSpecification(criteria);
        return applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttributeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationServiceOverrideAttributeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideAttributeDTO> findByCriteria(ApplicationServiceOverrideAttributeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationServiceOverrideAttribute> specification = createSpecification(criteria);
        return applicationServiceOverrideAttributeRepository.findAll(specification, page)
            .map(applicationServiceOverrideAttributeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationServiceOverrideAttributeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideAttribute> specification = createSpecification(criteria);
        return applicationServiceOverrideAttributeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ApplicationServiceOverrideAttribute> createSpecification(ApplicationServiceOverrideAttributeCriteria criteria) {
        Specification<ApplicationServiceOverrideAttribute> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ApplicationServiceOverrideAttribute_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ApplicationServiceOverrideAttribute_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ApplicationServiceOverrideAttribute_.value));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationServiceOverrideAttribute_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ApplicationServiceOverrideAttribute_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ApplicationServiceOverrideAttribute_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ApplicationServiceOverrideAttribute_.modifiedDateTime));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ApplicationServiceOverrideAttribute_.migratedBy));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApplicationServiceOverrideAttribute_.version));
            }
            if (criteria.getApplicationServiceOverrideId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideId(),
                    root -> root.join(ApplicationServiceOverrideAttribute_.applicationServiceOverride, JoinType.LEFT).get(ApplicationServiceOverride_.id)));
            }
        }
        return specification;
    }
}
