package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.Application;
import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.repository.ApplicationRepository;
import au.gov.qld.sir.service.ApplicationService;
import au.gov.qld.sir.service.dto.ApplicationDTO;
import au.gov.qld.sir.service.mapper.ApplicationMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ApplicationCriteria;
import au.gov.qld.sir.service.ApplicationQueryService;

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
 * Integration tests for the {@Link ApplicationResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ApplicationResourceIT {

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationQueryService applicationQueryService;

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

    private MockMvc restApplicationMockMvc;

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationService, applicationQueryService);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
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
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .name(DEFAULT_NAME)
            .migratedBy(DEFAULT_MIGRATED_BY)
            .version(DEFAULT_VERSION);
        return application;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .name(UPDATED_NAME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testApplication.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplication.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testApplication.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplication.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testApplication.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplication.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
        assertThat(testApplication.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setName(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getAllApplicationsByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultApplicationShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the applicationList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultApplicationShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllApplicationsByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultApplicationShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the applicationList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultApplicationShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllApplicationsByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where contactEmail is not null
        defaultApplicationShouldBeFound("contactEmail.specified=true");

        // Get all the applicationList where contactEmail is null
        defaultApplicationShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy is not null
        defaultApplicationShouldBeFound("createdBy.specified=true");

        // Get all the applicationList where createdBy is null
        defaultApplicationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultApplicationShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the applicationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultApplicationShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the applicationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDateTime is not null
        defaultApplicationShouldBeFound("createdDateTime.specified=true");

        // Get all the applicationList where createdDateTime is null
        defaultApplicationShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultApplicationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the applicationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultApplicationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the applicationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedBy is not null
        defaultApplicationShouldBeFound("modifiedBy.specified=true");

        // Get all the applicationList where modifiedBy is null
        defaultApplicationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultApplicationShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the applicationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultApplicationShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the applicationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where modifiedDateTime is not null
        defaultApplicationShouldBeFound("modifiedDateTime.specified=true");

        // Get all the applicationList where modifiedDateTime is null
        defaultApplicationShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where name equals to DEFAULT_NAME
        defaultApplicationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the applicationList where name equals to UPDATED_NAME
        defaultApplicationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApplicationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the applicationList where name equals to UPDATED_NAME
        defaultApplicationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where name is not null
        defaultApplicationShouldBeFound("name.specified=true");

        // Get all the applicationList where name is null
        defaultApplicationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultApplicationShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the applicationList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultApplicationShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the applicationList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where migratedBy is not null
        defaultApplicationShouldBeFound("migratedBy.specified=true");

        // Get all the applicationList where migratedBy is null
        defaultApplicationShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where version equals to DEFAULT_VERSION
        defaultApplicationShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the applicationList where version equals to UPDATED_VERSION
        defaultApplicationShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApplicationShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the applicationList where version equals to UPDATED_VERSION
        defaultApplicationShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where version is not null
        defaultApplicationShouldBeFound("version.specified=true");

        // Get all the applicationList where version is null
        defaultApplicationShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where version greater than or equals to DEFAULT_VERSION
        defaultApplicationShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the applicationList where version greater than or equals to UPDATED_VERSION
        defaultApplicationShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where version less than or equals to DEFAULT_VERSION
        defaultApplicationShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the applicationList where version less than or equals to UPDATED_VERSION
        defaultApplicationShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationsByApplicationServiceOverrideIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverride applicationServiceOverride = ApplicationServiceOverrideResourceIT.createEntity(em);
        em.persist(applicationServiceOverride);
        em.flush();
        application.addApplicationServiceOverride(applicationServiceOverride);
        applicationRepository.saveAndFlush(application);
        Long applicationServiceOverrideId = applicationServiceOverride.getId();

        // Get all the applicationList where applicationServiceOverride equals to applicationServiceOverrideId
        defaultApplicationShouldBeFound("applicationServiceOverrideId.equals=" + applicationServiceOverrideId);

        // Get all the applicationList where applicationServiceOverride equals to applicationServiceOverrideId + 1
        defaultApplicationShouldNotBeFound("applicationServiceOverrideId.equals=" + (applicationServiceOverrideId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationShouldBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));

        // Check, that the count call also returns 1
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationShouldNotBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .name(UPDATED_NAME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        ApplicationDTO applicationDTO = applicationMapper.toDto(updatedApplication);

        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testApplication.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplication.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testApplication.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplication.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplication.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
        assertThat(testApplication.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = new Application();
        application1.setId(1L);
        Application application2 = new Application();
        application2.setId(application1.getId());
        assertThat(application1).isEqualTo(application2);
        application2.setId(2L);
        assertThat(application1).isNotEqualTo(application2);
        application1.setId(null);
        assertThat(application1).isNotEqualTo(application2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationDTO.class);
        ApplicationDTO applicationDTO1 = new ApplicationDTO();
        applicationDTO1.setId(1L);
        ApplicationDTO applicationDTO2 = new ApplicationDTO();
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO2.setId(applicationDTO1.getId());
        assertThat(applicationDTO1).isEqualTo(applicationDTO2);
        applicationDTO2.setId(2L);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO1.setId(null);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationMapper.fromId(null)).isNull();
    }
}
