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

import au.gov.qld.sir.domain.Application;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ApplicationRepository;
import au.gov.qld.sir.service.dto.ApplicationCriteria;
import au.gov.qld.sir.service.dto.ApplicationDTO;
import au.gov.qld.sir.service.mapper.ApplicationMapper;

/**
 * Service for executing complex queries for {@link Application} entities in the database.
 * The main input is a {@link ApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationDTO} or a {@link Page} of {@link ApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationQueryService extends QueryService<Application> {

    private final Logger log = LoggerFactory.getLogger(ApplicationQueryService.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    public ApplicationQueryService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationDTO> findByCriteria(ApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationMapper.toDto(applicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findByCriteria(ApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification, page)
            .map(applicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Application> createSpecification(ApplicationCriteria criteria) {
        Specification<Application> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Application_.id));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), Application_.contactEmail));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Application_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), Application_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Application_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), Application_.modifiedDateTime));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Application_.name));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), Application_.migratedBy));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Application_.version));
            }
            if (criteria.getApplicationServiceOverrideId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationServiceOverrideId(),
                    root -> root.join(Application_.applicationServiceOverrides, JoinType.LEFT).get(ApplicationServiceOverride_.id)));
            }
        }
        return specification;
    }
}
