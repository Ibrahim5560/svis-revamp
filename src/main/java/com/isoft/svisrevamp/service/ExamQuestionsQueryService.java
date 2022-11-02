package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.ExamQuestions;
import com.isoft.svisrevamp.repository.ExamQuestionsRepository;
import com.isoft.svisrevamp.service.criteria.ExamQuestionsCriteria;
import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import com.isoft.svisrevamp.service.mapper.ExamQuestionsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ExamQuestions} entities in the database.
 * The main input is a {@link ExamQuestionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamQuestionsDTO} or a {@link Page} of {@link ExamQuestionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamQuestionsQueryService extends QueryService<ExamQuestions> {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionsQueryService.class);

    private final ExamQuestionsRepository examQuestionsRepository;

    private final ExamQuestionsMapper examQuestionsMapper;

    public ExamQuestionsQueryService(ExamQuestionsRepository examQuestionsRepository, ExamQuestionsMapper examQuestionsMapper) {
        this.examQuestionsRepository = examQuestionsRepository;
        this.examQuestionsMapper = examQuestionsMapper;
    }

    /**
     * Return a {@link List} of {@link ExamQuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamQuestionsDTO> findByCriteria(ExamQuestionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExamQuestions> specification = createSpecification(criteria);
        return examQuestionsMapper.toDto(examQuestionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExamQuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamQuestionsDTO> findByCriteria(ExamQuestionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExamQuestions> specification = createSpecification(criteria);
        return examQuestionsRepository.findAll(specification, page).map(examQuestionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamQuestionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExamQuestions> specification = createSpecification(criteria);
        return examQuestionsRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamQuestionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExamQuestions> createSpecification(ExamQuestionsCriteria criteria) {
        Specification<ExamQuestions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExamQuestions_.id));
            }
            if (criteria.getDescAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescAr(), ExamQuestions_.descAr));
            }
            if (criteria.getDescEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescEn(), ExamQuestions_.descEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ExamQuestions_.code));
            }
            if (criteria.getImgPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgPath(), ExamQuestions_.imgPath));
            }
            if (criteria.getTimeInSec() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeInSec(), ExamQuestions_.timeInSec));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), ExamQuestions_.type));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), ExamQuestions_.weight));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), ExamQuestions_.score));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), ExamQuestions_.startAt));
            }
            if (criteria.getSubmitAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitAt(), ExamQuestions_.submitAt));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), ExamQuestions_.categoryId));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuestionId(), ExamQuestions_.questionId));
            }
            if (criteria.getSeq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeq(), ExamQuestions_.seq));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), ExamQuestions_.status));
            }
            if (criteria.getExamId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getExamId(), root -> root.join(ExamQuestions_.exam, JoinType.LEFT).get(Exam_.id))
                    );
            }
        }
        return specification;
    }
}
