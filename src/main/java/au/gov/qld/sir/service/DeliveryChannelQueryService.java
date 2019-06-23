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

import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.DeliveryChannelRepository;
import au.gov.qld.sir.service.dto.DeliveryChannelCriteria;
import au.gov.qld.sir.service.dto.DeliveryChannelDTO;
import au.gov.qld.sir.service.mapper.DeliveryChannelMapper;

/**
 * Service for executing complex queries for {@link DeliveryChannel} entities in the database.
 * The main input is a {@link DeliveryChannelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryChannelDTO} or a {@link Page} of {@link DeliveryChannelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryChannelQueryService extends QueryService<DeliveryChannel> {

    private final Logger log = LoggerFactory.getLogger(DeliveryChannelQueryService.class);

    private final DeliveryChannelRepository deliveryChannelRepository;

    private final DeliveryChannelMapper deliveryChannelMapper;

    public DeliveryChannelQueryService(DeliveryChannelRepository deliveryChannelRepository, DeliveryChannelMapper deliveryChannelMapper) {
        this.deliveryChannelRepository = deliveryChannelRepository;
        this.deliveryChannelMapper = deliveryChannelMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryChannelDTO> findByCriteria(DeliveryChannelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeliveryChannel> specification = createSpecification(criteria);
        return deliveryChannelMapper.toDto(deliveryChannelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryChannelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryChannelDTO> findByCriteria(DeliveryChannelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeliveryChannel> specification = createSpecification(criteria);
        return deliveryChannelRepository.findAll(specification, page)
            .map(deliveryChannelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeliveryChannelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeliveryChannel> specification = createSpecification(criteria);
        return deliveryChannelRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<DeliveryChannel> createSpecification(DeliveryChannelCriteria criteria) {
        Specification<DeliveryChannel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DeliveryChannel_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), DeliveryChannel_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), DeliveryChannel_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), DeliveryChannel_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), DeliveryChannel_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), DeliveryChannel_.version));
            }
            if (criteria.getAdditionalDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalDetails(), DeliveryChannel_.additionalDetails));
            }
            if (criteria.getDeliveryChannelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryChannelType(), DeliveryChannel_.deliveryChannelType));
            }
            if (criteria.getDeliveryChannelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryChannelId(), DeliveryChannel_.deliveryChannelId));
            }
            if (criteria.getVirtualDeliveryDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVirtualDeliveryDetails(), DeliveryChannel_.virtualDeliveryDetails));
            }
            if (criteria.getDeliveryHoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryHoursId(),
                    root -> root.join(DeliveryChannel_.deliveryHours, JoinType.LEFT).get(AvailabilityHours_.id)));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(DeliveryChannel_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getServiceDeliveryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceDeliveryId(),
                    root -> root.join(DeliveryChannel_.serviceDelivery, JoinType.LEFT).get(ServiceDelivery_.id)));
            }
        }
        return specification;
    }
}
