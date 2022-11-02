package com.isoft.svisrevamp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.svisrevamp.IntegrationTest;
import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.domain.TemplateFacilitators;
import com.isoft.svisrevamp.repository.TemplateRepository;
import com.isoft.svisrevamp.service.criteria.TemplateCriteria;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import com.isoft.svisrevamp.service.mapper.TemplateMapper;
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
 * Integration tests for the {@link TemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateResourceIT {

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME_IN_SEC = 1L;
    private static final Long UPDATED_TIME_IN_SEC = 2L;
    private static final Long SMALLER_TIME_IN_SEC = 1L - 1L;

    private static final Double DEFAULT_PASS_SCORE = 1D;
    private static final Double UPDATED_PASS_SCORE = 2D;
    private static final Double SMALLER_PASS_SCORE = 1D - 1D;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateMockMvc;

    private Template template;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .timeInSec(DEFAULT_TIME_IN_SEC)
            .passScore(DEFAULT_PASS_SCORE)
            .status(DEFAULT_STATUS);
        return template;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createUpdatedEntity(EntityManager em) {
        Template template = new Template()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .passScore(UPDATED_PASS_SCORE)
            .status(UPDATED_STATUS);
        return template;
    }

    @BeforeEach
    public void initTest() {
        template = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();
        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);
        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testTemplate.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testTemplate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTemplate.getTimeInSec()).isEqualTo(DEFAULT_TIME_IN_SEC);
        assertThat(testTemplate.getPassScore()).isEqualTo(DEFAULT_PASS_SCORE);
        assertThat(testTemplate.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createTemplateWithExistingId() throws Exception {
        // Create the Template with an existing ID
        template.setId(1L);
        TemplateDTO templateDTO = templateMapper.toDto(template);

        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setNameAr(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setNameEn(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setCode(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].passScore").value(hasItem(DEFAULT_PASS_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.timeInSec").value(DEFAULT_TIME_IN_SEC.intValue()))
            .andExpect(jsonPath("$.passScore").value(DEFAULT_PASS_SCORE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        Long id = template.getId();

        defaultTemplateShouldBeFound("id.equals=" + id);
        defaultTemplateShouldNotBeFound("id.notEquals=" + id);

        defaultTemplateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplateShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameAr equals to DEFAULT_NAME_AR
        defaultTemplateShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the templateList where nameAr equals to UPDATED_NAME_AR
        defaultTemplateShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultTemplateShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the templateList where nameAr equals to UPDATED_NAME_AR
        defaultTemplateShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameAr is not null
        defaultTemplateShouldBeFound("nameAr.specified=true");

        // Get all the templateList where nameAr is null
        defaultTemplateShouldNotBeFound("nameAr.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByNameArContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameAr contains DEFAULT_NAME_AR
        defaultTemplateShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the templateList where nameAr contains UPDATED_NAME_AR
        defaultTemplateShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameAr does not contain DEFAULT_NAME_AR
        defaultTemplateShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the templateList where nameAr does not contain UPDATED_NAME_AR
        defaultTemplateShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameEn equals to DEFAULT_NAME_EN
        defaultTemplateShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the templateList where nameEn equals to UPDATED_NAME_EN
        defaultTemplateShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultTemplateShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the templateList where nameEn equals to UPDATED_NAME_EN
        defaultTemplateShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameEn is not null
        defaultTemplateShouldBeFound("nameEn.specified=true");

        // Get all the templateList where nameEn is null
        defaultTemplateShouldNotBeFound("nameEn.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameEn contains DEFAULT_NAME_EN
        defaultTemplateShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the templateList where nameEn contains UPDATED_NAME_EN
        defaultTemplateShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where nameEn does not contain DEFAULT_NAME_EN
        defaultTemplateShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the templateList where nameEn does not contain UPDATED_NAME_EN
        defaultTemplateShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void getAllTemplatesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where code equals to DEFAULT_CODE
        defaultTemplateShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the templateList where code equals to UPDATED_CODE
        defaultTemplateShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTemplateShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the templateList where code equals to UPDATED_CODE
        defaultTemplateShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where code is not null
        defaultTemplateShouldBeFound("code.specified=true");

        // Get all the templateList where code is null
        defaultTemplateShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByCodeContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where code contains DEFAULT_CODE
        defaultTemplateShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the templateList where code contains UPDATED_CODE
        defaultTemplateShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where code does not contain DEFAULT_CODE
        defaultTemplateShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the templateList where code does not contain UPDATED_CODE
        defaultTemplateShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec equals to DEFAULT_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.equals=" + DEFAULT_TIME_IN_SEC);

        // Get all the templateList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.equals=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec in DEFAULT_TIME_IN_SEC or UPDATED_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.in=" + DEFAULT_TIME_IN_SEC + "," + UPDATED_TIME_IN_SEC);

        // Get all the templateList where timeInSec equals to UPDATED_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.in=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec is not null
        defaultTemplateShouldBeFound("timeInSec.specified=true");

        // Get all the templateList where timeInSec is null
        defaultTemplateShouldNotBeFound("timeInSec.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec is greater than or equal to DEFAULT_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.greaterThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the templateList where timeInSec is greater than or equal to UPDATED_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.greaterThanOrEqual=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec is less than or equal to DEFAULT_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.lessThanOrEqual=" + DEFAULT_TIME_IN_SEC);

        // Get all the templateList where timeInSec is less than or equal to SMALLER_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.lessThanOrEqual=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsLessThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec is less than DEFAULT_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.lessThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the templateList where timeInSec is less than UPDATED_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.lessThan=" + UPDATED_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByTimeInSecIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where timeInSec is greater than DEFAULT_TIME_IN_SEC
        defaultTemplateShouldNotBeFound("timeInSec.greaterThan=" + DEFAULT_TIME_IN_SEC);

        // Get all the templateList where timeInSec is greater than SMALLER_TIME_IN_SEC
        defaultTemplateShouldBeFound("timeInSec.greaterThan=" + SMALLER_TIME_IN_SEC);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore equals to DEFAULT_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.equals=" + DEFAULT_PASS_SCORE);

        // Get all the templateList where passScore equals to UPDATED_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.equals=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore in DEFAULT_PASS_SCORE or UPDATED_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.in=" + DEFAULT_PASS_SCORE + "," + UPDATED_PASS_SCORE);

        // Get all the templateList where passScore equals to UPDATED_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.in=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore is not null
        defaultTemplateShouldBeFound("passScore.specified=true");

        // Get all the templateList where passScore is null
        defaultTemplateShouldNotBeFound("passScore.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore is greater than or equal to DEFAULT_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.greaterThanOrEqual=" + DEFAULT_PASS_SCORE);

        // Get all the templateList where passScore is greater than or equal to UPDATED_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.greaterThanOrEqual=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore is less than or equal to DEFAULT_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.lessThanOrEqual=" + DEFAULT_PASS_SCORE);

        // Get all the templateList where passScore is less than or equal to SMALLER_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.lessThanOrEqual=" + SMALLER_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore is less than DEFAULT_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.lessThan=" + DEFAULT_PASS_SCORE);

        // Get all the templateList where passScore is less than UPDATED_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.lessThan=" + UPDATED_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByPassScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where passScore is greater than DEFAULT_PASS_SCORE
        defaultTemplateShouldNotBeFound("passScore.greaterThan=" + DEFAULT_PASS_SCORE);

        // Get all the templateList where passScore is greater than SMALLER_PASS_SCORE
        defaultTemplateShouldBeFound("passScore.greaterThan=" + SMALLER_PASS_SCORE);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status equals to DEFAULT_STATUS
        defaultTemplateShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the templateList where status equals to UPDATED_STATUS
        defaultTemplateShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTemplateShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the templateList where status equals to UPDATED_STATUS
        defaultTemplateShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status is not null
        defaultTemplateShouldBeFound("status.specified=true");

        // Get all the templateList where status is null
        defaultTemplateShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status is greater than or equal to DEFAULT_STATUS
        defaultTemplateShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the templateList where status is greater than or equal to UPDATED_STATUS
        defaultTemplateShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status is less than or equal to DEFAULT_STATUS
        defaultTemplateShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the templateList where status is less than or equal to SMALLER_STATUS
        defaultTemplateShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status is less than DEFAULT_STATUS
        defaultTemplateShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the templateList where status is less than UPDATED_STATUS
        defaultTemplateShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where status is greater than DEFAULT_STATUS
        defaultTemplateShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the templateList where status is greater than SMALLER_STATUS
        defaultTemplateShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTemplatesByTemplateCategoriesIsEqualToSomething() throws Exception {
        TemplateCategories templateCategories;
        if (TestUtil.findAll(em, TemplateCategories.class).isEmpty()) {
            templateRepository.saveAndFlush(template);
            templateCategories = TemplateCategoriesResourceIT.createEntity(em);
        } else {
            templateCategories = TestUtil.findAll(em, TemplateCategories.class).get(0);
        }
        em.persist(templateCategories);
        em.flush();
        template.addTemplateCategories(templateCategories);
        templateRepository.saveAndFlush(template);
        Long templateCategoriesId = templateCategories.getId();

        // Get all the templateList where templateCategories equals to templateCategoriesId
        defaultTemplateShouldBeFound("templateCategoriesId.equals=" + templateCategoriesId);

        // Get all the templateList where templateCategories equals to (templateCategoriesId + 1)
        defaultTemplateShouldNotBeFound("templateCategoriesId.equals=" + (templateCategoriesId + 1));
    }

    @Test
    @Transactional
    void getAllTemplatesByTemplateFacilitatorsIsEqualToSomething() throws Exception {
        TemplateFacilitators templateFacilitators;
        if (TestUtil.findAll(em, TemplateFacilitators.class).isEmpty()) {
            templateRepository.saveAndFlush(template);
            templateFacilitators = TemplateFacilitatorsResourceIT.createEntity(em);
        } else {
            templateFacilitators = TestUtil.findAll(em, TemplateFacilitators.class).get(0);
        }
        em.persist(templateFacilitators);
        em.flush();
        template.addTemplateFacilitators(templateFacilitators);
        templateRepository.saveAndFlush(template);
        Long templateFacilitatorsId = templateFacilitators.getId();

        // Get all the templateList where templateFacilitators equals to templateFacilitatorsId
        defaultTemplateShouldBeFound("templateFacilitatorsId.equals=" + templateFacilitatorsId);

        // Get all the templateList where templateFacilitators equals to (templateFacilitatorsId + 1)
        defaultTemplateShouldNotBeFound("templateFacilitatorsId.equals=" + (templateFacilitatorsId + 1));
    }

    @Test
    @Transactional
    void getAllTemplatesByExamIsEqualToSomething() throws Exception {
        Exam exam;
        if (TestUtil.findAll(em, Exam.class).isEmpty()) {
            templateRepository.saveAndFlush(template);
            exam = ExamResourceIT.createEntity(em);
        } else {
            exam = TestUtil.findAll(em, Exam.class).get(0);
        }
        em.persist(exam);
        em.flush();
        template.addExam(exam);
        templateRepository.saveAndFlush(template);
        Long examId = exam.getId();

        // Get all the templateList where exam equals to examId
        defaultTemplateShouldBeFound("examId.equals=" + examId);

        // Get all the templateList where exam equals to (examId + 1)
        defaultTemplateShouldNotBeFound("examId.equals=" + (examId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplateShouldBeFound(String filter) throws Exception {
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].timeInSec").value(hasItem(DEFAULT_TIME_IN_SEC.intValue())))
            .andExpect(jsonPath("$.[*].passScore").value(hasItem(DEFAULT_PASS_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplateShouldNotBeFound(String filter) throws Exception {
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findById(template.getId()).get();
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .passScore(UPDATED_PASS_SCORE)
            .status(UPDATED_STATUS);
        TemplateDTO templateDTO = templateMapper.toDto(updatedTemplate);

        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testTemplate.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplate.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testTemplate.getPassScore()).isEqualTo(UPDATED_PASS_SCORE);
        assertThat(testTemplate.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateWithPatch() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template using partial update
        Template partialUpdatedTemplate = new Template();
        partialUpdatedTemplate.setId(template.getId());

        partialUpdatedTemplate.nameEn(UPDATED_NAME_EN).code(UPDATED_CODE).timeInSec(UPDATED_TIME_IN_SEC).status(UPDATED_STATUS);

        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplate))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testTemplate.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplate.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testTemplate.getPassScore()).isEqualTo(DEFAULT_PASS_SCORE);
        assertThat(testTemplate.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTemplateWithPatch() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template using partial update
        Template partialUpdatedTemplate = new Template();
        partialUpdatedTemplate.setId(template.getId());

        partialUpdatedTemplate
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .timeInSec(UPDATED_TIME_IN_SEC)
            .passScore(UPDATED_PASS_SCORE)
            .status(UPDATED_STATUS);

        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplate))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testTemplate.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplate.getTimeInSec()).isEqualTo(UPDATED_TIME_IN_SEC);
        assertThat(testTemplate.getPassScore()).isEqualTo(UPDATED_PASS_SCORE);
        assertThat(testTemplate.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();
        template.setId(count.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(templateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Delete the template
        restTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, template.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
