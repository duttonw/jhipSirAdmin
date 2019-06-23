package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceTagItems;
import au.gov.qld.sir.domain.ServiceRecord;
import au.gov.qld.sir.domain.ServiceTag;
import au.gov.qld.sir.repository.ServiceTagItemsRepository;
import au.gov.qld.sir.service.ServiceTagItemsService;
import au.gov.qld.sir.service.dto.ServiceTagItemsDTO;
import au.gov.qld.sir.service.mapper.ServiceTagItemsMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceTagItemsCriteria;
import au.gov.qld.sir.service.ServiceTagItemsQueryService;

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
 * Integration tests for the {@Link ServiceTagItemsResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceTagItemsResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ServiceTagItemsRepository serviceTagItemsRepository;

    @Autowired
    private ServiceTagItemsMapper serviceTagItemsMapper;

    @Autowired
    private ServiceTagItemsService serviceTagItemsService;

    @Autowired
    private ServiceTagItemsQueryService serviceTagItemsQueryService;

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

    private MockMvc restServiceTagItemsMockMvc;

    private ServiceTagItems serviceTagItems;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceTagItemsResource serviceTagItemsResource = new ServiceTagItemsResource(serviceTagItemsService, serviceTagItemsQueryService);
        this.restServiceTagItemsMockMvc = MockMvcBuilders.standaloneSetup(serviceTagItemsResource)
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
    public static ServiceTagItems createEntity(EntityManager em) {
        ServiceTagItems serviceTagItems = new ServiceTagItems()
            .source(DEFAULT_SOURCE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        serviceTagItems.setServiceRecord(serviceRecord);
        // Add required entity
        ServiceTag serviceTag;
        if (TestUtil.findAll(em, ServiceTag.class).isEmpty()) {
            serviceTag = ServiceTagResourceIT.createEntity(em);
            em.persist(serviceTag);
            em.flush();
        } else {
            serviceTag = TestUtil.findAll(em, ServiceTag.class).get(0);
        }
        serviceTagItems.setServiceTag(serviceTag);
        return serviceTagItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceTagItems createUpdatedEntity(EntityManager em) {
        ServiceTagItems serviceTagItems = new ServiceTagItems()
            .source(UPDATED_SOURCE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        // Add required entity
        ServiceRecord serviceRecord;
        if (TestUtil.findAll(em, ServiceRecord.class).isEmpty()) {
            serviceRecord = ServiceRecordResourceIT.createUpdatedEntity(em);
            em.persist(serviceRecord);
            em.flush();
        } else {
            serviceRecord = TestUtil.findAll(em, ServiceRecord.class).get(0);
        }
        serviceTagItems.setServiceRecord(serviceRecord);
        // Add required entity
        ServiceTag serviceTag;
        if (TestUtil.findAll(em, ServiceTag.class).isEmpty()) {
            serviceTag = ServiceTagResourceIT.createUpdatedEntity(em);
            em.persist(serviceTag);
            em.flush();
        } else {
            serviceTag = TestUtil.findAll(em, ServiceTag.class).get(0);
        }
        serviceTagItems.setServiceTag(serviceTag);
        return serviceTagItems;
    }

    @BeforeEach
    public void initTest() {
        serviceTagItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceTagItems() throws Exception {
        int databaseSizeBeforeCreate = serviceTagItemsRepository.findAll().size();

        // Create the ServiceTagItems
        ServiceTagItemsDTO serviceTagItemsDTO = serviceTagItemsMapper.toDto(serviceTagItems);
        restServiceTagItemsMockMvc.perform(post("/api/service-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceTagItems in the database
        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceTagItems testServiceTagItems = serviceTagItemsList.get(serviceTagItemsList.size() - 1);
        assertThat(testServiceTagItems.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testServiceTagItems.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceTagItems.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceTagItems.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceTagItems.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void createServiceTagItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceTagItemsRepository.findAll().size();

        // Create the ServiceTagItems with an existing ID
        serviceTagItems.setId(1L);
        ServiceTagItemsDTO serviceTagItemsDTO = serviceTagItemsMapper.toDto(serviceTagItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceTagItemsMockMvc.perform(post("/api/service-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceTagItems in the database
        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceTagItemsRepository.findAll().size();
        // set the field null
        serviceTagItems.setSource(null);

        // Create the ServiceTagItems, which fails.
        ServiceTagItemsDTO serviceTagItemsDTO = serviceTagItemsMapper.toDto(serviceTagItems);

        restServiceTagItemsMockMvc.perform(post("/api/service-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagItemsDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceTagItems() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceTagItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceTagItems() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get the serviceTagItems
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items/{id}", serviceTagItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceTagItems.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where source equals to DEFAULT_SOURCE
        defaultServiceTagItemsShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the serviceTagItemsList where source equals to UPDATED_SOURCE
        defaultServiceTagItemsShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultServiceTagItemsShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the serviceTagItemsList where source equals to UPDATED_SOURCE
        defaultServiceTagItemsShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where source is not null
        defaultServiceTagItemsShouldBeFound("source.specified=true");

        // Get all the serviceTagItemsList where source is null
        defaultServiceTagItemsShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceTagItemsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceTagItemsList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceTagItemsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceTagItemsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceTagItemsList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceTagItemsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdBy is not null
        defaultServiceTagItemsShouldBeFound("createdBy.specified=true");

        // Get all the serviceTagItemsList where createdBy is null
        defaultServiceTagItemsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceTagItemsShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceTagItemsList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceTagItemsShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceTagItemsShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceTagItemsList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceTagItemsShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where createdDateTime is not null
        defaultServiceTagItemsShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceTagItemsList where createdDateTime is null
        defaultServiceTagItemsShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceTagItemsShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceTagItemsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceTagItemsShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceTagItemsShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceTagItemsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceTagItemsShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedBy is not null
        defaultServiceTagItemsShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceTagItemsList where modifiedBy is null
        defaultServiceTagItemsShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceTagItemsShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceTagItemsList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagItemsShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagItemsShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceTagItemsList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagItemsShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        // Get all the serviceTagItemsList where modifiedDateTime is not null
        defaultServiceTagItemsShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceTagItemsList where modifiedDateTime is null
        defaultServiceTagItemsShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagItemsByServiceRecordIsEqualToSomething() throws Exception {
        // Get already existing entity
        ServiceRecord serviceRecord = serviceTagItems.getServiceRecord();
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);
        Long serviceRecordId = serviceRecord.getId();

        // Get all the serviceTagItemsList where serviceRecord equals to serviceRecordId
        defaultServiceTagItemsShouldBeFound("serviceRecordId.equals=" + serviceRecordId);

        // Get all the serviceTagItemsList where serviceRecord equals to serviceRecordId + 1
        defaultServiceTagItemsShouldNotBeFound("serviceRecordId.equals=" + (serviceRecordId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceTagItemsByServiceTagIsEqualToSomething() throws Exception {
        // Get already existing entity
        ServiceTag serviceTag = serviceTagItems.getServiceTag();
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);
        Long serviceTagId = serviceTag.getId();

        // Get all the serviceTagItemsList where serviceTag equals to serviceTagId
        defaultServiceTagItemsShouldBeFound("serviceTagId.equals=" + serviceTagId);

        // Get all the serviceTagItemsList where serviceTag equals to serviceTagId + 1
        defaultServiceTagItemsShouldNotBeFound("serviceTagId.equals=" + (serviceTagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceTagItemsShouldBeFound(String filter) throws Exception {
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceTagItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceTagItemsShouldNotBeFound(String filter) throws Exception {
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceTagItems() throws Exception {
        // Get the serviceTagItems
        restServiceTagItemsMockMvc.perform(get("/api/service-tag-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceTagItems() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        int databaseSizeBeforeUpdate = serviceTagItemsRepository.findAll().size();

        // Update the serviceTagItems
        ServiceTagItems updatedServiceTagItems = serviceTagItemsRepository.findById(serviceTagItems.getId()).get();
        // Disconnect from session so that the updates on updatedServiceTagItems are not directly saved in db
        em.detach(updatedServiceTagItems);
        updatedServiceTagItems
            .source(UPDATED_SOURCE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        ServiceTagItemsDTO serviceTagItemsDTO = serviceTagItemsMapper.toDto(updatedServiceTagItems);

        restServiceTagItemsMockMvc.perform(put("/api/service-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagItemsDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceTagItems in the database
        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeUpdate);
        ServiceTagItems testServiceTagItems = serviceTagItemsList.get(serviceTagItemsList.size() - 1);
        assertThat(testServiceTagItems.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testServiceTagItems.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceTagItems.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceTagItems.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceTagItems.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceTagItems() throws Exception {
        int databaseSizeBeforeUpdate = serviceTagItemsRepository.findAll().size();

        // Create the ServiceTagItems
        ServiceTagItemsDTO serviceTagItemsDTO = serviceTagItemsMapper.toDto(serviceTagItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceTagItemsMockMvc.perform(put("/api/service-tag-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceTagItems in the database
        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceTagItems() throws Exception {
        // Initialize the database
        serviceTagItemsRepository.saveAndFlush(serviceTagItems);

        int databaseSizeBeforeDelete = serviceTagItemsRepository.findAll().size();

        // Delete the serviceTagItems
        restServiceTagItemsMockMvc.perform(delete("/api/service-tag-items/{id}", serviceTagItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceTagItems> serviceTagItemsList = serviceTagItemsRepository.findAll();
        assertThat(serviceTagItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceTagItems.class);
        ServiceTagItems serviceTagItems1 = new ServiceTagItems();
        serviceTagItems1.setId(1L);
        ServiceTagItems serviceTagItems2 = new ServiceTagItems();
        serviceTagItems2.setId(serviceTagItems1.getId());
        assertThat(serviceTagItems1).isEqualTo(serviceTagItems2);
        serviceTagItems2.setId(2L);
        assertThat(serviceTagItems1).isNotEqualTo(serviceTagItems2);
        serviceTagItems1.setId(null);
        assertThat(serviceTagItems1).isNotEqualTo(serviceTagItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceTagItemsDTO.class);
        ServiceTagItemsDTO serviceTagItemsDTO1 = new ServiceTagItemsDTO();
        serviceTagItemsDTO1.setId(1L);
        ServiceTagItemsDTO serviceTagItemsDTO2 = new ServiceTagItemsDTO();
        assertThat(serviceTagItemsDTO1).isNotEqualTo(serviceTagItemsDTO2);
        serviceTagItemsDTO2.setId(serviceTagItemsDTO1.getId());
        assertThat(serviceTagItemsDTO1).isEqualTo(serviceTagItemsDTO2);
        serviceTagItemsDTO2.setId(2L);
        assertThat(serviceTagItemsDTO1).isNotEqualTo(serviceTagItemsDTO2);
        serviceTagItemsDTO1.setId(null);
        assertThat(serviceTagItemsDTO1).isNotEqualTo(serviceTagItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceTagItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceTagItemsMapper.fromId(null)).isNull();
    }
}
