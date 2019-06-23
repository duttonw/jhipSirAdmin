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

import au.gov.qld.sir.domain.ServiceSupportRoleContextType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceSupportRoleContextTypeRepository;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleContextTypeMapper;

/**
 * Service for executing complex queries for {@link ServiceSupportRoleContextType} entities in the database.
 * The main input is a {@link ServiceSupportRoleContextTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceSupportRoleContextTypeDTO} or a {@link Page} of {@link ServiceSupportRoleContextTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceSupportRoleContextTypeQueryService extends QueryService<ServiceSupportRoleContextType> {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleContextTypeQueryService.class);

    private final ServiceSupportRoleContextTypeRepository serviceSupportRoleContextTypeRepository;

    private final ServiceSupportRoleContextTypeMapper serviceSupportRoleContextTypeMapper;

    public ServiceSupportRoleContextTypeQueryService(ServiceSupportRoleContextTypeRepository serviceSupportRoleContextTypeRepository, ServiceSupportRoleContextTypeMapper serviceSupportRoleContextTypeMapper) {
        this.serviceSupportRoleContextTypeRepository = serviceSupportRoleContextTypeRepository;
        this.serviceSupportRoleContextTypeMapper = serviceSupportRoleContextTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceSupportRoleContextTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceSupportRoleContextTypeDTO> findByCriteria(ServiceSupportRoleContextTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceSupportRoleContextType> specification = createSpecification(criteria);
        return serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceSupportRoleContextTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceSupportRoleContextTypeDTO> findByCriteria(ServiceSupportRoleContextTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceSupportRoleContextType> specification = createSpecification(criteria);
        return serviceSupportRoleContextTypeRepository.findAll(specification, page)
            .map(serviceSupportRoleContextTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceSupportRoleContextTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceSupportRoleContextType> specification = createSpecification(criteria);
        return serviceSupportRoleContextTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceSupportRoleContextType> createSpecification(ServiceSupportRoleContextTypeCriteria criteria) {
        Specification<ServiceSupportRoleContextType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceSupportRoleContextType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceSupportRoleContextType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceSupportRoleContextType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceSupportRoleContextType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceSupportRoleContextType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceSupportRoleContextType_.version));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), ServiceSupportRoleContextType_.context));
            }
            if (criteria.getServiceSupportRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceSupportRoleId(),
                    root -> root.join(ServiceSupportRoleContextType_.serviceSupportRoles, JoinType.LEFT).get(ServiceSupportRole_.id)));
            }
        }
        return specification;
    }
}
