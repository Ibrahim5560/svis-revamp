package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.domain.ExamQuestions;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.repository.ExamRepository;
import com.isoft.svisrevamp.service.criteria.ExamCriteria;
import com.isoft.svisrevamp.service.dto.ExamDTO;
import com.isoft.svisrevamp.service.mapper.ExamMapper;
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
 * Integration tests for the {@link ExamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamResourceIT {

    private static final Double DEFAULT_PASS_SCORE = 1D;
    private static final Double UPDATED_PASS_SCORE = 2D;
    private static final Double SMALLER_PASS_SCORE = 1D - 1D;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;
    private static final Double SMALLER_SCORE = 1D - 1D;

    private static final Long DEFAULT_TIME_IN_SEC = 1L;
    private static final Long UPDATED_TIME_IN_SEC = 2L;
    private static final Long SMALLER_TIME_IN_SEC = 1L - 1L;

    private static final Instant DEFAULT_VALIDFROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDFROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALIDTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_START_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUBMIT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMIT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final Long DEFAULT_EXAMINER_ID = 1L;
    private static final Long UPDATED_EXAMINER_ID = 2L;
    private static final Long SMALLER_EXAMINER_ID = 1L - 1L;

    private static final Long DEFAULT_EXAM_CODE = 1L;
    private static final Long UPDATED_EXAM_CODE = 2L;
    private static final Long SMALLER_EXAM_CODE = 1L - 1L;

    private static final Instant DEFAULT_EXAM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXAM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_EXAM_RESULT = 1L;
    private static final Long UPDATED_EXAM_RESULT = 2L;
    private static final Long SMALLER_EXAM_RESULT = 1L - 1L;

    private static final Boolean DEFAULT_EXPORTED = false;
    private static final Boolean UPDATED_EXPORTED = true;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/exams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamMockMvc;

    private Exam exam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exam createEntity(EntityManager em) {
        Exam exam = new Exam()
            .passScore(DEFAULT_PASS_SCORE)
            .score(DEFAULT_SCORE)
            .timeInSec(DEFAULT_TIME_IN_SEC)
            .validfrom(DEFAULT_VALIDFROM)
            .validto(DEFAULT_VALIDTO)
            .startAt(DEFAULT_START_AT)
            .submitAt(DEFAULT_SUBMIT_AT)
            .userId(DEFAULT_USER_ID)
            .examinerId(DEFAULT_EXAMINER_ID)
            .examCode(DEFAULT_EXAM_CODE)
            .examDate(DEFAULT_EXAM_DATE)
            .examResult(DEFAULT_EXAM_RESULT)
            .exported(DEFAULT_EXPORTED)
            .status(DEFAULT_STATUS);
        return exam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exam createUpdatedEntity(EntityManager em) {
        Exam exam = new Exam()
            .passScore(UPDATED_PASS_SCORE)
            .score(UPDATED_SCORE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .validfrom(UPDATED_VALIDFROM)
            .validto(UPDATED_VALIDTO)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .userId(UPDATED_USER_ID)
            .examinerId(UPDATED_EXAMINER_ID)
            .examCode(UPDATED_EXAM_CODE)
            .examDate(UPDATED_EXAM_DATE)
            .examResult(UPDATED_EXAM_RESULT)
            .exported(UPDATED_EXPORTED)
            .status(UPDATED_STATUS);
        return exam;
    }

    @BeforeEach
    public void initTest() {
        exam = createEntity(em);
    }

    @Test
    @Transactional
    void createExam() throws Exception {
        int databaseSizeBeforeCreate = examRepository.findAll().size();
        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);
        restExamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isCreated());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeCreate + 1);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getPassScore()).isEqualTo(DEFAULT_PASS_SCORE);
        assertThat(testExam.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testExam.getTimeInSec()).isEqualTo(DEFAULT_TIME_IN_SEC);
        assertThat(testExam.getValidfrom()).isEqualTo(DEFAULT_VALIDFROM);
        assertThat(testExam.getValidto()).isEqualTo(DEFAULT_VALIDTO);
        assertThat(testExam.getStartAt()).isEqualTo(DEFAULT_START_AT);
        assertThat(testExam.getSubmitAt()).isEqualTo(DEFAULT_SUBMIT_AT);
        assertThat(testExam.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testExam.getExaminerId()).isEqualTo(DEFAULT_EXAMINER_ID);
        assertThat(testExam.getExamCode()).isEqualTo(DEFAULT_EXAM_CODE);
        assertThat(testExam.getExamDate()).isEqualTo(DEFAULT_EXAM_DATE);
        assertThat(testExam.getExamResult()).isEqualTo(DEFAULT_EXAM_RESULT);
        assertThat(testExam.getExported()).isEqualTo(DEFAULT_EXPORTED);
        assertThat(testExam.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createExamWithExistingId() throws Exception {
        // Create the Exam with an existing ID
        exam.setId(1L);
        ExamDTO examDTO = examMapper.toDto(exam);

        int databaseSizeBeforeCreate = examRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValidfromIsRequired() throws Exception {
        int databaseSizeBeforeTest = examRepository.findAll().size();
        // set the field null
        exam.setValidfrom(null);

        // Create the Exam, which fails.
        ExamDTO examDTO = examMapper.toDto(exam);

        restExamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidtoIsRequired() throws Exception {
        int databaseSizeBeforeTest = examRepository.findAll().size();
        // set the field null
        exam.setValidto(null);

        // Create the Exam, which fails.
        ExamDTO examDTO = examMapper.toDto(exam);

        restExamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = examRepository.findAll().size();
        // set the field null
        exam.setStatus(null);

        // Create the Exam, which fails.
        ExamDTO examDTO = examMapper.toDto(exam);

        restExamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExams() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList
        restExamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exam.getId().intValue())))
            .andExpect(jsonPath("$.[*].passScore").value(hasItem(DEFAULT_PASS_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].validfrom").value(hasItem(DEFAULT_VALIDFROM.toString())))
            .andExpect(jsonPath("$.[*].validto").value(hasItem(DEFAULT_VALIDTO.toString())))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(DEFAULT_START_AT.toString())))
            .andExpect(jsonPath("$.[*].submitAt").value(hasItem(DEFAULT_SUBMIT_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].examinerId").value(hasItem(DEFAULT_EXAMINER_ID.intValue())))
            .andExpect(jsonPath("$.[*].examCode").value(hasItem(DEFAULT_EXAM_CODE.intValue())))
            .andExpect(jsonPath("$.[*].examDate").value(hasItem(DEFAULT_EXAM_DATE.toString())))
            .andExpect(jsonPath("$.[*].examResult").value(hasItem(DEFAULT_EXAM_RESULT.intValue())))
            .andExpect(jsonPath("$.[*].exported").value(hasItem(DEFAULT_EXPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get the exam
        restExamMockMvc
            .perform(get(ENTITY_API_URL_ID, exam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exam.getId().intValue()))
            .andExpect(jsonPath("$.passScore").value(DEFAULT_PASS_SCORE.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.timeInSec").value(DEFAULT_TIME_IN_SEC.intValue()))
            .andExpect(jsonPath("$.validfrom").value(DEFAULT_VALIDFROM.toString()))
            .andExpect(jsonPath("$.validto").value(DEFAULT_VALIDTO.toString()))
            .andExpect(jsonPath("$.startAt").value(DEFAULT_START_AT.toString()))
            .andExpect(jsonPath("$.submitAt").value(DEFAULT_SUBMIT_AT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.examinerId").value(DEFAULT_EXAMINER_ID.intValue()))
            .andExpect(jsonPath("$.examCode").value(DEFAULT_EXAM_CODE.intValue()))
            .andExpect(jsonPath("$.examDate").value(DEFAULT_EXAM_DATE.toString()))
            .andExpect(jsonPath("$.examResult").value(DEFAULT_EXAM_RESULT.intValue()))
            .andExpect(jsonPath("$.exported").value(DEFAULT_EXPORTED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getExamsByIdFiltering() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        Long id = exam.getId();

        defaultExamShouldBeFound("id.equals=" + id);
        defaultExamShouldNotBeFound("id.notEquals=" + id);

        defaultExamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExamShouldNotBeFound("id.greaterThan=" + id);

        defaultExamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExamShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore equals to DEFAULT_PASS_SCORE
        defaultExamShouldBeFound("passScore.equals=" + DEFAULT_PASS_SCORE);

        // Get all the examList where passScore equals to UPDATED_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.equals=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore in DEFAULT_PASS_SCORE or UPDATED_PASS_SCORE
        defaultExamShouldBeFound("passScore.in=" + DEFAULT_PASS_SCORE + "," + UPDATED_PASS_SCORE);

        // Get all the examList where passScore equals to UPDATED_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.in=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore is not null
        defaultExamShouldBeFound("passScore.specified=true");

        // Get all the examList where passScore is null
        defaultExamShouldNotBeFound("passScore.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore is greater than or equal to DEFAULT_PASS_SCORE
        defaultExamShouldBeFound("passScore.greaterThanOrEqual=" + DEFAULT_PASS_SCORE);

        // Get all the examList where passScore is greater than or equal to UPDATED_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.greaterThanOrEqual=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore is less than or equal to DEFAULT_PASS_SCORE
        defaultExamShouldBeFound("passScore.lessThanOrEqual=" + DEFAULT_PASS_SCORE);

        // Get all the examList where passScore is less than or equal to SMALLER_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.lessThanOrEqual=" + SMALLER_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore is less than DEFAULT_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.lessThan=" + DEFAULT_PASS_SCORE);

        // Get all the examList where passScore is less than UPDATED_PASS_SCORE
        defaultExamShouldBeFound("passScore.lessThan=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByPassScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where passScore is greater than DEFAULT_PASS_SCORE
        defaultExamShouldNotBeFound("passScore.greaterThan=" + DEFAULT_PASS_SCORE);

        // Get all the examList where passScore is greater than SMALLER_PASS_SCORE
        defaultExamShouldBeFound("passScore.greaterThan=" + SMALLER_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score equals to DEFAULT_SCORE
        defaultExamShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the examList where score equals to UPDATED_SCORE
        defaultExamShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultExamShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the examList where score equals to UPDATED_SCORE
        defaultExamShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score is not null
        defaultExamShouldBeFound("score.specified=true");

        // Get all the examList where score is null
        defaultExamShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score is greater than or equal to DEFAULT_SCORE
        defaultExamShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the examList where score is greater than or equal to UPDATED_SCORE
        defaultExamShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score is less than or equal to DEFAULT_SCORE
        defaultExamShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the examList where score is less than or equal to SMALLER_SCORE
        defaultExamShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score is less than DEFAULT_SCORE
        defaultExamShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the examList where score is less than UPDATED_SCORE
        defaultExamShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where score is greater than DEFAULT_SCORE
        defaultExamShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the examList where score is greater than SMALLER_SCORE
        defaultExamShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec equals to DEFAULT_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.equals=" + DEFAULT_TIME_IN_SEC);

        // Get all the examList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.equals=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec in DEFAULT_TIME_IN_SEC or UPDATED_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.in=" + DEFAULT_TIME_IN_SEC + "," + UPDATED_TIME_IN_SEC);

        // Get all the examList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.in=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec is not null
        defaultExamShouldBeFound("timeInSec.specified=true");

        // Get all the examList where timeInSec is null
        defaultExamShouldNotBeFound("timeInSec.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec is greater than or equal to DEFAULT_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.greaterThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the examList where timeInSec is greater than or equal to UPDATED_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.greaterThanOrEqual=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec is less than or equal to DEFAULT_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.lessThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the examList where timeInSec is less than or equal to SMALLER_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.lessThanOrEqual=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec is less than DEFAULT_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.lessThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the examList where timeInSec is less than UPDATED_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.lessThan=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByTimeInSecIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where timeInSec is greater than DEFAULT_TIME_IN_SEC
        defaultExamShouldNotBeFound("timeInSec.greaterThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the examList where timeInSec is greater than SMALLER_TIME_IN_SEC
        defaultExamShouldBeFound("timeInSec.greaterThan=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllExamsByValidfromIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validfrom equals to DEFAULT_VALIDFROM
        defaultExamShouldBeFound("validfrom.equals=" + DEFAULT_VALIDFROM);

        // Get all the examList where validfrom equals to UPDATED_VALIDFROM
        defaultExamShouldNotBeFound("validfrom.equals=" + UPDATED_VALIDFROM);
    }

    @Test
    @Transactional
    void getAllExamsByValidfromIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validfrom in DEFAULT_VALIDFROM or UPDATED_VALIDFROM
        defaultExamShouldBeFound("validfrom.in=" + DEFAULT_VALIDFROM + "," + UPDATED_VALIDFROM);

        // Get all the examList where validfrom equals to UPDATED_VALIDFROM
        defaultExamShouldNotBeFound("validfrom.in=" + UPDATED_VALIDFROM);
    }

    @Test
    @Transactional
    void getAllExamsByValidfromIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validfrom is not null
        defaultExamShouldBeFound("validfrom.specified=true");

        // Get all the examList where validfrom is null
        defaultExamShouldNotBeFound("validfrom.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByValidtoIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validto equals to DEFAULT_VALIDTO
        defaultExamShouldBeFound("validto.equals=" + DEFAULT_VALIDTO);

        // Get all the examList where validto equals to UPDATED_VALIDTO
        defaultExamShouldNotBeFound("validto.equals=" + UPDATED_VALIDTO);
    }

    @Test
    @Transactional
    void getAllExamsByValidtoIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validto in DEFAULT_VALIDTO or UPDATED_VALIDTO
        defaultExamShouldBeFound("validto.in=" + DEFAULT_VALIDTO + "," + UPDATED_VALIDTO);

        // Get all the examList where validto equals to UPDATED_VALIDTO
        defaultExamShouldNotBeFound("validto.in=" + UPDATED_VALIDTO);
    }

    @Test
    @Transactional
    void getAllExamsByValidtoIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where validto is not null
        defaultExamShouldBeFound("validto.specified=true");

        // Get all the examList where validto is null
        defaultExamShouldNotBeFound("validto.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByStartAtIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where startAt equals to DEFAULT_START_AT
        defaultExamShouldBeFound("startAt.equals=" + DEFAULT_START_AT);

        // Get all the examList where startAt equals to UPDATED_START_AT
        defaultExamShouldNotBeFound("startAt.equals=" + UPDATED_START_AT);
    }

    @Test
    @Transactional
    void getAllExamsByStartAtIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where startAt in DEFAULT_START_AT or UPDATED_START_AT
        defaultExamShouldBeFound("startAt.in=" + DEFAULT_START_AT + "," + UPDATED_START_AT);

        // Get all the examList where startAt equals to UPDATED_START_AT
        defaultExamShouldNotBeFound("startAt.in=" + UPDATED_START_AT);
    }

    @Test
    @Transactional
    void getAllExamsByStartAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where startAt is not null
        defaultExamShouldBeFound("startAt.specified=true");

        // Get all the examList where startAt is null
        defaultExamShouldNotBeFound("startAt.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsBySubmitAtIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where submitAt equals to DEFAULT_SUBMIT_AT
        defaultExamShouldBeFound("submitAt.equals=" + DEFAULT_SUBMIT_AT);

        // Get all the examList where submitAt equals to UPDATED_SUBMIT_AT
        defaultExamShouldNotBeFound("submitAt.equals=" + UPDATED_SUBMIT_AT);
    }

    @Test
    @Transactional
    void getAllExamsBySubmitAtIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where submitAt in DEFAULT_SUBMIT_AT or UPDATED_SUBMIT_AT
        defaultExamShouldBeFound("submitAt.in=" + DEFAULT_SUBMIT_AT + "," + UPDATED_SUBMIT_AT);

        // Get all the examList where submitAt equals to UPDATED_SUBMIT_AT
        defaultExamShouldNotBeFound("submitAt.in=" + UPDATED_SUBMIT_AT);
    }

    @Test
    @Transactional
    void getAllExamsBySubmitAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where submitAt is not null
        defaultExamShouldBeFound("submitAt.specified=true");

        // Get all the examList where submitAt is null
        defaultExamShouldNotBeFound("submitAt.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId equals to DEFAULT_USER_ID
        defaultExamShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the examList where userId equals to UPDATED_USER_ID
        defaultExamShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultExamShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the examList where userId equals to UPDATED_USER_ID
        defaultExamShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId is not null
        defaultExamShouldBeFound("userId.specified=true");

        // Get all the examList where userId is null
        defaultExamShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId is greater than or equal to DEFAULT_USER_ID
        defaultExamShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the examList where userId is greater than or equal to UPDATED_USER_ID
        defaultExamShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId is less than or equal to DEFAULT_USER_ID
        defaultExamShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the examList where userId is less than or equal to SMALLER_USER_ID
        defaultExamShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId is less than DEFAULT_USER_ID
        defaultExamShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the examList where userId is less than UPDATED_USER_ID
        defaultExamShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where userId is greater than DEFAULT_USER_ID
        defaultExamShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the examList where userId is greater than SMALLER_USER_ID
        defaultExamShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId equals to DEFAULT_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.equals=" + DEFAULT_EXAMINER_ID);

        // Get all the examList where examinerId equals to UPDATED_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.equals=" + UPDATED_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId in DEFAULT_EXAMINER_ID or UPDATED_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.in=" + DEFAULT_EXAMINER_ID + "," + UPDATED_EXAMINER_ID);

        // Get all the examList where examinerId equals to UPDATED_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.in=" + UPDATED_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId is not null
        defaultExamShouldBeFound("examinerId.specified=true");

        // Get all the examList where examinerId is null
        defaultExamShouldNotBeFound("examinerId.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId is greater than or equal to DEFAULT_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.greaterThanOrEqual=" + DEFAULT_EXAMINER_ID);

        // Get all the examList where examinerId is greater than or equal to UPDATED_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.greaterThanOrEqual=" + UPDATED_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId is less than or equal to DEFAULT_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.lessThanOrEqual=" + DEFAULT_EXAMINER_ID);

        // Get all the examList where examinerId is less than or equal to SMALLER_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.lessThanOrEqual=" + SMALLER_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId is less than DEFAULT_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.lessThan=" + DEFAULT_EXAMINER_ID);

        // Get all the examList where examinerId is less than UPDATED_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.lessThan=" + UPDATED_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExaminerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examinerId is greater than DEFAULT_EXAMINER_ID
        defaultExamShouldNotBeFound("examinerId.greaterThan=" + DEFAULT_EXAMINER_ID);

        // Get all the examList where examinerId is greater than SMALLER_EXAMINER_ID
        defaultExamShouldBeFound("examinerId.greaterThan=" + SMALLER_EXAMINER_ID);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode equals to DEFAULT_EXAM_CODE
        defaultExamShouldBeFound("examCode.equals=" + DEFAULT_EXAM_CODE);

        // Get all the examList where examCode equals to UPDATED_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.equals=" + UPDATED_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode in DEFAULT_EXAM_CODE or UPDATED_EXAM_CODE
        defaultExamShouldBeFound("examCode.in=" + DEFAULT_EXAM_CODE + "," + UPDATED_EXAM_CODE);

        // Get all the examList where examCode equals to UPDATED_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.in=" + UPDATED_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode is not null
        defaultExamShouldBeFound("examCode.specified=true");

        // Get all the examList where examCode is null
        defaultExamShouldNotBeFound("examCode.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode is greater than or equal to DEFAULT_EXAM_CODE
        defaultExamShouldBeFound("examCode.greaterThanOrEqual=" + DEFAULT_EXAM_CODE);

        // Get all the examList where examCode is greater than or equal to UPDATED_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.greaterThanOrEqual=" + UPDATED_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode is less than or equal to DEFAULT_EXAM_CODE
        defaultExamShouldBeFound("examCode.lessThanOrEqual=" + DEFAULT_EXAM_CODE);

        // Get all the examList where examCode is less than or equal to SMALLER_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.lessThanOrEqual=" + SMALLER_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode is less than DEFAULT_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.lessThan=" + DEFAULT_EXAM_CODE);

        // Get all the examList where examCode is less than UPDATED_EXAM_CODE
        defaultExamShouldBeFound("examCode.lessThan=" + UPDATED_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examCode is greater than DEFAULT_EXAM_CODE
        defaultExamShouldNotBeFound("examCode.greaterThan=" + DEFAULT_EXAM_CODE);

        // Get all the examList where examCode is greater than SMALLER_EXAM_CODE
        defaultExamShouldBeFound("examCode.greaterThan=" + SMALLER_EXAM_CODE);
    }

    @Test
    @Transactional
    void getAllExamsByExamDateIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examDate equals to DEFAULT_EXAM_DATE
        defaultExamShouldBeFound("examDate.equals=" + DEFAULT_EXAM_DATE);

        // Get all the examList where examDate equals to UPDATED_EXAM_DATE
        defaultExamShouldNotBeFound("examDate.equals=" + UPDATED_EXAM_DATE);
    }

    @Test
    @Transactional
    void getAllExamsByExamDateIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examDate in DEFAULT_EXAM_DATE or UPDATED_EXAM_DATE
        defaultExamShouldBeFound("examDate.in=" + DEFAULT_EXAM_DATE + "," + UPDATED_EXAM_DATE);

        // Get all the examList where examDate equals to UPDATED_EXAM_DATE
        defaultExamShouldNotBeFound("examDate.in=" + UPDATED_EXAM_DATE);
    }

    @Test
    @Transactional
    void getAllExamsByExamDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examDate is not null
        defaultExamShouldBeFound("examDate.specified=true");

        // Get all the examList where examDate is null
        defaultExamShouldNotBeFound("examDate.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult equals to DEFAULT_EXAM_RESULT
        defaultExamShouldBeFound("examResult.equals=" + DEFAULT_EXAM_RESULT);

        // Get all the examList where examResult equals to UPDATED_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.equals=" + UPDATED_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult in DEFAULT_EXAM_RESULT or UPDATED_EXAM_RESULT
        defaultExamShouldBeFound("examResult.in=" + DEFAULT_EXAM_RESULT + "," + UPDATED_EXAM_RESULT);

        // Get all the examList where examResult equals to UPDATED_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.in=" + UPDATED_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult is not null
        defaultExamShouldBeFound("examResult.specified=true");

        // Get all the examList where examResult is null
        defaultExamShouldNotBeFound("examResult.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult is greater than or equal to DEFAULT_EXAM_RESULT
        defaultExamShouldBeFound("examResult.greaterThanOrEqual=" + DEFAULT_EXAM_RESULT);

        // Get all the examList where examResult is greater than or equal to UPDATED_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.greaterThanOrEqual=" + UPDATED_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult is less than or equal to DEFAULT_EXAM_RESULT
        defaultExamShouldBeFound("examResult.lessThanOrEqual=" + DEFAULT_EXAM_RESULT);

        // Get all the examList where examResult is less than or equal to SMALLER_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.lessThanOrEqual=" + SMALLER_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult is less than DEFAULT_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.lessThan=" + DEFAULT_EXAM_RESULT);

        // Get all the examList where examResult is less than UPDATED_EXAM_RESULT
        defaultExamShouldBeFound("examResult.lessThan=" + UPDATED_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExamResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where examResult is greater than DEFAULT_EXAM_RESULT
        defaultExamShouldNotBeFound("examResult.greaterThan=" + DEFAULT_EXAM_RESULT);

        // Get all the examList where examResult is greater than SMALLER_EXAM_RESULT
        defaultExamShouldBeFound("examResult.greaterThan=" + SMALLER_EXAM_RESULT);
    }

    @Test
    @Transactional
    void getAllExamsByExportedIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where exported equals to DEFAULT_EXPORTED
        defaultExamShouldBeFound("exported.equals=" + DEFAULT_EXPORTED);

        // Get all the examList where exported equals to UPDATED_EXPORTED
        defaultExamShouldNotBeFound("exported.equals=" + UPDATED_EXPORTED);
    }

    @Test
    @Transactional
    void getAllExamsByExportedIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where exported in DEFAULT_EXPORTED or UPDATED_EXPORTED
        defaultExamShouldBeFound("exported.in=" + DEFAULT_EXPORTED + "," + UPDATED_EXPORTED);

        // Get all the examList where exported equals to UPDATED_EXPORTED
        defaultExamShouldNotBeFound("exported.in=" + UPDATED_EXPORTED);
    }

    @Test
    @Transactional
    void getAllExamsByExportedIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where exported is not null
        defaultExamShouldBeFound("exported.specified=true");

        // Get all the examList where exported is null
        defaultExamShouldNotBeFound("exported.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status equals to DEFAULT_STATUS
        defaultExamShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the examList where status equals to UPDATED_STATUS
        defaultExamShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultExamShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the examList where status equals to UPDATED_STATUS
        defaultExamShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status is not null
        defaultExamShouldBeFound("status.specified=true");

        // Get all the examList where status is null
        defaultExamShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status is greater than or equal to DEFAULT_STATUS
        defaultExamShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the examList where status is greater than or equal to UPDATED_STATUS
        defaultExamShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status is less than or equal to DEFAULT_STATUS
        defaultExamShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the examList where status is less than or equal to SMALLER_STATUS
        defaultExamShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status is less than DEFAULT_STATUS
        defaultExamShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the examList where status is less than UPDATED_STATUS
        defaultExamShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList where status is greater than DEFAULT_STATUS
        defaultExamShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the examList where status is greater than SMALLER_STATUS
        defaultExamShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllExamsByExamQuestionsIsEqualToSomething() throws Exception {
        ExamQuestions examQuestions;
        if (TestUtil.findAll(em, ExamQuestions.class).isEmpty()) {
            examRepository.saveAndFlush(exam);
            examQuestions = ExamQuestionsResourceIT.createEntity(em);
        } else {
            examQuestions = TestUtil.findAll(em, ExamQuestions.class).get(0);
        }
        em.persist(examQuestions);
        em.flush();
        exam.addExamQuestions(examQuestions);
        examRepository.saveAndFlush(exam);
        Long examQuestionsId = examQuestions.getId();

        // Get all the examList where examQuestions equals to examQuestionsId
        defaultExamShouldBeFound("examQuestionsId.equals=" + examQuestionsId);

        // Get all the examList where examQuestions equals to (examQuestionsId + 1)
        defaultExamShouldNotBeFound("examQuestionsId.equals=" + (examQuestionsId + 1));
    }

    @Test
    @Transactional
    void getAllExamsByTemplateIsEqualToSomething() throws Exception {
        Template template;
        if (TestUtil.findAll(em, Template.class).isEmpty()) {
            examRepository.saveAndFlush(exam);
            template = TemplateResourceIT.createEntity(em);
        } else {
            template = TestUtil.findAll(em, Template.class).get(0);
        }
        em.persist(template);
        em.flush();
        exam.setTemplate(template);
        examRepository.saveAndFlush(exam);
        Long templateId = template.getId();

        // Get all the examList where template equals to templateId
        defaultExamShouldBeFound("templateId.equals=" + templateId);

        // Get all the examList where template equals to (templateId + 1)
        defaultExamShouldNotBeFound("templateId.equals=" + (templateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExamShouldBeFound(String filter) throws Exception {
        restExamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exam.getId().intValue())))
            .andExpect(jsonPath("$.[*].passScore").value(hasItem(DEFAULT_PASS_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].validfrom").value(hasItem(DEFAULT_VALIDFROM.toString())))
            .andExpect(jsonPath("$.[*].validto").value(hasItem(DEFAULT_VALIDTO.toString())))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(DEFAULT_START_AT.toString())))
            .andExpect(jsonPath("$.[*].submitAt").value(hasItem(DEFAULT_SUBMIT_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].examinerId").value(hasItem(DEFAULT_EXAMINER_ID.intValue())))
            .andExpect(jsonPath("$.[*].examCode").value(hasItem(DEFAULT_EXAM_CODE.intValue())))
            .andExpect(jsonPath("$.[*].examDate").value(hasItem(DEFAULT_EXAM_DATE.toString())))
            .andExpect(jsonPath("$.[*].examResult").value(hasItem(DEFAULT_EXAM_RESULT.intValue())))
            .andExpect(jsonPath("$.[*].exported").value(hasItem(DEFAULT_EXPORTED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restExamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExamShouldNotBeFound(String filter) throws Exception {
        restExamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExam() throws Exception {
        // Get the exam
        restExamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Update the exam
        Exam updatedExam = examRepository.findById(exam.getId()).get();
        // Disconnect from session so that the updates on updatedExam are not directly saved in db
        em.detach(updatedExam);
        updatedExam
            .passScore(UPDATED_PASS_SCORE)
            .score(UPDATED_SCORE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .validfrom(UPDATED_VALIDFROM)
            .validto(UPDATED_VALIDTO)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .userId(UPDATED_USER_ID)
            .examinerId(UPDATED_EXAMINER_ID)
            .examCode(UPDATED_EXAM_CODE)
            .examDate(UPDATED_EXAM_DATE)
            .examResult(UPDATED_EXAM_RESULT)
            .exported(UPDATED_EXPORTED)
            .status(UPDATED_STATUS);
        ExamDTO examDTO = examMapper.toDto(updatedExam);

        restExamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examDTO))
            )
            .andExpect(status().isOk());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getPassScore()).isEqualTo(UPDATED_PASS_SCORE);
        assertThat(testExam.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExam.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExam.getValidfrom()).isEqualTo(UPDATED_VALIDFROM);
        assertThat(testExam.getValidto()).isEqualTo(UPDATED_VALIDTO);
        assertThat(testExam.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExam.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExam.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testExam.getExaminerId()).isEqualTo(UPDATED_EXAMINER_ID);
        assertThat(testExam.getExamCode()).isEqualTo(UPDATED_EXAM_CODE);
        assertThat(testExam.getExamDate()).isEqualTo(UPDATED_EXAM_DATE);
        assertThat(testExam.getExamResult()).isEqualTo(UPDATED_EXAM_RESULT);
        assertThat(testExam.getExported()).isEqualTo(UPDATED_EXPORTED);
        assertThat(testExam.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamWithPatch() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Update the exam using partial update
        Exam partialUpdatedExam = new Exam();
        partialUpdatedExam.setId(exam.getId());

        partialUpdatedExam
            .passScore(UPDATED_PASS_SCORE)
            .score(UPDATED_SCORE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .validfrom(UPDATED_VALIDFROM)
            .validto(UPDATED_VALIDTO)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .userId(UPDATED_USER_ID)
            .examCode(UPDATED_EXAM_CODE)
            .exported(UPDATED_EXPORTED)
            .status(UPDATED_STATUS);

        restExamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExam))
            )
            .andExpect(status().isOk());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getPassScore()).isEqualTo(UPDATED_PASS_SCORE);
        assertThat(testExam.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExam.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExam.getValidfrom()).isEqualTo(UPDATED_VALIDFROM);
        assertThat(testExam.getValidto()).isEqualTo(UPDATED_VALIDTO);
        assertThat(testExam.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExam.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExam.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testExam.getExaminerId()).isEqualTo(DEFAULT_EXAMINER_ID);
        assertThat(testExam.getExamCode()).isEqualTo(UPDATED_EXAM_CODE);
        assertThat(testExam.getExamDate()).isEqualTo(DEFAULT_EXAM_DATE);
        assertThat(testExam.getExamResult()).isEqualTo(DEFAULT_EXAM_RESULT);
        assertThat(testExam.getExported()).isEqualTo(UPDATED_EXPORTED);
        assertThat(testExam.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateExamWithPatch() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Update the exam using partial update
        Exam partialUpdatedExam = new Exam();
        partialUpdatedExam.setId(exam.getId());

        partialUpdatedExam
            .passScore(UPDATED_PASS_SCORE)
            .score(UPDATED_SCORE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .validfrom(UPDATED_VALIDFROM)
            .validto(UPDATED_VALIDTO)
            .startAt(UPDATED_START_AT)
            .submitAt(UPDATED_SUBMIT_AT)
            .userId(UPDATED_USER_ID)
            .examinerId(UPDATED_EXAMINER_ID)
            .examCode(UPDATED_EXAM_CODE)
            .examDate(UPDATED_EXAM_DATE)
            .examResult(UPDATED_EXAM_RESULT)
            .exported(UPDATED_EXPORTED)
            .status(UPDATED_STATUS);

        restExamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExam))
            )
            .andExpect(status().isOk());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getPassScore()).isEqualTo(UPDATED_PASS_SCORE);
        assertThat(testExam.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExam.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testExam.getValidfrom()).isEqualTo(UPDATED_VALIDFROM);
        assertThat(testExam.getValidto()).isEqualTo(UPDATED_VALIDTO);
        assertThat(testExam.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testExam.getSubmitAt()).isEqualTo(UPDATED_SUBMIT_AT);
        assertThat(testExam.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testExam.getExaminerId()).isEqualTo(UPDATED_EXAMINER_ID);
        assertThat(testExam.getExamCode()).isEqualTo(UPDATED_EXAM_CODE);
        assertThat(testExam.getExamDate()).isEqualTo(UPDATED_EXAM_DATE);
        assertThat(testExam.getExamResult()).isEqualTo(UPDATED_EXAM_RESULT);
        assertThat(testExam.getExported()).isEqualTo(UPDATED_EXPORTED);
        assertThat(testExam.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();
        exam.setId(count.incrementAndGet());

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeDelete = examRepository.findAll().size();

        // Delete the exam
        restExamMockMvc
            .perform(delete(ENTITY_API_URL_ID, exam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
