package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.repository.CategoriesRepository;
import com.isoft.svisrevamp.service.criteria.CategoriesCriteria;
import com.isoft.svisrevamp.service.dto.CategoriesDTO;
import com.isoft.svisrevamp.service.mapper.CategoriesMapper;
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
 * Service for executing complex queries for {@link Categories} entities in the database.
 * The main input is a {@link CategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriesDTO} or a {@link Page} of {@link CategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriesQueryService extends QueryService<Categories> {

    private final Logger log = LoggerFactory.getLogger(CategoriesQueryService.class);

    private final CategoriesRepository categoriesRepository;

    private final CategoriesMapper categoriesMapper;

    public CategoriesQueryService(CategoriesRepository categoriesRepository, CategoriesMapper categoriesMapper) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesMapper = categoriesMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriesDTO> findByCriteria(CategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesMapper.toDto(categoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriesDTO> findByCriteria(CategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesRepository.findAll(specification, page).map(categoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Categories> createSpecification(CategoriesCriteria criteria) {
        Specification<Categories> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Categories_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), Categories_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), Categories_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Categories_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Categories_.status));
            }
            if (criteria.getTempCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTempCategoriesId(),
                            root -> root.join(Categories_.tempCategories, JoinType.LEFT).get(TemplateCategories_.id)
                        )
                    );
            }
            if (criteria.getQuestionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsId(),
                            root -> root.join(Categories_.questions, JoinType.LEFT).get(Questions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
