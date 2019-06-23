package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceFranchise;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceFranchiseRepository;
import au.gov.qld.sir.service.ServiceFranchiseService;
import au.gov.qld.sir.service.dto.ServiceFranchiseDTO;
import au.gov.qld.sir.service.mapper.ServiceFranchiseMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceFranchiseCriteria;
import au.gov.qld.sir.service.ServiceFranchiseQueryService;

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
 * Integration tests for the {@Link ServiceFranchiseResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceFranchiseResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FRANCHISE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FRANCHISE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private ServiceFranchiseRepository serviceFranchiseRepository;

    @Autowired
    private ServiceFranchiseMapper serviceFranchiseMapper;

    @Autowired
    private ServiceFranchiseService serviceFranchiseService;

    @Autowired
    private ServiceFranchiseQueryService serviceFranchiseQueryService;

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

    private MockMvc restServiceFranchiseMockMvc;

    private ServiceFranchise serviceFranchise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceFranchiseResource serviceFranchiseResource = new ServiceFranchiseResource(serviceFranchiseService, serviceFranchiseQueryService);
        this.restServiceFranchiseMockMvc = MockMvcBuilders.standaloneSetup(serviceFranchiseResource)
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
    public static ServiceFranchise createEntity(EntityManager em) {
        ServiceFranchise serviceFranchise = new ServiceFranchise()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .franchiseName(DEFAULT_FRANCHISE_NAME)
            .version(DEFAULT_VERSION);
        return serviceFranchise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceFranchise createUpdatedEntity(EntityManager em) {
        ServiceFranchise serviceFranchise = new ServiceFranchise()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .version(UPDATED_VERSION);
        return serviceFranchise;
    }

    @BeforeEach
    public void initTest() {
        serviceFranchise = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceFranchise() throws Exception {
        int databaseSizeBeforeCreate = serviceFranchiseRepository.findAll().size();

        // Create the ServiceFranchise
        ServiceFranchiseDTO serviceFranchiseDTO = serviceFranchiseMapper.toDto(serviceFranchise);
        restServiceFranchiseMockMvc.perform(post("/api/service-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceFranchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceFranchise in the database
        List<ServiceFranchise> serviceFranchiseList = serviceFranchiseRepository.findAll();
        assertThat(serviceFranchiseList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceFranchise testServiceFranchise = serviceFranchiseList.get(serviceFranchiseList.size() - 1);
        assertThat(testServiceFranchise.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceFranchise.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceFranchise.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceFranchise.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceFranchise.getFranchiseName()).isEqualTo(DEFAULT_FRANCHISE_NAME);
        assertThat(testServiceFranchise.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createServiceFranchiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceFranchiseRepository.findAll().size();

        // Create the ServiceFranchise with an existing ID
        serviceFranchise.setId(1L);
        ServiceFranchiseDTO serviceFranchiseDTO = serviceFranchiseMapper.toDto(serviceFranchise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceFranchiseMockMvc.perform(post("/api/service-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceFranchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceFranchise in the database
        List<ServiceFranchise> serviceFranchiseList = serviceFranchiseRepository.findAll();
        assertThat(serviceFranchiseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceFranchises() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceFranchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].franchiseName").value(hasItem(DEFAULT_FRANCHISE_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceFranchise() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get the serviceFranchise
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises/{id}", serviceFranchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceFranchise.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.franchiseName").value(DEFAULT_FRANCHISE_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceFranchiseShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceFranchiseList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceFranchiseShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceFranchiseShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceFranchiseList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceFranchiseShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdBy is not null
        defaultServiceFranchiseShouldBeFound("createdBy.specified=true");

        // Get all the serviceFranchiseList where createdBy is null
        defaultServiceFranchiseShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceFranchiseShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceFranchiseList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceFranchiseShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceFranchiseShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceFranchiseList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceFranchiseShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where createdDateTime is not null
        defaultServiceFranchiseShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceFranchiseList where createdDateTime is null
        defaultServiceFranchiseShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceFranchiseShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceFranchiseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceFranchiseShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceFranchiseShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceFranchiseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceFranchiseShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedBy is not null
        defaultServiceFranchiseShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceFranchiseList where modifiedBy is null
        defaultServiceFranchiseShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceFranchiseShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceFranchiseList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceFranchiseShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceFranchiseShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceFranchiseList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceFranchiseShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where modifiedDateTime is not null
        defaultServiceFranchiseShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceFranchiseList where modifiedDateTime is null
        defaultServiceFranchiseShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByFranchiseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where franchiseName equals to DEFAULT_FRANCHISE_NAME
        defaultServiceFranchiseShouldBeFound("franchiseName.equals=" + DEFAULT_FRANCHISE_NAME);

        // Get all the serviceFranchiseList where franchiseName equals to UPDATED_FRANCHISE_NAME
        defaultServiceFranchiseShouldNotBeFound("franchiseName.equals=" + UPDATED_FRANCHISE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByFranchiseNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where franchiseName in DEFAULT_FRANCHISE_NAME or UPDATED_FRANCHISE_NAME
        defaultServiceFranchiseShouldBeFound("franchiseName.in=" + DEFAULT_FRANCHISE_NAME + "," + UPDATED_FRANCHISE_NAME);

        // Get all the serviceFranchiseList where franchiseName equals to UPDATED_FRANCHISE_NAME
        defaultServiceFranchiseShouldNotBeFound("franchiseName.in=" + UPDATED_FRANCHISE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByFranchiseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where franchiseName is not null
        defaultServiceFranchiseShouldBeFound("franchiseName.specified=true");

        // Get all the serviceFranchiseList where franchiseName is null
        defaultServiceFranchiseShouldNotBeFound("franchiseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where version equals to DEFAULT_VERSION
        defaultServiceFranchiseShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceFranchiseList where version equals to UPDATED_VERSION
        defaultServiceFranchiseShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceFranchiseShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceFranchiseList where version equals to UPDATED_VERSION
        defaultServiceFranchiseShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where version is not null
        defaultServiceFranchiseShouldBeFound("version.specified=true");

        // Get all the serviceFranchiseList where version is null
        defaultServiceFranchiseShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where version greater than or equals to DEFAULT_VERSION
        defaultServiceFranchiseShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceFranchiseList where version greater than or equals to UPDATED_VERSION
        defaultServiceFranchiseShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceFranchisesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        // Get all the serviceFranchiseList where version less than or equals to DEFAULT_VERSION
        defaultServiceFranchiseShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceFranchiseList where version less than or equals to UPDATED_VERSION
        defaultServiceFranchiseShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceFranchisesByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceFranchise.addServiceRecord(serviceRecord);
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceFranchiseList where serviceRecord equals to serviceRecordId
        defaultServiceFranchiseShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceFranchiseList where serviceRecord equals to serviceRecordId + 1
        defaultServiceFranchiseShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceFranchiseShouldBeFound(String filter) throws Exception {
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceFranchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].franchiseName").value(hasItem(DEFAULT_FRANCHISE_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));

        // Check, that the count call also returns 1
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceFranchiseShouldNotBeFound(String filter) throws Exception {
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceFranchise() throws Exception {
        // Get the serviceFranchise
        restServiceFranchiseMockMvc.perform(get("/api/service-franchises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceFranchise() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        int databaseSizeBeforeUpdate = serviceFranchiseRepository.findAll().size();

        // Update the serviceFranchise
        ServiceFranchise updatedServiceFranchise = serviceFranchiseRepository.findById(serviceFranchise.getId()).get();
        // Disconnect from session so that the updates on updatedServiceFranchise are not directly saved in db
        em.detach(updatedServiceFranchise);
        updatedServiceFranchise
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .version(UPDATED_VERSION);
        ServiceFranchiseDTO serviceFranchiseDTO = serviceFranchiseMapper.toDto(updatedServiceFranchise);

        restServiceFranchiseMockMvc.perform(put("/api/service-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceFranchiseDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceFranchise in the database
        List<ServiceFranchise> serviceFranchiseList = serviceFranchiseRepository.findAll();
        assertThat(serviceFranchiseList).hasSize(databaseSizeBeforeUpdate);
        ServiceFranchise testServiceFranchise = serviceFranchiseList.get(serviceFranchiseList.size() - 1);
        assertThat(testServiceFranchise.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceFranchise.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceFranchise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceFranchise.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceFranchise.getFranchiseName()).isEqualTo(UPDATED_FRANCHISE_NAME);
        assertThat(testServiceFranchise.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceFranchise() throws Exception {
        int databaseSizeBeforeUpdate = serviceFranchiseRepository.findAll().size();

        // Create the ServiceFranchise
        ServiceFranchiseDTO serviceFranchiseDTO = serviceFranchiseMapper.toDto(serviceFranchise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceFranchiseMockMvc.perform(put("/api/service-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceFranchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceFranchise in the database
        List<ServiceFranchise> serviceFranchiseList = serviceFranchiseRepository.findAll();
        assertThat(serviceFranchiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceFranchise() throws Exception {
        // Initialize the database
        serviceFranchiseRepository.saveAndFlush(serviceFranchise);

        int databaseSizeBeforeDelete = serviceFranchiseRepository.findAll().size();

        // Delete the serviceFranchise
        restServiceFranchiseMockMvc.perform(delete("/api/service-franchises/{id}", serviceFranchise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceFranchise> serviceFranchiseList = serviceFranchiseRepository.findAll();
        assertThat(serviceFranchiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceFranchise.class);
        ServiceFranchise serviceFranchise1 = new ServiceFranchise();
        serviceFranchise1.setId(1L);
        ServiceFranchise serviceFranchise2 = new ServiceFranchise();
        serviceFranchise2.setId(serviceFranchise1.getId());
        assertThat(serviceFranchise1).isEqualTo(serviceFranchise2);
        serviceFranchise2.setId(2L);
        assertThat(serviceFranchise1).isNotEqualTo(serviceFranchise2);
        serviceFranchise1.setId(null);
        assertThat(serviceFranchise1).isNotEqualTo(serviceFranchise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceFranchiseDTO.class);
        ServiceFranchiseDTO serviceFranchiseDTO1 = new ServiceFranchiseDTO();
        serviceFranchiseDTO1.setId(1L);
        ServiceFranchiseDTO serviceFranchiseDTO2 = new ServiceFranchiseDTO();
        assertThat(serviceFranchiseDTO1).isNotEqualTo(serviceFranchiseDTO2);
        serviceFranchiseDTO2.setId(serviceFranchiseDTO1.getId());
        assertThat(serviceFranchiseDTO1).isEqualTo(serviceFranchiseDTO2);
        serviceFranchiseDTO2.setId(2L);
        assertThat(serviceFranchiseDTO1).isNotEqualTo(serviceFranchiseDTO2);
        serviceFranchiseDTO1.setId(null);
        assertThat(serviceFranchiseDTO1).isNotEqualTo(serviceFranchiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceFranchiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceFranchiseMapper.fromId(null)).isNull();
    }
}
