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

import au.gov.qld.sir.domain.AgencyType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.AgencyTypeRepository;
import au.gov.qld.sir.service.dto.AgencyTypeCriteria;
import au.gov.qld.sir.service.dto.AgencyTypeDTO;
import au.gov.qld.sir.service.mapper.AgencyTypeMapper;

/**
 * Service for executing complex queries for {@link AgencyType} entities in the database.
 * The main input is a {@link AgencyTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgencyTypeDTO} or a {@link Page} of {@link AgencyTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgencyTypeQueryService extends QueryService<AgencyType> {

    private final Logger log = LoggerFactory.getLogger(AgencyTypeQueryService.class);

    private final AgencyTypeRepository agencyTypeRepository;

    private final AgencyTypeMapper agencyTypeMapper;

    public AgencyTypeQueryService(AgencyTypeRepository agencyTypeRepository, AgencyTypeMapper agencyTypeMapper) {
        this.agencyTypeRepository = agencyTypeRepository;
        this.agencyTypeMapper = agencyTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AgencyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgencyTypeDTO> findByCriteria(AgencyTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AgencyType> specification = createSpecification(criteria);
        return agencyTypeMapper.toDto(agencyTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgencyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyTypeDTO> findByCriteria(AgencyTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AgencyType> specification = createSpecification(criteria);
        return agencyTypeRepository.findAll(specification, page)
            .map(agencyTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgencyTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AgencyType> specification = createSpecification(criteria);
        return agencyTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<AgencyType> createSpecification(AgencyTypeCriteria criteria) {
        Specification<AgencyType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AgencyType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AgencyType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), AgencyType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), AgencyType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), AgencyType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), AgencyType_.version));
            }
            if (criteria.getAgencyTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgencyTypeName(), AgencyType_.agencyTypeName));
            }
            if (criteria.getAgencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyId(),
                    root -> root.join(AgencyType_.agencies, JoinType.LEFT).get(Agency_.id)));
            }
        }
        return specification;
    }
}
