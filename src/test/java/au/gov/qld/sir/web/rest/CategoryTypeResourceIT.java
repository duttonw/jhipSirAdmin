package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.CategoryType;
import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.repository.CategoryTypeRepository;
import au.gov.qld.sir.service.CategoryTypeService;
import au.gov.qld.sir.service.dto.CategoryTypeDTO;
import au.gov.qld.sir.service.mapper.CategoryTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.CategoryTypeCriteria;
import au.gov.qld.sir.service.CategoryTypeQueryService;

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
 * Integration tests for the {@Link CategoryTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class CategoryTypeResourceIT {

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

    private static final String DEFAULT_CATEGORY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Autowired
    private CategoryTypeMapper categoryTypeMapper;

    @Autowired
    private CategoryTypeService categoryTypeService;

    @Autowired
    private CategoryTypeQueryService categoryTypeQueryService;

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

    private MockMvc restCategoryTypeMockMvc;

    private CategoryType categoryType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryTypeResource categoryTypeResource = new CategoryTypeResource(categoryTypeService, categoryTypeQueryService);
        this.restCategoryTypeMockMvc = MockMvcBuilders.standaloneSetup(categoryTypeResource)
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
    public static CategoryType createEntity(EntityManager em) {
        CategoryType categoryType = new CategoryType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .categoryType(DEFAULT_CATEGORY_TYPE)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        return categoryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryType createUpdatedEntity(EntityManager em) {
        CategoryType categoryType = new CategoryType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .categoryType(UPDATED_CATEGORY_TYPE)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        return categoryType;
    }

    @BeforeEach
    public void initTest() {
        categoryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryType() throws Exception {
        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();

        // Create the CategoryType
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);
        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCategoryType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testCategoryType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCategoryType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testCategoryType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCategoryType.getCategoryType()).isEqualTo(DEFAULT_CATEGORY_TYPE);
        assertThat(testCategoryType.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testCategoryType.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createCategoryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();

        // Create the CategoryType with an existing ID
        categoryType.setId(1L);
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setCategoryType(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryTypes() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList
        restCategoryTypeMockMvc.perform(get("/api/category-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].categoryType").value(hasItem(DEFAULT_CATEGORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get the categoryType
        restCategoryTypeMockMvc.perform(get("/api/category-types/{id}", categoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoryType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.categoryType").value(DEFAULT_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultCategoryTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the categoryTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultCategoryTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCategoryTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the categoryTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultCategoryTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdBy is not null
        defaultCategoryTypeShouldBeFound("createdBy.specified=true");

        // Get all the categoryTypeList where createdBy is null
        defaultCategoryTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultCategoryTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the categoryTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultCategoryTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultCategoryTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the categoryTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultCategoryTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where createdDateTime is not null
        defaultCategoryTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the categoryTypeList where createdDateTime is null
        defaultCategoryTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultCategoryTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the categoryTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCategoryTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultCategoryTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the categoryTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCategoryTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedBy is not null
        defaultCategoryTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the categoryTypeList where modifiedBy is null
        defaultCategoryTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultCategoryTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the categoryTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultCategoryTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultCategoryTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the categoryTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultCategoryTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where modifiedDateTime is not null
        defaultCategoryTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the categoryTypeList where modifiedDateTime is null
        defaultCategoryTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where version equals to DEFAULT_VERSION
        defaultCategoryTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the categoryTypeList where version equals to UPDATED_VERSION
        defaultCategoryTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultCategoryTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the categoryTypeList where version equals to UPDATED_VERSION
        defaultCategoryTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where version is not null
        defaultCategoryTypeShouldBeFound("version.specified=true");

        // Get all the categoryTypeList where version is null
        defaultCategoryTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where version greater than or equals to DEFAULT_VERSION
        defaultCategoryTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the categoryTypeList where version greater than or equals to UPDATED_VERSION
        defaultCategoryTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where version less than or equals to DEFAULT_VERSION
        defaultCategoryTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the categoryTypeList where version less than or equals to UPDATED_VERSION
        defaultCategoryTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllCategoryTypesByCategoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where categoryType equals to DEFAULT_CATEGORY_TYPE
        defaultCategoryTypeShouldBeFound("categoryType.equals=" + DEFAULT_CATEGORY_TYPE);

        // Get all the categoryTypeList where categoryType equals to UPDATED_CATEGORY_TYPE
        defaultCategoryTypeShouldNotBeFound("categoryType.equals=" + UPDATED_CATEGORY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCategoryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where categoryType in DEFAULT_CATEGORY_TYPE or UPDATED_CATEGORY_TYPE
        defaultCategoryTypeShouldBeFound("categoryType.in=" + DEFAULT_CATEGORY_TYPE + "," + UPDATED_CATEGORY_TYPE);

        // Get all the categoryTypeList where categoryType equals to UPDATED_CATEGORY_TYPE
        defaultCategoryTypeShouldNotBeFound("categoryType.in=" + UPDATED_CATEGORY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByCategoryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where categoryType is not null
        defaultCategoryTypeShouldBeFound("categoryType.specified=true");

        // Get all the categoryTypeList where categoryType is null
        defaultCategoryTypeShouldNotBeFound("categoryType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migrated equals to DEFAULT_MIGRATED
        defaultCategoryTypeShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the categoryTypeList where migrated equals to UPDATED_MIGRATED
        defaultCategoryTypeShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultCategoryTypeShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the categoryTypeList where migrated equals to UPDATED_MIGRATED
        defaultCategoryTypeShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migrated is not null
        defaultCategoryTypeShouldBeFound("migrated.specified=true");

        // Get all the categoryTypeList where migrated is null
        defaultCategoryTypeShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultCategoryTypeShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the categoryTypeList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultCategoryTypeShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultCategoryTypeShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the categoryTypeList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultCategoryTypeShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList where migratedBy is not null
        defaultCategoryTypeShouldBeFound("migratedBy.specified=true");

        // Get all the categoryTypeList where migratedBy is null
        defaultCategoryTypeShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryTypesByServiceGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceGroup serviceGroup = ServiceGroupResourceIT.createEntity(em);
        em.persist(serviceGroup);
        em.flush();
        categoryType.addServiceGroup(serviceGroup);
        categoryTypeRepository.saveAndFlush(categoryType);
        Long serviceGroupId = serviceGroup.getId();

        // Get all the categoryTypeList where serviceGroup equals to serviceGroupId
        defaultCategoryTypeShouldBeFound("serviceGroupId.equals=" + serviceGroupId);

        // Get all the categoryTypeList where serviceGroup equals to serviceGroupId + 1
        defaultCategoryTypeShouldNotBeFound("serviceGroupId.equals=" + (serviceGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryTypeShouldBeFound(String filter) throws Exception {
        restCategoryTypeMockMvc.perform(get("/api/category-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].categoryType").value(hasItem(DEFAULT_CATEGORY_TYPE)))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restCategoryTypeMockMvc.perform(get("/api/category-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryTypeShouldNotBeFound(String filter) throws Exception {
        restCategoryTypeMockMvc.perform(get("/api/category-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryTypeMockMvc.perform(get("/api/category-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoryType() throws Exception {
        // Get the categoryType
        restCategoryTypeMockMvc.perform(get("/api/category-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Update the categoryType
        CategoryType updatedCategoryType = categoryTypeRepository.findById(categoryType.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryType are not directly saved in db
        em.detach(updatedCategoryType);
        updatedCategoryType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .categoryType(UPDATED_CATEGORY_TYPE)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(updatedCategoryType);

        restCategoryTypeMockMvc.perform(put("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCategoryType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testCategoryType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCategoryType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testCategoryType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCategoryType.getCategoryType()).isEqualTo(UPDATED_CATEGORY_TYPE);
        assertThat(testCategoryType.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testCategoryType.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Create the CategoryType
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc.perform(put("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeDelete = categoryTypeRepository.findAll().size();

        // Delete the categoryType
        restCategoryTypeMockMvc.perform(delete("/api/category-types/{id}", categoryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryType.class);
        CategoryType categoryType1 = new CategoryType();
        categoryType1.setId(1L);
        CategoryType categoryType2 = new CategoryType();
        categoryType2.setId(categoryType1.getId());
        assertThat(categoryType1).isEqualTo(categoryType2);
        categoryType2.setId(2L);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
        categoryType1.setId(null);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTypeDTO.class);
        CategoryTypeDTO categoryTypeDTO1 = new CategoryTypeDTO();
        categoryTypeDTO1.setId(1L);
        CategoryTypeDTO categoryTypeDTO2 = new CategoryTypeDTO();
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
        categoryTypeDTO2.setId(categoryTypeDTO1.getId());
        assertThat(categoryTypeDTO1).isEqualTo(categoryTypeDTO2);
        categoryTypeDTO2.setId(2L);
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
        categoryTypeDTO1.setId(null);
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoryTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoryTypeMapper.fromId(null)).isNull();
    }
}
