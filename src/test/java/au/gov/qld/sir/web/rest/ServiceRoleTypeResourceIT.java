package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceRoleType;
import au.gov.qld.sir.domain.AgencySupportRole;
import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.repository.ServiceRoleTypeRepository;
import au.gov.qld.sir.service.ServiceRoleTypeService;
import au.gov.qld.sir.service.dto.ServiceRoleTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceRoleTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceRoleTypeCriteria;
import au.gov.qld.sir.service.ServiceRoleTypeQueryService;

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
 * Integration tests for the {@Link ServiceRoleTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceRoleTypeResourceIT {

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

    private static final String DEFAULT_SERVICE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ROLE = "BBBBBBBBBB";

    @Autowired
    private ServiceRoleTypeRepository serviceRoleTypeRepository;

    @Autowired
    private ServiceRoleTypeMapper serviceRoleTypeMapper;

    @Autowired
    private ServiceRoleTypeService serviceRoleTypeService;

    @Autowired
    private ServiceRoleTypeQueryService serviceRoleTypeQueryService;

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

    private MockMvc restServiceRoleTypeMockMvc;

    private ServiceRoleType serviceRoleType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRoleTypeResource serviceRoleTypeResource = new ServiceRoleTypeResource(serviceRoleTypeService, serviceRoleTypeQueryService);
        this.restServiceRoleTypeMockMvc = MockMvcBuilders.standaloneSetup(serviceRoleTypeResource)
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
    public static ServiceRoleType createEntity(EntityManager em) {
        ServiceRoleType serviceRoleType = new ServiceRoleType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .serviceRole(DEFAULT_SERVICE_ROLE);
        return serviceRoleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRoleType createUpdatedEntity(EntityManager em) {
        ServiceRoleType serviceRoleType = new ServiceRoleType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceRole(UPDATED_SERVICE_ROLE);
        return serviceRoleType;
    }

    @BeforeEach
    public void initTest() {
        serviceRoleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRoleType() throws Exception {
        int databaseSizeBeforeCreate = serviceRoleTypeRepository.findAll().size();

        // Create the ServiceRoleType
        ServiceRoleTypeDTO serviceRoleTypeDTO = serviceRoleTypeMapper.toDto(serviceRoleType);
        restServiceRoleTypeMockMvc.perform(post("/api/service-role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoleTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRoleType in the database
        List<ServiceRoleType> serviceRoleTypeList = serviceRoleTypeRepository.findAll();
        assertThat(serviceRoleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRoleType testServiceRoleType = serviceRoleTypeList.get(serviceRoleTypeList.size() - 1);
        assertThat(testServiceRoleType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceRoleType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceRoleType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceRoleType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceRoleType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceRoleType.getServiceRole()).isEqualTo(DEFAULT_SERVICE_ROLE);
    }

    @Test
    @Transactional
    public void createServiceRoleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRoleTypeRepository.findAll().size();

        // Create the ServiceRoleType with an existing ID
        serviceRoleType.setId(1L);
        ServiceRoleTypeDTO serviceRoleTypeDTO = serviceRoleTypeMapper.toDto(serviceRoleType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRoleTypeMockMvc.perform(post("/api/service-role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRoleType in the database
        List<ServiceRoleType> serviceRoleTypeList = serviceRoleTypeRepository.findAll();
        assertThat(serviceRoleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceRoleTypes() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRoleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceRole").value(hasItem(DEFAULT_SERVICE_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceRoleType() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get the serviceRoleType
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types/{id}", serviceRoleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRoleType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.serviceRole").value(DEFAULT_SERVICE_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceRoleTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceRoleTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRoleTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceRoleTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceRoleTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRoleTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdBy is not null
        defaultServiceRoleTypeShouldBeFound("createdBy.specified=true");

        // Get all the serviceRoleTypeList where createdBy is null
        defaultServiceRoleTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceRoleTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceRoleTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRoleTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceRoleTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceRoleTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRoleTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where createdDateTime is not null
        defaultServiceRoleTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceRoleTypeList where createdDateTime is null
        defaultServiceRoleTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceRoleTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceRoleTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRoleTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceRoleTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceRoleTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRoleTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedBy is not null
        defaultServiceRoleTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceRoleTypeList where modifiedBy is null
        defaultServiceRoleTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceRoleTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceRoleTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRoleTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceRoleTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceRoleTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRoleTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where modifiedDateTime is not null
        defaultServiceRoleTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceRoleTypeList where modifiedDateTime is null
        defaultServiceRoleTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where version equals to DEFAULT_VERSION
        defaultServiceRoleTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceRoleTypeList where version equals to UPDATED_VERSION
        defaultServiceRoleTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceRoleTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceRoleTypeList where version equals to UPDATED_VERSION
        defaultServiceRoleTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where version is not null
        defaultServiceRoleTypeShouldBeFound("version.specified=true");

        // Get all the serviceRoleTypeList where version is null
        defaultServiceRoleTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where version greater than or equals to DEFAULT_VERSION
        defaultServiceRoleTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceRoleTypeList where version greater than or equals to UPDATED_VERSION
        defaultServiceRoleTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where version less than or equals to DEFAULT_VERSION
        defaultServiceRoleTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceRoleTypeList where version less than or equals to UPDATED_VERSION
        defaultServiceRoleTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceRoleTypesByServiceRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where serviceRole equals to DEFAULT_SERVICE_ROLE
        defaultServiceRoleTypeShouldBeFound("serviceRole.equals=" + DEFAULT_SERVICE_ROLE);

        // Get all the serviceRoleTypeList where serviceRole equals to UPDATED_SERVICE_ROLE
        defaultServiceRoleTypeShouldNotBeFound("serviceRole.equals=" + UPDATED_SERVICE_ROLE);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByServiceRoleIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where serviceRole in DEFAULT_SERVICE_ROLE or UPDATED_SERVICE_ROLE
        defaultServiceRoleTypeShouldBeFound("serviceRole.in=" + DEFAULT_SERVICE_ROLE + "," + UPDATED_SERVICE_ROLE);

        // Get all the serviceRoleTypeList where serviceRole equals to UPDATED_SERVICE_ROLE
        defaultServiceRoleTypeShouldNotBeFound("serviceRole.in=" + UPDATED_SERVICE_ROLE);
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByServiceRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        // Get all the serviceRoleTypeList where serviceRole is not null
        defaultServiceRoleTypeShouldBeFound("serviceRole.specified=true");

        // Get all the serviceRoleTypeList where serviceRole is null
        defaultServiceRoleTypeShouldNotBeFound("serviceRole.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRoleTypesByAgencySupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        AgencySupportRole agencySupportRole = AgencySupportRoleResourceIT.createEntity(em);
        em.persist(agencySupportRole);
        em.flush();
        serviceRoleType.addAgencySupportRole(agencySupportRole);
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);
        Long agencySupportRoleId = agencySupportRole.getId();

        // Get all the serviceRoleTypeList where agencySupportRole equals to agencySupportRoleId
        defaultServiceRoleTypeShouldBeFound("agencySupportRoleId.equals=" + agencySupportRoleId);

        // Get all the serviceRoleTypeList where agencySupportRole equals to agencySupportRoleId + 1
        defaultServiceRoleTypeShouldNotBeFound("agencySupportRoleId.equals=" + (agencySupportRoleId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRoleTypesByServiceSupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceSupportRole serviceSupportRole = ServiceSupportRoleResourceIT.createEntity(em);
        em.persist(serviceSupportRole);
        em.flush();
        serviceRoleType.addServiceSupportRole(serviceSupportRole);
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);
        Long serviceSupportRoleId = serviceSupportRole.getId();

        // Get all the serviceRoleTypeList where serviceSupportRole equals to serviceSupportRoleId
        defaultServiceRoleTypeShouldBeFound("serviceSupportRoleId.equals=" + serviceSupportRoleId);

        // Get all the serviceRoleTypeList where serviceSupportRole equals to serviceSupportRoleId + 1
        defaultServiceRoleTypeShouldNotBeFound("serviceSupportRoleId.equals=" + (serviceSupportRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceRoleTypeShouldBeFound(String filter) throws Exception {
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRoleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceRole").value(hasItem(DEFAULT_SERVICE_ROLE)));

        // Check, that the count call also returns 1
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceRoleTypeShouldNotBeFound(String filter) throws Exception {
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceRoleType() throws Exception {
        // Get the serviceRoleType
        restServiceRoleTypeMockMvc.perform(get("/api/service-role-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRoleType() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        int databaseSizeBeforeUpdate = serviceRoleTypeRepository.findAll().size();

        // Update the serviceRoleType
        ServiceRoleType updatedServiceRoleType = serviceRoleTypeRepository.findById(serviceRoleType.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRoleType are not directly saved in db
        em.detach(updatedServiceRoleType);
        updatedServiceRoleType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceRole(UPDATED_SERVICE_ROLE);
        ServiceRoleTypeDTO serviceRoleTypeDTO = serviceRoleTypeMapper.toDto(updatedServiceRoleType);

        restServiceRoleTypeMockMvc.perform(put("/api/service-role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoleTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRoleType in the database
        List<ServiceRoleType> serviceRoleTypeList = serviceRoleTypeRepository.findAll();
        assertThat(serviceRoleTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceRoleType testServiceRoleType = serviceRoleTypeList.get(serviceRoleTypeList.size() - 1);
        assertThat(testServiceRoleType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceRoleType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceRoleType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceRoleType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceRoleType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceRoleType.getServiceRole()).isEqualTo(UPDATED_SERVICE_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRoleType() throws Exception {
        int databaseSizeBeforeUpdate = serviceRoleTypeRepository.findAll().size();

        // Create the ServiceRoleType
        ServiceRoleTypeDTO serviceRoleTypeDTO = serviceRoleTypeMapper.toDto(serviceRoleType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRoleTypeMockMvc.perform(put("/api/service-role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRoleType in the database
        List<ServiceRoleType> serviceRoleTypeList = serviceRoleTypeRepository.findAll();
        assertThat(serviceRoleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceRoleType() throws Exception {
        // Initialize the database
        serviceRoleTypeRepository.saveAndFlush(serviceRoleType);

        int databaseSizeBeforeDelete = serviceRoleTypeRepository.findAll().size();

        // Delete the serviceRoleType
        restServiceRoleTypeMockMvc.perform(delete("/api/service-role-types/{id}", serviceRoleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRoleType> serviceRoleTypeList = serviceRoleTypeRepository.findAll();
        assertThat(serviceRoleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRoleType.class);
        ServiceRoleType serviceRoleType1 = new ServiceRoleType();
        serviceRoleType1.setId(1L);
        ServiceRoleType serviceRoleType2 = new ServiceRoleType();
        serviceRoleType2.setId(serviceRoleType1.getId());
        assertThat(serviceRoleType1).isEqualTo(serviceRoleType2);
        serviceRoleType2.setId(2L);
        assertThat(serviceRoleType1).isNotEqualTo(serviceRoleType2);
        serviceRoleType1.setId(null);
        assertThat(serviceRoleType1).isNotEqualTo(serviceRoleType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRoleTypeDTO.class);
        ServiceRoleTypeDTO serviceRoleTypeDTO1 = new ServiceRoleTypeDTO();
        serviceRoleTypeDTO1.setId(1L);
        ServiceRoleTypeDTO serviceRoleTypeDTO2 = new ServiceRoleTypeDTO();
        assertThat(serviceRoleTypeDTO1).isNotEqualTo(serviceRoleTypeDTO2);
        serviceRoleTypeDTO2.setId(serviceRoleTypeDTO1.getId());
        assertThat(serviceRoleTypeDTO1).isEqualTo(serviceRoleTypeDTO2);
        serviceRoleTypeDTO2.setId(2L);
        assertThat(serviceRoleTypeDTO1).isNotEqualTo(serviceRoleTypeDTO2);
        serviceRoleTypeDTO1.setId(null);
        assertThat(serviceRoleTypeDTO1).isNotEqualTo(serviceRoleTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRoleTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRoleTypeMapper.fromId(null)).isNull();
    }
}
