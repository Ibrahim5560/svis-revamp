package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.Questions;
import com.isoft.svisrevamp.repository.QuestionsRepository;
import com.isoft.svisrevamp.service.criteria.QuestionsCriteria;
import com.isoft.svisrevamp.service.dto.QuestionsDTO;
import com.isoft.svisrevamp.service.mapper.QuestionsMapper;
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
 * Service for executing complex queries for {@link Questions} entities in the database.
 * The main input is a {@link QuestionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionsDTO} or a {@link Page} of {@link QuestionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsQueryService extends QueryService<Questions> {

    private final Logger log = LoggerFactory.getLogger(QuestionsQueryService.class);

    private final QuestionsRepository questionsRepository;

    private final QuestionsMapper questionsMapper;

    public QuestionsQueryService(QuestionsRepository questionsRepository, QuestionsMapper questionsMapper) {
        this.questionsRepository = questionsRepository;
        this.questionsMapper = questionsMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionsDTO> findByCriteria(QuestionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsMapper.toDto(questionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsDTO> findByCriteria(QuestionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.findAll(specification, page).map(questionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Questions> createSpecification(QuestionsCriteria criteria) {
        Specification<Questions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Questions_.id));
            }
            if (criteria.getDescAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescAr(), Questions_.descAr));
            }
            if (criteria.getDescEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescEn(), Questions_.descEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Questions_.code));
            }
            if (criteria.getImgPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgPath(), Questions_.imgPath));
            }
            if (criteria.getTimeInSec() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeInSec(), Questions_.timeInSec));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), Questions_.type));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Questions_.weight));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Questions_.status));
            }
            if (criteria.getCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriesId(),
                            root -> root.join(Questions_.categories, JoinType.LEFT).get(Categories_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
