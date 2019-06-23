package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.Category;
import au.gov.qld.sir.domain.ServiceEvidence;
import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.repository.CategoryRepository;
import au.gov.qld.sir.service.CategoryService;
import au.gov.qld.sir.service.dto.CategoryDTO;
import au.gov.qld.sir.service.mapper.CategoryMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.CategoryCriteria;
import au.gov.qld.sir.service.CategoryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static au.gov.qld.sir.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CategoryResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class CategoryResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryQueryService categoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCategoryMockMvc;

    private Category category;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryResource categoryResource = new CategoryResource(categoryService, categoryQueryService);
        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .category(DEFAULT_CATEGORY)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        return category;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .category(UPDATED_CATEGORY)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCategory.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testCategory.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCategory.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testCategory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCategory.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testCategory.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId(1L);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setCategory(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultCategoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the categoryList where createdBy equals to UPDATED_CREATED_BY
        defaultCategoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCategoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the categoryList where createdBy equals to UPDATED_CREATED_BY
        defaultCategoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdBy is not null
        defaultCategoryShouldBeFound("createdBy.specified=true");

        // Get all the categoryList where createdBy is null
        defaultCategoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultCategoryShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the categoryList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultCategoryShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultCategoryShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the categoryList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultCategoryShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where createdDateTime is not null
        defaultCategoryShouldBeFound("createdDateTime.specified=true");

        // Get all the categoryList where createdDateTime is null
        defaultCategoryShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultCategoryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the categoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCategoryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultCategoryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the categoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCategoryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedBy is not null
        defaultCategoryShouldBeFound("modifiedBy.specified=true");

        // Get all the categoryList where modifiedBy is null
        defaultCategoryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultCategoryShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the categoryList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultCategoryShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultCategoryShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the categoryList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultCategoryShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoriesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where modifiedDateTime is not null
        defaultCategoryShouldBeFound("modifiedDateTime.specified=true");

        // Get all the categoryList where modifiedDateTime is null
        defaultCategoryShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where version equals to DEFAULT_VERSION
        defaultCategoryShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the categoryList where version equals to UPDATED_VERSION
        defaultCategoryShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultCategoryShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the categoryList where version equals to UPDATED_VERSION
        defaultCategoryShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where version is not null
        defaultCategoryShouldBeFound("version.specified=true");

        // Get all the categoryList where version is null
        defaultCategoryShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where version greater than or equals to DEFAULT_VERSION
        defaultCategoryShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the categoryList where version greater than or equals to UPDATED_VERSION
        defaultCategoryShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where version less than or equals to DEFAULT_VERSION
        defaultCategoryShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the categoryList where version less than or equals to UPDATED_VERSION
        defaultCategoryShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where category equals to DEFAULT_CATEGORY
        defaultCategoryShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the categoryList where category equals to UPDATED_CATEGORY
        defaultCategoryShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultCategoryShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the categoryList where category equals to UPDATED_CATEGORY
        defaultCategoryShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where category is not null
        defaultCategoryShouldBeFound("category.specified=true");

        // Get all the categoryList where category is null
        defaultCategoryShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migrated equals to DEFAULT_MIGRATED
        defaultCategoryShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the categoryList where migrated equals to UPDATED_MIGRATED
        defaultCategoryShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultCategoryShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the categoryList where migrated equals to UPDATED_MIGRATED
        defaultCategoryShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migrated is not null
        defaultCategoryShouldBeFound("migrated.specified=true");

        // Get all the categoryList where migrated is null
        defaultCategoryShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultCategoryShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the categoryList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultCategoryShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultCategoryShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the categoryList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultCategoryShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where migratedBy is not null
        defaultCategoryShouldBeFound("migratedBy.specified=true");

        // Get all the categoryList where migratedBy is null
        defaultCategoryShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByServiceEvidenceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceEvidence serviceEvidence = ServiceEvidenceResourceIT.createEntity(em);
        em.persist(serviceEvidence);
        em.flush();
        category.addServiceEvidence(serviceEvidence);
        categoryRepository.saveAndFlush(category);
        Long serviceEvidenceId = serviceEvidence.getId();

        // Get all the categoryList where serviceEvidence equals to serviceEvidenceId
        defaultCategoryShouldBeFound("serviceEvidenceId.equals=" + serviceEvidenceId);

        // Get all the categoryList where serviceEvidence equals to serviceEvidenceId + 1
        defaultCategoryShouldNotBeFound("serviceEvidenceId.equals=" + (serviceEvidenceId + 1));
    }


    @Test
    @Transactional
    public void getAllCategoriesByServiceGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceGroup serviceGroup = ServiceGroupResourceIT.createEntity(em);
        em.persist(serviceGroup);
        em.flush();
        category.addServiceGroup(serviceGroup);
        categoryRepository.saveAndFlush(category);
        Long serviceGroupId = serviceGroup.getId();

        // Get all the categoryList where serviceGroup equals to serviceGroupId
        defaultCategoryShouldBeFound("serviceGroupId.equals=" + serviceGroupId);

        // Get all the categoryList where serviceGroup equals to serviceGroupId + 1
        defaultCategoryShouldNotBeFound("serviceGroupId.equals=" + (serviceGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryShouldBeFound(String filter) throws Exception {
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryShouldNotBeFound(String filter) throws Exception {
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .category(UPDATED_CATEGORY)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        CategoryDTO categoryDTO = categoryMapper.toDto(updatedCategory);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCategory.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testCategory.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCategory.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testCategory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCategory.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testCategory.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = new Category();
        category1.setId(1L);
        Category category2 = new Category();
        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);
        category2.setId(2L);
        assertThat(category1).isNotEqualTo(category2);
        category1.setId(null);
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDTO.class);
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        CategoryDTO categoryDTO2 = new CategoryDTO();
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO2.setId(categoryDTO1.getId());
        assertThat(categoryDTO1).isEqualTo(categoryDTO2);
        categoryDTO2.setId(2L);
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO1.setId(null);
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoryMapper.fromId(null)).isNull();
    }
}
