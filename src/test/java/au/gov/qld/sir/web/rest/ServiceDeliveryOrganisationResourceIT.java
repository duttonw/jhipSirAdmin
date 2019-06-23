package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceDeliveryOrganisationRepository;
import au.gov.qld.sir.service.ServiceDeliveryOrganisationService;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryOrganisationMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceDeliveryOrganisationCriteria;
import au.gov.qld.sir.service.ServiceDeliveryOrganisationQueryService;

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
 * Integration tests for the {@Link ServiceDeliveryOrganisationResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceDeliveryOrganisationResourceIT {

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

    private static final String DEFAULT_BUSINESS_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_UNIT_NAME = "BBBBBBBBBB";

    @Autowired
    private ServiceDeliveryOrganisationRepository serviceDeliveryOrganisationRepository;

    @Autowired
    private ServiceDeliveryOrganisationMapper serviceDeliveryOrganisationMapper;

    @Autowired
    private ServiceDeliveryOrganisationService serviceDeliveryOrganisationService;

    @Autowired
    private ServiceDeliveryOrganisationQueryService serviceDeliveryOrganisationQueryService;

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

    private MockMvc restServiceDeliveryOrganisationMockMvc;

    private ServiceDeliveryOrganisation serviceDeliveryOrganisation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceDeliveryOrganisationResource serviceDeliveryOrganisationResource = new ServiceDeliveryOrganisationResource(serviceDeliveryOrganisationService, serviceDeliveryOrganisationQueryService);
        this.restServiceDeliveryOrganisationMockMvc = MockMvcBuilders.standaloneSetup(serviceDeliveryOrganisationResource)
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
    public static ServiceDeliveryOrganisation createEntity(EntityManager em) {
        ServiceDeliveryOrganisation serviceDeliveryOrganisation = new ServiceDeliveryOrganisation()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .businessUnitName(DEFAULT_BUSINESS_UNIT_NAME);
        return serviceDeliveryOrganisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceDeliveryOrganisation createUpdatedEntity(EntityManager em) {
        ServiceDeliveryOrganisation serviceDeliveryOrganisation = new ServiceDeliveryOrganisation()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .businessUnitName(UPDATED_BUSINESS_UNIT_NAME);
        return serviceDeliveryOrganisation;
    }

    @BeforeEach
    public void initTest() {
        serviceDeliveryOrganisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceDeliveryOrganisation() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryOrganisationRepository.findAll().size();

        // Create the ServiceDeliveryOrganisation
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO = serviceDeliveryOrganisationMapper.toDto(serviceDeliveryOrganisation);
        restServiceDeliveryOrganisationMockMvc.perform(post("/api/service-delivery-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryOrganisationDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceDeliveryOrganisation in the database
        List<ServiceDeliveryOrganisation> serviceDeliveryOrganisationList = serviceDeliveryOrganisationRepository.findAll();
        assertThat(serviceDeliveryOrganisationList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceDeliveryOrganisation testServiceDeliveryOrganisation = serviceDeliveryOrganisationList.get(serviceDeliveryOrganisationList.size() - 1);
        assertThat(testServiceDeliveryOrganisation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceDeliveryOrganisation.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceDeliveryOrganisation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceDeliveryOrganisation.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceDeliveryOrganisation.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceDeliveryOrganisation.getBusinessUnitName()).isEqualTo(DEFAULT_BUSINESS_UNIT_NAME);
    }

    @Test
    @Transactional
    public void createServiceDeliveryOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryOrganisationRepository.findAll().size();

        // Create the ServiceDeliveryOrganisation with an existing ID
        serviceDeliveryOrganisation.setId(1L);
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO = serviceDeliveryOrganisationMapper.toDto(serviceDeliveryOrganisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceDeliveryOrganisationMockMvc.perform(post("/api/service-delivery-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDeliveryOrganisation in the database
        List<ServiceDeliveryOrganisation> serviceDeliveryOrganisationList = serviceDeliveryOrganisationRepository.findAll();
        assertThat(serviceDeliveryOrganisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisations() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDeliveryOrganisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].businessUnitName").value(hasItem(DEFAULT_BUSINESS_UNIT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceDeliveryOrganisation() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get the serviceDeliveryOrganisation
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations/{id}", serviceDeliveryOrganisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceDeliveryOrganisation.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.businessUnitName").value(DEFAULT_BUSINESS_UNIT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceDeliveryOrganisationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceDeliveryOrganisationList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceDeliveryOrganisationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceDeliveryOrganisationList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdBy is not null
        defaultServiceDeliveryOrganisationShouldBeFound("createdBy.specified=true");

        // Get all the serviceDeliveryOrganisationList where createdBy is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceDeliveryOrganisationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceDeliveryOrganisationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where createdDateTime is not null
        defaultServiceDeliveryOrganisationShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceDeliveryOrganisationList where createdDateTime is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceDeliveryOrganisationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceDeliveryOrganisationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedBy is not null
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceDeliveryOrganisationList where modifiedBy is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime is not null
        defaultServiceDeliveryOrganisationShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceDeliveryOrganisationList where modifiedDateTime is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where version equals to DEFAULT_VERSION
        defaultServiceDeliveryOrganisationShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryOrganisationList where version equals to UPDATED_VERSION
        defaultServiceDeliveryOrganisationShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceDeliveryOrganisationShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceDeliveryOrganisationList where version equals to UPDATED_VERSION
        defaultServiceDeliveryOrganisationShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where version is not null
        defaultServiceDeliveryOrganisationShouldBeFound("version.specified=true");

        // Get all the serviceDeliveryOrganisationList where version is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where version greater than or equals to DEFAULT_VERSION
        defaultServiceDeliveryOrganisationShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryOrganisationList where version greater than or equals to UPDATED_VERSION
        defaultServiceDeliveryOrganisationShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where version less than or equals to DEFAULT_VERSION
        defaultServiceDeliveryOrganisationShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryOrganisationList where version less than or equals to UPDATED_VERSION
        defaultServiceDeliveryOrganisationShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByBusinessUnitNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where businessUnitName equals to DEFAULT_BUSINESS_UNIT_NAME
        defaultServiceDeliveryOrganisationShouldBeFound("businessUnitName.equals=" + DEFAULT_BUSINESS_UNIT_NAME);

        // Get all the serviceDeliveryOrganisationList where businessUnitName equals to UPDATED_BUSINESS_UNIT_NAME
        defaultServiceDeliveryOrganisationShouldNotBeFound("businessUnitName.equals=" + UPDATED_BUSINESS_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByBusinessUnitNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where businessUnitName in DEFAULT_BUSINESS_UNIT_NAME or UPDATED_BUSINESS_UNIT_NAME
        defaultServiceDeliveryOrganisationShouldBeFound("businessUnitName.in=" + DEFAULT_BUSINESS_UNIT_NAME + "," + UPDATED_BUSINESS_UNIT_NAME);

        // Get all the serviceDeliveryOrganisationList where businessUnitName equals to UPDATED_BUSINESS_UNIT_NAME
        defaultServiceDeliveryOrganisationShouldNotBeFound("businessUnitName.in=" + UPDATED_BUSINESS_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByBusinessUnitNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        // Get all the serviceDeliveryOrganisationList where businessUnitName is not null
        defaultServiceDeliveryOrganisationShouldBeFound("businessUnitName.specified=true");

        // Get all the serviceDeliveryOrganisationList where businessUnitName is null
        defaultServiceDeliveryOrganisationShouldNotBeFound("businessUnitName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Agency agency = AgencyResourceIT.createEntity(em);
        em.persist(agency);
        em.flush();
        serviceDeliveryOrganisation.setAgency(agency);
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);
        Long agencyId = agency.getId();

        // Get all the serviceDeliveryOrganisationList where agency equals to agencyId
        defaultServiceDeliveryOrganisationShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the serviceDeliveryOrganisationList where agency equals to agencyId + 1
        defaultServiceDeliveryOrganisationShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceDeliveryOrganisationsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceDeliveryOrganisation.addServiceRecord(serviceRecord);
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceDeliveryOrganisationList where serviceRecord equals to serviceRecordId
        defaultServiceDeliveryOrganisationShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceDeliveryOrganisationList where serviceRecord equals to serviceRecordId + 1
        defaultServiceDeliveryOrganisationShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceDeliveryOrganisationShouldBeFound(String filter) throws Exception {
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDeliveryOrganisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].businessUnitName").value(hasItem(DEFAULT_BUSINESS_UNIT_NAME)));

        // Check, that the count call also returns 1
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceDeliveryOrganisationShouldNotBeFound(String filter) throws Exception {
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceDeliveryOrganisation() throws Exception {
        // Get the serviceDeliveryOrganisation
        restServiceDeliveryOrganisationMockMvc.perform(get("/api/service-delivery-organisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceDeliveryOrganisation() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        int databaseSizeBeforeUpdate = serviceDeliveryOrganisationRepository.findAll().size();

        // Update the serviceDeliveryOrganisation
        ServiceDeliveryOrganisation updatedServiceDeliveryOrganisation = serviceDeliveryOrganisationRepository.findById(serviceDeliveryOrganisation.getId()).get();
        // Disconnect from session so that the updates on updatedServiceDeliveryOrganisation are not directly saved in db
        em.detach(updatedServiceDeliveryOrganisation);
        updatedServiceDeliveryOrganisation
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .businessUnitName(UPDATED_BUSINESS_UNIT_NAME);
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO = serviceDeliveryOrganisationMapper.toDto(updatedServiceDeliveryOrganisation);

        restServiceDeliveryOrganisationMockMvc.perform(put("/api/service-delivery-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryOrganisationDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceDeliveryOrganisation in the database
        List<ServiceDeliveryOrganisation> serviceDeliveryOrganisationList = serviceDeliveryOrganisationRepository.findAll();
        assertThat(serviceDeliveryOrganisationList).hasSize(databaseSizeBeforeUpdate);
        ServiceDeliveryOrganisation testServiceDeliveryOrganisation = serviceDeliveryOrganisationList.get(serviceDeliveryOrganisationList.size() - 1);
        assertThat(testServiceDeliveryOrganisation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceDeliveryOrganisation.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceDeliveryOrganisation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceDeliveryOrganisation.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceDeliveryOrganisation.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceDeliveryOrganisation.getBusinessUnitName()).isEqualTo(UPDATED_BUSINESS_UNIT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceDeliveryOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = serviceDeliveryOrganisationRepository.findAll().size();

        // Create the ServiceDeliveryOrganisation
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO = serviceDeliveryOrganisationMapper.toDto(serviceDeliveryOrganisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceDeliveryOrganisationMockMvc.perform(put("/api/service-delivery-organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDeliveryOrganisation in the database
        List<ServiceDeliveryOrganisation> serviceDeliveryOrganisationList = serviceDeliveryOrganisationRepository.findAll();
        assertThat(serviceDeliveryOrganisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceDeliveryOrganisation() throws Exception {
        // Initialize the database
        serviceDeliveryOrganisationRepository.saveAndFlush(serviceDeliveryOrganisation);

        int databaseSizeBeforeDelete = serviceDeliveryOrganisationRepository.findAll().size();

        // Delete the serviceDeliveryOrganisation
        restServiceDeliveryOrganisationMockMvc.perform(delete("/api/service-delivery-organisations/{id}", serviceDeliveryOrganisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceDeliveryOrganisation> serviceDeliveryOrganisationList = serviceDeliveryOrganisationRepository.findAll();
        assertThat(serviceDeliveryOrganisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDeliveryOrganisation.class);
        ServiceDeliveryOrganisation serviceDeliveryOrganisation1 = new ServiceDeliveryOrganisation();
        serviceDeliveryOrganisation1.setId(1L);
        ServiceDeliveryOrganisation serviceDeliveryOrganisation2 = new ServiceDeliveryOrganisation();
        serviceDeliveryOrganisation2.setId(serviceDeliveryOrganisation1.getId());
        assertThat(serviceDeliveryOrganisation1).isEqualTo(serviceDeliveryOrganisation2);
        serviceDeliveryOrganisation2.setId(2L);
        assertThat(serviceDeliveryOrganisation1).isNotEqualTo(serviceDeliveryOrganisation2);
        serviceDeliveryOrganisation1.setId(null);
        assertThat(serviceDeliveryOrganisation1).isNotEqualTo(serviceDeliveryOrganisation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDeliveryOrganisationDTO.class);
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO1 = new ServiceDeliveryOrganisationDTO();
        serviceDeliveryOrganisationDTO1.setId(1L);
        ServiceDeliveryOrganisationDTO serviceDeliveryOrganisationDTO2 = new ServiceDeliveryOrganisationDTO();
        assertThat(serviceDeliveryOrganisationDTO1).isNotEqualTo(serviceDeliveryOrganisationDTO2);
        serviceDeliveryOrganisationDTO2.setId(serviceDeliveryOrganisationDTO1.getId());
        assertThat(serviceDeliveryOrganisationDTO1).isEqualTo(serviceDeliveryOrganisationDTO2);
        serviceDeliveryOrganisationDTO2.setId(2L);
        assertThat(serviceDeliveryOrganisationDTO1).isNotEqualTo(serviceDeliveryOrganisationDTO2);
        serviceDeliveryOrganisationDTO1.setId(null);
        assertThat(serviceDeliveryOrganisationDTO1).isNotEqualTo(serviceDeliveryOrganisationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceDeliveryOrganisationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceDeliveryOrganisationMapper.fromId(null)).isNull();
    }
}
