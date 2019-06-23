package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.domain.ServiceDelivery;
import au.gov.qld.sir.repository.DeliveryChannelRepository;
import au.gov.qld.sir.service.DeliveryChannelService;
import au.gov.qld.sir.service.dto.DeliveryChannelDTO;
import au.gov.qld.sir.service.mapper.DeliveryChannelMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.DeliveryChannelCriteria;
import au.gov.qld.sir.service.DeliveryChannelQueryService;

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
 * Integration tests for the {@Link DeliveryChannelResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class DeliveryChannelResourceIT {

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

    private static final String DEFAULT_ADDITIONAL_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_CHANNEL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_CHANNEL_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELIVERY_CHANNEL_ID = 1;
    private static final Integer UPDATED_DELIVERY_CHANNEL_ID = 2;

    private static final String DEFAULT_VIRTUAL_DELIVERY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_VIRTUAL_DELIVERY_DETAILS = "BBBBBBBBBB";

    @Autowired
    private DeliveryChannelRepository deliveryChannelRepository;

    @Autowired
    private DeliveryChannelMapper deliveryChannelMapper;

    @Autowired
    private DeliveryChannelService deliveryChannelService;

    @Autowired
    private DeliveryChannelQueryService deliveryChannelQueryService;

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

    private MockMvc restDeliveryChannelMockMvc;

    private DeliveryChannel deliveryChannel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryChannelResource deliveryChannelResource = new DeliveryChannelResource(deliveryChannelService, deliveryChannelQueryService);
        this.restDeliveryChannelMockMvc = MockMvcBuilders.standaloneSetup(deliveryChannelResource)
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
    public static DeliveryChannel createEntity(EntityManager em) {
        DeliveryChannel deliveryChannel = new DeliveryChannel()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .additionalDetails(DEFAULT_ADDITIONAL_DETAILS)
            .deliveryChannelType(DEFAULT_DELIVERY_CHANNEL_TYPE)
            .deliveryChannelId(DEFAULT_DELIVERY_CHANNEL_ID)
            .virtualDeliveryDetails(DEFAULT_VIRTUAL_DELIVERY_DETAILS);
        return deliveryChannel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryChannel createUpdatedEntity(EntityManager em) {
        DeliveryChannel deliveryChannel = new DeliveryChannel()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .additionalDetails(UPDATED_ADDITIONAL_DETAILS)
            .deliveryChannelType(UPDATED_DELIVERY_CHANNEL_TYPE)
            .deliveryChannelId(UPDATED_DELIVERY_CHANNEL_ID)
            .virtualDeliveryDetails(UPDATED_VIRTUAL_DELIVERY_DETAILS);
        return deliveryChannel;
    }

    @BeforeEach
    public void initTest() {
        deliveryChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryChannel() throws Exception {
        int databaseSizeBeforeCreate = deliveryChannelRepository.findAll().size();

        // Create the DeliveryChannel
        DeliveryChannelDTO deliveryChannelDTO = deliveryChannelMapper.toDto(deliveryChannel);
        restDeliveryChannelMockMvc.perform(post("/api/delivery-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryChannel in the database
        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryChannel testDeliveryChannel = deliveryChannelList.get(deliveryChannelList.size() - 1);
        assertThat(testDeliveryChannel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeliveryChannel.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testDeliveryChannel.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDeliveryChannel.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testDeliveryChannel.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDeliveryChannel.getAdditionalDetails()).isEqualTo(DEFAULT_ADDITIONAL_DETAILS);
        assertThat(testDeliveryChannel.getDeliveryChannelType()).isEqualTo(DEFAULT_DELIVERY_CHANNEL_TYPE);
        assertThat(testDeliveryChannel.getDeliveryChannelId()).isEqualTo(DEFAULT_DELIVERY_CHANNEL_ID);
        assertThat(testDeliveryChannel.getVirtualDeliveryDetails()).isEqualTo(DEFAULT_VIRTUAL_DELIVERY_DETAILS);
    }

    @Test
    @Transactional
    public void createDeliveryChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryChannelRepository.findAll().size();

        // Create the DeliveryChannel with an existing ID
        deliveryChannel.setId(1L);
        DeliveryChannelDTO deliveryChannelDTO = deliveryChannelMapper.toDto(deliveryChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryChannelMockMvc.perform(post("/api/delivery-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryChannel in the database
        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDeliveryChannelTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryChannelRepository.findAll().size();
        // set the field null
        deliveryChannel.setDeliveryChannelType(null);

        // Create the DeliveryChannel, which fails.
        DeliveryChannelDTO deliveryChannelDTO = deliveryChannelMapper.toDto(deliveryChannel);

        restDeliveryChannelMockMvc.perform(post("/api/delivery-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryChannelDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannels() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].additionalDetails").value(hasItem(DEFAULT_ADDITIONAL_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].deliveryChannelType").value(hasItem(DEFAULT_DELIVERY_CHANNEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deliveryChannelId").value(hasItem(DEFAULT_DELIVERY_CHANNEL_ID)))
            .andExpect(jsonPath("$.[*].virtualDeliveryDetails").value(hasItem(DEFAULT_VIRTUAL_DELIVERY_DETAILS.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryChannel() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get the deliveryChannel
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels/{id}", deliveryChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryChannel.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.additionalDetails").value(DEFAULT_ADDITIONAL_DETAILS.toString()))
            .andExpect(jsonPath("$.deliveryChannelType").value(DEFAULT_DELIVERY_CHANNEL_TYPE.toString()))
            .andExpect(jsonPath("$.deliveryChannelId").value(DEFAULT_DELIVERY_CHANNEL_ID))
            .andExpect(jsonPath("$.virtualDeliveryDetails").value(DEFAULT_VIRTUAL_DELIVERY_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdBy equals to DEFAULT_CREATED_BY
        defaultDeliveryChannelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the deliveryChannelList where createdBy equals to UPDATED_CREATED_BY
        defaultDeliveryChannelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDeliveryChannelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the deliveryChannelList where createdBy equals to UPDATED_CREATED_BY
        defaultDeliveryChannelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdBy is not null
        defaultDeliveryChannelShouldBeFound("createdBy.specified=true");

        // Get all the deliveryChannelList where createdBy is null
        defaultDeliveryChannelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultDeliveryChannelShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the deliveryChannelList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultDeliveryChannelShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultDeliveryChannelShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the deliveryChannelList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultDeliveryChannelShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where createdDateTime is not null
        defaultDeliveryChannelShouldBeFound("createdDateTime.specified=true");

        // Get all the deliveryChannelList where createdDateTime is null
        defaultDeliveryChannelShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDeliveryChannelShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the deliveryChannelList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDeliveryChannelShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDeliveryChannelShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the deliveryChannelList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDeliveryChannelShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedBy is not null
        defaultDeliveryChannelShouldBeFound("modifiedBy.specified=true");

        // Get all the deliveryChannelList where modifiedBy is null
        defaultDeliveryChannelShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultDeliveryChannelShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the deliveryChannelList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultDeliveryChannelShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultDeliveryChannelShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the deliveryChannelList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultDeliveryChannelShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where modifiedDateTime is not null
        defaultDeliveryChannelShouldBeFound("modifiedDateTime.specified=true");

        // Get all the deliveryChannelList where modifiedDateTime is null
        defaultDeliveryChannelShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where version equals to DEFAULT_VERSION
        defaultDeliveryChannelShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the deliveryChannelList where version equals to UPDATED_VERSION
        defaultDeliveryChannelShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultDeliveryChannelShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the deliveryChannelList where version equals to UPDATED_VERSION
        defaultDeliveryChannelShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where version is not null
        defaultDeliveryChannelShouldBeFound("version.specified=true");

        // Get all the deliveryChannelList where version is null
        defaultDeliveryChannelShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where version greater than or equals to DEFAULT_VERSION
        defaultDeliveryChannelShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the deliveryChannelList where version greater than or equals to UPDATED_VERSION
        defaultDeliveryChannelShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where version less than or equals to DEFAULT_VERSION
        defaultDeliveryChannelShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the deliveryChannelList where version less than or equals to UPDATED_VERSION
        defaultDeliveryChannelShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllDeliveryChannelsByAdditionalDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where additionalDetails equals to DEFAULT_ADDITIONAL_DETAILS
        defaultDeliveryChannelShouldBeFound("additionalDetails.equals=" + DEFAULT_ADDITIONAL_DETAILS);

        // Get all the deliveryChannelList where additionalDetails equals to UPDATED_ADDITIONAL_DETAILS
        defaultDeliveryChannelShouldNotBeFound("additionalDetails.equals=" + UPDATED_ADDITIONAL_DETAILS);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByAdditionalDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where additionalDetails in DEFAULT_ADDITIONAL_DETAILS or UPDATED_ADDITIONAL_DETAILS
        defaultDeliveryChannelShouldBeFound("additionalDetails.in=" + DEFAULT_ADDITIONAL_DETAILS + "," + UPDATED_ADDITIONAL_DETAILS);

        // Get all the deliveryChannelList where additionalDetails equals to UPDATED_ADDITIONAL_DETAILS
        defaultDeliveryChannelShouldNotBeFound("additionalDetails.in=" + UPDATED_ADDITIONAL_DETAILS);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByAdditionalDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where additionalDetails is not null
        defaultDeliveryChannelShouldBeFound("additionalDetails.specified=true");

        // Get all the deliveryChannelList where additionalDetails is null
        defaultDeliveryChannelShouldNotBeFound("additionalDetails.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelType equals to DEFAULT_DELIVERY_CHANNEL_TYPE
        defaultDeliveryChannelShouldBeFound("deliveryChannelType.equals=" + DEFAULT_DELIVERY_CHANNEL_TYPE);

        // Get all the deliveryChannelList where deliveryChannelType equals to UPDATED_DELIVERY_CHANNEL_TYPE
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelType.equals=" + UPDATED_DELIVERY_CHANNEL_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelType in DEFAULT_DELIVERY_CHANNEL_TYPE or UPDATED_DELIVERY_CHANNEL_TYPE
        defaultDeliveryChannelShouldBeFound("deliveryChannelType.in=" + DEFAULT_DELIVERY_CHANNEL_TYPE + "," + UPDATED_DELIVERY_CHANNEL_TYPE);

        // Get all the deliveryChannelList where deliveryChannelType equals to UPDATED_DELIVERY_CHANNEL_TYPE
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelType.in=" + UPDATED_DELIVERY_CHANNEL_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelType is not null
        defaultDeliveryChannelShouldBeFound("deliveryChannelType.specified=true");

        // Get all the deliveryChannelList where deliveryChannelType is null
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelId equals to DEFAULT_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldBeFound("deliveryChannelId.equals=" + DEFAULT_DELIVERY_CHANNEL_ID);

        // Get all the deliveryChannelList where deliveryChannelId equals to UPDATED_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelId.equals=" + UPDATED_DELIVERY_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelId in DEFAULT_DELIVERY_CHANNEL_ID or UPDATED_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldBeFound("deliveryChannelId.in=" + DEFAULT_DELIVERY_CHANNEL_ID + "," + UPDATED_DELIVERY_CHANNEL_ID);

        // Get all the deliveryChannelList where deliveryChannelId equals to UPDATED_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelId.in=" + UPDATED_DELIVERY_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelId is not null
        defaultDeliveryChannelShouldBeFound("deliveryChannelId.specified=true");

        // Get all the deliveryChannelList where deliveryChannelId is null
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelId greater than or equals to DEFAULT_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldBeFound("deliveryChannelId.greaterOrEqualThan=" + DEFAULT_DELIVERY_CHANNEL_ID);

        // Get all the deliveryChannelList where deliveryChannelId greater than or equals to UPDATED_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelId.greaterOrEqualThan=" + UPDATED_DELIVERY_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryChannelIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where deliveryChannelId less than or equals to DEFAULT_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldNotBeFound("deliveryChannelId.lessThan=" + DEFAULT_DELIVERY_CHANNEL_ID);

        // Get all the deliveryChannelList where deliveryChannelId less than or equals to UPDATED_DELIVERY_CHANNEL_ID
        defaultDeliveryChannelShouldBeFound("deliveryChannelId.lessThan=" + UPDATED_DELIVERY_CHANNEL_ID);
    }


    @Test
    @Transactional
    public void getAllDeliveryChannelsByVirtualDeliveryDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where virtualDeliveryDetails equals to DEFAULT_VIRTUAL_DELIVERY_DETAILS
        defaultDeliveryChannelShouldBeFound("virtualDeliveryDetails.equals=" + DEFAULT_VIRTUAL_DELIVERY_DETAILS);

        // Get all the deliveryChannelList where virtualDeliveryDetails equals to UPDATED_VIRTUAL_DELIVERY_DETAILS
        defaultDeliveryChannelShouldNotBeFound("virtualDeliveryDetails.equals=" + UPDATED_VIRTUAL_DELIVERY_DETAILS);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVirtualDeliveryDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where virtualDeliveryDetails in DEFAULT_VIRTUAL_DELIVERY_DETAILS or UPDATED_VIRTUAL_DELIVERY_DETAILS
        defaultDeliveryChannelShouldBeFound("virtualDeliveryDetails.in=" + DEFAULT_VIRTUAL_DELIVERY_DETAILS + "," + UPDATED_VIRTUAL_DELIVERY_DETAILS);

        // Get all the deliveryChannelList where virtualDeliveryDetails equals to UPDATED_VIRTUAL_DELIVERY_DETAILS
        defaultDeliveryChannelShouldNotBeFound("virtualDeliveryDetails.in=" + UPDATED_VIRTUAL_DELIVERY_DETAILS);
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByVirtualDeliveryDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        // Get all the deliveryChannelList where virtualDeliveryDetails is not null
        defaultDeliveryChannelShouldBeFound("virtualDeliveryDetails.specified=true");

        // Get all the deliveryChannelList where virtualDeliveryDetails is null
        defaultDeliveryChannelShouldNotBeFound("virtualDeliveryDetails.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryChannelsByDeliveryHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        AvailabilityHours deliveryHours = AvailabilityHoursResourceIT.createEntity(em);
        em.persist(deliveryHours);
        em.flush();
        deliveryChannel.setDeliveryHours(deliveryHours);
        deliveryChannelRepository.saveAndFlush(deliveryChannel);
        Long deliveryHoursId = deliveryHours.getId();

        // Get all the deliveryChannelList where deliveryHours equals to deliveryHoursId
        defaultDeliveryChannelShouldBeFound("deliveryHoursId.equals=" + deliveryHoursId);

        // Get all the deliveryChannelList where deliveryHours equals to deliveryHoursId + 1
        defaultDeliveryChannelShouldNotBeFound("deliveryHoursId.equals=" + (deliveryHoursId + 1));
    }


    @Test
    @Transactional
    public void getAllDeliveryChannelsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        deliveryChannel.setLocation(location);
        deliveryChannelRepository.saveAndFlush(deliveryChannel);
        Long locationId = location.getId();

        // Get all the deliveryChannelList where location equals to locationId
        defaultDeliveryChannelShouldBeFound("locationId.equals=" + locationId);

        // Get all the deliveryChannelList where location equals to locationId + 1
        defaultDeliveryChannelShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllDeliveryChannelsByServiceDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDelivery serviceDelivery = ServiceDeliveryResourceIT.createEntity(em);
        em.persist(serviceDelivery);
        em.flush();
        deliveryChannel.setServiceDelivery(serviceDelivery);
        deliveryChannelRepository.saveAndFlush(deliveryChannel);
        Long serviceDeliveryId = serviceDelivery.getId();

        // Get all the deliveryChannelList where serviceDelivery equals to serviceDeliveryId
        defaultDeliveryChannelShouldBeFound("serviceDeliveryId.equals=" + serviceDeliveryId);

        // Get all the deliveryChannelList where serviceDelivery equals to serviceDeliveryId + 1
        defaultDeliveryChannelShouldNotBeFound("serviceDeliveryId.equals=" + (serviceDeliveryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryChannelShouldBeFound(String filter) throws Exception {
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].additionalDetails").value(hasItem(DEFAULT_ADDITIONAL_DETAILS)))
            .andExpect(jsonPath("$.[*].deliveryChannelType").value(hasItem(DEFAULT_DELIVERY_CHANNEL_TYPE)))
            .andExpect(jsonPath("$.[*].deliveryChannelId").value(hasItem(DEFAULT_DELIVERY_CHANNEL_ID)))
            .andExpect(jsonPath("$.[*].virtualDeliveryDetails").value(hasItem(DEFAULT_VIRTUAL_DELIVERY_DETAILS)));

        // Check, that the count call also returns 1
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryChannelShouldNotBeFound(String filter) throws Exception {
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeliveryChannel() throws Exception {
        // Get the deliveryChannel
        restDeliveryChannelMockMvc.perform(get("/api/delivery-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryChannel() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        int databaseSizeBeforeUpdate = deliveryChannelRepository.findAll().size();

        // Update the deliveryChannel
        DeliveryChannel updatedDeliveryChannel = deliveryChannelRepository.findById(deliveryChannel.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryChannel are not directly saved in db
        em.detach(updatedDeliveryChannel);
        updatedDeliveryChannel
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .additionalDetails(UPDATED_ADDITIONAL_DETAILS)
            .deliveryChannelType(UPDATED_DELIVERY_CHANNEL_TYPE)
            .deliveryChannelId(UPDATED_DELIVERY_CHANNEL_ID)
            .virtualDeliveryDetails(UPDATED_VIRTUAL_DELIVERY_DETAILS);
        DeliveryChannelDTO deliveryChannelDTO = deliveryChannelMapper.toDto(updatedDeliveryChannel);

        restDeliveryChannelMockMvc.perform(put("/api/delivery-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryChannelDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryChannel in the database
        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeUpdate);
        DeliveryChannel testDeliveryChannel = deliveryChannelList.get(deliveryChannelList.size() - 1);
        assertThat(testDeliveryChannel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeliveryChannel.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testDeliveryChannel.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDeliveryChannel.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testDeliveryChannel.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDeliveryChannel.getAdditionalDetails()).isEqualTo(UPDATED_ADDITIONAL_DETAILS);
        assertThat(testDeliveryChannel.getDeliveryChannelType()).isEqualTo(UPDATED_DELIVERY_CHANNEL_TYPE);
        assertThat(testDeliveryChannel.getDeliveryChannelId()).isEqualTo(UPDATED_DELIVERY_CHANNEL_ID);
        assertThat(testDeliveryChannel.getVirtualDeliveryDetails()).isEqualTo(UPDATED_VIRTUAL_DELIVERY_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryChannel() throws Exception {
        int databaseSizeBeforeUpdate = deliveryChannelRepository.findAll().size();

        // Create the DeliveryChannel
        DeliveryChannelDTO deliveryChannelDTO = deliveryChannelMapper.toDto(deliveryChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryChannelMockMvc.perform(put("/api/delivery-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryChannel in the database
        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryChannel() throws Exception {
        // Initialize the database
        deliveryChannelRepository.saveAndFlush(deliveryChannel);

        int databaseSizeBeforeDelete = deliveryChannelRepository.findAll().size();

        // Delete the deliveryChannel
        restDeliveryChannelMockMvc.perform(delete("/api/delivery-channels/{id}", deliveryChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryChannel> deliveryChannelList = deliveryChannelRepository.findAll();
        assertThat(deliveryChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryChannel.class);
        DeliveryChannel deliveryChannel1 = new DeliveryChannel();
        deliveryChannel1.setId(1L);
        DeliveryChannel deliveryChannel2 = new DeliveryChannel();
        deliveryChannel2.setId(deliveryChannel1.getId());
        assertThat(deliveryChannel1).isEqualTo(deliveryChannel2);
        deliveryChannel2.setId(2L);
        assertThat(deliveryChannel1).isNotEqualTo(deliveryChannel2);
        deliveryChannel1.setId(null);
        assertThat(deliveryChannel1).isNotEqualTo(deliveryChannel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryChannelDTO.class);
        DeliveryChannelDTO deliveryChannelDTO1 = new DeliveryChannelDTO();
        deliveryChannelDTO1.setId(1L);
        DeliveryChannelDTO deliveryChannelDTO2 = new DeliveryChannelDTO();
        assertThat(deliveryChannelDTO1).isNotEqualTo(deliveryChannelDTO2);
        deliveryChannelDTO2.setId(deliveryChannelDTO1.getId());
        assertThat(deliveryChannelDTO1).isEqualTo(deliveryChannelDTO2);
        deliveryChannelDTO2.setId(2L);
        assertThat(deliveryChannelDTO1).isNotEqualTo(deliveryChannelDTO2);
        deliveryChannelDTO1.setId(null);
        assertThat(deliveryChannelDTO1).isNotEqualTo(deliveryChannelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryChannelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryChannelMapper.fromId(null)).isNull();
    }
}
