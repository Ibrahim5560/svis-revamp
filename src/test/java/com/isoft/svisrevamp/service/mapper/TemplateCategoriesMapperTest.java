package com.isoft.svisrevamp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateCategoriesMapperTest {

    private TemplateCategoriesMapper templateCategoriesMapper;

    @BeforeEach
    public void setUp() {
        templateCategoriesMapper = new TemplateCategoriesMapperImpl();
    }
}
