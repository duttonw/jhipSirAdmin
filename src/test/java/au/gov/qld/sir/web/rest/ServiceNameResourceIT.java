package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceName;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceNameRepository;
import au.gov.qld.sir.service.ServiceNameService;
import au.gov.qld.sir.service.dto.ServiceNameDTO;
import au.gov.qld.sir.service.mapper.ServiceNameMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceNameCriteria;
import au.gov.qld.sir.service.ServiceNameQueryService;

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
 * Integration tests for the {@Link ServiceNameResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceNameResourceIT {

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

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private ServiceNameRepository serviceNameRepository;

    @Autowired
    private ServiceNameMapper serviceNameMapper;

    @Autowired
    private ServiceNameService serviceNameService;

    @Autowired
    private ServiceNameQueryService serviceNameQueryService;

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

    private MockMvc restServiceNameMockMvc;

    private ServiceName serviceName;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceNameResource serviceNameResource = new ServiceNameResource(serviceNameService, serviceNameQueryService);
        this.restServiceNameMockMvc = MockMvcBuilders.standaloneSetup(serviceNameResource)
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
    public static ServiceName createEntity(EntityManager em) {
        ServiceName serviceName = new ServiceName()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .context(DEFAULT_CONTEXT)
            .serviceName(DEFAULT_SERVICE_NAME)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        return serviceName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceName createUpdatedEntity(EntityManager em) {
        ServiceName serviceName = new ServiceName()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT)
            .serviceName(UPDATED_SERVICE_NAME)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        return serviceName;
    }

    @BeforeEach
    public void initTest() {
        serviceName = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceName() throws Exception {
        int databaseSizeBeforeCreate = serviceNameRepository.findAll().size();

        // Create the ServiceName
        ServiceNameDTO serviceNameDTO = serviceNameMapper.toDto(serviceName);
        restServiceNameMockMvc.perform(post("/api/service-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceNameDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceName in the database
        List<ServiceName> serviceNameList = serviceNameRepository.findAll();
        assertThat(serviceNameList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceName testServiceName = serviceNameList.get(serviceNameList.size() - 1);
        assertThat(testServiceName.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceName.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceName.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceName.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceName.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceName.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testServiceName.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testServiceName.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testServiceName.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createServiceNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceNameRepository.findAll().size();

        // Create the ServiceName with an existing ID
        serviceName.setId(1L);
        ServiceNameDTO serviceNameDTO = serviceNameMapper.toDto(serviceName);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceNameMockMvc.perform(post("/api/service-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceNameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceName in the database
        List<ServiceName> serviceNameList = serviceNameRepository.findAll();
        assertThat(serviceNameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceNames() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList
        restServiceNameMockMvc.perform(get("/api/service-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceName.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceName() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get the serviceName
        restServiceNameMockMvc.perform(get("/api/service-names/{id}", serviceName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceName.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceNameShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceNameList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceNameShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceNameShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceNameList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceNameShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdBy is not null
        defaultServiceNameShouldBeFound("createdBy.specified=true");

        // Get all the serviceNameList where createdBy is null
        defaultServiceNameShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceNameShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceNameList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceNameShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceNameShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceNameList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceNameShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where createdDateTime is not null
        defaultServiceNameShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceNameList where createdDateTime is null
        defaultServiceNameShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceNameShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceNameList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceNameShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceNameShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceNameList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceNameShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedBy is not null
        defaultServiceNameShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceNameList where modifiedBy is null
        defaultServiceNameShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceNameShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceNameList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceNameShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceNameShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceNameList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceNameShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where modifiedDateTime is not null
        defaultServiceNameShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceNameList where modifiedDateTime is null
        defaultServiceNameShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where version equals to DEFAULT_VERSION
        defaultServiceNameShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceNameList where version equals to UPDATED_VERSION
        defaultServiceNameShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceNameShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceNameList where version equals to UPDATED_VERSION
        defaultServiceNameShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where version is not null
        defaultServiceNameShouldBeFound("version.specified=true");

        // Get all the serviceNameList where version is null
        defaultServiceNameShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where version greater than or equals to DEFAULT_VERSION
        defaultServiceNameShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceNameList where version greater than or equals to UPDATED_VERSION
        defaultServiceNameShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where version less than or equals to DEFAULT_VERSION
        defaultServiceNameShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceNameList where version less than or equals to UPDATED_VERSION
        defaultServiceNameShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceNamesByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where context equals to DEFAULT_CONTEXT
        defaultServiceNameShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the serviceNameList where context equals to UPDATED_CONTEXT
        defaultServiceNameShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByContextIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultServiceNameShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the serviceNameList where context equals to UPDATED_CONTEXT
        defaultServiceNameShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where context is not null
        defaultServiceNameShouldBeFound("context.specified=true");

        // Get all the serviceNameList where context is null
        defaultServiceNameShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultServiceNameShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceNameList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceNameShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultServiceNameShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the serviceNameList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceNameShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where serviceName is not null
        defaultServiceNameShouldBeFound("serviceName.specified=true");

        // Get all the serviceNameList where serviceName is null
        defaultServiceNameShouldNotBeFound("serviceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migrated equals to DEFAULT_MIGRATED
        defaultServiceNameShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the serviceNameList where migrated equals to UPDATED_MIGRATED
        defaultServiceNameShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultServiceNameShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the serviceNameList where migrated equals to UPDATED_MIGRATED
        defaultServiceNameShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migrated is not null
        defaultServiceNameShouldBeFound("migrated.specified=true");

        // Get all the serviceNameList where migrated is null
        defaultServiceNameShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultServiceNameShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the serviceNameList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceNameShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultServiceNameShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the serviceNameList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceNameShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceNamesByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        // Get all the serviceNameList where migratedBy is not null
        defaultServiceNameShouldBeFound("migratedBy.specified=true");

        // Get all the serviceNameList where migratedBy is null
        defaultServiceNameShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceNamesByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceName.setServiceRecord(serviceRecord);
        serviceNameRepository.saveAndFlush(serviceName);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceNameList where serviceRecord equals to serviceRecordId
        defaultServiceNameShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceNameList where serviceRecord equals to serviceRecordId + 1
        defaultServiceNameShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceNameShouldBeFound(String filter) throws Exception {
        restServiceNameMockMvc.perform(get("/api/service-names?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceName.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restServiceNameMockMvc.perform(get("/api/service-names/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceNameShouldNotBeFound(String filter) throws Exception {
        restServiceNameMockMvc.perform(get("/api/service-names?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceNameMockMvc.perform(get("/api/service-names/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceName() throws Exception {
        // Get the serviceName
        restServiceNameMockMvc.perform(get("/api/service-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceName() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        int databaseSizeBeforeUpdate = serviceNameRepository.findAll().size();

        // Update the serviceName
        ServiceName updatedServiceName = serviceNameRepository.findById(serviceName.getId()).get();
        // Disconnect from session so that the updates on updatedServiceName are not directly saved in db
        em.detach(updatedServiceName);
        updatedServiceName
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT)
            .serviceName(UPDATED_SERVICE_NAME)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        ServiceNameDTO serviceNameDTO = serviceNameMapper.toDto(updatedServiceName);

        restServiceNameMockMvc.perform(put("/api/service-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceNameDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceName in the database
        List<ServiceName> serviceNameList = serviceNameRepository.findAll();
        assertThat(serviceNameList).hasSize(databaseSizeBeforeUpdate);
        ServiceName testServiceName = serviceNameList.get(serviceNameList.size() - 1);
        assertThat(testServiceName.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceName.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceName.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceName.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceName.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceName.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testServiceName.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testServiceName.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testServiceName.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceName() throws Exception {
        int databaseSizeBeforeUpdate = serviceNameRepository.findAll().size();

        // Create the ServiceName
        ServiceNameDTO serviceNameDTO = serviceNameMapper.toDto(serviceName);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceNameMockMvc.perform(put("/api/service-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceNameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceName in the database
        List<ServiceName> serviceNameList = serviceNameRepository.findAll();
        assertThat(serviceNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceName() throws Exception {
        // Initialize the database
        serviceNameRepository.saveAndFlush(serviceName);

        int databaseSizeBeforeDelete = serviceNameRepository.findAll().size();

        // Delete the serviceName
        restServiceNameMockMvc.perform(delete("/api/service-names/{id}", serviceName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceName> serviceNameList = serviceNameRepository.findAll();
        assertThat(serviceNameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceName.class);
        ServiceName serviceName1 = new ServiceName();
        serviceName1.setId(1L);
        ServiceName serviceName2 = new ServiceName();
        serviceName2.setId(serviceName1.getId());
        assertThat(serviceName1).isEqualTo(serviceName2);
        serviceName2.setId(2L);
        assertThat(serviceName1).isNotEqualTo(serviceName2);
        serviceName1.setId(null);
        assertThat(serviceName1).isNotEqualTo(serviceName2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceNameDTO.class);
        ServiceNameDTO serviceNameDTO1 = new ServiceNameDTO();
        serviceNameDTO1.setId(1L);
        ServiceNameDTO serviceNameDTO2 = new ServiceNameDTO();
        assertThat(serviceNameDTO1).isNotEqualTo(serviceNameDTO2);
        serviceNameDTO2.setId(serviceNameDTO1.getId());
        assertThat(serviceNameDTO1).isEqualTo(serviceNameDTO2);
        serviceNameDTO2.setId(2L);
        assertThat(serviceNameDTO1).isNotEqualTo(serviceNameDTO2);
        serviceNameDTO1.setId(null);
        assertThat(serviceNameDTO1).isNotEqualTo(serviceNameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceNameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceNameMapper.fromId(null)).isNull();
    }
}
