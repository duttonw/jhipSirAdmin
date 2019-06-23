package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.OpeningHoursSpecification;
import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.repository.OpeningHoursSpecificationRepository;
import au.gov.qld.sir.service.OpeningHoursSpecificationService;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationDTO;
import au.gov.qld.sir.service.mapper.OpeningHoursSpecificationMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.OpeningHoursSpecificationCriteria;
import au.gov.qld.sir.service.OpeningHoursSpecificationQueryService;

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
 * Integration tests for the {@Link OpeningHoursSpecificationResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class OpeningHoursSpecificationResourceIT {

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

    private static final String DEFAULT_CLOSES = "22:43:45";
    private static final String UPDATED_CLOSES = "20:24:56";

    private static final String DEFAULT_DAY_OF_WEEK = "AAAAAAAAAA";
    private static final String UPDATED_DAY_OF_WEEK = "BBBBBBBBBB";

    private static final String DEFAULT_OPENS = "23:17:43";
    private static final String UPDATED_OPENS = "23:30:35";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OpeningHoursSpecificationRepository openingHoursSpecificationRepository;

    @Autowired
    private OpeningHoursSpecificationMapper openingHoursSpecificationMapper;

    @Autowired
    private OpeningHoursSpecificationService openingHoursSpecificationService;

    @Autowired
    private OpeningHoursSpecificationQueryService openingHoursSpecificationQueryService;

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

    private MockMvc restOpeningHoursSpecificationMockMvc;

    private OpeningHoursSpecification openingHoursSpecification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpeningHoursSpecificationResource openingHoursSpecificationResource = new OpeningHoursSpecificationResource(openingHoursSpecificationService, openingHoursSpecificationQueryService);
        this.restOpeningHoursSpecificationMockMvc = MockMvcBuilders.standaloneSetup(openingHoursSpecificationResource)
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
    public static OpeningHoursSpecification createEntity(EntityManager em) {
        OpeningHoursSpecification openingHoursSpecification = new OpeningHoursSpecification()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .closes(DEFAULT_CLOSES)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .opens(DEFAULT_OPENS)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return openingHoursSpecification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpeningHoursSpecification createUpdatedEntity(EntityManager em) {
        OpeningHoursSpecification openingHoursSpecification = new OpeningHoursSpecification()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .closes(UPDATED_CLOSES)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .opens(UPDATED_OPENS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return openingHoursSpecification;
    }

    @BeforeEach
    public void initTest() {
        openingHoursSpecification = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpeningHoursSpecification() throws Exception {
        int databaseSizeBeforeCreate = openingHoursSpecificationRepository.findAll().size();

        // Create the OpeningHoursSpecification
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO = openingHoursSpecificationMapper.toDto(openingHoursSpecification);
        restOpeningHoursSpecificationMockMvc.perform(post("/api/opening-hours-specifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(openingHoursSpecificationDTO)))
            .andExpect(status().isCreated());

        // Validate the OpeningHoursSpecification in the database
        List<OpeningHoursSpecification> openingHoursSpecificationList = openingHoursSpecificationRepository.findAll();
        assertThat(openingHoursSpecificationList).hasSize(databaseSizeBeforeCreate + 1);
        OpeningHoursSpecification testOpeningHoursSpecification = openingHoursSpecificationList.get(openingHoursSpecificationList.size() - 1);
        assertThat(testOpeningHoursSpecification.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOpeningHoursSpecification.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testOpeningHoursSpecification.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testOpeningHoursSpecification.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testOpeningHoursSpecification.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOpeningHoursSpecification.getCloses()).isEqualTo(DEFAULT_CLOSES);
        assertThat(testOpeningHoursSpecification.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testOpeningHoursSpecification.getOpens()).isEqualTo(DEFAULT_OPENS);
        assertThat(testOpeningHoursSpecification.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testOpeningHoursSpecification.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createOpeningHoursSpecificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = openingHoursSpecificationRepository.findAll().size();

        // Create the OpeningHoursSpecification with an existing ID
        openingHoursSpecification.setId(1L);
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO = openingHoursSpecificationMapper.toDto(openingHoursSpecification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpeningHoursSpecificationMockMvc.perform(post("/api/opening-hours-specifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(openingHoursSpecificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OpeningHoursSpecification in the database
        List<OpeningHoursSpecification> openingHoursSpecificationList = openingHoursSpecificationRepository.findAll();
        assertThat(openingHoursSpecificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOpeningHoursSpecifications() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(openingHoursSpecification.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].closes").value(hasItem(DEFAULT_CLOSES.toString())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].opens").value(hasItem(DEFAULT_OPENS.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getOpeningHoursSpecification() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get the openingHoursSpecification
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications/{id}", openingHoursSpecification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(openingHoursSpecification.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.closes").value(DEFAULT_CLOSES.toString()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.opens").value(DEFAULT_OPENS.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdBy equals to DEFAULT_CREATED_BY
        defaultOpeningHoursSpecificationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the openingHoursSpecificationList where createdBy equals to UPDATED_CREATED_BY
        defaultOpeningHoursSpecificationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOpeningHoursSpecificationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the openingHoursSpecificationList where createdBy equals to UPDATED_CREATED_BY
        defaultOpeningHoursSpecificationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdBy is not null
        defaultOpeningHoursSpecificationShouldBeFound("createdBy.specified=true");

        // Get all the openingHoursSpecificationList where createdBy is null
        defaultOpeningHoursSpecificationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultOpeningHoursSpecificationShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the openingHoursSpecificationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultOpeningHoursSpecificationShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultOpeningHoursSpecificationShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the openingHoursSpecificationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultOpeningHoursSpecificationShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where createdDateTime is not null
        defaultOpeningHoursSpecificationShouldBeFound("createdDateTime.specified=true");

        // Get all the openingHoursSpecificationList where createdDateTime is null
        defaultOpeningHoursSpecificationShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultOpeningHoursSpecificationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the openingHoursSpecificationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultOpeningHoursSpecificationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the openingHoursSpecificationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedBy is not null
        defaultOpeningHoursSpecificationShouldBeFound("modifiedBy.specified=true");

        // Get all the openingHoursSpecificationList where modifiedBy is null
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultOpeningHoursSpecificationShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the openingHoursSpecificationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultOpeningHoursSpecificationShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the openingHoursSpecificationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where modifiedDateTime is not null
        defaultOpeningHoursSpecificationShouldBeFound("modifiedDateTime.specified=true");

        // Get all the openingHoursSpecificationList where modifiedDateTime is null
        defaultOpeningHoursSpecificationShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where version equals to DEFAULT_VERSION
        defaultOpeningHoursSpecificationShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the openingHoursSpecificationList where version equals to UPDATED_VERSION
        defaultOpeningHoursSpecificationShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultOpeningHoursSpecificationShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the openingHoursSpecificationList where version equals to UPDATED_VERSION
        defaultOpeningHoursSpecificationShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where version is not null
        defaultOpeningHoursSpecificationShouldBeFound("version.specified=true");

        // Get all the openingHoursSpecificationList where version is null
        defaultOpeningHoursSpecificationShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where version greater than or equals to DEFAULT_VERSION
        defaultOpeningHoursSpecificationShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the openingHoursSpecificationList where version greater than or equals to UPDATED_VERSION
        defaultOpeningHoursSpecificationShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where version less than or equals to DEFAULT_VERSION
        defaultOpeningHoursSpecificationShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the openingHoursSpecificationList where version less than or equals to UPDATED_VERSION
        defaultOpeningHoursSpecificationShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByClosesIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where closes equals to DEFAULT_CLOSES
        defaultOpeningHoursSpecificationShouldBeFound("closes.equals=" + DEFAULT_CLOSES);

        // Get all the openingHoursSpecificationList where closes equals to UPDATED_CLOSES
        defaultOpeningHoursSpecificationShouldNotBeFound("closes.equals=" + UPDATED_CLOSES);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByClosesIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where closes in DEFAULT_CLOSES or UPDATED_CLOSES
        defaultOpeningHoursSpecificationShouldBeFound("closes.in=" + DEFAULT_CLOSES + "," + UPDATED_CLOSES);

        // Get all the openingHoursSpecificationList where closes equals to UPDATED_CLOSES
        defaultOpeningHoursSpecificationShouldNotBeFound("closes.in=" + UPDATED_CLOSES);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByClosesIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where closes is not null
        defaultOpeningHoursSpecificationShouldBeFound("closes.specified=true");

        // Get all the openingHoursSpecificationList where closes is null
        defaultOpeningHoursSpecificationShouldNotBeFound("closes.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByDayOfWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where dayOfWeek equals to DEFAULT_DAY_OF_WEEK
        defaultOpeningHoursSpecificationShouldBeFound("dayOfWeek.equals=" + DEFAULT_DAY_OF_WEEK);

        // Get all the openingHoursSpecificationList where dayOfWeek equals to UPDATED_DAY_OF_WEEK
        defaultOpeningHoursSpecificationShouldNotBeFound("dayOfWeek.equals=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByDayOfWeekIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where dayOfWeek in DEFAULT_DAY_OF_WEEK or UPDATED_DAY_OF_WEEK
        defaultOpeningHoursSpecificationShouldBeFound("dayOfWeek.in=" + DEFAULT_DAY_OF_WEEK + "," + UPDATED_DAY_OF_WEEK);

        // Get all the openingHoursSpecificationList where dayOfWeek equals to UPDATED_DAY_OF_WEEK
        defaultOpeningHoursSpecificationShouldNotBeFound("dayOfWeek.in=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByDayOfWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where dayOfWeek is not null
        defaultOpeningHoursSpecificationShouldBeFound("dayOfWeek.specified=true");

        // Get all the openingHoursSpecificationList where dayOfWeek is null
        defaultOpeningHoursSpecificationShouldNotBeFound("dayOfWeek.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByOpensIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where opens equals to DEFAULT_OPENS
        defaultOpeningHoursSpecificationShouldBeFound("opens.equals=" + DEFAULT_OPENS);

        // Get all the openingHoursSpecificationList where opens equals to UPDATED_OPENS
        defaultOpeningHoursSpecificationShouldNotBeFound("opens.equals=" + UPDATED_OPENS);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByOpensIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where opens in DEFAULT_OPENS or UPDATED_OPENS
        defaultOpeningHoursSpecificationShouldBeFound("opens.in=" + DEFAULT_OPENS + "," + UPDATED_OPENS);

        // Get all the openingHoursSpecificationList where opens equals to UPDATED_OPENS
        defaultOpeningHoursSpecificationShouldNotBeFound("opens.in=" + UPDATED_OPENS);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByOpensIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where opens is not null
        defaultOpeningHoursSpecificationShouldBeFound("opens.specified=true");

        // Get all the openingHoursSpecificationList where opens is null
        defaultOpeningHoursSpecificationShouldNotBeFound("opens.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validFrom equals to DEFAULT_VALID_FROM
        defaultOpeningHoursSpecificationShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the openingHoursSpecificationList where validFrom equals to UPDATED_VALID_FROM
        defaultOpeningHoursSpecificationShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultOpeningHoursSpecificationShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the openingHoursSpecificationList where validFrom equals to UPDATED_VALID_FROM
        defaultOpeningHoursSpecificationShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validFrom is not null
        defaultOpeningHoursSpecificationShouldBeFound("validFrom.specified=true");

        // Get all the openingHoursSpecificationList where validFrom is null
        defaultOpeningHoursSpecificationShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validTo equals to DEFAULT_VALID_TO
        defaultOpeningHoursSpecificationShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the openingHoursSpecificationList where validTo equals to UPDATED_VALID_TO
        defaultOpeningHoursSpecificationShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultOpeningHoursSpecificationShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the openingHoursSpecificationList where validTo equals to UPDATED_VALID_TO
        defaultOpeningHoursSpecificationShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        // Get all the openingHoursSpecificationList where validTo is not null
        defaultOpeningHoursSpecificationShouldBeFound("validTo.specified=true");

        // Get all the openingHoursSpecificationList where validTo is null
        defaultOpeningHoursSpecificationShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpeningHoursSpecificationsByAvailabilityHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        AvailabilityHours availabilityHours = AvailabilityHoursResourceIT.createEntity(em);
        em.persist(availabilityHours);
        em.flush();
        openingHoursSpecification.setAvailabilityHours(availabilityHours);
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);
        Long availabilityHoursId = availabilityHours.getId();

        // Get all the openingHoursSpecificationList where availabilityHours equals to availabilityHoursId
        defaultOpeningHoursSpecificationShouldBeFound("availabilityHoursId.equals=" + availabilityHoursId);

        // Get all the openingHoursSpecificationList where availabilityHours equals to availabilityHoursId + 1
        defaultOpeningHoursSpecificationShouldNotBeFound("availabilityHoursId.equals=" + (availabilityHoursId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpeningHoursSpecificationShouldBeFound(String filter) throws Exception {
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(openingHoursSpecification.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].closes").value(hasItem(DEFAULT_CLOSES)))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].opens").value(hasItem(DEFAULT_OPENS)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpeningHoursSpecificationShouldNotBeFound(String filter) throws Exception {
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOpeningHoursSpecification() throws Exception {
        // Get the openingHoursSpecification
        restOpeningHoursSpecificationMockMvc.perform(get("/api/opening-hours-specifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpeningHoursSpecification() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        int databaseSizeBeforeUpdate = openingHoursSpecificationRepository.findAll().size();

        // Update the openingHoursSpecification
        OpeningHoursSpecification updatedOpeningHoursSpecification = openingHoursSpecificationRepository.findById(openingHoursSpecification.getId()).get();
        // Disconnect from session so that the updates on updatedOpeningHoursSpecification are not directly saved in db
        em.detach(updatedOpeningHoursSpecification);
        updatedOpeningHoursSpecification
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .closes(UPDATED_CLOSES)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .opens(UPDATED_OPENS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO = openingHoursSpecificationMapper.toDto(updatedOpeningHoursSpecification);

        restOpeningHoursSpecificationMockMvc.perform(put("/api/opening-hours-specifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(openingHoursSpecificationDTO)))
            .andExpect(status().isOk());

        // Validate the OpeningHoursSpecification in the database
        List<OpeningHoursSpecification> openingHoursSpecificationList = openingHoursSpecificationRepository.findAll();
        assertThat(openingHoursSpecificationList).hasSize(databaseSizeBeforeUpdate);
        OpeningHoursSpecification testOpeningHoursSpecification = openingHoursSpecificationList.get(openingHoursSpecificationList.size() - 1);
        assertThat(testOpeningHoursSpecification.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOpeningHoursSpecification.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testOpeningHoursSpecification.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOpeningHoursSpecification.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testOpeningHoursSpecification.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOpeningHoursSpecification.getCloses()).isEqualTo(UPDATED_CLOSES);
        assertThat(testOpeningHoursSpecification.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testOpeningHoursSpecification.getOpens()).isEqualTo(UPDATED_OPENS);
        assertThat(testOpeningHoursSpecification.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testOpeningHoursSpecification.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingOpeningHoursSpecification() throws Exception {
        int databaseSizeBeforeUpdate = openingHoursSpecificationRepository.findAll().size();

        // Create the OpeningHoursSpecification
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO = openingHoursSpecificationMapper.toDto(openingHoursSpecification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpeningHoursSpecificationMockMvc.perform(put("/api/opening-hours-specifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(openingHoursSpecificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OpeningHoursSpecification in the database
        List<OpeningHoursSpecification> openingHoursSpecificationList = openingHoursSpecificationRepository.findAll();
        assertThat(openingHoursSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOpeningHoursSpecification() throws Exception {
        // Initialize the database
        openingHoursSpecificationRepository.saveAndFlush(openingHoursSpecification);

        int databaseSizeBeforeDelete = openingHoursSpecificationRepository.findAll().size();

        // Delete the openingHoursSpecification
        restOpeningHoursSpecificationMockMvc.perform(delete("/api/opening-hours-specifications/{id}", openingHoursSpecification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpeningHoursSpecification> openingHoursSpecificationList = openingHoursSpecificationRepository.findAll();
        assertThat(openingHoursSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpeningHoursSpecification.class);
        OpeningHoursSpecification openingHoursSpecification1 = new OpeningHoursSpecification();
        openingHoursSpecification1.setId(1L);
        OpeningHoursSpecification openingHoursSpecification2 = new OpeningHoursSpecification();
        openingHoursSpecification2.setId(openingHoursSpecification1.getId());
        assertThat(openingHoursSpecification1).isEqualTo(openingHoursSpecification2);
        openingHoursSpecification2.setId(2L);
        assertThat(openingHoursSpecification1).isNotEqualTo(openingHoursSpecification2);
        openingHoursSpecification1.setId(null);
        assertThat(openingHoursSpecification1).isNotEqualTo(openingHoursSpecification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpeningHoursSpecificationDTO.class);
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO1 = new OpeningHoursSpecificationDTO();
        openingHoursSpecificationDTO1.setId(1L);
        OpeningHoursSpecificationDTO openingHoursSpecificationDTO2 = new OpeningHoursSpecificationDTO();
        assertThat(openingHoursSpecificationDTO1).isNotEqualTo(openingHoursSpecificationDTO2);
        openingHoursSpecificationDTO2.setId(openingHoursSpecificationDTO1.getId());
        assertThat(openingHoursSpecificationDTO1).isEqualTo(openingHoursSpecificationDTO2);
        openingHoursSpecificationDTO2.setId(2L);
        assertThat(openingHoursSpecificationDTO1).isNotEqualTo(openingHoursSpecificationDTO2);
        openingHoursSpecificationDTO1.setId(null);
        assertThat(openingHoursSpecificationDTO1).isNotEqualTo(openingHoursSpecificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(openingHoursSpecificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(openingHoursSpecificationMapper.fromId(null)).isNull();
    }
}
