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

import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.LocationRepository;
import au.gov.qld.sir.service.dto.LocationCriteria;
import au.gov.qld.sir.service.dto.LocationDTO;
import au.gov.qld.sir.service.mapper.LocationMapper;

/**
 * Service for executing complex queries for {@link Location} entities in the database.
 * The main input is a {@link LocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationDTO} or a {@link Page} of {@link LocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationQueryService extends QueryService<Location> {

    private final Logger log = LoggerFactory.getLogger(LocationQueryService.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    public LocationQueryService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    /**
     * Return a {@link List} of {@link LocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationDTO> findByCriteria(LocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Location> specification = createSpecification(criteria);
        return locationMapper.toDto(locationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationDTO> findByCriteria(LocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Location> specification = createSpecification(criteria);
        return locationRepository.findAll(specification, page)
            .map(locationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Location> specification = createSpecification(criteria);
        return locationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Location> createSpecification(LocationCriteria criteria) {
        Specification<Location> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Location_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Location_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), Location_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Location_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), Location_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Location_.version));
            }
            if (criteria.getAccessibilityFacilities() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccessibilityFacilities(), Location_.accessibilityFacilities));
            }
            if (criteria.getAdditionalInformation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalInformation(), Location_.additionalInformation));
            }
            if (criteria.getLocationName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationName(), Location_.locationName));
            }
            if (criteria.getAgencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyId(),
                    root -> root.join(Location_.agency, JoinType.LEFT).get(Agency_.id)));
            }
            if (criteria.getLocationHoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationHoursId(),
                    root -> root.join(Location_.locationHours, JoinType.LEFT).get(AvailabilityHours_.id)));
            }
            if (criteria.getLocationTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationTypeId(),
                    root -> root.join(Location_.locationType, JoinType.LEFT).get(LocationType_.id)));
            }
            if (criteria.getDeliveryChannelId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryChannelId(),
                    root -> root.join(Location_.deliveryChannels, JoinType.LEFT).get(DeliveryChannel_.id)));
            }
            if (criteria.getLocationAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationAddressId(),
                    root -> root.join(Location_.locationAddresses, JoinType.LEFT).get(LocationAddress_.id)));
            }
            if (criteria.getLocationEmailId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationEmailId(),
                    root -> root.join(Location_.locationEmails, JoinType.LEFT).get(LocationEmail_.id)));
            }
            if (criteria.getLocationPhoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationPhoneId(),
                    root -> root.join(Location_.locationPhones, JoinType.LEFT).get(LocationPhone_.id)));
            }
        }
        return specification;
    }
}
