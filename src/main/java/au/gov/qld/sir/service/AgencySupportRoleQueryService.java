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

import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.AgencySupportRoleRepository;
import au.gov.qld.sir.service.dto.AgencySupportRoleCriteria;
import au.gov.qld.sir.service.dto.AgencySupportRoleDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleMapper;

/**
 * Service for executing complex queries for {@link AgencySupportRole} entities in the database.
 * The main input is a {@link AgencySupportRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgencySupportRoleDTO} or a {@link Page} of {@link AgencySupportRoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgencySupportRoleQueryService extends QueryService<AgencySupportRole> {

    private final Logger log = LoggerFactory.getLogger(AgencySupportRoleQueryService.class);

    private final AgencySupportRoleRepository agencySupportRoleRepository;

    private final AgencySupportRoleMapper agencySupportRoleMapper;

    public AgencySupportRoleQueryService(AgencySupportRoleRepository agencySupportRoleRepository, AgencySupportRoleMapper agencySupportRoleMapper) {
        this.agencySupportRoleRepository = agencySupportRoleRepository;
        this.agencySupportRoleMapper = agencySupportRoleMapper;
    }

    /**
     * Return a {@link List} of {@link AgencySupportRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgencySupportRoleDTO> findByCriteria(AgencySupportRoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AgencySupportRole> specification = createSpecification(criteria);
        return agencySupportRoleMapper.toDto(agencySupportRoleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgencySupportRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencySupportRoleDTO> findByCriteria(AgencySupportRoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AgencySupportRole> specification = createSpecification(criteria);
        return agencySupportRoleRepository.findAll(specification, page)
            .map(agencySupportRoleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgencySupportRoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AgencySupportRole> specification = createSpecification(criteria);
        return agencySupportRoleRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<AgencySupportRole> createSpecification(AgencySupportRoleCriteria criteria) {
        Specification<AgencySupportRole> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AgencySupportRole_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AgencySupportRole_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), AgencySupportRole_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), AgencySupportRole_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), AgencySupportRole_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), AgencySupportRole_.version));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), AgencySupportRole_.contactEmail));
            }
            if (criteria.getAgencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyId(),
                    root -> root.join(AgencySupportRole_.agency, JoinType.LEFT).get(Agency_.id)));
            }
            if (criteria.getAgencyRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyRoleTypeId(),
                    root -> root.join(AgencySupportRole_.agencyRoleType, JoinType.LEFT).get(ServiceRoleType_.id)));
            }
            if (criteria.getAgencySupportContextTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencySupportContextTypeId(),
                    root -> root.join(AgencySupportRole_.agencySupportContextType, JoinType.LEFT).get(AgencySupportRoleContextType_.id)));
            }
        }
        return specification;
    }
}
