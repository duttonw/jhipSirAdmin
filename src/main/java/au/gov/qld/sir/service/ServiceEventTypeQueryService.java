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

import au.gov.qld.sir.domain.ServiceEventType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceEventTypeRepository;
import au.gov.qld.sir.service.dto.ServiceEventTypeCriteria;
import au.gov.qld.sir.service.dto.ServiceEventTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceEventTypeMapper;

/**
 * Service for executing complex queries for {@link ServiceEventType} entities in the database.
 * The main input is a {@link ServiceEventTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceEventTypeDTO} or a {@link Page} of {@link ServiceEventTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceEventTypeQueryService extends QueryService<ServiceEventType> {

    private final Logger log = LoggerFactory.getLogger(ServiceEventTypeQueryService.class);

    private final ServiceEventTypeRepository serviceEventTypeRepository;

    private final ServiceEventTypeMapper serviceEventTypeMapper;

    public ServiceEventTypeQueryService(ServiceEventTypeRepository serviceEventTypeRepository, ServiceEventTypeMapper serviceEventTypeMapper) {
        this.serviceEventTypeRepository = serviceEventTypeRepository;
        this.serviceEventTypeMapper = serviceEventTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceEventTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceEventTypeDTO> findByCriteria(ServiceEventTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceEventType> specification = createSpecification(criteria);
        return serviceEventTypeMapper.toDto(serviceEventTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceEventTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEventTypeDTO> findByCriteria(ServiceEventTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceEventType> specification = createSpecification(criteria);
        return serviceEventTypeRepository.findAll(specification, page)
            .map(serviceEventTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceEventTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceEventType> specification = createSpecification(criteria);
        return serviceEventTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceEventType> createSpecification(ServiceEventTypeCriteria criteria) {
        Specification<ServiceEventType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceEventType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceEventType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceEventType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceEventType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceEventType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceEventType_.version));
            }
            if (criteria.getServiceEvent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceEvent(), ServiceEventType_.serviceEvent));
            }
            if (criteria.getServiceEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceEventId(),
                    root -> root.join(ServiceEventType_.serviceEvents, JoinType.LEFT).get(ServiceEvent_.id)));
            }
        }
        return specification;
    }
}
