package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceDelivery;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.repository.ServiceDeliveryRepository;
import au.gov.qld.sir.service.ServiceDeliveryService;
import au.gov.qld.sir.service.dto.ServiceDeliveryDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceDeliveryCriteria;
import au.gov.qld.sir.service.ServiceDeliveryQueryService;

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
 * Integration tests for the {@Link ServiceDeliveryResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceDeliveryResourceIT {

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

    private static final String DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ServiceDeliveryRepository serviceDeliveryRepository;

    @Autowired
    private ServiceDeliveryMapper serviceDeliveryMapper;

    @Autowired
    private ServiceDeliveryService serviceDeliveryService;

    @Autowired
    private ServiceDeliveryQueryService serviceDeliveryQueryService;

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

    private MockMvc restServiceDeliveryMockMvc;

    private ServiceDelivery serviceDelivery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceDeliveryResource serviceDeliveryResource = new ServiceDeliveryResource(serviceDeliveryService, serviceDeliveryQueryService);
        this.restServiceDeliveryMockMvc = MockMvcBuilders.standaloneSetup(serviceDeliveryResource)
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
    public static ServiceDelivery createEntity(EntityManager em) {
        ServiceDelivery serviceDelivery = new ServiceDelivery()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .serviceDeliveryChannelType(DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE)
            .status(DEFAULT_STATUS);
        return serviceDelivery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceDelivery createUpdatedEntity(EntityManager em) {
        ServiceDelivery serviceDelivery = new ServiceDelivery()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceDeliveryChannelType(UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE)
            .status(UPDATED_STATUS);
        return serviceDelivery;
    }

    @BeforeEach
    public void initTest() {
        serviceDelivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceDelivery() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryRepository.findAll().size();

        // Create the ServiceDelivery
        ServiceDeliveryDTO serviceDeliveryDTO = serviceDeliveryMapper.toDto(serviceDelivery);
        restServiceDeliveryMockMvc.perform(post("/api/service-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceDelivery in the database
        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceDelivery testServiceDelivery = serviceDeliveryList.get(serviceDeliveryList.size() - 1);
        assertThat(testServiceDelivery.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceDelivery.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceDelivery.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceDelivery.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceDelivery.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceDelivery.getServiceDeliveryChannelType()).isEqualTo(DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE);
        assertThat(testServiceDelivery.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createServiceDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryRepository.findAll().size();

        // Create the ServiceDelivery with an existing ID
        serviceDelivery.setId(1L);
        ServiceDeliveryDTO serviceDeliveryDTO = serviceDeliveryMapper.toDto(serviceDelivery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceDeliveryMockMvc.perform(post("/api/service-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDelivery in the database
        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceDeliveryChannelTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceDeliveryRepository.findAll().size();
        // set the field null
        serviceDelivery.setServiceDeliveryChannelType(null);

        // Create the ServiceDelivery, which fails.
        ServiceDeliveryDTO serviceDeliveryDTO = serviceDeliveryMapper.toDto(serviceDelivery);

        restServiceDeliveryMockMvc.perform(post("/api/service-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveries() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDelivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceDeliveryChannelType").value(hasItem(DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceDelivery() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get the serviceDelivery
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries/{id}", serviceDelivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceDelivery.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.serviceDeliveryChannelType").value(DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceDeliveryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceDeliveryList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceDeliveryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceDeliveryList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdBy is not null
        defaultServiceDeliveryShouldBeFound("createdBy.specified=true");

        // Get all the serviceDeliveryList where createdBy is null
        defaultServiceDeliveryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceDeliveryShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceDeliveryList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceDeliveryList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where createdDateTime is not null
        defaultServiceDeliveryShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceDeliveryList where createdDateTime is null
        defaultServiceDeliveryShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceDeliveryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceDeliveryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceDeliveryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceDeliveryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedBy is not null
        defaultServiceDeliveryShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceDeliveryList where modifiedBy is null
        defaultServiceDeliveryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceDeliveryShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where modifiedDateTime is not null
        defaultServiceDeliveryShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceDeliveryList where modifiedDateTime is null
        defaultServiceDeliveryShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where version equals to DEFAULT_VERSION
        defaultServiceDeliveryShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryList where version equals to UPDATED_VERSION
        defaultServiceDeliveryShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceDeliveryShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceDeliveryList where version equals to UPDATED_VERSION
        defaultServiceDeliveryShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where version is not null
        defaultServiceDeliveryShouldBeFound("version.specified=true");

        // Get all the serviceDeliveryList where version is null
        defaultServiceDeliveryShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where version greater than or equals to DEFAULT_VERSION
        defaultServiceDeliveryShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryList where version greater than or equals to UPDATED_VERSION
        defaultServiceDeliveryShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where version less than or equals to DEFAULT_VERSION
        defaultServiceDeliveryShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryList where version less than or equals to UPDATED_VERSION
        defaultServiceDeliveryShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceDeliveriesByServiceDeliveryChannelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where serviceDeliveryChannelType equals to DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE
        defaultServiceDeliveryShouldBeFound("serviceDeliveryChannelType.equals=" + DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE);

        // Get all the serviceDeliveryList where serviceDeliveryChannelType equals to UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE
        defaultServiceDeliveryShouldNotBeFound("serviceDeliveryChannelType.equals=" + UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByServiceDeliveryChannelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where serviceDeliveryChannelType in DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE or UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE
        defaultServiceDeliveryShouldBeFound("serviceDeliveryChannelType.in=" + DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE + "," + UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE);

        // Get all the serviceDeliveryList where serviceDeliveryChannelType equals to UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE
        defaultServiceDeliveryShouldNotBeFound("serviceDeliveryChannelType.in=" + UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByServiceDeliveryChannelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where serviceDeliveryChannelType is not null
        defaultServiceDeliveryShouldBeFound("serviceDeliveryChannelType.specified=true");

        // Get all the serviceDeliveryList where serviceDeliveryChannelType is null
        defaultServiceDeliveryShouldNotBeFound("serviceDeliveryChannelType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where status equals to DEFAULT_STATUS
        defaultServiceDeliveryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the serviceDeliveryList where status equals to UPDATED_STATUS
        defaultServiceDeliveryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultServiceDeliveryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the serviceDeliveryList where status equals to UPDATED_STATUS
        defaultServiceDeliveryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        // Get all the serviceDeliveryList where status is not null
        defaultServiceDeliveryShouldBeFound("status.specified=true");

        // Get all the serviceDeliveryList where status is null
        defaultServiceDeliveryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveriesByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceDelivery.setServiceRecord(serviceRecord);
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceDeliveryList where serviceRecord equals to serviceRecordId
        defaultServiceDeliveryShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceDeliveryList where serviceRecord equals to serviceRecordId + 1
        defaultServiceDeliveryShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceDeliveriesByDeliveryChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        DeliveryChannel deliveryChannel = DeliveryChannelResourceIT.createEntity(em);
        em.persist(deliveryChannel);
        em.flush();
        serviceDelivery.addDeliveryChannel(deliveryChannel);
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);
        Long deliveryChannelId = deliveryChannel.getId();

        // Get all the serviceDeliveryList where deliveryChannel equals to deliveryChannelId
        defaultServiceDeliveryShouldBeFound("deliveryChannelId.equals=" + deliveryChannelId);

        // Get all the serviceDeliveryList where deliveryChannel equals to deliveryChannelId + 1
        defaultServiceDeliveryShouldNotBeFound("deliveryChannelId.equals=" + (deliveryChannelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceDeliveryShouldBeFound(String filter) throws Exception {
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDelivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].serviceDeliveryChannelType").value(hasItem(DEFAULT_SERVICE_DELIVERY_CHANNEL_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceDeliveryShouldNotBeFound(String filter) throws Exception {
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceDelivery() throws Exception {
        // Get the serviceDelivery
        restServiceDeliveryMockMvc.perform(get("/api/service-deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceDelivery() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        int databaseSizeBeforeUpdate = serviceDeliveryRepository.findAll().size();

        // Update the serviceDelivery
        ServiceDelivery updatedServiceDelivery = serviceDeliveryRepository.findById(serviceDelivery.getId()).get();
        // Disconnect from session so that the updates on updatedServiceDelivery are not directly saved in db
        em.detach(updatedServiceDelivery);
        updatedServiceDelivery
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .serviceDeliveryChannelType(UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE)
            .status(UPDATED_STATUS);
        ServiceDeliveryDTO serviceDeliveryDTO = serviceDeliveryMapper.toDto(updatedServiceDelivery);

        restServiceDeliveryMockMvc.perform(put("/api/service-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceDelivery in the database
        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeUpdate);
        ServiceDelivery testServiceDelivery = serviceDeliveryList.get(serviceDeliveryList.size() - 1);
        assertThat(testServiceDelivery.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceDelivery.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceDelivery.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceDelivery.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceDelivery.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceDelivery.getServiceDeliveryChannelType()).isEqualTo(UPDATED_SERVICE_DELIVERY_CHANNEL_TYPE);
        assertThat(testServiceDelivery.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceDelivery() throws Exception {
        int databaseSizeBeforeUpdate = serviceDeliveryRepository.findAll().size();

        // Create the ServiceDelivery
        ServiceDeliveryDTO serviceDeliveryDTO = serviceDeliveryMapper.toDto(serviceDelivery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceDeliveryMockMvc.perform(put("/api/service-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDelivery in the database
        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceDelivery() throws Exception {
        // Initialize the database
        serviceDeliveryRepository.saveAndFlush(serviceDelivery);

        int databaseSizeBeforeDelete = serviceDeliveryRepository.findAll().size();

        // Delete the serviceDelivery
        restServiceDeliveryMockMvc.perform(delete("/api/service-deliveries/{id}", serviceDelivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceDelivery> serviceDeliveryList = serviceDeliveryRepository.findAll();
        assertThat(serviceDeliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDelivery.class);
        ServiceDelivery serviceDelivery1 = new ServiceDelivery();
        serviceDelivery1.setId(1L);
        ServiceDelivery serviceDelivery2 = new ServiceDelivery();
        serviceDelivery2.setId(serviceDelivery1.getId());
        assertThat(serviceDelivery1).isEqualTo(serviceDelivery2);
        serviceDelivery2.setId(2L);
        assertThat(serviceDelivery1).isNotEqualTo(serviceDelivery2);
        serviceDelivery1.setId(null);
        assertThat(serviceDelivery1).isNotEqualTo(serviceDelivery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDeliveryDTO.class);
        ServiceDeliveryDTO serviceDeliveryDTO1 = new ServiceDeliveryDTO();
        serviceDeliveryDTO1.setId(1L);
        ServiceDeliveryDTO serviceDeliveryDTO2 = new ServiceDeliveryDTO();
        assertThat(serviceDeliveryDTO1).isNotEqualTo(serviceDeliveryDTO2);
        serviceDeliveryDTO2.setId(serviceDeliveryDTO1.getId());
        assertThat(serviceDeliveryDTO1).isEqualTo(serviceDeliveryDTO2);
        serviceDeliveryDTO2.setId(2L);
        assertThat(serviceDeliveryDTO1).isNotEqualTo(serviceDeliveryDTO2);
        serviceDeliveryDTO1.setId(null);
        assertThat(serviceDeliveryDTO1).isNotEqualTo(serviceDeliveryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceDeliveryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceDeliveryMapper.fromId(null)).isNull();
    }
}
