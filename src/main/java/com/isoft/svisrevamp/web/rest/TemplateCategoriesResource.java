package com.isoft.svisrevamp.web.rest;

import com.isoft.svisrevamp.repository.TemplateCategoriesRepository;
import com.isoft.svisrevamp.service.TemplateCategoriesQueryService;
import com.isoft.svisrevamp.service.TemplateCategoriesService;
import com.isoft.svisrevamp.service.criteria.TemplateCategoriesCriteria;
import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
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
 * REST controller for managing {@link com.isoft.svisrevamp.domain.TemplateCategories}.
 */
@RestController
@RequestMapping("/api")
public class TemplateCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(TemplateCategoriesResource.class);

    private static final String ENTITY_NAME = "templateCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateCategoriesService templateCategoriesService;

    private final TemplateCategoriesRepository templateCategoriesRepository;

    private final TemplateCategoriesQueryService templateCategoriesQueryService;

    public TemplateCategoriesResource(
        TemplateCategoriesService templateCategoriesService,
        TemplateCategoriesRepository templateCategoriesRepository,
        TemplateCategoriesQueryService templateCategoriesQueryService
    ) {
        this.templateCategoriesService = templateCategoriesService;
        this.templateCategoriesRepository = templateCategoriesRepository;
        this.templateCategoriesQueryService = templateCategoriesQueryService;
    }

    /**
     * {@code POST  /template-categories} : Create a new templateCategories.
     *
     * @param templateCategoriesDTO the templateCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateCategoriesDTO, or with status {@code 400 (Bad Request)} if the templateCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-categories")
    public ResponseEntity<TemplateCategoriesDTO> createTemplateCategories(@Valid @RequestBody TemplateCategoriesDTO templateCategoriesDTO)
        throws URISyntaxException {
        log.debug("REST request to save TemplateCategories : {}", templateCategoriesDTO);
        if (templateCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateCategoriesDTO result = templateCategoriesService.save(templateCategoriesDTO);
        return ResponseEntity
            .created(new URI("/api/template-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-categories/:id} : Updates an existing templateCategories.
     *
     * @param id the id of the templateCategoriesDTO to save.
     * @param templateCategoriesDTO the templateCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the templateCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-categories/{id}")
    public ResponseEntity<TemplateCategoriesDTO> updateTemplateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemplateCategoriesDTO templateCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateCategories : {}, {}", id, templateCategoriesDTO);
        if (templateCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateCategoriesDTO result = templateCategoriesService.update(templateCategoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-categories/:id} : Partial updates given fields of an existing templateCategories, field will ignore if it is null
     *
     * @param id the id of the templateCategoriesDTO to save.
     * @param templateCategoriesDTO the templateCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the templateCategoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateCategoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateCategoriesDTO> partialUpdateTemplateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemplateCategoriesDTO templateCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateCategories partially : {}, {}", id, templateCategoriesDTO);
        if (templateCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateCategoriesDTO> result = templateCategoriesService.partialUpdate(templateCategoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateCategoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-categories} : get all the templateCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateCategories in body.
     */
    @GetMapping("/template-categories")
    public ResponseEntity<List<TemplateCategoriesDTO>> getAllTemplateCategories(
        TemplateCategoriesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TemplateCategories by criteria: {}", criteria);
        Page<TemplateCategoriesDTO> page = templateCategoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-categories/count} : count all the templateCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/template-categories/count")
    public ResponseEntity<Long> countTemplateCategories(TemplateCategoriesCriteria criteria) {
        log.debug("REST request to count TemplateCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(templateCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /template-categories/:id} : get the "id" templateCategories.
     *
     * @param id the id of the templateCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-categories/{id}")
    public ResponseEntity<TemplateCategoriesDTO> getTemplateCategories(@PathVariable Long id) {
        log.debug("REST request to get TemplateCategories : {}", id);
        Optional<TemplateCategoriesDTO> templateCategoriesDTO = templateCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateCategoriesDTO);
    }

    /**
     * {@code DELETE  /template-categories/:id} : delete the "id" templateCategories.
     *
     * @param id the id of the templateCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-categories/{id}")
    public ResponseEntity<Void> deleteTemplateCategories(@PathVariable Long id) {
        log.debug("REST request to delete TemplateCategories : {}", id);
        templateCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
