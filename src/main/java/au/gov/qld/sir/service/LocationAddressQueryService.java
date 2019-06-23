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

import au.gov.qld.sir.domain.LocationAddress;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.LocationAddressRepository;
import au.gov.qld.sir.service.dto.LocationAddressCriteria;
import au.gov.qld.sir.service.dto.LocationAddressDTO;
import au.gov.qld.sir.service.mapper.LocationAddressMapper;

/**
 * Service for executing complex queries for {@link LocationAddress} entities in the database.
 * The main input is a {@link LocationAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationAddressDTO} or a {@link Page} of {@link LocationAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationAddressQueryService extends QueryService<LocationAddress> {

    private final Logger log = LoggerFactory.getLogger(LocationAddressQueryService.class);

    private final LocationAddressRepository locationAddressRepository;

    private final LocationAddressMapper locationAddressMapper;

    public LocationAddressQueryService(LocationAddressRepository locationAddressRepository, LocationAddressMapper locationAddressMapper) {
        this.locationAddressRepository = locationAddressRepository;
        this.locationAddressMapper = locationAddressMapper;
    }

    /**
     * Return a {@link List} of {@link LocationAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationAddressDTO> findByCriteria(LocationAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationAddress> specification = createSpecification(criteria);
        return locationAddressMapper.toDto(locationAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationAddressDTO> findByCriteria(LocationAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationAddress> specification = createSpecification(criteria);
        return locationAddressRepository.findAll(specification, page)
            .map(locationAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationAddress> specification = createSpecification(criteria);
        return locationAddressRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<LocationAddress> createSpecification(LocationAddressCriteria criteria) {
        Specification<LocationAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LocationAddress_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LocationAddress_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), LocationAddress_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), LocationAddress_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), LocationAddress_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LocationAddress_.version));
            }
            if (criteria.getAdditionalInformation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalInformation(), LocationAddress_.additionalInformation));
            }
            if (criteria.getAddressLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine1(), LocationAddress_.addressLine1));
            }
            if (criteria.getAddressLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine2(), LocationAddress_.addressLine2));
            }
            if (criteria.getAddressType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressType(), LocationAddress_.addressType));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), LocationAddress_.countryCode));
            }
            if (criteria.getLocalityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalityName(), LocationAddress_.localityName));
            }
            if (criteria.getLocationPoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationPoint(), LocationAddress_.locationPoint));
            }
            if (criteria.getPostcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostcode(), LocationAddress_.postcode));
            }
            if (criteria.getStateCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateCode(), LocationAddress_.stateCode));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(LocationAddress_.location, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
