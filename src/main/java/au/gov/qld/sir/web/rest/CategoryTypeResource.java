package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.service.CategoryTypeService;
import au.gov.qld.sir.web.rest.errors.BadRequestAlertException;
import au.gov.qld.sir.service.dto.CategoryTypeDTO;
import au.gov.qld.sir.service.dto.CategoryTypeCriteria;
import au.gov.qld.sir.service.CategoryTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link au.gov.qld.sir.domain.CategoryType}.
 */
@RestController
@RequestMapping("/api")
public class CategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeResource.class);

    private static final String ENTITY_NAME = "categoryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryTypeService categoryTypeService;

    private final CategoryTypeQueryService categoryTypeQueryService;

    public CategoryTypeResource(CategoryTypeService categoryTypeService, CategoryTypeQueryService categoryTypeQueryService) {
        this.categoryTypeService = categoryTypeService;
        this.categoryTypeQueryService = categoryTypeQueryService;
    }

    /**
     * {@code POST  /category-types} : Create a new categoryType.
     *
     * @param categoryTypeDTO the categoryTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryTypeDTO, or with status {@code 400 (Bad Request)} if the categoryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-types")
    public ResponseEntity<CategoryTypeDTO> createCategoryType(@Valid @RequestBody CategoryTypeDTO categoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryType : {}", categoryTypeDTO);
        if (categoryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryTypeDTO result = categoryTypeService.save(categoryTypeDTO);
        return ResponseEntity.created(new URI("/api/category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-types} : Updates an existing categoryType.
     *
     * @param categoryTypeDTO the categoryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the categoryTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-types")
    public ResponseEntity<CategoryTypeDTO> updateCategoryType(@Valid @RequestBody CategoryTypeDTO categoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryType : {}", categoryTypeDTO);
        if (categoryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryTypeDTO result = categoryTypeService.save(categoryTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-types} : get all the categoryTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryTypes in body.
     */
    @GetMapping("/category-types")
    public ResponseEntity<List<CategoryTypeDTO>> getAllCategoryTypes(CategoryTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CategoryTypes by criteria: {}", criteria);
        Page<CategoryTypeDTO> page = categoryTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /category-types/count} : count all the categoryTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/category-types/count")
    public ResponseEntity<Long> countCategoryTypes(CategoryTypeCriteria criteria) {
        log.debug("REST request to count CategoryTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoryTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /category-types/:id} : get the "id" categoryType.
     *
     * @param id the id of the categoryTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-types/{id}")
    public ResponseEntity<CategoryTypeDTO> getCategoryType(@PathVariable Long id) {
        log.debug("REST request to get CategoryType : {}", id);
        Optional<CategoryTypeDTO> categoryTypeDTO = categoryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryTypeDTO);
    }

    /**
     * {@code DELETE  /category-types/:id} : delete the "id" categoryType.
     *
     * @param id the id of the categoryTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-types/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Long id) {
        log.debug("REST request to delete CategoryType : {}", id);
        categoryTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
