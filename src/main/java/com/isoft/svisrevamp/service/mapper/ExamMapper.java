package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Exam;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.service.dto.ExamDTO;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exam} and its DTO {@link ExamDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {
    @Mapping(target = "template", source = "template", qualifiedByName = "templateId")
    ExamDTO toDto(Exam s);

    @Named("templateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateDTO toDtoTemplateId(Template template);
}
