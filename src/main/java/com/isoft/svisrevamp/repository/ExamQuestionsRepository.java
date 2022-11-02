package com.isoft.svisrevamp.repository;

import com.isoft.svisrevamp.domain.ExamQuestions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExamQuestions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamQuestionsRepository extends JpaRepository<ExamQuestions, Long>, JpaSpecificationExecutor<ExamQuestions> {}
