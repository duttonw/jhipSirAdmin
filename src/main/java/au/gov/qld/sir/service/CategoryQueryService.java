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

import au.gov.qld.sir.domain.Category;
import au.gov.qld.sir.domain.*; // for static metamodels
import au.gov.qld.sir.repository.CategoryRepository;
import au.gov.qld.sir.service.dto.CategoryCriteria;
import au.gov.qld.sir.service.dto.CategoryDTO;
import au.gov.qld.sir.service.mapper.CategoryMapper;

/**
 * Service for executing complex queries for {@link Category} entities in the database.
 * The main input is a {@link CategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryDTO} or a {@link Page} of {@link CategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService extends QueryService<Category> {

    private final Logger log = LoggerFactory.getLogger(CategoryQueryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryQueryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Return a {@link List} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> findByCriteria(CategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryMapper.toDto(categoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findByCriteria(CategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findAll(specification, page)
            .map(categoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Category> createSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Category_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Category_.createdBy));
            }
            if (criteria.getCreatedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDateTime(), Category_.createdDateTime));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Category_.modifiedBy));
            }
            if (criteria.getModifiedDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDateTime(), Category_.modifiedDateTime));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Category_.version));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), Category_.category));
            }
            if (criteria.getMigrated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigrated(), Category_.migrated));
            }
            if (criteria.getMigratedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMigratedBy(), Category_.migratedBy));
            }
            if (criteria.getServiceEvidenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceEvidenceId(),
                    root -> root.join(Category_.serviceEvidences, JoinType.LEFT).get(ServiceEvidence_.id)));
            }
            if (criteria.getServiceGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceGroupId(),
                    root -> root.join(Category_.serviceGroups, JoinType.LEFT).get(ServiceGroup_.id)));
            }
        }
        return specification;
    }
}
