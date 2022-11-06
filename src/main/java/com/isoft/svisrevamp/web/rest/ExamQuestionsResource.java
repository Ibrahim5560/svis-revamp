package com.isoft.svisrevamp.web.rest;

import com.isoft.svisrevamp.repository.ExamQuestionsRepository;
import com.isoft.svisrevamp.service.ExamQuestionsQueryService;
import com.isoft.svisrevamp.service.ExamQuestionsService;
import com.isoft.svisrevamp.service.criteria.ExamQuestionsCriteria;
import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import com.isoft.svisrevamp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.isoft.svisrevamp.domain.ExamQuestions}.
 */
@RestController
@RequestMapping("/api")
public class ExamQuestionsResource {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionsResource.class);

    private static final String ENTITY_NAME = "examQuestions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamQuestionsService examQuestionsService;

    private final ExamQuestionsRepository examQuestionsRepository;

    private final ExamQuestionsQueryService examQuestionsQueryService;

    public ExamQuestionsResource(
        ExamQuestionsService examQuestionsService,
        ExamQuestionsRepository examQuestionsRepository,
        ExamQuestionsQueryService examQuestionsQueryService
    ) {
        this.examQuestionsService = examQuestionsService;
        this.examQuestionsRepository = examQuestionsRepository;
        this.examQuestionsQueryService = examQuestionsQueryService;
    }

    /**
     * {@code POST  /exam-questions} : Create a new examQuestions.
     *
     * @param examQuestionsDTO the examQuestionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examQuestionsDTO, or with status {@code 400 (Bad Request)} if the examQuestions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-questions")
    public ResponseEntity<ExamQuestionsDTO> createExamQuestions(@Valid @RequestBody ExamQuestionsDTO examQuestionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExamQuestions : {}", examQuestionsDTO);
        if (examQuestionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new examQuestions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamQuestionsDTO result = examQuestionsService.save(examQuestionsDTO);
        return ResponseEntity
            .created(new URI("/api/exam-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-questions/:id} : Updates an existing examQuestions.
     *
     * @param id the id of the examQuestionsDTO to save.
     * @param examQuestionsDTO the examQuestionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examQuestionsDTO,
     * or with status {@code 400 (Bad Request)} if the examQuestionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examQuestionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-questions/{id}")
    public ResponseEntity<ExamQuestionsDTO> updateExamQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExamQuestionsDTO examQuestionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExamQuestions : {}, {}", id, examQuestionsDTO);
        if (examQuestionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examQuestionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examQuestionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamQuestionsDTO result = examQuestionsService.update(examQuestionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examQuestionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exam-questions/:id} : Partial updates given fields of an existing examQuestions, field will ignore if it is null
     *
     * @param id the id of the examQuestionsDTO to save.
     * @param examQuestionsDTO the examQuestionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examQuestionsDTO,
     * or with status {@code 400 (Bad Request)} if the examQuestionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examQuestionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examQuestionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exam-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExamQuestionsDTO> partialUpdateExamQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExamQuestionsDTO examQuestionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExamQuestions partially : {}, {}", id, examQuestionsDTO);
        if (examQuestionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examQuestionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examQuestionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamQuestionsDTO> result = examQuestionsService.partialUpdate(examQuestionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examQuestionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /exam-questions} : get all the examQuestions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examQuestions in body.
     */
    @GetMapping("/exam-questions")
    public ResponseEntity<List<ExamQuestionsDTO>> getAllExamQuestions(
        ExamQuestionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ExamQuestions by criteria: {}", criteria);
        Page<ExamQuestionsDTO> page = examQuestionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exam-questions/count} : count all the examQuestions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/exam-questions/count")
    public ResponseEntity<Long> countExamQuestions(ExamQuestionsCriteria criteria) {
        log.debug("REST request to count ExamQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(examQuestionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /exam-questions/:id} : get the "id" examQuestions.
     *
     * @param id the id of the examQuestionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examQuestionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-questions/{id}")
    public ResponseEntity<ExamQuestionsDTO> getExamQuestions(@PathVariable Long id) {
        log.debug("REST request to get ExamQuestions : {}", id);
        Optional<ExamQuestionsDTO> examQuestionsDTO = examQuestionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examQuestionsDTO);
    }

    /**
     * {@code DELETE  /exam-questions/:id} : delete the "id" examQuestions.
     *
     * @param id the id of the examQuestionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-questions/{id}")
    public ResponseEntity<Void> deleteExamQuestions(@PathVariable Long id) {
        log.debug("REST request to delete ExamQuestions : {}", id);
        examQuestionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
