package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.Template;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>, JpaSpecificationExecutor<Template> {}
