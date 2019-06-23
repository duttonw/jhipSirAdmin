package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceDeliveryForm;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceDeliveryFormRepository;
import au.gov.qld.sir.service.ServiceDeliveryFormService;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormDTO;
import au.gov.qld.sir.service.mapper.ServiceDeliveryFormMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceDeliveryFormCriteria;
import au.gov.qld.sir.service.ServiceDeliveryFormQueryService;

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
 * Integration tests for the {@Link ServiceDeliveryFormResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceDeliveryFormResourceIT {

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

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_URL = "AAAAAAAAAA";
    private static final String UPDATED_FORM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Autowired
    private ServiceDeliveryFormRepository serviceDeliveryFormRepository;

    @Autowired
    private ServiceDeliveryFormMapper serviceDeliveryFormMapper;

    @Autowired
    private ServiceDeliveryFormService serviceDeliveryFormService;

    @Autowired
    private ServiceDeliveryFormQueryService serviceDeliveryFormQueryService;

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

    private MockMvc restServiceDeliveryFormMockMvc;

    private ServiceDeliveryForm serviceDeliveryForm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceDeliveryFormResource serviceDeliveryFormResource = new ServiceDeliveryFormResource(serviceDeliveryFormService, serviceDeliveryFormQueryService);
        this.restServiceDeliveryFormMockMvc = MockMvcBuilders.standaloneSetup(serviceDeliveryFormResource)
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
    public static ServiceDeliveryForm createEntity(EntityManager em) {
        ServiceDeliveryForm serviceDeliveryForm = new ServiceDeliveryForm()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .formName(DEFAULT_FORM_NAME)
            .formUrl(DEFAULT_FORM_URL)
            .source(DEFAULT_SOURCE);
        return serviceDeliveryForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceDeliveryForm createUpdatedEntity(EntityManager em) {
        ServiceDeliveryForm serviceDeliveryForm = new ServiceDeliveryForm()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .formName(UPDATED_FORM_NAME)
            .formUrl(UPDATED_FORM_URL)
            .source(UPDATED_SOURCE);
        return serviceDeliveryForm;
    }

    @BeforeEach
    public void initTest() {
        serviceDeliveryForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceDeliveryForm() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryFormRepository.findAll().size();

        // Create the ServiceDeliveryForm
        ServiceDeliveryFormDTO serviceDeliveryFormDTO = serviceDeliveryFormMapper.toDto(serviceDeliveryForm);
        restServiceDeliveryFormMockMvc.perform(post("/api/service-delivery-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryFormDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceDeliveryForm in the database
        List<ServiceDeliveryForm> serviceDeliveryFormList = serviceDeliveryFormRepository.findAll();
        assertThat(serviceDeliveryFormList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceDeliveryForm testServiceDeliveryForm = serviceDeliveryFormList.get(serviceDeliveryFormList.size() - 1);
        assertThat(testServiceDeliveryForm.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceDeliveryForm.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceDeliveryForm.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceDeliveryForm.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceDeliveryForm.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceDeliveryForm.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testServiceDeliveryForm.getFormUrl()).isEqualTo(DEFAULT_FORM_URL);
        assertThat(testServiceDeliveryForm.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    public void createServiceDeliveryFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceDeliveryFormRepository.findAll().size();

        // Create the ServiceDeliveryForm with an existing ID
        serviceDeliveryForm.setId(1L);
        ServiceDeliveryFormDTO serviceDeliveryFormDTO = serviceDeliveryFormMapper.toDto(serviceDeliveryForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceDeliveryFormMockMvc.perform(post("/api/service-delivery-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDeliveryForm in the database
        List<ServiceDeliveryForm> serviceDeliveryFormList = serviceDeliveryFormRepository.findAll();
        assertThat(serviceDeliveryFormList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceDeliveryForms() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDeliveryForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME.toString())))
            .andExpect(jsonPath("$.[*].formUrl").value(hasItem(DEFAULT_FORM_URL.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceDeliveryForm() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get the serviceDeliveryForm
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms/{id}", serviceDeliveryForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceDeliveryForm.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME.toString()))
            .andExpect(jsonPath("$.formUrl").value(DEFAULT_FORM_URL.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceDeliveryFormShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceDeliveryFormList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryFormShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceDeliveryFormShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceDeliveryFormList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceDeliveryFormShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdBy is not null
        defaultServiceDeliveryFormShouldBeFound("createdBy.specified=true");

        // Get all the serviceDeliveryFormList where createdBy is null
        defaultServiceDeliveryFormShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceDeliveryFormShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceDeliveryFormList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryFormShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryFormShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceDeliveryFormList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceDeliveryFormShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where createdDateTime is not null
        defaultServiceDeliveryFormShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceDeliveryFormList where createdDateTime is null
        defaultServiceDeliveryFormShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceDeliveryFormShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceDeliveryFormList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryFormShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceDeliveryFormShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceDeliveryFormList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceDeliveryFormShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedBy is not null
        defaultServiceDeliveryFormShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceDeliveryFormList where modifiedBy is null
        defaultServiceDeliveryFormShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceDeliveryFormShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryFormList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryFormShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryFormShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceDeliveryFormList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceDeliveryFormShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where modifiedDateTime is not null
        defaultServiceDeliveryFormShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceDeliveryFormList where modifiedDateTime is null
        defaultServiceDeliveryFormShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where version equals to DEFAULT_VERSION
        defaultServiceDeliveryFormShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryFormList where version equals to UPDATED_VERSION
        defaultServiceDeliveryFormShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceDeliveryFormShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceDeliveryFormList where version equals to UPDATED_VERSION
        defaultServiceDeliveryFormShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where version is not null
        defaultServiceDeliveryFormShouldBeFound("version.specified=true");

        // Get all the serviceDeliveryFormList where version is null
        defaultServiceDeliveryFormShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where version greater than or equals to DEFAULT_VERSION
        defaultServiceDeliveryFormShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryFormList where version greater than or equals to UPDATED_VERSION
        defaultServiceDeliveryFormShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where version less than or equals to DEFAULT_VERSION
        defaultServiceDeliveryFormShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceDeliveryFormList where version less than or equals to UPDATED_VERSION
        defaultServiceDeliveryFormShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formName equals to DEFAULT_FORM_NAME
        defaultServiceDeliveryFormShouldBeFound("formName.equals=" + DEFAULT_FORM_NAME);

        // Get all the serviceDeliveryFormList where formName equals to UPDATED_FORM_NAME
        defaultServiceDeliveryFormShouldNotBeFound("formName.equals=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formName in DEFAULT_FORM_NAME or UPDATED_FORM_NAME
        defaultServiceDeliveryFormShouldBeFound("formName.in=" + DEFAULT_FORM_NAME + "," + UPDATED_FORM_NAME);

        // Get all the serviceDeliveryFormList where formName equals to UPDATED_FORM_NAME
        defaultServiceDeliveryFormShouldNotBeFound("formName.in=" + UPDATED_FORM_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formName is not null
        defaultServiceDeliveryFormShouldBeFound("formName.specified=true");

        // Get all the serviceDeliveryFormList where formName is null
        defaultServiceDeliveryFormShouldNotBeFound("formName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formUrl equals to DEFAULT_FORM_URL
        defaultServiceDeliveryFormShouldBeFound("formUrl.equals=" + DEFAULT_FORM_URL);

        // Get all the serviceDeliveryFormList where formUrl equals to UPDATED_FORM_URL
        defaultServiceDeliveryFormShouldNotBeFound("formUrl.equals=" + UPDATED_FORM_URL);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormUrlIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formUrl in DEFAULT_FORM_URL or UPDATED_FORM_URL
        defaultServiceDeliveryFormShouldBeFound("formUrl.in=" + DEFAULT_FORM_URL + "," + UPDATED_FORM_URL);

        // Get all the serviceDeliveryFormList where formUrl equals to UPDATED_FORM_URL
        defaultServiceDeliveryFormShouldNotBeFound("formUrl.in=" + UPDATED_FORM_URL);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByFormUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where formUrl is not null
        defaultServiceDeliveryFormShouldBeFound("formUrl.specified=true");

        // Get all the serviceDeliveryFormList where formUrl is null
        defaultServiceDeliveryFormShouldNotBeFound("formUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where source equals to DEFAULT_SOURCE
        defaultServiceDeliveryFormShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the serviceDeliveryFormList where source equals to UPDATED_SOURCE
        defaultServiceDeliveryFormShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultServiceDeliveryFormShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the serviceDeliveryFormList where source equals to UPDATED_SOURCE
        defaultServiceDeliveryFormShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        // Get all the serviceDeliveryFormList where source is not null
        defaultServiceDeliveryFormShouldBeFound("source.specified=true");

        // Get all the serviceDeliveryFormList where source is null
        defaultServiceDeliveryFormShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceDeliveryFormsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceDeliveryForm.setServiceRecord(serviceRecord);
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceDeliveryFormList where serviceRecord equals to serviceRecordId
        defaultServiceDeliveryFormShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceDeliveryFormList where serviceRecord equals to serviceRecordId + 1
        defaultServiceDeliveryFormShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceDeliveryFormShouldBeFound(String filter) throws Exception {
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceDeliveryForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].formUrl").value(hasItem(DEFAULT_FORM_URL)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)));

        // Check, that the count call also returns 1
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceDeliveryFormShouldNotBeFound(String filter) throws Exception {
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceDeliveryForm() throws Exception {
        // Get the serviceDeliveryForm
        restServiceDeliveryFormMockMvc.perform(get("/api/service-delivery-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceDeliveryForm() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        int databaseSizeBeforeUpdate = serviceDeliveryFormRepository.findAll().size();

        // Update the serviceDeliveryForm
        ServiceDeliveryForm updatedServiceDeliveryForm = serviceDeliveryFormRepository.findById(serviceDeliveryForm.getId()).get();
        // Disconnect from session so that the updates on updatedServiceDeliveryForm are not directly saved in db
        em.detach(updatedServiceDeliveryForm);
        updatedServiceDeliveryForm
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .formName(UPDATED_FORM_NAME)
            .formUrl(UPDATED_FORM_URL)
            .source(UPDATED_SOURCE);
        ServiceDeliveryFormDTO serviceDeliveryFormDTO = serviceDeliveryFormMapper.toDto(updatedServiceDeliveryForm);

        restServiceDeliveryFormMockMvc.perform(put("/api/service-delivery-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryFormDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceDeliveryForm in the database
        List<ServiceDeliveryForm> serviceDeliveryFormList = serviceDeliveryFormRepository.findAll();
        assertThat(serviceDeliveryFormList).hasSize(databaseSizeBeforeUpdate);
        ServiceDeliveryForm testServiceDeliveryForm = serviceDeliveryFormList.get(serviceDeliveryFormList.size() - 1);
        assertThat(testServiceDeliveryForm.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceDeliveryForm.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceDeliveryForm.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceDeliveryForm.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceDeliveryForm.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceDeliveryForm.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testServiceDeliveryForm.getFormUrl()).isEqualTo(UPDATED_FORM_URL);
        assertThat(testServiceDeliveryForm.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceDeliveryForm() throws Exception {
        int databaseSizeBeforeUpdate = serviceDeliveryFormRepository.findAll().size();

        // Create the ServiceDeliveryForm
        ServiceDeliveryFormDTO serviceDeliveryFormDTO = serviceDeliveryFormMapper.toDto(serviceDeliveryForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceDeliveryFormMockMvc.perform(put("/api/service-delivery-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceDeliveryFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceDeliveryForm in the database
        List<ServiceDeliveryForm> serviceDeliveryFormList = serviceDeliveryFormRepository.findAll();
        assertThat(serviceDeliveryFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceDeliveryForm() throws Exception {
        // Initialize the database
        serviceDeliveryFormRepository.saveAndFlush(serviceDeliveryForm);

        int databaseSizeBeforeDelete = serviceDeliveryFormRepository.findAll().size();

        // Delete the serviceDeliveryForm
        restServiceDeliveryFormMockMvc.perform(delete("/api/service-delivery-forms/{id}", serviceDeliveryForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceDeliveryForm> serviceDeliveryFormList = serviceDeliveryFormRepository.findAll();
        assertThat(serviceDeliveryFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDeliveryForm.class);
        ServiceDeliveryForm serviceDeliveryForm1 = new ServiceDeliveryForm();
        serviceDeliveryForm1.setId(1L);
        ServiceDeliveryForm serviceDeliveryForm2 = new ServiceDeliveryForm();
        serviceDeliveryForm2.setId(serviceDeliveryForm1.getId());
        assertThat(serviceDeliveryForm1).isEqualTo(serviceDeliveryForm2);
        serviceDeliveryForm2.setId(2L);
        assertThat(serviceDeliveryForm1).isNotEqualTo(serviceDeliveryForm2);
        serviceDeliveryForm1.setId(null);
        assertThat(serviceDeliveryForm1).isNotEqualTo(serviceDeliveryForm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDeliveryFormDTO.class);
        ServiceDeliveryFormDTO serviceDeliveryFormDTO1 = new ServiceDeliveryFormDTO();
        serviceDeliveryFormDTO1.setId(1L);
        ServiceDeliveryFormDTO serviceDeliveryFormDTO2 = new ServiceDeliveryFormDTO();
        assertThat(serviceDeliveryFormDTO1).isNotEqualTo(serviceDeliveryFormDTO2);
        serviceDeliveryFormDTO2.setId(serviceDeliveryFormDTO1.getId());
        assertThat(serviceDeliveryFormDTO1).isEqualTo(serviceDeliveryFormDTO2);
        serviceDeliveryFormDTO2.setId(2L);
        assertThat(serviceDeliveryFormDTO1).isNotEqualTo(serviceDeliveryFormDTO2);
        serviceDeliveryFormDTO1.setId(null);
        assertThat(serviceDeliveryFormDTO1).isNotEqualTo(serviceDeliveryFormDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceDeliveryFormMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceDeliveryFormMapper.fromId(null)).isNull();
    }
}
