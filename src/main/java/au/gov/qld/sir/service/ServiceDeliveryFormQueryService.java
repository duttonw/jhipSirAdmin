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

import au.gov.qld.sir.domain.ServiceDeliveryForm;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceDeliveryFormRepository;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormCriteria;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryFormMapper;

/**
 * Service for executing complex queries for {@link ServiceDeliveryForm} entities in the database.
 * The main input is a {@link ServiceDeliveryFormCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceDeliveryFormDTO} or a {@link Page} of {@link ServiceDeliveryFormDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceDeliveryFormQueryService extends QueryService<ServiceDeliveryForm> {

    private final Logger log = LoggerFactory.getLogger(ServiceDeliveryFormQueryService.class);

    private final ServiceDeliveryFormRepository serviceDeliveryFormRepository;

    private final ServiceDeliveryFormMapper serviceDeliveryFormMapper;

    public ServiceDeliveryFormQueryService(ServiceDeliveryFormRepository serviceDeliveryFormRepository, ServiceDeliveryFormMapper serviceDeliveryFormMapper) {
        this.serviceDeliveryFormRepository = serviceDeliveryFormRepository;
        this.serviceDeliveryFormMapper = serviceDeliveryFormMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceDeliveryFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDeliveryFormDTO> findByCriteria(ServiceDeliveryFormCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceDeliveryForm> specification = createSpecification(criteria);
        return serviceDeliveryFormMapper.toDto(serviceDeliveryFormRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceDeliveryFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDeliveryFormDTO> findByCriteria(ServiceDeliveryFormCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceDeliveryForm> specification = createSpecification(criteria);
        return serviceDeliveryFormRepository.findAll(specification, page)
            .map(serviceDeliveryFormMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceDeliveryFormCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceDeliveryForm> specification = createSpecification(criteria);
        return serviceDeliveryFormRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceDeliveryForm> createSpecification(ServiceDeliveryFormCriteria criteria) {
        Specification<ServiceDeliveryForm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceDeliveryForm_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceDeliveryForm_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceDeliveryForm_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceDeliveryForm_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceDeliveryForm_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceDeliveryForm_.version));
            }
            if (criteria.getFormName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormName(), ServiceDeliveryForm_.formName));
            }
            if (criteria.getFormUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormUrl(), ServiceDeliveryForm_.formUrl));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), ServiceDeliveryForm_.source));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceDeliveryForm_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
