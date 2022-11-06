package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.svisrevamp.domain.TemplateCategories}.
 */
public interface TemplateCategoriesService {
    /**
     * Save a templateCategories.
     *
     * @param templateCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    TemplateCategoriesDTO save(TemplateCategoriesDTO templateCategoriesDTO);

    /**
     * Updates a templateCategories.
     *
     * @param templateCategoriesDTO the entity to update.
     * @return the persisted entity.
     */
    TemplateCategoriesDTO update(TemplateCategoriesDTO templateCategoriesDTO);

    /**
     * Partially updates a templateCategories.
     *
     * @param templateCategoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemplateCategoriesDTO> partialUpdate(TemplateCategoriesDTO templateCategoriesDTO);

    /**
     * Get all the templateCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateCategoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" templateCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemplateCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" templateCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
