package com.isoft.svisrevamp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateCategoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateCategoriesDTO.class);
        TemplateCategoriesDTO templateCategoriesDTO1 = new TemplateCategoriesDTO();
        templateCategoriesDTO1.setId(1L);
        TemplateCategoriesDTO templateCategoriesDTO2 = new TemplateCategoriesDTO();
        assertThat(templateCategoriesDTO1).isNotEqualTo(templateCategoriesDTO2);
        templateCategoriesDTO2.setId(templateCategoriesDTO1.getId());
        assertThat(templateCategoriesDTO1).isEqualTo(templateCategoriesDTO2);
        templateCategoriesDTO2.setId(2L);
        assertThat(templateCategoriesDTO1).isNotEqualTo(templateCategoriesDTO2);
        templateCategoriesDTO1.setId(null);
        assertThat(templateCategoriesDTO1).isNotEqualTo(templateCategoriesDTO2);
    }
}
