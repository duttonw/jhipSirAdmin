package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.Application;
import au.gov.qld.sir.domain.ApplicationServiceOverrideAttribute;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import au.gov.qld.sir.repository.ApplicationServiceOverrideRepository;
import au.gov.qld.sir.service.ApplicationServiceOverrideService;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideQueryService;

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
 * Integration tests for the {@Link ApplicationServiceOverrideResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ApplicationServiceOverrideResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ELIGIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBILITY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_REQUISITES = "AAAAAAAAAA";
    private static final String UPDATED_PRE_REQUISITES = "BBBBBBBBBB";

    private static final String DEFAULT_FEES = "AAAAAAAAAA";
    private static final String UPDATED_FEES = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE = "A";
    private static final String UPDATED_ACTIVE = "B";

    private static final String DEFAULT_REFERENCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_URL = "BBBBBBBBBB";

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

    private static final String DEFAULT_HOW_TO = "AAAAAAAAAA";
    private static final String UPDATED_HOW_TO = "BBBBBBBBBB";

    @Autowired
    private ApplicationServiceOverrideRepository applicationServiceOverrideRepository;

    @Autowired
    private ApplicationServiceOverrideMapper applicationServiceOverrideMapper;

    @Autowired
    private ApplicationServiceOverrideService applicationServiceOverrideService;

    @Autowired
    private ApplicationServiceOverrideQueryService applicationServiceOverrideQueryService;

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

    private MockMvc restApplicationServiceOverrideMockMvc;

    private ApplicationServiceOverride applicationServiceOverride;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationServiceOverrideResource applicationServiceOverrideResource = new ApplicationServiceOverrideResource(applicationServiceOverrideService, applicationServiceOverrideQueryService);
        this.restApplicationServiceOverrideMockMvc = MockMvcBuilders.standaloneSetup(applicationServiceOverrideResource)
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
    public static ApplicationServiceOverride createEntity(EntityManager em) {
        ApplicationServiceOverride applicationServiceOverride = new ApplicationServiceOverride()
            .description(DEFAULT_DESCRIPTION)
            .eligibility(DEFAULT_ELIGIBILITY)
            .keywords(DEFAULT_KEYWORDS)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .name(DEFAULT_NAME)
            .preRequisites(DEFAULT_PRE_REQUISITES)
            .fees(DEFAULT_FEES)
            .active(DEFAULT_ACTIVE)
            .referenceUrl(DEFAULT_REFERENCE_URL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .migratedBy(DEFAULT_MIGRATED_BY)
            .version(DEFAULT_VERSION)
            .howTo(DEFAULT_HOW_TO);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        applicationServiceOverride.setServiceRecord(serviceRecord);
        // Add required entity
        Application application;
        if (TestUtil.findAll(em, Application.class).isEmpty()) {
            application = ApplicationResourceIT.createEntity(em);
            em.persist(application);
            em.flush();
        } else {
            application = TestUtil.findAll(em, Application.class).get(0);
        }
        applicationServiceOverride.setApplication(application);
        return applicationServiceOverride;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServiceOverride createUpdatedEntity(EntityManager em) {
        ApplicationServiceOverride applicationServiceOverride = new ApplicationServiceOverride()
            .description(UPDATED_DESCRIPTION)
            .eligibility(UPDATED_ELIGIBILITY)
            .keywords(UPDATED_KEYWORDS)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .name(UPDATED_NAME)
            .preRequisites(UPDATED_PRE_REQUISITES)
            .fees(UPDATED_FEES)
            .active(UPDATED_ACTIVE)
            .referenceUrl(UPDATED_REFERENCE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION)
            .howTo(UPDATED_HOW_TO);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createUpdatedEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        applicationServiceOverride.setServiceRecord(serviceRecord);
        // Add required entity
        Application application;
        if (TestUtil.findAll(em, Application.class).isEmpty()) {
            application = ApplicationResourceIT.createUpdatedEntity(em);
            em.persist(application);
            em.flush();
        } else {
            application = TestUtil.findAll(em, Application.class).get(0);
        }
        applicationServiceOverride.setApplication(application);
        return applicationServiceOverride;
    }

    @BeforeEach
    public void initTest() {
        applicationServiceOverride = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverride() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideRepository.findAll().size();

        // Create the ApplicationServiceOverride
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO = applicationServiceOverrideMapper.toDto(applicationServiceOverride);
        restApplicationServiceOverrideMockMvc.perform(post("/api/application-service-overrides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationServiceOverride in the database
        List<ApplicationServiceOverride> applicationServiceOverrideList = applicationServiceOverrideRepository.findAll();
        assertThat(applicationServiceOverrideList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationServiceOverride testApplicationServiceOverride = applicationServiceOverrideList.get(applicationServiceOverrideList.size() - 1);
        assertThat(testApplicationServiceOverride.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationServiceOverride.getEligibility()).isEqualTo(DEFAULT_ELIGIBILITY);
        assertThat(testApplicationServiceOverride.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testApplicationServiceOverride.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testApplicationServiceOverride.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationServiceOverride.getPreRequisites()).isEqualTo(DEFAULT_PRE_REQUISITES);
        assertThat(testApplicationServiceOverride.getFees()).isEqualTo(DEFAULT_FEES);
        assertThat(testApplicationServiceOverride.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApplicationServiceOverride.getReferenceUrl()).isEqualTo(DEFAULT_REFERENCE_URL);
        assertThat(testApplicationServiceOverride.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationServiceOverride.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverride.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplicationServiceOverride.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverride.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
        assertThat(testApplicationServiceOverride.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testApplicationServiceOverride.getHowTo()).isEqualTo(DEFAULT_HOW_TO);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideRepository.findAll().size();

        // Create the ApplicationServiceOverride with an existing ID
        applicationServiceOverride.setId(1L);
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO = applicationServiceOverrideMapper.toDto(applicationServiceOverride);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationServiceOverrideMockMvc.perform(post("/api/application-service-overrides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverride in the database
        List<ApplicationServiceOverride> applicationServiceOverrideList = applicationServiceOverrideRepository.findAll();
        assertThat(applicationServiceOverrideList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrides() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverride.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].eligibility").value(hasItem(DEFAULT_ELIGIBILITY.toString())))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].preRequisites").value(hasItem(DEFAULT_PRE_REQUISITES.toString())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].referenceUrl").value(hasItem(DEFAULT_REFERENCE_URL.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].howTo").value(hasItem(DEFAULT_HOW_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationServiceOverride() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get the applicationServiceOverride
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides/{id}", applicationServiceOverride.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationServiceOverride.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.eligibility").value(DEFAULT_ELIGIBILITY.toString()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.preRequisites").value(DEFAULT_PRE_REQUISITES.toString()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.toString()))
            .andExpect(jsonPath("$.referenceUrl").value(DEFAULT_REFERENCE_URL.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.howTo").value(DEFAULT_HOW_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where description equals to DEFAULT_DESCRIPTION
        defaultApplicationServiceOverrideShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the applicationServiceOverrideList where description equals to UPDATED_DESCRIPTION
        defaultApplicationServiceOverrideShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApplicationServiceOverrideShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the applicationServiceOverrideList where description equals to UPDATED_DESCRIPTION
        defaultApplicationServiceOverrideShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where description is not null
        defaultApplicationServiceOverrideShouldBeFound("description.specified=true");

        // Get all the applicationServiceOverrideList where description is null
        defaultApplicationServiceOverrideShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByEligibilityIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where eligibility equals to DEFAULT_ELIGIBILITY
        defaultApplicationServiceOverrideShouldBeFound("eligibility.equals=" + DEFAULT_ELIGIBILITY);

        // Get all the applicationServiceOverrideList where eligibility equals to UPDATED_ELIGIBILITY
        defaultApplicationServiceOverrideShouldNotBeFound("eligibility.equals=" + UPDATED_ELIGIBILITY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByEligibilityIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where eligibility in DEFAULT_ELIGIBILITY or UPDATED_ELIGIBILITY
        defaultApplicationServiceOverrideShouldBeFound("eligibility.in=" + DEFAULT_ELIGIBILITY + "," + UPDATED_ELIGIBILITY);

        // Get all the applicationServiceOverrideList where eligibility equals to UPDATED_ELIGIBILITY
        defaultApplicationServiceOverrideShouldNotBeFound("eligibility.in=" + UPDATED_ELIGIBILITY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByEligibilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where eligibility is not null
        defaultApplicationServiceOverrideShouldBeFound("eligibility.specified=true");

        // Get all the applicationServiceOverrideList where eligibility is null
        defaultApplicationServiceOverrideShouldNotBeFound("eligibility.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByKeywordsIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where keywords equals to DEFAULT_KEYWORDS
        defaultApplicationServiceOverrideShouldBeFound("keywords.equals=" + DEFAULT_KEYWORDS);

        // Get all the applicationServiceOverrideList where keywords equals to UPDATED_KEYWORDS
        defaultApplicationServiceOverrideShouldNotBeFound("keywords.equals=" + UPDATED_KEYWORDS);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByKeywordsIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where keywords in DEFAULT_KEYWORDS or UPDATED_KEYWORDS
        defaultApplicationServiceOverrideShouldBeFound("keywords.in=" + DEFAULT_KEYWORDS + "," + UPDATED_KEYWORDS);

        // Get all the applicationServiceOverrideList where keywords equals to UPDATED_KEYWORDS
        defaultApplicationServiceOverrideShouldNotBeFound("keywords.in=" + UPDATED_KEYWORDS);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByKeywordsIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where keywords is not null
        defaultApplicationServiceOverrideShouldBeFound("keywords.specified=true");

        // Get all the applicationServiceOverrideList where keywords is null
        defaultApplicationServiceOverrideShouldNotBeFound("keywords.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByLongDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where longDescription equals to DEFAULT_LONG_DESCRIPTION
        defaultApplicationServiceOverrideShouldBeFound("longDescription.equals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the applicationServiceOverrideList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultApplicationServiceOverrideShouldNotBeFound("longDescription.equals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByLongDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where longDescription in DEFAULT_LONG_DESCRIPTION or UPDATED_LONG_DESCRIPTION
        defaultApplicationServiceOverrideShouldBeFound("longDescription.in=" + DEFAULT_LONG_DESCRIPTION + "," + UPDATED_LONG_DESCRIPTION);

        // Get all the applicationServiceOverrideList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultApplicationServiceOverrideShouldNotBeFound("longDescription.in=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByLongDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where longDescription is not null
        defaultApplicationServiceOverrideShouldBeFound("longDescription.specified=true");

        // Get all the applicationServiceOverrideList where longDescription is null
        defaultApplicationServiceOverrideShouldNotBeFound("longDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where name equals to DEFAULT_NAME
        defaultApplicationServiceOverrideShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the applicationServiceOverrideList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApplicationServiceOverrideShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the applicationServiceOverrideList where name equals to UPDATED_NAME
        defaultApplicationServiceOverrideShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where name is not null
        defaultApplicationServiceOverrideShouldBeFound("name.specified=true");

        // Get all the applicationServiceOverrideList where name is null
        defaultApplicationServiceOverrideShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByPreRequisitesIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where preRequisites equals to DEFAULT_PRE_REQUISITES
        defaultApplicationServiceOverrideShouldBeFound("preRequisites.equals=" + DEFAULT_PRE_REQUISITES);

        // Get all the applicationServiceOverrideList where preRequisites equals to UPDATED_PRE_REQUISITES
        defaultApplicationServiceOverrideShouldNotBeFound("preRequisites.equals=" + UPDATED_PRE_REQUISITES);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByPreRequisitesIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where preRequisites in DEFAULT_PRE_REQUISITES or UPDATED_PRE_REQUISITES
        defaultApplicationServiceOverrideShouldBeFound("preRequisites.in=" + DEFAULT_PRE_REQUISITES + "," + UPDATED_PRE_REQUISITES);

        // Get all the applicationServiceOverrideList where preRequisites equals to UPDATED_PRE_REQUISITES
        defaultApplicationServiceOverrideShouldNotBeFound("preRequisites.in=" + UPDATED_PRE_REQUISITES);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByPreRequisitesIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where preRequisites is not null
        defaultApplicationServiceOverrideShouldBeFound("preRequisites.specified=true");

        // Get all the applicationServiceOverrideList where preRequisites is null
        defaultApplicationServiceOverrideShouldNotBeFound("preRequisites.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where fees equals to DEFAULT_FEES
        defaultApplicationServiceOverrideShouldBeFound("fees.equals=" + DEFAULT_FEES);

        // Get all the applicationServiceOverrideList where fees equals to UPDATED_FEES
        defaultApplicationServiceOverrideShouldNotBeFound("fees.equals=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByFeesIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where fees in DEFAULT_FEES or UPDATED_FEES
        defaultApplicationServiceOverrideShouldBeFound("fees.in=" + DEFAULT_FEES + "," + UPDATED_FEES);

        // Get all the applicationServiceOverrideList where fees equals to UPDATED_FEES
        defaultApplicationServiceOverrideShouldNotBeFound("fees.in=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByFeesIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where fees is not null
        defaultApplicationServiceOverrideShouldBeFound("fees.specified=true");

        // Get all the applicationServiceOverrideList where fees is null
        defaultApplicationServiceOverrideShouldNotBeFound("fees.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where active equals to DEFAULT_ACTIVE
        defaultApplicationServiceOverrideShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the applicationServiceOverrideList where active equals to UPDATED_ACTIVE
        defaultApplicationServiceOverrideShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultApplicationServiceOverrideShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the applicationServiceOverrideList where active equals to UPDATED_ACTIVE
        defaultApplicationServiceOverrideShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where active is not null
        defaultApplicationServiceOverrideShouldBeFound("active.specified=true");

        // Get all the applicationServiceOverrideList where active is null
        defaultApplicationServiceOverrideShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByReferenceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where referenceUrl equals to DEFAULT_REFERENCE_URL
        defaultApplicationServiceOverrideShouldBeFound("referenceUrl.equals=" + DEFAULT_REFERENCE_URL);

        // Get all the applicationServiceOverrideList where referenceUrl equals to UPDATED_REFERENCE_URL
        defaultApplicationServiceOverrideShouldNotBeFound("referenceUrl.equals=" + UPDATED_REFERENCE_URL);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByReferenceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where referenceUrl in DEFAULT_REFERENCE_URL or UPDATED_REFERENCE_URL
        defaultApplicationServiceOverrideShouldBeFound("referenceUrl.in=" + DEFAULT_REFERENCE_URL + "," + UPDATED_REFERENCE_URL);

        // Get all the applicationServiceOverrideList where referenceUrl equals to UPDATED_REFERENCE_URL
        defaultApplicationServiceOverrideShouldNotBeFound("referenceUrl.in=" + UPDATED_REFERENCE_URL);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByReferenceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where referenceUrl is not null
        defaultApplicationServiceOverrideShouldBeFound("referenceUrl.specified=true");

        // Get all the applicationServiceOverrideList where referenceUrl is null
        defaultApplicationServiceOverrideShouldNotBeFound("referenceUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationServiceOverrideShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationServiceOverrideList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationServiceOverrideShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationServiceOverrideList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdBy is not null
        defaultApplicationServiceOverrideShouldBeFound("createdBy.specified=true");

        // Get all the applicationServiceOverrideList where createdBy is null
        defaultApplicationServiceOverrideShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultApplicationServiceOverrideShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where createdDateTime is not null
        defaultApplicationServiceOverrideShouldBeFound("createdDateTime.specified=true");

        // Get all the applicationServiceOverrideList where createdDateTime is null
        defaultApplicationServiceOverrideShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultApplicationServiceOverrideShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the applicationServiceOverrideList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the applicationServiceOverrideList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedBy is not null
        defaultApplicationServiceOverrideShouldBeFound("modifiedBy.specified=true");

        // Get all the applicationServiceOverrideList where modifiedBy is null
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where modifiedDateTime is not null
        defaultApplicationServiceOverrideShouldBeFound("modifiedDateTime.specified=true");

        // Get all the applicationServiceOverrideList where modifiedDateTime is null
        defaultApplicationServiceOverrideShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultApplicationServiceOverrideShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the applicationServiceOverrideList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the applicationServiceOverrideList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where migratedBy is not null
        defaultApplicationServiceOverrideShouldBeFound("migratedBy.specified=true");

        // Get all the applicationServiceOverrideList where migratedBy is null
        defaultApplicationServiceOverrideShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where version equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApplicationServiceOverrideShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the applicationServiceOverrideList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where version is not null
        defaultApplicationServiceOverrideShouldBeFound("version.specified=true");

        // Get all the applicationServiceOverrideList where version is null
        defaultApplicationServiceOverrideShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where version greater than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideList where version greater than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where version less than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideList where version less than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByHowToIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where howTo equals to DEFAULT_HOW_TO
        defaultApplicationServiceOverrideShouldBeFound("howTo.equals=" + DEFAULT_HOW_TO);

        // Get all the applicationServiceOverrideList where howTo equals to UPDATED_HOW_TO
        defaultApplicationServiceOverrideShouldNotBeFound("howTo.equals=" + UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByHowToIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where howTo in DEFAULT_HOW_TO or UPDATED_HOW_TO
        defaultApplicationServiceOverrideShouldBeFound("howTo.in=" + DEFAULT_HOW_TO + "," + UPDATED_HOW_TO);

        // Get all the applicationServiceOverrideList where howTo equals to UPDATED_HOW_TO
        defaultApplicationServiceOverrideShouldNotBeFound("howTo.in=" + UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByHowToIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        // Get all the applicationServiceOverrideList where howTo is not null
        defaultApplicationServiceOverrideShouldBeFound("howTo.specified=true");

        // Get all the applicationServiceOverrideList where howTo is null
        defaultApplicationServiceOverrideShouldNotBeFound("howTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByServiceRecordIsEqualToSomething() throws Exception {
        // Get already existing entity
        ServiceRecord serviceRecord = applicationServiceOverride.getServiceRecord();
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the applicationServiceOverrideList where serviceRecord equals to serviceRecordId
        defaultApplicationServiceOverrideShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the applicationServiceOverrideList where serviceRecord equals to serviceRecordId + 1
        defaultApplicationServiceOverrideShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByApplicationIsEqualToSomething() throws Exception {
        // Get already existing entity
        Application application = applicationServiceOverride.getApplication();
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);
        Long applicationId = application.getId();

        // Get all the applicationServiceOverrideList where application equals to applicationId
        defaultApplicationServiceOverrideShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the applicationServiceOverrideList where application equals to applicationId + 1
        defaultApplicationServiceOverrideShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByApplicationServiceOverrideAttributeIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverrideAttribute applicationServiceOverrideAttribute = ApplicationServiceOverrideAttributeResourceIT.createEntity(em);
        em.persist(applicationServiceOverrideAttribute);
        em.flush();
        applicationServiceOverride.addApplicationServiceOverrideAttribute(applicationServiceOverrideAttribute);
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);
        Long applicationServiceOverrideAttributeId = applicationServiceOverrideAttribute.getId();

        // Get all the applicationServiceOverrideList where applicationServiceOverrideAttribute equals to applicationServiceOverrideAttributeId
        defaultApplicationServiceOverrideShouldBeFound("applicationServiceOverrideAttributeId.equals=" + applicationServiceOverrideAttributeId);

        // Get all the applicationServiceOverrideList where applicationServiceOverrideAttribute equals to applicationServiceOverrideAttributeId + 1
        defaultApplicationServiceOverrideShouldNotBeFound("applicationServiceOverrideAttributeId.equals=" + (applicationServiceOverrideAttributeId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverridesByApplicationServiceOverrideTagItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = ApplicationServiceOverrideTagItemsResourceIT.createEntity(em);
        em.persist(applicationServiceOverrideTagItems);
        em.flush();
        applicationServiceOverride.addApplicationServiceOverrideTagItems(applicationServiceOverrideTagItems);
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);
        Long applicationServiceOverrideTagItemsId = applicationServiceOverrideTagItems.getId();

        // Get all the applicationServiceOverrideList where applicationServiceOverrideTagItems equals to applicationServiceOverrideTagItemsId
        defaultApplicationServiceOverrideShouldBeFound("applicationServiceOverrideTagItemsId.equals=" + applicationServiceOverrideTagItemsId);

        // Get all the applicationServiceOverrideList where applicationServiceOverrideTagItems equals to applicationServiceOverrideTagItemsId + 1
        defaultApplicationServiceOverrideShouldNotBeFound("applicationServiceOverrideTagItemsId.equals=" + (applicationServiceOverrideTagItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationServiceOverrideShouldBeFound(String filter) throws Exception {
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverride.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].eligibility").value(hasItem(DEFAULT_ELIGIBILITY)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].preRequisites").value(hasItem(DEFAULT_PRE_REQUISITES)))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].referenceUrl").value(hasItem(DEFAULT_REFERENCE_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].howTo").value(hasItem(DEFAULT_HOW_TO)));

        // Check, that the count call also returns 1
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationServiceOverrideShouldNotBeFound(String filter) throws Exception {
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationServiceOverride() throws Exception {
        // Get the applicationServiceOverride
        restApplicationServiceOverrideMockMvc.perform(get("/api/application-service-overrides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationServiceOverride() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        int databaseSizeBeforeUpdate = applicationServiceOverrideRepository.findAll().size();

        // Update the applicationServiceOverride
        ApplicationServiceOverride updatedApplicationServiceOverride = applicationServiceOverrideRepository.findById(applicationServiceOverride.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationServiceOverride are not directly saved in db
        em.detach(updatedApplicationServiceOverride);
        updatedApplicationServiceOverride
            .description(UPDATED_DESCRIPTION)
            .eligibility(UPDATED_ELIGIBILITY)
            .keywords(UPDATED_KEYWORDS)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .name(UPDATED_NAME)
            .preRequisites(UPDATED_PRE_REQUISITES)
            .fees(UPDATED_FEES)
            .active(UPDATED_ACTIVE)
            .referenceUrl(UPDATED_REFERENCE_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION)
            .howTo(UPDATED_HOW_TO);
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO = applicationServiceOverrideMapper.toDto(updatedApplicationServiceOverride);

        restApplicationServiceOverrideMockMvc.perform(put("/api/application-service-overrides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationServiceOverride in the database
        List<ApplicationServiceOverride> applicationServiceOverrideList = applicationServiceOverrideRepository.findAll();
        assertThat(applicationServiceOverrideList).hasSize(databaseSizeBeforeUpdate);
        ApplicationServiceOverride testApplicationServiceOverride = applicationServiceOverrideList.get(applicationServiceOverrideList.size() - 1);
        assertThat(testApplicationServiceOverride.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationServiceOverride.getEligibility()).isEqualTo(UPDATED_ELIGIBILITY);
        assertThat(testApplicationServiceOverride.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testApplicationServiceOverride.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testApplicationServiceOverride.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationServiceOverride.getPreRequisites()).isEqualTo(UPDATED_PRE_REQUISITES);
        assertThat(testApplicationServiceOverride.getFees()).isEqualTo(UPDATED_FEES);
        assertThat(testApplicationServiceOverride.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApplicationServiceOverride.getReferenceUrl()).isEqualTo(UPDATED_REFERENCE_URL);
        assertThat(testApplicationServiceOverride.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationServiceOverride.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverride.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplicationServiceOverride.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverride.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
        assertThat(testApplicationServiceOverride.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApplicationServiceOverride.getHowTo()).isEqualTo(UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationServiceOverride() throws Exception {
        int databaseSizeBeforeUpdate = applicationServiceOverrideRepository.findAll().size();

        // Create the ApplicationServiceOverride
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO = applicationServiceOverrideMapper.toDto(applicationServiceOverride);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServiceOverrideMockMvc.perform(put("/api/application-service-overrides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverride in the database
        List<ApplicationServiceOverride> applicationServiceOverrideList = applicationServiceOverrideRepository.findAll();
        assertThat(applicationServiceOverrideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationServiceOverride() throws Exception {
        // Initialize the database
        applicationServiceOverrideRepository.saveAndFlush(applicationServiceOverride);

        int databaseSizeBeforeDelete = applicationServiceOverrideRepository.findAll().size();

        // Delete the applicationServiceOverride
        restApplicationServiceOverrideMockMvc.perform(delete("/api/application-service-overrides/{id}", applicationServiceOverride.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationServiceOverride> applicationServiceOverrideList = applicationServiceOverrideRepository.findAll();
        assertThat(applicationServiceOverrideList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverride.class);
        ApplicationServiceOverride applicationServiceOverride1 = new ApplicationServiceOverride();
        applicationServiceOverride1.setId(1L);
        ApplicationServiceOverride applicationServiceOverride2 = new ApplicationServiceOverride();
        applicationServiceOverride2.setId(applicationServiceOverride1.getId());
        assertThat(applicationServiceOverride1).isEqualTo(applicationServiceOverride2);
        applicationServiceOverride2.setId(2L);
        assertThat(applicationServiceOverride1).isNotEqualTo(applicationServiceOverride2);
        applicationServiceOverride1.setId(null);
        assertThat(applicationServiceOverride1).isNotEqualTo(applicationServiceOverride2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideDTO.class);
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO1 = new ApplicationServiceOverrideDTO();
        applicationServiceOverrideDTO1.setId(1L);
        ApplicationServiceOverrideDTO applicationServiceOverrideDTO2 = new ApplicationServiceOverrideDTO();
        assertThat(applicationServiceOverrideDTO1).isNotEqualTo(applicationServiceOverrideDTO2);
        applicationServiceOverrideDTO2.setId(applicationServiceOverrideDTO1.getId());
        assertThat(applicationServiceOverrideDTO1).isEqualTo(applicationServiceOverrideDTO2);
        applicationServiceOverrideDTO2.setId(2L);
        assertThat(applicationServiceOverrideDTO1).isNotEqualTo(applicationServiceOverrideDTO2);
        applicationServiceOverrideDTO1.setId(null);
        assertThat(applicationServiceOverrideDTO1).isNotEqualTo(applicationServiceOverrideDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationServiceOverrideMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationServiceOverrideMapper.fromId(null)).isNull();
    }
}
