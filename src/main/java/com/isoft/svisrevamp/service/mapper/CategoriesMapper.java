package com.isoft.svisrevamp.service.mapper;

import com.isoft.svisrevamp.domain.Categories;
import com.isoft.svisrevamp.service.dto.CategoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categories} and its DTO {@link CategoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriesMapper extends EntityMapper<CategoriesDTO, Categories> {}
