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

import au.gov.qld.sir.domain.ServiceEvidence;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.ServiceEvidenceRepository;
import au.gov.qld.sir.service.dto.ServiceEvidenceCriteria;
import au.gov.qld.sir.service.dto.ServiceEvidenceDTO;
import au.gov.qld.sir.service.mapper.ServiceEvidenceMapper;

/**
 * Service for executing complex queries for {@link ServiceEvidence} entities in the database.
 * The main input is a {@link ServiceEvidenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceEvidenceDTO} or a {@link Page} of {@link ServiceEvidenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceEvidenceQueryService extends QueryService<ServiceEvidence> {

    private final Logger log = LoggerFactory.getLogger(ServiceEvidenceQueryService.class);

    private final ServiceEvidenceRepository serviceEvidenceRepository;

    private final ServiceEvidenceMapper serviceEvidenceMapper;

    public ServiceEvidenceQueryService(ServiceEvidenceRepository serviceEvidenceRepository, ServiceEvidenceMapper serviceEvidenceMapper) {
        this.serviceEvidenceRepository = serviceEvidenceRepository;
        this.serviceEvidenceMapper = serviceEvidenceMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceEvidenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceEvidenceDTO> findByCriteria(ServiceEvidenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceEvidence> specification = createSpecification(criteria);
        return serviceEvidenceMapper.toDto(serviceEvidenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceEvidenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceEvidenceDTO> findByCriteria(ServiceEvidenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceEvidence> specification = createSpecification(criteria);
        return serviceEvidenceRepository.findAll(specification, page)
            .map(serviceEvidenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceEvidenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceEvidence> specification = createSpecification(criteria);
        return serviceEvidenceRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ServiceEvidence> createSpecification(ServiceEvidenceCriteria criteria) {
        Specification<ServiceEvidence> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceEvidence_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ServiceEvidence_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), ServiceEvidence_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ServiceEvidence_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), ServiceEvidence_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ServiceEvidence_.version));
            }
            if (criteria.getEvidenceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvidenceName(), ServiceEvidence_.evidenceName));
            }
            if (criteria.getMetaData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMetaData(), ServiceEvidence_.metaData));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), ServiceEvidence_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), ServiceEvidence_.migratedBy));
            }
            if (criteria.getDisplayedForCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisplayedForCategoryId(),
                    root -> root.join(ServiceEvidence_.displayedForCategory, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getServiceRecordId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceRecordId(),
                    root -> root.join(ServiceEvidence_.serviceRecord, JoinType.LEFT).get(ServiceRecord_.id)));
            }
        }
        return specification;
    }
}
