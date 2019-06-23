package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceRelationship;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceRelationshipRepository;
import au.gov.qld.sir.service.ServiceRelationshipService;
import au.gov.qld.sir.service.dto.ServiceRelationshipDTO;
import au.gov.qld.sir.service.mapper.ServiceRelationshipMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceRelationshipCriteria;
import au.gov.qld.sir.service.ServiceRelationshipQueryService;

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
 * Integration tests for the {@Link ServiceRelationshipResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceRelationshipResourceIT {

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

    private static final String DEFAULT_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBBBBBBB";

    @Autowired
    private ServiceRelationshipRepository serviceRelationshipRepository;

    @Autowired
    private ServiceRelationshipMapper serviceRelationshipMapper;

    @Autowired
    private ServiceRelationshipService serviceRelationshipService;

    @Autowired
    private ServiceRelationshipQueryService serviceRelationshipQueryService;

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

    private MockMvc restServiceRelationshipMockMvc;

    private ServiceRelationship serviceRelationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRelationshipResource serviceRelationshipResource = new ServiceRelationshipResource(serviceRelationshipService, serviceRelationshipQueryService);
        this.restServiceRelationshipMockMvc = MockMvcBuilders.standaloneSetup(serviceRelationshipResource)
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
    public static ServiceRelationship createEntity(EntityManager em) {
        ServiceRelationship serviceRelationship = new ServiceRelationship()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .relationship(DEFAULT_RELATIONSHIP);
        return serviceRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRelationship createUpdatedEntity(EntityManager em) {
        ServiceRelationship serviceRelationship = new ServiceRelationship()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .relationship(UPDATED_RELATIONSHIP);
        return serviceRelationship;
    }

    @BeforeEach
    public void initTest() {
        serviceRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRelationship() throws Exception {
        int databaseSizeBeforeCreate = serviceRelationshipRepository.findAll().size();

        // Create the ServiceRelationship
        ServiceRelationshipDTO serviceRelationshipDTO = serviceRelationshipMapper.toDto(serviceRelationship);
        restServiceRelationshipMockMvc.perform(post("/api/service-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRelationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRelationship in the database
        List<ServiceRelationship> serviceRelationshipList = serviceRelationshipRepository.findAll();
        assertThat(serviceRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRelationship testServiceRelationship = serviceRelationshipList.get(serviceRelationshipList.size() - 1);
        assertThat(testServiceRelationship.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceRelationship.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceRelationship.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceRelationship.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceRelationship.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceRelationship.getRelationship()).isEqualTo(DEFAULT_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void createServiceRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRelationshipRepository.findAll().size();

        // Create the ServiceRelationship with an existing ID
        serviceRelationship.setId(1L);
        ServiceRelationshipDTO serviceRelationshipDTO = serviceRelationshipMapper.toDto(serviceRelationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRelationshipMockMvc.perform(post("/api/service-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRelationship in the database
        List<ServiceRelationship> serviceRelationshipList = serviceRelationshipRepository.findAll();
        assertThat(serviceRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceRelationships() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceRelationship() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get the serviceRelationship
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships/{id}", serviceRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRelationship.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.relationship").value(DEFAULT_RELATIONSHIP.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceRelationshipShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceRelationshipList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRelationshipShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceRelationshipShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceRelationshipList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRelationshipShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdBy is not null
        defaultServiceRelationshipShouldBeFound("createdBy.specified=true");

        // Get all the serviceRelationshipList where createdBy is null
        defaultServiceRelationshipShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceRelationshipShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceRelationshipList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRelationshipShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceRelationshipShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceRelationshipList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRelationshipShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where createdDateTime is not null
        defaultServiceRelationshipShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceRelationshipList where createdDateTime is null
        defaultServiceRelationshipShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceRelationshipShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceRelationshipList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRelationshipShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceRelationshipShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceRelationshipList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRelationshipShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedBy is not null
        defaultServiceRelationshipShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceRelationshipList where modifiedBy is null
        defaultServiceRelationshipShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceRelationshipShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceRelationshipList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRelationshipShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceRelationshipShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceRelationshipList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRelationshipShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where modifiedDateTime is not null
        defaultServiceRelationshipShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceRelationshipList where modifiedDateTime is null
        defaultServiceRelationshipShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where version equals to DEFAULT_VERSION
        defaultServiceRelationshipShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceRelationshipList where version equals to UPDATED_VERSION
        defaultServiceRelationshipShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceRelationshipShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceRelationshipList where version equals to UPDATED_VERSION
        defaultServiceRelationshipShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where version is not null
        defaultServiceRelationshipShouldBeFound("version.specified=true");

        // Get all the serviceRelationshipList where version is null
        defaultServiceRelationshipShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where version greater than or equals to DEFAULT_VERSION
        defaultServiceRelationshipShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceRelationshipList where version greater than or equals to UPDATED_VERSION
        defaultServiceRelationshipShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where version less than or equals to DEFAULT_VERSION
        defaultServiceRelationshipShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceRelationshipList where version less than or equals to UPDATED_VERSION
        defaultServiceRelationshipShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceRelationshipsByRelationshipIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where relationship equals to DEFAULT_RELATIONSHIP
        defaultServiceRelationshipShouldBeFound("relationship.equals=" + DEFAULT_RELATIONSHIP);

        // Get all the serviceRelationshipList where relationship equals to UPDATED_RELATIONSHIP
        defaultServiceRelationshipShouldNotBeFound("relationship.equals=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByRelationshipIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where relationship in DEFAULT_RELATIONSHIP or UPDATED_RELATIONSHIP
        defaultServiceRelationshipShouldBeFound("relationship.in=" + DEFAULT_RELATIONSHIP + "," + UPDATED_RELATIONSHIP);

        // Get all the serviceRelationshipList where relationship equals to UPDATED_RELATIONSHIP
        defaultServiceRelationshipShouldNotBeFound("relationship.in=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByRelationshipIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        // Get all the serviceRelationshipList where relationship is not null
        defaultServiceRelationshipShouldBeFound("relationship.specified=true");

        // Get all the serviceRelationshipList where relationship is null
        defaultServiceRelationshipShouldNotBeFound("relationship.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRelationshipsByFromServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord fromService = ServiceRecordResourceIT.createEntity(em);
        em.persist(fromService);
        em.flush();
        serviceRelationship.setFromService(fromService);
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);
        Long fromServiceId = fromService.getId();

        // Get all the serviceRelationshipList where fromService equals to fromServiceId
        defaultServiceRelationshipShouldBeFound("fromServiceId.equals=" + fromServiceId);

        // Get all the serviceRelationshipList where fromService equals to fromServiceId + 1
        defaultServiceRelationshipShouldNotBeFound("fromServiceId.equals=" + (fromServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRelationshipsByToServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord toService = ServiceRecordResourceIT.createEntity(em);
        em.persist(toService);
        em.flush();
        serviceRelationship.setToService(toService);
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);
        Long toServiceId = toService.getId();

        // Get all the serviceRelationshipList where toService equals to toServiceId
        defaultServiceRelationshipShouldBeFound("toServiceId.equals=" + toServiceId);

        // Get all the serviceRelationshipList where toService equals to toServiceId + 1
        defaultServiceRelationshipShouldNotBeFound("toServiceId.equals=" + (toServiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceRelationshipShouldBeFound(String filter) throws Exception {
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP)));

        // Check, that the count call also returns 1
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceRelationshipShouldNotBeFound(String filter) throws Exception {
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceRelationship() throws Exception {
        // Get the serviceRelationship
        restServiceRelationshipMockMvc.perform(get("/api/service-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRelationship() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        int databaseSizeBeforeUpdate = serviceRelationshipRepository.findAll().size();

        // Update the serviceRelationship
        ServiceRelationship updatedServiceRelationship = serviceRelationshipRepository.findById(serviceRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRelationship are not directly saved in db
        em.detach(updatedServiceRelationship);
        updatedServiceRelationship
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .relationship(UPDATED_RELATIONSHIP);
        ServiceRelationshipDTO serviceRelationshipDTO = serviceRelationshipMapper.toDto(updatedServiceRelationship);

        restServiceRelationshipMockMvc.perform(put("/api/service-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRelationshipDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRelationship in the database
        List<ServiceRelationship> serviceRelationshipList = serviceRelationshipRepository.findAll();
        assertThat(serviceRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ServiceRelationship testServiceRelationship = serviceRelationshipList.get(serviceRelationshipList.size() - 1);
        assertThat(testServiceRelationship.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceRelationship.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceRelationship.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceRelationship.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceRelationship.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceRelationship.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRelationship() throws Exception {
        int databaseSizeBeforeUpdate = serviceRelationshipRepository.findAll().size();

        // Create the ServiceRelationship
        ServiceRelationshipDTO serviceRelationshipDTO = serviceRelationshipMapper.toDto(serviceRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRelationshipMockMvc.perform(put("/api/service-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRelationship in the database
        List<ServiceRelationship> serviceRelationshipList = serviceRelationshipRepository.findAll();
        assertThat(serviceRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceRelationship() throws Exception {
        // Initialize the database
        serviceRelationshipRepository.saveAndFlush(serviceRelationship);

        int databaseSizeBeforeDelete = serviceRelationshipRepository.findAll().size();

        // Delete the serviceRelationship
        restServiceRelationshipMockMvc.perform(delete("/api/service-relationships/{id}", serviceRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRelationship> serviceRelationshipList = serviceRelationshipRepository.findAll();
        assertThat(serviceRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRelationship.class);
        ServiceRelationship serviceRelationship1 = new ServiceRelationship();
        serviceRelationship1.setId(1L);
        ServiceRelationship serviceRelationship2 = new ServiceRelationship();
        serviceRelationship2.setId(serviceRelationship1.getId());
        assertThat(serviceRelationship1).isEqualTo(serviceRelationship2);
        serviceRelationship2.setId(2L);
        assertThat(serviceRelationship1).isNotEqualTo(serviceRelationship2);
        serviceRelationship1.setId(null);
        assertThat(serviceRelationship1).isNotEqualTo(serviceRelationship2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRelationshipDTO.class);
        ServiceRelationshipDTO serviceRelationshipDTO1 = new ServiceRelationshipDTO();
        serviceRelationshipDTO1.setId(1L);
        ServiceRelationshipDTO serviceRelationshipDTO2 = new ServiceRelationshipDTO();
        assertThat(serviceRelationshipDTO1).isNotEqualTo(serviceRelationshipDTO2);
        serviceRelationshipDTO2.setId(serviceRelationshipDTO1.getId());
        assertThat(serviceRelationshipDTO1).isEqualTo(serviceRelationshipDTO2);
        serviceRelationshipDTO2.setId(2L);
        assertThat(serviceRelationshipDTO1).isNotEqualTo(serviceRelationshipDTO2);
        serviceRelationshipDTO1.setId(null);
        assertThat(serviceRelationshipDTO1).isNotEqualTo(serviceRelationshipDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRelationshipMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRelationshipMapper.fromId(null)).isNull();
    }
}
