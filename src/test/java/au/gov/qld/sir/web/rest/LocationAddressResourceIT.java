package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.LocationAddress;
import au.gov.qld.sir.domain.Location;
import au.gov.qld.sir.repository.LocationAddressRepository;
import au.gov.qld.sir.service.LocationAddressService;
import au.gov.qld.sir.service.dto.LocationAddressDTO;
import au.gov.qld.sir.service.mapper.LocationAddressMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.LocationAddressCriteria;
import au.gov.qld.sir.service.LocationAddressQueryService;

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
 * Integration tests for the {@Link LocationAddressResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class LocationAddressResourceIT {

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

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AA";
    private static final String UPDATED_COUNTRY_CODE = "BB";

    private static final String DEFAULT_LOCALITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_POINT = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_POINT = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAA";
    private static final String UPDATED_POSTCODE = "BBBB";

    private static final String DEFAULT_STATE_CODE = "AAA";
    private static final String UPDATED_STATE_CODE = "BBB";

    @Autowired
    private LocationAddressRepository locationAddressRepository;

    @Autowired
    private LocationAddressMapper locationAddressMapper;

    @Autowired
    private LocationAddressService locationAddressService;

    @Autowired
    private LocationAddressQueryService locationAddressQueryService;

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

    private MockMvc restLocationAddressMockMvc;

    private LocationAddress locationAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationAddressResource locationAddressResource = new LocationAddressResource(locationAddressService, locationAddressQueryService);
        this.restLocationAddressMockMvc = MockMvcBuilders.standaloneSetup(locationAddressResource)
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
    public static LocationAddress createEntity(EntityManager em) {
        LocationAddress locationAddress = new LocationAddress()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .addressType(DEFAULT_ADDRESS_TYPE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .localityName(DEFAULT_LOCALITY_NAME)
            .locationPoint(DEFAULT_LOCATION_POINT)
            .postcode(DEFAULT_POSTCODE)
            .stateCode(DEFAULT_STATE_CODE);
        return locationAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationAddress createUpdatedEntity(EntityManager em) {
        LocationAddress locationAddress = new LocationAddress()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressType(UPDATED_ADDRESS_TYPE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .localityName(UPDATED_LOCALITY_NAME)
            .locationPoint(UPDATED_LOCATION_POINT)
            .postcode(UPDATED_POSTCODE)
            .stateCode(UPDATED_STATE_CODE);
        return locationAddress;
    }

    @BeforeEach
    public void initTest() {
        locationAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationAddress() throws Exception {
        int databaseSizeBeforeCreate = locationAddressRepository.findAll().size();

        // Create the LocationAddress
        LocationAddressDTO locationAddressDTO = locationAddressMapper.toDto(locationAddress);
        restLocationAddressMockMvc.perform(post("/api/location-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationAddress in the database
        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeCreate + 1);
        LocationAddress testLocationAddress = locationAddressList.get(locationAddressList.size() - 1);
        assertThat(testLocationAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocationAddress.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testLocationAddress.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLocationAddress.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testLocationAddress.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLocationAddress.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testLocationAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testLocationAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testLocationAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testLocationAddress.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testLocationAddress.getLocalityName()).isEqualTo(DEFAULT_LOCALITY_NAME);
        assertThat(testLocationAddress.getLocationPoint()).isEqualTo(DEFAULT_LOCATION_POINT);
        assertThat(testLocationAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testLocationAddress.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
    }

    @Test
    @Transactional
    public void createLocationAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationAddressRepository.findAll().size();

        // Create the LocationAddress with an existing ID
        locationAddress.setId(1L);
        LocationAddressDTO locationAddressDTO = locationAddressMapper.toDto(locationAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationAddressMockMvc.perform(post("/api/location-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationAddress in the database
        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationAddressRepository.findAll().size();
        // set the field null
        locationAddress.setAddressType(null);

        // Create the LocationAddress, which fails.
        LocationAddressDTO locationAddressDTO = locationAddressMapper.toDto(locationAddress);

        restLocationAddressMockMvc.perform(post("/api/location-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationAddressDTO)))
            .andExpect(status().isBadRequest());

        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocationAddresses() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList
        restLocationAddressMockMvc.perform(get("/api/location-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].localityName").value(hasItem(DEFAULT_LOCALITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].locationPoint").value(hasItem(DEFAULT_LOCATION_POINT.toString())))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getLocationAddress() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get the locationAddress
        restLocationAddressMockMvc.perform(get("/api/location-addresses/{id}", locationAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationAddress.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.localityName").value(DEFAULT_LOCALITY_NAME.toString()))
            .andExpect(jsonPath("$.locationPoint").value(DEFAULT_LOCATION_POINT.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationAddressShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationAddressList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationAddressShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationAddressShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationAddressList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationAddressShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdBy is not null
        defaultLocationAddressShouldBeFound("createdBy.specified=true");

        // Get all the locationAddressList where createdBy is null
        defaultLocationAddressShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultLocationAddressShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the locationAddressList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationAddressShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultLocationAddressShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the locationAddressList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultLocationAddressShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where createdDateTime is not null
        defaultLocationAddressShouldBeFound("createdDateTime.specified=true");

        // Get all the locationAddressList where createdDateTime is null
        defaultLocationAddressShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLocationAddressShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the locationAddressList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationAddressShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLocationAddressShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the locationAddressList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLocationAddressShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedBy is not null
        defaultLocationAddressShouldBeFound("modifiedBy.specified=true");

        // Get all the locationAddressList where modifiedBy is null
        defaultLocationAddressShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultLocationAddressShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the locationAddressList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationAddressShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultLocationAddressShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the locationAddressList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultLocationAddressShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where modifiedDateTime is not null
        defaultLocationAddressShouldBeFound("modifiedDateTime.specified=true");

        // Get all the locationAddressList where modifiedDateTime is null
        defaultLocationAddressShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where version equals to DEFAULT_VERSION
        defaultLocationAddressShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the locationAddressList where version equals to UPDATED_VERSION
        defaultLocationAddressShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultLocationAddressShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the locationAddressList where version equals to UPDATED_VERSION
        defaultLocationAddressShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where version is not null
        defaultLocationAddressShouldBeFound("version.specified=true");

        // Get all the locationAddressList where version is null
        defaultLocationAddressShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where version greater than or equals to DEFAULT_VERSION
        defaultLocationAddressShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the locationAddressList where version greater than or equals to UPDATED_VERSION
        defaultLocationAddressShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where version less than or equals to DEFAULT_VERSION
        defaultLocationAddressShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the locationAddressList where version less than or equals to UPDATED_VERSION
        defaultLocationAddressShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllLocationAddressesByAdditionalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where additionalInformation equals to DEFAULT_ADDITIONAL_INFORMATION
        defaultLocationAddressShouldBeFound("additionalInformation.equals=" + DEFAULT_ADDITIONAL_INFORMATION);

        // Get all the locationAddressList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultLocationAddressShouldNotBeFound("additionalInformation.equals=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAdditionalInformationIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where additionalInformation in DEFAULT_ADDITIONAL_INFORMATION or UPDATED_ADDITIONAL_INFORMATION
        defaultLocationAddressShouldBeFound("additionalInformation.in=" + DEFAULT_ADDITIONAL_INFORMATION + "," + UPDATED_ADDITIONAL_INFORMATION);

        // Get all the locationAddressList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultLocationAddressShouldNotBeFound("additionalInformation.in=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAdditionalInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where additionalInformation is not null
        defaultLocationAddressShouldBeFound("additionalInformation.specified=true");

        // Get all the locationAddressList where additionalInformation is null
        defaultLocationAddressShouldNotBeFound("additionalInformation.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine1 equals to DEFAULT_ADDRESS_LINE_1
        defaultLocationAddressShouldBeFound("addressLine1.equals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the locationAddressList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultLocationAddressShouldNotBeFound("addressLine1.equals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine1IsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine1 in DEFAULT_ADDRESS_LINE_1 or UPDATED_ADDRESS_LINE_1
        defaultLocationAddressShouldBeFound("addressLine1.in=" + DEFAULT_ADDRESS_LINE_1 + "," + UPDATED_ADDRESS_LINE_1);

        // Get all the locationAddressList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultLocationAddressShouldNotBeFound("addressLine1.in=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine1 is not null
        defaultLocationAddressShouldBeFound("addressLine1.specified=true");

        // Get all the locationAddressList where addressLine1 is null
        defaultLocationAddressShouldNotBeFound("addressLine1.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine2 equals to DEFAULT_ADDRESS_LINE_2
        defaultLocationAddressShouldBeFound("addressLine2.equals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the locationAddressList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultLocationAddressShouldNotBeFound("addressLine2.equals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine2IsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine2 in DEFAULT_ADDRESS_LINE_2 or UPDATED_ADDRESS_LINE_2
        defaultLocationAddressShouldBeFound("addressLine2.in=" + DEFAULT_ADDRESS_LINE_2 + "," + UPDATED_ADDRESS_LINE_2);

        // Get all the locationAddressList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultLocationAddressShouldNotBeFound("addressLine2.in=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressLine2 is not null
        defaultLocationAddressShouldBeFound("addressLine2.specified=true");

        // Get all the locationAddressList where addressLine2 is null
        defaultLocationAddressShouldNotBeFound("addressLine2.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressType equals to DEFAULT_ADDRESS_TYPE
        defaultLocationAddressShouldBeFound("addressType.equals=" + DEFAULT_ADDRESS_TYPE);

        // Get all the locationAddressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultLocationAddressShouldNotBeFound("addressType.equals=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressTypeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressType in DEFAULT_ADDRESS_TYPE or UPDATED_ADDRESS_TYPE
        defaultLocationAddressShouldBeFound("addressType.in=" + DEFAULT_ADDRESS_TYPE + "," + UPDATED_ADDRESS_TYPE);

        // Get all the locationAddressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultLocationAddressShouldNotBeFound("addressType.in=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByAddressTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where addressType is not null
        defaultLocationAddressShouldBeFound("addressType.specified=true");

        // Get all the locationAddressList where addressType is null
        defaultLocationAddressShouldNotBeFound("addressType.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCountryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where countryCode equals to DEFAULT_COUNTRY_CODE
        defaultLocationAddressShouldBeFound("countryCode.equals=" + DEFAULT_COUNTRY_CODE);

        // Get all the locationAddressList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultLocationAddressShouldNotBeFound("countryCode.equals=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCountryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where countryCode in DEFAULT_COUNTRY_CODE or UPDATED_COUNTRY_CODE
        defaultLocationAddressShouldBeFound("countryCode.in=" + DEFAULT_COUNTRY_CODE + "," + UPDATED_COUNTRY_CODE);

        // Get all the locationAddressList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultLocationAddressShouldNotBeFound("countryCode.in=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByCountryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where countryCode is not null
        defaultLocationAddressShouldBeFound("countryCode.specified=true");

        // Get all the locationAddressList where countryCode is null
        defaultLocationAddressShouldNotBeFound("countryCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocalityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where localityName equals to DEFAULT_LOCALITY_NAME
        defaultLocationAddressShouldBeFound("localityName.equals=" + DEFAULT_LOCALITY_NAME);

        // Get all the locationAddressList where localityName equals to UPDATED_LOCALITY_NAME
        defaultLocationAddressShouldNotBeFound("localityName.equals=" + UPDATED_LOCALITY_NAME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocalityNameIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where localityName in DEFAULT_LOCALITY_NAME or UPDATED_LOCALITY_NAME
        defaultLocationAddressShouldBeFound("localityName.in=" + DEFAULT_LOCALITY_NAME + "," + UPDATED_LOCALITY_NAME);

        // Get all the locationAddressList where localityName equals to UPDATED_LOCALITY_NAME
        defaultLocationAddressShouldNotBeFound("localityName.in=" + UPDATED_LOCALITY_NAME);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocalityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where localityName is not null
        defaultLocationAddressShouldBeFound("localityName.specified=true");

        // Get all the locationAddressList where localityName is null
        defaultLocationAddressShouldNotBeFound("localityName.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocationPointIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where locationPoint equals to DEFAULT_LOCATION_POINT
        defaultLocationAddressShouldBeFound("locationPoint.equals=" + DEFAULT_LOCATION_POINT);

        // Get all the locationAddressList where locationPoint equals to UPDATED_LOCATION_POINT
        defaultLocationAddressShouldNotBeFound("locationPoint.equals=" + UPDATED_LOCATION_POINT);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocationPointIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where locationPoint in DEFAULT_LOCATION_POINT or UPDATED_LOCATION_POINT
        defaultLocationAddressShouldBeFound("locationPoint.in=" + DEFAULT_LOCATION_POINT + "," + UPDATED_LOCATION_POINT);

        // Get all the locationAddressList where locationPoint equals to UPDATED_LOCATION_POINT
        defaultLocationAddressShouldNotBeFound("locationPoint.in=" + UPDATED_LOCATION_POINT);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocationPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where locationPoint is not null
        defaultLocationAddressShouldBeFound("locationPoint.specified=true");

        // Get all the locationAddressList where locationPoint is null
        defaultLocationAddressShouldNotBeFound("locationPoint.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where postcode equals to DEFAULT_POSTCODE
        defaultLocationAddressShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the locationAddressList where postcode equals to UPDATED_POSTCODE
        defaultLocationAddressShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultLocationAddressShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the locationAddressList where postcode equals to UPDATED_POSTCODE
        defaultLocationAddressShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where postcode is not null
        defaultLocationAddressShouldBeFound("postcode.specified=true");

        // Get all the locationAddressList where postcode is null
        defaultLocationAddressShouldNotBeFound("postcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByStateCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where stateCode equals to DEFAULT_STATE_CODE
        defaultLocationAddressShouldBeFound("stateCode.equals=" + DEFAULT_STATE_CODE);

        // Get all the locationAddressList where stateCode equals to UPDATED_STATE_CODE
        defaultLocationAddressShouldNotBeFound("stateCode.equals=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByStateCodeIsInShouldWork() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where stateCode in DEFAULT_STATE_CODE or UPDATED_STATE_CODE
        defaultLocationAddressShouldBeFound("stateCode.in=" + DEFAULT_STATE_CODE + "," + UPDATED_STATE_CODE);

        // Get all the locationAddressList where stateCode equals to UPDATED_STATE_CODE
        defaultLocationAddressShouldNotBeFound("stateCode.in=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByStateCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        // Get all the locationAddressList where stateCode is not null
        defaultLocationAddressShouldBeFound("stateCode.specified=true");

        // Get all the locationAddressList where stateCode is null
        defaultLocationAddressShouldNotBeFound("stateCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationAddressesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        locationAddress.setLocation(location);
        locationAddressRepository.saveAndFlush(locationAddress);
        Long locationId = location.getId();

        // Get all the locationAddressList where location equals to locationId
        defaultLocationAddressShouldBeFound("locationId.equals=" + locationId);

        // Get all the locationAddressList where location equals to locationId + 1
        defaultLocationAddressShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationAddressShouldBeFound(String filter) throws Exception {
        restLocationAddressMockMvc.perform(get("/api/location-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].localityName").value(hasItem(DEFAULT_LOCALITY_NAME)))
            .andExpect(jsonPath("$.[*].locationPoint").value(hasItem(DEFAULT_LOCATION_POINT)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)));

        // Check, that the count call also returns 1
        restLocationAddressMockMvc.perform(get("/api/location-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationAddressShouldNotBeFound(String filter) throws Exception {
        restLocationAddressMockMvc.perform(get("/api/location-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationAddressMockMvc.perform(get("/api/location-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocationAddress() throws Exception {
        // Get the locationAddress
        restLocationAddressMockMvc.perform(get("/api/location-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationAddress() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        int databaseSizeBeforeUpdate = locationAddressRepository.findAll().size();

        // Update the locationAddress
        LocationAddress updatedLocationAddress = locationAddressRepository.findById(locationAddress.getId()).get();
        // Disconnect from session so that the updates on updatedLocationAddress are not directly saved in db
        em.detach(updatedLocationAddress);
        updatedLocationAddress
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressType(UPDATED_ADDRESS_TYPE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .localityName(UPDATED_LOCALITY_NAME)
            .locationPoint(UPDATED_LOCATION_POINT)
            .postcode(UPDATED_POSTCODE)
            .stateCode(UPDATED_STATE_CODE);
        LocationAddressDTO locationAddressDTO = locationAddressMapper.toDto(updatedLocationAddress);

        restLocationAddressMockMvc.perform(put("/api/location-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationAddressDTO)))
            .andExpect(status().isOk());

        // Validate the LocationAddress in the database
        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeUpdate);
        LocationAddress testLocationAddress = locationAddressList.get(locationAddressList.size() - 1);
        assertThat(testLocationAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocationAddress.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testLocationAddress.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLocationAddress.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testLocationAddress.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLocationAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testLocationAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testLocationAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testLocationAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testLocationAddress.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testLocationAddress.getLocalityName()).isEqualTo(UPDATED_LOCALITY_NAME);
        assertThat(testLocationAddress.getLocationPoint()).isEqualTo(UPDATED_LOCATION_POINT);
        assertThat(testLocationAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testLocationAddress.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationAddress() throws Exception {
        int databaseSizeBeforeUpdate = locationAddressRepository.findAll().size();

        // Create the LocationAddress
        LocationAddressDTO locationAddressDTO = locationAddressMapper.toDto(locationAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationAddressMockMvc.perform(put("/api/location-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationAddress in the database
        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationAddress() throws Exception {
        // Initialize the database
        locationAddressRepository.saveAndFlush(locationAddress);

        int databaseSizeBeforeDelete = locationAddressRepository.findAll().size();

        // Delete the locationAddress
        restLocationAddressMockMvc.perform(delete("/api/location-addresses/{id}", locationAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationAddress> locationAddressList = locationAddressRepository.findAll();
        assertThat(locationAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationAddress.class);
        LocationAddress locationAddress1 = new LocationAddress();
        locationAddress1.setId(1L);
        LocationAddress locationAddress2 = new LocationAddress();
        locationAddress2.setId(locationAddress1.getId());
        assertThat(locationAddress1).isEqualTo(locationAddress2);
        locationAddress2.setId(2L);
        assertThat(locationAddress1).isNotEqualTo(locationAddress2);
        locationAddress1.setId(null);
        assertThat(locationAddress1).isNotEqualTo(locationAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationAddressDTO.class);
        LocationAddressDTO locationAddressDTO1 = new LocationAddressDTO();
        locationAddressDTO1.setId(1L);
        LocationAddressDTO locationAddressDTO2 = new LocationAddressDTO();
        assertThat(locationAddressDTO1).isNotEqualTo(locationAddressDTO2);
        locationAddressDTO2.setId(locationAddressDTO1.getId());
        assertThat(locationAddressDTO1).isEqualTo(locationAddressDTO2);
        locationAddressDTO2.setId(2L);
        assertThat(locationAddressDTO1).isNotEqualTo(locationAddressDTO2);
        locationAddressDTO1.setId(null);
        assertThat(locationAddressDTO1).isNotEqualTo(locationAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationAddressMapper.fromId(null)).isNull();
    }
}
