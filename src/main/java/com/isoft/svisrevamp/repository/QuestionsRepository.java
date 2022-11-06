package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.Questions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Questions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long>, JpaSpecificationExecutor<Questions> {}
