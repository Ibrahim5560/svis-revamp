package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.TemplateFacilitators;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TemplateFacilitators entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateFacilitatorsRepository
    extends JpaRepository<TemplateFacilitators, Long>, JpaSpecificationExecutor<TemplateFacilitators> {}
