package com.isoft.svisrevamp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateCategories.class);
        TemplateCategories templateCategories1 = new TemplateCategories();
        templateCategories1.setId(1L);
        TemplateCategories templateCategories2 = new TemplateCategories();
        templateCategories2.setId(templateCategories1.getId());
        assertThat(templateCategories1).isEqualTo(templateCategories2);
        templateCategories2.setId(2L);
        assertThat(templateCategories1).isNotEqualTo(templateCategories2);
        templateCategories1.setId(null);
        assertThat(templateCategories1).isNotEqualTo(templateCategories2);
    }
}
