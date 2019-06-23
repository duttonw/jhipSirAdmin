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

import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceSupportRoleRepository;
import au.gov.qld.sir.service.dto.ServiceSupportRoleCriteria;
import au.gov.qld.sir.service.dto.ServiceSupportRoleDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleMapper;

/**
 * Service for executing complex queries for {@link ServiceSupportRole} entities in the database.
 * The main input is a {@link ServiceSupportRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceSupportRoleDTO} or a {@link Page} of {@link ServiceSupportRoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceSupportRoleQueryService extends QueryService<ServiceSupportRole> {

    private final Logger log = LoggerFactory.getLogger(ServiceSupportRoleQueryService.class);

    private final ServiceSupportRoleRepository serviceSupportRoleRepository;

    private final ServiceSupportRoleMapper serviceSupportRoleMapper;

    public ServiceSupportRoleQueryService(ServiceSupportRoleRepository serviceSupportRoleRepository, ServiceSupportRoleMapper serviceSupportRoleMapper) {
        this.serviceSupportRoleRepository = serviceSupportRoleRepository;
        this.serviceSupportRoleMapper = serviceSupportRoleMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceSupportRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceSupportRoleDTO> findByCriteria(ServiceSupportRoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceSupportRole> specification = createSpecification(criteria);
        return serviceSupportRoleMapper.toDto(serviceSupportRoleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceSupportRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceSupportRoleDTO> findByCriteria(ServiceSupportRoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceSupportRole> specification = createSpecification(criteria);
        return serviceSupportRoleRepository.findAll(specification, page)
            .map(serviceSupportRoleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceSupportRoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceSupportRole> specification = createSpecification(criteria);
        return serviceSupportRoleRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceSupportRole> createSpecification(ServiceSupportRoleCriteria criteria) {
        Specification<ServiceSupportRole> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceSupportRole_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceSupportRole_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceSupportRole_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceSupportRole_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceSupportRole_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceSupportRole_.version));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), ServiceSupportRole_.contactEmail));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), ServiceSupportRole_.contactName));
            }
            if (criteria.getContactPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPhoneNumber(), ServiceSupportRole_.contactPhoneNumber));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceSupportRole_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getServiceRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRoleTypeId(),
                    root -> root.join(ServiceSupportRole_.serviceRoleType, JoinType.LEFT).get(ServiceRoleType_.id)));
            }
            if (criteria.getServiceSupportContextTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceSupportContextTypeId(),
                    root -> root.join(ServiceSupportRole_.serviceSupportContextType, JoinType.LEFT).get(ServiceSupportRoleContextType_.id)));
            }
        }
        return specification;
    }
}
