package com.isoft.svisrevamp.service.impl;

import com.isoft.svisrevamp.domain.Questions;
import com.isoft.svisrevamp.repository.QuestionsRepository;
import com.isoft.svisrevamp.service.QuestionsService;
import com.isoft.svisrevamp.service.dto.QuestionsDTO;
import com.isoft.svisrevamp.service.mapper.QuestionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Questions}.
 */
@Service
@Transactional
public class QuestionsServiceImpl implements QuestionsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsServiceImpl.class);

    private final QuestionsRepository questionsRepository;

    private final QuestionsMapper questionsMapper;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository, QuestionsMapper questionsMapper) {
        this.questionsRepository = questionsRepository;
        this.questionsMapper = questionsMapper;
    }

    @Override
    public QuestionsDTO save(QuestionsDTO questionsDTO) {
        log.debug("Request to save Questions : {}", questionsDTO);
        Questions questions = questionsMapper.toEntity(questionsDTO);
        questions = questionsRepository.save(questions);
        return questionsMapper.toDto(questions);
    }

    @Override
    public QuestionsDTO update(QuestionsDTO questionsDTO) {
        log.debug("Request to update Questions : {}", questionsDTO);
        Questions questions = questionsMapper.toEntity(questionsDTO);
        questions = questionsRepository.save(questions);
        return questionsMapper.toDto(questions);
    }

    @Override
    public Optional<QuestionsDTO> partialUpdate(QuestionsDTO questionsDTO) {
        log.debug("Request to partially update Questions : {}", questionsDTO);

        return questionsRepository
            .findById(questionsDTO.getId())
            .map(existingQuestions -> {
                questionsMapper.partialUpdate(existingQuestions, questionsDTO);

                return existingQuestions;
            })
            .map(questionsRepository::save)
            .map(questionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionsRepository.findAll(pageable).map(questionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionsDTO> findOne(Long id) {
        log.debug("Request to get Questions : {}", id);
        return questionsRepository.findById(id).map(questionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Questions : {}", id);
        questionsRepository.deleteById(id);
    }
}
