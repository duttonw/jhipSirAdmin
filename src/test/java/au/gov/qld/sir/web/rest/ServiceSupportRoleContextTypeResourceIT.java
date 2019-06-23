package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceSupportRoleContextType;
import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.repository.ServiceSupportRoleContextTypeRepository;
import au.gov.qld.sir.service.ServiceSupportRoleContextTypeService;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleContextTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceSupportRoleContextTypeCriteria;
import au.gov.qld.sir.service.ServiceSupportRoleContextTypeQueryService;

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
 * Integration tests for the {@Link ServiceSupportRoleContextTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceSupportRoleContextTypeResourceIT {

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
    private ServiceSupportRoleContextTypeRepository serviceSupportRoleContextTypeRepository;

    @Autowired
    private ServiceSupportRoleContextTypeMapper serviceSupportRoleContextTypeMapper;

    @Autowired
    private ServiceSupportRoleContextTypeService serviceSupportRoleContextTypeService;

    @Autowired
    private ServiceSupportRoleContextTypeQueryService serviceSupportRoleContextTypeQueryService;

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

    private MockMvc restServiceSupportRoleContextTypeMockMvc;

    private ServiceSupportRoleContextType serviceSupportRoleContextType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceSupportRoleContextTypeResource serviceSupportRoleContextTypeResource = new ServiceSupportRoleContextTypeResource(serviceSupportRoleContextTypeService, serviceSupportRoleContextTypeQueryService);
        this.restServiceSupportRoleContextTypeMockMvc = MockMvcBuilders.standaloneSetup(serviceSupportRoleContextTypeResource)
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
    public static ServiceSupportRoleContextType createEntity(EntityManager em) {
        ServiceSupportRoleContextType serviceSupportRoleContextType = new ServiceSupportRoleContextType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .context(DEFAULT_CONTEXT);
        return serviceSupportRoleContextType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSupportRoleContextType createUpdatedEntity(EntityManager em) {
        ServiceSupportRoleContextType serviceSupportRoleContextType = new ServiceSupportRoleContextType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT);
        return serviceSupportRoleContextType;
    }

    @BeforeEach
    public void initTest() {
        serviceSupportRoleContextType = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceSupportRoleContextType() throws Exception {
        int databaseSizeBeforeCreate = serviceSupportRoleContextTypeRepository.findAll().size();

        // Create the ServiceSupportRoleContextType
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextType);
        restServiceSupportRoleContextTypeMockMvc.perform(post("/api/service-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleContextTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceSupportRoleContextType in the database
        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSupportRoleContextType testServiceSupportRoleContextType = serviceSupportRoleContextTypeList.get(serviceSupportRoleContextTypeList.size() - 1);
        assertThat(testServiceSupportRoleContextType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceSupportRoleContextType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceSupportRoleContextType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceSupportRoleContextType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceSupportRoleContextType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceSupportRoleContextType.getContext()).isEqualTo(DEFAULT_CONTEXT);
    }

    @Test
    @Transactional
    public void createServiceSupportRoleContextTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSupportRoleContextTypeRepository.findAll().size();

        // Create the ServiceSupportRoleContextType with an existing ID
        serviceSupportRoleContextType.setId(1L);
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSupportRoleContextTypeMockMvc.perform(post("/api/service-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupportRoleContextType in the database
        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContextIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceSupportRoleContextTypeRepository.findAll().size();
        // set the field null
        serviceSupportRoleContextType.setContext(null);

        // Create the ServiceSupportRoleContextType, which fails.
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextType);

        restServiceSupportRoleContextTypeMockMvc.perform(post("/api/service-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypes() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSupportRoleContextType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceSupportRoleContextType() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get the serviceSupportRoleContextType
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types/{id}", serviceSupportRoleContextType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSupportRoleContextType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceSupportRoleContextTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceSupportRoleContextTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceSupportRoleContextTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceSupportRoleContextTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdBy is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("createdBy.specified=true");

        // Get all the serviceSupportRoleContextTypeList where createdBy is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceSupportRoleContextTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceSupportRoleContextTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where createdDateTime is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceSupportRoleContextTypeList where createdDateTime is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceSupportRoleContextTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceSupportRoleContextTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedBy is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceSupportRoleContextTypeList where modifiedBy is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceSupportRoleContextTypeList where modifiedDateTime is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where version equals to DEFAULT_VERSION
        defaultServiceSupportRoleContextTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleContextTypeList where version equals to UPDATED_VERSION
        defaultServiceSupportRoleContextTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceSupportRoleContextTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceSupportRoleContextTypeList where version equals to UPDATED_VERSION
        defaultServiceSupportRoleContextTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where version is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("version.specified=true");

        // Get all the serviceSupportRoleContextTypeList where version is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where version greater than or equals to DEFAULT_VERSION
        defaultServiceSupportRoleContextTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleContextTypeList where version greater than or equals to UPDATED_VERSION
        defaultServiceSupportRoleContextTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where version less than or equals to DEFAULT_VERSION
        defaultServiceSupportRoleContextTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleContextTypeList where version less than or equals to UPDATED_VERSION
        defaultServiceSupportRoleContextTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where context equals to DEFAULT_CONTEXT
        defaultServiceSupportRoleContextTypeShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the serviceSupportRoleContextTypeList where context equals to UPDATED_CONTEXT
        defaultServiceSupportRoleContextTypeShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByContextIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultServiceSupportRoleContextTypeShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the serviceSupportRoleContextTypeList where context equals to UPDATED_CONTEXT
        defaultServiceSupportRoleContextTypeShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        // Get all the serviceSupportRoleContextTypeList where context is not null
        defaultServiceSupportRoleContextTypeShouldBeFound("context.specified=true");

        // Get all the serviceSupportRoleContextTypeList where context is null
        defaultServiceSupportRoleContextTypeShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRoleContextTypesByServiceSupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceSupportRole serviceSupportRole = ServiceSupportRoleResourceIT.createEntity(em);
        em.persist(serviceSupportRole);
        em.flush();
        serviceSupportRoleContextType.addServiceSupportRole(serviceSupportRole);
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);
        Long serviceSupportRoleId = serviceSupportRole.getId();

        // Get all the serviceSupportRoleContextTypeList where serviceSupportRole equals to serviceSupportRoleId
        defaultServiceSupportRoleContextTypeShouldBeFound("serviceSupportRoleId.equals=" + serviceSupportRoleId);

        // Get all the serviceSupportRoleContextTypeList where serviceSupportRole equals to serviceSupportRoleId + 1
        defaultServiceSupportRoleContextTypeShouldNotBeFound("serviceSupportRoleId.equals=" + (serviceSupportRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceSupportRoleContextTypeShouldBeFound(String filter) throws Exception {
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSupportRoleContextType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)));

        // Check, that the count call also returns 1
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceSupportRoleContextTypeShouldNotBeFound(String filter) throws Exception {
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceSupportRoleContextType() throws Exception {
        // Get the serviceSupportRoleContextType
        restServiceSupportRoleContextTypeMockMvc.perform(get("/api/service-support-role-context-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceSupportRoleContextType() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        int databaseSizeBeforeUpdate = serviceSupportRoleContextTypeRepository.findAll().size();

        // Update the serviceSupportRoleContextType
        ServiceSupportRoleContextType updatedServiceSupportRoleContextType = serviceSupportRoleContextTypeRepository.findById(serviceSupportRoleContextType.getId()).get();
        // Disconnect from session so that the updates on updatedServiceSupportRoleContextType are not directly saved in db
        em.detach(updatedServiceSupportRoleContextType);
        updatedServiceSupportRoleContextType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .context(UPDATED_CONTEXT);
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeMapper.toDto(updatedServiceSupportRoleContextType);

        restServiceSupportRoleContextTypeMockMvc.perform(put("/api/service-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleContextTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceSupportRoleContextType in the database
        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceSupportRoleContextType testServiceSupportRoleContextType = serviceSupportRoleContextTypeList.get(serviceSupportRoleContextTypeList.size() - 1);
        assertThat(testServiceSupportRoleContextType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceSupportRoleContextType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceSupportRoleContextType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceSupportRoleContextType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceSupportRoleContextType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSupportRoleContextType.getContext()).isEqualTo(UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceSupportRoleContextType() throws Exception {
        int databaseSizeBeforeUpdate = serviceSupportRoleContextTypeRepository.findAll().size();

        // Create the ServiceSupportRoleContextType
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO = serviceSupportRoleContextTypeMapper.toDto(serviceSupportRoleContextType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceSupportRoleContextTypeMockMvc.perform(put("/api/service-support-role-context-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleContextTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupportRoleContextType in the database
        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceSupportRoleContextType() throws Exception {
        // Initialize the database
        serviceSupportRoleContextTypeRepository.saveAndFlush(serviceSupportRoleContextType);

        int databaseSizeBeforeDelete = serviceSupportRoleContextTypeRepository.findAll().size();

        // Delete the serviceSupportRoleContextType
        restServiceSupportRoleContextTypeMockMvc.perform(delete("/api/service-support-role-context-types/{id}", serviceSupportRoleContextType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceSupportRoleContextType> serviceSupportRoleContextTypeList = serviceSupportRoleContextTypeRepository.findAll();
        assertThat(serviceSupportRoleContextTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSupportRoleContextType.class);
        ServiceSupportRoleContextType serviceSupportRoleContextType1 = new ServiceSupportRoleContextType();
        serviceSupportRoleContextType1.setId(1L);
        ServiceSupportRoleContextType serviceSupportRoleContextType2 = new ServiceSupportRoleContextType();
        serviceSupportRoleContextType2.setId(serviceSupportRoleContextType1.getId());
        assertThat(serviceSupportRoleContextType1).isEqualTo(serviceSupportRoleContextType2);
        serviceSupportRoleContextType2.setId(2L);
        assertThat(serviceSupportRoleContextType1).isNotEqualTo(serviceSupportRoleContextType2);
        serviceSupportRoleContextType1.setId(null);
        assertThat(serviceSupportRoleContextType1).isNotEqualTo(serviceSupportRoleContextType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSupportRoleContextTypeDTO.class);
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO1 = new ServiceSupportRoleContextTypeDTO();
        serviceSupportRoleContextTypeDTO1.setId(1L);
        ServiceSupportRoleContextTypeDTO serviceSupportRoleContextTypeDTO2 = new ServiceSupportRoleContextTypeDTO();
        assertThat(serviceSupportRoleContextTypeDTO1).isNotEqualTo(serviceSupportRoleContextTypeDTO2);
        serviceSupportRoleContextTypeDTO2.setId(serviceSupportRoleContextTypeDTO1.getId());
        assertThat(serviceSupportRoleContextTypeDTO1).isEqualTo(serviceSupportRoleContextTypeDTO2);
        serviceSupportRoleContextTypeDTO2.setId(2L);
        assertThat(serviceSupportRoleContextTypeDTO1).isNotEqualTo(serviceSupportRoleContextTypeDTO2);
        serviceSupportRoleContextTypeDTO1.setId(null);
        assertThat(serviceSupportRoleContextTypeDTO1).isNotEqualTo(serviceSupportRoleContextTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceSupportRoleContextTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceSupportRoleContextTypeMapper.fromId(null)).isNull();
    }
}
