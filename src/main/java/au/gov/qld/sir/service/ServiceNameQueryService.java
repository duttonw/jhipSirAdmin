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

import au.gov.qld.sir.domain.ServiceName;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceNameRepository;
import au.gov.qld.sir.service.dto.ServiceNameCriteria;
import au.gov.qld.sir.service.dto.ServiceNameDTO;
import au.gov.qld.sir.service.mapper.ServiceNameMapper;

/**
 * Service for executing complex queries for {@link ServiceName} entities in the database.
 * The main input is a {@link ServiceNameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceNameDTO} or a {@link Page} of {@link ServiceNameDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceNameQueryService extends QueryService<ServiceName> {

    private final Logger log = LoggerFactory.getLogger(ServiceNameQueryService.class);

    private final ServiceNameRepository serviceNameRepository;

    private final ServiceNameMapper serviceNameMapper;

    public ServiceNameQueryService(ServiceNameRepository serviceNameRepository, ServiceNameMapper serviceNameMapper) {
        this.serviceNameRepository = serviceNameRepository;
        this.serviceNameMapper = serviceNameMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceNameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceNameDTO> findByCriteria(ServiceNameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceName> specification = createSpecification(criteria);
        return serviceNameMapper.toDto(serviceNameRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceNameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceNameDTO> findByCriteria(ServiceNameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceName> specification = createSpecification(criteria);
        return serviceNameRepository.findAll(specification, page)
            .map(serviceNameMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceNameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceName> specification = createSpecification(criteria);
        return serviceNameRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceName> createSpecification(ServiceNameCriteria criteria) {
        Specification<ServiceName> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceName_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceName_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceName_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceName_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceName_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceName_.version));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), ServiceName_.context));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), ServiceName_.serviceName));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), ServiceName_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ServiceName_.migratedBy));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceName_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
