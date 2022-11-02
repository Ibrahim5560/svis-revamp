package com.isoft.svisrevamp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExamQuestionsMapperTest {

    private ExamQuestionsMapper examQuestionsMapper;

    @BeforeEach
    public void setUp() {
        examQuestionsMapper = new ExamQuestionsMapperImpl();
    }
}
