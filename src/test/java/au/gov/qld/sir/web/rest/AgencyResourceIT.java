package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.AgencyType;
import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.domain.IntegrationMapping;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import au.gov.qld.sir.repository.AgencyRepository;
import au.gov.qld.sir.service.AgencyService;
import au.gov.qld.sir.service.dto.AgencyDTO;
import au.gov.qld.sir.service.mapper.AgencyMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.AgencyCriteria;
import au.gov.qld.sir.service.AgencyQueryService;

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
 * Integration tests for the {@Link AgencyResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class AgencyResourceIT {

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

    private static final String DEFAULT_AGENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY_URL = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_URL = "BBBBBBBBBB";

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private AgencyMapper agencyMapper;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AgencyQueryService agencyQueryService;

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

    private MockMvc restAgencyMockMvc;

    private Agency agency;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencyResource agencyResource = new AgencyResource(agencyService, agencyQueryService);
        this.restAgencyMockMvc = MockMvcBuilders.standaloneSetup(agencyResource)
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
    public static Agency createEntity(EntityManager em) {
        Agency agency = new Agency()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .agencyCode(DEFAULT_AGENCY_CODE)
            .agencyName(DEFAULT_AGENCY_NAME)
            .agencyUrl(DEFAULT_AGENCY_URL);
        return agency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agency createUpdatedEntity(EntityManager em) {
        Agency agency = new Agency()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .agencyCode(UPDATED_AGENCY_CODE)
            .agencyName(UPDATED_AGENCY_NAME)
            .agencyUrl(UPDATED_AGENCY_URL);
        return agency;
    }

    @BeforeEach
    public void initTest() {
        agency = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgency() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);
        restAgencyMockMvc.perform(post("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate + 1);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAgency.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testAgency.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAgency.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testAgency.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAgency.getAgencyCode()).isEqualTo(DEFAULT_AGENCY_CODE);
        assertThat(testAgency.getAgencyName()).isEqualTo(DEFAULT_AGENCY_NAME);
        assertThat(testAgency.getAgencyUrl()).isEqualTo(DEFAULT_AGENCY_URL);
    }

    @Test
    @Transactional
    public void createAgencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // Create the Agency with an existing ID
        agency.setId(1L);
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencyMockMvc.perform(post("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgencies() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList
        restAgencyMockMvc.perform(get("/api/agencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].agencyCode").value(hasItem(DEFAULT_AGENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyUrl").value(hasItem(DEFAULT_AGENCY_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", agency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agency.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.agencyCode").value(DEFAULT_AGENCY_CODE.toString()))
            .andExpect(jsonPath("$.agencyName").value(DEFAULT_AGENCY_NAME.toString()))
            .andExpect(jsonPath("$.agencyUrl").value(DEFAULT_AGENCY_URL.toString()));
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdBy equals to DEFAULT_CREATED_BY
        defaultAgencyShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the agencyList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencyShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAgencyShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the agencyList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencyShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdBy is not null
        defaultAgencyShouldBeFound("createdBy.specified=true");

        // Get all the agencyList where createdBy is null
        defaultAgencyShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultAgencyShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the agencyList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencyShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultAgencyShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the agencyList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencyShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where createdDateTime is not null
        defaultAgencyShouldBeFound("createdDateTime.specified=true");

        // Get all the agencyList where createdDateTime is null
        defaultAgencyShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAgencyShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the agencyList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencyShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAgencyShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the agencyList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencyShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedBy is not null
        defaultAgencyShouldBeFound("modifiedBy.specified=true");

        // Get all the agencyList where modifiedBy is null
        defaultAgencyShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultAgencyShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the agencyList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencyShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultAgencyShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the agencyList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencyShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where modifiedDateTime is not null
        defaultAgencyShouldBeFound("modifiedDateTime.specified=true");

        // Get all the agencyList where modifiedDateTime is null
        defaultAgencyShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where version equals to DEFAULT_VERSION
        defaultAgencyShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the agencyList where version equals to UPDATED_VERSION
        defaultAgencyShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgenciesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAgencyShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the agencyList where version equals to UPDATED_VERSION
        defaultAgencyShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgenciesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where version is not null
        defaultAgencyShouldBeFound("version.specified=true");

        // Get all the agencyList where version is null
        defaultAgencyShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where version greater than or equals to DEFAULT_VERSION
        defaultAgencyShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the agencyList where version greater than or equals to UPDATED_VERSION
        defaultAgencyShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgenciesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where version less than or equals to DEFAULT_VERSION
        defaultAgencyShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the agencyList where version less than or equals to UPDATED_VERSION
        defaultAgencyShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAgenciesByAgencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyCode equals to DEFAULT_AGENCY_CODE
        defaultAgencyShouldBeFound("agencyCode.equals=" + DEFAULT_AGENCY_CODE);

        // Get all the agencyList where agencyCode equals to UPDATED_AGENCY_CODE
        defaultAgencyShouldNotBeFound("agencyCode.equals=" + UPDATED_AGENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyCode in DEFAULT_AGENCY_CODE or UPDATED_AGENCY_CODE
        defaultAgencyShouldBeFound("agencyCode.in=" + DEFAULT_AGENCY_CODE + "," + UPDATED_AGENCY_CODE);

        // Get all the agencyList where agencyCode equals to UPDATED_AGENCY_CODE
        defaultAgencyShouldNotBeFound("agencyCode.in=" + UPDATED_AGENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyCode is not null
        defaultAgencyShouldBeFound("agencyCode.specified=true");

        // Get all the agencyList where agencyCode is null
        defaultAgencyShouldNotBeFound("agencyCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyName equals to DEFAULT_AGENCY_NAME
        defaultAgencyShouldBeFound("agencyName.equals=" + DEFAULT_AGENCY_NAME);

        // Get all the agencyList where agencyName equals to UPDATED_AGENCY_NAME
        defaultAgencyShouldNotBeFound("agencyName.equals=" + UPDATED_AGENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyName in DEFAULT_AGENCY_NAME or UPDATED_AGENCY_NAME
        defaultAgencyShouldBeFound("agencyName.in=" + DEFAULT_AGENCY_NAME + "," + UPDATED_AGENCY_NAME);

        // Get all the agencyList where agencyName equals to UPDATED_AGENCY_NAME
        defaultAgencyShouldNotBeFound("agencyName.in=" + UPDATED_AGENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyName is not null
        defaultAgencyShouldBeFound("agencyName.specified=true");

        // Get all the agencyList where agencyName is null
        defaultAgencyShouldNotBeFound("agencyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyUrl equals to DEFAULT_AGENCY_URL
        defaultAgencyShouldBeFound("agencyUrl.equals=" + DEFAULT_AGENCY_URL);

        // Get all the agencyList where agencyUrl equals to UPDATED_AGENCY_URL
        defaultAgencyShouldNotBeFound("agencyUrl.equals=" + UPDATED_AGENCY_URL);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyUrlIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyUrl in DEFAULT_AGENCY_URL or UPDATED_AGENCY_URL
        defaultAgencyShouldBeFound("agencyUrl.in=" + DEFAULT_AGENCY_URL + "," + UPDATED_AGENCY_URL);

        // Get all the agencyList where agencyUrl equals to UPDATED_AGENCY_URL
        defaultAgencyShouldNotBeFound("agencyUrl.in=" + UPDATED_AGENCY_URL);
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where agencyUrl is not null
        defaultAgencyShouldBeFound("agencyUrl.specified=true");

        // Get all the agencyList where agencyUrl is null
        defaultAgencyShouldNotBeFound("agencyUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgenciesByAgencyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        AgencyType agencyType = AgencyTypeResourceIT.createEntity(em);
        em.persist(agencyType);
        em.flush();
        agency.setAgencyType(agencyType);
        agencyRepository.saveAndFlush(agency);
        Long agencyTypeId = agencyType.getId();

        // Get all the agencyList where agencyType equals to agencyTypeId
        defaultAgencyShouldBeFound("agencyTypeId.equals=" + agencyTypeId);

        // Get all the agencyList where agencyType equals to agencyTypeId + 1
        defaultAgencyShouldNotBeFound("agencyTypeId.equals=" + (agencyTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllAgenciesByAgencySupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        AgencySupportRole agencySupportRole = AgencySupportRoleResourceIT.createEntity(em);
        em.persist(agencySupportRole);
        em.flush();
        agency.addAgencySupportRole(agencySupportRole);
        agencyRepository.saveAndFlush(agency);
        Long agencySupportRoleId = agencySupportRole.getId();

        // Get all the agencyList where agencySupportRole equals to agencySupportRoleId
        defaultAgencyShouldBeFound("agencySupportRoleId.equals=" + agencySupportRoleId);

        // Get all the agencyList where agencySupportRole equals to agencySupportRoleId + 1
        defaultAgencyShouldNotBeFound("agencySupportRoleId.equals=" + (agencySupportRoleId + 1));
    }


    @Test
    @Transactional
    public void getAllAgenciesByIntegrationMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        IntegrationMapping integrationMapping = IntegrationMappingResourceIT.createEntity(em);
        em.persist(integrationMapping);
        em.flush();
        agency.addIntegrationMapping(integrationMapping);
        agencyRepository.saveAndFlush(agency);
        Long integrationMappingId = integrationMapping.getId();

        // Get all the agencyList where integrationMapping equals to integrationMappingId
        defaultAgencyShouldBeFound("integrationMappingId.equals=" + integrationMappingId);

        // Get all the agencyList where integrationMapping equals to integrationMappingId + 1
        defaultAgencyShouldNotBeFound("integrationMappingId.equals=" + (integrationMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllAgenciesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        agency.addLocation(location);
        agencyRepository.saveAndFlush(agency);
        Long locationId = location.getId();

        // Get all the agencyList where location equals to locationId
        defaultAgencyShouldBeFound("locationId.equals=" + locationId);

        // Get all the agencyList where location equals to locationId + 1
        defaultAgencyShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllAgenciesByServiceDeliveryOrganisationIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDeliveryOrganisation serviceDeliveryOrganisation = ServiceDeliveryOrganisationResourceIT.createEntity(em);
        em.persist(serviceDeliveryOrganisation);
        em.flush();
        agency.addServiceDeliveryOrganisation(serviceDeliveryOrganisation);
        agencyRepository.saveAndFlush(agency);
        Long serviceDeliveryOrganisationId = serviceDeliveryOrganisation.getId();

        // Get all the agencyList where serviceDeliveryOrganisation equals to serviceDeliveryOrganisationId
        defaultAgencyShouldBeFound("serviceDeliveryOrganisationId.equals=" + serviceDeliveryOrganisationId);

        // Get all the agencyList where serviceDeliveryOrganisation equals to serviceDeliveryOrganisationId + 1
        defaultAgencyShouldNotBeFound("serviceDeliveryOrganisationId.equals=" + (serviceDeliveryOrganisationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgencyShouldBeFound(String filter) throws Exception {
        restAgencyMockMvc.perform(get("/api/agencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].agencyCode").value(hasItem(DEFAULT_AGENCY_CODE)))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME)))
            .andExpect(jsonPath("$.[*].agencyUrl").value(hasItem(DEFAULT_AGENCY_URL)));

        // Check, that the count call also returns 1
        restAgencyMockMvc.perform(get("/api/agencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgencyShouldNotBeFound(String filter) throws Exception {
        restAgencyMockMvc.perform(get("/api/agencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgencyMockMvc.perform(get("/api/agencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgency() throws Exception {
        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency
        Agency updatedAgency = agencyRepository.findById(agency.getId()).get();
        // Disconnect from session so that the updates on updatedAgency are not directly saved in db
        em.detach(updatedAgency);
        updatedAgency
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .agencyCode(UPDATED_AGENCY_CODE)
            .agencyName(UPDATED_AGENCY_NAME)
            .agencyUrl(UPDATED_AGENCY_URL);
        AgencyDTO agencyDTO = agencyMapper.toDto(updatedAgency);

        restAgencyMockMvc.perform(put("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAgency.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testAgency.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAgency.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testAgency.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAgency.getAgencyCode()).isEqualTo(UPDATED_AGENCY_CODE);
        assertThat(testAgency.getAgencyName()).isEqualTo(UPDATED_AGENCY_NAME);
        assertThat(testAgency.getAgencyUrl()).isEqualTo(UPDATED_AGENCY_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencyMockMvc.perform(put("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeDelete = agencyRepository.findAll().size();

        // Delete the agency
        restAgencyMockMvc.perform(delete("/api/agencies/{id}", agency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agency.class);
        Agency agency1 = new Agency();
        agency1.setId(1L);
        Agency agency2 = new Agency();
        agency2.setId(agency1.getId());
        assertThat(agency1).isEqualTo(agency2);
        agency2.setId(2L);
        assertThat(agency1).isNotEqualTo(agency2);
        agency1.setId(null);
        assertThat(agency1).isNotEqualTo(agency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyDTO.class);
        AgencyDTO agencyDTO1 = new AgencyDTO();
        agencyDTO1.setId(1L);
        AgencyDTO agencyDTO2 = new AgencyDTO();
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
        agencyDTO2.setId(agencyDTO1.getId());
        assertThat(agencyDTO1).isEqualTo(agencyDTO2);
        agencyDTO2.setId(2L);
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
        agencyDTO1.setId(null);
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencyMapper.fromId(null)).isNull();
    }
}
