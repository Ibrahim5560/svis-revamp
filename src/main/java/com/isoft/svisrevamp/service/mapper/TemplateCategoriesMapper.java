package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.domain.Template;
import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.service.dto.CategoriesDTO;
import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
import com.isoft.svisrevamp.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateCategories} and its DTO {@link TemplateCategoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateCategoriesMapper extends EntityMapper<TemplateCategoriesDTO, TemplateCategories> {
    @Mapping(target = "template", source = "template", qualifiedByName = "templateId")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoriesId")
    TemplateCategoriesDTO toDto(TemplateCategories s);

    @Named("templateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateDTO toDtoTemplateId(Template template);

    @Named("categoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriesDTO toDtoCategoriesId(Categories categories);
}
