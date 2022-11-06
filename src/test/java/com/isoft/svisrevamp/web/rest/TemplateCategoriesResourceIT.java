package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.repository.TemplateCategoriesRepository;
import com.isoft.svisrevamp.service.criteria.TemplateCategoriesCriteria;
import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
import com.isoft.svisrevamp.service.mapper.TemplateCategoriesMapper;
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
 * Integration tests for the {@link TemplateCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateCategoriesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_QUESTIONS = 1;
    private static final Integer UPDATED_NO_OF_QUESTIONS = 2;
    private static final Integer SMALLER_NO_OF_QUESTIONS = 1 - 1;

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;
    private static final Integer SMALLER_SEQ = 1 - 1;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/template-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateCategoriesRepository templateCategoriesRepository;

    @Autowired
    private TemplateCategoriesMapper templateCategoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateCategoriesMockMvc;

    private TemplateCategories templateCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateCategories createEntity(EntityManager em) {
        TemplateCategories templateCategories = new TemplateCategories()
            .code(DEFAULT_CODE)
            .noOfQuestions(DEFAULT_NO_OF_QUESTIONS)
            .seq(DEFAULT_SEQ)
            .status(DEFAULT_STATUS);
        return templateCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateCategories createUpdatedEntity(EntityManager em) {
        TemplateCategories templateCategories = new TemplateCategories()
            .code(UPDATED_CODE)
            .noOfQuestions(UPDATED_NO_OF_QUESTIONS)
            .seq(UPDATED_SEQ)
            .status(UPDATED_STATUS);
        return templateCategories;
    }

    @BeforeEach
    public void initTest() {
        templateCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateCategories() throws Exception {
        int databaseSizeBeforeCreate = templateCategoriesRepository.findAll().size();
        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);
        restTemplateCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateCategories testTemplateCategories = templateCategoriesList.get(templateCategoriesList.size() - 1);
        assertThat(testTemplateCategories.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTemplateCategories.getNoOfQuestions()).isEqualTo(DEFAULT_NO_OF_QUESTIONS);
        assertThat(testTemplateCategories.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testTemplateCategories.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createTemplateCategoriesWithExistingId() throws Exception {
        // Create the TemplateCategories with an existing ID
        templateCategories.setId(1L);
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        int databaseSizeBeforeCreate = templateCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoOfQuestionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateCategoriesRepository.findAll().size();
        // set the field null
        templateCategories.setNoOfQuestions(null);

        // Create the TemplateCategories, which fails.
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        restTemplateCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateCategoriesRepository.findAll().size();
        // set the field null
        templateCategories.setSeq(null);

        // Create the TemplateCategories, which fails.
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        restTemplateCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplateCategories() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].noOfQuestions").value(hasItem(DEFAULT_NO_OF_QUESTIONS)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getTemplateCategories() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get the templateCategories
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, templateCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateCategories.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.noOfQuestions").value(DEFAULT_NO_OF_QUESTIONS))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getTemplateCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        Long id = templateCategories.getId();

        defaultTemplateCategoriesShouldBeFound("id.equals=" + id);
        defaultTemplateCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultTemplateCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplateCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplateCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplateCategoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where code equals to DEFAULT_CODE
        defaultTemplateCategoriesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the templateCategoriesList where code equals to UPDATED_CODE
        defaultTemplateCategoriesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTemplateCategoriesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the templateCategoriesList where code equals to UPDATED_CODE
        defaultTemplateCategoriesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where code is not null
        defaultTemplateCategoriesShouldBeFound("code.specified=true");

        // Get all the templateCategoriesList where code is null
        defaultTemplateCategoriesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where code contains DEFAULT_CODE
        defaultTemplateCategoriesShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the templateCategoriesList where code contains UPDATED_CODE
        defaultTemplateCategoriesShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where code does not contain DEFAULT_CODE
        defaultTemplateCategoriesShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the templateCategoriesList where code does not contain UPDATED_CODE
        defaultTemplateCategoriesShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions equals to DEFAULT_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.equals=" + DEFAULT_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions equals to UPDATED_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.equals=" + UPDATED_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsInShouldWork() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions in DEFAULT_NO_OF_QUESTIONS or UPDATED_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.in=" + DEFAULT_NO_OF_QUESTIONS + "," + UPDATED_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions equals to UPDATED_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.in=" + UPDATED_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions is not null
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.specified=true");

        // Get all the templateCategoriesList where noOfQuestions is null
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions is greater than or equal to DEFAULT_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.greaterThanOrEqual=" + DEFAULT_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions is greater than or equal to UPDATED_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.greaterThanOrEqual=" + UPDATED_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions is less than or equal to DEFAULT_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.lessThanOrEqual=" + DEFAULT_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions is less than or equal to SMALLER_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.lessThanOrEqual=" + SMALLER_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsLessThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions is less than DEFAULT_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.lessThan=" + DEFAULT_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions is less than UPDATED_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.lessThan=" + UPDATED_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByNoOfQuestionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where noOfQuestions is greater than DEFAULT_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldNotBeFound("noOfQuestions.greaterThan=" + DEFAULT_NO_OF_QUESTIONS);

        // Get all the templateCategoriesList where noOfQuestions is greater than SMALLER_NO_OF_QUESTIONS
        defaultTemplateCategoriesShouldBeFound("noOfQuestions.greaterThan=" + SMALLER_NO_OF_QUESTIONS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq equals to DEFAULT_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.equals=" + DEFAULT_SEQ);

        // Get all the templateCategoriesList where seq equals to UPDATED_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.equals=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsInShouldWork() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq in DEFAULT_SEQ or UPDATED_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.in=" + DEFAULT_SEQ + "," + UPDATED_SEQ);

        // Get all the templateCategoriesList where seq equals to UPDATED_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.in=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq is not null
        defaultTemplateCategoriesShouldBeFound("seq.specified=true");

        // Get all the templateCategoriesList where seq is null
        defaultTemplateCategoriesShouldNotBeFound("seq.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq is greater than or equal to DEFAULT_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.greaterThanOrEqual=" + DEFAULT_SEQ);

        // Get all the templateCategoriesList where seq is greater than or equal to UPDATED_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.greaterThanOrEqual=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq is less than or equal to DEFAULT_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.lessThanOrEqual=" + DEFAULT_SEQ);

        // Get all the templateCategoriesList where seq is less than or equal to SMALLER_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.lessThanOrEqual=" + SMALLER_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsLessThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq is less than DEFAULT_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.lessThan=" + DEFAULT_SEQ);

        // Get all the templateCategoriesList where seq is less than UPDATED_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.lessThan=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesBySeqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where seq is greater than DEFAULT_SEQ
        defaultTemplateCategoriesShouldNotBeFound("seq.greaterThan=" + DEFAULT_SEQ);

        // Get all the templateCategoriesList where seq is greater than SMALLER_SEQ
        defaultTemplateCategoriesShouldBeFound("seq.greaterThan=" + SMALLER_SEQ);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status equals to DEFAULT_STATUS
        defaultTemplateCategoriesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the templateCategoriesList where status equals to UPDATED_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTemplateCategoriesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the templateCategoriesList where status equals to UPDATED_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status is not null
        defaultTemplateCategoriesShouldBeFound("status.specified=true");

        // Get all the templateCategoriesList where status is null
        defaultTemplateCategoriesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status is greater than or equal to DEFAULT_STATUS
        defaultTemplateCategoriesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the templateCategoriesList where status is greater than or equal to UPDATED_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status is less than or equal to DEFAULT_STATUS
        defaultTemplateCategoriesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the templateCategoriesList where status is less than or equal to SMALLER_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status is less than DEFAULT_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the templateCategoriesList where status is less than UPDATED_STATUS
        defaultTemplateCategoriesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        // Get all the templateCategoriesList where status is greater than DEFAULT_STATUS
        defaultTemplateCategoriesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the templateCategoriesList where status is greater than SMALLER_STATUS
        defaultTemplateCategoriesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByTemplateIsEqualToSomething() throws Exception {
        Template template;
        if (TestUtil.findAll(em, Template.class).isEmpty()) {
            templateCategoriesRepository.saveAndFlush(templateCategories);
            template = TemplateResourceIT.createEntity(em);
        } else {
            template = TestUtil.findAll(em, Template.class).get(0);
        }
        em.persist(template);
        em.flush();
        templateCategories.setTemplate(template);
        templateCategoriesRepository.saveAndFlush(templateCategories);
        Long templateId = template.getId();

        // Get all the templateCategoriesList where template equals to templateId
        defaultTemplateCategoriesShouldBeFound("templateId.equals=" + templateId);

        // Get all the templateCategoriesList where template equals to (templateId + 1)
        defaultTemplateCategoriesShouldNotBeFound("templateId.equals=" + (templateId + 1));
    }

    @Test
    @Transactional
    void getAllTemplateCategoriesByCategoriesIsEqualToSomething() throws Exception {
        Categories categories;
        if (TestUtil.findAll(em, Categories.class).isEmpty()) {
            templateCategoriesRepository.saveAndFlush(templateCategories);
            categories = CategoriesResourceIT.createEntity(em);
        } else {
            categories = TestUtil.findAll(em, Categories.class).get(0);
        }
        em.persist(categories);
        em.flush();
        templateCategories.setCategories(categories);
        templateCategoriesRepository.saveAndFlush(templateCategories);
        Long categoriesId = categories.getId();

        // Get all the templateCategoriesList where categories equals to categoriesId
        defaultTemplateCategoriesShouldBeFound("categoriesId.equals=" + categoriesId);

        // Get all the templateCategoriesList where categories equals to (categoriesId + 1)
        defaultTemplateCategoriesShouldNotBeFound("categoriesId.equals=" + (categoriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplateCategoriesShouldBeFound(String filter) throws Exception {
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].noOfQuestions").value(hasItem(DEFAULT_NO_OF_QUESTIONS)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplateCategoriesShouldNotBeFound(String filter) throws Exception {
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemplateCategories() throws Exception {
        // Get the templateCategories
        restTemplateCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplateCategories() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();

        // Update the templateCategories
        TemplateCategories updatedTemplateCategories = templateCategoriesRepository.findById(templateCategories.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateCategories are not directly saved in db
        em.detach(updatedTemplateCategories);
        updatedTemplateCategories.code(UPDATED_CODE).noOfQuestions(UPDATED_NO_OF_QUESTIONS).seq(UPDATED_SEQ).status(UPDATED_STATUS);
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(updatedTemplateCategories);

        restTemplateCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TemplateCategories testTemplateCategories = templateCategoriesList.get(templateCategoriesList.size() - 1);
        assertThat(testTemplateCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCategories.getNoOfQuestions()).isEqualTo(UPDATED_NO_OF_QUESTIONS);
        assertThat(testTemplateCategories.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testTemplateCategories.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateCategoriesWithPatch() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();

        // Update the templateCategories using partial update
        TemplateCategories partialUpdatedTemplateCategories = new TemplateCategories();
        partialUpdatedTemplateCategories.setId(templateCategories.getId());

        partialUpdatedTemplateCategories.code(UPDATED_CODE).noOfQuestions(UPDATED_NO_OF_QUESTIONS).seq(UPDATED_SEQ);

        restTemplateCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateCategories))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TemplateCategories testTemplateCategories = templateCategoriesList.get(templateCategoriesList.size() - 1);
        assertThat(testTemplateCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCategories.getNoOfQuestions()).isEqualTo(UPDATED_NO_OF_QUESTIONS);
        assertThat(testTemplateCategories.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testTemplateCategories.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTemplateCategoriesWithPatch() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();

        // Update the templateCategories using partial update
        TemplateCategories partialUpdatedTemplateCategories = new TemplateCategories();
        partialUpdatedTemplateCategories.setId(templateCategories.getId());

        partialUpdatedTemplateCategories.code(UPDATED_CODE).noOfQuestions(UPDATED_NO_OF_QUESTIONS).seq(UPDATED_SEQ).status(UPDATED_STATUS);

        restTemplateCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateCategories))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
        TemplateCategories testTemplateCategories = templateCategoriesList.get(templateCategoriesList.size() - 1);
        assertThat(testTemplateCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCategories.getNoOfQuestions()).isEqualTo(UPDATED_NO_OF_QUESTIONS);
        assertThat(testTemplateCategories.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testTemplateCategories.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateCategoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateCategories() throws Exception {
        int databaseSizeBeforeUpdate = templateCategoriesRepository.findAll().size();
        templateCategories.setId(count.incrementAndGet());

        // Create the TemplateCategories
        TemplateCategoriesDTO templateCategoriesDTO = templateCategoriesMapper.toDto(templateCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateCategories in the database
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateCategories() throws Exception {
        // Initialize the database
        templateCategoriesRepository.saveAndFlush(templateCategories);

        int databaseSizeBeforeDelete = templateCategoriesRepository.findAll().size();

        // Delete the templateCategories
        restTemplateCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateCategories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateCategories> templateCategoriesList = templateCategoriesRepository.findAll();
        assertThat(templateCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
