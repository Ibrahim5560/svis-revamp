package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.service.dto.QuestionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.svisrevamp.domain.Questions}.
 */
public interface QuestionsService {
    /**
     * Save a questions.
     *
     * @param questionsDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionsDTO save(QuestionsDTO questionsDTO);

    /**
     * Updates a questions.
     *
     * @param questionsDTO the entity to update.
     * @return the persisted entity.
     */
    QuestionsDTO update(QuestionsDTO questionsDTO);

    /**
     * Partially updates a questions.
     *
     * @param questionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuestionsDTO> partialUpdate(QuestionsDTO questionsDTO);

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" questions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionsDTO> findOne(Long id);

    /**
     * Delete the "id" questions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
