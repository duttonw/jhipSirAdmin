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

import au.gov.qld.sir.domain.CategoryType;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.CategoryTypeRepository;
import au.gov.qld.sir.service.dto.CategoryTypeCriteria;
import au.gov.qld.sir.service.dto.CategoryTypeDTO;
import au.gov.qld.sir.service.mapper.CategoryTypeMapper;

/**
 * Service for executing complex queries for {@link CategoryType} entities in the database.
 * The main input is a {@link CategoryTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryTypeDTO} or a {@link Page} of {@link CategoryTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryTypeQueryService extends QueryService<CategoryType> {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeQueryService.class);

    private final CategoryTypeRepository categoryTypeRepository;

    private final CategoryTypeMapper categoryTypeMapper;

    public CategoryTypeQueryService(CategoryTypeRepository categoryTypeRepository, CategoryTypeMapper categoryTypeMapper) {
        this.categoryTypeRepository = categoryTypeRepository;
        this.categoryTypeMapper = categoryTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CategoryTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryTypeDTO> findByCriteria(CategoryTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoryType> specification = createSpecification(criteria);
        return categoryTypeMapper.toDto(categoryTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoryTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryTypeDTO> findByCriteria(CategoryTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoryType> specification = createSpecification(criteria);
        return categoryTypeRepository.findAll(specification, page)
            .map(categoryTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoryType> specification = createSpecification(criteria);
        return categoryTypeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<CategoryType> createSpecification(CategoryTypeCriteria criteria) {
        Specification<CategoryType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CategoryType_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CategoryType_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), CategoryType_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), CategoryType_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), CategoryType_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), CategoryType_.version));
            }
            if (criteria.getCategoryType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryType(), CategoryType_.categoryType));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), CategoryType_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), CategoryType_.migratedBy));
            }
            if (criteria.getServiceGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceGroupId(),
                    root -> root.join(CategoryType_.serviceGroups, JoinType.LEFT).get(ServiceGroup_.id)));
            }
        }
        return specification;
    }
}
