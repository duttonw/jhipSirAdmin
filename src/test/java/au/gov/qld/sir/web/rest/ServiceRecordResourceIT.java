package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.ServiceDeliveryOrganisation;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.ServiceFranchise;
import au.gov.qld.sir.domain.ApplicationServiceOverride;
import au.gov.qld.sir.domain.IntegrationMapping;
import au.gov.qld.sir.domain.ServiceDelivery;
import au.gov.qld.sir.domain.ServiceDeliveryForm;
import au.gov.qld.sir.domain.ServiceDescription;
import au.gov.qld.sir.domain.ServiceEvent;
import au.gov.qld.sir.domain.ServiceEvidence;
import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.domain.ServiceName;
import au.gov.qld.sir.domain.ServiceRelationship;
import au.gov.qld.sir.domain.ServiceSupportRole;
import au.gov.qld.sir.domain.ServiceTagItems;
import au.gov.qld.sir.repository.ServiceRecordRepository;
import au.gov.qld.sir.service.ServiceRecordService;
import au.gov.qld.sir.service.dto.ServiceRecordDTO;
import au.gov.qld.sir.service.mapper.ServiceRecordMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceRecordCriteria;
import au.gov.qld.sir.service.ServiceRecordQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static au.gov.qld.sir.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ServiceRecordResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceRecordResourceIT {

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

    private static final String DEFAULT_ACTIVE = "A";
    private static final String UPDATED_ACTIVE = "B";

    private static final String DEFAULT_ELIGIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBILITY = "BBBBBBBBBB";

    private static final String DEFAULT_FEES = "AAAAAAAAAA";
    private static final String UPDATED_FEES = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INTERACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTERACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_REQUISITES = "AAAAAAAAAA";
    private static final String UPDATED_PRE_REQUISITES = "BBBBBBBBBB";

    private static final String DEFAULT_QGS_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_QGS_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALIDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_REQUISITES_NEW = "AAAAAAAAAA";
    private static final String UPDATED_PRE_REQUISITES_NEW = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_URL_NEW = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_URL_NEW = "BBBBBBBBBB";

    private static final String DEFAULT_ELIGIBILITY_NEW = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBILITY_NEW = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ROADMAP_LOGIN_REQUIRED = "A";
    private static final String UPDATED_ROADMAP_LOGIN_REQUIRED = "B";

    private static final String DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED = "A";
    private static final String UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED = "B";

    private static final String DEFAULT_ROADMAP_CUSTOMER_DETAILS = "A";
    private static final String UPDATED_ROADMAP_CUSTOMER_DETAILS = "B";

    private static final String DEFAULT_ROADMAP_IMPROVE_INTENTION = "A";
    private static final String UPDATED_ROADMAP_IMPROVE_INTENTION = "B";

    private static final String DEFAULT_ROADMAP_IMPROVE_FUTURE = "A";
    private static final String UPDATED_ROADMAP_IMPROVE_FUTURE = "B";

    private static final String DEFAULT_ROADMAP_IMPROVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_IMPROVE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ROADMAP_IMPROVE_WHEN = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_IMPROVE_WHEN = "BBBBBBBBBB";

    private static final String DEFAULT_ROADMAP_IMPROVE_HOW = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_IMPROVE_HOW = "BBBBBBBBBB";

    private static final String DEFAULT_ROADMAP_MATURITY_CURRENT = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_MATURITY_CURRENT = "BBBBBBBBBB";

    private static final String DEFAULT_ROADMAP_MATURITY_DESIRED = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_MATURITY_DESIRED = "BBBBBBBBBB";

    private static final String DEFAULT_ROADMAP_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ROADMAP_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_HOW_TO = "AAAAAAAAAA";
    private static final String UPDATED_HOW_TO = "BBBBBBBBBB";

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Autowired
    private ServiceRecordMapper serviceRecordMapper;

    @Autowired
    private ServiceRecordService serviceRecordService;

    @Autowired
    private ServiceRecordQueryService serviceRecordQueryService;

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

    private MockMvc restServiceRecordMockMvc;

    private ServiceRecord serviceRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRecordResource serviceRecordResource = new ServiceRecordResource(serviceRecordService, serviceRecordQueryService);
        this.restServiceRecordMockMvc = MockMvcBuilders.standaloneSetup(serviceRecordResource)
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
    public static ServiceRecord createEntity(EntityManager em) {
        ServiceRecord serviceRecord = new ServiceRecord()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .active(DEFAULT_ACTIVE)
            .eligibility(DEFAULT_ELIGIBILITY)
            .fees(DEFAULT_FEES)
            .groupHeader(DEFAULT_GROUP_HEADER)
            .groupId(DEFAULT_GROUP_ID)
            .interactionId(DEFAULT_INTERACTION_ID)
            .keywords(DEFAULT_KEYWORDS)
            .preRequisites(DEFAULT_PRE_REQUISITES)
            .qgsServiceId(DEFAULT_QGS_SERVICE_ID)
            .referenceUrl(DEFAULT_REFERENCE_URL)
            .serviceName(DEFAULT_SERVICE_NAME)
            .validatedDate(DEFAULT_VALIDATED_DATE)
            .description(DEFAULT_DESCRIPTION)
            .preRequisitesNew(DEFAULT_PRE_REQUISITES_NEW)
            .referenceUrlNew(DEFAULT_REFERENCE_URL_NEW)
            .eligibilityNew(DEFAULT_ELIGIBILITY_NEW)
            .serviceContext(DEFAULT_SERVICE_CONTEXT)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .roadmapLoginRequired(DEFAULT_ROADMAP_LOGIN_REQUIRED)
            .roadmapCustomerIdRequired(DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED)
            .roadmapCustomerDetails(DEFAULT_ROADMAP_CUSTOMER_DETAILS)
            .roadmapImproveIntention(DEFAULT_ROADMAP_IMPROVE_INTENTION)
            .roadmapImproveFuture(DEFAULT_ROADMAP_IMPROVE_FUTURE)
            .roadmapImproveType(DEFAULT_ROADMAP_IMPROVE_TYPE)
            .roadmapImproveWhen(DEFAULT_ROADMAP_IMPROVE_WHEN)
            .roadmapImproveHow(DEFAULT_ROADMAP_IMPROVE_HOW)
            .roadmapMaturityCurrent(DEFAULT_ROADMAP_MATURITY_CURRENT)
            .roadmapMaturityDesired(DEFAULT_ROADMAP_MATURITY_DESIRED)
            .roadmapComments(DEFAULT_ROADMAP_COMMENTS)
            .howTo(DEFAULT_HOW_TO);
        return serviceRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRecord createUpdatedEntity(EntityManager em) {
        ServiceRecord serviceRecord = new ServiceRecord()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .active(UPDATED_ACTIVE)
            .eligibility(UPDATED_ELIGIBILITY)
            .fees(UPDATED_FEES)
            .groupHeader(UPDATED_GROUP_HEADER)
            .groupId(UPDATED_GROUP_ID)
            .interactionId(UPDATED_INTERACTION_ID)
            .keywords(UPDATED_KEYWORDS)
            .preRequisites(UPDATED_PRE_REQUISITES)
            .qgsServiceId(UPDATED_QGS_SERVICE_ID)
            .referenceUrl(UPDATED_REFERENCE_URL)
            .serviceName(UPDATED_SERVICE_NAME)
            .validatedDate(UPDATED_VALIDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .preRequisitesNew(UPDATED_PRE_REQUISITES_NEW)
            .referenceUrlNew(UPDATED_REFERENCE_URL_NEW)
            .eligibilityNew(UPDATED_ELIGIBILITY_NEW)
            .serviceContext(UPDATED_SERVICE_CONTEXT)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .roadmapLoginRequired(UPDATED_ROADMAP_LOGIN_REQUIRED)
            .roadmapCustomerIdRequired(UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED)
            .roadmapCustomerDetails(UPDATED_ROADMAP_CUSTOMER_DETAILS)
            .roadmapImproveIntention(UPDATED_ROADMAP_IMPROVE_INTENTION)
            .roadmapImproveFuture(UPDATED_ROADMAP_IMPROVE_FUTURE)
            .roadmapImproveType(UPDATED_ROADMAP_IMPROVE_TYPE)
            .roadmapImproveWhen(UPDATED_ROADMAP_IMPROVE_WHEN)
            .roadmapImproveHow(UPDATED_ROADMAP_IMPROVE_HOW)
            .roadmapMaturityCurrent(UPDATED_ROADMAP_MATURITY_CURRENT)
            .roadmapMaturityDesired(UPDATED_ROADMAP_MATURITY_DESIRED)
            .roadmapComments(UPDATED_ROADMAP_COMMENTS)
            .howTo(UPDATED_HOW_TO);
        return serviceRecord;
    }

    @BeforeEach
    public void initTest() {
        serviceRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRecord() throws Exception {
        int databaseSizeBeforeCreate = serviceRecordRepository.findAll().size();

        // Create the ServiceRecord
        ServiceRecordDTO serviceRecordDTO = serviceRecordMapper.toDto(serviceRecord);
        restServiceRecordMockMvc.perform(post("/api/service-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRecord in the database
        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRecord testServiceRecord = serviceRecordList.get(serviceRecordList.size() - 1);
        assertThat(testServiceRecord.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceRecord.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceRecord.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceRecord.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceRecord.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceRecord.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testServiceRecord.getEligibility()).isEqualTo(DEFAULT_ELIGIBILITY);
        assertThat(testServiceRecord.getFees()).isEqualTo(DEFAULT_FEES);
        assertThat(testServiceRecord.getGroupHeader()).isEqualTo(DEFAULT_GROUP_HEADER);
        assertThat(testServiceRecord.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testServiceRecord.getInteractionId()).isEqualTo(DEFAULT_INTERACTION_ID);
        assertThat(testServiceRecord.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testServiceRecord.getPreRequisites()).isEqualTo(DEFAULT_PRE_REQUISITES);
        assertThat(testServiceRecord.getQgsServiceId()).isEqualTo(DEFAULT_QGS_SERVICE_ID);
        assertThat(testServiceRecord.getReferenceUrl()).isEqualTo(DEFAULT_REFERENCE_URL);
        assertThat(testServiceRecord.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testServiceRecord.getValidatedDate()).isEqualTo(DEFAULT_VALIDATED_DATE);
        assertThat(testServiceRecord.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceRecord.getPreRequisitesNew()).isEqualTo(DEFAULT_PRE_REQUISITES_NEW);
        assertThat(testServiceRecord.getReferenceUrlNew()).isEqualTo(DEFAULT_REFERENCE_URL_NEW);
        assertThat(testServiceRecord.getEligibilityNew()).isEqualTo(DEFAULT_ELIGIBILITY_NEW);
        assertThat(testServiceRecord.getServiceContext()).isEqualTo(DEFAULT_SERVICE_CONTEXT);
        assertThat(testServiceRecord.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testServiceRecord.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceRecord.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testServiceRecord.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testServiceRecord.getRoadmapLoginRequired()).isEqualTo(DEFAULT_ROADMAP_LOGIN_REQUIRED);
        assertThat(testServiceRecord.getRoadmapCustomerIdRequired()).isEqualTo(DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED);
        assertThat(testServiceRecord.getRoadmapCustomerDetails()).isEqualTo(DEFAULT_ROADMAP_CUSTOMER_DETAILS);
        assertThat(testServiceRecord.getRoadmapImproveIntention()).isEqualTo(DEFAULT_ROADMAP_IMPROVE_INTENTION);
        assertThat(testServiceRecord.getRoadmapImproveFuture()).isEqualTo(DEFAULT_ROADMAP_IMPROVE_FUTURE);
        assertThat(testServiceRecord.getRoadmapImproveType()).isEqualTo(DEFAULT_ROADMAP_IMPROVE_TYPE);
        assertThat(testServiceRecord.getRoadmapImproveWhen()).isEqualTo(DEFAULT_ROADMAP_IMPROVE_WHEN);
        assertThat(testServiceRecord.getRoadmapImproveHow()).isEqualTo(DEFAULT_ROADMAP_IMPROVE_HOW);
        assertThat(testServiceRecord.getRoadmapMaturityCurrent()).isEqualTo(DEFAULT_ROADMAP_MATURITY_CURRENT);
        assertThat(testServiceRecord.getRoadmapMaturityDesired()).isEqualTo(DEFAULT_ROADMAP_MATURITY_DESIRED);
        assertThat(testServiceRecord.getRoadmapComments()).isEqualTo(DEFAULT_ROADMAP_COMMENTS);
        assertThat(testServiceRecord.getHowTo()).isEqualTo(DEFAULT_HOW_TO);
    }

    @Test
    @Transactional
    public void createServiceRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRecordRepository.findAll().size();

        // Create the ServiceRecord with an existing ID
        serviceRecord.setId(1L);
        ServiceRecordDTO serviceRecordDTO = serviceRecordMapper.toDto(serviceRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRecordMockMvc.perform(post("/api/service-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRecord in the database
        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRecordRepository.findAll().size();
        // set the field null
        serviceRecord.setName(null);

        // Create the ServiceRecord, which fails.
        ServiceRecordDTO serviceRecordDTO = serviceRecordMapper.toDto(serviceRecord);

        restServiceRecordMockMvc.perform(post("/api/service-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRecordDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceRecords() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList
        restServiceRecordMockMvc.perform(get("/api/service-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].eligibility").value(hasItem(DEFAULT_ELIGIBILITY.toString())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.toString())))
            .andExpect(jsonPath("$.[*].groupHeader").value(hasItem(DEFAULT_GROUP_HEADER.toString())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.toString())))
            .andExpect(jsonPath("$.[*].interactionId").value(hasItem(DEFAULT_INTERACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].preRequisites").value(hasItem(DEFAULT_PRE_REQUISITES.toString())))
            .andExpect(jsonPath("$.[*].qgsServiceId").value(hasItem(DEFAULT_QGS_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].referenceUrl").value(hasItem(DEFAULT_REFERENCE_URL.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].validatedDate").value(hasItem(DEFAULT_VALIDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].preRequisitesNew").value(hasItem(DEFAULT_PRE_REQUISITES_NEW.toString())))
            .andExpect(jsonPath("$.[*].referenceUrlNew").value(hasItem(DEFAULT_REFERENCE_URL_NEW.toString())))
            .andExpect(jsonPath("$.[*].eligibilityNew").value(hasItem(DEFAULT_ELIGIBILITY_NEW.toString())))
            .andExpect(jsonPath("$.[*].serviceContext").value(hasItem(DEFAULT_SERVICE_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].roadmapLoginRequired").value(hasItem(DEFAULT_ROADMAP_LOGIN_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].roadmapCustomerIdRequired").value(hasItem(DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].roadmapCustomerDetails").value(hasItem(DEFAULT_ROADMAP_CUSTOMER_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].roadmapImproveIntention").value(hasItem(DEFAULT_ROADMAP_IMPROVE_INTENTION.toString())))
            .andExpect(jsonPath("$.[*].roadmapImproveFuture").value(hasItem(DEFAULT_ROADMAP_IMPROVE_FUTURE.toString())))
            .andExpect(jsonPath("$.[*].roadmapImproveType").value(hasItem(DEFAULT_ROADMAP_IMPROVE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].roadmapImproveWhen").value(hasItem(DEFAULT_ROADMAP_IMPROVE_WHEN.toString())))
            .andExpect(jsonPath("$.[*].roadmapImproveHow").value(hasItem(DEFAULT_ROADMAP_IMPROVE_HOW.toString())))
            .andExpect(jsonPath("$.[*].roadmapMaturityCurrent").value(hasItem(DEFAULT_ROADMAP_MATURITY_CURRENT.toString())))
            .andExpect(jsonPath("$.[*].roadmapMaturityDesired").value(hasItem(DEFAULT_ROADMAP_MATURITY_DESIRED.toString())))
            .andExpect(jsonPath("$.[*].roadmapComments").value(hasItem(DEFAULT_ROADMAP_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].howTo").value(hasItem(DEFAULT_HOW_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceRecord() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get the serviceRecord
        restServiceRecordMockMvc.perform(get("/api/service-records/{id}", serviceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRecord.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.toString()))
            .andExpect(jsonPath("$.eligibility").value(DEFAULT_ELIGIBILITY.toString()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.toString()))
            .andExpect(jsonPath("$.groupHeader").value(DEFAULT_GROUP_HEADER.toString()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.toString()))
            .andExpect(jsonPath("$.interactionId").value(DEFAULT_INTERACTION_ID.toString()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.preRequisites").value(DEFAULT_PRE_REQUISITES.toString()))
            .andExpect(jsonPath("$.qgsServiceId").value(DEFAULT_QGS_SERVICE_ID.toString()))
            .andExpect(jsonPath("$.referenceUrl").value(DEFAULT_REFERENCE_URL.toString()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.validatedDate").value(DEFAULT_VALIDATED_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.preRequisitesNew").value(DEFAULT_PRE_REQUISITES_NEW.toString()))
            .andExpect(jsonPath("$.referenceUrlNew").value(DEFAULT_REFERENCE_URL_NEW.toString()))
            .andExpect(jsonPath("$.eligibilityNew").value(DEFAULT_ELIGIBILITY_NEW.toString()))
            .andExpect(jsonPath("$.serviceContext").value(DEFAULT_SERVICE_CONTEXT.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.roadmapLoginRequired").value(DEFAULT_ROADMAP_LOGIN_REQUIRED.toString()))
            .andExpect(jsonPath("$.roadmapCustomerIdRequired").value(DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED.toString()))
            .andExpect(jsonPath("$.roadmapCustomerDetails").value(DEFAULT_ROADMAP_CUSTOMER_DETAILS.toString()))
            .andExpect(jsonPath("$.roadmapImproveIntention").value(DEFAULT_ROADMAP_IMPROVE_INTENTION.toString()))
            .andExpect(jsonPath("$.roadmapImproveFuture").value(DEFAULT_ROADMAP_IMPROVE_FUTURE.toString()))
            .andExpect(jsonPath("$.roadmapImproveType").value(DEFAULT_ROADMAP_IMPROVE_TYPE.toString()))
            .andExpect(jsonPath("$.roadmapImproveWhen").value(DEFAULT_ROADMAP_IMPROVE_WHEN.toString()))
            .andExpect(jsonPath("$.roadmapImproveHow").value(DEFAULT_ROADMAP_IMPROVE_HOW.toString()))
            .andExpect(jsonPath("$.roadmapMaturityCurrent").value(DEFAULT_ROADMAP_MATURITY_CURRENT.toString()))
            .andExpect(jsonPath("$.roadmapMaturityDesired").value(DEFAULT_ROADMAP_MATURITY_DESIRED.toString()))
            .andExpect(jsonPath("$.roadmapComments").value(DEFAULT_ROADMAP_COMMENTS.toString()))
            .andExpect(jsonPath("$.howTo").value(DEFAULT_HOW_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceRecordShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceRecordList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRecordShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceRecordShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceRecordList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceRecordShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdBy is not null
        defaultServiceRecordShouldBeFound("createdBy.specified=true");

        // Get all the serviceRecordList where createdBy is null
        defaultServiceRecordShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceRecordShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceRecordList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRecordShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceRecordShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceRecordList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceRecordShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where createdDateTime is not null
        defaultServiceRecordShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceRecordList where createdDateTime is null
        defaultServiceRecordShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceRecordShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceRecordList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRecordShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceRecordShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceRecordList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceRecordShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedBy is not null
        defaultServiceRecordShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceRecordList where modifiedBy is null
        defaultServiceRecordShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceRecordShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceRecordList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRecordShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceRecordShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceRecordList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceRecordShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where modifiedDateTime is not null
        defaultServiceRecordShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceRecordList where modifiedDateTime is null
        defaultServiceRecordShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where version equals to DEFAULT_VERSION
        defaultServiceRecordShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceRecordList where version equals to UPDATED_VERSION
        defaultServiceRecordShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceRecordShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceRecordList where version equals to UPDATED_VERSION
        defaultServiceRecordShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where version is not null
        defaultServiceRecordShouldBeFound("version.specified=true");

        // Get all the serviceRecordList where version is null
        defaultServiceRecordShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where version greater than or equals to DEFAULT_VERSION
        defaultServiceRecordShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceRecordList where version greater than or equals to UPDATED_VERSION
        defaultServiceRecordShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where version less than or equals to DEFAULT_VERSION
        defaultServiceRecordShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceRecordList where version less than or equals to UPDATED_VERSION
        defaultServiceRecordShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where active equals to DEFAULT_ACTIVE
        defaultServiceRecordShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the serviceRecordList where active equals to UPDATED_ACTIVE
        defaultServiceRecordShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultServiceRecordShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the serviceRecordList where active equals to UPDATED_ACTIVE
        defaultServiceRecordShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where active is not null
        defaultServiceRecordShouldBeFound("active.specified=true");

        // Get all the serviceRecordList where active is null
        defaultServiceRecordShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibility equals to DEFAULT_ELIGIBILITY
        defaultServiceRecordShouldBeFound("eligibility.equals=" + DEFAULT_ELIGIBILITY);

        // Get all the serviceRecordList where eligibility equals to UPDATED_ELIGIBILITY
        defaultServiceRecordShouldNotBeFound("eligibility.equals=" + UPDATED_ELIGIBILITY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibility in DEFAULT_ELIGIBILITY or UPDATED_ELIGIBILITY
        defaultServiceRecordShouldBeFound("eligibility.in=" + DEFAULT_ELIGIBILITY + "," + UPDATED_ELIGIBILITY);

        // Get all the serviceRecordList where eligibility equals to UPDATED_ELIGIBILITY
        defaultServiceRecordShouldNotBeFound("eligibility.in=" + UPDATED_ELIGIBILITY);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibility is not null
        defaultServiceRecordShouldBeFound("eligibility.specified=true");

        // Get all the serviceRecordList where eligibility is null
        defaultServiceRecordShouldNotBeFound("eligibility.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where fees equals to DEFAULT_FEES
        defaultServiceRecordShouldBeFound("fees.equals=" + DEFAULT_FEES);

        // Get all the serviceRecordList where fees equals to UPDATED_FEES
        defaultServiceRecordShouldNotBeFound("fees.equals=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByFeesIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where fees in DEFAULT_FEES or UPDATED_FEES
        defaultServiceRecordShouldBeFound("fees.in=" + DEFAULT_FEES + "," + UPDATED_FEES);

        // Get all the serviceRecordList where fees equals to UPDATED_FEES
        defaultServiceRecordShouldNotBeFound("fees.in=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByFeesIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where fees is not null
        defaultServiceRecordShouldBeFound("fees.specified=true");

        // Get all the serviceRecordList where fees is null
        defaultServiceRecordShouldNotBeFound("fees.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupHeaderIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupHeader equals to DEFAULT_GROUP_HEADER
        defaultServiceRecordShouldBeFound("groupHeader.equals=" + DEFAULT_GROUP_HEADER);

        // Get all the serviceRecordList where groupHeader equals to UPDATED_GROUP_HEADER
        defaultServiceRecordShouldNotBeFound("groupHeader.equals=" + UPDATED_GROUP_HEADER);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupHeaderIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupHeader in DEFAULT_GROUP_HEADER or UPDATED_GROUP_HEADER
        defaultServiceRecordShouldBeFound("groupHeader.in=" + DEFAULT_GROUP_HEADER + "," + UPDATED_GROUP_HEADER);

        // Get all the serviceRecordList where groupHeader equals to UPDATED_GROUP_HEADER
        defaultServiceRecordShouldNotBeFound("groupHeader.in=" + UPDATED_GROUP_HEADER);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupHeaderIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupHeader is not null
        defaultServiceRecordShouldBeFound("groupHeader.specified=true");

        // Get all the serviceRecordList where groupHeader is null
        defaultServiceRecordShouldNotBeFound("groupHeader.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupId equals to DEFAULT_GROUP_ID
        defaultServiceRecordShouldBeFound("groupId.equals=" + DEFAULT_GROUP_ID);

        // Get all the serviceRecordList where groupId equals to UPDATED_GROUP_ID
        defaultServiceRecordShouldNotBeFound("groupId.equals=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupId in DEFAULT_GROUP_ID or UPDATED_GROUP_ID
        defaultServiceRecordShouldBeFound("groupId.in=" + DEFAULT_GROUP_ID + "," + UPDATED_GROUP_ID);

        // Get all the serviceRecordList where groupId equals to UPDATED_GROUP_ID
        defaultServiceRecordShouldNotBeFound("groupId.in=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByGroupIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where groupId is not null
        defaultServiceRecordShouldBeFound("groupId.specified=true");

        // Get all the serviceRecordList where groupId is null
        defaultServiceRecordShouldNotBeFound("groupId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByInteractionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where interactionId equals to DEFAULT_INTERACTION_ID
        defaultServiceRecordShouldBeFound("interactionId.equals=" + DEFAULT_INTERACTION_ID);

        // Get all the serviceRecordList where interactionId equals to UPDATED_INTERACTION_ID
        defaultServiceRecordShouldNotBeFound("interactionId.equals=" + UPDATED_INTERACTION_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByInteractionIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where interactionId in DEFAULT_INTERACTION_ID or UPDATED_INTERACTION_ID
        defaultServiceRecordShouldBeFound("interactionId.in=" + DEFAULT_INTERACTION_ID + "," + UPDATED_INTERACTION_ID);

        // Get all the serviceRecordList where interactionId equals to UPDATED_INTERACTION_ID
        defaultServiceRecordShouldNotBeFound("interactionId.in=" + UPDATED_INTERACTION_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByInteractionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where interactionId is not null
        defaultServiceRecordShouldBeFound("interactionId.specified=true");

        // Get all the serviceRecordList where interactionId is null
        defaultServiceRecordShouldNotBeFound("interactionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByKeywordsIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where keywords equals to DEFAULT_KEYWORDS
        defaultServiceRecordShouldBeFound("keywords.equals=" + DEFAULT_KEYWORDS);

        // Get all the serviceRecordList where keywords equals to UPDATED_KEYWORDS
        defaultServiceRecordShouldNotBeFound("keywords.equals=" + UPDATED_KEYWORDS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByKeywordsIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where keywords in DEFAULT_KEYWORDS or UPDATED_KEYWORDS
        defaultServiceRecordShouldBeFound("keywords.in=" + DEFAULT_KEYWORDS + "," + UPDATED_KEYWORDS);

        // Get all the serviceRecordList where keywords equals to UPDATED_KEYWORDS
        defaultServiceRecordShouldNotBeFound("keywords.in=" + UPDATED_KEYWORDS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByKeywordsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where keywords is not null
        defaultServiceRecordShouldBeFound("keywords.specified=true");

        // Get all the serviceRecordList where keywords is null
        defaultServiceRecordShouldNotBeFound("keywords.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisites equals to DEFAULT_PRE_REQUISITES
        defaultServiceRecordShouldBeFound("preRequisites.equals=" + DEFAULT_PRE_REQUISITES);

        // Get all the serviceRecordList where preRequisites equals to UPDATED_PRE_REQUISITES
        defaultServiceRecordShouldNotBeFound("preRequisites.equals=" + UPDATED_PRE_REQUISITES);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisites in DEFAULT_PRE_REQUISITES or UPDATED_PRE_REQUISITES
        defaultServiceRecordShouldBeFound("preRequisites.in=" + DEFAULT_PRE_REQUISITES + "," + UPDATED_PRE_REQUISITES);

        // Get all the serviceRecordList where preRequisites equals to UPDATED_PRE_REQUISITES
        defaultServiceRecordShouldNotBeFound("preRequisites.in=" + UPDATED_PRE_REQUISITES);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisites is not null
        defaultServiceRecordShouldBeFound("preRequisites.specified=true");

        // Get all the serviceRecordList where preRequisites is null
        defaultServiceRecordShouldNotBeFound("preRequisites.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByQgsServiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where qgsServiceId equals to DEFAULT_QGS_SERVICE_ID
        defaultServiceRecordShouldBeFound("qgsServiceId.equals=" + DEFAULT_QGS_SERVICE_ID);

        // Get all the serviceRecordList where qgsServiceId equals to UPDATED_QGS_SERVICE_ID
        defaultServiceRecordShouldNotBeFound("qgsServiceId.equals=" + UPDATED_QGS_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByQgsServiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where qgsServiceId in DEFAULT_QGS_SERVICE_ID or UPDATED_QGS_SERVICE_ID
        defaultServiceRecordShouldBeFound("qgsServiceId.in=" + DEFAULT_QGS_SERVICE_ID + "," + UPDATED_QGS_SERVICE_ID);

        // Get all the serviceRecordList where qgsServiceId equals to UPDATED_QGS_SERVICE_ID
        defaultServiceRecordShouldNotBeFound("qgsServiceId.in=" + UPDATED_QGS_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByQgsServiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where qgsServiceId is not null
        defaultServiceRecordShouldBeFound("qgsServiceId.specified=true");

        // Get all the serviceRecordList where qgsServiceId is null
        defaultServiceRecordShouldNotBeFound("qgsServiceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrl equals to DEFAULT_REFERENCE_URL
        defaultServiceRecordShouldBeFound("referenceUrl.equals=" + DEFAULT_REFERENCE_URL);

        // Get all the serviceRecordList where referenceUrl equals to UPDATED_REFERENCE_URL
        defaultServiceRecordShouldNotBeFound("referenceUrl.equals=" + UPDATED_REFERENCE_URL);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrl in DEFAULT_REFERENCE_URL or UPDATED_REFERENCE_URL
        defaultServiceRecordShouldBeFound("referenceUrl.in=" + DEFAULT_REFERENCE_URL + "," + UPDATED_REFERENCE_URL);

        // Get all the serviceRecordList where referenceUrl equals to UPDATED_REFERENCE_URL
        defaultServiceRecordShouldNotBeFound("referenceUrl.in=" + UPDATED_REFERENCE_URL);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrl is not null
        defaultServiceRecordShouldBeFound("referenceUrl.specified=true");

        // Get all the serviceRecordList where referenceUrl is null
        defaultServiceRecordShouldNotBeFound("referenceUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultServiceRecordShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceRecordList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceRecordShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultServiceRecordShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the serviceRecordList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceRecordShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceName is not null
        defaultServiceRecordShouldBeFound("serviceName.specified=true");

        // Get all the serviceRecordList where serviceName is null
        defaultServiceRecordShouldNotBeFound("serviceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByValidatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where validatedDate equals to DEFAULT_VALIDATED_DATE
        defaultServiceRecordShouldBeFound("validatedDate.equals=" + DEFAULT_VALIDATED_DATE);

        // Get all the serviceRecordList where validatedDate equals to UPDATED_VALIDATED_DATE
        defaultServiceRecordShouldNotBeFound("validatedDate.equals=" + UPDATED_VALIDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByValidatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where validatedDate in DEFAULT_VALIDATED_DATE or UPDATED_VALIDATED_DATE
        defaultServiceRecordShouldBeFound("validatedDate.in=" + DEFAULT_VALIDATED_DATE + "," + UPDATED_VALIDATED_DATE);

        // Get all the serviceRecordList where validatedDate equals to UPDATED_VALIDATED_DATE
        defaultServiceRecordShouldNotBeFound("validatedDate.in=" + UPDATED_VALIDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByValidatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where validatedDate is not null
        defaultServiceRecordShouldBeFound("validatedDate.specified=true");

        // Get all the serviceRecordList where validatedDate is null
        defaultServiceRecordShouldNotBeFound("validatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where description equals to DEFAULT_DESCRIPTION
        defaultServiceRecordShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceRecordList where description equals to UPDATED_DESCRIPTION
        defaultServiceRecordShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultServiceRecordShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the serviceRecordList where description equals to UPDATED_DESCRIPTION
        defaultServiceRecordShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where description is not null
        defaultServiceRecordShouldBeFound("description.specified=true");

        // Get all the serviceRecordList where description is null
        defaultServiceRecordShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesNewIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisitesNew equals to DEFAULT_PRE_REQUISITES_NEW
        defaultServiceRecordShouldBeFound("preRequisitesNew.equals=" + DEFAULT_PRE_REQUISITES_NEW);

        // Get all the serviceRecordList where preRequisitesNew equals to UPDATED_PRE_REQUISITES_NEW
        defaultServiceRecordShouldNotBeFound("preRequisitesNew.equals=" + UPDATED_PRE_REQUISITES_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesNewIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisitesNew in DEFAULT_PRE_REQUISITES_NEW or UPDATED_PRE_REQUISITES_NEW
        defaultServiceRecordShouldBeFound("preRequisitesNew.in=" + DEFAULT_PRE_REQUISITES_NEW + "," + UPDATED_PRE_REQUISITES_NEW);

        // Get all the serviceRecordList where preRequisitesNew equals to UPDATED_PRE_REQUISITES_NEW
        defaultServiceRecordShouldNotBeFound("preRequisitesNew.in=" + UPDATED_PRE_REQUISITES_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByPreRequisitesNewIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where preRequisitesNew is not null
        defaultServiceRecordShouldBeFound("preRequisitesNew.specified=true");

        // Get all the serviceRecordList where preRequisitesNew is null
        defaultServiceRecordShouldNotBeFound("preRequisitesNew.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlNewIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrlNew equals to DEFAULT_REFERENCE_URL_NEW
        defaultServiceRecordShouldBeFound("referenceUrlNew.equals=" + DEFAULT_REFERENCE_URL_NEW);

        // Get all the serviceRecordList where referenceUrlNew equals to UPDATED_REFERENCE_URL_NEW
        defaultServiceRecordShouldNotBeFound("referenceUrlNew.equals=" + UPDATED_REFERENCE_URL_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlNewIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrlNew in DEFAULT_REFERENCE_URL_NEW or UPDATED_REFERENCE_URL_NEW
        defaultServiceRecordShouldBeFound("referenceUrlNew.in=" + DEFAULT_REFERENCE_URL_NEW + "," + UPDATED_REFERENCE_URL_NEW);

        // Get all the serviceRecordList where referenceUrlNew equals to UPDATED_REFERENCE_URL_NEW
        defaultServiceRecordShouldNotBeFound("referenceUrlNew.in=" + UPDATED_REFERENCE_URL_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByReferenceUrlNewIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where referenceUrlNew is not null
        defaultServiceRecordShouldBeFound("referenceUrlNew.specified=true");

        // Get all the serviceRecordList where referenceUrlNew is null
        defaultServiceRecordShouldNotBeFound("referenceUrlNew.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityNewIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibilityNew equals to DEFAULT_ELIGIBILITY_NEW
        defaultServiceRecordShouldBeFound("eligibilityNew.equals=" + DEFAULT_ELIGIBILITY_NEW);

        // Get all the serviceRecordList where eligibilityNew equals to UPDATED_ELIGIBILITY_NEW
        defaultServiceRecordShouldNotBeFound("eligibilityNew.equals=" + UPDATED_ELIGIBILITY_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityNewIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibilityNew in DEFAULT_ELIGIBILITY_NEW or UPDATED_ELIGIBILITY_NEW
        defaultServiceRecordShouldBeFound("eligibilityNew.in=" + DEFAULT_ELIGIBILITY_NEW + "," + UPDATED_ELIGIBILITY_NEW);

        // Get all the serviceRecordList where eligibilityNew equals to UPDATED_ELIGIBILITY_NEW
        defaultServiceRecordShouldNotBeFound("eligibilityNew.in=" + UPDATED_ELIGIBILITY_NEW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEligibilityNewIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where eligibilityNew is not null
        defaultServiceRecordShouldBeFound("eligibilityNew.specified=true");

        // Get all the serviceRecordList where eligibilityNew is null
        defaultServiceRecordShouldNotBeFound("eligibilityNew.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceContextIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceContext equals to DEFAULT_SERVICE_CONTEXT
        defaultServiceRecordShouldBeFound("serviceContext.equals=" + DEFAULT_SERVICE_CONTEXT);

        // Get all the serviceRecordList where serviceContext equals to UPDATED_SERVICE_CONTEXT
        defaultServiceRecordShouldNotBeFound("serviceContext.equals=" + UPDATED_SERVICE_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceContextIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceContext in DEFAULT_SERVICE_CONTEXT or UPDATED_SERVICE_CONTEXT
        defaultServiceRecordShouldBeFound("serviceContext.in=" + DEFAULT_SERVICE_CONTEXT + "," + UPDATED_SERVICE_CONTEXT);

        // Get all the serviceRecordList where serviceContext equals to UPDATED_SERVICE_CONTEXT
        defaultServiceRecordShouldNotBeFound("serviceContext.in=" + UPDATED_SERVICE_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByServiceContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where serviceContext is not null
        defaultServiceRecordShouldBeFound("serviceContext.specified=true");

        // Get all the serviceRecordList where serviceContext is null
        defaultServiceRecordShouldNotBeFound("serviceContext.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByLongDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where longDescription equals to DEFAULT_LONG_DESCRIPTION
        defaultServiceRecordShouldBeFound("longDescription.equals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the serviceRecordList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultServiceRecordShouldNotBeFound("longDescription.equals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByLongDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where longDescription in DEFAULT_LONG_DESCRIPTION or UPDATED_LONG_DESCRIPTION
        defaultServiceRecordShouldBeFound("longDescription.in=" + DEFAULT_LONG_DESCRIPTION + "," + UPDATED_LONG_DESCRIPTION);

        // Get all the serviceRecordList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultServiceRecordShouldNotBeFound("longDescription.in=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByLongDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where longDescription is not null
        defaultServiceRecordShouldBeFound("longDescription.specified=true");

        // Get all the serviceRecordList where longDescription is null
        defaultServiceRecordShouldNotBeFound("longDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where name equals to DEFAULT_NAME
        defaultServiceRecordShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the serviceRecordList where name equals to UPDATED_NAME
        defaultServiceRecordShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where name in DEFAULT_NAME or UPDATED_NAME
        defaultServiceRecordShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the serviceRecordList where name equals to UPDATED_NAME
        defaultServiceRecordShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where name is not null
        defaultServiceRecordShouldBeFound("name.specified=true");

        // Get all the serviceRecordList where name is null
        defaultServiceRecordShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where startDate equals to DEFAULT_START_DATE
        defaultServiceRecordShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the serviceRecordList where startDate equals to UPDATED_START_DATE
        defaultServiceRecordShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultServiceRecordShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the serviceRecordList where startDate equals to UPDATED_START_DATE
        defaultServiceRecordShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where startDate is not null
        defaultServiceRecordShouldBeFound("startDate.specified=true");

        // Get all the serviceRecordList where startDate is null
        defaultServiceRecordShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where startDate greater than or equals to DEFAULT_START_DATE
        defaultServiceRecordShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the serviceRecordList where startDate greater than or equals to UPDATED_START_DATE
        defaultServiceRecordShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where startDate less than or equals to DEFAULT_START_DATE
        defaultServiceRecordShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the serviceRecordList where startDate less than or equals to UPDATED_START_DATE
        defaultServiceRecordShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where endDate equals to DEFAULT_END_DATE
        defaultServiceRecordShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the serviceRecordList where endDate equals to UPDATED_END_DATE
        defaultServiceRecordShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultServiceRecordShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the serviceRecordList where endDate equals to UPDATED_END_DATE
        defaultServiceRecordShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where endDate is not null
        defaultServiceRecordShouldBeFound("endDate.specified=true");

        // Get all the serviceRecordList where endDate is null
        defaultServiceRecordShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where endDate greater than or equals to DEFAULT_END_DATE
        defaultServiceRecordShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the serviceRecordList where endDate greater than or equals to UPDATED_END_DATE
        defaultServiceRecordShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where endDate less than or equals to DEFAULT_END_DATE
        defaultServiceRecordShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the serviceRecordList where endDate less than or equals to UPDATED_END_DATE
        defaultServiceRecordShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapLoginRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapLoginRequired equals to DEFAULT_ROADMAP_LOGIN_REQUIRED
        defaultServiceRecordShouldBeFound("roadmapLoginRequired.equals=" + DEFAULT_ROADMAP_LOGIN_REQUIRED);

        // Get all the serviceRecordList where roadmapLoginRequired equals to UPDATED_ROADMAP_LOGIN_REQUIRED
        defaultServiceRecordShouldNotBeFound("roadmapLoginRequired.equals=" + UPDATED_ROADMAP_LOGIN_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapLoginRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapLoginRequired in DEFAULT_ROADMAP_LOGIN_REQUIRED or UPDATED_ROADMAP_LOGIN_REQUIRED
        defaultServiceRecordShouldBeFound("roadmapLoginRequired.in=" + DEFAULT_ROADMAP_LOGIN_REQUIRED + "," + UPDATED_ROADMAP_LOGIN_REQUIRED);

        // Get all the serviceRecordList where roadmapLoginRequired equals to UPDATED_ROADMAP_LOGIN_REQUIRED
        defaultServiceRecordShouldNotBeFound("roadmapLoginRequired.in=" + UPDATED_ROADMAP_LOGIN_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapLoginRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapLoginRequired is not null
        defaultServiceRecordShouldBeFound("roadmapLoginRequired.specified=true");

        // Get all the serviceRecordList where roadmapLoginRequired is null
        defaultServiceRecordShouldNotBeFound("roadmapLoginRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerIdRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerIdRequired equals to DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED
        defaultServiceRecordShouldBeFound("roadmapCustomerIdRequired.equals=" + DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED);

        // Get all the serviceRecordList where roadmapCustomerIdRequired equals to UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED
        defaultServiceRecordShouldNotBeFound("roadmapCustomerIdRequired.equals=" + UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerIdRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerIdRequired in DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED or UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED
        defaultServiceRecordShouldBeFound("roadmapCustomerIdRequired.in=" + DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED + "," + UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED);

        // Get all the serviceRecordList where roadmapCustomerIdRequired equals to UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED
        defaultServiceRecordShouldNotBeFound("roadmapCustomerIdRequired.in=" + UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerIdRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerIdRequired is not null
        defaultServiceRecordShouldBeFound("roadmapCustomerIdRequired.specified=true");

        // Get all the serviceRecordList where roadmapCustomerIdRequired is null
        defaultServiceRecordShouldNotBeFound("roadmapCustomerIdRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerDetails equals to DEFAULT_ROADMAP_CUSTOMER_DETAILS
        defaultServiceRecordShouldBeFound("roadmapCustomerDetails.equals=" + DEFAULT_ROADMAP_CUSTOMER_DETAILS);

        // Get all the serviceRecordList where roadmapCustomerDetails equals to UPDATED_ROADMAP_CUSTOMER_DETAILS
        defaultServiceRecordShouldNotBeFound("roadmapCustomerDetails.equals=" + UPDATED_ROADMAP_CUSTOMER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerDetails in DEFAULT_ROADMAP_CUSTOMER_DETAILS or UPDATED_ROADMAP_CUSTOMER_DETAILS
        defaultServiceRecordShouldBeFound("roadmapCustomerDetails.in=" + DEFAULT_ROADMAP_CUSTOMER_DETAILS + "," + UPDATED_ROADMAP_CUSTOMER_DETAILS);

        // Get all the serviceRecordList where roadmapCustomerDetails equals to UPDATED_ROADMAP_CUSTOMER_DETAILS
        defaultServiceRecordShouldNotBeFound("roadmapCustomerDetails.in=" + UPDATED_ROADMAP_CUSTOMER_DETAILS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCustomerDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapCustomerDetails is not null
        defaultServiceRecordShouldBeFound("roadmapCustomerDetails.specified=true");

        // Get all the serviceRecordList where roadmapCustomerDetails is null
        defaultServiceRecordShouldNotBeFound("roadmapCustomerDetails.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveIntentionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveIntention equals to DEFAULT_ROADMAP_IMPROVE_INTENTION
        defaultServiceRecordShouldBeFound("roadmapImproveIntention.equals=" + DEFAULT_ROADMAP_IMPROVE_INTENTION);

        // Get all the serviceRecordList where roadmapImproveIntention equals to UPDATED_ROADMAP_IMPROVE_INTENTION
        defaultServiceRecordShouldNotBeFound("roadmapImproveIntention.equals=" + UPDATED_ROADMAP_IMPROVE_INTENTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveIntentionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveIntention in DEFAULT_ROADMAP_IMPROVE_INTENTION or UPDATED_ROADMAP_IMPROVE_INTENTION
        defaultServiceRecordShouldBeFound("roadmapImproveIntention.in=" + DEFAULT_ROADMAP_IMPROVE_INTENTION + "," + UPDATED_ROADMAP_IMPROVE_INTENTION);

        // Get all the serviceRecordList where roadmapImproveIntention equals to UPDATED_ROADMAP_IMPROVE_INTENTION
        defaultServiceRecordShouldNotBeFound("roadmapImproveIntention.in=" + UPDATED_ROADMAP_IMPROVE_INTENTION);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveIntentionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveIntention is not null
        defaultServiceRecordShouldBeFound("roadmapImproveIntention.specified=true");

        // Get all the serviceRecordList where roadmapImproveIntention is null
        defaultServiceRecordShouldNotBeFound("roadmapImproveIntention.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveFutureIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveFuture equals to DEFAULT_ROADMAP_IMPROVE_FUTURE
        defaultServiceRecordShouldBeFound("roadmapImproveFuture.equals=" + DEFAULT_ROADMAP_IMPROVE_FUTURE);

        // Get all the serviceRecordList where roadmapImproveFuture equals to UPDATED_ROADMAP_IMPROVE_FUTURE
        defaultServiceRecordShouldNotBeFound("roadmapImproveFuture.equals=" + UPDATED_ROADMAP_IMPROVE_FUTURE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveFutureIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveFuture in DEFAULT_ROADMAP_IMPROVE_FUTURE or UPDATED_ROADMAP_IMPROVE_FUTURE
        defaultServiceRecordShouldBeFound("roadmapImproveFuture.in=" + DEFAULT_ROADMAP_IMPROVE_FUTURE + "," + UPDATED_ROADMAP_IMPROVE_FUTURE);

        // Get all the serviceRecordList where roadmapImproveFuture equals to UPDATED_ROADMAP_IMPROVE_FUTURE
        defaultServiceRecordShouldNotBeFound("roadmapImproveFuture.in=" + UPDATED_ROADMAP_IMPROVE_FUTURE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveFutureIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveFuture is not null
        defaultServiceRecordShouldBeFound("roadmapImproveFuture.specified=true");

        // Get all the serviceRecordList where roadmapImproveFuture is null
        defaultServiceRecordShouldNotBeFound("roadmapImproveFuture.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveType equals to DEFAULT_ROADMAP_IMPROVE_TYPE
        defaultServiceRecordShouldBeFound("roadmapImproveType.equals=" + DEFAULT_ROADMAP_IMPROVE_TYPE);

        // Get all the serviceRecordList where roadmapImproveType equals to UPDATED_ROADMAP_IMPROVE_TYPE
        defaultServiceRecordShouldNotBeFound("roadmapImproveType.equals=" + UPDATED_ROADMAP_IMPROVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveType in DEFAULT_ROADMAP_IMPROVE_TYPE or UPDATED_ROADMAP_IMPROVE_TYPE
        defaultServiceRecordShouldBeFound("roadmapImproveType.in=" + DEFAULT_ROADMAP_IMPROVE_TYPE + "," + UPDATED_ROADMAP_IMPROVE_TYPE);

        // Get all the serviceRecordList where roadmapImproveType equals to UPDATED_ROADMAP_IMPROVE_TYPE
        defaultServiceRecordShouldNotBeFound("roadmapImproveType.in=" + UPDATED_ROADMAP_IMPROVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveType is not null
        defaultServiceRecordShouldBeFound("roadmapImproveType.specified=true");

        // Get all the serviceRecordList where roadmapImproveType is null
        defaultServiceRecordShouldNotBeFound("roadmapImproveType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveWhen equals to DEFAULT_ROADMAP_IMPROVE_WHEN
        defaultServiceRecordShouldBeFound("roadmapImproveWhen.equals=" + DEFAULT_ROADMAP_IMPROVE_WHEN);

        // Get all the serviceRecordList where roadmapImproveWhen equals to UPDATED_ROADMAP_IMPROVE_WHEN
        defaultServiceRecordShouldNotBeFound("roadmapImproveWhen.equals=" + UPDATED_ROADMAP_IMPROVE_WHEN);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveWhenIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveWhen in DEFAULT_ROADMAP_IMPROVE_WHEN or UPDATED_ROADMAP_IMPROVE_WHEN
        defaultServiceRecordShouldBeFound("roadmapImproveWhen.in=" + DEFAULT_ROADMAP_IMPROVE_WHEN + "," + UPDATED_ROADMAP_IMPROVE_WHEN);

        // Get all the serviceRecordList where roadmapImproveWhen equals to UPDATED_ROADMAP_IMPROVE_WHEN
        defaultServiceRecordShouldNotBeFound("roadmapImproveWhen.in=" + UPDATED_ROADMAP_IMPROVE_WHEN);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveWhen is not null
        defaultServiceRecordShouldBeFound("roadmapImproveWhen.specified=true");

        // Get all the serviceRecordList where roadmapImproveWhen is null
        defaultServiceRecordShouldNotBeFound("roadmapImproveWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveHowIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveHow equals to DEFAULT_ROADMAP_IMPROVE_HOW
        defaultServiceRecordShouldBeFound("roadmapImproveHow.equals=" + DEFAULT_ROADMAP_IMPROVE_HOW);

        // Get all the serviceRecordList where roadmapImproveHow equals to UPDATED_ROADMAP_IMPROVE_HOW
        defaultServiceRecordShouldNotBeFound("roadmapImproveHow.equals=" + UPDATED_ROADMAP_IMPROVE_HOW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveHowIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveHow in DEFAULT_ROADMAP_IMPROVE_HOW or UPDATED_ROADMAP_IMPROVE_HOW
        defaultServiceRecordShouldBeFound("roadmapImproveHow.in=" + DEFAULT_ROADMAP_IMPROVE_HOW + "," + UPDATED_ROADMAP_IMPROVE_HOW);

        // Get all the serviceRecordList where roadmapImproveHow equals to UPDATED_ROADMAP_IMPROVE_HOW
        defaultServiceRecordShouldNotBeFound("roadmapImproveHow.in=" + UPDATED_ROADMAP_IMPROVE_HOW);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapImproveHowIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapImproveHow is not null
        defaultServiceRecordShouldBeFound("roadmapImproveHow.specified=true");

        // Get all the serviceRecordList where roadmapImproveHow is null
        defaultServiceRecordShouldNotBeFound("roadmapImproveHow.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityCurrentIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityCurrent equals to DEFAULT_ROADMAP_MATURITY_CURRENT
        defaultServiceRecordShouldBeFound("roadmapMaturityCurrent.equals=" + DEFAULT_ROADMAP_MATURITY_CURRENT);

        // Get all the serviceRecordList where roadmapMaturityCurrent equals to UPDATED_ROADMAP_MATURITY_CURRENT
        defaultServiceRecordShouldNotBeFound("roadmapMaturityCurrent.equals=" + UPDATED_ROADMAP_MATURITY_CURRENT);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityCurrentIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityCurrent in DEFAULT_ROADMAP_MATURITY_CURRENT or UPDATED_ROADMAP_MATURITY_CURRENT
        defaultServiceRecordShouldBeFound("roadmapMaturityCurrent.in=" + DEFAULT_ROADMAP_MATURITY_CURRENT + "," + UPDATED_ROADMAP_MATURITY_CURRENT);

        // Get all the serviceRecordList where roadmapMaturityCurrent equals to UPDATED_ROADMAP_MATURITY_CURRENT
        defaultServiceRecordShouldNotBeFound("roadmapMaturityCurrent.in=" + UPDATED_ROADMAP_MATURITY_CURRENT);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityCurrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityCurrent is not null
        defaultServiceRecordShouldBeFound("roadmapMaturityCurrent.specified=true");

        // Get all the serviceRecordList where roadmapMaturityCurrent is null
        defaultServiceRecordShouldNotBeFound("roadmapMaturityCurrent.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityDesiredIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityDesired equals to DEFAULT_ROADMAP_MATURITY_DESIRED
        defaultServiceRecordShouldBeFound("roadmapMaturityDesired.equals=" + DEFAULT_ROADMAP_MATURITY_DESIRED);

        // Get all the serviceRecordList where roadmapMaturityDesired equals to UPDATED_ROADMAP_MATURITY_DESIRED
        defaultServiceRecordShouldNotBeFound("roadmapMaturityDesired.equals=" + UPDATED_ROADMAP_MATURITY_DESIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityDesiredIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityDesired in DEFAULT_ROADMAP_MATURITY_DESIRED or UPDATED_ROADMAP_MATURITY_DESIRED
        defaultServiceRecordShouldBeFound("roadmapMaturityDesired.in=" + DEFAULT_ROADMAP_MATURITY_DESIRED + "," + UPDATED_ROADMAP_MATURITY_DESIRED);

        // Get all the serviceRecordList where roadmapMaturityDesired equals to UPDATED_ROADMAP_MATURITY_DESIRED
        defaultServiceRecordShouldNotBeFound("roadmapMaturityDesired.in=" + UPDATED_ROADMAP_MATURITY_DESIRED);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapMaturityDesiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapMaturityDesired is not null
        defaultServiceRecordShouldBeFound("roadmapMaturityDesired.specified=true");

        // Get all the serviceRecordList where roadmapMaturityDesired is null
        defaultServiceRecordShouldNotBeFound("roadmapMaturityDesired.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapComments equals to DEFAULT_ROADMAP_COMMENTS
        defaultServiceRecordShouldBeFound("roadmapComments.equals=" + DEFAULT_ROADMAP_COMMENTS);

        // Get all the serviceRecordList where roadmapComments equals to UPDATED_ROADMAP_COMMENTS
        defaultServiceRecordShouldNotBeFound("roadmapComments.equals=" + UPDATED_ROADMAP_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapComments in DEFAULT_ROADMAP_COMMENTS or UPDATED_ROADMAP_COMMENTS
        defaultServiceRecordShouldBeFound("roadmapComments.in=" + DEFAULT_ROADMAP_COMMENTS + "," + UPDATED_ROADMAP_COMMENTS);

        // Get all the serviceRecordList where roadmapComments equals to UPDATED_ROADMAP_COMMENTS
        defaultServiceRecordShouldNotBeFound("roadmapComments.in=" + UPDATED_ROADMAP_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByRoadmapCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where roadmapComments is not null
        defaultServiceRecordShouldBeFound("roadmapComments.specified=true");

        // Get all the serviceRecordList where roadmapComments is null
        defaultServiceRecordShouldNotBeFound("roadmapComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByHowToIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where howTo equals to DEFAULT_HOW_TO
        defaultServiceRecordShouldBeFound("howTo.equals=" + DEFAULT_HOW_TO);

        // Get all the serviceRecordList where howTo equals to UPDATED_HOW_TO
        defaultServiceRecordShouldNotBeFound("howTo.equals=" + UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByHowToIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where howTo in DEFAULT_HOW_TO or UPDATED_HOW_TO
        defaultServiceRecordShouldBeFound("howTo.in=" + DEFAULT_HOW_TO + "," + UPDATED_HOW_TO);

        // Get all the serviceRecordList where howTo equals to UPDATED_HOW_TO
        defaultServiceRecordShouldNotBeFound("howTo.in=" + UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByHowToIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        // Get all the serviceRecordList where howTo is not null
        defaultServiceRecordShouldBeFound("howTo.specified=true");

        // Get all the serviceRecordList where howTo is null
        defaultServiceRecordShouldNotBeFound("howTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRecordsByDeliveryOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDeliveryOrganisation deliveryOrg = ServiceDeliveryOrganisationResourceIT.createEntity(em);
        em.persist(deliveryOrg);
        em.flush();
        serviceRecord.setDeliveryOrg(deliveryOrg);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long deliveryOrgId = deliveryOrg.getId();

        // Get all the serviceRecordList where deliveryOrg equals to deliveryOrgId
        defaultServiceRecordShouldBeFound("deliveryOrgId.equals=" + deliveryOrgId);

        // Get all the serviceRecordList where deliveryOrg equals to deliveryOrgId + 1
        defaultServiceRecordShouldNotBeFound("deliveryOrgId.equals=" + (deliveryOrgId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByParentServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord parentService = ServiceRecordResourceIT.createEntity(em);
        em.persist(parentService);
        em.flush();
        serviceRecord.setParentService(parentService);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long parentServiceId = parentService.getId();

        // Get all the serviceRecordList where parentService equals to parentServiceId
        defaultServiceRecordShouldBeFound("parentServiceId.equals=" + parentServiceId);

        // Get all the serviceRecordList where parentService equals to parentServiceId + 1
        defaultServiceRecordShouldNotBeFound("parentServiceId.equals=" + (parentServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceFranchise serviceFranchise = ServiceFranchiseResourceIT.createEntity(em);
        em.persist(serviceFranchise);
        em.flush();
        serviceRecord.setServiceFranchise(serviceFranchise);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceFranchiseId = serviceFranchise.getId();

        // Get all the serviceRecordList where serviceFranchise equals to serviceFranchiseId
        defaultServiceRecordShouldBeFound("serviceFranchiseId.equals=" + serviceFranchiseId);

        // Get all the serviceRecordList where serviceFranchise equals to serviceFranchiseId + 1
        defaultServiceRecordShouldNotBeFound("serviceFranchiseId.equals=" + (serviceFranchiseId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByApplicationServiceOverrideIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationServiceOverride applicationServiceOverride = ApplicationServiceOverrideResourceIT.createEntity(em);
        em.persist(applicationServiceOverride);
        em.flush();
        serviceRecord.addApplicationServiceOverride(applicationServiceOverride);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long applicationServiceOverrideId = applicationServiceOverride.getId();

        // Get all the serviceRecordList where applicationServiceOverride equals to applicationServiceOverrideId
        defaultServiceRecordShouldBeFound("applicationServiceOverrideId.equals=" + applicationServiceOverrideId);

        // Get all the serviceRecordList where applicationServiceOverride equals to applicationServiceOverrideId + 1
        defaultServiceRecordShouldNotBeFound("applicationServiceOverrideId.equals=" + (applicationServiceOverrideId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByIntegrationMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        IntegrationMapping integrationMapping = IntegrationMappingResourceIT.createEntity(em);
        em.persist(integrationMapping);
        em.flush();
        serviceRecord.addIntegrationMapping(integrationMapping);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long integrationMappingId = integrationMapping.getId();

        // Get all the serviceRecordList where integrationMapping equals to integrationMappingId
        defaultServiceRecordShouldBeFound("integrationMappingId.equals=" + integrationMappingId);

        // Get all the serviceRecordList where integrationMapping equals to integrationMappingId + 1
        defaultServiceRecordShouldNotBeFound("integrationMappingId.equals=" + (integrationMappingId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceRecord.addServiceRecord(serviceRecord);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceRecordList where serviceRecord equals to serviceRecordId
        defaultServiceRecordShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceRecordList where serviceRecord equals to serviceRecordId + 1
        defaultServiceRecordShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDelivery serviceDelivery = ServiceDeliveryResourceIT.createEntity(em);
        em.persist(serviceDelivery);
        em.flush();
        serviceRecord.addServiceDelivery(serviceDelivery);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceDeliveryId = serviceDelivery.getId();

        // Get all the serviceRecordList where serviceDelivery equals to serviceDeliveryId
        defaultServiceRecordShouldBeFound("serviceDeliveryId.equals=" + serviceDeliveryId);

        // Get all the serviceRecordList where serviceDelivery equals to serviceDeliveryId + 1
        defaultServiceRecordShouldNotBeFound("serviceDeliveryId.equals=" + (serviceDeliveryId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceDeliveryFormIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDeliveryForm serviceDeliveryForm = ServiceDeliveryFormResourceIT.createEntity(em);
        em.persist(serviceDeliveryForm);
        em.flush();
        serviceRecord.addServiceDeliveryForm(serviceDeliveryForm);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceDeliveryFormId = serviceDeliveryForm.getId();

        // Get all the serviceRecordList where serviceDeliveryForm equals to serviceDeliveryFormId
        defaultServiceRecordShouldBeFound("serviceDeliveryFormId.equals=" + serviceDeliveryFormId);

        // Get all the serviceRecordList where serviceDeliveryForm equals to serviceDeliveryFormId + 1
        defaultServiceRecordShouldNotBeFound("serviceDeliveryFormId.equals=" + (serviceDeliveryFormId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceDescription serviceDescription = ServiceDescriptionResourceIT.createEntity(em);
        em.persist(serviceDescription);
        em.flush();
        serviceRecord.addServiceDescription(serviceDescription);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceDescriptionId = serviceDescription.getId();

        // Get all the serviceRecordList where serviceDescription equals to serviceDescriptionId
        defaultServiceRecordShouldBeFound("serviceDescriptionId.equals=" + serviceDescriptionId);

        // Get all the serviceRecordList where serviceDescription equals to serviceDescriptionId + 1
        defaultServiceRecordShouldNotBeFound("serviceDescriptionId.equals=" + (serviceDescriptionId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceEventIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceEvent serviceEvent = ServiceEventResourceIT.createEntity(em);
        em.persist(serviceEvent);
        em.flush();
        serviceRecord.addServiceEvent(serviceEvent);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceEventId = serviceEvent.getId();

        // Get all the serviceRecordList where serviceEvent equals to serviceEventId
        defaultServiceRecordShouldBeFound("serviceEventId.equals=" + serviceEventId);

        // Get all the serviceRecordList where serviceEvent equals to serviceEventId + 1
        defaultServiceRecordShouldNotBeFound("serviceEventId.equals=" + (serviceEventId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceEvidenceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceEvidence serviceEvidence = ServiceEvidenceResourceIT.createEntity(em);
        em.persist(serviceEvidence);
        em.flush();
        serviceRecord.addServiceEvidence(serviceEvidence);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceEvidenceId = serviceEvidence.getId();

        // Get all the serviceRecordList where serviceEvidence equals to serviceEvidenceId
        defaultServiceRecordShouldBeFound("serviceEvidenceId.equals=" + serviceEvidenceId);

        // Get all the serviceRecordList where serviceEvidence equals to serviceEvidenceId + 1
        defaultServiceRecordShouldNotBeFound("serviceEvidenceId.equals=" + (serviceEvidenceId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceGroup serviceGroup = ServiceGroupResourceIT.createEntity(em);
        em.persist(serviceGroup);
        em.flush();
        serviceRecord.addServiceGroup(serviceGroup);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceGroupId = serviceGroup.getId();

        // Get all the serviceRecordList where serviceGroup equals to serviceGroupId
        defaultServiceRecordShouldBeFound("serviceGroupId.equals=" + serviceGroupId);

        // Get all the serviceRecordList where serviceGroup equals to serviceGroupId + 1
        defaultServiceRecordShouldNotBeFound("serviceGroupId.equals=" + (serviceGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceNameIsEqualToSomething2() throws Exception {
        // Initialize the database
        ServiceName serviceName = ServiceNameResourceIT.createEntity(em);
        em.persist(serviceName);
        em.flush();
        serviceRecord.addServiceName(serviceName);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceNameId = serviceName.getId();

        // Get all the serviceRecordList where serviceName equals to serviceNameId
        defaultServiceRecordShouldBeFound("serviceNameId.equals=" + serviceNameId);

        // Get all the serviceRecordList where serviceName equals to serviceNameId + 1
        defaultServiceRecordShouldNotBeFound("serviceNameId.equals=" + (serviceNameId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByFromServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRelationship fromService = ServiceRelationshipResourceIT.createEntity(em);
        em.persist(fromService);
        em.flush();
        serviceRecord.addFromService(fromService);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long fromServiceId = fromService.getId();

        // Get all the serviceRecordList where fromService equals to fromServiceId
        defaultServiceRecordShouldBeFound("fromServiceId.equals=" + fromServiceId);

        // Get all the serviceRecordList where fromService equals to fromServiceId + 1
        defaultServiceRecordShouldNotBeFound("fromServiceId.equals=" + (fromServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByToServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRelationship toService = ServiceRelationshipResourceIT.createEntity(em);
        em.persist(toService);
        em.flush();
        serviceRecord.addToService(toService);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long toServiceId = toService.getId();

        // Get all the serviceRecordList where toService equals to toServiceId
        defaultServiceRecordShouldBeFound("toServiceId.equals=" + toServiceId);

        // Get all the serviceRecordList where toService equals to toServiceId + 1
        defaultServiceRecordShouldNotBeFound("toServiceId.equals=" + (toServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceSupportRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceSupportRole serviceSupportRole = ServiceSupportRoleResourceIT.createEntity(em);
        em.persist(serviceSupportRole);
        em.flush();
        serviceRecord.addServiceSupportRole(serviceSupportRole);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceSupportRoleId = serviceSupportRole.getId();

        // Get all the serviceRecordList where serviceSupportRole equals to serviceSupportRoleId
        defaultServiceRecordShouldBeFound("serviceSupportRoleId.equals=" + serviceSupportRoleId);

        // Get all the serviceRecordList where serviceSupportRole equals to serviceSupportRoleId + 1
        defaultServiceRecordShouldNotBeFound("serviceSupportRoleId.equals=" + (serviceSupportRoleId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRecordsByServiceTagItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceTagItems serviceTagItems = ServiceTagItemsResourceIT.createEntity(em);
        em.persist(serviceTagItems);
        em.flush();
        serviceRecord.addServiceTagItems(serviceTagItems);
        serviceRecordRepository.saveAndFlush(serviceRecord);
        Long serviceTagItemsId = serviceTagItems.getId();

        // Get all the serviceRecordList where serviceTagItems equals to serviceTagItemsId
        defaultServiceRecordShouldBeFound("serviceTagItemsId.equals=" + serviceTagItemsId);

        // Get all the serviceRecordList where serviceTagItems equals to serviceTagItemsId + 1
        defaultServiceRecordShouldNotBeFound("serviceTagItemsId.equals=" + (serviceTagItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceRecordShouldBeFound(String filter) throws Exception {
        restServiceRecordMockMvc.perform(get("/api/service-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].eligibility").value(hasItem(DEFAULT_ELIGIBILITY)))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES)))
            .andExpect(jsonPath("$.[*].groupHeader").value(hasItem(DEFAULT_GROUP_HEADER)))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID)))
            .andExpect(jsonPath("$.[*].interactionId").value(hasItem(DEFAULT_INTERACTION_ID)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS)))
            .andExpect(jsonPath("$.[*].preRequisites").value(hasItem(DEFAULT_PRE_REQUISITES)))
            .andExpect(jsonPath("$.[*].qgsServiceId").value(hasItem(DEFAULT_QGS_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].referenceUrl").value(hasItem(DEFAULT_REFERENCE_URL)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].validatedDate").value(hasItem(DEFAULT_VALIDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preRequisitesNew").value(hasItem(DEFAULT_PRE_REQUISITES_NEW)))
            .andExpect(jsonPath("$.[*].referenceUrlNew").value(hasItem(DEFAULT_REFERENCE_URL_NEW)))
            .andExpect(jsonPath("$.[*].eligibilityNew").value(hasItem(DEFAULT_ELIGIBILITY_NEW)))
            .andExpect(jsonPath("$.[*].serviceContext").value(hasItem(DEFAULT_SERVICE_CONTEXT)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].roadmapLoginRequired").value(hasItem(DEFAULT_ROADMAP_LOGIN_REQUIRED)))
            .andExpect(jsonPath("$.[*].roadmapCustomerIdRequired").value(hasItem(DEFAULT_ROADMAP_CUSTOMER_ID_REQUIRED)))
            .andExpect(jsonPath("$.[*].roadmapCustomerDetails").value(hasItem(DEFAULT_ROADMAP_CUSTOMER_DETAILS)))
            .andExpect(jsonPath("$.[*].roadmapImproveIntention").value(hasItem(DEFAULT_ROADMAP_IMPROVE_INTENTION)))
            .andExpect(jsonPath("$.[*].roadmapImproveFuture").value(hasItem(DEFAULT_ROADMAP_IMPROVE_FUTURE)))
            .andExpect(jsonPath("$.[*].roadmapImproveType").value(hasItem(DEFAULT_ROADMAP_IMPROVE_TYPE)))
            .andExpect(jsonPath("$.[*].roadmapImproveWhen").value(hasItem(DEFAULT_ROADMAP_IMPROVE_WHEN)))
            .andExpect(jsonPath("$.[*].roadmapImproveHow").value(hasItem(DEFAULT_ROADMAP_IMPROVE_HOW)))
            .andExpect(jsonPath("$.[*].roadmapMaturityCurrent").value(hasItem(DEFAULT_ROADMAP_MATURITY_CURRENT)))
            .andExpect(jsonPath("$.[*].roadmapMaturityDesired").value(hasItem(DEFAULT_ROADMAP_MATURITY_DESIRED)))
            .andExpect(jsonPath("$.[*].roadmapComments").value(hasItem(DEFAULT_ROADMAP_COMMENTS)))
            .andExpect(jsonPath("$.[*].howTo").value(hasItem(DEFAULT_HOW_TO)));

        // Check, that the count call also returns 1
        restServiceRecordMockMvc.perform(get("/api/service-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceRecordShouldNotBeFound(String filter) throws Exception {
        restServiceRecordMockMvc.perform(get("/api/service-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceRecordMockMvc.perform(get("/api/service-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceRecord() throws Exception {
        // Get the serviceRecord
        restServiceRecordMockMvc.perform(get("/api/service-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRecord() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        int databaseSizeBeforeUpdate = serviceRecordRepository.findAll().size();

        // Update the serviceRecord
        ServiceRecord updatedServiceRecord = serviceRecordRepository.findById(serviceRecord.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRecord are not directly saved in db
        em.detach(updatedServiceRecord);
        updatedServiceRecord
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .active(UPDATED_ACTIVE)
            .eligibility(UPDATED_ELIGIBILITY)
            .fees(UPDATED_FEES)
            .groupHeader(UPDATED_GROUP_HEADER)
            .groupId(UPDATED_GROUP_ID)
            .interactionId(UPDATED_INTERACTION_ID)
            .keywords(UPDATED_KEYWORDS)
            .preRequisites(UPDATED_PRE_REQUISITES)
            .qgsServiceId(UPDATED_QGS_SERVICE_ID)
            .referenceUrl(UPDATED_REFERENCE_URL)
            .serviceName(UPDATED_SERVICE_NAME)
            .validatedDate(UPDATED_VALIDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .preRequisitesNew(UPDATED_PRE_REQUISITES_NEW)
            .referenceUrlNew(UPDATED_REFERENCE_URL_NEW)
            .eligibilityNew(UPDATED_ELIGIBILITY_NEW)
            .serviceContext(UPDATED_SERVICE_CONTEXT)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .roadmapLoginRequired(UPDATED_ROADMAP_LOGIN_REQUIRED)
            .roadmapCustomerIdRequired(UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED)
            .roadmapCustomerDetails(UPDATED_ROADMAP_CUSTOMER_DETAILS)
            .roadmapImproveIntention(UPDATED_ROADMAP_IMPROVE_INTENTION)
            .roadmapImproveFuture(UPDATED_ROADMAP_IMPROVE_FUTURE)
            .roadmapImproveType(UPDATED_ROADMAP_IMPROVE_TYPE)
            .roadmapImproveWhen(UPDATED_ROADMAP_IMPROVE_WHEN)
            .roadmapImproveHow(UPDATED_ROADMAP_IMPROVE_HOW)
            .roadmapMaturityCurrent(UPDATED_ROADMAP_MATURITY_CURRENT)
            .roadmapMaturityDesired(UPDATED_ROADMAP_MATURITY_DESIRED)
            .roadmapComments(UPDATED_ROADMAP_COMMENTS)
            .howTo(UPDATED_HOW_TO);
        ServiceRecordDTO serviceRecordDTO = serviceRecordMapper.toDto(updatedServiceRecord);

        restServiceRecordMockMvc.perform(put("/api/service-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRecordDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRecord in the database
        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeUpdate);
        ServiceRecord testServiceRecord = serviceRecordList.get(serviceRecordList.size() - 1);
        assertThat(testServiceRecord.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceRecord.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceRecord.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceRecord.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceRecord.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceRecord.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testServiceRecord.getEligibility()).isEqualTo(UPDATED_ELIGIBILITY);
        assertThat(testServiceRecord.getFees()).isEqualTo(UPDATED_FEES);
        assertThat(testServiceRecord.getGroupHeader()).isEqualTo(UPDATED_GROUP_HEADER);
        assertThat(testServiceRecord.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testServiceRecord.getInteractionId()).isEqualTo(UPDATED_INTERACTION_ID);
        assertThat(testServiceRecord.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testServiceRecord.getPreRequisites()).isEqualTo(UPDATED_PRE_REQUISITES);
        assertThat(testServiceRecord.getQgsServiceId()).isEqualTo(UPDATED_QGS_SERVICE_ID);
        assertThat(testServiceRecord.getReferenceUrl()).isEqualTo(UPDATED_REFERENCE_URL);
        assertThat(testServiceRecord.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testServiceRecord.getValidatedDate()).isEqualTo(UPDATED_VALIDATED_DATE);
        assertThat(testServiceRecord.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceRecord.getPreRequisitesNew()).isEqualTo(UPDATED_PRE_REQUISITES_NEW);
        assertThat(testServiceRecord.getReferenceUrlNew()).isEqualTo(UPDATED_REFERENCE_URL_NEW);
        assertThat(testServiceRecord.getEligibilityNew()).isEqualTo(UPDATED_ELIGIBILITY_NEW);
        assertThat(testServiceRecord.getServiceContext()).isEqualTo(UPDATED_SERVICE_CONTEXT);
        assertThat(testServiceRecord.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testServiceRecord.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceRecord.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testServiceRecord.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testServiceRecord.getRoadmapLoginRequired()).isEqualTo(UPDATED_ROADMAP_LOGIN_REQUIRED);
        assertThat(testServiceRecord.getRoadmapCustomerIdRequired()).isEqualTo(UPDATED_ROADMAP_CUSTOMER_ID_REQUIRED);
        assertThat(testServiceRecord.getRoadmapCustomerDetails()).isEqualTo(UPDATED_ROADMAP_CUSTOMER_DETAILS);
        assertThat(testServiceRecord.getRoadmapImproveIntention()).isEqualTo(UPDATED_ROADMAP_IMPROVE_INTENTION);
        assertThat(testServiceRecord.getRoadmapImproveFuture()).isEqualTo(UPDATED_ROADMAP_IMPROVE_FUTURE);
        assertThat(testServiceRecord.getRoadmapImproveType()).isEqualTo(UPDATED_ROADMAP_IMPROVE_TYPE);
        assertThat(testServiceRecord.getRoadmapImproveWhen()).isEqualTo(UPDATED_ROADMAP_IMPROVE_WHEN);
        assertThat(testServiceRecord.getRoadmapImproveHow()).isEqualTo(UPDATED_ROADMAP_IMPROVE_HOW);
        assertThat(testServiceRecord.getRoadmapMaturityCurrent()).isEqualTo(UPDATED_ROADMAP_MATURITY_CURRENT);
        assertThat(testServiceRecord.getRoadmapMaturityDesired()).isEqualTo(UPDATED_ROADMAP_MATURITY_DESIRED);
        assertThat(testServiceRecord.getRoadmapComments()).isEqualTo(UPDATED_ROADMAP_COMMENTS);
        assertThat(testServiceRecord.getHowTo()).isEqualTo(UPDATED_HOW_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRecord() throws Exception {
        int databaseSizeBeforeUpdate = serviceRecordRepository.findAll().size();

        // Create the ServiceRecord
        ServiceRecordDTO serviceRecordDTO = serviceRecordMapper.toDto(serviceRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRecordMockMvc.perform(put("/api/service-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRecord in the database
        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceRecord() throws Exception {
        // Initialize the database
        serviceRecordRepository.saveAndFlush(serviceRecord);

        int databaseSizeBeforeDelete = serviceRecordRepository.findAll().size();

        // Delete the serviceRecord
        restServiceRecordMockMvc.perform(delete("/api/service-records/{id}", serviceRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRecord> serviceRecordList = serviceRecordRepository.findAll();
        assertThat(serviceRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRecord.class);
        ServiceRecord serviceRecord1 = new ServiceRecord();
        serviceRecord1.setId(1L);
        ServiceRecord serviceRecord2 = new ServiceRecord();
        serviceRecord2.setId(serviceRecord1.getId());
        assertThat(serviceRecord1).isEqualTo(serviceRecord2);
        serviceRecord2.setId(2L);
        assertThat(serviceRecord1).isNotEqualTo(serviceRecord2);
        serviceRecord1.setId(null);
        assertThat(serviceRecord1).isNotEqualTo(serviceRecord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRecordDTO.class);
        ServiceRecordDTO serviceRecordDTO1 = new ServiceRecordDTO();
        serviceRecordDTO1.setId(1L);
        ServiceRecordDTO serviceRecordDTO2 = new ServiceRecordDTO();
        assertThat(serviceRecordDTO1).isNotEqualTo(serviceRecordDTO2);
        serviceRecordDTO2.setId(serviceRecordDTO1.getId());
        assertThat(serviceRecordDTO1).isEqualTo(serviceRecordDTO2);
        serviceRecordDTO2.setId(2L);
        assertThat(serviceRecordDTO1).isNotEqualTo(serviceRecordDTO2);
        serviceRecordDTO1.setId(null);
        assertThat(serviceRecordDTO1).isNotEqualTo(serviceRecordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRecordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRecordMapper.fromId(null)).isNull();
    }
}
