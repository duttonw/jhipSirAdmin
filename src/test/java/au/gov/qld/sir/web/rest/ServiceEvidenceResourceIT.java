package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceEvidence;
import au.gov.qld.sir.domain.Category;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceEvidenceRepository;
import au.gov.qld.sir.service.ServiceEvidenceService;
import au.gov.qld.sir.service.dto.ServiceEvidenceDTO;
import au.gov.qld.sir.service.mapper.ServiceEvidenceMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceEvidenceCriteria;
import au.gov.qld.sir.service.ServiceEvidenceQueryService;

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
 * Integration tests for the {@Link ServiceEvidenceResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceEvidenceResourceIT {

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

    private static final String DEFAULT_EVIDENCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVIDENCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private ServiceEvidenceRepository serviceEvidenceRepository;

    @Autowired
    private ServiceEvidenceMapper serviceEvidenceMapper;

    @Autowired
    private ServiceEvidenceService serviceEvidenceService;

    @Autowired
    private ServiceEvidenceQueryService serviceEvidenceQueryService;

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

    private MockMvc restServiceEvidenceMockMvc;

    private ServiceEvidence serviceEvidence;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceEvidenceResource serviceEvidenceResource = new ServiceEvidenceResource(serviceEvidenceService, serviceEvidenceQueryService);
        this.restServiceEvidenceMockMvc = MockMvcBuilders.standaloneSetup(serviceEvidenceResource)
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
    public static ServiceEvidence createEntity(EntityManager em) {
        ServiceEvidence serviceEvidence = new ServiceEvidence()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .evidenceName(DEFAULT_EVIDENCE_NAME)
            .metaData(DEFAULT_META_DATA)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        serviceEvidence.setDisplayedForCategory(category);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        serviceEvidence.setServiceRecord(serviceRecord);
        return serviceEvidence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceEvidence createUpdatedEntity(EntityManager em) {
        ServiceEvidence serviceEvidence = new ServiceEvidence()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .evidenceName(UPDATED_EVIDENCE_NAME)
            .metaData(UPDATED_META_DATA)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        serviceEvidence.setDisplayedForCategory(category);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createUpdatedEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        serviceEvidence.setServiceRecord(serviceRecord);
        return serviceEvidence;
    }

    @BeforeEach
    public void initTest() {
        serviceEvidence = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceEvidence() throws Exception {
        int databaseSizeBeforeCreate = serviceEvidenceRepository.findAll().size();

        // Create the ServiceEvidence
        ServiceEvidenceDTO serviceEvidenceDTO = serviceEvidenceMapper.toDto(serviceEvidence);
        restServiceEvidenceMockMvc.perform(post("/api/service-evidences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEvidenceDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceEvidence in the database
        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceEvidence testServiceEvidence = serviceEvidenceList.get(serviceEvidenceList.size() - 1);
        assertThat(testServiceEvidence.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceEvidence.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceEvidence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceEvidence.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceEvidence.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceEvidence.getEvidenceName()).isEqualTo(DEFAULT_EVIDENCE_NAME);
        assertThat(testServiceEvidence.getMetaData()).isEqualTo(DEFAULT_META_DATA);
        assertThat(testServiceEvidence.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testServiceEvidence.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createServiceEvidenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceEvidenceRepository.findAll().size();

        // Create the ServiceEvidence with an existing ID
        serviceEvidence.setId(1L);
        ServiceEvidenceDTO serviceEvidenceDTO = serviceEvidenceMapper.toDto(serviceEvidence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceEvidenceMockMvc.perform(post("/api/service-evidences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEvidenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEvidence in the database
        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEvidenceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceEvidenceRepository.findAll().size();
        // set the field null
        serviceEvidence.setEvidenceName(null);

        // Create the ServiceEvidence, which fails.
        ServiceEvidenceDTO serviceEvidenceDTO = serviceEvidenceMapper.toDto(serviceEvidence);

        restServiceEvidenceMockMvc.perform(post("/api/service-evidences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEvidenceDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceEvidences() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEvidence.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].evidenceName").value(hasItem(DEFAULT_EVIDENCE_NAME.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceEvidence() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get the serviceEvidence
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences/{id}", serviceEvidence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceEvidence.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.evidenceName").value(DEFAULT_EVIDENCE_NAME.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceEvidenceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceEvidenceList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEvidenceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceEvidenceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceEvidenceList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEvidenceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdBy is not null
        defaultServiceEvidenceShouldBeFound("createdBy.specified=true");

        // Get all the serviceEvidenceList where createdBy is null
        defaultServiceEvidenceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceEvidenceShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceEvidenceList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEvidenceShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceEvidenceShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceEvidenceList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEvidenceShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where createdDateTime is not null
        defaultServiceEvidenceShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceEvidenceList where createdDateTime is null
        defaultServiceEvidenceShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceEvidenceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceEvidenceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEvidenceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceEvidenceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceEvidenceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEvidenceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedBy is not null
        defaultServiceEvidenceShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceEvidenceList where modifiedBy is null
        defaultServiceEvidenceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceEvidenceShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceEvidenceList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEvidenceShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceEvidenceShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceEvidenceList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEvidenceShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where modifiedDateTime is not null
        defaultServiceEvidenceShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceEvidenceList where modifiedDateTime is null
        defaultServiceEvidenceShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where version equals to DEFAULT_VERSION
        defaultServiceEvidenceShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceEvidenceList where version equals to UPDATED_VERSION
        defaultServiceEvidenceShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceEvidenceShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceEvidenceList where version equals to UPDATED_VERSION
        defaultServiceEvidenceShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where version is not null
        defaultServiceEvidenceShouldBeFound("version.specified=true");

        // Get all the serviceEvidenceList where version is null
        defaultServiceEvidenceShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where version greater than or equals to DEFAULT_VERSION
        defaultServiceEvidenceShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceEvidenceList where version greater than or equals to UPDATED_VERSION
        defaultServiceEvidenceShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where version less than or equals to DEFAULT_VERSION
        defaultServiceEvidenceShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceEvidenceList where version less than or equals to UPDATED_VERSION
        defaultServiceEvidenceShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceEvidencesByEvidenceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where evidenceName equals to DEFAULT_EVIDENCE_NAME
        defaultServiceEvidenceShouldBeFound("evidenceName.equals=" + DEFAULT_EVIDENCE_NAME);

        // Get all the serviceEvidenceList where evidenceName equals to UPDATED_EVIDENCE_NAME
        defaultServiceEvidenceShouldNotBeFound("evidenceName.equals=" + UPDATED_EVIDENCE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByEvidenceNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where evidenceName in DEFAULT_EVIDENCE_NAME or UPDATED_EVIDENCE_NAME
        defaultServiceEvidenceShouldBeFound("evidenceName.in=" + DEFAULT_EVIDENCE_NAME + "," + UPDATED_EVIDENCE_NAME);

        // Get all the serviceEvidenceList where evidenceName equals to UPDATED_EVIDENCE_NAME
        defaultServiceEvidenceShouldNotBeFound("evidenceName.in=" + UPDATED_EVIDENCE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByEvidenceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where evidenceName is not null
        defaultServiceEvidenceShouldBeFound("evidenceName.specified=true");

        // Get all the serviceEvidenceList where evidenceName is null
        defaultServiceEvidenceShouldNotBeFound("evidenceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMetaDataIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where metaData equals to DEFAULT_META_DATA
        defaultServiceEvidenceShouldBeFound("metaData.equals=" + DEFAULT_META_DATA);

        // Get all the serviceEvidenceList where metaData equals to UPDATED_META_DATA
        defaultServiceEvidenceShouldNotBeFound("metaData.equals=" + UPDATED_META_DATA);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMetaDataIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where metaData in DEFAULT_META_DATA or UPDATED_META_DATA
        defaultServiceEvidenceShouldBeFound("metaData.in=" + DEFAULT_META_DATA + "," + UPDATED_META_DATA);

        // Get all the serviceEvidenceList where metaData equals to UPDATED_META_DATA
        defaultServiceEvidenceShouldNotBeFound("metaData.in=" + UPDATED_META_DATA);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMetaDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where metaData is not null
        defaultServiceEvidenceShouldBeFound("metaData.specified=true");

        // Get all the serviceEvidenceList where metaData is null
        defaultServiceEvidenceShouldNotBeFound("metaData.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migrated equals to DEFAULT_MIGRATED
        defaultServiceEvidenceShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the serviceEvidenceList where migrated equals to UPDATED_MIGRATED
        defaultServiceEvidenceShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultServiceEvidenceShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the serviceEvidenceList where migrated equals to UPDATED_MIGRATED
        defaultServiceEvidenceShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migrated is not null
        defaultServiceEvidenceShouldBeFound("migrated.specified=true");

        // Get all the serviceEvidenceList where migrated is null
        defaultServiceEvidenceShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultServiceEvidenceShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the serviceEvidenceList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceEvidenceShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultServiceEvidenceShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the serviceEvidenceList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceEvidenceShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        // Get all the serviceEvidenceList where migratedBy is not null
        defaultServiceEvidenceShouldBeFound("migratedBy.specified=true");

        // Get all the serviceEvidenceList where migratedBy is null
        defaultServiceEvidenceShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEvidencesByDisplayedForCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category displayedForCategory = serviceEvidence.getDisplayedForCategory();
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);
        Long displayedForCategoryId = displayedForCategory.getId();

        // Get all the serviceEvidenceList where displayedForCategory equals to displayedForCategoryId
        defaultServiceEvidenceShouldBeFound("displayedForCategoryId.equals=" + displayedForCategoryId);

        // Get all the serviceEvidenceList where displayedForCategory equals to displayedForCategoryId + 1
        defaultServiceEvidenceShouldNotBeFound("displayedForCategoryId.equals=" + (displayedForCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceEvidencesByServiceRecordIsEqualToSomething() throws Exception {
        // Get already existing entity
        ServiceRecord serviceRecord = serviceEvidence.getServiceRecord();
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceEvidenceList where serviceRecord equals to serviceRecordId
        defaultServiceEvidenceShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceEvidenceList where serviceRecord equals to serviceRecordId + 1
        defaultServiceEvidenceShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceEvidenceShouldBeFound(String filter) throws Exception {
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEvidence.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].evidenceName").value(hasItem(DEFAULT_EVIDENCE_NAME)))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA)))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceEvidenceShouldNotBeFound(String filter) throws Exception {
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceEvidence() throws Exception {
        // Get the serviceEvidence
        restServiceEvidenceMockMvc.perform(get("/api/service-evidences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceEvidence() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        int databaseSizeBeforeUpdate = serviceEvidenceRepository.findAll().size();

        // Update the serviceEvidence
        ServiceEvidence updatedServiceEvidence = serviceEvidenceRepository.findById(serviceEvidence.getId()).get();
        // Disconnect from session so that the updates on updatedServiceEvidence are not directly saved in db
        em.detach(updatedServiceEvidence);
        updatedServiceEvidence
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .evidenceName(UPDATED_EVIDENCE_NAME)
            .metaData(UPDATED_META_DATA)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        ServiceEvidenceDTO serviceEvidenceDTO = serviceEvidenceMapper.toDto(updatedServiceEvidence);

        restServiceEvidenceMockMvc.perform(put("/api/service-evidences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEvidenceDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceEvidence in the database
        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeUpdate);
        ServiceEvidence testServiceEvidence = serviceEvidenceList.get(serviceEvidenceList.size() - 1);
        assertThat(testServiceEvidence.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceEvidence.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceEvidence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceEvidence.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceEvidence.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceEvidence.getEvidenceName()).isEqualTo(UPDATED_EVIDENCE_NAME);
        assertThat(testServiceEvidence.getMetaData()).isEqualTo(UPDATED_META_DATA);
        assertThat(testServiceEvidence.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testServiceEvidence.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceEvidence() throws Exception {
        int databaseSizeBeforeUpdate = serviceEvidenceRepository.findAll().size();

        // Create the ServiceEvidence
        ServiceEvidenceDTO serviceEvidenceDTO = serviceEvidenceMapper.toDto(serviceEvidence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceEvidenceMockMvc.perform(put("/api/service-evidences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEvidenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEvidence in the database
        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceEvidence() throws Exception {
        // Initialize the database
        serviceEvidenceRepository.saveAndFlush(serviceEvidence);

        int databaseSizeBeforeDelete = serviceEvidenceRepository.findAll().size();

        // Delete the serviceEvidence
        restServiceEvidenceMockMvc.perform(delete("/api/service-evidences/{id}", serviceEvidence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceEvidence> serviceEvidenceList = serviceEvidenceRepository.findAll();
        assertThat(serviceEvidenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEvidence.class);
        ServiceEvidence serviceEvidence1 = new ServiceEvidence();
        serviceEvidence1.setId(1L);
        ServiceEvidence serviceEvidence2 = new ServiceEvidence();
        serviceEvidence2.setId(serviceEvidence1.getId());
        assertThat(serviceEvidence1).isEqualTo(serviceEvidence2);
        serviceEvidence2.setId(2L);
        assertThat(serviceEvidence1).isNotEqualTo(serviceEvidence2);
        serviceEvidence1.setId(null);
        assertThat(serviceEvidence1).isNotEqualTo(serviceEvidence2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEvidenceDTO.class);
        ServiceEvidenceDTO serviceEvidenceDTO1 = new ServiceEvidenceDTO();
        serviceEvidenceDTO1.setId(1L);
        ServiceEvidenceDTO serviceEvidenceDTO2 = new ServiceEvidenceDTO();
        assertThat(serviceEvidenceDTO1).isNotEqualTo(serviceEvidenceDTO2);
        serviceEvidenceDTO2.setId(serviceEvidenceDTO1.getId());
        assertThat(serviceEvidenceDTO1).isEqualTo(serviceEvidenceDTO2);
        serviceEvidenceDTO2.setId(2L);
        assertThat(serviceEvidenceDTO1).isNotEqualTo(serviceEvidenceDTO2);
        serviceEvidenceDTO1.setId(null);
        assertThat(serviceEvidenceDTO1).isNotEqualTo(serviceEvidenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceEvidenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceEvidenceMapper.fromId(null)).isNull();
    }
}
