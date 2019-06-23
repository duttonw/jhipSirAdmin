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

import au.gov.qld.sir.domain.ServiceFranchise;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceFranchiseRepository;
import au.gov.qld.sir.service.dto.ServiceFranchiseCriteria;
import au.gov.qld.sir.service.dto.ServiceFranchiseDTO;
import au.gov.qld.sir.service.mapper.ServiceFranchiseMapper;

/**
 * Service for executing complex queries for {@link ServiceFranchise} entities in the database.
 * The main input is a {@link ServiceFranchiseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceFranchiseDTO} or a {@link Page} of {@link ServiceFranchiseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceFranchiseQueryService extends QueryService<ServiceFranchise> {

    private final Logger log = LoggerFactory.getLogger(ServiceFranchiseQueryService.class);

    private final ServiceFranchiseRepository serviceFranchiseRepository;

    private final ServiceFranchiseMapper serviceFranchiseMapper;

    public ServiceFranchiseQueryService(ServiceFranchiseRepository serviceFranchiseRepository, ServiceFranchiseMapper serviceFranchiseMapper) {
        this.serviceFranchiseRepository = serviceFranchiseRepository;
        this.serviceFranchiseMapper = serviceFranchiseMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceFranchiseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceFranchiseDTO> findByCriteria(ServiceFranchiseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceFranchise> specification = createSpecification(criteria);
        return serviceFranchiseMapper.toDto(serviceFranchiseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceFranchiseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceFranchiseDTO> findByCriteria(ServiceFranchiseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceFranchise> specification = createSpecification(criteria);
        return serviceFranchiseRepository.findAll(specification, page)
            .map(serviceFranchiseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceFranchiseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceFranchise> specification = createSpecification(criteria);
        return serviceFranchiseRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceFranchise> createSpecification(ServiceFranchiseCriteria criteria) {
        Specification<ServiceFranchise> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceFranchise_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceFranchise_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceFranchise_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceFranchise_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceFranchise_.modifiedDateTime));
            }
            if (criteria.getFranchiseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFranchiseName(), ServiceFranchise_.franchiseName));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceFranchise_.version));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceFranchise_.serviceRecords, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
