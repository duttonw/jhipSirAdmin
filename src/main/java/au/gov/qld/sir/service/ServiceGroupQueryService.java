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

import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceGroupRepository;
import au.gov.qld.sir.service.dto.ServiceGroupCriteria;
import au.gov.qld.sir.service.dto.ServiceGroupDTO;
import au.gov.qld.sir.service.mapper.ServiceGroupMapper;

/**
 * Service for executing complex queries for {@link ServiceGroup} entities in the database.
 * The main input is a {@link ServiceGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceGroupDTO} or a {@link Page} of {@link ServiceGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceGroupQueryService extends QueryService<ServiceGroup> {

    private final Logger log = LoggerFactory.getLogger(ServiceGroupQueryService.class);

    private final ServiceGroupRepository serviceGroupRepository;

    private final ServiceGroupMapper serviceGroupMapper;

    public ServiceGroupQueryService(ServiceGroupRepository serviceGroupRepository, ServiceGroupMapper serviceGroupMapper) {
        this.serviceGroupRepository = serviceGroupRepository;
        this.serviceGroupMapper = serviceGroupMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceGroupDTO> findByCriteria(ServiceGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceGroup> specification = createSpecification(criteria);
        return serviceGroupMapper.toDto(serviceGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceGroupDTO> findByCriteria(ServiceGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceGroup> specification = createSpecification(criteria);
        return serviceGroupRepository.findAll(specification, page)
            .map(serviceGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceGroup> specification = createSpecification(criteria);
        return serviceGroupRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceGroup> createSpecification(ServiceGroupCriteria criteria) {
        Specification<ServiceGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceGroup_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceGroup_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceGroup_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceGroup_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceGroup_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceGroup_.version));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), ServiceGroup_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ServiceGroup_.migratedBy));
            }
            if (criteria.getServiceGroupCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceGroupCategoryId(),
                    root -> root.join(ServiceGroup_.serviceGroupCategory, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getServiceGroupCategoryTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceGroupCategoryTypeId(),
                    root -> root.join(ServiceGroup_.serviceGroupCategoryType, JoinType.LEFT).get(CategoryType_.id)));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceGroup_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
