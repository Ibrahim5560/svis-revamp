package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.domain.Questions;
import com.isoft.svisrevamp.repository.QuestionsRepository;
import com.isoft.svisrevamp.service.criteria.QuestionsCriteria;
import com.isoft.svisrevamp.service.dto.QuestionsDTO;
import com.isoft.svisrevamp.service.mapper.QuestionsMapper;
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
 * Integration tests for the {@link QuestionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsResourceIT {

    private static final String DEFAULT_DESC_AR = "AAAAAAAAAA";
    private static final String UPDATED_DESC_AR = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESC_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMG_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME_IN_SEC = 1L;
    private static final Long UPDATED_TIME_IN_SEC = 2L;
    private static final Long SMALLER_TIME_IN_SEC = 1L - 1L;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;
    private static final Double SMALLER_WEIGHT = 1D - 1D;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .descAr(DEFAULT_DESC_AR)
            .descEn(DEFAULT_DESC_EN)
            .code(DEFAULT_CODE)
            .imgPath(DEFAULT_IMG_PATH)
            .timeInSec(DEFAULT_TIME_IN_SEC)
            .type(DEFAULT_TYPE)
            .weight(DEFAULT_WEIGHT)
            .status(DEFAULT_STATUS);
        return questions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createUpdatedEntity(EntityManager em) {
        Questions questions = new Questions()
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS);
        return questions;
    }

    @BeforeEach
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);
        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getDescAr()).isEqualTo(DEFAULT_DESC_AR);
        assertThat(testQuestions.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testQuestions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testQuestions.getImgPath()).isEqualTo(DEFAULT_IMG_PATH);
        assertThat(testQuestions.getTimeInSec()).isEqualTo(DEFAULT_TIME_IN_SEC);
        assertThat(testQuestions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testQuestions.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testQuestions.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createQuestionsWithExistingId() throws Exception {
        // Create the Questions with an existing ID
        questions.setId(1L);
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescArIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setDescAr(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setDescEn(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setCode(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setType(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setWeight(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imgPath").value(hasItem(DEFAULT_IMG_PATH)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL_ID, questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.descAr").value(DEFAULT_DESC_AR))
            .andExpect(jsonPath("$.descEn").value(DEFAULT_DESC_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imgPath").value(DEFAULT_IMG_PATH))
            .andExpect(jsonPath("$.timeInSec").value(DEFAULT_TIME_IN_SEC.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        Long id = questions.getId();

        defaultQuestionsShouldBeFound("id.equals=" + id);
        defaultQuestionsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescArIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descAr equals to DEFAULT_DESC_AR
        defaultQuestionsShouldBeFound("descAr.equals=" + DEFAULT_DESC_AR);

        // Get all the questionsList where descAr equals to UPDATED_DESC_AR
        defaultQuestionsShouldNotBeFound("descAr.equals=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescArIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descAr in DEFAULT_DESC_AR or UPDATED_DESC_AR
        defaultQuestionsShouldBeFound("descAr.in=" + DEFAULT_DESC_AR + "," + UPDATED_DESC_AR);

        // Get all the questionsList where descAr equals to UPDATED_DESC_AR
        defaultQuestionsShouldNotBeFound("descAr.in=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescArIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descAr is not null
        defaultQuestionsShouldBeFound("descAr.specified=true");

        // Get all the questionsList where descAr is null
        defaultQuestionsShouldNotBeFound("descAr.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByDescArContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descAr contains DEFAULT_DESC_AR
        defaultQuestionsShouldBeFound("descAr.contains=" + DEFAULT_DESC_AR);

        // Get all the questionsList where descAr contains UPDATED_DESC_AR
        defaultQuestionsShouldNotBeFound("descAr.contains=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescArNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descAr does not contain DEFAULT_DESC_AR
        defaultQuestionsShouldNotBeFound("descAr.doesNotContain=" + DEFAULT_DESC_AR);

        // Get all the questionsList where descAr does not contain UPDATED_DESC_AR
        defaultQuestionsShouldBeFound("descAr.doesNotContain=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescEnIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descEn equals to DEFAULT_DESC_EN
        defaultQuestionsShouldBeFound("descEn.equals=" + DEFAULT_DESC_EN);

        // Get all the questionsList where descEn equals to UPDATED_DESC_EN
        defaultQuestionsShouldNotBeFound("descEn.equals=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescEnIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descEn in DEFAULT_DESC_EN or UPDATED_DESC_EN
        defaultQuestionsShouldBeFound("descEn.in=" + DEFAULT_DESC_EN + "," + UPDATED_DESC_EN);

        // Get all the questionsList where descEn equals to UPDATED_DESC_EN
        defaultQuestionsShouldNotBeFound("descEn.in=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descEn is not null
        defaultQuestionsShouldBeFound("descEn.specified=true");

        // Get all the questionsList where descEn is null
        defaultQuestionsShouldNotBeFound("descEn.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByDescEnContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descEn contains DEFAULT_DESC_EN
        defaultQuestionsShouldBeFound("descEn.contains=" + DEFAULT_DESC_EN);

        // Get all the questionsList where descEn contains UPDATED_DESC_EN
        defaultQuestionsShouldNotBeFound("descEn.contains=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllQuestionsByDescEnNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where descEn does not contain DEFAULT_DESC_EN
        defaultQuestionsShouldNotBeFound("descEn.doesNotContain=" + DEFAULT_DESC_EN);

        // Get all the questionsList where descEn does not contain UPDATED_DESC_EN
        defaultQuestionsShouldBeFound("descEn.doesNotContain=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllQuestionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where code equals to DEFAULT_CODE
        defaultQuestionsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the questionsList where code equals to UPDATED_CODE
        defaultQuestionsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuestionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultQuestionsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the questionsList where code equals to UPDATED_CODE
        defaultQuestionsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuestionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where code is not null
        defaultQuestionsShouldBeFound("code.specified=true");

        // Get all the questionsList where code is null
        defaultQuestionsShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where code contains DEFAULT_CODE
        defaultQuestionsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the questionsList where code contains UPDATED_CODE
        defaultQuestionsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuestionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where code does not contain DEFAULT_CODE
        defaultQuestionsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the questionsList where code does not contain UPDATED_CODE
        defaultQuestionsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuestionsByImgPathIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where imgPath equals to DEFAULT_IMG_PATH
        defaultQuestionsShouldBeFound("imgPath.equals=" + DEFAULT_IMG_PATH);

        // Get all the questionsList where imgPath equals to UPDATED_IMG_PATH
        defaultQuestionsShouldNotBeFound("imgPath.equals=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllQuestionsByImgPathIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where imgPath in DEFAULT_IMG_PATH or UPDATED_IMG_PATH
        defaultQuestionsShouldBeFound("imgPath.in=" + DEFAULT_IMG_PATH + "," + UPDATED_IMG_PATH);

        // Get all the questionsList where imgPath equals to UPDATED_IMG_PATH
        defaultQuestionsShouldNotBeFound("imgPath.in=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllQuestionsByImgPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where imgPath is not null
        defaultQuestionsShouldBeFound("imgPath.specified=true");

        // Get all the questionsList where imgPath is null
        defaultQuestionsShouldNotBeFound("imgPath.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByImgPathContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where imgPath contains DEFAULT_IMG_PATH
        defaultQuestionsShouldBeFound("imgPath.contains=" + DEFAULT_IMG_PATH);

        // Get all the questionsList where imgPath contains UPDATED_IMG_PATH
        defaultQuestionsShouldNotBeFound("imgPath.contains=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllQuestionsByImgPathNotContainsSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where imgPath does not contain DEFAULT_IMG_PATH
        defaultQuestionsShouldNotBeFound("imgPath.doesNotContain=" + DEFAULT_IMG_PATH);

        // Get all the questionsList where imgPath does not contain UPDATED_IMG_PATH
        defaultQuestionsShouldBeFound("imgPath.doesNotContain=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec equals to DEFAULT_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.equals=" + DEFAULT_TIME_IN_SEC);

        // Get all the questionsList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.equals=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec in DEFAULT_TIME_IN_SEC or UPDATED_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.in=" + DEFAULT_TIME_IN_SEC + "," + UPDATED_TIME_IN_SEC);

        // Get all the questionsList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.in=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec is not null
        defaultQuestionsShouldBeFound("timeInSec.specified=true");

        // Get all the questionsList where timeInSec is null
        defaultQuestionsShouldNotBeFound("timeInSec.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec is greater than or equal to DEFAULT_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.greaterThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the questionsList where timeInSec is greater than or equal to UPDATED_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.greaterThanOrEqual=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec is less than or equal to DEFAULT_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.lessThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the questionsList where timeInSec is less than or equal to SMALLER_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.lessThanOrEqual=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec is less than DEFAULT_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.lessThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the questionsList where timeInSec is less than UPDATED_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.lessThan=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTimeInSecIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where timeInSec is greater than DEFAULT_TIME_IN_SEC
        defaultQuestionsShouldNotBeFound("timeInSec.greaterThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the questionsList where timeInSec is greater than SMALLER_TIME_IN_SEC
        defaultQuestionsShouldBeFound("timeInSec.greaterThan=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type equals to DEFAULT_TYPE
        defaultQuestionsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the questionsList where type equals to UPDATED_TYPE
        defaultQuestionsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultQuestionsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the questionsList where type equals to UPDATED_TYPE
        defaultQuestionsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type is not null
        defaultQuestionsShouldBeFound("type.specified=true");

        // Get all the questionsList where type is null
        defaultQuestionsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type is greater than or equal to DEFAULT_TYPE
        defaultQuestionsShouldBeFound("type.greaterThanOrEqual=" + DEFAULT_TYPE);

        // Get all the questionsList where type is greater than or equal to UPDATED_TYPE
        defaultQuestionsShouldNotBeFound("type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type is less than or equal to DEFAULT_TYPE
        defaultQuestionsShouldBeFound("type.lessThanOrEqual=" + DEFAULT_TYPE);

        // Get all the questionsList where type is less than or equal to SMALLER_TYPE
        defaultQuestionsShouldNotBeFound("type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type is less than DEFAULT_TYPE
        defaultQuestionsShouldNotBeFound("type.lessThan=" + DEFAULT_TYPE);

        // Get all the questionsList where type is less than UPDATED_TYPE
        defaultQuestionsShouldBeFound("type.lessThan=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where type is greater than DEFAULT_TYPE
        defaultQuestionsShouldNotBeFound("type.greaterThan=" + DEFAULT_TYPE);

        // Get all the questionsList where type is greater than SMALLER_TYPE
        defaultQuestionsShouldBeFound("type.greaterThan=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight equals to DEFAULT_WEIGHT
        defaultQuestionsShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the questionsList where weight equals to UPDATED_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultQuestionsShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the questionsList where weight equals to UPDATED_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight is not null
        defaultQuestionsShouldBeFound("weight.specified=true");

        // Get all the questionsList where weight is null
        defaultQuestionsShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultQuestionsShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the questionsList where weight is greater than or equal to UPDATED_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight is less than or equal to DEFAULT_WEIGHT
        defaultQuestionsShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the questionsList where weight is less than or equal to SMALLER_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight is less than DEFAULT_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the questionsList where weight is less than UPDATED_WEIGHT
        defaultQuestionsShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where weight is greater than DEFAULT_WEIGHT
        defaultQuestionsShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the questionsList where weight is greater than SMALLER_WEIGHT
        defaultQuestionsShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status equals to DEFAULT_STATUS
        defaultQuestionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the questionsList where status equals to UPDATED_STATUS
        defaultQuestionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultQuestionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the questionsList where status equals to UPDATED_STATUS
        defaultQuestionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status is not null
        defaultQuestionsShouldBeFound("status.specified=true");

        // Get all the questionsList where status is null
        defaultQuestionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status is greater than or equal to DEFAULT_STATUS
        defaultQuestionsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the questionsList where status is greater than or equal to UPDATED_STATUS
        defaultQuestionsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status is less than or equal to DEFAULT_STATUS
        defaultQuestionsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the questionsList where status is less than or equal to SMALLER_STATUS
        defaultQuestionsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status is less than DEFAULT_STATUS
        defaultQuestionsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the questionsList where status is less than UPDATED_STATUS
        defaultQuestionsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where status is greater than DEFAULT_STATUS
        defaultQuestionsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the questionsList where status is greater than SMALLER_STATUS
        defaultQuestionsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllQuestionsByCategoriesIsEqualToSomething() throws Exception {
        Categories categories;
        if (TestUtil.findAll(em, Categories.class).isEmpty()) {
            questionsRepository.saveAndFlush(questions);
            categories = CategoriesResourceIT.createEntity(em);
        } else {
            categories = TestUtil.findAll(em, Categories.class).get(0);
        }
        em.persist(categories);
        em.flush();
        questions.setCategories(categories);
        questionsRepository.saveAndFlush(questions);
        Long categoriesId = categories.getId();

        // Get all the questionsList where categories equals to categoriesId
        defaultQuestionsShouldBeFound("categoriesId.equals=" + categoriesId);

        // Get all the questionsList where categories equals to (categoriesId + 1)
        defaultQuestionsShouldNotBeFound("categoriesId.equals=" + (categoriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsShouldBeFound(String filter) throws Exception {
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imgPath").value(hasItem(DEFAULT_IMG_PATH)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsShouldNotBeFound(String filter) throws Exception {
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS);
        QuestionsDTO questionsDTO = questionsMapper.toDto(updatedQuestions);

        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testQuestions.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuestions.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
        assertThat(testQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .descAr(UPDATED_DESC_AR)
            .code(UPDATED_CODE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testQuestions.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuestions.getImgPath()).isEqualTo(DEFAULT_IMG_PATH);
        assertThat(testQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testQuestions.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuestions.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
        assertThat(testQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(count.incrementAndGet());

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Delete the questions
        restQuestionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, questions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
