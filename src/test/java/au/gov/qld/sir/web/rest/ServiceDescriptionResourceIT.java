package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceDescription;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceDescriptionRepository;
import au.gov.qld.sir.service.ServiceDescriptionService;
import au.gov.qld.sir.service.dto.ServiceDescriptionDTO;
import au.gov.qld.sir.service.mapper.ServiceDescriptionMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceDescriptionCriteria;
import au.gov.qld.sir.service.ServiceDescriptionQueryService;

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
 * Integration tests for the {@Link ServiceDescriptionResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceDescriptionResourceIT {

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

    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private ServiceDescriptionRepository serviceDescriptionRepository;

    @Autowired
    private ServiceDescriptionMapper serviceDescriptionMapper;

    @Autowired
    private ServiceDescriptionService serviceDescriptionService;

    @Autowired
    private ServiceDescriptionQueryService serviceDescriptionQueryService;

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

    private MockMvc restServiceDescriptionMockMvc;

    private ServiceDescription serviceDescription;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceDescriptionResource serviceDescriptionResource = new ServiceDescriptionResource(serviceDescriptionService, serviceDescriptionQueryService);
        this.restServiceDescriptionMockMvc = MockMvcBuilders.standaloneSetup(serviceDescriptionResource)
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
    public static ServiceDescription createEntity(EntityManager em) {
        ServiceDescription serviceDescription = new ServiceDescription()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .context(DEFAULT_CONTEXT)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        return serviceDescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceDescription createUpdatedEntity(EntityManager em) {
        ServiceDescription serviceDescription = new ServiceDescription()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        return serviceDescription;
    }

    @BeforeEach
    public void initTest() {
        serviceDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceDescription() throws Exception {
        int databaseSizeBeforeCreate = serviceDescriptionRepository.findAll().size();

        // Create the ServiceDescription
        ServiceDescriptionDTO serviceDescriptionDTO = serviceDescriptionMapper.toDto(serviceDescription);
        restServiceDescriptionMockMvc.perform(post("/api/service-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDescriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceDescription in the database
        List<ServiceDescription> serviceDescriptionList = serviceDescriptionRepository.findAll();
        assertThat(serviceDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceDescription testServiceDescription = serviceDescriptionList.get(serviceDescriptionList.size() - 1);
        assertThat(testServiceDescription.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceDescription.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceDescription.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceDescription.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceDescription.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceDescription.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testServiceDescription.getServiceDescription()).isEqualTo(DEFAULT_SERVICE_DESCRIPTION);
        assertThat(testServiceDescription.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testServiceDescription.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createServiceDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceDescriptionRepository.findAll().size();

        // Create the ServiceDescription with an existing ID
        serviceDescription.setId(1L);
        ServiceDescriptionDTO serviceDescriptionDTO = serviceDescriptionMapper.toDto(serviceDescription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceDescriptionMockMvc.perform(post("/api/service-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDescription in the database
        List<ServiceDescription> serviceDescriptionList = serviceDescriptionRepository.findAll();
        assertThat(serviceDescriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceDescriptions() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceDescription() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get the serviceDescription
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions/{id}", serviceDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceDescription.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceDescriptionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceDescriptionList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDescriptionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceDescriptionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceDescriptionList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDescriptionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdBy is not null
        defaultServiceDescriptionShouldBeFound("createdBy.specified=true");

        // Get all the serviceDescriptionList where createdBy is null
        defaultServiceDescriptionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceDescriptionShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceDescriptionList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDescriptionShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceDescriptionShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceDescriptionList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDescriptionShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where createdDateTime is not null
        defaultServiceDescriptionShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceDescriptionList where createdDateTime is null
        defaultServiceDescriptionShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceDescriptionShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceDescriptionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDescriptionShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceDescriptionShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceDescriptionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDescriptionShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedBy is not null
        defaultServiceDescriptionShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceDescriptionList where modifiedBy is null
        defaultServiceDescriptionShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceDescriptionShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceDescriptionList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDescriptionShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceDescriptionShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceDescriptionList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDescriptionShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where modifiedDateTime is not null
        defaultServiceDescriptionShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceDescriptionList where modifiedDateTime is null
        defaultServiceDescriptionShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where version equals to DEFAULT_VERSION
        defaultServiceDescriptionShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceDescriptionList where version equals to UPDATED_VERSION
        defaultServiceDescriptionShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceDescriptionShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceDescriptionList where version equals to UPDATED_VERSION
        defaultServiceDescriptionShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where version is not null
        defaultServiceDescriptionShouldBeFound("version.specified=true");

        // Get all the serviceDescriptionList where version is null
        defaultServiceDescriptionShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where version greater than or equals to DEFAULT_VERSION
        defaultServiceDescriptionShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceDescriptionList where version greater than or equals to UPDATED_VERSION
        defaultServiceDescriptionShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where version less than or equals to DEFAULT_VERSION
        defaultServiceDescriptionShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceDescriptionList where version less than or equals to UPDATED_VERSION
        defaultServiceDescriptionShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceDescriptionsByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where context equals to DEFAULT_CONTEXT
        defaultServiceDescriptionShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the serviceDescriptionList where context equals to UPDATED_CONTEXT
        defaultServiceDescriptionShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByContextIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultServiceDescriptionShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the serviceDescriptionList where context equals to UPDATED_CONTEXT
        defaultServiceDescriptionShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where context is not null
        defaultServiceDescriptionShouldBeFound("context.specified=true");

        // Get all the serviceDescriptionList where context is null
        defaultServiceDescriptionShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByServiceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where serviceDescription equals to DEFAULT_SERVICE_DESCRIPTION
        defaultServiceDescriptionShouldBeFound("serviceDescription.equals=" + DEFAULT_SERVICE_DESCRIPTION);

        // Get all the serviceDescriptionList where serviceDescription equals to UPDATED_SERVICE_DESCRIPTION
        defaultServiceDescriptionShouldNotBeFound("serviceDescription.equals=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByServiceDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where serviceDescription in DEFAULT_SERVICE_DESCRIPTION or UPDATED_SERVICE_DESCRIPTION
        defaultServiceDescriptionShouldBeFound("serviceDescription.in=" + DEFAULT_SERVICE_DESCRIPTION + "," + UPDATED_SERVICE_DESCRIPTION);

        // Get all the serviceDescriptionList where serviceDescription equals to UPDATED_SERVICE_DESCRIPTION
        defaultServiceDescriptionShouldNotBeFound("serviceDescription.in=" + UPDATED_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByServiceDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where serviceDescription is not null
        defaultServiceDescriptionShouldBeFound("serviceDescription.specified=true");

        // Get all the serviceDescriptionList where serviceDescription is null
        defaultServiceDescriptionShouldNotBeFound("serviceDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migrated equals to DEFAULT_MIGRATED
        defaultServiceDescriptionShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the serviceDescriptionList where migrated equals to UPDATED_MIGRATED
        defaultServiceDescriptionShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultServiceDescriptionShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the serviceDescriptionList where migrated equals to UPDATED_MIGRATED
        defaultServiceDescriptionShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migrated is not null
        defaultServiceDescriptionShouldBeFound("migrated.specified=true");

        // Get all the serviceDescriptionList where migrated is null
        defaultServiceDescriptionShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultServiceDescriptionShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the serviceDescriptionList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceDescriptionShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultServiceDescriptionShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the serviceDescriptionList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceDescriptionShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        // Get all the serviceDescriptionList where migratedBy is not null
        defaultServiceDescriptionShouldBeFound("migratedBy.specified=true");

        // Get all the serviceDescriptionList where migratedBy is null
        defaultServiceDescriptionShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDescriptionsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceDescription.setServiceRecord(serviceRecord);
        serviceDescriptionRepository.saveAndFlush(serviceDescription);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceDescriptionList where serviceRecord equals to serviceRecordId
        defaultServiceDescriptionShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceDescriptionList where serviceRecord equals to serviceRecordId + 1
        defaultServiceDescriptionShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceDescriptionShouldBeFound(String filter) throws Exception {
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceDescriptionShouldNotBeFound(String filter) throws Exception {
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceDescription() throws Exception {
        // Get the serviceDescription
        restServiceDescriptionMockMvc.perform(get("/api/service-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceDescription() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        int databaseSizeBeforeUpdate = serviceDescriptionRepository.findAll().size();

        // Update the serviceDescription
        ServiceDescription updatedServiceDescription = serviceDescriptionRepository.findById(serviceDescription.getId()).get();
        // Disconnect from session so that the updates on updatedServiceDescription are not directly saved in db
        em.detach(updatedServiceDescription);
        updatedServiceDescription
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        ServiceDescriptionDTO serviceDescriptionDTO = serviceDescriptionMapper.toDto(updatedServiceDescription);

        restServiceDescriptionMockMvc.perform(put("/api/service-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDescriptionDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceDescription in the database
        List<ServiceDescription> serviceDescriptionList = serviceDescriptionRepository.findAll();
        assertThat(serviceDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ServiceDescription testServiceDescription = serviceDescriptionList.get(serviceDescriptionList.size() - 1);
        assertThat(testServiceDescription.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceDescription.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceDescription.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceDescription.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceDescription.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceDescription.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testServiceDescription.getServiceDescription()).isEqualTo(UPDATED_SERVICE_DESCRIPTION);
        assertThat(testServiceDescription.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testServiceDescription.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceDescription() throws Exception {
        int databaseSizeBeforeUpdate = serviceDescriptionRepository.findAll().size();

        // Create the ServiceDescription
        ServiceDescriptionDTO serviceDescriptionDTO = serviceDescriptionMapper.toDto(serviceDescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceDescriptionMockMvc.perform(put("/api/service-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDescription in the database
        List<ServiceDescription> serviceDescriptionList = serviceDescriptionRepository.findAll();
        assertThat(serviceDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceDescription() throws Exception {
        // Initialize the database
        serviceDescriptionRepository.saveAndFlush(serviceDescription);

        int databaseSizeBeforeDelete = serviceDescriptionRepository.findAll().size();

        // Delete the serviceDescription
        restServiceDescriptionMockMvc.perform(delete("/api/service-descriptions/{id}", serviceDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceDescription> serviceDescriptionList = serviceDescriptionRepository.findAll();
        assertThat(serviceDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDescription.class);
        ServiceDescription serviceDescription1 = new ServiceDescription();
        serviceDescription1.setId(1L);
        ServiceDescription serviceDescription2 = new ServiceDescription();
        serviceDescription2.setId(serviceDescription1.getId());
        assertThat(serviceDescription1).isEqualTo(serviceDescription2);
        serviceDescription2.setId(2L);
        assertThat(serviceDescription1).isNotEqualTo(serviceDescription2);
        serviceDescription1.setId(null);
        assertThat(serviceDescription1).isNotEqualTo(serviceDescription2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDescriptionDTO.class);
        ServiceDescriptionDTO serviceDescriptionDTO1 = new ServiceDescriptionDTO();
        serviceDescriptionDTO1.setId(1L);
        ServiceDescriptionDTO serviceDescriptionDTO2 = new ServiceDescriptionDTO();
        assertThat(serviceDescriptionDTO1).isNotEqualTo(serviceDescriptionDTO2);
        serviceDescriptionDTO2.setId(serviceDescriptionDTO1.getId());
        assertThat(serviceDescriptionDTO1).isEqualTo(serviceDescriptionDTO2);
        serviceDescriptionDTO2.setId(2L);
        assertThat(serviceDescriptionDTO1).isNotEqualTo(serviceDescriptionDTO2);
        serviceDescriptionDTO1.setId(null);
        assertThat(serviceDescriptionDTO1).isNotEqualTo(serviceDescriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceDescriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceDescriptionMapper.fromId(null)).isNull();
    }
}
