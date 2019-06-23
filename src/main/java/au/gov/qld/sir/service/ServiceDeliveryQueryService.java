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

import au.gov.qld.sir.domain.ServiceDelivery;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceDeliveryRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryCriteria;
import au.gov.qld.sir.service.dto.ServiceDeliveryDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryMapper;

/**
 * Service for executing complex queries for {@link ServiceDelivery} entities in the database.
 * The main input is a {@link ServiceDeliveryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceDeliveryDTO} or a {@link Page} of {@link ServiceDeliveryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceDeliveryQueryService extends QueryService<ServiceDelivery> {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryQueryService.class);

    private final ServiceDeliveryRepository serviceDeliveryRepository;

    private final ServiceDeliveryMapper serviceDeliveryMapper;

    public ServiceDeliveryQueryService(ServiceDeliveryRepository serviceDeliveryRepository, ServiceDeliveryMapper serviceDeliveryMapper) {
        this.serviceDeliveryRepository = serviceDeliveryRepository;
        this.serviceDeliveryMapper = serviceDeliveryMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceDeliveryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDeliveryDTO> findByCriteria(ServiceDeliveryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceDelivery> specification = createSpecification(criteria);
        return serviceDeliveryMapper.toDto(serviceDeliveryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceDeliveryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryDTO> findByCriteria(ServiceDeliveryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceDelivery> specification = createSpecification(criteria);
        return serviceDeliveryRepository.findAll(specification, page)
            .map(serviceDeliveryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceDeliveryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceDelivery> specification = createSpecification(criteria);
        return serviceDeliveryRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceDelivery> createSpecification(ServiceDeliveryCriteria criteria) {
        Specification<ServiceDelivery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceDelivery_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceDelivery_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceDelivery_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceDelivery_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceDelivery_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceDelivery_.version));
            }
            if (criteria.getServiceDeliveryChannelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceDeliveryChannelType(), ServiceDelivery_.serviceDeliveryChannelType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ServiceDelivery_.status));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceDelivery_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getDeliveryChannelId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryChannelId(),
                    root -> root.join(ServiceDelivery_.deliveryChannels, JoinType.LEFT).get(DeliveryChannel_.id)));
            }
        }
        return specification;
    }
}
