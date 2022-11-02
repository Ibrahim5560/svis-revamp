package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.domain.Questions;
import com.isoft.svisrevamp.service.dto.CategoriesDTO;
import com.isoft.svisrevamp.service.dto.QuestionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questions} and its DTO {@link QuestionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionsMapper extends EntityMapper<QuestionsDTO, Questions> {
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoriesId")
    QuestionsDTO toDto(Questions s);

    @Named("categoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriesDTO toDtoCategoriesId(Categories categories);
}
