package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.AgencyType;
import au.gov.qld.sir.domain.Agency;
import au.gov.qld.sir.repository.AgencyTypeRepository;
import au.gov.qld.sir.service.AgencyTypeService;
import au.gov.qld.sir.service.dto.AgencyTypeDTO;
import au.gov.qld.sir.service.mapper.AgencyTypeMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.AgencyTypeCriteria;
import au.gov.qld.sir.service.AgencyTypeQueryService;

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
 * Integration tests for the {@Link AgencyTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class AgencyTypeResourceIT {

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

    private static final String DEFAULT_AGENCY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private AgencyTypeRepository agencyTypeRepository;

    @Autowired
    private AgencyTypeMapper agencyTypeMapper;

    @Autowired
    private AgencyTypeService agencyTypeService;

    @Autowired
    private AgencyTypeQueryService agencyTypeQueryService;

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

    private MockMvc restAgencyTypeMockMvc;

    private AgencyType agencyType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencyTypeResource agencyTypeResource = new AgencyTypeResource(agencyTypeService, agencyTypeQueryService);
        this.restAgencyTypeMockMvc = MockMvcBuilders.standaloneSetup(agencyTypeResource)
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
    public static AgencyType createEntity(EntityManager em) {
        AgencyType agencyType = new AgencyType()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME)
            .version(DEFAULT_VERSION)
            .agencyTypeName(DEFAULT_AGENCY_TYPE_NAME);
        return agencyType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgencyType createUpdatedEntity(EntityManager em) {
        AgencyType agencyType = new AgencyType()
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .agencyTypeName(UPDATED_AGENCY_TYPE_NAME);
        return agencyType;
    }

    @BeforeEach
    public void initTest() {
        agencyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgencyType() throws Exception {
        int databaseSizeBeforeCreate = agencyTypeRepository.findAll().size();

        // Create the AgencyType
        AgencyTypeDTO agencyTypeDTO = agencyTypeMapper.toDto(agencyType);
        restAgencyTypeMockMvc.perform(post("/api/agency-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AgencyType in the database
        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AgencyType testAgencyType = agencyTypeList.get(agencyTypeList.size() - 1);
        assertThat(testAgencyType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAgencyType.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testAgencyType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAgencyType.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
        assertThat(testAgencyType.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAgencyType.getAgencyTypeName()).isEqualTo(DEFAULT_AGENCY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createAgencyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencyTypeRepository.findAll().size();

        // Create the AgencyType with an existing ID
        agencyType.setId(1L);
        AgencyTypeDTO agencyTypeDTO = agencyTypeMapper.toDto(agencyType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencyTypeMockMvc.perform(post("/api/agency-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencyType in the database
        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAgencyTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencyTypeRepository.findAll().size();
        // set the field null
        agencyType.setAgencyTypeName(null);

        // Create the AgencyType, which fails.
        AgencyTypeDTO agencyTypeDTO = agencyTypeMapper.toDto(agencyType);

        restAgencyTypeMockMvc.perform(post("/api/agency-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgencyTypes() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList
        restAgencyTypeMockMvc.perform(get("/api/agency-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].agencyTypeName").value(hasItem(DEFAULT_AGENCY_TYPE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAgencyType() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get the agencyType
        restAgencyTypeMockMvc.perform(get("/api/agency-types/{id}", agencyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agencyType.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.agencyTypeName").value(DEFAULT_AGENCY_TYPE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultAgencyTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the agencyTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencyTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAgencyTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the agencyTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultAgencyTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdBy is not null
        defaultAgencyTypeShouldBeFound("createdBy.specified=true");

        // Get all the agencyTypeList where createdBy is null
        defaultAgencyTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultAgencyTypeShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the agencyTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencyTypeShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultAgencyTypeShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the agencyTypeList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultAgencyTypeShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where createdDateTime is not null
        defaultAgencyTypeShouldBeFound("createdDateTime.specified=true");

        // Get all the agencyTypeList where createdDateTime is null
        defaultAgencyTypeShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAgencyTypeShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the agencyTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencyTypeShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAgencyTypeShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the agencyTypeList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAgencyTypeShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedBy is not null
        defaultAgencyTypeShouldBeFound("modifiedBy.specified=true");

        // Get all the agencyTypeList where modifiedBy is null
        defaultAgencyTypeShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultAgencyTypeShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the agencyTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencyTypeShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultAgencyTypeShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the agencyTypeList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultAgencyTypeShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where modifiedDateTime is not null
        defaultAgencyTypeShouldBeFound("modifiedDateTime.specified=true");

        // Get all the agencyTypeList where modifiedDateTime is null
        defaultAgencyTypeShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where version equals to DEFAULT_VERSION
        defaultAgencyTypeShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the agencyTypeList where version equals to UPDATED_VERSION
        defaultAgencyTypeShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAgencyTypeShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the agencyTypeList where version equals to UPDATED_VERSION
        defaultAgencyTypeShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where version is not null
        defaultAgencyTypeShouldBeFound("version.specified=true");

        // Get all the agencyTypeList where version is null
        defaultAgencyTypeShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where version greater than or equals to DEFAULT_VERSION
        defaultAgencyTypeShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the agencyTypeList where version greater than or equals to UPDATED_VERSION
        defaultAgencyTypeShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where version less than or equals to DEFAULT_VERSION
        defaultAgencyTypeShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the agencyTypeList where version less than or equals to UPDATED_VERSION
        defaultAgencyTypeShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllAgencyTypesByAgencyTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where agencyTypeName equals to DEFAULT_AGENCY_TYPE_NAME
        defaultAgencyTypeShouldBeFound("agencyTypeName.equals=" + DEFAULT_AGENCY_TYPE_NAME);

        // Get all the agencyTypeList where agencyTypeName equals to UPDATED_AGENCY_TYPE_NAME
        defaultAgencyTypeShouldNotBeFound("agencyTypeName.equals=" + UPDATED_AGENCY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByAgencyTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where agencyTypeName in DEFAULT_AGENCY_TYPE_NAME or UPDATED_AGENCY_TYPE_NAME
        defaultAgencyTypeShouldBeFound("agencyTypeName.in=" + DEFAULT_AGENCY_TYPE_NAME + "," + UPDATED_AGENCY_TYPE_NAME);

        // Get all the agencyTypeList where agencyTypeName equals to UPDATED_AGENCY_TYPE_NAME
        defaultAgencyTypeShouldNotBeFound("agencyTypeName.in=" + UPDATED_AGENCY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByAgencyTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        // Get all the agencyTypeList where agencyTypeName is not null
        defaultAgencyTypeShouldBeFound("agencyTypeName.specified=true");

        // Get all the agencyTypeList where agencyTypeName is null
        defaultAgencyTypeShouldNotBeFound("agencyTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencyTypesByAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Agency agency = AgencyResourceIT.createEntity(em);
        em.persist(agency);
        em.flush();
        agencyType.addAgency(agency);
        agencyTypeRepository.saveAndFlush(agencyType);
        Long agencyId = agency.getId();

        // Get all the agencyTypeList where agency equals to agencyId
        defaultAgencyTypeShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the agencyTypeList where agency equals to agencyId + 1
        defaultAgencyTypeShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgencyTypeShouldBeFound(String filter) throws Exception {
        restAgencyTypeMockMvc.perform(get("/api/agency-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].agencyTypeName").value(hasItem(DEFAULT_AGENCY_TYPE_NAME)));

        // Check, that the count call also returns 1
        restAgencyTypeMockMvc.perform(get("/api/agency-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgencyTypeShouldNotBeFound(String filter) throws Exception {
        restAgencyTypeMockMvc.perform(get("/api/agency-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgencyTypeMockMvc.perform(get("/api/agency-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgencyType() throws Exception {
        // Get the agencyType
        restAgencyTypeMockMvc.perform(get("/api/agency-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgencyType() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        int databaseSizeBeforeUpdate = agencyTypeRepository.findAll().size();

        // Update the agencyType
        AgencyType updatedAgencyType = agencyTypeRepository.findById(agencyType.getId()).get();
        // Disconnect from session so that the updates on updatedAgencyType are not directly saved in db
        em.detach(updatedAgencyType);
        updatedAgencyType
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME)
            .version(UPDATED_VERSION)
            .agencyTypeName(UPDATED_AGENCY_TYPE_NAME);
        AgencyTypeDTO agencyTypeDTO = agencyTypeMapper.toDto(updatedAgencyType);

        restAgencyTypeMockMvc.perform(put("/api/agency-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AgencyType in the database
        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeUpdate);
        AgencyType testAgencyType = agencyTypeList.get(agencyTypeList.size() - 1);
        assertThat(testAgencyType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAgencyType.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testAgencyType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAgencyType.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
        assertThat(testAgencyType.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAgencyType.getAgencyTypeName()).isEqualTo(UPDATED_AGENCY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAgencyType() throws Exception {
        int databaseSizeBeforeUpdate = agencyTypeRepository.findAll().size();

        // Create the AgencyType
        AgencyTypeDTO agencyTypeDTO = agencyTypeMapper.toDto(agencyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencyTypeMockMvc.perform(put("/api/agency-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencyType in the database
        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgencyType() throws Exception {
        // Initialize the database
        agencyTypeRepository.saveAndFlush(agencyType);

        int databaseSizeBeforeDelete = agencyTypeRepository.findAll().size();

        // Delete the agencyType
        restAgencyTypeMockMvc.perform(delete("/api/agency-types/{id}", agencyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgencyType> agencyTypeList = agencyTypeRepository.findAll();
        assertThat(agencyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyType.class);
        AgencyType agencyType1 = new AgencyType();
        agencyType1.setId(1L);
        AgencyType agencyType2 = new AgencyType();
        agencyType2.setId(agencyType1.getId());
        assertThat(agencyType1).isEqualTo(agencyType2);
        agencyType2.setId(2L);
        assertThat(agencyType1).isNotEqualTo(agencyType2);
        agencyType1.setId(null);
        assertThat(agencyType1).isNotEqualTo(agencyType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyTypeDTO.class);
        AgencyTypeDTO agencyTypeDTO1 = new AgencyTypeDTO();
        agencyTypeDTO1.setId(1L);
        AgencyTypeDTO agencyTypeDTO2 = new AgencyTypeDTO();
        assertThat(agencyTypeDTO1).isNotEqualTo(agencyTypeDTO2);
        agencyTypeDTO2.setId(agencyTypeDTO1.getId());
        assertThat(agencyTypeDTO1).isEqualTo(agencyTypeDTO2);
        agencyTypeDTO2.setId(2L);
        assertThat(agencyTypeDTO1).isNotEqualTo(agencyTypeDTO2);
        agencyTypeDTO1.setId(null);
        assertThat(agencyTypeDTO1).isNotEqualTo(agencyTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencyTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencyTypeMapper.fromId(null)).isNull();
    }
}
