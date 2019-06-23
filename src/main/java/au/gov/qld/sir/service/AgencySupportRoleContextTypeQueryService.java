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

import au.gov.qld.sir.domain.AgencySupportRoleContextType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.AgencySupportRoleContextTypeRepository;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleContextTypeMapper;

/**
 * Service for executing complex queries for {@link AgencySupportRoleContextType} entities in the database.
 * The main input is a {@link AgencySupportRoleContextTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgencySupportRoleContextTypeDTO} or a {@link Page} of {@link AgencySupportRoleContextTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgencySupportRoleContextTypeQueryService extends QueryService<AgencySupportRoleContextType> {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleContextTypeQueryService.class);

    private final AgencySupportRoleContextTypeRepository agencySupportRoleContextTypeRepository;

    private final AgencySupportRoleContextTypeMapper agencySupportRoleContextTypeMapper;

    public AgencySupportRoleContextTypeQueryService(AgencySupportRoleContextTypeRepository agencySupportRoleContextTypeRepository, AgencySupportRoleContextTypeMapper agencySupportRoleContextTypeMapper) {
        this.agencySupportRoleContextTypeRepository = agencySupportRoleContextTypeRepository;
        this.agencySupportRoleContextTypeMapper = agencySupportRoleContextTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AgencySupportRoleContextTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgencySupportRoleContextTypeDTO> findByCriteria(AgencySupportRoleContextTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AgencySupportRoleContextType> specification = createSpecification(criteria);
        return agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgencySupportRoleContextTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencySupportRoleContextTypeDTO> findByCriteria(AgencySupportRoleContextTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AgencySupportRoleContextType> specification = createSpecification(criteria);
        return agencySupportRoleContextTypeRepository.findAll(specification, page)
            .map(agencySupportRoleContextTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgencySupportRoleContextTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AgencySupportRoleContextType> specification = createSpecification(criteria);
        return agencySupportRoleContextTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<AgencySupportRoleContextType> createSpecification(AgencySupportRoleContextTypeCriteria criteria) {
        Specification<AgencySupportRoleContextType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AgencySupportRoleContextType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AgencySupportRoleContextType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), AgencySupportRoleContextType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), AgencySupportRoleContextType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), AgencySupportRoleContextType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), AgencySupportRoleContextType_.version));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), AgencySupportRoleContextType_.context));
            }
            if (criteria.getAgencySupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencySupportRoleId(),
                    root -> root.join(AgencySupportRoleContextType_.agencySupportRoles, JoinType.LEFT).get(AgencySupportRole_.id)));
            }
        }
        return specification;
    }
}
