package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.domain.ExamQuestions;
import com.isoft.svisrevamp.service.dto.ExamDTO;
import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamQuestions} and its DTO {@link ExamQuestionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamQuestionsMapper extends EntityMapper<ExamQuestionsDTO, ExamQuestions> {
    @Mapping(target = "exam", source = "exam", qualifiedByName = "examId")
    ExamQuestionsDTO toDto(ExamQuestions s);

    @Named("examId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamDTO toDtoExamId(Exam exam);
}
