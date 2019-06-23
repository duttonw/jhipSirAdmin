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

import au.gov.qld.sir.domain.LocationEmail;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.LocationEmailRepository;
import au.gov.qld.sir.service.dto.LocationEmailCriteria;
import au.gov.qld.sir.service.dto.LocationEmailDTO;
import au.gov.qld.sir.service.mapper.LocationEmailMapper;

/**
 * Service for executing complex queries for {@link LocationEmail} entities in the database.
 * The main input is a {@link LocationEmailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationEmailDTO} or a {@link Page} of {@link LocationEmailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationEmailQueryService extends QueryService<LocationEmail> {

    private final Logger log = LoggerFactory.getLogger(LocationEmailQueryService.class);

    private final LocationEmailRepository locationEmailRepository;

    private final LocationEmailMapper locationEmailMapper;

    public LocationEmailQueryService(LocationEmailRepository locationEmailRepository, LocationEmailMapper locationEmailMapper) {
        this.locationEmailRepository = locationEmailRepository;
        this.locationEmailMapper = locationEmailMapper;
    }

    /**
     * Return a {@link List} of {@link LocationEmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationEmailDTO> findByCriteria(LocationEmailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationEmail> specification = createSpecification(criteria);
        return locationEmailMapper.toDto(locationEmailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationEmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationEmailDTO> findByCriteria(LocationEmailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationEmail> specification = createSpecification(criteria);
        return locationEmailRepository.findAll(specification, page)
            .map(locationEmailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationEmailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationEmail> specification = createSpecification(criteria);
        return locationEmailRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<LocationEmail> createSpecification(LocationEmailCriteria criteria) {
        Specification<LocationEmail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LocationEmail_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LocationEmail_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), LocationEmail_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), LocationEmail_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), LocationEmail_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LocationEmail_.version));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), LocationEmail_.comment));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), LocationEmail_.email));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(LocationEmail_.location, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
