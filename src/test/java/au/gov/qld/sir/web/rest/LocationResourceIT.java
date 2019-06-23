package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.domain.AvailabilityHours;
import au.gov.qld.sir.domain.LocationType;
import au.gov.qld.sir.domain.DeliveryChannel;
import au.gov.qld.sir.domain.LocationAddress;
import au.gov.qld.sir.domain.LocationEmail;
import au.gov.qld.sir.domain.LocationPhone;
import au.gov.qld.sir.repository.LocationRepository;
import au.gov.qld.sir.service.LocationService;
import au.gov.qld.sir.service.dto.LocationDTO;
import au.gov.qld.sir.service.mapper.LocationMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.LocationCriteria;
import au.gov.qld.sir.service.LocationQueryService;

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
 * Integration tests for the {@Link LocationResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class LocationResourceIT {

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

    private static final String DEFAULT_ACCESSIBILITY_FACILITIES = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSIBILITY_FACILITIES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationQueryService locationQueryService;

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

    private MockMvc restLocationMockMvc;

    private Location location;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationResource locationResource = new LocationResource(locationService, locationQueryService);
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
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
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .accessibilityFacilities(DEFAULT_ACCESSIBILITY_FACILITIES)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION)
            .locationName(DEFAULT_LOCATION_NAME);
        return location;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .accessibilityFacilities(UPDATED_ACCESSIBILITY_FACILITIES)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .locationName(UPDATED_LOCATION_NAME);
        return location;
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocation.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testLocation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLocation.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testLocation.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLocation.getAccessibilityFacilities()).isEqualTo(DEFAULT_ACCESSIBILITY_FACILITIES);
        assertThat(testLocation.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testLocation.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLocationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setLocationName(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].accessibilityFacilities").value(hasItem(DEFAULT_ACCESSIBILITY_FACILITIES.toString())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.accessibilityFacilities").value(DEFAULT_ACCESSIBILITY_FACILITIES.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy is not null
        defaultLocationShouldBeFound("createdBy.specified=true");

        // Get all the locationList where createdBy is null
        defaultLocationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultLocationShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the locationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultLocationShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the locationList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDateTime is not null
        defaultLocationShouldBeFound("createdDateTime.specified=true");

        // Get all the locationList where createdDateTime is null
        defaultLocationShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLocationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the locationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLocationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the locationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedBy is not null
        defaultLocationShouldBeFound("modifiedBy.specified=true");

        // Get all the locationList where modifiedBy is null
        defaultLocationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultLocationShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the locationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultLocationShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the locationList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where modifiedDateTime is not null
        defaultLocationShouldBeFound("modifiedDateTime.specified=true");

        // Get all the locationList where modifiedDateTime is null
        defaultLocationShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where version equals to DEFAULT_VERSION
        defaultLocationShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the locationList where version equals to UPDATED_VERSION
        defaultLocationShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLocationShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the locationList where version equals to UPDATED_VERSION
        defaultLocationShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where version is not null
        defaultLocationShouldBeFound("version.specified=true");

        // Get all the locationList where version is null
        defaultLocationShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where version greater than or equals to DEFAULT_VERSION
        defaultLocationShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the locationList where version greater than or equals to UPDATED_VERSION
        defaultLocationShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where version less than or equals to DEFAULT_VERSION
        defaultLocationShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the locationList where version less than or equals to UPDATED_VERSION
        defaultLocationShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllLocationsByAccessibilityFacilitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where accessibilityFacilities equals to DEFAULT_ACCESSIBILITY_FACILITIES
        defaultLocationShouldBeFound("accessibilityFacilities.equals=" + DEFAULT_ACCESSIBILITY_FACILITIES);

        // Get all the locationList where accessibilityFacilities equals to UPDATED_ACCESSIBILITY_FACILITIES
        defaultLocationShouldNotBeFound("accessibilityFacilities.equals=" + UPDATED_ACCESSIBILITY_FACILITIES);
    }

    @Test
    @Transactional
    public void getAllLocationsByAccessibilityFacilitiesIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where accessibilityFacilities in DEFAULT_ACCESSIBILITY_FACILITIES or UPDATED_ACCESSIBILITY_FACILITIES
        defaultLocationShouldBeFound("accessibilityFacilities.in=" + DEFAULT_ACCESSIBILITY_FACILITIES + "," + UPDATED_ACCESSIBILITY_FACILITIES);

        // Get all the locationList where accessibilityFacilities equals to UPDATED_ACCESSIBILITY_FACILITIES
        defaultLocationShouldNotBeFound("accessibilityFacilities.in=" + UPDATED_ACCESSIBILITY_FACILITIES);
    }

    @Test
    @Transactional
    public void getAllLocationsByAccessibilityFacilitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where accessibilityFacilities is not null
        defaultLocationShouldBeFound("accessibilityFacilities.specified=true");

        // Get all the locationList where accessibilityFacilities is null
        defaultLocationShouldNotBeFound("accessibilityFacilities.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByAdditionalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where additionalInformation equals to DEFAULT_ADDITIONAL_INFORMATION
        defaultLocationShouldBeFound("additionalInformation.equals=" + DEFAULT_ADDITIONAL_INFORMATION);

        // Get all the locationList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultLocationShouldNotBeFound("additionalInformation.equals=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLocationsByAdditionalInformationIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where additionalInformation in DEFAULT_ADDITIONAL_INFORMATION or UPDATED_ADDITIONAL_INFORMATION
        defaultLocationShouldBeFound("additionalInformation.in=" + DEFAULT_ADDITIONAL_INFORMATION + "," + UPDATED_ADDITIONAL_INFORMATION);

        // Get all the locationList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultLocationShouldNotBeFound("additionalInformation.in=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLocationsByAdditionalInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where additionalInformation is not null
        defaultLocationShouldBeFound("additionalInformation.specified=true");

        // Get all the locationList where additionalInformation is null
        defaultLocationShouldNotBeFound("additionalInformation.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByLocationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where locationName equals to DEFAULT_LOCATION_NAME
        defaultLocationShouldBeFound("locationName.equals=" + DEFAULT_LOCATION_NAME);

        // Get all the locationList where locationName equals to UPDATED_LOCATION_NAME
        defaultLocationShouldNotBeFound("locationName.equals=" + UPDATED_LOCATION_NAME);
    }

    @Test
    @Transactional
    public void getAllLocationsByLocationNameIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where locationName in DEFAULT_LOCATION_NAME or UPDATED_LOCATION_NAME
        defaultLocationShouldBeFound("locationName.in=" + DEFAULT_LOCATION_NAME + "," + UPDATED_LOCATION_NAME);

        // Get all the locationList where locationName equals to UPDATED_LOCATION_NAME
        defaultLocationShouldNotBeFound("locationName.in=" + UPDATED_LOCATION_NAME);
    }

    @Test
    @Transactional
    public void getAllLocationsByLocationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where locationName is not null
        defaultLocationShouldBeFound("locationName.specified=true");

        // Get all the locationList where locationName is null
        defaultLocationShouldNotBeFound("locationName.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationsByAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Agency agency = AgencyResourceIT.createEntity(em);
        em.persist(agency);
        em.flush();
        location.setAgency(agency);
        locationRepository.saveAndFlush(location);
        Long agencyId = agency.getId();

        // Get all the locationList where agency equals to agencyId
        defaultLocationShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the locationList where agency equals to agencyId + 1
        defaultLocationShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByLocationHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        AvailabilityHours locationHours = AvailabilityHoursResourceIT.createEntity(em);
        em.persist(locationHours);
        em.flush();
        location.setLocationHours(locationHours);
        locationRepository.saveAndFlush(location);
        Long locationHoursId = locationHours.getId();

        // Get all the locationList where locationHours equals to locationHoursId
        defaultLocationShouldBeFound("locationHoursId.equals=" + locationHoursId);

        // Get all the locationList where locationHours equals to locationHoursId + 1
        defaultLocationShouldNotBeFound("locationHoursId.equals=" + (locationHoursId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByLocationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        LocationType locationType = LocationTypeResourceIT.createEntity(em);
        em.persist(locationType);
        em.flush();
        location.setLocationType(locationType);
        locationRepository.saveAndFlush(location);
        Long locationTypeId = locationType.getId();

        // Get all the locationList where locationType equals to locationTypeId
        defaultLocationShouldBeFound("locationTypeId.equals=" + locationTypeId);

        // Get all the locationList where locationType equals to locationTypeId + 1
        defaultLocationShouldNotBeFound("locationTypeId.equals=" + (locationTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByDeliveryChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        DeliveryChannel deliveryChannel = DeliveryChannelResourceIT.createEntity(em);
        em.persist(deliveryChannel);
        em.flush();
        location.addDeliveryChannel(deliveryChannel);
        locationRepository.saveAndFlush(location);
        Long deliveryChannelId = deliveryChannel.getId();

        // Get all the locationList where deliveryChannel equals to deliveryChannelId
        defaultLocationShouldBeFound("deliveryChannelId.equals=" + deliveryChannelId);

        // Get all the locationList where deliveryChannel equals to deliveryChannelId + 1
        defaultLocationShouldNotBeFound("deliveryChannelId.equals=" + (deliveryChannelId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByLocationAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        LocationAddress locationAddress = LocationAddressResourceIT.createEntity(em);
        em.persist(locationAddress);
        em.flush();
        location.addLocationAddress(locationAddress);
        locationRepository.saveAndFlush(location);
        Long locationAddressId = locationAddress.getId();

        // Get all the locationList where locationAddress equals to locationAddressId
        defaultLocationShouldBeFound("locationAddressId.equals=" + locationAddressId);

        // Get all the locationList where locationAddress equals to locationAddressId + 1
        defaultLocationShouldNotBeFound("locationAddressId.equals=" + (locationAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByLocationEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        LocationEmail locationEmail = LocationEmailResourceIT.createEntity(em);
        em.persist(locationEmail);
        em.flush();
        location.addLocationEmail(locationEmail);
        locationRepository.saveAndFlush(location);
        Long locationEmailId = locationEmail.getId();

        // Get all the locationList where locationEmail equals to locationEmailId
        defaultLocationShouldBeFound("locationEmailId.equals=" + locationEmailId);

        // Get all the locationList where locationEmail equals to locationEmailId + 1
        defaultLocationShouldNotBeFound("locationEmailId.equals=" + (locationEmailId + 1));
    }


    @Test
    @Transactional
    public void getAllLocationsByLocationPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        LocationPhone locationPhone = LocationPhoneResourceIT.createEntity(em);
        em.persist(locationPhone);
        em.flush();
        location.addLocationPhone(locationPhone);
        locationRepository.saveAndFlush(location);
        Long locationPhoneId = locationPhone.getId();

        // Get all the locationList where locationPhone equals to locationPhoneId
        defaultLocationShouldBeFound("locationPhoneId.equals=" + locationPhoneId);

        // Get all the locationList where locationPhone equals to locationPhoneId + 1
        defaultLocationShouldNotBeFound("locationPhoneId.equals=" + (locationPhoneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationShouldBeFound(String filter) throws Exception {
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].accessibilityFacilities").value(hasItem(DEFAULT_ACCESSIBILITY_FACILITIES)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)));

        // Check, that the count call also returns 1
        restLocationMockMvc.perform(get("/api/locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationShouldNotBeFound(String filter) throws Exception {
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationMockMvc.perform(get("/api/locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .accessibilityFacilities(UPDATED_ACCESSIBILITY_FACILITIES)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .locationName(UPDATED_LOCATION_NAME);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocation.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testLocation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLocation.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testLocation.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLocation.getAccessibilityFacilities()).isEqualTo(UPDATED_ACCESSIBILITY_FACILITIES);
        assertThat(testLocation.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testLocation.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Delete the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = new Location();
        location1.setId(1L);
        Location location2 = new Location();
        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);
        location2.setId(2L);
        assertThat(location1).isNotEqualTo(location2);
        location1.setId(null);
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationDTO.class);
        LocationDTO locationDTO1 = new LocationDTO();
        locationDTO1.setId(1L);
        LocationDTO locationDTO2 = new LocationDTO();
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO2.setId(locationDTO1.getId());
        assertThat(locationDTO1).isEqualTo(locationDTO2);
        locationDTO2.setId(2L);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO1.setId(null);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationMapper.fromId(null)).isNull();
    }
}
