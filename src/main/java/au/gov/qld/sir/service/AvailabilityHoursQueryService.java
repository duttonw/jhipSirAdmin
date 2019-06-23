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

import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.AvailabilityHoursRepository;
import au.gov.qld.sir.service.dto.AvailabilityHoursCriteria;
import au.gov.qld.sir.service.dto.AvailabilityHoursDTO;
import au.gov.qld.sir.service.mapper.AvailabilityHoursMapper;

/**
 * Service for executing complex queries for {@link AvailabilityHours} entities in the database.
 * The main input is a {@link AvailabilityHoursCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvailabilityHoursDTO} or a {@link Page} of {@link AvailabilityHoursDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvailabilityHoursQueryService extends QueryService<AvailabilityHours> {

    private final Logger log = LoggerFactory.getLogger(AvailabilityHoursQueryService.class);

    private final AvailabilityHoursRepository availabilityHoursRepository;

    private final AvailabilityHoursMapper availabilityHoursMapper;

    public AvailabilityHoursQueryService(AvailabilityHoursRepository availabilityHoursRepository, AvailabilityHoursMapper availabilityHoursMapper) {
        this.availabilityHoursRepository = availabilityHoursRepository;
        this.availabilityHoursMapper = availabilityHoursMapper;
    }

    /**
     * Return a {@link List} of {@link AvailabilityHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvailabilityHoursDTO> findByCriteria(AvailabilityHoursCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AvailabilityHours> specification = createSpecification(criteria);
        return availabilityHoursMapper.toDto(availabilityHoursRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvailabilityHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvailabilityHoursDTO> findByCriteria(AvailabilityHoursCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AvailabilityHours> specification = createSpecification(criteria);
        return availabilityHoursRepository.findAll(specification, page)
            .map(availabilityHoursMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvailabilityHoursCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AvailabilityHours> specification = createSpecification(criteria);
        return availabilityHoursRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<AvailabilityHours> createSpecification(AvailabilityHoursCriteria criteria) {
        Specification<AvailabilityHours> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AvailabilityHours_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AvailabilityHours_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), AvailabilityHours_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), AvailabilityHours_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), AvailabilityHours_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), AvailabilityHours_.version));
            }
            if (criteria.getAvailable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAvailable(), AvailabilityHours_.available));
            }
            if (criteria.getAvailabilityHoursId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailabilityHoursId(), AvailabilityHours_.availabilityHoursId));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), AvailabilityHours_.comments));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), AvailabilityHours_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), AvailabilityHours_.validTo));
            }
            if (criteria.getDeliveryChannelId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryChannelId(),
                    root -> root.join(AvailabilityHours_.deliveryChannels, JoinType.LEFT).get(DeliveryChannel_.id)));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(AvailabilityHours_.locations, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getOpeningHoursSpecificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getOpeningHoursSpecificationId(),
                    root -> root.join(AvailabilityHours_.openingHoursSpecifications, JoinType.LEFT).get(OpeningHoursSpecification_.id)));
            }
        }
        return specification;
    }
}
