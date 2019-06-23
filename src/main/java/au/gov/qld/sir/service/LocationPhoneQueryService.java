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

import au.gov.qld.sir.domain.LocationPhone;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.LocationPhoneRepository;
import au.gov.qld.sir.service.dto.LocationPhoneCriteria;
import au.gov.qld.sir.service.dto.LocationPhoneDTO;
import au.gov.qld.sir.service.mapper.LocationPhoneMapper;

/**
 * Service for executing complex queries for {@link LocationPhone} entities in the database.
 * The main input is a {@link LocationPhoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationPhoneDTO} or a {@link Page} of {@link LocationPhoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationPhoneQueryService extends QueryService<LocationPhone> {

    private final Logger log = LoggerFactory.getLogger(LocationPhoneQueryService.class);

    private final LocationPhoneRepository locationPhoneRepository;

    private final LocationPhoneMapper locationPhoneMapper;

    public LocationPhoneQueryService(LocationPhoneRepository locationPhoneRepository, LocationPhoneMapper locationPhoneMapper) {
        this.locationPhoneRepository = locationPhoneRepository;
        this.locationPhoneMapper = locationPhoneMapper;
    }

    /**
     * Return a {@link List} of {@link LocationPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationPhoneDTO> findByCriteria(LocationPhoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationPhone> specification = createSpecification(criteria);
        return locationPhoneMapper.toDto(locationPhoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationPhoneDTO> findByCriteria(LocationPhoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationPhone> specification = createSpecification(criteria);
        return locationPhoneRepository.findAll(specification, page)
            .map(locationPhoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationPhoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationPhone> specification = createSpecification(criteria);
        return locationPhoneRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<LocationPhone> createSpecification(LocationPhoneCriteria criteria) {
        Specification<LocationPhone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LocationPhone_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LocationPhone_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), LocationPhone_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), LocationPhone_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), LocationPhone_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LocationPhone_.version));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), LocationPhone_.comment));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), LocationPhone_.phoneNumber));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(LocationPhone_.location, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
