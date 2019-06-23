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

import au.gov.qld.sir.domain.ServiceEvent;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceEventRepository;
import au.gov.qld.sir.service.dto.ServiceEventCriteria;
import au.gov.qld.sir.service.dto.ServiceEventDTO;
import au.gov.qld.sir.service.mapper.ServiceEventMapper;

/**
 * Service for executing complex queries for {@link ServiceEvent} entities in the database.
 * The main input is a {@link ServiceEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceEventDTO} or a {@link Page} of {@link ServiceEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceEventQueryService extends QueryService<ServiceEvent> {

    private final Logger log = LoggerFactory.getLogger(ServiceEventQueryService.class);

    private final ServiceEventRepository serviceEventRepository;

    private final ServiceEventMapper serviceEventMapper;

    public ServiceEventQueryService(ServiceEventRepository serviceEventRepository, ServiceEventMapper serviceEventMapper) {
        this.serviceEventRepository = serviceEventRepository;
        this.serviceEventMapper = serviceEventMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceEventDTO> findByCriteria(ServiceEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceEvent> specification = createSpecification(criteria);
        return serviceEventMapper.toDto(serviceEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEventDTO> findByCriteria(ServiceEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceEvent> specification = createSpecification(criteria);
        return serviceEventRepository.findAll(specification, page)
            .map(serviceEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceEvent> specification = createSpecification(criteria);
        return serviceEventRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceEvent> createSpecification(ServiceEventCriteria criteria) {
        Specification<ServiceEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceEvent_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceEvent_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceEvent_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceEvent_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceEvent_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceEvent_.version));
            }
            if (criteria.getServiceEventSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServiceEventSequence(), ServiceEvent_.serviceEventSequence));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceEvent_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getServiceEventTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceEventTypeId(),
                    root -> root.join(ServiceEvent_.serviceEventType, JoinType.LEFT).get(ServiceEventType_.id)));
            }
        }
        return specification;
    }
}
