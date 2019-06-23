package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.LocationPhone;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.repository.LocationPhoneRepository;
import au.gov.qld.sir.service.LocationPhoneService;
import au.gov.qld.sir.service.dto.LocationPhoneDTO;
import au.gov.qld.sir.service.mapper.LocationPhoneMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.LocationPhoneCriteria;
import au.gov.qld.sir.service.LocationPhoneQueryService;

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
 * Integration tests for the {@Link LocationPhoneResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class LocationPhoneResourceIT {

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

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private LocationPhoneRepository locationPhoneRepository;

    @Autowired
    private LocationPhoneMapper locationPhoneMapper;

    @Autowired
    private LocationPhoneService locationPhoneService;

    @Autowired
    private LocationPhoneQueryService locationPhoneQueryService;

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

    private MockMvc restLocationPhoneMockMvc;

    private LocationPhone locationPhone;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationPhoneResource locationPhoneResource = new LocationPhoneResource(locationPhoneService, locationPhoneQueryService);
        this.restLocationPhoneMockMvc = MockMvcBuilders.standaloneSetup(locationPhoneResource)
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
    public static LocationPhone createEntity(EntityManager em) {
        LocationPhone locationPhone = new LocationPhone()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .comment(DEFAULT_COMMENT)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return locationPhone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationPhone createUpdatedEntity(EntityManager em) {
        LocationPhone locationPhone = new LocationPhone()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .comment(UPDATED_COMMENT)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return locationPhone;
    }

    @BeforeEach
    public void initTest() {
        locationPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationPhone() throws Exception {
        int databaseSizeBeforeCreate = locationPhoneRepository.findAll().size();

        // Create the LocationPhone
        LocationPhoneDTO locationPhoneDTO = locationPhoneMapper.toDto(locationPhone);
        restLocationPhoneMockMvc.perform(post("/api/location-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationPhone in the database
        List<LocationPhone> locationPhoneList = locationPhoneRepository.findAll();
        assertThat(locationPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        LocationPhone testLocationPhone = locationPhoneList.get(locationPhoneList.size() - 1);
        assertThat(testLocationPhone.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocationPhone.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testLocationPhone.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLocationPhone.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testLocationPhone.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLocationPhone.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testLocationPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createLocationPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationPhoneRepository.findAll().size();

        // Create the LocationPhone with an existing ID
        locationPhone.setId(1L);
        LocationPhoneDTO locationPhoneDTO = locationPhoneMapper.toDto(locationPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationPhoneMockMvc.perform(post("/api/location-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationPhone in the database
        List<LocationPhone> locationPhoneList = locationPhoneRepository.findAll();
        assertThat(locationPhoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocationPhones() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList
        restLocationPhoneMockMvc.perform(get("/api/location-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getLocationPhone() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get the locationPhone
        restLocationPhoneMockMvc.perform(get("/api/location-phones/{id}", locationPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationPhone.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationPhoneShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationPhoneList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationPhoneShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationPhoneShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationPhoneList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationPhoneShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdBy is not null
        defaultLocationPhoneShouldBeFound("createdBy.specified=true");

        // Get all the locationPhoneList where createdBy is null
        defaultLocationPhoneShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultLocationPhoneShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the locationPhoneList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationPhoneShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultLocationPhoneShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the locationPhoneList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationPhoneShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where createdDateTime is not null
        defaultLocationPhoneShouldBeFound("createdDateTime.specified=true");

        // Get all the locationPhoneList where createdDateTime is null
        defaultLocationPhoneShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLocationPhoneShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the locationPhoneList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationPhoneShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLocationPhoneShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the locationPhoneList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationPhoneShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedBy is not null
        defaultLocationPhoneShouldBeFound("modifiedBy.specified=true");

        // Get all the locationPhoneList where modifiedBy is null
        defaultLocationPhoneShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultLocationPhoneShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the locationPhoneList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationPhoneShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultLocationPhoneShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the locationPhoneList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationPhoneShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where modifiedDateTime is not null
        defaultLocationPhoneShouldBeFound("modifiedDateTime.specified=true");

        // Get all the locationPhoneList where modifiedDateTime is null
        defaultLocationPhoneShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where version equals to DEFAULT_VERSION
        defaultLocationPhoneShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the locationPhoneList where version equals to UPDATED_VERSION
        defaultLocationPhoneShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLocationPhoneShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the locationPhoneList where version equals to UPDATED_VERSION
        defaultLocationPhoneShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where version is not null
        defaultLocationPhoneShouldBeFound("version.specified=true");

        // Get all the locationPhoneList where version is null
        defaultLocationPhoneShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where version greater than or equals to DEFAULT_VERSION
        defaultLocationPhoneShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the locationPhoneList where version greater than or equals to UPDATED_VERSION
        defaultLocationPhoneShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where version less than or equals to DEFAULT_VERSION
        defaultLocationPhoneShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the locationPhoneList where version less than or equals to UPDATED_VERSION
        defaultLocationPhoneShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllLocationPhonesByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where comment equals to DEFAULT_COMMENT
        defaultLocationPhoneShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the locationPhoneList where comment equals to UPDATED_COMMENT
        defaultLocationPhoneShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultLocationPhoneShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the locationPhoneList where comment equals to UPDATED_COMMENT
        defaultLocationPhoneShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where comment is not null
        defaultLocationPhoneShouldBeFound("comment.specified=true");

        // Get all the locationPhoneList where comment is null
        defaultLocationPhoneShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultLocationPhoneShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the locationPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultLocationPhoneShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultLocationPhoneShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the locationPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultLocationPhoneShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        // Get all the locationPhoneList where phoneNumber is not null
        defaultLocationPhoneShouldBeFound("phoneNumber.specified=true");

        // Get all the locationPhoneList where phoneNumber is null
        defaultLocationPhoneShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationPhonesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        locationPhone.setLocation(location);
        locationPhoneRepository.saveAndFlush(locationPhone);
        Long locationId = location.getId();

        // Get all the locationPhoneList where location equals to locationId
        defaultLocationPhoneShouldBeFound("locationId.equals=" + locationId);

        // Get all the locationPhoneList where location equals to locationId + 1
        defaultLocationPhoneShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationPhoneShouldBeFound(String filter) throws Exception {
        restLocationPhoneMockMvc.perform(get("/api/location-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));

        // Check, that the count call also returns 1
        restLocationPhoneMockMvc.perform(get("/api/location-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationPhoneShouldNotBeFound(String filter) throws Exception {
        restLocationPhoneMockMvc.perform(get("/api/location-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationPhoneMockMvc.perform(get("/api/location-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocationPhone() throws Exception {
        // Get the locationPhone
        restLocationPhoneMockMvc.perform(get("/api/location-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationPhone() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        int databaseSizeBeforeUpdate = locationPhoneRepository.findAll().size();

        // Update the locationPhone
        LocationPhone updatedLocationPhone = locationPhoneRepository.findById(locationPhone.getId()).get();
        // Disconnect from session so that the updates on updatedLocationPhone are not directly saved in db
        em.detach(updatedLocationPhone);
        updatedLocationPhone
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .comment(UPDATED_COMMENT)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        LocationPhoneDTO locationPhoneDTO = locationPhoneMapper.toDto(updatedLocationPhone);

        restLocationPhoneMockMvc.perform(put("/api/location-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the LocationPhone in the database
        List<LocationPhone> locationPhoneList = locationPhoneRepository.findAll();
        assertThat(locationPhoneList).hasSize(databaseSizeBeforeUpdate);
        LocationPhone testLocationPhone = locationPhoneList.get(locationPhoneList.size() - 1);
        assertThat(testLocationPhone.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocationPhone.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testLocationPhone.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLocationPhone.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testLocationPhone.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLocationPhone.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testLocationPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationPhone() throws Exception {
        int databaseSizeBeforeUpdate = locationPhoneRepository.findAll().size();

        // Create the LocationPhone
        LocationPhoneDTO locationPhoneDTO = locationPhoneMapper.toDto(locationPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationPhoneMockMvc.perform(put("/api/location-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationPhone in the database
        List<LocationPhone> locationPhoneList = locationPhoneRepository.findAll();
        assertThat(locationPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationPhone() throws Exception {
        // Initialize the database
        locationPhoneRepository.saveAndFlush(locationPhone);

        int databaseSizeBeforeDelete = locationPhoneRepository.findAll().size();

        // Delete the locationPhone
        restLocationPhoneMockMvc.perform(delete("/api/location-phones/{id}", locationPhone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationPhone> locationPhoneList = locationPhoneRepository.findAll();
        assertThat(locationPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationPhone.class);
        LocationPhone locationPhone1 = new LocationPhone();
        locationPhone1.setId(1L);
        LocationPhone locationPhone2 = new LocationPhone();
        locationPhone2.setId(locationPhone1.getId());
        assertThat(locationPhone1).isEqualTo(locationPhone2);
        locationPhone2.setId(2L);
        assertThat(locationPhone1).isNotEqualTo(locationPhone2);
        locationPhone1.setId(null);
        assertThat(locationPhone1).isNotEqualTo(locationPhone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationPhoneDTO.class);
        LocationPhoneDTO locationPhoneDTO1 = new LocationPhoneDTO();
        locationPhoneDTO1.setId(1L);
        LocationPhoneDTO locationPhoneDTO2 = new LocationPhoneDTO();
        assertThat(locationPhoneDTO1).isNotEqualTo(locationPhoneDTO2);
        locationPhoneDTO2.setId(locationPhoneDTO1.getId());
        assertThat(locationPhoneDTO1).isEqualTo(locationPhoneDTO2);
        locationPhoneDTO2.setId(2L);
        assertThat(locationPhoneDTO1).isNotEqualTo(locationPhoneDTO2);
        locationPhoneDTO1.setId(null);
        assertThat(locationPhoneDTO1).isNotEqualTo(locationPhoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationPhoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationPhoneMapper.fromId(null)).isNull();
    }
}
