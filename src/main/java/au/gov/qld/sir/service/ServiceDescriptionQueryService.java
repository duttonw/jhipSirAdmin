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

import au.gov.qld.sir.domain.ServiceDescription;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceDescriptionRepository;
import au.gov.qld.sir.service.dto.ServiceDescriptionCriteria;
import au.gov.qld.sir.service.dto.ServiceDescriptionDTO;
import au.gov.qld.sir.service.mapper.ServiceDescriptionMapper;

/**
 * Service for executing complex queries for {@link ServiceDescription} entities in the database.
 * The main input is a {@link ServiceDescriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceDescriptionDTO} or a {@link Page} of {@link ServiceDescriptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceDescriptionQueryService extends QueryService<ServiceDescription> {

    private final Logger log = LoggerFactory.getLogger(ServiceDescriptionQueryService.class);

    private final ServiceDescriptionRepository serviceDescriptionRepository;

    private final ServiceDescriptionMapper serviceDescriptionMapper;

    public ServiceDescriptionQueryService(ServiceDescriptionRepository serviceDescriptionRepository, ServiceDescriptionMapper serviceDescriptionMapper) {
        this.serviceDescriptionRepository = serviceDescriptionRepository;
        this.serviceDescriptionMapper = serviceDescriptionMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceDescriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDescriptionDTO> findByCriteria(ServiceDescriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceDescription> specification = createSpecification(criteria);
        return serviceDescriptionMapper.toDto(serviceDescriptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceDescriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDescriptionDTO> findByCriteria(ServiceDescriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceDescription> specification = createSpecification(criteria);
        return serviceDescriptionRepository.findAll(specification, page)
            .map(serviceDescriptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceDescriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceDescription> specification = createSpecification(criteria);
        return serviceDescriptionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceDescription> createSpecification(ServiceDescriptionCriteria criteria) {
        Specification<ServiceDescription> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceDescription_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceDescription_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceDescription_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceDescription_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceDescription_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceDescription_.version));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), ServiceDescription_.context));
            }
            if (criteria.getServiceDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceDescription(), ServiceDescription_.serviceDescription));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), ServiceDescription_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ServiceDescription_.migratedBy));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceDescription_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
