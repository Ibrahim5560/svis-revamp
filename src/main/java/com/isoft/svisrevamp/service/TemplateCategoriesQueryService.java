package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.repository.TemplateCategoriesRepository;
import com.isoft.svisrevamp.service.criteria.TemplateCategoriesCriteria;
import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
import com.isoft.svisrevamp.service.mapper.TemplateCategoriesMapper;
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
 * Service for executing complex queries for {@link TemplateCategories} entities in the database.
 * The main input is a {@link TemplateCategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateCategoriesDTO} or a {@link Page} of {@link TemplateCategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateCategoriesQueryService extends QueryService<TemplateCategories> {

    private final Logger log = LoggerFactory.getLogger(TemplateCategoriesQueryService.class);

    private final TemplateCategoriesRepository templateCategoriesRepository;

    private final TemplateCategoriesMapper templateCategoriesMapper;

    public TemplateCategoriesQueryService(
        TemplateCategoriesRepository templateCategoriesRepository,
        TemplateCategoriesMapper templateCategoriesMapper
    ) {
        this.templateCategoriesRepository = templateCategoriesRepository;
        this.templateCategoriesMapper = templateCategoriesMapper;
    }

    /**
     * Return a {@link List} of {@link TemplateCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateCategoriesDTO> findByCriteria(TemplateCategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TemplateCategories> specification = createSpecification(criteria);
        return templateCategoriesMapper.toDto(templateCategoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateCategoriesDTO> findByCriteria(TemplateCategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TemplateCategories> specification = createSpecification(criteria);
        return templateCategoriesRepository.findAll(specification, page).map(templateCategoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateCategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TemplateCategories> specification = createSpecification(criteria);
        return templateCategoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateCategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TemplateCategories> createSpecification(TemplateCategoriesCriteria criteria) {
        Specification<TemplateCategories> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TemplateCategories_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TemplateCategories_.code));
            }
            if (criteria.getNoOfQuestions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfQuestions(), TemplateCategories_.noOfQuestions));
            }
            if (criteria.getSeq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeq(), TemplateCategories_.seq));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), TemplateCategories_.status));
            }
            if (criteria.getTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateId(),
                            root -> root.join(TemplateCategories_.template, JoinType.LEFT).get(Template_.id)
                        )
                    );
            }
            if (criteria.getCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriesId(),
                            root -> root.join(TemplateCategories_.categories, JoinType.LEFT).get(Categories_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
