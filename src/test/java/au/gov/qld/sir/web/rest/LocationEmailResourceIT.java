package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.LocationEmail;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.repository.LocationEmailRepository;
import au.gov.qld.sir.service.LocationEmailService;
import au.gov.qld.sir.service.dto.LocationEmailDTO;
import au.gov.qld.sir.service.mapper.LocationEmailMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.LocationEmailCriteria;
import au.gov.qld.sir.service.LocationEmailQueryService;

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
 * Integration tests for the {@Link LocationEmailResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class LocationEmailResourceIT {

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

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private LocationEmailRepository locationEmailRepository;

    @Autowired
    private LocationEmailMapper locationEmailMapper;

    @Autowired
    private LocationEmailService locationEmailService;

    @Autowired
    private LocationEmailQueryService locationEmailQueryService;

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

    private MockMvc restLocationEmailMockMvc;

    private LocationEmail locationEmail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationEmailResource locationEmailResource = new LocationEmailResource(locationEmailService, locationEmailQueryService);
        this.restLocationEmailMockMvc = MockMvcBuilders.standaloneSetup(locationEmailResource)
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
    public static LocationEmail createEntity(EntityManager em) {
        LocationEmail locationEmail = new LocationEmail()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .comment(DEFAULT_COMMENT)
            .email(DEFAULT_EMAIL);
        return locationEmail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationEmail createUpdatedEntity(EntityManager em) {
        LocationEmail locationEmail = new LocationEmail()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .comment(UPDATED_COMMENT)
            .email(UPDATED_EMAIL);
        return locationEmail;
    }

    @BeforeEach
    public void initTest() {
        locationEmail = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationEmail() throws Exception {
        int databaseSizeBeforeCreate = locationEmailRepository.findAll().size();

        // Create the LocationEmail
        LocationEmailDTO locationEmailDTO = locationEmailMapper.toDto(locationEmail);
        restLocationEmailMockMvc.perform(post("/api/location-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationEmail in the database
        List<LocationEmail> locationEmailList = locationEmailRepository.findAll();
        assertThat(locationEmailList).hasSize(databaseSizeBeforeCreate + 1);
        LocationEmail testLocationEmail = locationEmailList.get(locationEmailList.size() - 1);
        assertThat(testLocationEmail.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocationEmail.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testLocationEmail.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLocationEmail.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testLocationEmail.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLocationEmail.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testLocationEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createLocationEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationEmailRepository.findAll().size();

        // Create the LocationEmail with an existing ID
        locationEmail.setId(1L);
        LocationEmailDTO locationEmailDTO = locationEmailMapper.toDto(locationEmail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationEmailMockMvc.perform(post("/api/location-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationEmail in the database
        List<LocationEmail> locationEmailList = locationEmailRepository.findAll();
        assertThat(locationEmailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocationEmails() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList
        restLocationEmailMockMvc.perform(get("/api/location-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getLocationEmail() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get the locationEmail
        restLocationEmailMockMvc.perform(get("/api/location-emails/{id}", locationEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationEmail.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationEmailShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationEmailList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationEmailShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationEmailShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationEmailList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationEmailShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdBy is not null
        defaultLocationEmailShouldBeFound("createdBy.specified=true");

        // Get all the locationEmailList where createdBy is null
        defaultLocationEmailShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultLocationEmailShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the locationEmailList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationEmailShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultLocationEmailShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the locationEmailList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationEmailShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where createdDateTime is not null
        defaultLocationEmailShouldBeFound("createdDateTime.specified=true");

        // Get all the locationEmailList where createdDateTime is null
        defaultLocationEmailShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLocationEmailShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the locationEmailList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationEmailShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLocationEmailShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the locationEmailList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationEmailShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedBy is not null
        defaultLocationEmailShouldBeFound("modifiedBy.specified=true");

        // Get all the locationEmailList where modifiedBy is null
        defaultLocationEmailShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultLocationEmailShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the locationEmailList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationEmailShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultLocationEmailShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the locationEmailList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationEmailShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where modifiedDateTime is not null
        defaultLocationEmailShouldBeFound("modifiedDateTime.specified=true");

        // Get all the locationEmailList where modifiedDateTime is null
        defaultLocationEmailShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where version equals to DEFAULT_VERSION
        defaultLocationEmailShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the locationEmailList where version equals to UPDATED_VERSION
        defaultLocationEmailShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLocationEmailShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the locationEmailList where version equals to UPDATED_VERSION
        defaultLocationEmailShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where version is not null
        defaultLocationEmailShouldBeFound("version.specified=true");

        // Get all the locationEmailList where version is null
        defaultLocationEmailShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where version greater than or equals to DEFAULT_VERSION
        defaultLocationEmailShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the locationEmailList where version greater than or equals to UPDATED_VERSION
        defaultLocationEmailShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where version less than or equals to DEFAULT_VERSION
        defaultLocationEmailShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the locationEmailList where version less than or equals to UPDATED_VERSION
        defaultLocationEmailShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllLocationEmailsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where comment equals to DEFAULT_COMMENT
        defaultLocationEmailShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the locationEmailList where comment equals to UPDATED_COMMENT
        defaultLocationEmailShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultLocationEmailShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the locationEmailList where comment equals to UPDATED_COMMENT
        defaultLocationEmailShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where comment is not null
        defaultLocationEmailShouldBeFound("comment.specified=true");

        // Get all the locationEmailList where comment is null
        defaultLocationEmailShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where email equals to DEFAULT_EMAIL
        defaultLocationEmailShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the locationEmailList where email equals to UPDATED_EMAIL
        defaultLocationEmailShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultLocationEmailShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the locationEmailList where email equals to UPDATED_EMAIL
        defaultLocationEmailShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        // Get all the locationEmailList where email is not null
        defaultLocationEmailShouldBeFound("email.specified=true");

        // Get all the locationEmailList where email is null
        defaultLocationEmailShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEmailsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        locationEmail.setLocation(location);
        locationEmailRepository.saveAndFlush(locationEmail);
        Long locationId = location.getId();

        // Get all the locationEmailList where location equals to locationId
        defaultLocationEmailShouldBeFound("locationId.equals=" + locationId);

        // Get all the locationEmailList where location equals to locationId + 1
        defaultLocationEmailShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationEmailShouldBeFound(String filter) throws Exception {
        restLocationEmailMockMvc.perform(get("/api/location-emails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restLocationEmailMockMvc.perform(get("/api/location-emails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationEmailShouldNotBeFound(String filter) throws Exception {
        restLocationEmailMockMvc.perform(get("/api/location-emails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationEmailMockMvc.perform(get("/api/location-emails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocationEmail() throws Exception {
        // Get the locationEmail
        restLocationEmailMockMvc.perform(get("/api/location-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationEmail() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        int databaseSizeBeforeUpdate = locationEmailRepository.findAll().size();

        // Update the locationEmail
        LocationEmail updatedLocationEmail = locationEmailRepository.findById(locationEmail.getId()).get();
        // Disconnect from session so that the updates on updatedLocationEmail are not directly saved in db
        em.detach(updatedLocationEmail);
        updatedLocationEmail
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .comment(UPDATED_COMMENT)
            .email(UPDATED_EMAIL);
        LocationEmailDTO locationEmailDTO = locationEmailMapper.toDto(updatedLocationEmail);

        restLocationEmailMockMvc.perform(put("/api/location-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationEmailDTO)))
            .andExpect(status().isOk());

        // Validate the LocationEmail in the database
        List<LocationEmail> locationEmailList = locationEmailRepository.findAll();
        assertThat(locationEmailList).hasSize(databaseSizeBeforeUpdate);
        LocationEmail testLocationEmail = locationEmailList.get(locationEmailList.size() - 1);
        assertThat(testLocationEmail.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocationEmail.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testLocationEmail.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLocationEmail.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testLocationEmail.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLocationEmail.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testLocationEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationEmail() throws Exception {
        int databaseSizeBeforeUpdate = locationEmailRepository.findAll().size();

        // Create the LocationEmail
        LocationEmailDTO locationEmailDTO = locationEmailMapper.toDto(locationEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationEmailMockMvc.perform(put("/api/location-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationEmail in the database
        List<LocationEmail> locationEmailList = locationEmailRepository.findAll();
        assertThat(locationEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationEmail() throws Exception {
        // Initialize the database
        locationEmailRepository.saveAndFlush(locationEmail);

        int databaseSizeBeforeDelete = locationEmailRepository.findAll().size();

        // Delete the locationEmail
        restLocationEmailMockMvc.perform(delete("/api/location-emails/{id}", locationEmail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationEmail> locationEmailList = locationEmailRepository.findAll();
        assertThat(locationEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEmail.class);
        LocationEmail locationEmail1 = new LocationEmail();
        locationEmail1.setId(1L);
        LocationEmail locationEmail2 = new LocationEmail();
        locationEmail2.setId(locationEmail1.getId());
        assertThat(locationEmail1).isEqualTo(locationEmail2);
        locationEmail2.setId(2L);
        assertThat(locationEmail1).isNotEqualTo(locationEmail2);
        locationEmail1.setId(null);
        assertThat(locationEmail1).isNotEqualTo(locationEmail2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEmailDTO.class);
        LocationEmailDTO locationEmailDTO1 = new LocationEmailDTO();
        locationEmailDTO1.setId(1L);
        LocationEmailDTO locationEmailDTO2 = new LocationEmailDTO();
        assertThat(locationEmailDTO1).isNotEqualTo(locationEmailDTO2);
        locationEmailDTO2.setId(locationEmailDTO1.getId());
        assertThat(locationEmailDTO1).isEqualTo(locationEmailDTO2);
        locationEmailDTO2.setId(2L);
        assertThat(locationEmailDTO1).isNotEqualTo(locationEmailDTO2);
        locationEmailDTO1.setId(null);
        assertThat(locationEmailDTO1).isNotEqualTo(locationEmailDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationEmailMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationEmailMapper.fromId(null)).isNull();
    }
}
