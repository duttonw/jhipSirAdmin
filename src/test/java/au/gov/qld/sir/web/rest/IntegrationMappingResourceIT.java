package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.IntegrationMapping;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.IntegrationMappingRepository;
import au.gov.qld.sir.service.IntegrationMappingService;
import au.gov.qld.sir.service.dto.IntegrationMappingDTO;
import au.gov.qld.sir.service.mapper.IntegrationMappingMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.IntegrationMappingCriteria;
import au.gov.qld.sir.service.IntegrationMappingQueryService;

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
 * Integration tests for the {@Link IntegrationMappingResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class IntegrationMappingResourceIT {

    private static final String DEFAULT_AGENCY_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntegrationMappingRepository integrationMappingRepository;

    @Autowired
    private IntegrationMappingMapper integrationMappingMapper;

    @Autowired
    private IntegrationMappingService integrationMappingService;

    @Autowired
    private IntegrationMappingQueryService integrationMappingQueryService;

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

    private MockMvc restIntegrationMappingMockMvc;

    private IntegrationMapping integrationMapping;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntegrationMappingResource integrationMappingResource = new IntegrationMappingResource(integrationMappingService, integrationMappingQueryService);
        this.restIntegrationMappingMockMvc = MockMvcBuilders.standaloneSetup(integrationMappingResource)
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
    public static IntegrationMapping createEntity(EntityManager em) {
        IntegrationMapping integrationMapping = new IntegrationMapping()
            .agencyServiceId(DEFAULT_AGENCY_SERVICE_ID)
            .serviceName(DEFAULT_SERVICE_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME);
        // Add required entity
        Agency agency;
        if (TestUtil.findAll(em, Agency.class).isEmpty()) {
            agency = AgencyResourceIT.createEntity(em);
            em.persist(agency);
            em.flush();
        } else {
            agency = TestUtil.findAll(em, Agency.class).get(0);
        }
        integrationMapping.setAgency(agency);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        integrationMapping.setServiceRecord(serviceRecord);
        return integrationMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationMapping createUpdatedEntity(EntityManager em) {
        IntegrationMapping integrationMapping = new IntegrationMapping()
            .agencyServiceId(UPDATED_AGENCY_SERVICE_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        // Add required entity
        Agency agency;
        if (TestUtil.findAll(em, Agency.class).isEmpty()) {
            agency = AgencyResourceIT.createUpdatedEntity(em);
            em.persist(agency);
            em.flush();
        } else {
            agency = TestUtil.findAll(em, Agency.class).get(0);
        }
        integrationMapping.setAgency(agency);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createUpdatedEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        integrationMapping.setServiceRecord(serviceRecord);
        return integrationMapping;
    }

    @BeforeEach
    public void initTest() {
        integrationMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntegrationMapping() throws Exception {
        int databaseSizeBeforeCreate = integrationMappingRepository.findAll().size();

        // Create the IntegrationMapping
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(integrationMapping);
        restIntegrationMappingMockMvc.perform(post("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the IntegrationMapping in the database
        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeCreate + 1);
        IntegrationMapping testIntegrationMapping = integrationMappingList.get(integrationMappingList.size() - 1);
        assertThat(testIntegrationMapping.getAgencyServiceId()).isEqualTo(DEFAULT_AGENCY_SERVICE_ID);
        assertThat(testIntegrationMapping.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testIntegrationMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIntegrationMapping.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testIntegrationMapping.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testIntegrationMapping.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void createIntegrationMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = integrationMappingRepository.findAll().size();

        // Create the IntegrationMapping with an existing ID
        integrationMapping.setId(1L);
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(integrationMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrationMappingMockMvc.perform(post("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrationMapping in the database
        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAgencyServiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = integrationMappingRepository.findAll().size();
        // set the field null
        integrationMapping.setAgencyServiceId(null);

        // Create the IntegrationMapping, which fails.
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(integrationMapping);

        restIntegrationMappingMockMvc.perform(post("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isBadRequest());

        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = integrationMappingRepository.findAll().size();
        // set the field null
        integrationMapping.setServiceName(null);

        // Create the IntegrationMapping, which fails.
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(integrationMapping);

        restIntegrationMappingMockMvc.perform(post("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isBadRequest());

        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappings() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].agencyServiceId").value(hasItem(DEFAULT_AGENCY_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getIntegrationMapping() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get the integrationMapping
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings/{id}", integrationMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(integrationMapping.getId().intValue()))
            .andExpect(jsonPath("$.agencyServiceId").value(DEFAULT_AGENCY_SERVICE_ID.toString()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByAgencyServiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where agencyServiceId equals to DEFAULT_AGENCY_SERVICE_ID
        defaultIntegrationMappingShouldBeFound("agencyServiceId.equals=" + DEFAULT_AGENCY_SERVICE_ID);

        // Get all the integrationMappingList where agencyServiceId equals to UPDATED_AGENCY_SERVICE_ID
        defaultIntegrationMappingShouldNotBeFound("agencyServiceId.equals=" + UPDATED_AGENCY_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByAgencyServiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where agencyServiceId in DEFAULT_AGENCY_SERVICE_ID or UPDATED_AGENCY_SERVICE_ID
        defaultIntegrationMappingShouldBeFound("agencyServiceId.in=" + DEFAULT_AGENCY_SERVICE_ID + "," + UPDATED_AGENCY_SERVICE_ID);

        // Get all the integrationMappingList where agencyServiceId equals to UPDATED_AGENCY_SERVICE_ID
        defaultIntegrationMappingShouldNotBeFound("agencyServiceId.in=" + UPDATED_AGENCY_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByAgencyServiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where agencyServiceId is not null
        defaultIntegrationMappingShouldBeFound("agencyServiceId.specified=true");

        // Get all the integrationMappingList where agencyServiceId is null
        defaultIntegrationMappingShouldNotBeFound("agencyServiceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultIntegrationMappingShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the integrationMappingList where serviceName equals to UPDATED_SERVICE_NAME
        defaultIntegrationMappingShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultIntegrationMappingShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the integrationMappingList where serviceName equals to UPDATED_SERVICE_NAME
        defaultIntegrationMappingShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where serviceName is not null
        defaultIntegrationMappingShouldBeFound("serviceName.specified=true");

        // Get all the integrationMappingList where serviceName is null
        defaultIntegrationMappingShouldNotBeFound("serviceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdBy equals to DEFAULT_CREATED_BY
        defaultIntegrationMappingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the integrationMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultIntegrationMappingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultIntegrationMappingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the integrationMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultIntegrationMappingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdBy is not null
        defaultIntegrationMappingShouldBeFound("createdBy.specified=true");

        // Get all the integrationMappingList where createdBy is null
        defaultIntegrationMappingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultIntegrationMappingShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the integrationMappingList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultIntegrationMappingShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultIntegrationMappingShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the integrationMappingList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultIntegrationMappingShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where createdDateTime is not null
        defaultIntegrationMappingShouldBeFound("createdDateTime.specified=true");

        // Get all the integrationMappingList where createdDateTime is null
        defaultIntegrationMappingShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultIntegrationMappingShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the integrationMappingList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultIntegrationMappingShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultIntegrationMappingShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the integrationMappingList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultIntegrationMappingShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedBy is not null
        defaultIntegrationMappingShouldBeFound("modifiedBy.specified=true");

        // Get all the integrationMappingList where modifiedBy is null
        defaultIntegrationMappingShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultIntegrationMappingShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the integrationMappingList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultIntegrationMappingShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultIntegrationMappingShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the integrationMappingList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultIntegrationMappingShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        // Get all the integrationMappingList where modifiedDateTime is not null
        defaultIntegrationMappingShouldBeFound("modifiedDateTime.specified=true");

        // Get all the integrationMappingList where modifiedDateTime is null
        defaultIntegrationMappingShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegrationMappingsByAgencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        Agency agency = integrationMapping.getAgency();
        integrationMappingRepository.saveAndFlush(integrationMapping);
        Long agencyId = agency.getId();

        // Get all the integrationMappingList where agency equals to agencyId
        defaultIntegrationMappingShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the integrationMappingList where agency equals to agencyId + 1
        defaultIntegrationMappingShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }


    @Test
    @Transactional
    public void getAllIntegrationMappingsByServiceRecordIsEqualToSomething() throws Exception {
        // Get already existing entity
        ServiceRecord serviceRecord = integrationMapping.getServiceRecord();
        integrationMappingRepository.saveAndFlush(integrationMapping);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the integrationMappingList where serviceRecord equals to serviceRecordId
        defaultIntegrationMappingShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the integrationMappingList where serviceRecord equals to serviceRecordId + 1
        defaultIntegrationMappingShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntegrationMappingShouldBeFound(String filter) throws Exception {
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].agencyServiceId").value(hasItem(DEFAULT_AGENCY_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntegrationMappingShouldNotBeFound(String filter) throws Exception {
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIntegrationMapping() throws Exception {
        // Get the integrationMapping
        restIntegrationMappingMockMvc.perform(get("/api/integration-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrationMapping() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        int databaseSizeBeforeUpdate = integrationMappingRepository.findAll().size();

        // Update the integrationMapping
        IntegrationMapping updatedIntegrationMapping = integrationMappingRepository.findById(integrationMapping.getId()).get();
        // Disconnect from session so that the updates on updatedIntegrationMapping are not directly saved in db
        em.detach(updatedIntegrationMapping);
        updatedIntegrationMapping
            .agencyServiceId(UPDATED_AGENCY_SERVICE_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(updatedIntegrationMapping);

        restIntegrationMappingMockMvc.perform(put("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isOk());

        // Validate the IntegrationMapping in the database
        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeUpdate);
        IntegrationMapping testIntegrationMapping = integrationMappingList.get(integrationMappingList.size() - 1);
        assertThat(testIntegrationMapping.getAgencyServiceId()).isEqualTo(UPDATED_AGENCY_SERVICE_ID);
        assertThat(testIntegrationMapping.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testIntegrationMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIntegrationMapping.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testIntegrationMapping.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testIntegrationMapping.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingIntegrationMapping() throws Exception {
        int databaseSizeBeforeUpdate = integrationMappingRepository.findAll().size();

        // Create the IntegrationMapping
        IntegrationMappingDTO integrationMappingDTO = integrationMappingMapper.toDto(integrationMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationMappingMockMvc.perform(put("/api/integration-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrationMapping in the database
        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntegrationMapping() throws Exception {
        // Initialize the database
        integrationMappingRepository.saveAndFlush(integrationMapping);

        int databaseSizeBeforeDelete = integrationMappingRepository.findAll().size();

        // Delete the integrationMapping
        restIntegrationMappingMockMvc.perform(delete("/api/integration-mappings/{id}", integrationMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntegrationMapping> integrationMappingList = integrationMappingRepository.findAll();
        assertThat(integrationMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationMapping.class);
        IntegrationMapping integrationMapping1 = new IntegrationMapping();
        integrationMapping1.setId(1L);
        IntegrationMapping integrationMapping2 = new IntegrationMapping();
        integrationMapping2.setId(integrationMapping1.getId());
        assertThat(integrationMapping1).isEqualTo(integrationMapping2);
        integrationMapping2.setId(2L);
        assertThat(integrationMapping1).isNotEqualTo(integrationMapping2);
        integrationMapping1.setId(null);
        assertThat(integrationMapping1).isNotEqualTo(integrationMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationMappingDTO.class);
        IntegrationMappingDTO integrationMappingDTO1 = new IntegrationMappingDTO();
        integrationMappingDTO1.setId(1L);
        IntegrationMappingDTO integrationMappingDTO2 = new IntegrationMappingDTO();
        assertThat(integrationMappingDTO1).isNotEqualTo(integrationMappingDTO2);
        integrationMappingDTO2.setId(integrationMappingDTO1.getId());
        assertThat(integrationMappingDTO1).isEqualTo(integrationMappingDTO2);
        integrationMappingDTO2.setId(2L);
        assertThat(integrationMappingDTO1).isNotEqualTo(integrationMappingDTO2);
        integrationMappingDTO1.setId(null);
        assertThat(integrationMappingDTO1).isNotEqualTo(integrationMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(integrationMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(integrationMappingMapper.fromId(null)).isNull();
    }
}
