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

import au.gov.qld.sir.domain.OpeningHoursSpecification;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.OpeningHoursSpecificationRepository;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationCriteria;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationDTO;
import au.gov.qld.sir.service.mapper.OpeningHoursSpecificationMapper;

/**
 * Service for executing complex queries for {@link OpeningHoursSpecification} entities in the database.
 * The main input is a {@link OpeningHoursSpecificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpeningHoursSpecificationDTO} or a {@link Page} of {@link OpeningHoursSpecificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpeningHoursSpecificationQueryService extends QueryService<OpeningHoursSpecification> {

    private final Logger log = LoggerFactory.getLogger(OpeningHoursSpecificationQueryService.class);

    private final OpeningHoursSpecificationRepository openingHoursSpecificationRepository;

    private final OpeningHoursSpecificationMapper openingHoursSpecificationMapper;

    public OpeningHoursSpecificationQueryService(OpeningHoursSpecificationRepository openingHoursSpecificationRepository, OpeningHoursSpecificationMapper openingHoursSpecificationMapper) {
        this.openingHoursSpecificationRepository = openingHoursSpecificationRepository;
        this.openingHoursSpecificationMapper = openingHoursSpecificationMapper;
    }

    /**
     * Return a {@link List} of {@link OpeningHoursSpecificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpeningHoursSpecificationDTO> findByCriteria(OpeningHoursSpecificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OpeningHoursSpecification> specification = createSpecification(criteria);
        return openingHoursSpecificationMapper.toDto(openingHoursSpecificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OpeningHoursSpecificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpeningHoursSpecificationDTO> findByCriteria(OpeningHoursSpecificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpeningHoursSpecification> specification = createSpecification(criteria);
        return openingHoursSpecificationRepository.findAll(specification, page)
            .map(openingHoursSpecificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpeningHoursSpecificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpeningHoursSpecification> specification = createSpecification(criteria);
        return openingHoursSpecificationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<OpeningHoursSpecification> createSpecification(OpeningHoursSpecificationCriteria criteria) {
        Specification<OpeningHoursSpecification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OpeningHoursSpecification_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OpeningHoursSpecification_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), OpeningHoursSpecification_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), OpeningHoursSpecification_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), OpeningHoursSpecification_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), OpeningHoursSpecification_.version));
            }
            if (criteria.getCloses() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCloses(), OpeningHoursSpecification_.closes));
            }
            if (criteria.getDayOfWeek() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayOfWeek(), OpeningHoursSpecification_.dayOfWeek));
            }
            if (criteria.getOpens() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpens(), OpeningHoursSpecification_.opens));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), OpeningHoursSpecification_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), OpeningHoursSpecification_.validTo));
            }
            if (criteria.getAvailabilityHoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailabilityHoursId(),
                    root -> root.join(OpeningHoursSpecification_.availabilityHours, JoinType.LEFT).get(AvailabilityHours_.id)));
            }
        }
        return specification;
    }
}
