package com.isoft.svisrevamp.service.impl;

import com.isoft.svisrevamp.domain.ExamQuestions;
import com.isoft.svisrevamp.repository.ExamQuestionsRepository;
import com.isoft.svisrevamp.service.ExamQuestionsService;
import com.isoft.svisrevamp.service.dto.ExamQuestionsDTO;
import com.isoft.svisrevamp.service.mapper.ExamQuestionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExamQuestions}.
 */
@Service
@Transactional
public class ExamQuestionsServiceImpl implements ExamQuestionsService {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionsServiceImpl.class);

    private final ExamQuestionsRepository examQuestionsRepository;

    private final ExamQuestionsMapper examQuestionsMapper;

    public ExamQuestionsServiceImpl(ExamQuestionsRepository examQuestionsRepository, ExamQuestionsMapper examQuestionsMapper) {
        this.examQuestionsRepository = examQuestionsRepository;
        this.examQuestionsMapper = examQuestionsMapper;
    }

    @Override
    public ExamQuestionsDTO save(ExamQuestionsDTO examQuestionsDTO) {
        log.debug("Request to save ExamQuestions : {}", examQuestionsDTO);
        ExamQuestions examQuestions = examQuestionsMapper.toEntity(examQuestionsDTO);
        examQuestions = examQuestionsRepository.save(examQuestions);
        return examQuestionsMapper.toDto(examQuestions);
    }

    @Override
    public ExamQuestionsDTO update(ExamQuestionsDTO examQuestionsDTO) {
        log.debug("Request to update ExamQuestions : {}", examQuestionsDTO);
        ExamQuestions examQuestions = examQuestionsMapper.toEntity(examQuestionsDTO);
        examQuestions = examQuestionsRepository.save(examQuestions);
        return examQuestionsMapper.toDto(examQuestions);
    }

    @Override
    public Optional<ExamQuestionsDTO> partialUpdate(ExamQuestionsDTO examQuestionsDTO) {
        log.debug("Request to partially update ExamQuestions : {}", examQuestionsDTO);

        return examQuestionsRepository
            .findById(examQuestionsDTO.getId())
            .map(existingExamQuestions -> {
                examQuestionsMapper.partialUpdate(existingExamQuestions, examQuestionsDTO);

                return existingExamQuestions;
            })
            .map(examQuestionsRepository::save)
            .map(examQuestionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamQuestionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamQuestions");
        return examQuestionsRepository.findAll(pageable).map(examQuestionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamQuestionsDTO> findOne(Long id) {
        log.debug("Request to get ExamQuestions : {}", id);
        return examQuestionsRepository.findById(id).map(examQuestionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamQuestions : {}", id);
        examQuestionsRepository.deleteById(id);
    }
}
