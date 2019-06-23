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

import au.gov.qld.sir.domain.LocationType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.LocationTypeRepository;
import au.gov.qld.sir.service.dto.LocationTypeCriteria;
import au.gov.qld.sir.service.dto.LocationTypeDTO;
import au.gov.qld.sir.service.mapper.LocationTypeMapper;

/**
 * Service for executing complex queries for {@link LocationType} entities in the database.
 * The main input is a {@link LocationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationTypeDTO} or a {@link Page} of {@link LocationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationTypeQueryService extends QueryService<LocationType> {

    private final Logger log = LoggerFactory.getLogger(LocationTypeQueryService.class);

    private final LocationTypeRepository locationTypeRepository;

    private final LocationTypeMapper locationTypeMapper;

    public LocationTypeQueryService(LocationTypeRepository locationTypeRepository, LocationTypeMapper locationTypeMapper) {
        this.locationTypeRepository = locationTypeRepository;
        this.locationTypeMapper = locationTypeMapper;
    }

    /**
     * Return a {@link List} of {@link LocationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationTypeDTO> findByCriteria(LocationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationType> specification = createSpecification(criteria);
        return locationTypeMapper.toDto(locationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationTypeDTO> findByCriteria(LocationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationType> specification = createSpecification(criteria);
        return locationTypeRepository.findAll(specification, page)
            .map(locationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationType> specification = createSpecification(criteria);
        return locationTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<LocationType> createSpecification(LocationTypeCriteria criteria) {
        Specification<LocationType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LocationType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LocationType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), LocationType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), LocationType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), LocationType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LocationType_.version));
            }
            if (criteria.getLocationTypeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationTypeCode(), LocationType_.locationTypeCode));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(LocationType_.locations, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
