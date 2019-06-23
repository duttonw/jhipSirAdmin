package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.LocationType;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.repository.LocationTypeRepository;
import au.gov.qld.sir.service.LocationTypeService;
import au.gov.qld.sir.service.dto.LocationTypeDTO;
import au.gov.qld.sir.service.mapper.LocationTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.LocationTypeCriteria;
import au.gov.qld.sir.service.LocationTypeQueryService;

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
 * Integration tests for the {@Link LocationTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class LocationTypeResourceIT {

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

    private static final String DEFAULT_LOCATION_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_TYPE_CODE = "BBBBBBBBBB";

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private LocationTypeMapper locationTypeMapper;

    @Autowired
    private LocationTypeService locationTypeService;

    @Autowired
    private LocationTypeQueryService locationTypeQueryService;

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

    private MockMvc restLocationTypeMockMvc;

    private LocationType locationType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationTypeResource locationTypeResource = new LocationTypeResource(locationTypeService, locationTypeQueryService);
        this.restLocationTypeMockMvc = MockMvcBuilders.standaloneSetup(locationTypeResource)
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
    public static LocationType createEntity(EntityManager em) {
        LocationType locationType = new LocationType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .locationTypeCode(DEFAULT_LOCATION_TYPE_CODE);
        return locationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationType createUpdatedEntity(EntityManager em) {
        LocationType locationType = new LocationType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .locationTypeCode(UPDATED_LOCATION_TYPE_CODE);
        return locationType;
    }

    @BeforeEach
    public void initTest() {
        locationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationType() throws Exception {
        int databaseSizeBeforeCreate = locationTypeRepository.findAll().size();

        // Create the LocationType
        LocationTypeDTO locationTypeDTO = locationTypeMapper.toDto(locationType);
        restLocationTypeMockMvc.perform(post("/api/location-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationType in the database
        List<LocationType> locationTypeList = locationTypeRepository.findAll();
        assertThat(locationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LocationType testLocationType = locationTypeList.get(locationTypeList.size() - 1);
        assertThat(testLocationType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocationType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testLocationType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLocationType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testLocationType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLocationType.getLocationTypeCode()).isEqualTo(DEFAULT_LOCATION_TYPE_CODE);
    }

    @Test
    @Transactional
    public void createLocationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationTypeRepository.findAll().size();

        // Create the LocationType with an existing ID
        locationType.setId(1L);
        LocationTypeDTO locationTypeDTO = locationTypeMapper.toDto(locationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationTypeMockMvc.perform(post("/api/location-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationType in the database
        List<LocationType> locationTypeList = locationTypeRepository.findAll();
        assertThat(locationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocationTypes() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList
        restLocationTypeMockMvc.perform(get("/api/location-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].locationTypeCode").value(hasItem(DEFAULT_LOCATION_TYPE_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getLocationType() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get the locationType
        restLocationTypeMockMvc.perform(get("/api/location-types/{id}", locationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.locationTypeCode").value(DEFAULT_LOCATION_TYPE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdBy is not null
        defaultLocationTypeShouldBeFound("createdBy.specified=true");

        // Get all the locationTypeList where createdBy is null
        defaultLocationTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultLocationTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the locationTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultLocationTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the locationTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where createdDateTime is not null
        defaultLocationTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the locationTypeList where createdDateTime is null
        defaultLocationTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLocationTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the locationTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLocationTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the locationTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedBy is not null
        defaultLocationTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the locationTypeList where modifiedBy is null
        defaultLocationTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultLocationTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the locationTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultLocationTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the locationTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where modifiedDateTime is not null
        defaultLocationTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the locationTypeList where modifiedDateTime is null
        defaultLocationTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where version equals to DEFAULT_VERSION
        defaultLocationTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the locationTypeList where version equals to UPDATED_VERSION
        defaultLocationTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLocationTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the locationTypeList where version equals to UPDATED_VERSION
        defaultLocationTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where version is not null
        defaultLocationTypeShouldBeFound("version.specified=true");

        // Get all the locationTypeList where version is null
        defaultLocationTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where version greater than or equals to DEFAULT_VERSION
        defaultLocationTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the locationTypeList where version greater than or equals to UPDATED_VERSION
        defaultLocationTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where version less than or equals to DEFAULT_VERSION
        defaultLocationTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the locationTypeList where version less than or equals to UPDATED_VERSION
        defaultLocationTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllLocationTypesByLocationTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where locationTypeCode equals to DEFAULT_LOCATION_TYPE_CODE
        defaultLocationTypeShouldBeFound("locationTypeCode.equals=" + DEFAULT_LOCATION_TYPE_CODE);

        // Get all the locationTypeList where locationTypeCode equals to UPDATED_LOCATION_TYPE_CODE
        defaultLocationTypeShouldNotBeFound("locationTypeCode.equals=" + UPDATED_LOCATION_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByLocationTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where locationTypeCode in DEFAULT_LOCATION_TYPE_CODE or UPDATED_LOCATION_TYPE_CODE
        defaultLocationTypeShouldBeFound("locationTypeCode.in=" + DEFAULT_LOCATION_TYPE_CODE + "," + UPDATED_LOCATION_TYPE_CODE);

        // Get all the locationTypeList where locationTypeCode equals to UPDATED_LOCATION_TYPE_CODE
        defaultLocationTypeShouldNotBeFound("locationTypeCode.in=" + UPDATED_LOCATION_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationTypesByLocationTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        // Get all the locationTypeList where locationTypeCode is not null
        defaultLocationTypeShouldBeFound("locationTypeCode.specified=true");

        // Get all the locationTypeList where locationTypeCode is null
        defaultLocationTypeShouldNotBeFound("locationTypeCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationTypesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        locationType.addLocation(location);
        locationTypeRepository.saveAndFlush(locationType);
        Long locationId = location.getId();

        // Get all the locationTypeList where location equals to locationId
        defaultLocationTypeShouldBeFound("locationId.equals=" + locationId);

        // Get all the locationTypeList where location equals to locationId + 1
        defaultLocationTypeShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationTypeShouldBeFound(String filter) throws Exception {
        restLocationTypeMockMvc.perform(get("/api/location-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].locationTypeCode").value(hasItem(DEFAULT_LOCATION_TYPE_CODE)));

        // Check, that the count call also returns 1
        restLocationTypeMockMvc.perform(get("/api/location-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationTypeShouldNotBeFound(String filter) throws Exception {
        restLocationTypeMockMvc.perform(get("/api/location-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationTypeMockMvc.perform(get("/api/location-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocationType() throws Exception {
        // Get the locationType
        restLocationTypeMockMvc.perform(get("/api/location-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationType() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        int databaseSizeBeforeUpdate = locationTypeRepository.findAll().size();

        // Update the locationType
        LocationType updatedLocationType = locationTypeRepository.findById(locationType.getId()).get();
        // Disconnect from session so that the updates on updatedLocationType are not directly saved in db
        em.detach(updatedLocationType);
        updatedLocationType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .locationTypeCode(UPDATED_LOCATION_TYPE_CODE);
        LocationTypeDTO locationTypeDTO = locationTypeMapper.toDto(updatedLocationType);

        restLocationTypeMockMvc.perform(put("/api/location-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LocationType in the database
        List<LocationType> locationTypeList = locationTypeRepository.findAll();
        assertThat(locationTypeList).hasSize(databaseSizeBeforeUpdate);
        LocationType testLocationType = locationTypeList.get(locationTypeList.size() - 1);
        assertThat(testLocationType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocationType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testLocationType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLocationType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testLocationType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLocationType.getLocationTypeCode()).isEqualTo(UPDATED_LOCATION_TYPE_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationType() throws Exception {
        int databaseSizeBeforeUpdate = locationTypeRepository.findAll().size();

        // Create the LocationType
        LocationTypeDTO locationTypeDTO = locationTypeMapper.toDto(locationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationTypeMockMvc.perform(put("/api/location-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationType in the database
        List<LocationType> locationTypeList = locationTypeRepository.findAll();
        assertThat(locationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationType() throws Exception {
        // Initialize the database
        locationTypeRepository.saveAndFlush(locationType);

        int databaseSizeBeforeDelete = locationTypeRepository.findAll().size();

        // Delete the locationType
        restLocationTypeMockMvc.perform(delete("/api/location-types/{id}", locationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationType> locationTypeList = locationTypeRepository.findAll();
        assertThat(locationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationType.class);
        LocationType locationType1 = new LocationType();
        locationType1.setId(1L);
        LocationType locationType2 = new LocationType();
        locationType2.setId(locationType1.getId());
        assertThat(locationType1).isEqualTo(locationType2);
        locationType2.setId(2L);
        assertThat(locationType1).isNotEqualTo(locationType2);
        locationType1.setId(null);
        assertThat(locationType1).isNotEqualTo(locationType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationTypeDTO.class);
        LocationTypeDTO locationTypeDTO1 = new LocationTypeDTO();
        locationTypeDTO1.setId(1L);
        LocationTypeDTO locationTypeDTO2 = new LocationTypeDTO();
        assertThat(locationTypeDTO1).isNotEqualTo(locationTypeDTO2);
        locationTypeDTO2.setId(locationTypeDTO1.getId());
        assertThat(locationTypeDTO1).isEqualTo(locationTypeDTO2);
        locationTypeDTO2.setId(2L);
        assertThat(locationTypeDTO1).isNotEqualTo(locationTypeDTO2);
        locationTypeDTO1.setId(null);
        assertThat(locationTypeDTO1).isNotEqualTo(locationTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationTypeMapper.fromId(null)).isNull();
    }
}
