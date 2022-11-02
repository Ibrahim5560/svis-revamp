package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.repository.ExamRepository;
import com.isoft.svisrevamp.service.criteria.ExamCriteria;
import com.isoft.svisrevamp.service.dto.ExamDTO;
import com.isoft.svisrevamp.service.mapper.ExamMapper;
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
 * Service for executing complex queries for {@link Exam} entities in the database.
 * The main input is a {@link ExamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamDTO} or a {@link Page} of {@link ExamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamQueryService extends QueryService<Exam> {

    private final Logger log = LoggerFactory.getLogger(ExamQueryService.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    public ExamQueryService(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    /**
     * Return a {@link List} of {@link ExamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamDTO> findByCriteria(ExamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Exam> specification = createSpecification(criteria);
        return examMapper.toDto(examRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamDTO> findByCriteria(ExamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Exam> specification = createSpecification(criteria);
        return examRepository.findAll(specification, page).map(examMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Exam> specification = createSpecification(criteria);
        return examRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Exam> createSpecification(ExamCriteria criteria) {
        Specification<Exam> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Exam_.id));
            }
            if (criteria.getPassScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassScore(), Exam_.passScore));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Exam_.score));
            }
            if (criteria.getTimeInSec() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeInSec(), Exam_.timeInSec));
            }
            if (criteria.getValidfrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidfrom(), Exam_.validfrom));
            }
            if (criteria.getValidto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidto(), Exam_.validto));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), Exam_.startAt));
            }
            if (criteria.getSubmitAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitAt(), Exam_.submitAt));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), Exam_.userId));
            }
            if (criteria.getExaminerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExaminerId(), Exam_.examinerId));
            }
            if (criteria.getExamCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExamCode(), Exam_.examCode));
            }
            if (criteria.getExamDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExamDate(), Exam_.examDate));
            }
            if (criteria.getExamResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExamResult(), Exam_.examResult));
            }
            if (criteria.getExported() != null) {
                specification = specification.and(buildSpecification(criteria.getExported(), Exam_.exported));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Exam_.status));
            }
            if (criteria.getExamQuestionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExamQuestionsId(),
                            root -> root.join(Exam_.examQuestions, JoinType.LEFT).get(ExamQuestions_.id)
                        )
                    );
            }
            if (criteria.getTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTemplateId(), root -> root.join(Exam_.template, JoinType.LEFT).get(Template_.id))
                    );
            }
        }
        return specification;
    }
}
