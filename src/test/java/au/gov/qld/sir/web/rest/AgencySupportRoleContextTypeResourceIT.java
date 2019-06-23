package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.AgencySupportRoleContextType;
import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.repository.AgencySupportRoleContextTypeRepository;
import au.gov.qld.sir.service.AgencySupportRoleContextTypeService;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.AgencySupportRoleContextTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.AgencySupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.AgencySupportRoleContextTypeQueryService;

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
 * Integration tests for the {@Link AgencySupportRoleContextTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class AgencySupportRoleContextTypeResourceIT {

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

    @Autowired
    private AgencySupportRoleContextTypeRepository agencySupportRoleContextTypeRepository;

    @Autowired
    private AgencySupportRoleContextTypeMapper agencySupportRoleContextTypeMapper;

    @Autowired
    private AgencySupportRoleContextTypeService agencySupportRoleContextTypeService;

    @Autowired
    private AgencySupportRoleContextTypeQueryService agencySupportRoleContextTypeQueryService;

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

    private MockMvc restAgencySupportRoleContextTypeMockMvc;

    private AgencySupportRoleContextType agencySupportRoleContextType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencySupportRoleContextTypeResource agencySupportRoleContextTypeResource = new AgencySupportRoleContextTypeResource(agencySupportRoleContextTypeService, agencySupportRoleContextTypeQueryService);
        this.restAgencySupportRoleContextTypeMockMvc = MockMvcBuilders.standaloneSetup(agencySupportRoleContextTypeResource)
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
    public static AgencySupportRoleContextType createEntity(EntityManager em) {
        AgencySupportRoleContextType agencySupportRoleContextType = new AgencySupportRoleContextType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .context(DEFAULT_CONTEXT);
        return agencySupportRoleContextType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgencySupportRoleContextType createUpdatedEntity(EntityManager em) {
        AgencySupportRoleContextType agencySupportRoleContextType = new AgencySupportRoleContextType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT);
        return agencySupportRoleContextType;
    }

    @BeforeEach
    public void initTest() {
        agencySupportRoleContextType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgencySupportRoleContextType() throws Exception {
        int databaseSizeBeforeCreate = agencySupportRoleContextTypeRepository.findAll().size();

        // Create the AgencySupportRoleContextType
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextType);
        restAgencySupportRoleContextTypeMockMvc.perform(post("/api/agency-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleContextTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AgencySupportRoleContextType in the database
        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AgencySupportRoleContextType testAgencySupportRoleContextType = agencySupportRoleContextTypeList.get(agencySupportRoleContextTypeList.size() - 1);
        assertThat(testAgencySupportRoleContextType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAgencySupportRoleContextType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testAgencySupportRoleContextType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAgencySupportRoleContextType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testAgencySupportRoleContextType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAgencySupportRoleContextType.getContext()).isEqualTo(DEFAULT_CONTEXT);
    }

    @Test
    @Transactional
    public void createAgencySupportRoleContextTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencySupportRoleContextTypeRepository.findAll().size();

        // Create the AgencySupportRoleContextType with an existing ID
        agencySupportRoleContextType.setId(1L);
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencySupportRoleContextTypeMockMvc.perform(post("/api/agency-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencySupportRoleContextType in the database
        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContextIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencySupportRoleContextTypeRepository.findAll().size();
        // set the field null
        agencySupportRoleContextType.setContext(null);

        // Create the AgencySupportRoleContextType, which fails.
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextType);

        restAgencySupportRoleContextTypeMockMvc.perform(post("/api/agency-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypes() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencySupportRoleContextType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getAgencySupportRoleContextType() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get the agencySupportRoleContextType
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types/{id}", agencySupportRoleContextType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agencySupportRoleContextType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()));
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultAgencySupportRoleContextTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the agencySupportRoleContextTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAgencySupportRoleContextTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the agencySupportRoleContextTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdBy is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("createdBy.specified=true");

        // Get all the agencySupportRoleContextTypeList where createdBy is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the agencySupportRoleContextTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the agencySupportRoleContextTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where createdDateTime is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the agencySupportRoleContextTypeList where createdDateTime is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the agencySupportRoleContextTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the agencySupportRoleContextTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedBy is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the agencySupportRoleContextTypeList where modifiedBy is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the agencySupportRoleContextTypeList where modifiedDateTime is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where version equals to DEFAULT_VERSION
        defaultAgencySupportRoleContextTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleContextTypeList where version equals to UPDATED_VERSION
        defaultAgencySupportRoleContextTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAgencySupportRoleContextTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the agencySupportRoleContextTypeList where version equals to UPDATED_VERSION
        defaultAgencySupportRoleContextTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where version is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("version.specified=true");

        // Get all the agencySupportRoleContextTypeList where version is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where version greater than or equals to DEFAULT_VERSION
        defaultAgencySupportRoleContextTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleContextTypeList where version greater than or equals to UPDATED_VERSION
        defaultAgencySupportRoleContextTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where version less than or equals to DEFAULT_VERSION
        defaultAgencySupportRoleContextTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the agencySupportRoleContextTypeList where version less than or equals to UPDATED_VERSION
        defaultAgencySupportRoleContextTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where context equals to DEFAULT_CONTEXT
        defaultAgencySupportRoleContextTypeShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the agencySupportRoleContextTypeList where context equals to UPDATED_CONTEXT
        defaultAgencySupportRoleContextTypeShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByContextIsInShouldWork() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultAgencySupportRoleContextTypeShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the agencySupportRoleContextTypeList where context equals to UPDATED_CONTEXT
        defaultAgencySupportRoleContextTypeShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        // Get all the agencySupportRoleContextTypeList where context is not null
        defaultAgencySupportRoleContextTypeShouldBeFound("context.specified=true");

        // Get all the agencySupportRoleContextTypeList where context is null
        defaultAgencySupportRoleContextTypeShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencySupportRoleContextTypesByAgencySupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        AgencySupportRole agencySupportRole = AgencySupportRoleResourceIT.createEntity(em);
        em.persist(agencySupportRole);
        em.flush();
        agencySupportRoleContextType.addAgencySupportRole(agencySupportRole);
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);
        Long agencySupportRoleId = agencySupportRole.getId();

        // Get all the agencySupportRoleContextTypeList where agencySupportRole equals to agencySupportRoleId
        defaultAgencySupportRoleContextTypeShouldBeFound("agencySupportRoleId.equals=" + agencySupportRoleId);

        // Get all the agencySupportRoleContextTypeList where agencySupportRole equals to agencySupportRoleId + 1
        defaultAgencySupportRoleContextTypeShouldNotBeFound("agencySupportRoleId.equals=" + (agencySupportRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgencySupportRoleContextTypeShouldBeFound(String filter) throws Exception {
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencySupportRoleContextType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)));

        // Check, that the count call also returns 1
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgencySupportRoleContextTypeShouldNotBeFound(String filter) throws Exception {
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgencySupportRoleContextType() throws Exception {
        // Get the agencySupportRoleContextType
        restAgencySupportRoleContextTypeMockMvc.perform(get("/api/agency-support-role-context-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgencySupportRoleContextType() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        int databaseSizeBeforeUpdate = agencySupportRoleContextTypeRepository.findAll().size();

        // Update the agencySupportRoleContextType
        AgencySupportRoleContextType updatedAgencySupportRoleContextType = agencySupportRoleContextTypeRepository.findById(agencySupportRoleContextType.getId()).get();
        // Disconnect from session so that the updates on updatedAgencySupportRoleContextType are not directly saved in db
        em.detach(updatedAgencySupportRoleContextType);
        updatedAgencySupportRoleContextType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT);
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeMapper.toDto(updatedAgencySupportRoleContextType);

        restAgencySupportRoleContextTypeMockMvc.perform(put("/api/agency-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleContextTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AgencySupportRoleContextType in the database
        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeUpdate);
        AgencySupportRoleContextType testAgencySupportRoleContextType = agencySupportRoleContextTypeList.get(agencySupportRoleContextTypeList.size() - 1);
        assertThat(testAgencySupportRoleContextType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAgencySupportRoleContextType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testAgencySupportRoleContextType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAgencySupportRoleContextType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testAgencySupportRoleContextType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAgencySupportRoleContextType.getContext()).isEqualTo(UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingAgencySupportRoleContextType() throws Exception {
        int databaseSizeBeforeUpdate = agencySupportRoleContextTypeRepository.findAll().size();

        // Create the AgencySupportRoleContextType
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO = agencySupportRoleContextTypeMapper.toDto(agencySupportRoleContextType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencySupportRoleContextTypeMockMvc.perform(put("/api/agency-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencySupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencySupportRoleContextType in the database
        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgencySupportRoleContextType() throws Exception {
        // Initialize the database
        agencySupportRoleContextTypeRepository.saveAndFlush(agencySupportRoleContextType);

        int databaseSizeBeforeDelete = agencySupportRoleContextTypeRepository.findAll().size();

        // Delete the agencySupportRoleContextType
        restAgencySupportRoleContextTypeMockMvc.perform(delete("/api/agency-support-role-context-types/{id}", agencySupportRoleContextType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgencySupportRoleContextType> agencySupportRoleContextTypeList = agencySupportRoleContextTypeRepository.findAll();
        assertThat(agencySupportRoleContextTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencySupportRoleContextType.class);
        AgencySupportRoleContextType agencySupportRoleContextType1 = new AgencySupportRoleContextType();
        agencySupportRoleContextType1.setId(1L);
        AgencySupportRoleContextType agencySupportRoleContextType2 = new AgencySupportRoleContextType();
        agencySupportRoleContextType2.setId(agencySupportRoleContextType1.getId());
        assertThat(agencySupportRoleContextType1).isEqualTo(agencySupportRoleContextType2);
        agencySupportRoleContextType2.setId(2L);
        assertThat(agencySupportRoleContextType1).isNotEqualTo(agencySupportRoleContextType2);
        agencySupportRoleContextType1.setId(null);
        assertThat(agencySupportRoleContextType1).isNotEqualTo(agencySupportRoleContextType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencySupportRoleContextTypeDTO.class);
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO1 = new AgencySupportRoleContextTypeDTO();
        agencySupportRoleContextTypeDTO1.setId(1L);
        AgencySupportRoleContextTypeDTO agencySupportRoleContextTypeDTO2 = new AgencySupportRoleContextTypeDTO();
        assertThat(agencySupportRoleContextTypeDTO1).isNotEqualTo(agencySupportRoleContextTypeDTO2);
        agencySupportRoleContextTypeDTO2.setId(agencySupportRoleContextTypeDTO1.getId());
        assertThat(agencySupportRoleContextTypeDTO1).isEqualTo(agencySupportRoleContextTypeDTO2);
        agencySupportRoleContextTypeDTO2.setId(2L);
        assertThat(agencySupportRoleContextTypeDTO1).isNotEqualTo(agencySupportRoleContextTypeDTO2);
        agencySupportRoleContextTypeDTO1.setId(null);
        assertThat(agencySupportRoleContextTypeDTO1).isNotEqualTo(agencySupportRoleContextTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencySupportRoleContextTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencySupportRoleContextTypeMapper.fromId(null)).isNull();
    }
}
