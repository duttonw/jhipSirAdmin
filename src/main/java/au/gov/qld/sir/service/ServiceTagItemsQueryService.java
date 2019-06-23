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

import au.gov.qld.sir.domain.ServiceTagItems;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceTagItemsRepository;
import au.gov.qld.sir.service.dto.ServiceTagItemsCriteria;
import au.gov.qld.sir.service.dto.ServiceTagItemsDTO;
import au.gov.qld.sir.service.mapper.ServiceTagItemsMapper;

/**
 * Service for executing complex queries for {@link ServiceTagItems} entities in the database.
 * The main input is a {@link ServiceTagItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceTagItemsDTO} or a {@link Page} of {@link ServiceTagItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceTagItemsQueryService extends QueryService<ServiceTagItems> {

    private final Logger log = LoggerFactory.getLogger(ServiceTagItemsQueryService.class);

    private final ServiceTagItemsRepository serviceTagItemsRepository;

    private final ServiceTagItemsMapper serviceTagItemsMapper;

    public ServiceTagItemsQueryService(ServiceTagItemsRepository serviceTagItemsRepository, ServiceTagItemsMapper serviceTagItemsMapper) {
        this.serviceTagItemsRepository = serviceTagItemsRepository;
        this.serviceTagItemsMapper = serviceTagItemsMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceTagItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceTagItemsDTO> findByCriteria(ServiceTagItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceTagItems> specification = createSpecification(criteria);
        return serviceTagItemsMapper.toDto(serviceTagItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceTagItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceTagItemsDTO> findByCriteria(ServiceTagItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceTagItems> specification = createSpecification(criteria);
        return serviceTagItemsRepository.findAll(specification, page)
            .map(serviceTagItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceTagItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceTagItems> specification = createSpecification(criteria);
        return serviceTagItemsRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceTagItems> createSpecification(ServiceTagItemsCriteria criteria) {
        Specification<ServiceTagItems> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceTagItems_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), ServiceTagItems_.source));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceTagItems_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceTagItems_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceTagItems_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceTagItems_.modifiedDateTime));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceTagItems_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
            if (criteria.getServiceTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceTagId(),
                    root -> root.join(ServiceTagItems_.serviceTag, JoinType.LEFT).get(ServiceTag_.id)));
            }
        }
        return specification;
    }
}
