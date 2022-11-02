package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.domain.TemplateFacilitators;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import com.isoft.svisrevamp.service.dto.TemplateFacilitatorsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateFacilitators} and its DTO {@link TemplateFacilitatorsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateFacilitatorsMapper extends EntityMapper<TemplateFacilitatorsDTO, TemplateFacilitators> {
    @Mapping(target = "template", source = "template", qualifiedByName = "templateId")
    TemplateFacilitatorsDTO toDto(TemplateFacilitators s);

    @Named("templateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateDTO toDtoTemplateId(Template template);
}
