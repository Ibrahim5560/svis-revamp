package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.domain.*; // for static metamodels
import com.isoft.svisrevamp.domain.TemplateFacilitators;
import com.isoft.svisrevamp.repository.TemplateFacilitatorsRepository;
import com.isoft.svisrevamp.service.criteria.TemplateFacilitatorsCriteria;
import com.isoft.svisrevamp.service.dto.TemplateFacilitatorsDTO;
import com.isoft.svisrevamp.service.mapper.TemplateFacilitatorsMapper;
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
 * Service for executing complex queries for {@link TemplateFacilitators} entities in the database.
 * The main input is a {@link TemplateFacilitatorsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateFacilitatorsDTO} or a {@link Page} of {@link TemplateFacilitatorsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateFacilitatorsQueryService extends QueryService<TemplateFacilitators> {

    private final Logger log = LoggerFactory.getLogger(TemplateFacilitatorsQueryService.class);

    private final TemplateFacilitatorsRepository templateFacilitatorsRepository;

    private final TemplateFacilitatorsMapper templateFacilitatorsMapper;

    public TemplateFacilitatorsQueryService(
        TemplateFacilitatorsRepository templateFacilitatorsRepository,
        TemplateFacilitatorsMapper templateFacilitatorsMapper
    ) {
        this.templateFacilitatorsRepository = templateFacilitatorsRepository;
        this.templateFacilitatorsMapper = templateFacilitatorsMapper;
    }

    /**
     * Return a {@link List} of {@link TemplateFacilitatorsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateFacilitatorsDTO> findByCriteria(TemplateFacilitatorsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TemplateFacilitators> specification = createSpecification(criteria);
        return templateFacilitatorsMapper.toDto(templateFacilitatorsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateFacilitatorsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateFacilitatorsDTO> findByCriteria(TemplateFacilitatorsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TemplateFacilitators> specification = createSpecification(criteria);
        return templateFacilitatorsRepository.findAll(specification, page).map(templateFacilitatorsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateFacilitatorsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TemplateFacilitators> specification = createSpecification(criteria);
        return templateFacilitatorsRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateFacilitatorsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TemplateFacilitators> createSpecification(TemplateFacilitatorsCriteria criteria) {
        Specification<TemplateFacilitators> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TemplateFacilitators_.id));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), TemplateFacilitators_.count));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCenterId(), TemplateFacilitators_.centerId));
            }
            if (criteria.getFacilitatorType() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFacilitatorType(), TemplateFacilitators_.facilitatorType));
            }
            if (criteria.getTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateId(),
                            root -> root.join(TemplateFacilitators_.template, JoinType.LEFT).get(Template_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
