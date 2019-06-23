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

import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagItemsRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsCriteria;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagItemsMapper;

/**
 * Service for executing complex queries for {@link ApplicationServiceOverrideTagItems} entities in the database.
 * The main input is a {@link ApplicationServiceOverrideTagItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationServiceOverrideTagItemsDTO} or a {@link Page} of {@link ApplicationServiceOverrideTagItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationServiceOverrideTagItemsQueryService extends QueryService<ApplicationServiceOverrideTagItems> {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagItemsQueryService.class);

    private final ApplicationServiceOverrideTagItemsRepository applicationServiceOverrideTagItemsRepository;

    private final ApplicationServiceOverrideTagItemsMapper applicationServiceOverrideTagItemsMapper;

    public ApplicationServiceOverrideTagItemsQueryService(ApplicationServiceOverrideTagItemsRepository applicationServiceOverrideTagItemsRepository, ApplicationServiceOverrideTagItemsMapper applicationServiceOverrideTagItemsMapper) {
        this.applicationServiceOverrideTagItemsRepository = applicationServiceOverrideTagItemsRepository;
        this.applicationServiceOverrideTagItemsMapper = applicationServiceOverrideTagItemsMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationServiceOverrideTagItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationServiceOverrideTagItemsDTO> findByCriteria(ApplicationServiceOverrideTagItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideTagItems> specification = createSpecification(criteria);
        return applicationServiceOverrideTagItemsMapper.toDto(applicationServiceOverrideTagItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationServiceOverrideTagItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideTagItemsDTO> findByCriteria(ApplicationServiceOverrideTagItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationServiceOverrideTagItems> specification = createSpecification(criteria);
        return applicationServiceOverrideTagItemsRepository.findAll(specification, page)
            .map(applicationServiceOverrideTagItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationServiceOverrideTagItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideTagItems> specification = createSpecification(criteria);
        return applicationServiceOverrideTagItemsRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ApplicationServiceOverrideTagItems> createSpecification(ApplicationServiceOverrideTagItemsCriteria criteria) {
        Specification<ApplicationServiceOverrideTagItems> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ApplicationServiceOverrideTagItems_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationServiceOverrideTagItems_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ApplicationServiceOverrideTagItems_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ApplicationServiceOverrideTagItems_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ApplicationServiceOverrideTagItems_.modifiedDateTime));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ApplicationServiceOverrideTagItems_.migratedBy));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApplicationServiceOverrideTagItems_.version));
            }
            if (criteria.getApplicationServiceOverrideId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideId(),
                    root -> root.join(ApplicationServiceOverrideTagItems_.applicationServiceOverride, JoinType.LEFT).get(ApplicationServiceOverride_.id)));
            }
            if (criteria.getApplicationServiceOverrideTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideTagId(),
                    root -> root.join(ApplicationServiceOverrideTagItems_.applicationServiceOverrideTag, JoinType.LEFT).get(ApplicationServiceOverrideTag_.id)));
            }
        }
        return specification;
    }
}
