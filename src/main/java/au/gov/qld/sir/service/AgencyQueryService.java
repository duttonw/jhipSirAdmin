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

import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.AgencyRepository;
import au.gov.qld.sir.service.dto.AgencyCriteria;
import au.gov.qld.sir.service.dto.AgencyDTO;
import au.gov.qld.sir.service.mapper.AgencyMapper;

/**
 * Service for executing complex queries for {@link Agency} entities in the database.
 * The main input is a {@link AgencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgencyDTO} or a {@link Page} of {@link AgencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgencyQueryService extends QueryService<Agency> {

    private final Logger log = LoggerFactory.getLogger(AgencyQueryService.class);

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    public AgencyQueryService(AgencyRepository agencyRepository, AgencyMapper agencyMapper) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
    }

    /**
     * Return a {@link List} of {@link AgencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findByCriteria(AgencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyMapper.toDto(agencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyDTO> findByCriteria(AgencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyRepository.findAll(specification, page)
            .map(agencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Agency> createSpecification(AgencyCriteria criteria) {
        Specification<Agency> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Agency_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Agency_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), Agency_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Agency_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), Agency_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Agency_.version));
            }
            if (criteria.getAgencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgencyCode(), Agency_.agencyCode));
            }
            if (criteria.getAgencyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgencyName(), Agency_.agencyName));
            }
            if (criteria.getAgencyUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgencyUrl(), Agency_.agencyUrl));
            }
            if (criteria.getAgencyTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencyTypeId(),
                    root -> root.join(Agency_.agencyType, JoinType.LEFT).get(AgencyType_.id)));
            }
            if (criteria.getAgencySupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencySupportRoleId(),
                    root -> root.join(Agency_.agencySupportRoles, JoinType.LEFT).get(AgencySupportRole_.id)));
            }
            if (criteria.getIntegrationMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getIntegrationMappingId(),
                    root -> root.join(Agency_.integrationMappings, JoinType.LEFT).get(IntegrationMapping_.id)));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Agency_.locations, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getServiceDeliveryOrganisationId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceDeliveryOrganisationId(),
                    root -> root.join(Agency_.serviceDeliveryOrganisations, JoinType.LEFT).get(ServiceDeliveryOrganisation_.id)));
            }
        }
        return specification;
    }
}
