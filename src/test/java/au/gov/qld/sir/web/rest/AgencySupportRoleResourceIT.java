package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.ServiceRoleType;
import au.gov.qld.sir.domain.AgencySupportRoleContextType;
import au.gov.qld.sir.repository.AgencySupportRoleRepository;
import au.gov.qld.sir.service.AgencySupportRoleService;
import au.gov.qld.sir.service.dto.AgencySupportRoleDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.AgencySupportRoleCriteria;
import au.gov.qld.sir.service.AgencySupportRoleQueryService;

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
 * Integration tests for the {@Link AgencySupportRoleResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class AgencySupportRoleResourceIT {

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

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    @Autowired
    private AgencySupportRoleRepository agencySupportRoleRepository;

    @Autowired
    private AgencySupportRoleMapper agencySupportRoleMapper;

    @Autowired
    private AgencySupportRoleService agencySupportRoleService;

    @Autowired
    private AgencySupportRoleQueryService agencySupportRoleQueryService;

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

    private MockMvc restAgencySupportRoleMockMvc;

    private AgencySupportRole agencySupportRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencySupportRoleResource agencySupportRoleResource = new AgencySupportRoleResource(agencySupportRoleService, agencySupportRoleQueryService);
        this.restAgencySupportRoleMockMvc = MockMvcBuilders.standaloneSetup(agencySupportRoleResource)
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
    public static AgencySupportRole createEntity(EntityManager em) {
        AgencySupportRole agencySupportRole = new AgencySupportRole()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .contactEmail(DEFAULT_CONTACT_EMAIL);
        return agencySupportRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgencySupportRole createUpdatedEntity(EntityManager em) {
        AgencySupportRole agencySupportRole = new AgencySupportRole()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .contactEmail(UPDATED_CONTACT_EMAIL);
        return agencySupportRole;
    }

    @BeforeEach
    public void initTest() {
        agencySupportRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgencySupportRole() throws Exception {
        int databaseSizeBeforeCreate = agencySupportRoleRepository.findAll().size();

        // Create the AgencySupportRole
        AgencySupportRoleDTO agencySupportRoleDTO = agencySupportRoleMapper.toDto(agencySupportRole);
        restAgencySupportRoleMockMvc.perform(post("/api/agency-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the AgencySupportRole in the database
        List<AgencySupportRole> agencySupportRoleList = agencySupportRoleRepository.findAll();
        assertThat(agencySupportRoleList).hasSize(databaseSizeBeforeCreate + 1);
        AgencySupportRole testAgencySupportRole = agencySupportRoleList.get(agencySupportRoleList.size() - 1);
        assertThat(testAgencySupportRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAgencySupportRole.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testAgencySupportRole.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAgencySupportRole.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testAgencySupportRole.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAgencySupportRole.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void createAgencySupportRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencySupportRoleRepository.findAll().size();

        // Create the AgencySupportRole with an existing ID
        agencySupportRole.setId(1L);
        AgencySupportRoleDTO agencySupportRoleDTO = agencySupportRoleMapper.toDto(agencySupportRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencySupportRoleMockMvc.perform(post("/api/agency-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencySupportRole in the database
        List<AgencySupportRole> agencySupportRoleList = agencySupportRoleRepository.findAll();
        assertThat(agencySupportRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgencySupportRoles() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencySupportRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getAgencySupportRole() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get the agencySupportRole
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles/{id}", agencySupportRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agencySupportRole.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdBy equals to DEFAULT_CREATED_BY
        defaultAgencySupportRoleShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the agencySupportRoleList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencySupportRoleShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAgencySupportRoleShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the agencySupportRoleList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencySupportRoleShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdBy is not null
        defaultAgencySupportRoleShouldBeFound("createdBy.specified=true");

        // Get all the agencySupportRoleList where createdBy is null
        defaultAgencySupportRoleShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultAgencySupportRoleShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the agencySupportRoleList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the agencySupportRoleList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where createdDateTime is not null
        defaultAgencySupportRoleShouldBeFound("createdDateTime.specified=true");

        // Get all the agencySupportRoleList where createdDateTime is null
        defaultAgencySupportRoleShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAgencySupportRoleShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the agencySupportRoleList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencySupportRoleShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAgencySupportRoleShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the agencySupportRoleList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencySupportRoleShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedBy is not null
        defaultAgencySupportRoleShouldBeFound("modifiedBy.specified=true");

        // Get all the agencySupportRoleList where modifiedBy is null
        defaultAgencySupportRoleShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultAgencySupportRoleShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the agencySupportRoleList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the agencySupportRoleList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where modifiedDateTime is not null
        defaultAgencySupportRoleShouldBeFound("modifiedDateTime.specified=true");

        // Get all the agencySupportRoleList where modifiedDateTime is null
        defaultAgencySupportRoleShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where version equals to DEFAULT_VERSION
        defaultAgencySupportRoleShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleList where version equals to UPDATED_VERSION
        defaultAgencySupportRoleShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAgencySupportRoleShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the agencySupportRoleList where version equals to UPDATED_VERSION
        defaultAgencySupportRoleShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where version is not null
        defaultAgencySupportRoleShouldBeFound("version.specified=true");

        // Get all the agencySupportRoleList where version is null
        defaultAgencySupportRoleShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where version greater than or equals to DEFAULT_VERSION
        defaultAgencySupportRoleShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleList where version greater than or equals to UPDATED_VERSION
        defaultAgencySupportRoleShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where version less than or equals to DEFAULT_VERSION
        defaultAgencySupportRoleShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleList where version less than or equals to UPDATED_VERSION
        defaultAgencySupportRoleShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAgencySupportRolesByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultAgencySupportRoleShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the agencySupportRoleList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultAgencySupportRoleShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultAgencySupportRoleShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the agencySupportRoleList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultAgencySupportRoleShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        // Get all the agencySupportRoleList where contactEmail is not null
        defaultAgencySupportRoleShouldBeFound("contactEmail.specified=true");

        // Get all the agencySupportRoleList where contactEmail is null
        defaultAgencySupportRoleShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRolesByAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Agency agency = AgencyResourceIT.createEntity(em);
        em.persist(agency);
        em.flush();
        agencySupportRole.setAgency(agency);
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);
        Long agencyId = agency.getId();

        // Get all the agencySupportRoleList where agency equals to agencyId
        defaultAgencySupportRoleShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the agencySupportRoleList where agency equals to agencyId + 1
        defaultAgencySupportRoleShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }


    @Test
    @Transactional
    public void getAllAgencySupportRolesByAgencyRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRoleType agencyRoleType = ServiceRoleTypeResourceIT.createEntity(em);
        em.persist(agencyRoleType);
        em.flush();
        agencySupportRole.setAgencyRoleType(agencyRoleType);
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);
        Long agencyRoleTypeId = agencyRoleType.getId();

        // Get all the agencySupportRoleList where agencyRoleType equals to agencyRoleTypeId
        defaultAgencySupportRoleShouldBeFound("agencyRoleTypeId.equals=" + agencyRoleTypeId);

        // Get all the agencySupportRoleList where agencyRoleType equals to agencyRoleTypeId + 1
        defaultAgencySupportRoleShouldNotBeFound("agencyRoleTypeId.equals=" + (agencyRoleTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllAgencySupportRolesByAgencySupportContextTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        AgencySupportRoleContextType agencySupportContextType = AgencySupportRoleContextTypeResourceIT.createEntity(em);
        em.persist(agencySupportContextType);
        em.flush();
        agencySupportRole.setAgencySupportContextType(agencySupportContextType);
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);
        Long agencySupportContextTypeId = agencySupportContextType.getId();

        // Get all the agencySupportRoleList where agencySupportContextType equals to agencySupportContextTypeId
        defaultAgencySupportRoleShouldBeFound("agencySupportContextTypeId.equals=" + agencySupportContextTypeId);

        // Get all the agencySupportRoleList where agencySupportContextType equals to agencySupportContextTypeId + 1
        defaultAgencySupportRoleShouldNotBeFound("agencySupportContextTypeId.equals=" + (agencySupportContextTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgencySupportRoleShouldBeFound(String filter) throws Exception {
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencySupportRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)));

        // Check, that the count call also returns 1
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgencySupportRoleShouldNotBeFound(String filter) throws Exception {
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgencySupportRole() throws Exception {
        // Get the agencySupportRole
        restAgencySupportRoleMockMvc.perform(get("/api/agency-support-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgencySupportRole() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        int databaseSizeBeforeUpdate = agencySupportRoleRepository.findAll().size();

        // Update the agencySupportRole
        AgencySupportRole updatedAgencySupportRole = agencySupportRoleRepository.findById(agencySupportRole.getId()).get();
        // Disconnect from session so that the updates on updatedAgencySupportRole are not directly saved in db
        em.detach(updatedAgencySupportRole);
        updatedAgencySupportRole
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .contactEmail(UPDATED_CONTACT_EMAIL);
        AgencySupportRoleDTO agencySupportRoleDTO = agencySupportRoleMapper.toDto(updatedAgencySupportRole);

        restAgencySupportRoleMockMvc.perform(put("/api/agency-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleDTO)))
            .andExpect(status().isOk());

        // Validate the AgencySupportRole in the database
        List<AgencySupportRole> agencySupportRoleList = agencySupportRoleRepository.findAll();
        assertThat(agencySupportRoleList).hasSize(databaseSizeBeforeUpdate);
        AgencySupportRole testAgencySupportRole = agencySupportRoleList.get(agencySupportRoleList.size() - 1);
        assertThat(testAgencySupportRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAgencySupportRole.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testAgencySupportRole.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAgencySupportRole.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testAgencySupportRole.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAgencySupportRole.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAgencySupportRole() throws Exception {
        int databaseSizeBeforeUpdate = agencySupportRoleRepository.findAll().size();

        // Create the AgencySupportRole
        AgencySupportRoleDTO agencySupportRoleDTO = agencySupportRoleMapper.toDto(agencySupportRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencySupportRoleMockMvc.perform(put("/api/agency-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencySupportRole in the database
        List<AgencySupportRole> agencySupportRoleList = agencySupportRoleRepository.findAll();
        assertThat(agencySupportRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgencySupportRole() throws Exception {
        // Initialize the database
        agencySupportRoleRepository.saveAndFlush(agencySupportRole);

        int databaseSizeBeforeDelete = agencySupportRoleRepository.findAll().size();

        // Delete the agencySupportRole
        restAgencySupportRoleMockMvc.perform(delete("/api/agency-support-roles/{id}", agencySupportRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgencySupportRole> agencySupportRoleList = agencySupportRoleRepository.findAll();
        assertThat(agencySupportRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencySupportRole.class);
        AgencySupportRole agencySupportRole1 = new AgencySupportRole();
        agencySupportRole1.setId(1L);
        AgencySupportRole agencySupportRole2 = new AgencySupportRole();
        agencySupportRole2.setId(agencySupportRole1.getId());
        assertThat(agencySupportRole1).isEqualTo(agencySupportRole2);
        agencySupportRole2.setId(2L);
        assertThat(agencySupportRole1).isNotEqualTo(agencySupportRole2);
        agencySupportRole1.setId(null);
        assertThat(agencySupportRole1).isNotEqualTo(agencySupportRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencySupportRoleDTO.class);
        AgencySupportRoleDTO agencySupportRoleDTO1 = new AgencySupportRoleDTO();
        agencySupportRoleDTO1.setId(1L);
        AgencySupportRoleDTO agencySupportRoleDTO2 = new AgencySupportRoleDTO();
        assertThat(agencySupportRoleDTO1).isNotEqualTo(agencySupportRoleDTO2);
        agencySupportRoleDTO2.setId(agencySupportRoleDTO1.getId());
        assertThat(agencySupportRoleDTO1).isEqualTo(agencySupportRoleDTO2);
        agencySupportRoleDTO2.setId(2L);
        assertThat(agencySupportRoleDTO1).isNotEqualTo(agencySupportRoleDTO2);
        agencySupportRoleDTO1.setId(null);
        assertThat(agencySupportRoleDTO1).isNotEqualTo(agencySupportRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencySupportRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencySupportRoleMapper.fromId(null)).isNull();
    }
}
