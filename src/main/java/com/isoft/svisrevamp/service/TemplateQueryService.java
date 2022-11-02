package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.repository.TemplateRepository;
import com.isoft.svisrevamp.service.criteria.TemplateCriteria;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import com.isoft.svisrevamp.service.mapper.TemplateMapper;
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
 * Service for executing complex queries for {@link Template} entities in the database.
 * The main input is a {@link TemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateDTO} or a {@link Page} of {@link TemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateQueryService extends QueryService<Template> {

    private final Logger log = LoggerFactory.getLogger(TemplateQueryService.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateQueryService(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * Return a {@link List} of {@link TemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateDTO> findByCriteria(TemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateMapper.toDto(templateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateDTO> findByCriteria(TemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.findAll(specification, page).map(templateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Template> createSpecification(TemplateCriteria criteria) {
        Specification<Template> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Template_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), Template_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), Template_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Template_.code));
            }
            if (criteria.getTimeInSec() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeInSec(), Template_.timeInSec));
            }
            if (criteria.getPassScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassScore(), Template_.passScore));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Template_.status));
            }
            if (criteria.getTemplateCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateCategoriesId(),
                            root -> root.join(Template_.templateCategories, JoinType.LEFT).get(TemplateCategories_.id)
                        )
                    );
            }
            if (criteria.getTemplateFacilitatorsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateFacilitatorsId(),
                            root -> root.join(Template_.templateFacilitators, JoinType.LEFT).get(TemplateFacilitators_.id)
                        )
                    );
            }
            if (criteria.getExamId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getExamId(), root -> root.join(Template_.exams, JoinType.LEFT).get(Exam_.id))
                    );
            }
        }
        return specification;
    }
}
