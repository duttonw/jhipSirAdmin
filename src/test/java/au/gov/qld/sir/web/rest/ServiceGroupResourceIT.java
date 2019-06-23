package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceGroup;
import au.gov.qld.sir.domain.Category;
import au.gov.qld.sir.domain.CategoryType;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.repository.ServiceGroupRepository;
import au.gov.qld.sir.service.ServiceGroupService;
import au.gov.qld.sir.service.dto.ServiceGroupDTO;
import au.gov.qld.sir.service.mapper.ServiceGroupMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceGroupCriteria;
import au.gov.qld.sir.service.ServiceGroupQueryService;

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
 * Integration tests for the {@Link ServiceGroupResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceGroupResourceIT {

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

    private static final String DEFAULT_MIGRATED = "A";
    private static final String UPDATED_MIGRATED = "B";

    private static final String DEFAULT_MIGRATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MIGRATED_BY = "BBBBBBBBBB";

    @Autowired
    private ServiceGroupRepository serviceGroupRepository;

    @Autowired
    private ServiceGroupMapper serviceGroupMapper;

    @Autowired
    private ServiceGroupService serviceGroupService;

    @Autowired
    private ServiceGroupQueryService serviceGroupQueryService;

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

    private MockMvc restServiceGroupMockMvc;

    private ServiceGroup serviceGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceGroupResource serviceGroupResource = new ServiceGroupResource(serviceGroupService, serviceGroupQueryService);
        this.restServiceGroupMockMvc = MockMvcBuilders.standaloneSetup(serviceGroupResource)
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
    public static ServiceGroup createEntity(EntityManager em) {
        ServiceGroup serviceGroup = new ServiceGroup()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .migrated(DEFAULT_MIGRATED)
            .migratedBy(DEFAULT_MIGRATED_BY);
        return serviceGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceGroup createUpdatedEntity(EntityManager em) {
        ServiceGroup serviceGroup = new ServiceGroup()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        return serviceGroup;
    }

    @BeforeEach
    public void initTest() {
        serviceGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceGroup() throws Exception {
        int databaseSizeBeforeCreate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);
        restServiceGroupMockMvc.perform(post("/api/service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceGroup testServiceGroup = serviceGroupList.get(serviceGroupList.size() - 1);
        assertThat(testServiceGroup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceGroup.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceGroup.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceGroup.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testServiceGroup.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceGroup.getMigrated()).isEqualTo(DEFAULT_MIGRATED);
        assertThat(testServiceGroup.getMigratedBy()).isEqualTo(DEFAULT_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void createServiceGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup with an existing ID
        serviceGroup.setId(1L);
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceGroupMockMvc.perform(post("/api/service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceGroups() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList
        restServiceGroupMockMvc.perform(get("/api/service-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED.toString())))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get the serviceGroup
        restServiceGroupMockMvc.perform(get("/api/service-groups/{id}", serviceGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceGroup.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.migrated").value(DEFAULT_MIGRATED.toString()))
            .andExpect(jsonPath("$.migratedBy").value(DEFAULT_MIGRATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceGroupShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceGroupShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceGroupShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceGroupList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceGroupShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdBy is not null
        defaultServiceGroupShouldBeFound("createdBy.specified=true");

        // Get all the serviceGroupList where createdBy is null
        defaultServiceGroupShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceGroupShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceGroupList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceGroupShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceGroupShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceGroupList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceGroupShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where createdDateTime is not null
        defaultServiceGroupShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceGroupList where createdDateTime is null
        defaultServiceGroupShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceGroupShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceGroupList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceGroupShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceGroupShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceGroupList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceGroupShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedBy is not null
        defaultServiceGroupShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceGroupList where modifiedBy is null
        defaultServiceGroupShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceGroupShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceGroupList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceGroupShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceGroupShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceGroupList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceGroupShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where modifiedDateTime is not null
        defaultServiceGroupShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceGroupList where modifiedDateTime is null
        defaultServiceGroupShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where version equals to DEFAULT_VERSION
        defaultServiceGroupShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the serviceGroupList where version equals to UPDATED_VERSION
        defaultServiceGroupShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultServiceGroupShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the serviceGroupList where version equals to UPDATED_VERSION
        defaultServiceGroupShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where version is not null
        defaultServiceGroupShouldBeFound("version.specified=true");

        // Get all the serviceGroupList where version is null
        defaultServiceGroupShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where version greater than or equals to DEFAULT_VERSION
        defaultServiceGroupShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the serviceGroupList where version greater than or equals to UPDATED_VERSION
        defaultServiceGroupShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where version less than or equals to DEFAULT_VERSION
        defaultServiceGroupShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the serviceGroupList where version less than or equals to UPDATED_VERSION
        defaultServiceGroupShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migrated equals to DEFAULT_MIGRATED
        defaultServiceGroupShouldBeFound("migrated.equals=" + DEFAULT_MIGRATED);

        // Get all the serviceGroupList where migrated equals to UPDATED_MIGRATED
        defaultServiceGroupShouldNotBeFound("migrated.equals=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migrated in DEFAULT_MIGRATED or UPDATED_MIGRATED
        defaultServiceGroupShouldBeFound("migrated.in=" + DEFAULT_MIGRATED + "," + UPDATED_MIGRATED);

        // Get all the serviceGroupList where migrated equals to UPDATED_MIGRATED
        defaultServiceGroupShouldNotBeFound("migrated.in=" + UPDATED_MIGRATED);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migrated is not null
        defaultServiceGroupShouldBeFound("migrated.specified=true");

        // Get all the serviceGroupList where migrated is null
        defaultServiceGroupShouldNotBeFound("migrated.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migratedBy equals to DEFAULT_MIGRATED_BY
        defaultServiceGroupShouldBeFound("migratedBy.equals=" + DEFAULT_MIGRATED_BY);

        // Get all the serviceGroupList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceGroupShouldNotBeFound("migratedBy.equals=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migratedBy in DEFAULT_MIGRATED_BY or UPDATED_MIGRATED_BY
        defaultServiceGroupShouldBeFound("migratedBy.in=" + DEFAULT_MIGRATED_BY + "," + UPDATED_MIGRATED_BY);

        // Get all the serviceGroupList where migratedBy equals to UPDATED_MIGRATED_BY
        defaultServiceGroupShouldNotBeFound("migratedBy.in=" + UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByMigratedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList where migratedBy is not null
        defaultServiceGroupShouldBeFound("migratedBy.specified=true");

        // Get all the serviceGroupList where migratedBy is null
        defaultServiceGroupShouldNotBeFound("migratedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceGroupsByServiceGroupCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category serviceGroupCategory = CategoryResourceIT.createEntity(em);
        em.persist(serviceGroupCategory);
        em.flush();
        serviceGroup.setServiceGroupCategory(serviceGroupCategory);
        serviceGroupRepository.saveAndFlush(serviceGroup);
        Long serviceGroupCategoryId = serviceGroupCategory.getId();

        // Get all the serviceGroupList where serviceGroupCategory equals to serviceGroupCategoryId
        defaultServiceGroupShouldBeFound("serviceGroupCategoryId.equals=" + serviceGroupCategoryId);

        // Get all the serviceGroupList where serviceGroupCategory equals to serviceGroupCategoryId + 1
        defaultServiceGroupShouldNotBeFound("serviceGroupCategoryId.equals=" + (serviceGroupCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceGroupsByServiceGroupCategoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        CategoryType serviceGroupCategoryType = CategoryTypeResourceIT.createEntity(em);
        em.persist(serviceGroupCategoryType);
        em.flush();
        serviceGroup.setServiceGroupCategoryType(serviceGroupCategoryType);
        serviceGroupRepository.saveAndFlush(serviceGroup);
        Long serviceGroupCategoryTypeId = serviceGroupCategoryType.getId();

        // Get all the serviceGroupList where serviceGroupCategoryType equals to serviceGroupCategoryTypeId
        defaultServiceGroupShouldBeFound("serviceGroupCategoryTypeId.equals=" + serviceGroupCategoryTypeId);

        // Get all the serviceGroupList where serviceGroupCategoryType equals to serviceGroupCategoryTypeId + 1
        defaultServiceGroupShouldNotBeFound("serviceGroupCategoryTypeId.equals=" + (serviceGroupCategoryTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceGroupsByServiceRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceRecord serviceRecord = ServiceRecordResourceIT.createEntity(em);
        em.persist(serviceRecord);
        em.flush();
        serviceGroup.setServiceRecord(serviceRecord);
        serviceGroupRepository.saveAndFlush(serviceGroup);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceGroupList where serviceRecord equals to serviceRecordId
        defaultServiceGroupShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceGroupList where serviceRecord equals to serviceRecordId + 1
        defaultServiceGroupShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceGroupShouldBeFound(String filter) throws Exception {
        restServiceGroupMockMvc.perform(get("/api/service-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].migrated").value(hasItem(DEFAULT_MIGRATED)))
            .andExpect(jsonPath("$.[*].migratedBy").value(hasItem(DEFAULT_MIGRATED_BY)));

        // Check, that the count call also returns 1
        restServiceGroupMockMvc.perform(get("/api/service-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceGroupShouldNotBeFound(String filter) throws Exception {
        restServiceGroupMockMvc.perform(get("/api/service-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceGroupMockMvc.perform(get("/api/service-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceGroup() throws Exception {
        // Get the serviceGroup
        restServiceGroupMockMvc.perform(get("/api/service-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        int databaseSizeBeforeUpdate = serviceGroupRepository.findAll().size();

        // Update the serviceGroup
        ServiceGroup updatedServiceGroup = serviceGroupRepository.findById(serviceGroup.getId()).get();
        // Disconnect from session so that the updates on updatedServiceGroup are not directly saved in db
        em.detach(updatedServiceGroup);
        updatedServiceGroup
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .migrated(UPDATED_MIGRATED)
            .migratedBy(UPDATED_MIGRATED_BY);
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(updatedServiceGroup);

        restServiceGroupMockMvc.perform(put("/api/service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeUpdate);
        ServiceGroup testServiceGroup = serviceGroupList.get(serviceGroupList.size() - 1);
        assertThat(testServiceGroup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceGroup.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceGroup.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceGroup.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testServiceGroup.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceGroup.getMigrated()).isEqualTo(UPDATED_MIGRATED);
        assertThat(testServiceGroup.getMigratedBy()).isEqualTo(UPDATED_MIGRATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceGroup() throws Exception {
        int databaseSizeBeforeUpdate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceGroupMockMvc.perform(put("/api/service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        int databaseSizeBeforeDelete = serviceGroupRepository.findAll().size();

        // Delete the serviceGroup
        restServiceGroupMockMvc.perform(delete("/api/service-groups/{id}", serviceGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceGroup.class);
        ServiceGroup serviceGroup1 = new ServiceGroup();
        serviceGroup1.setId(1L);
        ServiceGroup serviceGroup2 = new ServiceGroup();
        serviceGroup2.setId(serviceGroup1.getId());
        assertThat(serviceGroup1).isEqualTo(serviceGroup2);
        serviceGroup2.setId(2L);
        assertThat(serviceGroup1).isNotEqualTo(serviceGroup2);
        serviceGroup1.setId(null);
        assertThat(serviceGroup1).isNotEqualTo(serviceGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceGroupDTO.class);
        ServiceGroupDTO serviceGroupDTO1 = new ServiceGroupDTO();
        serviceGroupDTO1.setId(1L);
        ServiceGroupDTO serviceGroupDTO2 = new ServiceGroupDTO();
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
        serviceGroupDTO2.setId(serviceGroupDTO1.getId());
        assertThat(serviceGroupDTO1).isEqualTo(serviceGroupDTO2);
        serviceGroupDTO2.setId(2L);
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
        serviceGroupDTO1.setId(null);
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceGroupMapper.fromId(null)).isNull();
    }
}
