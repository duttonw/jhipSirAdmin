package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTagItems;
import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.domain.ApplicationServiceOverrideTag;
import au.gov.qld.sir.repository.ApplicationServiceOverrideTagItemsRepository;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagItemsService;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsDTO;
import au.gov.qld.sir.service.mapper.ApplicationServiceOverrideTagItemsMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ApplicationServiceOverrideTagItemsCriteria;
import au.gov.qld.sir.service.ApplicationServiceOverrideTagItemsQueryService;

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
 * Integration tests for the {@Link ApplicationServiceOverrideTagItemsResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ApplicationServiceOverrideTagItemsResourceIT {

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
    private ApplicationServiceOverrideTagItemsRepository applicationServiceOverrideTagItemsRepository;

    @Autowired
    private ApplicationServiceOverrideTagItemsMapper applicationServiceOverrideTagItemsMapper;

    @Autowired
    private ApplicationServiceOverrideTagItemsService applicationServiceOverrideTagItemsService;

    @Autowired
    private ApplicationServiceOverrideTagItemsQueryService applicationServiceOverrideTagItemsQueryService;

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

    private MockMvc restApplicationServiceOverrideTagItemsMockMvc;

    private ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationServiceOverrideTagItemsResource applicationServiceOverrideTagItemsResource = new ApplicationServiceOverrideTagItemsResource(applicationServiceOverrideTagItemsService, applicationServiceOverrideTagItemsQueryService);
        this.restApplicationServiceOverrideTagItemsMockMvc = MockMvcBuilders.standaloneSetup(applicationServiceOverrideTagItemsResource)
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
    public static ApplicationServiceOverrideTagItems createEntity(EntityManager em) {
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = new ApplicationServiceOverrideTagItems()
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
        applicationServiceOverrideTagItems.setApplicationServiceOverride(applicationServiceOverride);
        // Add required entity
        ApplicationServiceOverrideTag applicationServiceOverrideTag;
        if (TestUtil.findAll(em, ApplicationServiceOverrideTag.class).isEmpty()) {
            applicationServiceOverrideTag = ApplicationServiceOverrideTagResourceIT.createEntity(em);
            em.persist(applicationServiceOverrideTag);
            em.flush();
        } else {
            applicationServiceOverrideTag = TestUtil.findAll(em, ApplicationServiceOverrideTag.class).get(0);
        }
        applicationServiceOverrideTagItems.setApplicationServiceOverrideTag(applicationServiceOverrideTag);
        return applicationServiceOverrideTagItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServiceOverrideTagItems createUpdatedEntity(EntityManager em) {
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems = new ApplicationServiceOverrideTagItems()
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
        applicationServiceOverrideTagItems.setApplicationServiceOverride(applicationServiceOverride);
        // Add required entity
        ApplicationServiceOverrideTag applicationServiceOverrideTag;
        if (TestUtil.findAll(em, ApplicationServiceOverrideTag.class).isEmpty()) {
            applicationServiceOverrideTag = ApplicationServiceOverrideTagResourceIT.createUpdatedEntity(em);
            em.persist(applicationServiceOverrideTag);
            em.flush();
        } else {
            applicationServiceOverrideTag = TestUtil.findAll(em, ApplicationServiceOverrideTag.class).get(0);
        }
        applicationServiceOverrideTagItems.setApplicationServiceOverrideTag(applicationServiceOverrideTag);
        return applicationServiceOverrideTagItems;
    }

    @BeforeEach
    public void initTest() {
        applicationServiceOverrideTagItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideTagItems() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideTagItemsRepository.findAll().size();

        // Create the ApplicationServiceOverrideTagItems
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO = applicationServiceOverrideTagItemsMapper.toDto(applicationServiceOverrideTagItems);
        restApplicationServiceOverrideTagItemsMockMvc.perform(post("/api/application-service-override-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationServiceOverrideTagItems in the database
        List<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItemsList = applicationServiceOverrideTagItemsRepository.findAll();
        assertThat(applicationServiceOverrideTagItemsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationServiceOverrideTagItems testApplicationServiceOverrideTagItems = applicationServiceOverrideTagItemsList.get(applicationServiceOverrideTagItemsList.size() - 1);
        assertThat(testApplicationServiceOverrideTagItems.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTagItems.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTagItems.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createApplicationServiceOverrideTagItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationServiceOverrideTagItemsRepository.findAll().size();

        // Create the ApplicationServiceOverrideTagItems with an existing ID
        applicationServiceOverrideTagItems.setId(1L);
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO = applicationServiceOverrideTagItemsMapper.toDto(applicationServiceOverrideTagItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationServiceOverrideTagItemsMockMvc.perform(post("/api/application-service-override-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideTagItems in the database
        List<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItemsList = applicationServiceOverrideTagItemsRepository.findAll();
        assertThat(applicationServiceOverrideTagItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItems() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideTagItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @Test
    @Transactional
    public void getApplicationServiceOverrideTagItems() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get the applicationServiceOverrideTagItems
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items/{id}", applicationServiceOverrideTagItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationServiceOverrideTagItems.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationServiceOverrideTagItemsList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationServiceOverrideTagItemsList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdBy is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdBy.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where createdBy is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("createdDateTime.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where createdDateTime is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedBy.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where modifiedBy is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("modifiedDateTime.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where modifiedDateTime is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the applicationServiceOverrideTagItemsList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagItemsShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the applicationServiceOverrideTagItemsList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where migratedBy is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("migratedBy.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where migratedBy is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where version equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagItemsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagItemsList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApplicationServiceOverrideTagItemsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the applicationServiceOverrideTagItemsList where version equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where version is not null
        defaultApplicationServiceOverrideTagItemsShouldBeFound("version.specified=true");

        // Get all the applicationServiceOverrideTagItemsList where version is null
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where version greater than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagItemsShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagItemsList where version greater than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        // Get all the applicationServiceOverrideTagItemsList where version less than or equals to DEFAULT_VERSION
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the applicationServiceOverrideTagItemsList where version less than or equals to UPDATED_VERSION
        defaultApplicationServiceOverrideTagItemsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByApplicationServiceOverrideIsEqualToSomething() throws Exception {
        // Get already existing entity
        ApplicationServiceOverride applicationServiceOverride = applicationServiceOverrideTagItems.getApplicationServiceOverride();
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);
        Long applicationServiceOverrideId = applicationServiceOverride.getId();

        // Get all the applicationServiceOverrideTagItemsList where applicationServiceOverride equals to applicationServiceOverrideId
        defaultApplicationServiceOverrideTagItemsShouldBeFound("applicationServiceOverrideId.equals=" + applicationServiceOverrideId);

        // Get all the applicationServiceOverrideTagItemsList where applicationServiceOverride equals to applicationServiceOverrideId + 1
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("applicationServiceOverrideId.equals=" + (applicationServiceOverrideId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationServiceOverrideTagItemsByApplicationServiceOverrideTagIsEqualToSomething() throws Exception {
        // Get already existing entity
        ApplicationServiceOverrideTag applicationServiceOverrideTag = applicationServiceOverrideTagItems.getApplicationServiceOverrideTag();
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);
        Long applicationServiceOverrideTagId = applicationServiceOverrideTag.getId();

        // Get all the applicationServiceOverrideTagItemsList where applicationServiceOverrideTag equals to applicationServiceOverrideTagId
        defaultApplicationServiceOverrideTagItemsShouldBeFound("applicationServiceOverrideTagId.equals=" + applicationServiceOverrideTagId);

        // Get all the applicationServiceOverrideTagItemsList where applicationServiceOverrideTag equals to applicationServiceOverrideTagId + 1
        defaultApplicationServiceOverrideTagItemsShouldNotBeFound("applicationServiceOverrideTagId.equals=" + (applicationServiceOverrideTagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationServiceOverrideTagItemsShouldBeFound(String filter) throws Exception {
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServiceOverrideTagItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));

        // Check, that the count call also returns 1
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationServiceOverrideTagItemsShouldNotBeFound(String filter) throws Exception {
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationServiceOverrideTagItems() throws Exception {
        // Get the applicationServiceOverrideTagItems
        restApplicationServiceOverrideTagItemsMockMvc.perform(get("/api/application-service-override-tag-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationServiceOverrideTagItems() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        int databaseSizeBeforeUpdate = applicationServiceOverrideTagItemsRepository.findAll().size();

        // Update the applicationServiceOverrideTagItems
        ApplicationServiceOverrideTagItems updatedApplicationServiceOverrideTagItems = applicationServiceOverrideTagItemsRepository.findById(applicationServiceOverrideTagItems.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationServiceOverrideTagItems are not directly saved in db
        em.detach(updatedApplicationServiceOverrideTagItems);
        updatedApplicationServiceOverrideTagItems
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .migratedBy(UPDATED_MIGRATED_BY)
            .version(UPDATED_VERSION);
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO = applicationServiceOverrideTagItemsMapper.toDto(updatedApplicationServiceOverrideTagItems);

        restApplicationServiceOverrideTagItemsMockMvc.perform(put("/api/application-service-override-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagItemsDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationServiceOverrideTagItems in the database
        List<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItemsList = applicationServiceOverrideTagItemsRepository.findAll();
        assertThat(applicationServiceOverrideTagItemsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationServiceOverrideTagItems testApplicationServiceOverrideTagItems = applicationServiceOverrideTagItemsList.get(applicationServiceOverrideTagItemsList.size() - 1);
        assertThat(testApplicationServiceOverrideTagItems.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTagItems.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testApplicationServiceOverrideTagItems.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
        assertThat(testApplicationServiceOverrideTagItems.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationServiceOverrideTagItems() throws Exception {
        int databaseSizeBeforeUpdate = applicationServiceOverrideTagItemsRepository.findAll().size();

        // Create the ApplicationServiceOverrideTagItems
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO = applicationServiceOverrideTagItemsMapper.toDto(applicationServiceOverrideTagItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServiceOverrideTagItemsMockMvc.perform(put("/api/application-service-override-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationServiceOverrideTagItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServiceOverrideTagItems in the database
        List<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItemsList = applicationServiceOverrideTagItemsRepository.findAll();
        assertThat(applicationServiceOverrideTagItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationServiceOverrideTagItems() throws Exception {
        // Initialize the database
        applicationServiceOverrideTagItemsRepository.saveAndFlush(applicationServiceOverrideTagItems);

        int databaseSizeBeforeDelete = applicationServiceOverrideTagItemsRepository.findAll().size();

        // Delete the applicationServiceOverrideTagItems
        restApplicationServiceOverrideTagItemsMockMvc.perform(delete("/api/application-service-override-tag-items/{id}", applicationServiceOverrideTagItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationServiceOverrideTagItems> applicationServiceOverrideTagItemsList = applicationServiceOverrideTagItemsRepository.findAll();
        assertThat(applicationServiceOverrideTagItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideTagItems.class);
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems1 = new ApplicationServiceOverrideTagItems();
        applicationServiceOverrideTagItems1.setId(1L);
        ApplicationServiceOverrideTagItems applicationServiceOverrideTagItems2 = new ApplicationServiceOverrideTagItems();
        applicationServiceOverrideTagItems2.setId(applicationServiceOverrideTagItems1.getId());
        assertThat(applicationServiceOverrideTagItems1).isEqualTo(applicationServiceOverrideTagItems2);
        applicationServiceOverrideTagItems2.setId(2L);
        assertThat(applicationServiceOverrideTagItems1).isNotEqualTo(applicationServiceOverrideTagItems2);
        applicationServiceOverrideTagItems1.setId(null);
        assertThat(applicationServiceOverrideTagItems1).isNotEqualTo(applicationServiceOverrideTagItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServiceOverrideTagItemsDTO.class);
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO1 = new ApplicationServiceOverrideTagItemsDTO();
        applicationServiceOverrideTagItemsDTO1.setId(1L);
        ApplicationServiceOverrideTagItemsDTO applicationServiceOverrideTagItemsDTO2 = new ApplicationServiceOverrideTagItemsDTO();
        assertThat(applicationServiceOverrideTagItemsDTO1).isNotEqualTo(applicationServiceOverrideTagItemsDTO2);
        applicationServiceOverrideTagItemsDTO2.setId(applicationServiceOverrideTagItemsDTO1.getId());
        assertThat(applicationServiceOverrideTagItemsDTO1).isEqualTo(applicationServiceOverrideTagItemsDTO2);
        applicationServiceOverrideTagItemsDTO2.setId(2L);
        assertThat(applicationServiceOverrideTagItemsDTO1).isNotEqualTo(applicationServiceOverrideTagItemsDTO2);
        applicationServiceOverrideTagItemsDTO1.setId(null);
        assertThat(applicationServiceOverrideTagItemsDTO1).isNotEqualTo(applicationServiceOverrideTagItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationServiceOverrideTagItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationServiceOverrideTagItemsMapper.fromId(null)).isNull();
    }
}
