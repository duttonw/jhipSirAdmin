package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute;
import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.repository.ApplicationServiceOverrideAttributeRepository;
import au.gov.qld.sir.service.ApplicationServiceOverrideAttributeService;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideAttributeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideAttributeCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideAttributeQueryService;

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
 * Integration tests for the {@Link ApplicationServiceOverrideAttributeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ApplicationServiceOverrideAttributeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

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
    private ApplicationServiceOverrideAttributeRepository applicationServiceOverrideAttributeRepository;

    @Autowired
    private ApplicationServiceOverrideAttributeMapper applicationServiceOverrideAttributeMapper;

    @Autowired
    private ApplicationServiceOverrideAttributeService applicationServiceOverrideAttributeService;

    @Autowired
    private ApplicationServiceOverrideAttributeQueryService applicationServiceOverrideAttributeQueryService;

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

    private MockMvc restApplicationServiceOverrideAttributeMockMvc;

    private ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationServiceOverrideAttributeResource applicationServiceOverrideAttributeResource = new ApplicationServiceOverrideAttributeResource(applicationServiceOverrideAttributeService, applicationServiceOverrideAttributeQueryService);
        this.restApplicationServiceOverrideAttributeMockMvc = MockMvcBuilders.standaloneSetup(applicationServiceOverrideAttributeResource)
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
    public static ApplicationServiceOverrideAttribute createEntity(EntityManager em) {
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute = new ApplicationServiceOverrideAttribute()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .migratedBy(DEFAULT_MIGRATED_BY)
            .version(DEFAULT_VERSION);
        // Add required entity
        ApplicationServiceOverride applicationServiceOverride;
        if (TestUtil.findAll(em, ApplicationServiceOverride.class).isEmpty()) {
            applicationServiceOverride = ApplicationServiceOverrideResourceIT.createEntity(em);
            em.persist(applicationServiceOverride);
            em.flush();
        } else {
            applicationServiceOverride = TestUtil.findAll(em, ApplicationServiceOverride.class).get(0);
        }
        applicationServiceOverrideAttribute.setApplicationServiceOverride(applicationServiceOverride);
        return applicationServiceOverrideAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServiceOverrideAttribute createUpdatedEntity(EntityManager em) {
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute = new ApplicationServiceOverrideAttribute()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        // Add required entity
        ApplicationServiceOverride applicationServiceOverride;
        if (TestUtil.findAll(em, ApplicationServiceOverride.class).isEmpty()) {
            applicationServiceOverride = ApplicationServiceOverrideResourceIT.createUpdatedEntity(em);
            em.persist(applicationServiceOverride);
            em.flush();
        } else {
            applicationServiceOverride = TestUtil.findAll(em, ApplicationServiceOverride.class).get(0);
        }
        applicationServiceOverrideAttribute.setApplicationServiceOverride(applicationServiceOverride);
        return applicationServiceOverrideAttribute;
    }

    @BeforeEach
    public void initTest() {
        applicationServiceOverrideAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideAttribute() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideAttributeRepository.findAll().size();

        // Create the ApplicationServiceOverrideAttribute
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttribute);
        restApplicationServiceOverrideAttributeMockMvc.perform(post("/api/application-service-override-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationServiceOverrideAttribute in the database
        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationServiceOverrideAttribute testApplicationServiceOverrideAttribute = applicationServiceOverrideAttributeList.get(applicationServiceOverrideAttributeList.size() - 1);
        assertThat(testApplicationServiceOverrideAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationServiceOverrideAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testApplicationServiceOverrideAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideAttribute.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideAttribute.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideAttributeRepository.findAll().size();

        // Create the ApplicationServiceOverrideAttribute with an existing ID
        applicationServiceOverrideAttribute.setId(1L);
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationServiceOverrideAttributeMockMvc.perform(post("/api/application-service-override-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideAttribute in the database
        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationServiceOverrideAttributeRepository.findAll().size();
        // set the field null
        applicationServiceOverrideAttribute.setName(null);

        // Create the ApplicationServiceOverrideAttribute, which fails.
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttribute);

        restApplicationServiceOverrideAttributeMockMvc.perform(post("/api/application-service-override-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideAttributeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributes() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @Test
    @Transactional
    public void getApplicationServiceOverrideAttribute() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get the applicationServiceOverrideAttribute
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes/{id}", applicationServiceOverrideAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationServiceOverrideAttribute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where name equals to DEFAULT_NAME
        defaultApplicationServiceOverrideAttributeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the applicationServiceOverrideAttributeList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApplicationServiceOverrideAttributeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the applicationServiceOverrideAttributeList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where name is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("name.specified=true");

        // Get all the applicationServiceOverrideAttributeList where name is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where value equals to DEFAULT_VALUE
        defaultApplicationServiceOverrideAttributeShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the applicationServiceOverrideAttributeList where value equals to UPDATED_VALUE
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultApplicationServiceOverrideAttributeShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the applicationServiceOverrideAttributeList where value equals to UPDATED_VALUE
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where value is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("value.specified=true");

        // Get all the applicationServiceOverrideAttributeList where value is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationServiceOverrideAttributeList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationServiceOverrideAttributeList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdBy is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdBy.specified=true");

        // Get all the applicationServiceOverrideAttributeList where createdBy is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideAttributeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideAttributeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where createdDateTime is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("createdDateTime.specified=true");

        // Get all the applicationServiceOverrideAttributeList where createdDateTime is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the applicationServiceOverrideAttributeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the applicationServiceOverrideAttributeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedBy is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedBy.specified=true");

        // Get all the applicationServiceOverrideAttributeList where modifiedBy is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the applicationServiceOverrideAttributeList where modifiedDateTime is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the applicationServiceOverrideAttributeList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideAttributeShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the applicationServiceOverrideAttributeList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where migratedBy is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("migratedBy.specified=true");

        // Get all the applicationServiceOverrideAttributeList where migratedBy is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where version equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideAttributeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideAttributeList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApplicationServiceOverrideAttributeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the applicationServiceOverrideAttributeList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where version is not null
        defaultApplicationServiceOverrideAttributeShouldBeFound("version.specified=true");

        // Get all the applicationServiceOverrideAttributeList where version is null
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where version greater than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideAttributeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideAttributeList where version greater than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        // Get all the applicationServiceOverrideAttributeList where version less than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideAttributeList where version less than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideAttributeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideAttributesByApplicationServiceOverrideIsEqualToSomething() throws Exception {
        // Get already existing entity
        ApplicationServiceOverride applicationServiceOverride = applicationServiceOverrideAttribute.getApplicationServiceOverride();
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);
        Long applicationServiceOverrideId = applicationServiceOverride.getId();

        // Get all the applicationServiceOverrideAttributeList where applicationServiceOverride equals to applicationServiceOverrideId
        defaultApplicationServiceOverrideAttributeShouldBeFound("applicationServiceOverrideId.equals=" + applicationServiceOverrideId);

        // Get all the applicationServiceOverrideAttributeList where applicationServiceOverride equals to applicationServiceOverrideId + 1
        defaultApplicationServiceOverrideAttributeShouldNotBeFound("applicationServiceOverrideId.equals=" + (applicationServiceOverrideId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationServiceOverrideAttributeShouldBeFound(String filter) throws Exception {
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));

        // Check, that the count call also returns 1
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationServiceOverrideAttributeShouldNotBeFound(String filter) throws Exception {
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationServiceOverrideAttribute() throws Exception {
        // Get the applicationServiceOverrideAttribute
        restApplicationServiceOverrideAttributeMockMvc.perform(get("/api/application-service-override-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationServiceOverrideAttribute() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        int databaseSizeBeforeUpdate = applicationServiceOverrideAttributeRepository.findAll().size();

        // Update the applicationServiceOverrideAttribute
        ApplicationServiceOverrideAttribute updatedApplicationServiceOverrideAttribute = applicationServiceOverrideAttributeRepository.findById(applicationServiceOverrideAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationServiceOverrideAttribute are not directly saved in db
        em.detach(updatedApplicationServiceOverrideAttribute);
        updatedApplicationServiceOverrideAttribute
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeMapper.toDto(updatedApplicationServiceOverrideAttribute);

        restApplicationServiceOverrideAttributeMockMvc.perform(put("/api/application-service-override-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationServiceOverrideAttribute in the database
        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationServiceOverrideAttribute testApplicationServiceOverrideAttribute = applicationServiceOverrideAttributeList.get(applicationServiceOverrideAttributeList.size() - 1);
        assertThat(testApplicationServiceOverrideAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationServiceOverrideAttribute.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testApplicationServiceOverrideAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideAttribute.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideAttribute.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideAttribute.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationServiceOverrideAttribute() throws Exception {
        int databaseSizeBeforeUpdate = applicationServiceOverrideAttributeRepository.findAll().size();

        // Create the ApplicationServiceOverrideAttribute
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO = applicationServiceOverrideAttributeMapper.toDto(applicationServiceOverrideAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServiceOverrideAttributeMockMvc.perform(put("/api/application-service-override-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideAttribute in the database
        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationServiceOverrideAttribute() throws Exception {
        // Initialize the database
        applicationServiceOverrideAttributeRepository.saveAndFlush(applicationServiceOverrideAttribute);

        int databaseSizeBeforeDelete = applicationServiceOverrideAttributeRepository.findAll().size();

        // Delete the applicationServiceOverrideAttribute
        restApplicationServiceOverrideAttributeMockMvc.perform(delete("/api/application-service-override-attributes/{id}", applicationServiceOverrideAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationServiceOverrideAttribute> applicationServiceOverrideAttributeList = applicationServiceOverrideAttributeRepository.findAll();
        assertThat(applicationServiceOverrideAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideAttribute.class);
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute1 = new ApplicationServiceOverrideAttribute();
        applicationServiceOverrideAttribute1.setId(1L);
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute2 = new ApplicationServiceOverrideAttribute();
        applicationServiceOverrideAttribute2.setId(applicationServiceOverrideAttribute1.getId());
        assertThat(applicationServiceOverrideAttribute1).isEqualTo(applicationServiceOverrideAttribute2);
        applicationServiceOverrideAttribute2.setId(2L);
        assertThat(applicationServiceOverrideAttribute1).isNotEqualTo(applicationServiceOverrideAttribute2);
        applicationServiceOverrideAttribute1.setId(null);
        assertThat(applicationServiceOverrideAttribute1).isNotEqualTo(applicationServiceOverrideAttribute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideAttributeDTO.class);
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO1 = new ApplicationServiceOverrideAttributeDTO();
        applicationServiceOverrideAttributeDTO1.setId(1L);
        ApplicationServiceOverrideAttributeDTO applicationServiceOverrideAttributeDTO2 = new ApplicationServiceOverrideAttributeDTO();
        assertThat(applicationServiceOverrideAttributeDTO1).isNotEqualTo(applicationServiceOverrideAttributeDTO2);
        applicationServiceOverrideAttributeDTO2.setId(applicationServiceOverrideAttributeDTO1.getId());
        assertThat(applicationServiceOverrideAttributeDTO1).isEqualTo(applicationServiceOverrideAttributeDTO2);
        applicationServiceOverrideAttributeDTO2.setId(2L);
        assertThat(applicationServiceOverrideAttributeDTO1).isNotEqualTo(applicationServiceOverrideAttributeDTO2);
        applicationServiceOverrideAttributeDTO1.setId(null);
        assertThat(applicationServiceOverrideAttributeDTO1).isNotEqualTo(applicationServiceOverrideAttributeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationServiceOverrideAttributeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationServiceOverrideAttributeMapper.fromId(null)).isNull();
    }
}
