package au.gov.qld.sir.service;

import au.gov.qld.sir.domain.CategoryType;
import au.gov.qld.sir.repository.CategoryTypeRepository;
import au.gov.qld.sir.service.dto.CategoryTypeDTO;
import au.gov.qld.sir.service.mapper.CategoryTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryType}.
 */
@Service
@Transactional
public class CategoryTypeService {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeService.class);

    private final CategoryTypeRepository categoryTypeRepository;

    private final CategoryTypeMapper categoryTypeMapper;

    public CategoryTypeService(CategoryTypeRepository categoryTypeRepository, CategoryTypeMapper categoryTypeMapper) {
        this.categoryTypeRepository = categoryTypeRepository;
        this.categoryTypeMapper = categoryTypeMapper;
    }

    /**
     * Save a categoryType.
     *
     * @param categoryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoryTypeDTO save(CategoryTypeDTO categoryTypeDTO) {
        log.debug("Request to save CategoryType : {}", categoryTypeDTO);
        CategoryType categoryType = categoryTypeMapper.toEntity(categoryTypeDTO);
        categoryType = categoryTypeRepository.save(categoryType);
        return categoryTypeMapper.toDto(categoryType);
    }

    /**
     * Get all the categoryTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryTypes");
        return categoryTypeRepository.findAll(pageable)
            .map(categoryTypeMapper::toDto);
    }


    /**
     * Get one categoryType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoryTypeDTO> findOne(Long id) {
        log.debug("Request to get CategoryType : {}", id);
        return categoryTypeRepository.findById(id)
            .map(categoryTypeMapper::toDto);
    }

    /**
     * Delete the categoryType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoryType : {}", id);
        categoryTypeRepository.deleteById(id);
    }
}
