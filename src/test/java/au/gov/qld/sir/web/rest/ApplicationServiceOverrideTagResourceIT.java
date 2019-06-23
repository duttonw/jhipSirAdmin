package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagRepository;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagService;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagQueryService;

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
 * Integration tests for the {@Link ApplicationServiceOverrideTagResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ApplicationServiceOverrideTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private ApplicationServiceOverrideTagRepository applicationServiceOverrideTagRepository;

    @Autowired
    private ApplicationServiceOverrideTagMapper applicationServiceOverrideTagMapper;

    @Autowired
    private ApplicationServiceOverrideTagService applicationServiceOverrideTagService;

    @Autowired
    private ApplicationServiceOverrideTagQueryService applicationServiceOverrideTagQueryService;

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

    private MockMvc restApplicationServiceOverrideTagMockMvc;

    private ApplicationServiceOverrideTag applicationServiceOverrideTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationServiceOverrideTagResource applicationServiceOverrideTagResource = new ApplicationServiceOverrideTagResource(applicationServiceOverrideTagService, applicationServiceOverrideTagQueryService);
        this.restApplicationServiceOverrideTagMockMvc = MockMvcBuilders.standaloneSetup(applicationServiceOverrideTagResource)
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
    public static ApplicationServiceOverrideTag createEntity(EntityManager em) {
        ApplicationServiceOverrideTag applicationServiceOverrideTag = new ApplicationServiceOverrideTag()
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .migratedBy(DEFAULT_MIGRATED_BY)
            .version(DEFAULT_VERSION);
        return applicationServiceOverrideTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServiceOverrideTag createUpdatedEntity(EntityManager em) {
        ApplicationServiceOverrideTag applicationServiceOverrideTag = new ApplicationServiceOverrideTag()
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        return applicationServiceOverrideTag;
    }

    @BeforeEach
    public void initTest() {
        applicationServiceOverrideTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideTag() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideTagRepository.findAll().size();

        // Create the ApplicationServiceOverrideTag
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO = applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTag);
        restApplicationServiceOverrideTagMockMvc.perform(post("/api/application-service-override-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationServiceOverrideTag in the database
        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationServiceOverrideTag testApplicationServiceOverrideTag = applicationServiceOverrideTagList.get(applicationServiceOverrideTagList.size() - 1);
        assertThat(testApplicationServiceOverrideTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationServiceOverrideTag.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationServiceOverrideTag.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTag.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideTag.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTag.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideTag.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideTagRepository.findAll().size();

        // Create the ApplicationServiceOverrideTag with an existing ID
        applicationServiceOverrideTag.setId(1L);
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO = applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationServiceOverrideTagMockMvc.perform(post("/api/application-service-override-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideTag in the database
        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationServiceOverrideTagRepository.findAll().size();
        // set the field null
        applicationServiceOverrideTag.setName(null);

        // Create the ApplicationServiceOverrideTag, which fails.
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO = applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTag);

        restApplicationServiceOverrideTagMockMvc.perform(post("/api/application-service-override-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTags() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @Test
    @Transactional
    public void getApplicationServiceOverrideTag() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get the applicationServiceOverrideTag
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags/{id}", applicationServiceOverrideTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationServiceOverrideTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where name equals to DEFAULT_NAME
        defaultApplicationServiceOverrideTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the applicationServiceOverrideTagList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApplicationServiceOverrideTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the applicationServiceOverrideTagList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where name is not null
        defaultApplicationServiceOverrideTagShouldBeFound("name.specified=true");

        // Get all the applicationServiceOverrideTagList where name is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationServiceOverrideTagList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationServiceOverrideTagList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdBy is not null
        defaultApplicationServiceOverrideTagShouldBeFound("createdBy.specified=true");

        // Get all the applicationServiceOverrideTagList where createdBy is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideTagList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideTagList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where createdDateTime is not null
        defaultApplicationServiceOverrideTagShouldBeFound("createdDateTime.specified=true");

        // Get all the applicationServiceOverrideTagList where createdDateTime is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the applicationServiceOverrideTagList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the applicationServiceOverrideTagList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedBy is not null
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedBy.specified=true");

        // Get all the applicationServiceOverrideTagList where modifiedBy is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideTagList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideTagList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where modifiedDateTime is not null
        defaultApplicationServiceOverrideTagShouldBeFound("modifiedDateTime.specified=true");

        // Get all the applicationServiceOverrideTagList where modifiedDateTime is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the applicationServiceOverrideTagList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the applicationServiceOverrideTagList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where migratedBy is not null
        defaultApplicationServiceOverrideTagShouldBeFound("migratedBy.specified=true");

        // Get all the applicationServiceOverrideTagList where migratedBy is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where version equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApplicationServiceOverrideTagShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the applicationServiceOverrideTagList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where version is not null
        defaultApplicationServiceOverrideTagShouldBeFound("version.specified=true");

        // Get all the applicationServiceOverrideTagList where version is null
        defaultApplicationServiceOverrideTagShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where version greater than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagList where version greater than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        // Get all the applicationServiceOverrideTagList where version less than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagList where version less than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverrideTag parent = ApplicationServiceOverrideTagResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        applicationServiceOverrideTag.setParent(parent);
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);
        Long parentId = parent.getId();

        // Get all the applicationServiceOverrideTagList where parent equals to parentId
        defaultApplicationServiceOverrideTagShouldBeFound("parentId.equals=" + parentId);

        // Get all the applicationServiceOverrideTagList where parent equals to parentId + 1
        defaultApplicationServiceOverrideTagShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByApplicationServiceOverrideTagIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverrideTag applicationServiceOverrideTag = ApplicationServiceOverrideTagResourceIT.createEntity(em);
        em.persist(applicationServiceOverrideTag);
        em.flush();
        applicationServiceOverrideTag.addApplicationServiceOverrideTag(applicationServiceOverrideTag);
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);
        Long applicationServiceOverrideTagId = applicationServiceOverrideTag.getId();

        // Get all the applicationServiceOverrideTagList where applicationServiceOverrideTag equals to applicationServiceOverrideTagId
        defaultApplicationServiceOverrideTagShouldBeFound("applicationServiceOverrideTagId.equals=" + applicationServiceOverrideTagId);

        // Get all the applicationServiceOverrideTagList where applicationServiceOverrideTag equals to applicationServiceOverrideTagId + 1
        defaultApplicationServiceOverrideTagShouldNotBeFound("applicationServiceOverrideTagId.equals=" + (applicationServiceOverrideTagId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagsByApplicationServiceOverrideTagItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = ApplicationServiceOverrideTagItemsResourceIT.createEntity(em);
        em.persist(applicationServiceOverrideTagItems);
        em.flush();
        applicationServiceOverrideTag.addApplicationServiceOverrideTagItems(applicationServiceOverrideTagItems);
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);
        Long applicationServiceOverrideTagItemsId = applicationServiceOverrideTagItems.getId();

        // Get all the applicationServiceOverrideTagList where applicationServiceOverrideTagItems equals to applicationServiceOverrideTagItemsId
        defaultApplicationServiceOverrideTagShouldBeFound("applicationServiceOverrideTagItemsId.equals=" + applicationServiceOverrideTagItemsId);

        // Get all the applicationServiceOverrideTagList where applicationServiceOverrideTagItems equals to applicationServiceOverrideTagItemsId + 1
        defaultApplicationServiceOverrideTagShouldNotBeFound("applicationServiceOverrideTagItemsId.equals=" + (applicationServiceOverrideTagItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationServiceOverrideTagShouldBeFound(String filter) throws Exception {
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));

        // Check, that the count call also returns 1
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationServiceOverrideTagShouldNotBeFound(String filter) throws Exception {
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationServiceOverrideTag() throws Exception {
        // Get the applicationServiceOverrideTag
        restApplicationServiceOverrideTagMockMvc.perform(get("/api/application-service-override-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationServiceOverrideTag() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        int databaseSizeBeforeUpdate = applicationServiceOverrideTagRepository.findAll().size();

        // Update the applicationServiceOverrideTag
        ApplicationServiceOverrideTag updatedApplicationServiceOverrideTag = applicationServiceOverrideTagRepository.findById(applicationServiceOverrideTag.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationServiceOverrideTag are not directly saved in db
        em.detach(updatedApplicationServiceOverrideTag);
        updatedApplicationServiceOverrideTag
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO = applicationServiceOverrideTagMapper.toDto(updatedApplicationServiceOverrideTag);

        restApplicationServiceOverrideTagMockMvc.perform(put("/api/application-service-override-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationServiceOverrideTag in the database
        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeUpdate);
        ApplicationServiceOverrideTag testApplicationServiceOverrideTag = applicationServiceOverrideTagList.get(applicationServiceOverrideTagList.size() - 1);
        assertThat(testApplicationServiceOverrideTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationServiceOverrideTag.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationServiceOverrideTag.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTag.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideTag.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTag.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideTag.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationServiceOverrideTag() throws Exception {
        int databaseSizeBeforeUpdate = applicationServiceOverrideTagRepository.findAll().size();

        // Create the ApplicationServiceOverrideTag
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO = applicationServiceOverrideTagMapper.toDto(applicationServiceOverrideTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServiceOverrideTagMockMvc.perform(put("/api/application-service-override-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideTag in the database
        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationServiceOverrideTag() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagRepository.saveAndFlush(applicationServiceOverrideTag);

        int databaseSizeBeforeDelete = applicationServiceOverrideTagRepository.findAll().size();

        // Delete the applicationServiceOverrideTag
        restApplicationServiceOverrideTagMockMvc.perform(delete("/api/application-service-override-tags/{id}", applicationServiceOverrideTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationServiceOverrideTag> applicationServiceOverrideTagList = applicationServiceOverrideTagRepository.findAll();
        assertThat(applicationServiceOverrideTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideTag.class);
        ApplicationServiceOverrideTag applicationServiceOverrideTag1 = new ApplicationServiceOverrideTag();
        applicationServiceOverrideTag1.setId(1L);
        ApplicationServiceOverrideTag applicationServiceOverrideTag2 = new ApplicationServiceOverrideTag();
        applicationServiceOverrideTag2.setId(applicationServiceOverrideTag1.getId());
        assertThat(applicationServiceOverrideTag1).isEqualTo(applicationServiceOverrideTag2);
        applicationServiceOverrideTag2.setId(2L);
        assertThat(applicationServiceOverrideTag1).isNotEqualTo(applicationServiceOverrideTag2);
        applicationServiceOverrideTag1.setId(null);
        assertThat(applicationServiceOverrideTag1).isNotEqualTo(applicationServiceOverrideTag2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideTagDTO.class);
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO1 = new ApplicationServiceOverrideTagDTO();
        applicationServiceOverrideTagDTO1.setId(1L);
        ApplicationServiceOverrideTagDTO applicationServiceOverrideTagDTO2 = new ApplicationServiceOverrideTagDTO();
        assertThat(applicationServiceOverrideTagDTO1).isNotEqualTo(applicationServiceOverrideTagDTO2);
        applicationServiceOverrideTagDTO2.setId(applicationServiceOverrideTagDTO1.getId());
        assertThat(applicationServiceOverrideTagDTO1).isEqualTo(applicationServiceOverrideTagDTO2);
        applicationServiceOverrideTagDTO2.setId(2L);
        assertThat(applicationServiceOverrideTagDTO1).isNotEqualTo(applicationServiceOverrideTagDTO2);
        applicationServiceOverrideTagDTO1.setId(null);
        assertThat(applicationServiceOverrideTagDTO1).isNotEqualTo(applicationServiceOverrideTagDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationServiceOverrideTagMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationServiceOverrideTagMapper.fromId(null)).isNull();
    }
}
