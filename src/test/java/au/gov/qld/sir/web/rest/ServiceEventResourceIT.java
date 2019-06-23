package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceEvent;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.ServiceEventType;
import au.gov.qld.sir.repository.ServiceEventRepository;
import au.gov.qld.sir.service.ServiceEventService;
import au.gov.qld.sir.service.dto.ServiceEventDTO;
import au.gov.qld.sir.service.mapper.ServiceEventMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceEventCriteria;
import au.gov.qld.sir.service.ServiceEventQueryService;

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
 * Integration tests for the {@Link ServiceEventResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceEventResourceIT {

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

    private static final Integer DEFAULT_SERVICE_EVENT_SEQUENCE = 1;
    private static final Integer UPDATED_SERVICE_EVENT_SEQUENCE = 2;

    @Autowired
    private ServiceEventRepository serviceEventRepository;

    @Autowired
    private ServiceEventMapper serviceEventMapper;

    @Autowired
    private ServiceEventService serviceEventService;

    @Autowired
    private ServiceEventQueryService serviceEventQueryService;

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

    private MockMvc restServiceEventMockMvc;

    private ServiceEvent serviceEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceEventResource serviceEventResource = new ServiceEventResource(serviceEventService, serviceEventQueryService);
        this.restServiceEventMockMvc = MockMvcBuilders.standaloneSetup(serviceEventResource)
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
    public static ServiceEvent createEntity(EntityManager em) {
        ServiceEvent serviceEvent = new ServiceEvent()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .serviceEventSequence(DEFAULT_SERVICE_EVENT_SEQUENCE);
        return serviceEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceEvent createUpdatedEntity(EntityManager em) {
        ServiceEvent serviceEvent = new ServiceEvent()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceEventSequence(UPDATED_SERVICE_EVENT_SEQUENCE);
        return serviceEvent;
    }

    @BeforeEach
    public void initTest() {
        serviceEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceEvent() throws Exception {
        int databaseSizeBeforeCreate = serviceEventRepository.findAll().size();

        // Create the ServiceEvent
        ServiceEventDTO serviceEventDTO = serviceEventMapper.toDto(serviceEvent);
        restServiceEventMockMvc.perform(post("/api/service-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceEvent in the database
        List<ServiceEvent> serviceEventList = serviceEventRepository.findAll();
        assertThat(serviceEventList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceEvent testServiceEvent = serviceEventList.get(serviceEventList.size() - 1);
        assertThat(testServiceEvent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceEvent.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceEvent.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceEvent.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceEvent.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceEvent.getServiceEventSequence()).isEqualTo(DEFAULT_SERVICE_EVENT_SEQUENCE);
    }

    @Test
    @Transactional
    public void createServiceEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceEventRepository.findAll().size();

        // Create the ServiceEvent with an existing ID
        serviceEvent.setId(1L);
        ServiceEventDTO serviceEventDTO = serviceEventMapper.toDto(serviceEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceEventMockMvc.perform(post("/api/service-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEvent in the database
        List<ServiceEvent> serviceEventList = serviceEventRepository.findAll();
        assertThat(serviceEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceEvents() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList
        restServiceEventMockMvc.perform(get("/api/service-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceEventSequence").value(hasItem(DEFAULT_SERVICE_EVENT_SEQUENCE)));
    }
    
    @Test
    @Transactional
    public void getServiceEvent() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get the serviceEvent
        restServiceEventMockMvc.perform(get("/api/service-events/{id}", serviceEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceEvent.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.serviceEventSequence").value(DEFAULT_SERVICE_EVENT_SEQUENCE));
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceEventShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceEventList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEventShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceEventShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceEventList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceEventShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdBy is not null
        defaultServiceEventShouldBeFound("createdBy.specified=true");

        // Get all the serviceEventList where createdBy is null
        defaultServiceEventShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceEventShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceEventList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEventShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceEventShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceEventList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceEventShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where createdDateTime is not null
        defaultServiceEventShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceEventList where createdDateTime is null
        defaultServiceEventShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceEventShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceEventList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEventShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceEventShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceEventList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceEventShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedBy is not null
        defaultServiceEventShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceEventList where modifiedBy is null
        defaultServiceEventShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceEventShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceEventList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceEventList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceEventShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where modifiedDateTime is not null
        defaultServiceEventShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceEventList where modifiedDateTime is null
        defaultServiceEventShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where version equals to DEFAULT_VERSION
        defaultServiceEventShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceEventList where version equals to UPDATED_VERSION
        defaultServiceEventShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceEventShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceEventList where version equals to UPDATED_VERSION
        defaultServiceEventShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where version is not null
        defaultServiceEventShouldBeFound("version.specified=true");

        // Get all the serviceEventList where version is null
        defaultServiceEventShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where version greater than or equals to DEFAULT_VERSION
        defaultServiceEventShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceEventList where version greater than or equals to UPDATED_VERSION
        defaultServiceEventShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where version less than or equals to DEFAULT_VERSION
        defaultServiceEventShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceEventList where version less than or equals to UPDATED_VERSION
        defaultServiceEventShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where serviceEventSequence equals to DEFAULT_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldBeFound("serviceEventSequence.equals=" + DEFAULT_SERVICE_EVENT_SEQUENCE);

        // Get all the serviceEventList where serviceEventSequence equals to UPDATED_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldNotBeFound("serviceEventSequence.equals=" + UPDATED_SERVICE_EVENT_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where serviceEventSequence in DEFAULT_SERVICE_EVENT_SEQUENCE or UPDATED_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldBeFound("serviceEventSequence.in=" + DEFAULT_SERVICE_EVENT_SEQUENCE + "," + UPDATED_SERVICE_EVENT_SEQUENCE);

        // Get all the serviceEventList where serviceEventSequence equals to UPDATED_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldNotBeFound("serviceEventSequence.in=" + UPDATED_SERVICE_EVENT_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where serviceEventSequence is not null
        defaultServiceEventShouldBeFound("serviceEventSequence.specified=true");

        // Get all the serviceEventList where serviceEventSequence is null
        defaultServiceEventShouldNotBeFound("serviceEventSequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where serviceEventSequence greater than or equals to DEFAULT_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldBeFound("serviceEventSequence.greaterOrEqualThan=" + DEFAULT_SERVICE_EVENT_SEQUENCE);

        // Get all the serviceEventList where serviceEventSequence greater than or equals to UPDATED_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldNotBeFound("serviceEventSequence.greaterOrEqualThan=" + UPDATED_SERVICE_EVENT_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        // Get all the serviceEventList where serviceEventSequence less than or equals to DEFAULT_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldNotBeFound("serviceEventSequence.lessThan=" + DEFAULT_SERVICE_EVENT_SEQUENCE);

        // Get all the serviceEventList where serviceEventSequence less than or equals to UPDATED_SERVICE_EVENT_SEQUENCE
        defaultServiceEventShouldBeFound("serviceEventSequence.lessThan=" + UPDATED_SERVICE_EVENT_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllServiceEventsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceEvent.setServiceRecord(serviceRecord);
        serviceEventRepository.saveAndFlush(serviceEvent);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceEventList where serviceRecord equals to serviceRecordId
        defaultServiceEventShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceEventList where serviceRecord equals to serviceRecordId + 1
        defaultServiceEventShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceEventsByServiceEventTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceEventType serviceEventType = ServiceEventTypeResourceIT.createEntity(em);
        em.persist(serviceEventType);
        em.flush();
        serviceEvent.setServiceEventType(serviceEventType);
        serviceEventRepository.saveAndFlush(serviceEvent);
        Long serviceEventTypeId = serviceEventType.getId();

        // Get all the serviceEventList where serviceEventType equals to serviceEventTypeId
        defaultServiceEventShouldBeFound("serviceEventTypeId.equals=" + serviceEventTypeId);

        // Get all the serviceEventList where serviceEventType equals to serviceEventTypeId + 1
        defaultServiceEventShouldNotBeFound("serviceEventTypeId.equals=" + (serviceEventTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceEventShouldBeFound(String filter) throws Exception {
        restServiceEventMockMvc.perform(get("/api/service-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceEventSequence").value(hasItem(DEFAULT_SERVICE_EVENT_SEQUENCE)));

        // Check, that the count call also returns 1
        restServiceEventMockMvc.perform(get("/api/service-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceEventShouldNotBeFound(String filter) throws Exception {
        restServiceEventMockMvc.perform(get("/api/service-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceEventMockMvc.perform(get("/api/service-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceEvent() throws Exception {
        // Get the serviceEvent
        restServiceEventMockMvc.perform(get("/api/service-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceEvent() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        int databaseSizeBeforeUpdate = serviceEventRepository.findAll().size();

        // Update the serviceEvent
        ServiceEvent updatedServiceEvent = serviceEventRepository.findById(serviceEvent.getId()).get();
        // Disconnect from session so that the updates on updatedServiceEvent are not directly saved in db
        em.detach(updatedServiceEvent);
        updatedServiceEvent
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceEventSequence(UPDATED_SERVICE_EVENT_SEQUENCE);
        ServiceEventDTO serviceEventDTO = serviceEventMapper.toDto(updatedServiceEvent);

        restServiceEventMockMvc.perform(put("/api/service-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceEvent in the database
        List<ServiceEvent> serviceEventList = serviceEventRepository.findAll();
        assertThat(serviceEventList).hasSize(databaseSizeBeforeUpdate);
        ServiceEvent testServiceEvent = serviceEventList.get(serviceEventList.size() - 1);
        assertThat(testServiceEvent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceEvent.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceEvent.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceEvent.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceEvent.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceEvent.getServiceEventSequence()).isEqualTo(UPDATED_SERVICE_EVENT_SEQUENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceEvent() throws Exception {
        int databaseSizeBeforeUpdate = serviceEventRepository.findAll().size();

        // Create the ServiceEvent
        ServiceEventDTO serviceEventDTO = serviceEventMapper.toDto(serviceEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceEventMockMvc.perform(put("/api/service-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceEvent in the database
        List<ServiceEvent> serviceEventList = serviceEventRepository.findAll();
        assertThat(serviceEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceEvent() throws Exception {
        // Initialize the database
        serviceEventRepository.saveAndFlush(serviceEvent);

        int databaseSizeBeforeDelete = serviceEventRepository.findAll().size();

        // Delete the serviceEvent
        restServiceEventMockMvc.perform(delete("/api/service-events/{id}", serviceEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceEvent> serviceEventList = serviceEventRepository.findAll();
        assertThat(serviceEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEvent.class);
        ServiceEvent serviceEvent1 = new ServiceEvent();
        serviceEvent1.setId(1L);
        ServiceEvent serviceEvent2 = new ServiceEvent();
        serviceEvent2.setId(serviceEvent1.getId());
        assertThat(serviceEvent1).isEqualTo(serviceEvent2);
        serviceEvent2.setId(2L);
        assertThat(serviceEvent1).isNotEqualTo(serviceEvent2);
        serviceEvent1.setId(null);
        assertThat(serviceEvent1).isNotEqualTo(serviceEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEventDTO.class);
        ServiceEventDTO serviceEventDTO1 = new ServiceEventDTO();
        serviceEventDTO1.setId(1L);
        ServiceEventDTO serviceEventDTO2 = new ServiceEventDTO();
        assertThat(serviceEventDTO1).isNotEqualTo(serviceEventDTO2);
        serviceEventDTO2.setId(serviceEventDTO1.getId());
        assertThat(serviceEventDTO1).isEqualTo(serviceEventDTO2);
        serviceEventDTO2.setId(2L);
        assertThat(serviceEventDTO1).isNotEqualTo(serviceEventDTO2);
        serviceEventDTO1.setId(null);
        assertThat(serviceEventDTO1).isNotEqualTo(serviceEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceEventMapper.fromId(null)).isNull();
    }
}
