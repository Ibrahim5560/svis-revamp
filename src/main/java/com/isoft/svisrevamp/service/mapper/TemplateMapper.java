package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Template} and its DTO {@link TemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {}
