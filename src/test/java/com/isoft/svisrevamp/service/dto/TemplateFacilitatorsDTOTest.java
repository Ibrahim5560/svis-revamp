package com.isoft.svisrevamp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateFacilitatorsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFacilitatorsDTO.class);
        TemplateFacilitatorsDTO templateFacilitatorsDTO1 = new TemplateFacilitatorsDTO();
        templateFacilitatorsDTO1.setId(1L);
        TemplateFacilitatorsDTO templateFacilitatorsDTO2 = new TemplateFacilitatorsDTO();
        assertThat(templateFacilitatorsDTO1).isNotEqualTo(templateFacilitatorsDTO2);
        templateFacilitatorsDTO2.setId(templateFacilitatorsDTO1.getId());
        assertThat(templateFacilitatorsDTO1).isEqualTo(templateFacilitatorsDTO2);
        templateFacilitatorsDTO2.setId(2L);
        assertThat(templateFacilitatorsDTO1).isNotEqualTo(templateFacilitatorsDTO2);
        templateFacilitatorsDTO1.setId(null);
        assertThat(templateFacilitatorsDTO1).isNotEqualTo(templateFacilitatorsDTO2);
    }
}
