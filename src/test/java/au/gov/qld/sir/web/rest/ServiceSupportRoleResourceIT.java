package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.ServiceRoleType;
import au.gov.qld.sir.domain.ServiceSupportRoleContextType;
import au.gov.qld.sir.repository.ServiceSupportRoleRepository;
import au.gov.qld.sir.service.ServiceSupportRoleService;
import au.gov.qld.sir.service.dto.ServiceSupportRoleDTO;
import au.gov.qld.sir.service.mapper.ServiceSupportRoleMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceSupportRoleCriteria;
import au.gov.qld.sir.service.ServiceSupportRoleQueryService;

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
 * Integration tests for the {@Link ServiceSupportRoleResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceSupportRoleResourceIT {

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

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ServiceSupportRoleRepository serviceSupportRoleRepository;

    @Autowired
    private ServiceSupportRoleMapper serviceSupportRoleMapper;

    @Autowired
    private ServiceSupportRoleService serviceSupportRoleService;

    @Autowired
    private ServiceSupportRoleQueryService serviceSupportRoleQueryService;

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

    private MockMvc restServiceSupportRoleMockMvc;

    private ServiceSupportRole serviceSupportRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceSupportRoleResource serviceSupportRoleResource = new ServiceSupportRoleResource(serviceSupportRoleService, serviceSupportRoleQueryService);
        this.restServiceSupportRoleMockMvc = MockMvcBuilders.standaloneSetup(serviceSupportRoleResource)
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
    public static ServiceSupportRole createEntity(EntityManager em) {
        ServiceSupportRole serviceSupportRole = new ServiceSupportRole()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactName(DEFAULT_CONTACT_NAME)
            .contactPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER);
        return serviceSupportRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSupportRole createUpdatedEntity(EntityManager em) {
        ServiceSupportRole serviceSupportRole = new ServiceSupportRole()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER);
        return serviceSupportRole;
    }

    @BeforeEach
    public void initTest() {
        serviceSupportRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceSupportRole() throws Exception {
        int databaseSizeBeforeCreate = serviceSupportRoleRepository.findAll().size();

        // Create the ServiceSupportRole
        ServiceSupportRoleDTO serviceSupportRoleDTO = serviceSupportRoleMapper.toDto(serviceSupportRole);
        restServiceSupportRoleMockMvc.perform(post("/api/service-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceSupportRole in the database
        List<ServiceSupportRole> serviceSupportRoleList = serviceSupportRoleRepository.findAll();
        assertThat(serviceSupportRoleList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSupportRole testServiceSupportRole = serviceSupportRoleList.get(serviceSupportRoleList.size() - 1);
        assertThat(testServiceSupportRole.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceSupportRole.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceSupportRole.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceSupportRole.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceSupportRole.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceSupportRole.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testServiceSupportRole.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testServiceSupportRole.getContactPhoneNumber()).isEqualTo(DEFAULT_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createServiceSupportRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSupportRoleRepository.findAll().size();

        // Create the ServiceSupportRole with an existing ID
        serviceSupportRole.setId(1L);
        ServiceSupportRoleDTO serviceSupportRoleDTO = serviceSupportRoleMapper.toDto(serviceSupportRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSupportRoleMockMvc.perform(post("/api/service-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupportRole in the database
        List<ServiceSupportRole> serviceSupportRoleList = serviceSupportRoleRepository.findAll();
        assertThat(serviceSupportRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceSupportRoles() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSupportRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceSupportRole() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get the serviceSupportRole
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles/{id}", serviceSupportRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSupportRole.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.contactPhoneNumber").value(DEFAULT_CONTACT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceSupportRoleShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceSupportRoleList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceSupportRoleShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceSupportRoleShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceSupportRoleList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceSupportRoleShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdBy is not null
        defaultServiceSupportRoleShouldBeFound("createdBy.specified=true");

        // Get all the serviceSupportRoleList where createdBy is null
        defaultServiceSupportRoleShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceSupportRoleShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceSupportRoleList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceSupportRoleList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceSupportRoleShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where createdDateTime is not null
        defaultServiceSupportRoleShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceSupportRoleList where createdDateTime is null
        defaultServiceSupportRoleShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceSupportRoleShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceSupportRoleList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceSupportRoleShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceSupportRoleShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceSupportRoleList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceSupportRoleShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedBy is not null
        defaultServiceSupportRoleShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceSupportRoleList where modifiedBy is null
        defaultServiceSupportRoleShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceSupportRoleShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceSupportRoleList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceSupportRoleList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceSupportRoleShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where modifiedDateTime is not null
        defaultServiceSupportRoleShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceSupportRoleList where modifiedDateTime is null
        defaultServiceSupportRoleShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where version equals to DEFAULT_VERSION
        defaultServiceSupportRoleShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleList where version equals to UPDATED_VERSION
        defaultServiceSupportRoleShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceSupportRoleShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceSupportRoleList where version equals to UPDATED_VERSION
        defaultServiceSupportRoleShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where version is not null
        defaultServiceSupportRoleShouldBeFound("version.specified=true");

        // Get all the serviceSupportRoleList where version is null
        defaultServiceSupportRoleShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where version greater than or equals to DEFAULT_VERSION
        defaultServiceSupportRoleShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleList where version greater than or equals to UPDATED_VERSION
        defaultServiceSupportRoleShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where version less than or equals to DEFAULT_VERSION
        defaultServiceSupportRoleShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceSupportRoleList where version less than or equals to UPDATED_VERSION
        defaultServiceSupportRoleShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultServiceSupportRoleShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the serviceSupportRoleList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultServiceSupportRoleShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultServiceSupportRoleShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the serviceSupportRoleList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultServiceSupportRoleShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactEmail is not null
        defaultServiceSupportRoleShouldBeFound("contactEmail.specified=true");

        // Get all the serviceSupportRoleList where contactEmail is null
        defaultServiceSupportRoleShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactName equals to DEFAULT_CONTACT_NAME
        defaultServiceSupportRoleShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the serviceSupportRoleList where contactName equals to UPDATED_CONTACT_NAME
        defaultServiceSupportRoleShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultServiceSupportRoleShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the serviceSupportRoleList where contactName equals to UPDATED_CONTACT_NAME
        defaultServiceSupportRoleShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactName is not null
        defaultServiceSupportRoleShouldBeFound("contactName.specified=true");

        // Get all the serviceSupportRoleList where contactName is null
        defaultServiceSupportRoleShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactPhoneNumber equals to DEFAULT_CONTACT_PHONE_NUMBER
        defaultServiceSupportRoleShouldBeFound("contactPhoneNumber.equals=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the serviceSupportRoleList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultServiceSupportRoleShouldNotBeFound("contactPhoneNumber.equals=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactPhoneNumber in DEFAULT_CONTACT_PHONE_NUMBER or UPDATED_CONTACT_PHONE_NUMBER
        defaultServiceSupportRoleShouldBeFound("contactPhoneNumber.in=" + DEFAULT_CONTACT_PHONE_NUMBER + "," + UPDATED_CONTACT_PHONE_NUMBER);

        // Get all the serviceSupportRoleList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultServiceSupportRoleShouldNotBeFound("contactPhoneNumber.in=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByContactPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        // Get all the serviceSupportRoleList where contactPhoneNumber is not null
        defaultServiceSupportRoleShouldBeFound("contactPhoneNumber.specified=true");

        // Get all the serviceSupportRoleList where contactPhoneNumber is null
        defaultServiceSupportRoleShouldNotBeFound("contactPhoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceSupportRolesByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceSupportRole.setServiceRecord(serviceRecord);
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceSupportRoleList where serviceRecord equals to serviceRecordId
        defaultServiceSupportRoleShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceSupportRoleList where serviceRecord equals to serviceRecordId + 1
        defaultServiceSupportRoleShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceSupportRolesByServiceRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRoleType serviceRoleType = ServiceRoleTypeResourceIT.createEntity(em);
        em.persist(serviceRoleType);
        em.flush();
        serviceSupportRole.setServiceRoleType(serviceRoleType);
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);
        Long serviceRoleTypeId = serviceRoleType.getId();

        // Get all the serviceSupportRoleList where serviceRoleType equals to serviceRoleTypeId
        defaultServiceSupportRoleShouldBeFound("serviceRoleTypeId.equals=" + serviceRoleTypeId);

        // Get all the serviceSupportRoleList where serviceRoleType equals to serviceRoleTypeId + 1
        defaultServiceSupportRoleShouldNotBeFound("serviceRoleTypeId.equals=" + (serviceRoleTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceSupportRolesByServiceSupportContextTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceSupportRoleContextType serviceSupportContextType = ServiceSupportRoleContextTypeResourceIT.createEntity(em);
        em.persist(serviceSupportContextType);
        em.flush();
        serviceSupportRole.setServiceSupportContextType(serviceSupportContextType);
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);
        Long serviceSupportContextTypeId = serviceSupportContextType.getId();

        // Get all the serviceSupportRoleList where serviceSupportContextType equals to serviceSupportContextTypeId
        defaultServiceSupportRoleShouldBeFound("serviceSupportContextTypeId.equals=" + serviceSupportContextTypeId);

        // Get all the serviceSupportRoleList where serviceSupportContextType equals to serviceSupportContextTypeId + 1
        defaultServiceSupportRoleShouldNotBeFound("serviceSupportContextTypeId.equals=" + (serviceSupportContextTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceSupportRoleShouldBeFound(String filter) throws Exception {
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSupportRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER)));

        // Check, that the count call also returns 1
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceSupportRoleShouldNotBeFound(String filter) throws Exception {
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceSupportRole() throws Exception {
        // Get the serviceSupportRole
        restServiceSupportRoleMockMvc.perform(get("/api/service-support-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceSupportRole() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        int databaseSizeBeforeUpdate = serviceSupportRoleRepository.findAll().size();

        // Update the serviceSupportRole
        ServiceSupportRole updatedServiceSupportRole = serviceSupportRoleRepository.findById(serviceSupportRole.getId()).get();
        // Disconnect from session so that the updates on updatedServiceSupportRole are not directly saved in db
        em.detach(updatedServiceSupportRole);
        updatedServiceSupportRole
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER);
        ServiceSupportRoleDTO serviceSupportRoleDTO = serviceSupportRoleMapper.toDto(updatedServiceSupportRole);

        restServiceSupportRoleMockMvc.perform(put("/api/service-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceSupportRole in the database
        List<ServiceSupportRole> serviceSupportRoleList = serviceSupportRoleRepository.findAll();
        assertThat(serviceSupportRoleList).hasSize(databaseSizeBeforeUpdate);
        ServiceSupportRole testServiceSupportRole = serviceSupportRoleList.get(serviceSupportRoleList.size() - 1);
        assertThat(testServiceSupportRole.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceSupportRole.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceSupportRole.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceSupportRole.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceSupportRole.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSupportRole.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testServiceSupportRole.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testServiceSupportRole.getContactPhoneNumber()).isEqualTo(UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceSupportRole() throws Exception {
        int databaseSizeBeforeUpdate = serviceSupportRoleRepository.findAll().size();

        // Create the ServiceSupportRole
        ServiceSupportRoleDTO serviceSupportRoleDTO = serviceSupportRoleMapper.toDto(serviceSupportRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceSupportRoleMockMvc.perform(put("/api/service-support-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupportRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupportRole in the database
        List<ServiceSupportRole> serviceSupportRoleList = serviceSupportRoleRepository.findAll();
        assertThat(serviceSupportRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceSupportRole() throws Exception {
        // Initialize the database
        serviceSupportRoleRepository.saveAndFlush(serviceSupportRole);

        int databaseSizeBeforeDelete = serviceSupportRoleRepository.findAll().size();

        // Delete the serviceSupportRole
        restServiceSupportRoleMockMvc.perform(delete("/api/service-support-roles/{id}", serviceSupportRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceSupportRole> serviceSupportRoleList = serviceSupportRoleRepository.findAll();
        assertThat(serviceSupportRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSupportRole.class);
        ServiceSupportRole serviceSupportRole1 = new ServiceSupportRole();
        serviceSupportRole1.setId(1L);
        ServiceSupportRole serviceSupportRole2 = new ServiceSupportRole();
        serviceSupportRole2.setId(serviceSupportRole1.getId());
        assertThat(serviceSupportRole1).isEqualTo(serviceSupportRole2);
        serviceSupportRole2.setId(2L);
        assertThat(serviceSupportRole1).isNotEqualTo(serviceSupportRole2);
        serviceSupportRole1.setId(null);
        assertThat(serviceSupportRole1).isNotEqualTo(serviceSupportRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSupportRoleDTO.class);
        ServiceSupportRoleDTO serviceSupportRoleDTO1 = new ServiceSupportRoleDTO();
        serviceSupportRoleDTO1.setId(1L);
        ServiceSupportRoleDTO serviceSupportRoleDTO2 = new ServiceSupportRoleDTO();
        assertThat(serviceSupportRoleDTO1).isNotEqualTo(serviceSupportRoleDTO2);
        serviceSupportRoleDTO2.setId(serviceSupportRoleDTO1.getId());
        assertThat(serviceSupportRoleDTO1).isEqualTo(serviceSupportRoleDTO2);
        serviceSupportRoleDTO2.setId(2L);
        assertThat(serviceSupportRoleDTO1).isNotEqualTo(serviceSupportRoleDTO2);
        serviceSupportRoleDTO1.setId(null);
        assertThat(serviceSupportRoleDTO1).isNotEqualTo(serviceSupportRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceSupportRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceSupportRoleMapper.fromId(null)).isNull();
    }
}
