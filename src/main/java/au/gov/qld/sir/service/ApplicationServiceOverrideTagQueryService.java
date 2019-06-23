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

import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagCriteria;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagMapper;

/**
 * Service for executing complex queries for {@link ApplicationServiceOverrideTag} entities in the database.
 * The main input is a {@link ApplicationServiceOverrideTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationServiceOverrideTagDTO} or a {@link Page} of {@link ApplicationServiceOverrideTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationServiceOverrideTagQueryService extends QueryService<ApplicationServiceOverrideTag> {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideTagQueryService.class);

    private final ApplicationServiceOverrideTagRepository applicationServiceOverrideTagRepository;

    private final ApplicationServiceOverrideTagMapper applicationServiceOverrideTagMapper;

    public ApplicationServiceOverrideTagQueryService(ApplicationServiceOverrideTagRepository applicationServiceOverrideTagRepository, ApplicationServiceOverrideTagMapper applicationServiceOverrideTagMapper) {
        this.applicationServiceOverrideTagRepository = applicationServiceOverrideTagRepository;
        this.applicationServiceOverrideTagMapper = applicationServiceOverrideTagMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationServiceOverrideTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationServiceOverrideTagDTO> findByCriteria(ApplicationServiceOverrideTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideTag> specification = createSpecification(criteria);
        return applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationServiceOverrideTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideTagDTO> findByCriteria(ApplicationServiceOverrideTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationServiceOverrideTag> specification = createSpecification(criteria);
        return applicationServiceOverrideTagRepository.findAll(specification, page)
            .map(applicationServiceOverrideTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationServiceOverrideTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationServiceOverrideTag> specification = createSpecification(criteria);
        return applicationServiceOverrideTagRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ApplicationServiceOverrideTag> createSpecification(ApplicationServiceOverrideTagCriteria criteria) {
        Specification<ApplicationServiceOverrideTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ApplicationServiceOverrideTag_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ApplicationServiceOverrideTag_.name));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationServiceOverrideTag_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ApplicationServiceOverrideTag_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ApplicationServiceOverrideTag_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ApplicationServiceOverrideTag_.modifiedDateTime));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ApplicationServiceOverrideTag_.migratedBy));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApplicationServiceOverrideTag_.version));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ApplicationServiceOverrideTag_.parent, JoinType.LEFT).get(ApplicationServiceOverrideTag_.id)));
            }
            if (criteria.getApplicationServiceOverrideTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideTagId(),
                    root -> root.join(ApplicationServiceOverrideTag_.applicationServiceOverrideTags, JoinType.LEFT).get(ApplicationServiceOverrideTag_.id)));
            }
            if (criteria.getApplicationServiceOverrideTagItemsId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideTagItemsId(),
                    root -> root.join(ApplicationServiceOverrideTag_.applicationServiceOverrideTagItems, JoinType.LEFT).get(ApplicationServiceOverrideTagItems_.id)));
            }
        }
        return specification;
    }
}
