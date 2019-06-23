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

import au.gov.qld.sir.domain.ServiceRoleType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceRoleTypeRepository;
import au.gov.qld.sir.service.dto.ServiceRoleTypeCriteria;
import au.gov.qld.sir.service.dto.ServiceRoleTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceRoleTypeMapper;

/**
 * Service for executing complex queries for {@link ServiceRoleType} entities in the database.
 * The main input is a {@link ServiceRoleTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceRoleTypeDTO} or a {@link Page} of {@link ServiceRoleTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceRoleTypeQueryService extends QueryService<ServiceRoleType> {

    private final Logger log = LoggerFactory.getLogger(ServiceRoleTypeQueryService.class);

    private final ServiceRoleTypeRepository serviceRoleTypeRepository;

    private final ServiceRoleTypeMapper serviceRoleTypeMapper;

    public ServiceRoleTypeQueryService(ServiceRoleTypeRepository serviceRoleTypeRepository, ServiceRoleTypeMapper serviceRoleTypeMapper) {
        this.serviceRoleTypeRepository = serviceRoleTypeRepository;
        this.serviceRoleTypeMapper = serviceRoleTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceRoleTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRoleTypeDTO> findByCriteria(ServiceRoleTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceRoleType> specification = createSpecification(criteria);
        return serviceRoleTypeMapper.toDto(serviceRoleTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceRoleTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRoleTypeDTO> findByCriteria(ServiceRoleTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceRoleType> specification = createSpecification(criteria);
        return serviceRoleTypeRepository.findAll(specification, page)
            .map(serviceRoleTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceRoleTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceRoleType> specification = createSpecification(criteria);
        return serviceRoleTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceRoleType> createSpecification(ServiceRoleTypeCriteria criteria) {
        Specification<ServiceRoleType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceRoleType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceRoleType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceRoleType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceRoleType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceRoleType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceRoleType_.version));
            }
            if (criteria.getServiceRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceRole(), ServiceRoleType_.serviceRole));
            }
            if (criteria.getAgencySupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgencySupportRoleId(),
                    root -> root.join(ServiceRoleType_.agencySupportRoles, JoinType.LEFT).get(AgencySupportRole_.id)));
            }
            if (criteria.getServiceSupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceSupportRoleId(),
                    root -> root.join(ServiceRoleType_.serviceSupportRoles, JoinType.LEFT).get(ServiceSupportRole_.id)));
            }
        }
        return specification;
    }
}
