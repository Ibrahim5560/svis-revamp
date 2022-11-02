package com.isoft.svisrevamp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.svisrevamp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamQuestionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestions.class);
        ExamQuestions examQuestions1 = new ExamQuestions();
        examQuestions1.setId(1L);
        ExamQuestions examQuestions2 = new ExamQuestions();
        examQuestions2.setId(examQuestions1.getId());
        assertThat(examQuestions1).isEqualTo(examQuestions2);
        examQuestions2.setId(2L);
        assertThat(examQuestions1).isNotEqualTo(examQuestions2);
        examQuestions1.setId(null);
        assertThat(examQuestions1).isNotEqualTo(examQuestions2);
    }
}
