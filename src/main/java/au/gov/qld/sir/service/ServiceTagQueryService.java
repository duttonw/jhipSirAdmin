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

import au.gov.qld.sir.domain.ServiceTag;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceTagRepository;
import au.gov.qld.sir.service.dto.ServiceTagCriteria;
import au.gov.qld.sir.service.dto.ServiceTagDTO;
import au.gov.qld.sir.service.mapper.ServiceTagMapper;

/**
 * Service for executing complex queries for {@link ServiceTag} entities in the database.
 * The main input is a {@link ServiceTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceTagDTO} or a {@link Page} of {@link ServiceTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceTagQueryService extends QueryService<ServiceTag> {

    private final Logger log = LoggerFactory.getLogger(ServiceTagQueryService.class);

    private final ServiceTagRepository serviceTagRepository;

    private final ServiceTagMapper serviceTagMapper;

    public ServiceTagQueryService(ServiceTagRepository serviceTagRepository, ServiceTagMapper serviceTagMapper) {
        this.serviceTagRepository = serviceTagRepository;
        this.serviceTagMapper = serviceTagMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceTagDTO> findByCriteria(ServiceTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceTag> specification = createSpecification(criteria);
        return serviceTagMapper.toDto(serviceTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceTagDTO> findByCriteria(ServiceTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceTag> specification = createSpecification(criteria);
        return serviceTagRepository.findAll(specification, page)
            .map(serviceTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceTag> specification = createSpecification(criteria);
        return serviceTagRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceTag> createSpecification(ServiceTagCriteria criteria) {
        Specification<ServiceTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceTag_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ServiceTag_.name));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceTag_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceTag_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceTag_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceTag_.modifiedDateTime));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ServiceTag_.parent, JoinType.LEFT).get(ServiceTag_.id)));
            }
            if (criteria.getServiceTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceTagId(),
                    root -> root.join(ServiceTag_.serviceTags, JoinType.LEFT).get(ServiceTag_.id)));
            }
            if (criteria.getServiceTagItemsId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceTagItemsId(),
                    root -> root.join(ServiceTag_.serviceTagItems, JoinType.LEFT).get(ServiceTagItems_.id)));
            }
        }
        return specification;
    }
}
