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

import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ApplicationServiceOverrideRepository;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideCriteria;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideMapper;

/**
 * Service for executing complex queries for {@link ApplicationServiceOverride} entities in the database.
 * The main input is a {@link ApplicationServiceOverrideCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationServiceOverrideDTO} or a {@link Page} of {@link ApplicationServiceOverrideDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationServiceOverrideQueryService extends QueryService<ApplicationServiceOverride> {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceOverrideQueryService.class);

    private final ApplicationServiceOverrideRepository applicationServiceOverrideRepository;

    private final ApplicationServiceOverrideMapper applicationServiceOverrideMapper;

    public ApplicationServiceOverrideQueryService(ApplicationServiceOverrideRepository applicationServiceOverrideRepository, ApplicationServiceOverrideMapper applicationServiceOverrideMapper) {
        this.applicationServiceOverrideRepository = applicationServiceOverrideRepository;
        this.applicationServiceOverrideMapper = applicationServiceOverrideMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationServiceOverrideDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationServiceOverrideDTO> findByCriteria(ApplicationServiceOverrideCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationServiceOverride> specification = createSpecification(criteria);
        return applicationServiceOverrideMapper.toDto(applicationServiceOverrideRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationServiceOverrideDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationServiceOverrideDTO> findByCriteria(ApplicationServiceOverrideCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationServiceOverride> specification = createSpecification(criteria);
        return applicationServiceOverrideRepository.findAll(specification, page)
            .map(applicationServiceOverrideMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationServiceOverrideCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationServiceOverride> specification = createSpecification(criteria);
        return applicationServiceOverrideRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ApplicationServiceOverride> createSpecification(ApplicationServiceOverrideCriteria criteria) {
        Specification<ApplicationServiceOverride> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ApplicationServiceOverride_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ApplicationServiceOverride_.description));
            }
            if (criteria.getEligibility() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEligibility(), ApplicationServiceOverride_.eligibility));
            }
            if (criteria.getKeywords() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeywords(), ApplicationServiceOverride_.keywords));
            }
            if (criteria.getLongDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongDescription(), ApplicationServiceOverride_.longDescription));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ApplicationServiceOverride_.name));
            }
            if (criteria.getPreRequisites() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreRequisites(), ApplicationServiceOverride_.preRequisites));
            }
            if (criteria.getFees() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFees(), ApplicationServiceOverride_.fees));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), ApplicationServiceOverride_.active));
            }
            if (criteria.getReferenceUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceUrl(), ApplicationServiceOverride_.referenceUrl));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationServiceOverride_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ApplicationServiceOverride_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ApplicationServiceOverride_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ApplicationServiceOverride_.modifiedDateTime));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ApplicationServiceOverride_.migratedBy));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApplicationServiceOverride_.version));
            }
            if (criteria.getHowTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHowTo(), ApplicationServiceOverride_.howTo));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ApplicationServiceOverride_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(ApplicationServiceOverride_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getApplicationServiceOverrideAttributeId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideAttributeId(),
                    root -> root.join(ApplicationServiceOverride_.applicationServiceOverrideAttributes, JoinType.LEFT).get(ApplicationServiceOverrideAttribute_.id)));
            }
            if (criteria.getApplicationServiceOverrideTagItemsId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideTagItemsId(),
                    root -> root.join(ApplicationServiceOverride_.applicationServiceOverrideTagItems, JoinType.LEFT).get(ApplicationServiceOverrideTagItems_.id)));
            }
        }
        return specification;
    }
}
