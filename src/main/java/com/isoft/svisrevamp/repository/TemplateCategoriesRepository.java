package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.TemplateCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TemplateCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateCategoriesRepository
    extends JpaRepository<TemplateCategories, Long>, JpaSpecificationExecutor<TemplateCategories> {}
