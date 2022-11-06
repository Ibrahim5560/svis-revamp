package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.domain.ExamQuestions;
import com.isoft.svisrevamp.repository.ExamQuestionsRepository;
import com.isoft.svisrevamp.service.criteria.ExamQuestionsCriteria;
import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import com.isoft.svisrevamp.service.mapper.ExamQuestionsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ExamQuestionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamQuestionsResourceIT {

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

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;
    private static final Double SMALLER_SCORE = 1D - 1D;

    private static final Instant DEFAULT_START_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUBMIT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMIT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;
    private static final Integer SMALLER_CATEGORY_ID = 1 - 1;

    private static final Integer DEFAULT_QUESTION_ID = 1;
    private static final Integer UPDATED_QUESTION_ID = 2;
    private static final Integer SMALLER_QUESTION_ID = 1 - 1;

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;
    private static final Integer SMALLER_SEQ = 1 - 1;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/exam-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExamQuestionsRepository examQuestionsRepository;

    @Autowired
    private ExamQuestionsMapper examQuestionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamQuestionsMockMvc;

    private ExamQuestions examQuestions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamQuestions createEntity(EntityManager em) {
        ExamQuestions examQuestions = new ExamQuestions()
            .descAr(DEFAULT_DESC_AR)
            .descEn(DEFAULT_DESC_EN)
            .code(DEFAULT_CODE)
            .imgPath(DEFAULT_IMG_PATH)
            .timeInSec(DEFAULT_TIME_IN_SEC)
            .type(DEFAULT_TYPE)
            .weight(DEFAULT_WEIGHT)
            .score(DEFAULT_SCORE)
            .startAt(DEFAULT_START_AT)
            .submitAt(DEFAULT_SUBMIT_AT)
            .categoryId(DEFAULT_CATEGORY_ID)
            .questionId(DEFAULT_QUESTION_ID)
            .seq(DEFAULT_SEQ)
            .status(DEFAULT_STATUS);
        return examQuestions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamQuestions createUpdatedEntity(EntityManager em) {
        ExamQuestions examQuestions = new ExamQuestions()
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .score(UPDATED_SCORE)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .categoryId(UPDATED_CATEGORY_ID)
            .questionId(UPDATED_QUESTION_ID)
            .seq(UPDATED_SEQ)
            .status(UPDATED_STATUS);
        return examQuestions;
    }

    @BeforeEach
    public void initTest() {
        examQuestions = createEntity(em);
    }

    @Test
    @Transactional
    void createExamQuestions() throws Exception {
        int databaseSizeBeforeCreate = examQuestionsRepository.findAll().size();
        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);
        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeCreate + 1);
        ExamQuestions testExamQuestions = examQuestionsList.get(examQuestionsList.size() - 1);
        assertThat(testExamQuestions.getDescAr()).isEqualTo(DEFAULT_DESC_AR);
        assertThat(testExamQuestions.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testExamQuestions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExamQuestions.getImgPath()).isEqualTo(DEFAULT_IMG_PATH);
        assertThat(testExamQuestions.getTimeInSec()).isEqualTo(DEFAULT_TIME_IN_SEC);
        assertThat(testExamQuestions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExamQuestions.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testExamQuestions.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testExamQuestions.getStartAt()).isEqualTo(DEFAULT_START_AT);
        assertThat(testExamQuestions.getSubmitAt()).isEqualTo(DEFAULT_SUBMIT_AT);
        assertThat(testExamQuestions.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testExamQuestions.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testExamQuestions.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testExamQuestions.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createExamQuestionsWithExistingId() throws Exception {
        // Create the ExamQuestions with an existing ID
        examQuestions.setId(1L);
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        int databaseSizeBeforeCreate = examQuestionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescArIsRequired() throws Exception {
        int databaseSizeBeforeTest = examQuestionsRepository.findAll().size();
        // set the field null
        examQuestions.setDescAr(null);

        // Create the ExamQuestions, which fails.
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = examQuestionsRepository.findAll().size();
        // set the field null
        examQuestions.setDescEn(null);

        // Create the ExamQuestions, which fails.
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examQuestionsRepository.findAll().size();
        // set the field null
        examQuestions.setCode(null);

        // Create the ExamQuestions, which fails.
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examQuestionsRepository.findAll().size();
        // set the field null
        examQuestions.setType(null);

        // Create the ExamQuestions, which fails.
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = examQuestionsRepository.findAll().size();
        // set the field null
        examQuestions.setWeight(null);

        // Create the ExamQuestions, which fails.
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        restExamQuestionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExamQuestions() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examQuestions.getId().intValue())))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imgPath").value(hasItem(DEFAULT_IMG_PATH)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(DEFAULT_START_AT.toString())))
            .andExpect(jsonPath("$.[*].submitAt").value(hasItem(DEFAULT_SUBMIT_AT.toString())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].questionId").value(hasItem(DEFAULT_QUESTION_ID)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getExamQuestions() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get the examQuestions
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL_ID, examQuestions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examQuestions.getId().intValue()))
            .andExpect(jsonPath("$.descAr").value(DEFAULT_DESC_AR))
            .andExpect(jsonPath("$.descEn").value(DEFAULT_DESC_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imgPath").value(DEFAULT_IMG_PATH))
            .andExpect(jsonPath("$.timeInSec").value(DEFAULT_TIME_IN_SEC.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.startAt").value(DEFAULT_START_AT.toString()))
            .andExpect(jsonPath("$.submitAt").value(DEFAULT_SUBMIT_AT.toString()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.questionId").value(DEFAULT_QUESTION_ID))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getExamQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        Long id = examQuestions.getId();

        defaultExamQuestionsShouldBeFound("id.equals=" + id);
        defaultExamQuestionsShouldNotBeFound("id.notEquals=" + id);

        defaultExamQuestionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExamQuestionsShouldNotBeFound("id.greaterThan=" + id);

        defaultExamQuestionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExamQuestionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescArIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descAr equals to DEFAULT_DESC_AR
        defaultExamQuestionsShouldBeFound("descAr.equals=" + DEFAULT_DESC_AR);

        // Get all the examQuestionsList where descAr equals to UPDATED_DESC_AR
        defaultExamQuestionsShouldNotBeFound("descAr.equals=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescArIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descAr in DEFAULT_DESC_AR or UPDATED_DESC_AR
        defaultExamQuestionsShouldBeFound("descAr.in=" + DEFAULT_DESC_AR + "," + UPDATED_DESC_AR);

        // Get all the examQuestionsList where descAr equals to UPDATED_DESC_AR
        defaultExamQuestionsShouldNotBeFound("descAr.in=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescArIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descAr is not null
        defaultExamQuestionsShouldBeFound("descAr.specified=true");

        // Get all the examQuestionsList where descAr is null
        defaultExamQuestionsShouldNotBeFound("descAr.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescArContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descAr contains DEFAULT_DESC_AR
        defaultExamQuestionsShouldBeFound("descAr.contains=" + DEFAULT_DESC_AR);

        // Get all the examQuestionsList where descAr contains UPDATED_DESC_AR
        defaultExamQuestionsShouldNotBeFound("descAr.contains=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescArNotContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descAr does not contain DEFAULT_DESC_AR
        defaultExamQuestionsShouldNotBeFound("descAr.doesNotContain=" + DEFAULT_DESC_AR);

        // Get all the examQuestionsList where descAr does not contain UPDATED_DESC_AR
        defaultExamQuestionsShouldBeFound("descAr.doesNotContain=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescEnIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descEn equals to DEFAULT_DESC_EN
        defaultExamQuestionsShouldBeFound("descEn.equals=" + DEFAULT_DESC_EN);

        // Get all the examQuestionsList where descEn equals to UPDATED_DESC_EN
        defaultExamQuestionsShouldNotBeFound("descEn.equals=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescEnIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descEn in DEFAULT_DESC_EN or UPDATED_DESC_EN
        defaultExamQuestionsShouldBeFound("descEn.in=" + DEFAULT_DESC_EN + "," + UPDATED_DESC_EN);

        // Get all the examQuestionsList where descEn equals to UPDATED_DESC_EN
        defaultExamQuestionsShouldNotBeFound("descEn.in=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descEn is not null
        defaultExamQuestionsShouldBeFound("descEn.specified=true");

        // Get all the examQuestionsList where descEn is null
        defaultExamQuestionsShouldNotBeFound("descEn.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescEnContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descEn contains DEFAULT_DESC_EN
        defaultExamQuestionsShouldBeFound("descEn.contains=" + DEFAULT_DESC_EN);

        // Get all the examQuestionsList where descEn contains UPDATED_DESC_EN
        defaultExamQuestionsShouldNotBeFound("descEn.contains=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByDescEnNotContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where descEn does not contain DEFAULT_DESC_EN
        defaultExamQuestionsShouldNotBeFound("descEn.doesNotContain=" + DEFAULT_DESC_EN);

        // Get all the examQuestionsList where descEn does not contain UPDATED_DESC_EN
        defaultExamQuestionsShouldBeFound("descEn.doesNotContain=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where code equals to DEFAULT_CODE
        defaultExamQuestionsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the examQuestionsList where code equals to UPDATED_CODE
        defaultExamQuestionsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultExamQuestionsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the examQuestionsList where code equals to UPDATED_CODE
        defaultExamQuestionsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where code is not null
        defaultExamQuestionsShouldBeFound("code.specified=true");

        // Get all the examQuestionsList where code is null
        defaultExamQuestionsShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where code contains DEFAULT_CODE
        defaultExamQuestionsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the examQuestionsList where code contains UPDATED_CODE
        defaultExamQuestionsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where code does not contain DEFAULT_CODE
        defaultExamQuestionsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the examQuestionsList where code does not contain UPDATED_CODE
        defaultExamQuestionsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByImgPathIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where imgPath equals to DEFAULT_IMG_PATH
        defaultExamQuestionsShouldBeFound("imgPath.equals=" + DEFAULT_IMG_PATH);

        // Get all the examQuestionsList where imgPath equals to UPDATED_IMG_PATH
        defaultExamQuestionsShouldNotBeFound("imgPath.equals=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByImgPathIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where imgPath in DEFAULT_IMG_PATH or UPDATED_IMG_PATH
        defaultExamQuestionsShouldBeFound("imgPath.in=" + DEFAULT_IMG_PATH + "," + UPDATED_IMG_PATH);

        // Get all the examQuestionsList where imgPath equals to UPDATED_IMG_PATH
        defaultExamQuestionsShouldNotBeFound("imgPath.in=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByImgPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where imgPath is not null
        defaultExamQuestionsShouldBeFound("imgPath.specified=true");

        // Get all the examQuestionsList where imgPath is null
        defaultExamQuestionsShouldNotBeFound("imgPath.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByImgPathContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where imgPath contains DEFAULT_IMG_PATH
        defaultExamQuestionsShouldBeFound("imgPath.contains=" + DEFAULT_IMG_PATH);

        // Get all the examQuestionsList where imgPath contains UPDATED_IMG_PATH
        defaultExamQuestionsShouldNotBeFound("imgPath.contains=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByImgPathNotContainsSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where imgPath does not contain DEFAULT_IMG_PATH
        defaultExamQuestionsShouldNotBeFound("imgPath.doesNotContain=" + DEFAULT_IMG_PATH);

        // Get all the examQuestionsList where imgPath does not contain UPDATED_IMG_PATH
        defaultExamQuestionsShouldBeFound("imgPath.doesNotContain=" + UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec equals to DEFAULT_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.equals=" + DEFAULT_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.equals=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec in DEFAULT_TIME_IN_SEC or UPDATED_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.in=" + DEFAULT_TIME_IN_SEC + "," + UPDATED_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.in=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec is not null
        defaultExamQuestionsShouldBeFound("timeInSec.specified=true");

        // Get all the examQuestionsList where timeInSec is null
        defaultExamQuestionsShouldNotBeFound("timeInSec.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec is greater than or equal to DEFAULT_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.greaterThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec is greater than or equal to UPDATED_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.greaterThanOrEqual=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec is less than or equal to DEFAULT_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.lessThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec is less than or equal to SMALLER_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.lessThanOrEqual=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec is less than DEFAULT_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.lessThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec is less than UPDATED_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.lessThan=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTimeInSecIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where timeInSec is greater than DEFAULT_TIME_IN_SEC
        defaultExamQuestionsShouldNotBeFound("timeInSec.greaterThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the examQuestionsList where timeInSec is greater than SMALLER_TIME_IN_SEC
        defaultExamQuestionsShouldBeFound("timeInSec.greaterThan=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type equals to DEFAULT_TYPE
        defaultExamQuestionsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the examQuestionsList where type equals to UPDATED_TYPE
        defaultExamQuestionsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultExamQuestionsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the examQuestionsList where type equals to UPDATED_TYPE
        defaultExamQuestionsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type is not null
        defaultExamQuestionsShouldBeFound("type.specified=true");

        // Get all the examQuestionsList where type is null
        defaultExamQuestionsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type is greater than or equal to DEFAULT_TYPE
        defaultExamQuestionsShouldBeFound("type.greaterThanOrEqual=" + DEFAULT_TYPE);

        // Get all the examQuestionsList where type is greater than or equal to UPDATED_TYPE
        defaultExamQuestionsShouldNotBeFound("type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type is less than or equal to DEFAULT_TYPE
        defaultExamQuestionsShouldBeFound("type.lessThanOrEqual=" + DEFAULT_TYPE);

        // Get all the examQuestionsList where type is less than or equal to SMALLER_TYPE
        defaultExamQuestionsShouldNotBeFound("type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type is less than DEFAULT_TYPE
        defaultExamQuestionsShouldNotBeFound("type.lessThan=" + DEFAULT_TYPE);

        // Get all the examQuestionsList where type is less than UPDATED_TYPE
        defaultExamQuestionsShouldBeFound("type.lessThan=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where type is greater than DEFAULT_TYPE
        defaultExamQuestionsShouldNotBeFound("type.greaterThan=" + DEFAULT_TYPE);

        // Get all the examQuestionsList where type is greater than SMALLER_TYPE
        defaultExamQuestionsShouldBeFound("type.greaterThan=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight equals to DEFAULT_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the examQuestionsList where weight equals to UPDATED_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the examQuestionsList where weight equals to UPDATED_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight is not null
        defaultExamQuestionsShouldBeFound("weight.specified=true");

        // Get all the examQuestionsList where weight is null
        defaultExamQuestionsShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the examQuestionsList where weight is greater than or equal to UPDATED_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight is less than or equal to DEFAULT_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the examQuestionsList where weight is less than or equal to SMALLER_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight is less than DEFAULT_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the examQuestionsList where weight is less than UPDATED_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where weight is greater than DEFAULT_WEIGHT
        defaultExamQuestionsShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the examQuestionsList where weight is greater than SMALLER_WEIGHT
        defaultExamQuestionsShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score equals to DEFAULT_SCORE
        defaultExamQuestionsShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the examQuestionsList where score equals to UPDATED_SCORE
        defaultExamQuestionsShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultExamQuestionsShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the examQuestionsList where score equals to UPDATED_SCORE
        defaultExamQuestionsShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score is not null
        defaultExamQuestionsShouldBeFound("score.specified=true");

        // Get all the examQuestionsList where score is null
        defaultExamQuestionsShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score is greater than or equal to DEFAULT_SCORE
        defaultExamQuestionsShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the examQuestionsList where score is greater than or equal to UPDATED_SCORE
        defaultExamQuestionsShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score is less than or equal to DEFAULT_SCORE
        defaultExamQuestionsShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the examQuestionsList where score is less than or equal to SMALLER_SCORE
        defaultExamQuestionsShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score is less than DEFAULT_SCORE
        defaultExamQuestionsShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the examQuestionsList where score is less than UPDATED_SCORE
        defaultExamQuestionsShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where score is greater than DEFAULT_SCORE
        defaultExamQuestionsShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the examQuestionsList where score is greater than SMALLER_SCORE
        defaultExamQuestionsShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStartAtIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where startAt equals to DEFAULT_START_AT
        defaultExamQuestionsShouldBeFound("startAt.equals=" + DEFAULT_START_AT);

        // Get all the examQuestionsList where startAt equals to UPDATED_START_AT
        defaultExamQuestionsShouldNotBeFound("startAt.equals=" + UPDATED_START_AT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStartAtIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where startAt in DEFAULT_START_AT or UPDATED_START_AT
        defaultExamQuestionsShouldBeFound("startAt.in=" + DEFAULT_START_AT + "," + UPDATED_START_AT);

        // Get all the examQuestionsList where startAt equals to UPDATED_START_AT
        defaultExamQuestionsShouldNotBeFound("startAt.in=" + UPDATED_START_AT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStartAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where startAt is not null
        defaultExamQuestionsShouldBeFound("startAt.specified=true");

        // Get all the examQuestionsList where startAt is null
        defaultExamQuestionsShouldNotBeFound("startAt.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySubmitAtIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where submitAt equals to DEFAULT_SUBMIT_AT
        defaultExamQuestionsShouldBeFound("submitAt.equals=" + DEFAULT_SUBMIT_AT);

        // Get all the examQuestionsList where submitAt equals to UPDATED_SUBMIT_AT
        defaultExamQuestionsShouldNotBeFound("submitAt.equals=" + UPDATED_SUBMIT_AT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySubmitAtIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where submitAt in DEFAULT_SUBMIT_AT or UPDATED_SUBMIT_AT
        defaultExamQuestionsShouldBeFound("submitAt.in=" + DEFAULT_SUBMIT_AT + "," + UPDATED_SUBMIT_AT);

        // Get all the examQuestionsList where submitAt equals to UPDATED_SUBMIT_AT
        defaultExamQuestionsShouldNotBeFound("submitAt.in=" + UPDATED_SUBMIT_AT);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySubmitAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where submitAt is not null
        defaultExamQuestionsShouldBeFound("submitAt.specified=true");

        // Get all the examQuestionsList where submitAt is null
        defaultExamQuestionsShouldNotBeFound("submitAt.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId equals to DEFAULT_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.equals=" + DEFAULT_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId equals to UPDATED_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.equals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId in DEFAULT_CATEGORY_ID or UPDATED_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.in=" + DEFAULT_CATEGORY_ID + "," + UPDATED_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId equals to UPDATED_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.in=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId is not null
        defaultExamQuestionsShouldBeFound("categoryId.specified=true");

        // Get all the examQuestionsList where categoryId is null
        defaultExamQuestionsShouldNotBeFound("categoryId.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId is greater than or equal to DEFAULT_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.greaterThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId is greater than or equal to UPDATED_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.greaterThanOrEqual=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId is less than or equal to DEFAULT_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.lessThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId is less than or equal to SMALLER_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.lessThanOrEqual=" + SMALLER_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId is less than DEFAULT_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.lessThan=" + DEFAULT_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId is less than UPDATED_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.lessThan=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where categoryId is greater than DEFAULT_CATEGORY_ID
        defaultExamQuestionsShouldNotBeFound("categoryId.greaterThan=" + DEFAULT_CATEGORY_ID);

        // Get all the examQuestionsList where categoryId is greater than SMALLER_CATEGORY_ID
        defaultExamQuestionsShouldBeFound("categoryId.greaterThan=" + SMALLER_CATEGORY_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId equals to DEFAULT_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.equals=" + DEFAULT_QUESTION_ID);

        // Get all the examQuestionsList where questionId equals to UPDATED_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.equals=" + UPDATED_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId in DEFAULT_QUESTION_ID or UPDATED_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.in=" + DEFAULT_QUESTION_ID + "," + UPDATED_QUESTION_ID);

        // Get all the examQuestionsList where questionId equals to UPDATED_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.in=" + UPDATED_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId is not null
        defaultExamQuestionsShouldBeFound("questionId.specified=true");

        // Get all the examQuestionsList where questionId is null
        defaultExamQuestionsShouldNotBeFound("questionId.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId is greater than or equal to DEFAULT_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.greaterThanOrEqual=" + DEFAULT_QUESTION_ID);

        // Get all the examQuestionsList where questionId is greater than or equal to UPDATED_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.greaterThanOrEqual=" + UPDATED_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId is less than or equal to DEFAULT_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.lessThanOrEqual=" + DEFAULT_QUESTION_ID);

        // Get all the examQuestionsList where questionId is less than or equal to SMALLER_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.lessThanOrEqual=" + SMALLER_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId is less than DEFAULT_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.lessThan=" + DEFAULT_QUESTION_ID);

        // Get all the examQuestionsList where questionId is less than UPDATED_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.lessThan=" + UPDATED_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByQuestionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where questionId is greater than DEFAULT_QUESTION_ID
        defaultExamQuestionsShouldNotBeFound("questionId.greaterThan=" + DEFAULT_QUESTION_ID);

        // Get all the examQuestionsList where questionId is greater than SMALLER_QUESTION_ID
        defaultExamQuestionsShouldBeFound("questionId.greaterThan=" + SMALLER_QUESTION_ID);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq equals to DEFAULT_SEQ
        defaultExamQuestionsShouldBeFound("seq.equals=" + DEFAULT_SEQ);

        // Get all the examQuestionsList where seq equals to UPDATED_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.equals=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq in DEFAULT_SEQ or UPDATED_SEQ
        defaultExamQuestionsShouldBeFound("seq.in=" + DEFAULT_SEQ + "," + UPDATED_SEQ);

        // Get all the examQuestionsList where seq equals to UPDATED_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.in=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq is not null
        defaultExamQuestionsShouldBeFound("seq.specified=true");

        // Get all the examQuestionsList where seq is null
        defaultExamQuestionsShouldNotBeFound("seq.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq is greater than or equal to DEFAULT_SEQ
        defaultExamQuestionsShouldBeFound("seq.greaterThanOrEqual=" + DEFAULT_SEQ);

        // Get all the examQuestionsList where seq is greater than or equal to UPDATED_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.greaterThanOrEqual=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq is less than or equal to DEFAULT_SEQ
        defaultExamQuestionsShouldBeFound("seq.lessThanOrEqual=" + DEFAULT_SEQ);

        // Get all the examQuestionsList where seq is less than or equal to SMALLER_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.lessThanOrEqual=" + SMALLER_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq is less than DEFAULT_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.lessThan=" + DEFAULT_SEQ);

        // Get all the examQuestionsList where seq is less than UPDATED_SEQ
        defaultExamQuestionsShouldBeFound("seq.lessThan=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsBySeqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where seq is greater than DEFAULT_SEQ
        defaultExamQuestionsShouldNotBeFound("seq.greaterThan=" + DEFAULT_SEQ);

        // Get all the examQuestionsList where seq is greater than SMALLER_SEQ
        defaultExamQuestionsShouldBeFound("seq.greaterThan=" + SMALLER_SEQ);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status equals to DEFAULT_STATUS
        defaultExamQuestionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the examQuestionsList where status equals to UPDATED_STATUS
        defaultExamQuestionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultExamQuestionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the examQuestionsList where status equals to UPDATED_STATUS
        defaultExamQuestionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status is not null
        defaultExamQuestionsShouldBeFound("status.specified=true");

        // Get all the examQuestionsList where status is null
        defaultExamQuestionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status is greater than or equal to DEFAULT_STATUS
        defaultExamQuestionsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the examQuestionsList where status is greater than or equal to UPDATED_STATUS
        defaultExamQuestionsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status is less than or equal to DEFAULT_STATUS
        defaultExamQuestionsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the examQuestionsList where status is less than or equal to SMALLER_STATUS
        defaultExamQuestionsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status is less than DEFAULT_STATUS
        defaultExamQuestionsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the examQuestionsList where status is less than UPDATED_STATUS
        defaultExamQuestionsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        // Get all the examQuestionsList where status is greater than DEFAULT_STATUS
        defaultExamQuestionsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the examQuestionsList where status is greater than SMALLER_STATUS
        defaultExamQuestionsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllExamQuestionsByExamIsEqualToSomething() throws Exception {
        Exam exam;
        if (TestUtil.findAll(em, Exam.class).isEmpty()) {
            examQuestionsRepository.saveAndFlush(examQuestions);
            exam = ExamResourceIT.createEntity(em);
        } else {
            exam = TestUtil.findAll(em, Exam.class).get(0);
        }
        em.persist(exam);
        em.flush();
        examQuestions.setExam(exam);
        examQuestionsRepository.saveAndFlush(examQuestions);
        Long examId = exam.getId();

        // Get all the examQuestionsList where exam equals to examId
        defaultExamQuestionsShouldBeFound("examId.equals=" + examId);

        // Get all the examQuestionsList where exam equals to (examId + 1)
        defaultExamQuestionsShouldNotBeFound("examId.equals=" + (examId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExamQuestionsShouldBeFound(String filter) throws Exception {
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examQuestions.getId().intValue())))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imgPath").value(hasItem(DEFAULT_IMG_PATH)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(DEFAULT_START_AT.toString())))
            .andExpect(jsonPath("$.[*].submitAt").value(hasItem(DEFAULT_SUBMIT_AT.toString())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].questionId").value(hasItem(DEFAULT_QUESTION_ID)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExamQuestionsShouldNotBeFound(String filter) throws Exception {
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExamQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExamQuestions() throws Exception {
        // Get the examQuestions
        restExamQuestionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExamQuestions() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();

        // Update the examQuestions
        ExamQuestions updatedExamQuestions = examQuestionsRepository.findById(examQuestions.getId()).get();
        // Disconnect from session so that the updates on updatedExamQuestions are not directly saved in db
        em.detach(updatedExamQuestions);
        updatedExamQuestions
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .score(UPDATED_SCORE)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .categoryId(UPDATED_CATEGORY_ID)
            .questionId(UPDATED_QUESTION_ID)
            .seq(UPDATED_SEQ)
            .status(UPDATED_STATUS);
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(updatedExamQuestions);

        restExamQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examQuestionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
        ExamQuestions testExamQuestions = examQuestionsList.get(examQuestionsList.size() - 1);
        assertThat(testExamQuestions.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testExamQuestions.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testExamQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExamQuestions.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
        assertThat(testExamQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExamQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExamQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExamQuestions.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExamQuestions.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExamQuestions.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExamQuestions.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testExamQuestions.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testExamQuestions.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testExamQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examQuestionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamQuestionsWithPatch() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();

        // Update the examQuestions using partial update
        ExamQuestions partialUpdatedExamQuestions = new ExamQuestions();
        partialUpdatedExamQuestions.setId(examQuestions.getId());

        partialUpdatedExamQuestions
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .score(UPDATED_SCORE)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .status(UPDATED_STATUS);

        restExamQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamQuestions))
            )
            .andExpect(status().isOk());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
        ExamQuestions testExamQuestions = examQuestionsList.get(examQuestionsList.size() - 1);
        assertThat(testExamQuestions.getDescAr()).isEqualTo(DEFAULT_DESC_AR);
        assertThat(testExamQuestions.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testExamQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExamQuestions.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
        assertThat(testExamQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExamQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExamQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExamQuestions.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExamQuestions.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExamQuestions.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExamQuestions.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testExamQuestions.getQuestionId()).isEqualTo(DEFAULT_QUESTION_ID);
        assertThat(testExamQuestions.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testExamQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateExamQuestionsWithPatch() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();

        // Update the examQuestions using partial update
        ExamQuestions partialUpdatedExamQuestions = new ExamQuestions();
        partialUpdatedExamQuestions.setId(examQuestions.getId());

        partialUpdatedExamQuestions
            .descAr(UPDATED_DESC_AR)
            .descEn(UPDATED_DESC_EN)
            .code(UPDATED_CODE)
            .imgPath(UPDATED_IMG_PATH)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .score(UPDATED_SCORE)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .categoryId(UPDATED_CATEGORY_ID)
            .questionId(UPDATED_QUESTION_ID)
            .seq(UPDATED_SEQ)
            .status(UPDATED_STATUS);

        restExamQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamQuestions))
            )
            .andExpect(status().isOk());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
        ExamQuestions testExamQuestions = examQuestionsList.get(examQuestionsList.size() - 1);
        assertThat(testExamQuestions.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testExamQuestions.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testExamQuestions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExamQuestions.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
        assertThat(testExamQuestions.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExamQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExamQuestions.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExamQuestions.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExamQuestions.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExamQuestions.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExamQuestions.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testExamQuestions.getQuestionId()).isEqualTo(UPDATED_QUESTION_ID);
        assertThat(testExamQuestions.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testExamQuestions.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examQuestionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionsRepository.findAll().size();
        examQuestions.setId(count.incrementAndGet());

        // Create the ExamQuestions
        ExamQuestionsDTO examQuestionsDTO = examQuestionsMapper.toDto(examQuestions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examQuestionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamQuestions in the database
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamQuestions() throws Exception {
        // Initialize the database
        examQuestionsRepository.saveAndFlush(examQuestions);

        int databaseSizeBeforeDelete = examQuestionsRepository.findAll().size();

        // Delete the examQuestions
        restExamQuestionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, examQuestions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findAll();
        assertThat(examQuestionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
