package au.gov.qld.sir.web.rest;

import au.gov.qld.sir.JhipSirAdminApp;
import au.gov.qld.sir.domain.ServiceTag;
import au.gov.qld.sir.domain.ServiceTag;
import au.gov.qld.sir.domain.ServiceTagItems;
import au.gov.qld.sir.repository.ServiceTagRepository;
import au.gov.qld.sir.service.ServiceTagService;
import au.gov.qld.sir.service.dto.ServiceTagDTO;
import au.gov.qld.sir.service.mapper.ServiceTagMapper;
import au.gov.qld.sir.web.rest.errors.ExceptionTranslator;
import au.gov.qld.sir.service.dto.ServiceTagCriteria;
import au.gov.qld.sir.service.ServiceTagQueryService;

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
 * Integration tests for the {@Link ServiceTagResource} REST controller.
 */
@SpringBootTest(classes = JhipSirAdminApp.class)
public class ServiceTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ServiceTagRepository serviceTagRepository;

    @Autowired
    private ServiceTagMapper serviceTagMapper;

    @Autowired
    private ServiceTagService serviceTagService;

    @Autowired
    private ServiceTagQueryService serviceTagQueryService;

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

    private MockMvc restServiceTagMockMvc;

    private ServiceTag serviceTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceTagResource serviceTagResource = new ServiceTagResource(serviceTagService, serviceTagQueryService);
        this.restServiceTagMockMvc = MockMvcBuilders.standaloneSetup(serviceTagResource)
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
    public static ServiceTag createEntity(EntityManager em) {
        ServiceTag serviceTag = new ServiceTag()
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDateTime(DEFAULT_CREATED_DATE_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDateTime(DEFAULT_MODIFIED_DATE_TIME);
        return serviceTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceTag createUpdatedEntity(EntityManager em) {
        ServiceTag serviceTag = new ServiceTag()
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        return serviceTag;
    }

    @BeforeEach
    public void initTest() {
        serviceTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceTag() throws Exception {
        int databaseSizeBeforeCreate = serviceTagRepository.findAll().size();

        // Create the ServiceTag
        ServiceTagDTO serviceTagDTO = serviceTagMapper.toDto(serviceTag);
        restServiceTagMockMvc.perform(post("/api/service-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceTag in the database
        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceTag testServiceTag = serviceTagList.get(serviceTagList.size() - 1);
        assertThat(testServiceTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceTag.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServiceTag.getCreatedDateTime()).isEqualTo(DEFAULT_CREATED_DATE_TIME);
        assertThat(testServiceTag.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServiceTag.getModifiedDateTime()).isEqualTo(DEFAULT_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void createServiceTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceTagRepository.findAll().size();

        // Create the ServiceTag with an existing ID
        serviceTag.setId(1L);
        ServiceTagDTO serviceTagDTO = serviceTagMapper.toDto(serviceTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceTagMockMvc.perform(post("/api/service-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceTag in the database
        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceTagRepository.findAll().size();
        // set the field null
        serviceTag.setName(null);

        // Create the ServiceTag, which fails.
        ServiceTagDTO serviceTagDTO = serviceTagMapper.toDto(serviceTag);

        restServiceTagMockMvc.perform(post("/api/service-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceTags() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList
        restServiceTagMockMvc.perform(get("/api/service-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceTag() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get the serviceTag
        restServiceTagMockMvc.perform(get("/api/service-tags/{id}", serviceTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDateTime").value(DEFAULT_CREATED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDateTime").value(DEFAULT_MODIFIED_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where name equals to DEFAULT_NAME
        defaultServiceTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the serviceTagList where name equals to UPDATED_NAME
        defaultServiceTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultServiceTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the serviceTagList where name equals to UPDATED_NAME
        defaultServiceTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where name is not null
        defaultServiceTagShouldBeFound("name.specified=true");

        // Get all the serviceTagList where name is null
        defaultServiceTagShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdBy equals to DEFAULT_CREATED_BY
        defaultServiceTagShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the serviceTagList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceTagShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultServiceTagShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the serviceTagList where createdBy equals to UPDATED_CREATED_BY
        defaultServiceTagShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdBy is not null
        defaultServiceTagShouldBeFound("createdBy.specified=true");

        // Get all the serviceTagList where createdBy is null
        defaultServiceTagShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdDateTime equals to DEFAULT_CREATED_DATE_TIME
        defaultServiceTagShouldBeFound("createdDateTime.equals=" + DEFAULT_CREATED_DATE_TIME);

        // Get all the serviceTagList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceTagShouldNotBeFound("createdDateTime.equals=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdDateTime in DEFAULT_CREATED_DATE_TIME or UPDATED_CREATED_DATE_TIME
        defaultServiceTagShouldBeFound("createdDateTime.in=" + DEFAULT_CREATED_DATE_TIME + "," + UPDATED_CREATED_DATE_TIME);

        // Get all the serviceTagList where createdDateTime equals to UPDATED_CREATED_DATE_TIME
        defaultServiceTagShouldNotBeFound("createdDateTime.in=" + UPDATED_CREATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByCreatedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where createdDateTime is not null
        defaultServiceTagShouldBeFound("createdDateTime.specified=true");

        // Get all the serviceTagList where createdDateTime is null
        defaultServiceTagShouldNotBeFound("createdDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultServiceTagShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the serviceTagList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceTagShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultServiceTagShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the serviceTagList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultServiceTagShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedBy is not null
        defaultServiceTagShouldBeFound("modifiedBy.specified=true");

        // Get all the serviceTagList where modifiedBy is null
        defaultServiceTagShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedDateTime equals to DEFAULT_MODIFIED_DATE_TIME
        defaultServiceTagShouldBeFound("modifiedDateTime.equals=" + DEFAULT_MODIFIED_DATE_TIME);

        // Get all the serviceTagList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagShouldNotBeFound("modifiedDateTime.equals=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedDateTime in DEFAULT_MODIFIED_DATE_TIME or UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagShouldBeFound("modifiedDateTime.in=" + DEFAULT_MODIFIED_DATE_TIME + "," + UPDATED_MODIFIED_DATE_TIME);

        // Get all the serviceTagList where modifiedDateTime equals to UPDATED_MODIFIED_DATE_TIME
        defaultServiceTagShouldNotBeFound("modifiedDateTime.in=" + UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllServiceTagsByModifiedDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        // Get all the serviceTagList where modifiedDateTime is not null
        defaultServiceTagShouldBeFound("modifiedDateTime.specified=true");

        // Get all the serviceTagList where modifiedDateTime is null
        defaultServiceTagShouldNotBeFound("modifiedDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceTagsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceTag parent = ServiceTagResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        serviceTag.setParent(parent);
        serviceTagRepository.saveAndFlush(serviceTag);
        Long parentId = parent.getId();

        // Get all the serviceTagList where parent equals to parentId
        defaultServiceTagShouldBeFound("parentId.equals=" + parentId);

        // Get all the serviceTagList where parent equals to parentId + 1
        defaultServiceTagShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceTagsByServiceTagIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceTag serviceTag = ServiceTagResourceIT.createEntity(em);
        em.persist(serviceTag);
        em.flush();
        serviceTag.addServiceTag(serviceTag);
        serviceTagRepository.saveAndFlush(serviceTag);
        Long serviceTagId = serviceTag.getId();

        // Get all the serviceTagList where serviceTag equals to serviceTagId
        defaultServiceTagShouldBeFound("serviceTagId.equals=" + serviceTagId);

        // Get all the serviceTagList where serviceTag equals to serviceTagId + 1
        defaultServiceTagShouldNotBeFound("serviceTagId.equals=" + (serviceTagId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceTagsByServiceTagItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        ServiceTagItems serviceTagItems = ServiceTagItemsResourceIT.createEntity(em);
        em.persist(serviceTagItems);
        em.flush();
        serviceTag.addServiceTagItems(serviceTagItems);
        serviceTagRepository.saveAndFlush(serviceTag);
        Long serviceTagItemsId = serviceTagItems.getId();

        // Get all the serviceTagList where serviceTagItems equals to serviceTagItemsId
        defaultServiceTagShouldBeFound("serviceTagItemsId.equals=" + serviceTagItemsId);

        // Get all the serviceTagList where serviceTagItems equals to serviceTagItemsId + 1
        defaultServiceTagShouldNotBeFound("serviceTagItemsId.equals=" + (serviceTagItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceTagShouldBeFound(String filter) throws Exception {
        restServiceTagMockMvc.perform(get("/api/service-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDateTime").value(hasItem(DEFAULT_CREATED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDateTime").value(hasItem(DEFAULT_MODIFIED_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceTagMockMvc.perform(get("/api/service-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceTagShouldNotBeFound(String filter) throws Exception {
        restServiceTagMockMvc.perform(get("/api/service-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceTagMockMvc.perform(get("/api/service-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceTag() throws Exception {
        // Get the serviceTag
        restServiceTagMockMvc.perform(get("/api/service-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceTag() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        int databaseSizeBeforeUpdate = serviceTagRepository.findAll().size();

        // Update the serviceTag
        ServiceTag updatedServiceTag = serviceTagRepository.findById(serviceTag.getId()).get();
        // Disconnect from session so that the updates on updatedServiceTag are not directly saved in db
        em.detach(updatedServiceTag);
        updatedServiceTag
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDateTime(UPDATED_CREATED_DATE_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDateTime(UPDATED_MODIFIED_DATE_TIME);
        ServiceTagDTO serviceTagDTO = serviceTagMapper.toDto(updatedServiceTag);

        restServiceTagMockMvc.perform(put("/api/service-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceTag in the database
        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeUpdate);
        ServiceTag testServiceTag = serviceTagList.get(serviceTagList.size() - 1);
        assertThat(testServiceTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceTag.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServiceTag.getCreatedDateTime()).isEqualTo(UPDATED_CREATED_DATE_TIME);
        assertThat(testServiceTag.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServiceTag.getModifiedDateTime()).isEqualTo(UPDATED_MODIFIED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceTag() throws Exception {
        int databaseSizeBeforeUpdate = serviceTagRepository.findAll().size();

        // Create the ServiceTag
        ServiceTagDTO serviceTagDTO = serviceTagMapper.toDto(serviceTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceTagMockMvc.perform(put("/api/service-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceTag in the database
        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceTag() throws Exception {
        // Initialize the database
        serviceTagRepository.saveAndFlush(serviceTag);

        int databaseSizeBeforeDelete = serviceTagRepository.findAll().size();

        // Delete the serviceTag
        restServiceTagMockMvc.perform(delete("/api/service-tags/{id}", serviceTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceTag> serviceTagList = serviceTagRepository.findAll();
        assertThat(serviceTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceTag.class);
        ServiceTag serviceTag1 = new ServiceTag();
        serviceTag1.setId(1L);
        ServiceTag serviceTag2 = new ServiceTag();
        serviceTag2.setId(serviceTag1.getId());
        assertThat(serviceTag1).isEqualTo(serviceTag2);
        serviceTag2.setId(2L);
        assertThat(serviceTag1).isNotEqualTo(serviceTag2);
        serviceTag1.setId(null);
        assertThat(serviceTag1).isNotEqualTo(serviceTag2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceTagDTO.class);
        ServiceTagDTO serviceTagDTO1 = new ServiceTagDTO();
        serviceTagDTO1.setId(1L);
        ServiceTagDTO serviceTagDTO2 = new ServiceTagDTO();
        assertThat(serviceTagDTO1).isNotEqualTo(serviceTagDTO2);
        serviceTagDTO2.setId(serviceTagDTO1.getId());
        assertThat(serviceTagDTO1).isEqualTo(serviceTagDTO2);
        serviceTagDTO2.setId(2L);
        assertThat(serviceTagDTO1).isNotEqualTo(serviceTagDTO2);
        serviceTagDTO1.setId(null);
        assertThat(serviceTagDTO1).isNotEqualTo(serviceTagDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceTagMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceTagMapper.fromId(null)).isNull();
    }
}
