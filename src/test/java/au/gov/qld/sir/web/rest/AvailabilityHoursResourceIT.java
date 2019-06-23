package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.domain.OpeningHoursSpecification;
import au.gov.qld.sir.repository.AvailabilityHoursRepository;
import au.gov.qld.sir.service.AvailabilityHoursService;
import au.gov.qld.sir.service.dto.AvailabilityHoursDTO;
import au.gov.qld.sir.service.mapper.AvailabilityHoursMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.AvailabilityHoursCriteria;
import au.gov.qld.sir.service.AvailabilityHoursQueryService;

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
 * Integration tests for the {@Link AvailabilityHoursResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class AvailabilityHoursResourceIT {

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

    private static final String DEFAULT_AVAILABLE = "A";
    private static final String UPDATED_AVAILABLE = "B";

    private static final Integer DEFAULT_AVAILABILITY_HOURS_ID = 1;
    private static final Integer UPDATED_AVAILABILITY_HOURS_ID = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvailabilityHoursRepository availabilityHoursRepository;

    @Autowired
    private AvailabilityHoursMapper availabilityHoursMapper;

    @Autowired
    private AvailabilityHoursService availabilityHoursService;

    @Autowired
    private AvailabilityHoursQueryService availabilityHoursQueryService;

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

    private MockMvc restAvailabilityHoursMockMvc;

    private AvailabilityHours availabilityHours;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvailabilityHoursResource availabilityHoursResource = new AvailabilityHoursResource(availabilityHoursService, availabilityHoursQueryService);
        this.restAvailabilityHoursMockMvc = MockMvcBuilders.standaloneSetup(availabilityHoursResource)
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
    public static AvailabilityHours createEntity(EntityManager em) {
        AvailabilityHours availabilityHours = new AvailabilityHours()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .available(DEFAULT_AVAILABLE)
            .availabilityHoursId(DEFAULT_AVAILABILITY_HOURS_ID)
            .comments(DEFAULT_COMMENTS)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return availabilityHours;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvailabilityHours createUpdatedEntity(EntityManager em) {
        AvailabilityHours availabilityHours = new AvailabilityHours()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .available(UPDATED_AVAILABLE)
            .availabilityHoursId(UPDATED_AVAILABILITY_HOURS_ID)
            .comments(UPDATED_COMMENTS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return availabilityHours;
    }

    @BeforeEach
    public void initTest() {
        availabilityHours = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvailabilityHours() throws Exception {
        int databaseSizeBeforeCreate = availabilityHoursRepository.findAll().size();

        // Create the AvailabilityHours
        AvailabilityHoursDTO availabilityHoursDTO = availabilityHoursMapper.toDto(availabilityHours);
        restAvailabilityHoursMockMvc.perform(post("/api/availability-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityHoursDTO)))
            .andExpect(status().isCreated());

        // Validate the AvailabilityHours in the database
        List<AvailabilityHours> availabilityHoursList = availabilityHoursRepository.findAll();
        assertThat(availabilityHoursList).hasSize(databaseSizeBeforeCreate + 1);
        AvailabilityHours testAvailabilityHours = availabilityHoursList.get(availabilityHoursList.size() - 1);
        assertThat(testAvailabilityHours.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAvailabilityHours.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testAvailabilityHours.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAvailabilityHours.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testAvailabilityHours.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAvailabilityHours.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testAvailabilityHours.getAvailabilityHoursId()).isEqualTo(DEFAULT_AVAILABILITY_HOURS_ID);
        assertThat(testAvailabilityHours.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAvailabilityHours.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testAvailabilityHours.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createAvailabilityHoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = availabilityHoursRepository.findAll().size();

        // Create the AvailabilityHours with an existing ID
        availabilityHours.setId(1L);
        AvailabilityHoursDTO availabilityHoursDTO = availabilityHoursMapper.toDto(availabilityHours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvailabilityHoursMockMvc.perform(post("/api/availability-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvailabilityHours in the database
        List<AvailabilityHours> availabilityHoursList = availabilityHoursRepository.findAll();
        assertThat(availabilityHoursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvailabilityHours() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availabilityHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.toString())))
            .andExpect(jsonPath("$.[*].availabilityHoursId").value(hasItem(DEFAULT_AVAILABILITY_HOURS_ID)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getAvailabilityHours() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get the availabilityHours
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours/{id}", availabilityHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(availabilityHours.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.toString()))
            .andExpect(jsonPath("$.availabilityHoursId").value(DEFAULT_AVAILABILITY_HOURS_ID))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdBy equals to DEFAULT_CREATED_BY
        defaultAvailabilityHoursShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the availabilityHoursList where createdBy equals to UPDATED_CREATED_BY
        defaultAvailabilityHoursShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAvailabilityHoursShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the availabilityHoursList where createdBy equals to UPDATED_CREATED_BY
        defaultAvailabilityHoursShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdBy is not null
        defaultAvailabilityHoursShouldBeFound("createdBy.specified=true");

        // Get all the availabilityHoursList where createdBy is null
        defaultAvailabilityHoursShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultAvailabilityHoursShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the availabilityHoursList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAvailabilityHoursShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultAvailabilityHoursShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the availabilityHoursList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAvailabilityHoursShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where createdDateTime is not null
        defaultAvailabilityHoursShouldBeFound("createdDateTime.specified=true");

        // Get all the availabilityHoursList where createdDateTime is null
        defaultAvailabilityHoursShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAvailabilityHoursShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the availabilityHoursList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAvailabilityHoursShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAvailabilityHoursShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the availabilityHoursList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAvailabilityHoursShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedBy is not null
        defaultAvailabilityHoursShouldBeFound("modifiedBy.specified=true");

        // Get all the availabilityHoursList where modifiedBy is null
        defaultAvailabilityHoursShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultAvailabilityHoursShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the availabilityHoursList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAvailabilityHoursShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultAvailabilityHoursShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the availabilityHoursList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAvailabilityHoursShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where modifiedDateTime is not null
        defaultAvailabilityHoursShouldBeFound("modifiedDateTime.specified=true");

        // Get all the availabilityHoursList where modifiedDateTime is null
        defaultAvailabilityHoursShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where version equals to DEFAULT_VERSION
        defaultAvailabilityHoursShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the availabilityHoursList where version equals to UPDATED_VERSION
        defaultAvailabilityHoursShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAvailabilityHoursShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the availabilityHoursList where version equals to UPDATED_VERSION
        defaultAvailabilityHoursShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where version is not null
        defaultAvailabilityHoursShouldBeFound("version.specified=true");

        // Get all the availabilityHoursList where version is null
        defaultAvailabilityHoursShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where version greater than or equals to DEFAULT_VERSION
        defaultAvailabilityHoursShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the availabilityHoursList where version greater than or equals to UPDATED_VERSION
        defaultAvailabilityHoursShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where version less than or equals to DEFAULT_VERSION
        defaultAvailabilityHoursShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the availabilityHoursList where version less than or equals to UPDATED_VERSION
        defaultAvailabilityHoursShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where available equals to DEFAULT_AVAILABLE
        defaultAvailabilityHoursShouldBeFound("available.equals=" + DEFAULT_AVAILABLE);

        // Get all the availabilityHoursList where available equals to UPDATED_AVAILABLE
        defaultAvailabilityHoursShouldNotBeFound("available.equals=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where available in DEFAULT_AVAILABLE or UPDATED_AVAILABLE
        defaultAvailabilityHoursShouldBeFound("available.in=" + DEFAULT_AVAILABLE + "," + UPDATED_AVAILABLE);

        // Get all the availabilityHoursList where available equals to UPDATED_AVAILABLE
        defaultAvailabilityHoursShouldNotBeFound("available.in=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where available is not null
        defaultAvailabilityHoursShouldBeFound("available.specified=true");

        // Get all the availabilityHoursList where available is null
        defaultAvailabilityHoursShouldNotBeFound("available.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailabilityHoursIdIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where availabilityHoursId equals to DEFAULT_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldBeFound("availabilityHoursId.equals=" + DEFAULT_AVAILABILITY_HOURS_ID);

        // Get all the availabilityHoursList where availabilityHoursId equals to UPDATED_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldNotBeFound("availabilityHoursId.equals=" + UPDATED_AVAILABILITY_HOURS_ID);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailabilityHoursIdIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where availabilityHoursId in DEFAULT_AVAILABILITY_HOURS_ID or UPDATED_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldBeFound("availabilityHoursId.in=" + DEFAULT_AVAILABILITY_HOURS_ID + "," + UPDATED_AVAILABILITY_HOURS_ID);

        // Get all the availabilityHoursList where availabilityHoursId equals to UPDATED_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldNotBeFound("availabilityHoursId.in=" + UPDATED_AVAILABILITY_HOURS_ID);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailabilityHoursIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where availabilityHoursId is not null
        defaultAvailabilityHoursShouldBeFound("availabilityHoursId.specified=true");

        // Get all the availabilityHoursList where availabilityHoursId is null
        defaultAvailabilityHoursShouldNotBeFound("availabilityHoursId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailabilityHoursIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where availabilityHoursId greater than or equals to DEFAULT_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldBeFound("availabilityHoursId.greaterOrEqualThan=" + DEFAULT_AVAILABILITY_HOURS_ID);

        // Get all the availabilityHoursList where availabilityHoursId greater than or equals to UPDATED_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldNotBeFound("availabilityHoursId.greaterOrEqualThan=" + UPDATED_AVAILABILITY_HOURS_ID);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByAvailabilityHoursIdIsLessThanSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where availabilityHoursId less than or equals to DEFAULT_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldNotBeFound("availabilityHoursId.lessThan=" + DEFAULT_AVAILABILITY_HOURS_ID);

        // Get all the availabilityHoursList where availabilityHoursId less than or equals to UPDATED_AVAILABILITY_HOURS_ID
        defaultAvailabilityHoursShouldBeFound("availabilityHoursId.lessThan=" + UPDATED_AVAILABILITY_HOURS_ID);
    }


    @Test
    @Transactional
    public void getAllAvailabilityHoursByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where comments equals to DEFAULT_COMMENTS
        defaultAvailabilityHoursShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the availabilityHoursList where comments equals to UPDATED_COMMENTS
        defaultAvailabilityHoursShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultAvailabilityHoursShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the availabilityHoursList where comments equals to UPDATED_COMMENTS
        defaultAvailabilityHoursShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where comments is not null
        defaultAvailabilityHoursShouldBeFound("comments.specified=true");

        // Get all the availabilityHoursList where comments is null
        defaultAvailabilityHoursShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validFrom equals to DEFAULT_VALID_FROM
        defaultAvailabilityHoursShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the availabilityHoursList where validFrom equals to UPDATED_VALID_FROM
        defaultAvailabilityHoursShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultAvailabilityHoursShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the availabilityHoursList where validFrom equals to UPDATED_VALID_FROM
        defaultAvailabilityHoursShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validFrom is not null
        defaultAvailabilityHoursShouldBeFound("validFrom.specified=true");

        // Get all the availabilityHoursList where validFrom is null
        defaultAvailabilityHoursShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validTo equals to DEFAULT_VALID_TO
        defaultAvailabilityHoursShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the availabilityHoursList where validTo equals to UPDATED_VALID_TO
        defaultAvailabilityHoursShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultAvailabilityHoursShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the availabilityHoursList where validTo equals to UPDATED_VALID_TO
        defaultAvailabilityHoursShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        // Get all the availabilityHoursList where validTo is not null
        defaultAvailabilityHoursShouldBeFound("validTo.specified=true");

        // Get all the availabilityHoursList where validTo is null
        defaultAvailabilityHoursShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvailabilityHoursByDeliveryChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        DeliveryChannel deliveryChannel = DeliveryChannelResourceIT.createEntity(em);
        em.persist(deliveryChannel);
        em.flush();
        availabilityHours.addDeliveryChannel(deliveryChannel);
        availabilityHoursRepository.saveAndFlush(availabilityHours);
        Long deliveryChannelId = deliveryChannel.getId();

        // Get all the availabilityHoursList where deliveryChannel equals to deliveryChannelId
        defaultAvailabilityHoursShouldBeFound("deliveryChannelId.equals=" + deliveryChannelId);

        // Get all the availabilityHoursList where deliveryChannel equals to deliveryChannelId + 1
        defaultAvailabilityHoursShouldNotBeFound("deliveryChannelId.equals=" + (deliveryChannelId + 1));
    }


    @Test
    @Transactional
    public void getAllAvailabilityHoursByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        availabilityHours.addLocation(location);
        availabilityHoursRepository.saveAndFlush(availabilityHours);
        Long locationId = location.getId();

        // Get all the availabilityHoursList where location equals to locationId
        defaultAvailabilityHoursShouldBeFound("locationId.equals=" + locationId);

        // Get all the availabilityHoursList where location equals to locationId + 1
        defaultAvailabilityHoursShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllAvailabilityHoursByOpeningHoursSpecificationIsEqualToSomething() throws Exception {
        // Initialize the database
        OpeningHoursSpecification openingHoursSpecification = OpeningHoursSpecificationResourceIT.createEntity(em);
        em.persist(openingHoursSpecification);
        em.flush();
        availabilityHours.addOpeningHoursSpecification(openingHoursSpecification);
        availabilityHoursRepository.saveAndFlush(availabilityHours);
        Long openingHoursSpecificationId = openingHoursSpecification.getId();

        // Get all the availabilityHoursList where openingHoursSpecification equals to openingHoursSpecificationId
        defaultAvailabilityHoursShouldBeFound("openingHoursSpecificationId.equals=" + openingHoursSpecificationId);

        // Get all the availabilityHoursList where openingHoursSpecification equals to openingHoursSpecificationId + 1
        defaultAvailabilityHoursShouldNotBeFound("openingHoursSpecificationId.equals=" + (openingHoursSpecificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAvailabilityHoursShouldBeFound(String filter) throws Exception {
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availabilityHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)))
            .andExpect(jsonPath("$.[*].availabilityHoursId").value(hasItem(DEFAULT_AVAILABILITY_HOURS_ID)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAvailabilityHoursShouldNotBeFound(String filter) throws Exception {
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAvailabilityHours() throws Exception {
        // Get the availabilityHours
        restAvailabilityHoursMockMvc.perform(get("/api/availability-hours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvailabilityHours() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        int databaseSizeBeforeUpdate = availabilityHoursRepository.findAll().size();

        // Update the availabilityHours
        AvailabilityHours updatedAvailabilityHours = availabilityHoursRepository.findById(availabilityHours.getId()).get();
        // Disconnect from session so that the updates on updatedAvailabilityHours are not directly saved in db
        em.detach(updatedAvailabilityHours);
        updatedAvailabilityHours
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .available(UPDATED_AVAILABLE)
            .availabilityHoursId(UPDATED_AVAILABILITY_HOURS_ID)
            .comments(UPDATED_COMMENTS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        AvailabilityHoursDTO availabilityHoursDTO = availabilityHoursMapper.toDto(updatedAvailabilityHours);

        restAvailabilityHoursMockMvc.perform(put("/api/availability-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityHoursDTO)))
            .andExpect(status().isOk());

        // Validate the AvailabilityHours in the database
        List<AvailabilityHours> availabilityHoursList = availabilityHoursRepository.findAll();
        assertThat(availabilityHoursList).hasSize(databaseSizeBeforeUpdate);
        AvailabilityHours testAvailabilityHours = availabilityHoursList.get(availabilityHoursList.size() - 1);
        assertThat(testAvailabilityHours.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAvailabilityHours.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testAvailabilityHours.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAvailabilityHours.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testAvailabilityHours.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAvailabilityHours.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testAvailabilityHours.getAvailabilityHoursId()).isEqualTo(UPDATED_AVAILABILITY_HOURS_ID);
        assertThat(testAvailabilityHours.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAvailabilityHours.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testAvailabilityHours.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingAvailabilityHours() throws Exception {
        int databaseSizeBeforeUpdate = availabilityHoursRepository.findAll().size();

        // Create the AvailabilityHours
        AvailabilityHoursDTO availabilityHoursDTO = availabilityHoursMapper.toDto(availabilityHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvailabilityHoursMockMvc.perform(put("/api/availability-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvailabilityHours in the database
        List<AvailabilityHours> availabilityHoursList = availabilityHoursRepository.findAll();
        assertThat(availabilityHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvailabilityHours() throws Exception {
        // Initialize the database
        availabilityHoursRepository.saveAndFlush(availabilityHours);

        int databaseSizeBeforeDelete = availabilityHoursRepository.findAll().size();

        // Delete the availabilityHours
        restAvailabilityHoursMockMvc.perform(delete("/api/availability-hours/{id}", availabilityHours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvailabilityHours> availabilityHoursList = availabilityHoursRepository.findAll();
        assertThat(availabilityHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvailabilityHours.class);
        AvailabilityHours availabilityHours1 = new AvailabilityHours();
        availabilityHours1.setId(1L);
        AvailabilityHours availabilityHours2 = new AvailabilityHours();
        availabilityHours2.setId(availabilityHours1.getId());
        assertThat(availabilityHours1).isEqualTo(availabilityHours2);
        availabilityHours2.setId(2L);
        assertThat(availabilityHours1).isNotEqualTo(availabilityHours2);
        availabilityHours1.setId(null);
        assertThat(availabilityHours1).isNotEqualTo(availabilityHours2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvailabilityHoursDTO.class);
        AvailabilityHoursDTO availabilityHoursDTO1 = new AvailabilityHoursDTO();
        availabilityHoursDTO1.setId(1L);
        AvailabilityHoursDTO availabilityHoursDTO2 = new AvailabilityHoursDTO();
        assertThat(availabilityHoursDTO1).isNotEqualTo(availabilityHoursDTO2);
        availabilityHoursDTO2.setId(availabilityHoursDTO1.getId());
        assertThat(availabilityHoursDTO1).isEqualTo(availabilityHoursDTO2);
        availabilityHoursDTO2.setId(2L);
        assertThat(availabilityHoursDTO1).isNotEqualTo(availabilityHoursDTO2);
        availabilityHoursDTO1.setId(null);
        assertThat(availabilityHoursDTO1).isNotEqualTo(availabilityHoursDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(availabilityHoursMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(availabilityHoursMapper.fromId(null)).isNull();
    }
}
