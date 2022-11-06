package com.isoft.svisrevamp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateFacilitatorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFacilitators.class);
        TemplateFacilitators templateFacilitators1 = new TemplateFacilitators();
        templateFacilitators1.setId(1L);
        TemplateFacilitators templateFacilitators2 = new TemplateFacilitators();
        templateFacilitators2.setId(templateFacilitators1.getId());
        assertThat(templateFacilitators1).isEqualTo(templateFacilitators2);
        templateFacilitators2.setId(2L);
        assertThat(templateFacilitators1).isNotEqualTo(templateFacilitators2);
        templateFacilitators1.setId(null);
        assertThat(templateFacilitators1).isNotEqualTo(templateFacilitators2);
    }
}
