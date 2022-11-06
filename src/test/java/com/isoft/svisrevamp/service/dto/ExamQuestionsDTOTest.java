package com.isoft.svisrevamp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamQuestionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestionsDTO.class);
        ExamQuestionsDTO examQuestionsDTO1 = new ExamQuestionsDTO();
        examQuestionsDTO1.setId(1L);
        ExamQuestionsDTO examQuestionsDTO2 = new ExamQuestionsDTO();
        assertThat(examQuestionsDTO1).isNotEqualTo(examQuestionsDTO2);
        examQuestionsDTO2.setId(examQuestionsDTO1.getId());
        assertThat(examQuestionsDTO1).isEqualTo(examQuestionsDTO2);
        examQuestionsDTO2.setId(2L);
        assertThat(examQuestionsDTO1).isNotEqualTo(examQuestionsDTO2);
        examQuestionsDTO1.setId(null);
        assertThat(examQuestionsDTO1).isNotEqualTo(examQuestionsDTO2);
    }
}
