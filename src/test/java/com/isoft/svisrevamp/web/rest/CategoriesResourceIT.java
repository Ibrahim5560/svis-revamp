package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.domain.Questions;
import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.repository.CategoriesRepository;
import com.isoft.svisrevamp.service.criteria.CategoriesCriteria;
import com.isoft.svisrevamp.service.dto.CategoriesDTO;
import com.isoft.svisrevamp.service.mapper.CategoriesMapper;
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
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriesResourceIT {

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories().nameAr(DEFAULT_NAME_AR).nameEn(DEFAULT_NAME_EN).code(DEFAULT_CODE).status(DEFAULT_STATUS);
        return categories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories().nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();
        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);
        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testCategories.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testCategories.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategories.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCategoriesWithExistingId() throws Exception {
        // Create the Categories with an existing ID
        categories.setId(1L);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setNameAr(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setNameEn(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setCode(null);

        // Create the Categories, which fails.
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categories.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        Long id = categories.getId();

        defaultCategoriesShouldBeFound("id.equals=" + id);
        defaultCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameAr equals to DEFAULT_NAME_AR
        defaultCategoriesShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the categoriesList where nameAr equals to UPDATED_NAME_AR
        defaultCategoriesShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultCategoriesShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the categoriesList where nameAr equals to UPDATED_NAME_AR
        defaultCategoriesShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameAr is not null
        defaultCategoriesShouldBeFound("nameAr.specified=true");

        // Get all the categoriesList where nameAr is null
        defaultCategoriesShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByNameArContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameAr contains DEFAULT_NAME_AR
        defaultCategoriesShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the categoriesList where nameAr contains UPDATED_NAME_AR
        defaultCategoriesShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameAr does not contain DEFAULT_NAME_AR
        defaultCategoriesShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the categoriesList where nameAr does not contain UPDATED_NAME_AR
        defaultCategoriesShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameEn equals to DEFAULT_NAME_EN
        defaultCategoriesShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the categoriesList where nameEn equals to UPDATED_NAME_EN
        defaultCategoriesShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultCategoriesShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the categoriesList where nameEn equals to UPDATED_NAME_EN
        defaultCategoriesShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameEn is not null
        defaultCategoriesShouldBeFound("nameEn.specified=true");

        // Get all the categoriesList where nameEn is null
        defaultCategoriesShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameEn contains DEFAULT_NAME_EN
        defaultCategoriesShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the categoriesList where nameEn contains UPDATED_NAME_EN
        defaultCategoriesShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllCategoriesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where nameEn does not contain DEFAULT_NAME_EN
        defaultCategoriesShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the categoriesList where nameEn does not contain UPDATED_NAME_EN
        defaultCategoriesShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where code equals to DEFAULT_CODE
        defaultCategoriesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the categoriesList where code equals to UPDATED_CODE
        defaultCategoriesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCategoriesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the categoriesList where code equals to UPDATED_CODE
        defaultCategoriesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where code is not null
        defaultCategoriesShouldBeFound("code.specified=true");

        // Get all the categoriesList where code is null
        defaultCategoriesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where code contains DEFAULT_CODE
        defaultCategoriesShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the categoriesList where code contains UPDATED_CODE
        defaultCategoriesShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where code does not contain DEFAULT_CODE
        defaultCategoriesShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the categoriesList where code does not contain UPDATED_CODE
        defaultCategoriesShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status equals to DEFAULT_STATUS
        defaultCategoriesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the categoriesList where status equals to UPDATED_STATUS
        defaultCategoriesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCategoriesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the categoriesList where status equals to UPDATED_STATUS
        defaultCategoriesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status is not null
        defaultCategoriesShouldBeFound("status.specified=true");

        // Get all the categoriesList where status is null
        defaultCategoriesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status is greater than or equal to DEFAULT_STATUS
        defaultCategoriesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the categoriesList where status is greater than or equal to UPDATED_STATUS
        defaultCategoriesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status is less than or equal to DEFAULT_STATUS
        defaultCategoriesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the categoriesList where status is less than or equal to SMALLER_STATUS
        defaultCategoriesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status is less than DEFAULT_STATUS
        defaultCategoriesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the categoriesList where status is less than UPDATED_STATUS
        defaultCategoriesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList where status is greater than DEFAULT_STATUS
        defaultCategoriesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the categoriesList where status is greater than SMALLER_STATUS
        defaultCategoriesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllCategoriesByTempCategoriesIsEqualToSomething() throws Exception {
        TemplateCategories tempCategories;
        if (TestUtil.findAll(em, TemplateCategories.class).isEmpty()) {
            categoriesRepository.saveAndFlush(categories);
            tempCategories = TemplateCategoriesResourceIT.createEntity(em);
        } else {
            tempCategories = TestUtil.findAll(em, TemplateCategories.class).get(0);
        }
        em.persist(tempCategories);
        em.flush();
        categories.addTempCategories(tempCategories);
        categoriesRepository.saveAndFlush(categories);
        Long tempCategoriesId = tempCategories.getId();

        // Get all the categoriesList where tempCategories equals to tempCategoriesId
        defaultCategoriesShouldBeFound("tempCategoriesId.equals=" + tempCategoriesId);

        // Get all the categoriesList where tempCategories equals to (tempCategoriesId + 1)
        defaultCategoriesShouldNotBeFound("tempCategoriesId.equals=" + (tempCategoriesId + 1));
    }

    @Test
    @Transactional
    void getAllCategoriesByQuestionsIsEqualToSomething() throws Exception {
        Questions questions;
        if (TestUtil.findAll(em, Questions.class).isEmpty()) {
            categoriesRepository.saveAndFlush(categories);
            questions = QuestionsResourceIT.createEntity(em);
        } else {
            questions = TestUtil.findAll(em, Questions.class).get(0);
        }
        em.persist(questions);
        em.flush();
        categories.addQuestions(questions);
        categoriesRepository.saveAndFlush(categories);
        Long questionsId = questions.getId();

        // Get all the categoriesList where questions equals to questionsId
        defaultCategoriesShouldBeFound("questionsId.equals=" + questionsId);

        // Get all the categoriesList where questions equals to (questionsId + 1)
        defaultCategoriesShouldNotBeFound("questionsId.equals=" + (questionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriesShouldBeFound(String filter) throws Exception {
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriesShouldNotBeFound(String filter) throws Exception {
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getId()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(updatedCategories);

        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testCategories.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategories.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setId(categories.getId());

        partialUpdatedCategories.nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testCategories.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategories.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setId(categories.getId());

        partialUpdatedCategories.nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).status(UPDATED_STATUS);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testCategories.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCategories.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategories.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setId(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, categories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
