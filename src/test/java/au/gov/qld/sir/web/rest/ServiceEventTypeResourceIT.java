package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceEventType;
import au.gov.qld.sir.domain.ServiceEvent;
import au.gov.qld.sir.repository.ServiceEventTypeRepository;
import au.gov.qld.sir.service.ServiceEventTypeService;
import au.gov.qld.sir.service.dto.ServiceEventTypeDTO;
import au.gov.qld.sir.service.mapper.ServiceEventTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceEventTypeCriteria;
import au.gov.qld.sir.service.ServiceEventTypeQueryService;

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
 * Integration tests for the {@Link ServiceEventTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceEventTypeResourceIT {

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

    private static final String DEFAULT_SERVICE_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_EVENT = "BBBBBBBBBB";

    @Autowired
    private ServiceEventTypeRepository serviceEventTypeRepository;

    @Autowired
    private ServiceEventTypeMapper serviceEventTypeMapper;

    @Autowired
    private ServiceEventTypeService serviceEventTypeService;

    @Autowired
    private ServiceEventTypeQueryService serviceEventTypeQueryService;

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

    private MockMvc restServiceEventTypeMockMvc;

    private ServiceEventType serviceEventType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceEventTypeResource serviceEventTypeResource = new ServiceEventTypeResource(serviceEventTypeService, serviceEventTypeQueryService);
        this.restServiceEventTypeMockMvc = MockMvcBuilders.standaloneSetup(serviceEventTypeResource)
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
    public static ServiceEventType createEntity(EntityManager em) {
        ServiceEventType serviceEventType = new ServiceEventType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .serviceEvent(DEFAULT_SERVICE_EVENT);
        return serviceEventType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceEventType createUpdatedEntity(EntityManager em) {
        ServiceEventType serviceEventType = new ServiceEventType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceEvent(UPDATED_SERVICE_EVENT);
        return serviceEventType;
    }

    @BeforeEach
    public void initTest() {
        serviceEventType = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceEventType() throws Exception {
        int databaseSizeBeforeCreate = serviceEventTypeRepository.findAll().size();

        // Create the ServiceEventType
        ServiceEventTypeDTO serviceEventTypeDTO = serviceEventTypeMapper.toDto(serviceEventType);
        restServiceEventTypeMockMvc.perform(post("/api/service-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceEventType in the database
        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceEventType testServiceEventType = serviceEventTypeList.get(serviceEventTypeList.size() - 1);
        assertThat(testServiceEventType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceEventType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceEventType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceEventType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceEventType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceEventType.getServiceEvent()).isEqualTo(DEFAULT_SERVICE_EVENT);
    }

    @Test
    @Transactional
    public void createServiceEventTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceEventTypeRepository.findAll().size();

        // Create the ServiceEventType with an existing ID
        serviceEventType.setId(1L);
        ServiceEventTypeDTO serviceEventTypeDTO = serviceEventTypeMapper.toDto(serviceEventType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceEventTypeMockMvc.perform(post("/api/service-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEventType in the database
        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceEventTypeRepository.findAll().size();
        // set the field null
        serviceEventType.setServiceEvent(null);

        // Create the ServiceEventType, which fails.
        ServiceEventTypeDTO serviceEventTypeDTO = serviceEventTypeMapper.toDto(serviceEventType);

        restServiceEventTypeMockMvc.perform(post("/api/service-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypes() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceEvent").value(hasItem(DEFAULT_SERVICE_EVENT.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceEventType() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get the serviceEventType
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types/{id}", serviceEventType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceEventType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.serviceEvent").value(DEFAULT_SERVICE_EVENT.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceEventTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceEventTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEventTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceEventTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceEventTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEventTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdBy is not null
        defaultServiceEventTypeShouldBeFound("createdBy.specified=true");

        // Get all the serviceEventTypeList where createdBy is null
        defaultServiceEventTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceEventTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceEventTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEventTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceEventTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceEventTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEventTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where createdDateTime is not null
        defaultServiceEventTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceEventTypeList where createdDateTime is null
        defaultServiceEventTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceEventTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceEventTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEventTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceEventTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceEventTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEventTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedBy is not null
        defaultServiceEventTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceEventTypeList where modifiedBy is null
        defaultServiceEventTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceEventTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceEventTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceEventTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where modifiedDateTime is not null
        defaultServiceEventTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceEventTypeList where modifiedDateTime is null
        defaultServiceEventTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where version equals to DEFAULT_VERSION
        defaultServiceEventTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceEventTypeList where version equals to UPDATED_VERSION
        defaultServiceEventTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceEventTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceEventTypeList where version equals to UPDATED_VERSION
        defaultServiceEventTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where version is not null
        defaultServiceEventTypeShouldBeFound("version.specified=true");

        // Get all the serviceEventTypeList where version is null
        defaultServiceEventTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where version greater than or equals to DEFAULT_VERSION
        defaultServiceEventTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceEventTypeList where version greater than or equals to UPDATED_VERSION
        defaultServiceEventTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where version less than or equals to DEFAULT_VERSION
        defaultServiceEventTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceEventTypeList where version less than or equals to UPDATED_VERSION
        defaultServiceEventTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceEventTypesByServiceEventIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where serviceEvent equals to DEFAULT_SERVICE_EVENT
        defaultServiceEventTypeShouldBeFound("serviceEvent.equals=" + DEFAULT_SERVICE_EVENT);

        // Get all the serviceEventTypeList where serviceEvent equals to UPDATED_SERVICE_EVENT
        defaultServiceEventTypeShouldNotBeFound("serviceEvent.equals=" + UPDATED_SERVICE_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByServiceEventIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where serviceEvent in DEFAULT_SERVICE_EVENT or UPDATED_SERVICE_EVENT
        defaultServiceEventTypeShouldBeFound("serviceEvent.in=" + DEFAULT_SERVICE_EVENT + "," + UPDATED_SERVICE_EVENT);

        // Get all the serviceEventTypeList where serviceEvent equals to UPDATED_SERVICE_EVENT
        defaultServiceEventTypeShouldNotBeFound("serviceEvent.in=" + UPDATED_SERVICE_EVENT);
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByServiceEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        // Get all the serviceEventTypeList where serviceEvent is not null
        defaultServiceEventTypeShouldBeFound("serviceEvent.specified=true");

        // Get all the serviceEventTypeList where serviceEvent is null
        defaultServiceEventTypeShouldNotBeFound("serviceEvent.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventTypesByServiceEventIsEqualToSomethingb() throws Exception {
        // Initialize the database
        ServiceEvent serviceEvent = ServiceEventResourceIT.createEntity(em);
        em.persist(serviceEvent);
        em.flush();
        serviceEventType.addServiceEvent(serviceEvent);
        serviceEventTypeRepository.saveAndFlush(serviceEventType);
        Long serviceEventId = serviceEvent.getId();

        // Get all the serviceEventTypeList where serviceEvent equals to serviceEventId
        defaultServiceEventTypeShouldBeFound("serviceEventId.equals=" + serviceEventId);

        // Get all the serviceEventTypeList where serviceEvent equals to serviceEventId + 1
        defaultServiceEventTypeShouldNotBeFound("serviceEventId.equals=" + (serviceEventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceEventTypeShouldBeFound(String filter) throws Exception {
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEventType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceEvent").value(hasItem(DEFAULT_SERVICE_EVENT)));

        // Check, that the count call also returns 1
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceEventTypeShouldNotBeFound(String filter) throws Exception {
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceEventType() throws Exception {
        // Get the serviceEventType
        restServiceEventTypeMockMvc.perform(get("/api/service-event-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceEventType() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        int databaseSizeBeforeUpdate = serviceEventTypeRepository.findAll().size();

        // Update the serviceEventType
        ServiceEventType updatedServiceEventType = serviceEventTypeRepository.findById(serviceEventType.getId()).get();
        // Disconnect from session so that the updates on updatedServiceEventType are not directly saved in db
        em.detach(updatedServiceEventType);
        updatedServiceEventType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceEvent(UPDATED_SERVICE_EVENT);
        ServiceEventTypeDTO serviceEventTypeDTO = serviceEventTypeMapper.toDto(updatedServiceEventType);

        restServiceEventTypeMockMvc.perform(put("/api/service-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceEventType in the database
        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceEventType testServiceEventType = serviceEventTypeList.get(serviceEventTypeList.size() - 1);
        assertThat(testServiceEventType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceEventType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceEventType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceEventType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceEventType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceEventType.getServiceEvent()).isEqualTo(UPDATED_SERVICE_EVENT);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceEventType() throws Exception {
        int databaseSizeBeforeUpdate = serviceEventTypeRepository.findAll().size();

        // Create the ServiceEventType
        ServiceEventTypeDTO serviceEventTypeDTO = serviceEventTypeMapper.toDto(serviceEventType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceEventTypeMockMvc.perform(put("/api/service-event-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEventType in the database
        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceEventType() throws Exception {
        // Initialize the database
        serviceEventTypeRepository.saveAndFlush(serviceEventType);

        int databaseSizeBeforeDelete = serviceEventTypeRepository.findAll().size();

        // Delete the serviceEventType
        restServiceEventTypeMockMvc.perform(delete("/api/service-event-types/{id}", serviceEventType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceEventType> serviceEventTypeList = serviceEventTypeRepository.findAll();
        assertThat(serviceEventTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEventType.class);
        ServiceEventType serviceEventType1 = new ServiceEventType();
        serviceEventType1.setId(1L);
        ServiceEventType serviceEventType2 = new ServiceEventType();
        serviceEventType2.setId(serviceEventType1.getId());
        assertThat(serviceEventType1).isEqualTo(serviceEventType2);
        serviceEventType2.setId(2L);
        assertThat(serviceEventType1).isNotEqualTo(serviceEventType2);
        serviceEventType1.setId(null);
        assertThat(serviceEventType1).isNotEqualTo(serviceEventType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEventTypeDTO.class);
        ServiceEventTypeDTO serviceEventTypeDTO1 = new ServiceEventTypeDTO();
        serviceEventTypeDTO1.setId(1L);
        ServiceEventTypeDTO serviceEventTypeDTO2 = new ServiceEventTypeDTO();
        assertThat(serviceEventTypeDTO1).isNotEqualTo(serviceEventTypeDTO2);
        serviceEventTypeDTO2.setId(serviceEventTypeDTO1.getId());
        assertThat(serviceEventTypeDTO1).isEqualTo(serviceEventTypeDTO2);
        serviceEventTypeDTO2.setId(2L);
        assertThat(serviceEventTypeDTO1).isNotEqualTo(serviceEventTypeDTO2);
        serviceEventTypeDTO1.setId(null);
        assertThat(serviceEventTypeDTO1).isNotEqualTo(serviceEventTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceEventTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceEventTypeMapper.fromId(null)).isNull();
    }
}
