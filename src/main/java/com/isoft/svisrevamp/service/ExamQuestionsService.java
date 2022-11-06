package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.svisrevamp.domain.ExamQuestions}.
 */
public interface ExamQuestionsService {
    /**
     * Save a examQuestions.
     *
     * @param examQuestionsDTO the entity to save.
     * @return the persisted entity.
     */
    ExamQuestionsDTO save(ExamQuestionsDTO examQuestionsDTO);

    /**
     * Updates a examQuestions.
     *
     * @param examQuestionsDTO the entity to update.
     * @return the persisted entity.
     */
    ExamQuestionsDTO update(ExamQuestionsDTO examQuestionsDTO);

    /**
     * Partially updates a examQuestions.
     *
     * @param examQuestionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamQuestionsDTO> partialUpdate(ExamQuestionsDTO examQuestionsDTO);

    /**
     * Get all the examQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamQuestionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examQuestions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamQuestionsDTO> findOne(Long id);

    /**
     * Delete the "id" examQuestions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
