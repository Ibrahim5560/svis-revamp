package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.domain.TemplateFacilitators;
import com.isoft.svisrevamp.repository.TemplateFacilitatorsRepository;
import com.isoft.svisrevamp.service.criteria.TemplateFacilitatorsCriteria;
import com.isoft.svisrevamp.service.dto.TemplateFacilitatorsDTO;
import com.isoft.svisrevamp.service.mapper.TemplateFacilitatorsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TemplateFacilitatorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateFacilitatorsResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;
    private static final Integer SMALLER_COUNT = 1 - 1;

    private static final Long DEFAULT_CENTER_ID = 1L;
    private static final Long UPDATED_CENTER_ID = 2L;
    private static final Long SMALLER_CENTER_ID = 1L - 1L;

    private static final Integer DEFAULT_FACILITATOR_TYPE = 1;
    private static final Integer UPDATED_FACILITATOR_TYPE = 2;
    private static final Integer SMALLER_FACILITATOR_TYPE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/template-facilitators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateFacilitatorsRepository templateFacilitatorsRepository;

    @Autowired
    private TemplateFacilitatorsMapper templateFacilitatorsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateFacilitatorsMockMvc;

    private TemplateFacilitators templateFacilitators;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFacilitators createEntity(EntityManager em) {
        TemplateFacilitators templateFacilitators = new TemplateFacilitators()
            .count(DEFAULT_COUNT)
            .centerId(DEFAULT_CENTER_ID)
            .facilitatorType(DEFAULT_FACILITATOR_TYPE);
        return templateFacilitators;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFacilitators createUpdatedEntity(EntityManager em) {
        TemplateFacilitators templateFacilitators = new TemplateFacilitators()
            .count(UPDATED_COUNT)
            .centerId(UPDATED_CENTER_ID)
            .facilitatorType(UPDATED_FACILITATOR_TYPE);
        return templateFacilitators;
    }

    @BeforeEach
    public void initTest() {
        templateFacilitators = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateFacilitators() throws Exception {
        int databaseSizeBeforeCreate = templateFacilitatorsRepository.findAll().size();
        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);
        restTemplateFacilitatorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateFacilitators testTemplateFacilitators = templateFacilitatorsList.get(templateFacilitatorsList.size() - 1);
        assertThat(testTemplateFacilitators.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testTemplateFacilitators.getCenterId()).isEqualTo(DEFAULT_CENTER_ID);
        assertThat(testTemplateFacilitators.getFacilitatorType()).isEqualTo(DEFAULT_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void createTemplateFacilitatorsWithExistingId() throws Exception {
        // Create the TemplateFacilitators with an existing ID
        templateFacilitators.setId(1L);
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        int databaseSizeBeforeCreate = templateFacilitatorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateFacilitatorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFacilitatorsRepository.findAll().size();
        // set the field null
        templateFacilitators.setCount(null);

        // Create the TemplateFacilitators, which fails.
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        restTemplateFacilitatorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCenterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFacilitatorsRepository.findAll().size();
        // set the field null
        templateFacilitators.setCenterId(null);

        // Create the TemplateFacilitators, which fails.
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        restTemplateFacilitatorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFacilitatorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFacilitatorsRepository.findAll().size();
        // set the field null
        templateFacilitators.setFacilitatorType(null);

        // Create the TemplateFacilitators, which fails.
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        restTemplateFacilitatorsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitators() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateFacilitators.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilitatorType").value(hasItem(DEFAULT_FACILITATOR_TYPE)));
    }

    @Test
    @Transactional
    void getTemplateFacilitators() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get the templateFacilitators
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL_ID, templateFacilitators.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateFacilitators.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.centerId").value(DEFAULT_CENTER_ID.intValue()))
            .andExpect(jsonPath("$.facilitatorType").value(DEFAULT_FACILITATOR_TYPE));
    }

    @Test
    @Transactional
    void getTemplateFacilitatorsByIdFiltering() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        Long id = templateFacilitators.getId();

        defaultTemplateFacilitatorsShouldBeFound("id.equals=" + id);
        defaultTemplateFacilitatorsShouldNotBeFound("id.notEquals=" + id);

        defaultTemplateFacilitatorsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplateFacilitatorsShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplateFacilitatorsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplateFacilitatorsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count equals to DEFAULT_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the templateFacilitatorsList where count equals to UPDATED_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the templateFacilitatorsList where count equals to UPDATED_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count is not null
        defaultTemplateFacilitatorsShouldBeFound("count.specified=true");

        // Get all the templateFacilitatorsList where count is null
        defaultTemplateFacilitatorsShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count is greater than or equal to DEFAULT_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the templateFacilitatorsList where count is greater than or equal to UPDATED_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count is less than or equal to DEFAULT_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the templateFacilitatorsList where count is less than or equal to SMALLER_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count is less than DEFAULT_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the templateFacilitatorsList where count is less than UPDATED_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where count is greater than DEFAULT_COUNT
        defaultTemplateFacilitatorsShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the templateFacilitatorsList where count is greater than SMALLER_COUNT
        defaultTemplateFacilitatorsShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId equals to DEFAULT_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.equals=" + DEFAULT_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId equals to UPDATED_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.equals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsInShouldWork() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId in DEFAULT_CENTER_ID or UPDATED_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.in=" + DEFAULT_CENTER_ID + "," + UPDATED_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId equals to UPDATED_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.in=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId is not null
        defaultTemplateFacilitatorsShouldBeFound("centerId.specified=true");

        // Get all the templateFacilitatorsList where centerId is null
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId is greater than or equal to DEFAULT_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.greaterThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId is greater than or equal to UPDATED_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.greaterThanOrEqual=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId is less than or equal to DEFAULT_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.lessThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId is less than or equal to SMALLER_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.lessThanOrEqual=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId is less than DEFAULT_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.lessThan=" + DEFAULT_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId is less than UPDATED_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.lessThan=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByCenterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where centerId is greater than DEFAULT_CENTER_ID
        defaultTemplateFacilitatorsShouldNotBeFound("centerId.greaterThan=" + DEFAULT_CENTER_ID);

        // Get all the templateFacilitatorsList where centerId is greater than SMALLER_CENTER_ID
        defaultTemplateFacilitatorsShouldBeFound("centerId.greaterThan=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType equals to DEFAULT_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.equals=" + DEFAULT_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType equals to UPDATED_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.equals=" + UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType in DEFAULT_FACILITATOR_TYPE or UPDATED_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.in=" + DEFAULT_FACILITATOR_TYPE + "," + UPDATED_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType equals to UPDATED_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.in=" + UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType is not null
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.specified=true");

        // Get all the templateFacilitatorsList where facilitatorType is null
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType is greater than or equal to DEFAULT_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.greaterThanOrEqual=" + DEFAULT_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType is greater than or equal to UPDATED_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.greaterThanOrEqual=" + UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType is less than or equal to DEFAULT_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.lessThanOrEqual=" + DEFAULT_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType is less than or equal to SMALLER_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.lessThanOrEqual=" + SMALLER_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType is less than DEFAULT_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.lessThan=" + DEFAULT_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType is less than UPDATED_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.lessThan=" + UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByFacilitatorTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        // Get all the templateFacilitatorsList where facilitatorType is greater than DEFAULT_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldNotBeFound("facilitatorType.greaterThan=" + DEFAULT_FACILITATOR_TYPE);

        // Get all the templateFacilitatorsList where facilitatorType is greater than SMALLER_FACILITATOR_TYPE
        defaultTemplateFacilitatorsShouldBeFound("facilitatorType.greaterThan=" + SMALLER_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateFacilitatorsByTemplateIsEqualToSomething() throws Exception {
        Template template;
        if (TestUtil.findAll(em, Template.class).isEmpty()) {
            templateFacilitatorsRepository.saveAndFlush(templateFacilitators);
            template = TemplateResourceIT.createEntity(em);
        } else {
            template = TestUtil.findAll(em, Template.class).get(0);
        }
        em.persist(template);
        em.flush();
        templateFacilitators.setTemplate(template);
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);
        Long templateId = template.getId();

        // Get all the templateFacilitatorsList where template equals to templateId
        defaultTemplateFacilitatorsShouldBeFound("templateId.equals=" + templateId);

        // Get all the templateFacilitatorsList where template equals to (templateId + 1)
        defaultTemplateFacilitatorsShouldNotBeFound("templateId.equals=" + (templateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplateFacilitatorsShouldBeFound(String filter) throws Exception {
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateFacilitators.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilitatorType").value(hasItem(DEFAULT_FACILITATOR_TYPE)));

        // Check, that the count call also returns 1
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplateFacilitatorsShouldNotBeFound(String filter) throws Exception {
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateFacilitatorsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemplateFacilitators() throws Exception {
        // Get the templateFacilitators
        restTemplateFacilitatorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplateFacilitators() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();

        // Update the templateFacilitators
        TemplateFacilitators updatedTemplateFacilitators = templateFacilitatorsRepository.findById(templateFacilitators.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateFacilitators are not directly saved in db
        em.detach(updatedTemplateFacilitators);
        updatedTemplateFacilitators.count(UPDATED_COUNT).centerId(UPDATED_CENTER_ID).facilitatorType(UPDATED_FACILITATOR_TYPE);
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(updatedTemplateFacilitators);

        restTemplateFacilitatorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateFacilitatorsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
        TemplateFacilitators testTemplateFacilitators = templateFacilitatorsList.get(templateFacilitatorsList.size() - 1);
        assertThat(testTemplateFacilitators.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testTemplateFacilitators.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testTemplateFacilitators.getFacilitatorType()).isEqualTo(UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateFacilitatorsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateFacilitatorsWithPatch() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();

        // Update the templateFacilitators using partial update
        TemplateFacilitators partialUpdatedTemplateFacilitators = new TemplateFacilitators();
        partialUpdatedTemplateFacilitators.setId(templateFacilitators.getId());

        partialUpdatedTemplateFacilitators.centerId(UPDATED_CENTER_ID);

        restTemplateFacilitatorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateFacilitators.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateFacilitators))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
        TemplateFacilitators testTemplateFacilitators = templateFacilitatorsList.get(templateFacilitatorsList.size() - 1);
        assertThat(testTemplateFacilitators.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testTemplateFacilitators.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testTemplateFacilitators.getFacilitatorType()).isEqualTo(DEFAULT_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTemplateFacilitatorsWithPatch() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();

        // Update the templateFacilitators using partial update
        TemplateFacilitators partialUpdatedTemplateFacilitators = new TemplateFacilitators();
        partialUpdatedTemplateFacilitators.setId(templateFacilitators.getId());

        partialUpdatedTemplateFacilitators.count(UPDATED_COUNT).centerId(UPDATED_CENTER_ID).facilitatorType(UPDATED_FACILITATOR_TYPE);

        restTemplateFacilitatorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateFacilitators.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateFacilitators))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
        TemplateFacilitators testTemplateFacilitators = templateFacilitatorsList.get(templateFacilitatorsList.size() - 1);
        assertThat(testTemplateFacilitators.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testTemplateFacilitators.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testTemplateFacilitators.getFacilitatorType()).isEqualTo(UPDATED_FACILITATOR_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateFacilitatorsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateFacilitators() throws Exception {
        int databaseSizeBeforeUpdate = templateFacilitatorsRepository.findAll().size();
        templateFacilitators.setId(count.incrementAndGet());

        // Create the TemplateFacilitators
        TemplateFacilitatorsDTO templateFacilitatorsDTO = templateFacilitatorsMapper.toDto(templateFacilitators);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFacilitatorsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFacilitatorsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateFacilitators in the database
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateFacilitators() throws Exception {
        // Initialize the database
        templateFacilitatorsRepository.saveAndFlush(templateFacilitators);

        int databaseSizeBeforeDelete = templateFacilitatorsRepository.findAll().size();

        // Delete the templateFacilitators
        restTemplateFacilitatorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateFacilitators.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateFacilitators> templateFacilitatorsList = templateFacilitatorsRepository.findAll();
        assertThat(templateFacilitatorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
