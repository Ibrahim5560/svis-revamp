package com.isoft.svisrevamp.service.impl;

import com.isoft.svisrevamp.domain.TemplateCategories;
import com.isoft.svisrevamp.repository.TemplateCategoriesRepository;
import com.isoft.svisrevamp.service.TemplateCategoriesService;
import com.isoft.svisrevamp.service.dto.TemplateCategoriesDTO;
import com.isoft.svisrevamp.service.mapper.TemplateCategoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemplateCategories}.
 */
@Service
@Transactional
public class TemplateCategoriesServiceImpl implements TemplateCategoriesService {

    private final Logger log = LoggerFactory.getLogger(TemplateCategoriesServiceImpl.class);

    private final TemplateCategoriesRepository templateCategoriesRepository;

    private final TemplateCategoriesMapper templateCategoriesMapper;

    public TemplateCategoriesServiceImpl(
        TemplateCategoriesRepository templateCategoriesRepository,
        TemplateCategoriesMapper templateCategoriesMapper
    ) {
        this.templateCategoriesRepository = templateCategoriesRepository;
        this.templateCategoriesMapper = templateCategoriesMapper;
    }

    @Override
    public TemplateCategoriesDTO save(TemplateCategoriesDTO templateCategoriesDTO) {
        log.debug("Request to save TemplateCategories : {}", templateCategoriesDTO);
        TemplateCategories templateCategories = templateCategoriesMapper.toEntity(templateCategoriesDTO);
        templateCategories = templateCategoriesRepository.save(templateCategories);
        return templateCategoriesMapper.toDto(templateCategories);
    }

    @Override
    public TemplateCategoriesDTO update(TemplateCategoriesDTO templateCategoriesDTO) {
        log.debug("Request to update TemplateCategories : {}", templateCategoriesDTO);
        TemplateCategories templateCategories = templateCategoriesMapper.toEntity(templateCategoriesDTO);
        templateCategories = templateCategoriesRepository.save(templateCategories);
        return templateCategoriesMapper.toDto(templateCategories);
    }

    @Override
    public Optional<TemplateCategoriesDTO> partialUpdate(TemplateCategoriesDTO templateCategoriesDTO) {
        log.debug("Request to partially update TemplateCategories : {}", templateCategoriesDTO);

        return templateCategoriesRepository
            .findById(templateCategoriesDTO.getId())
            .map(existingTemplateCategories -> {
                templateCategoriesMapper.partialUpdate(existingTemplateCategories, templateCategoriesDTO);

                return existingTemplateCategories;
            })
            .map(templateCategoriesRepository::save)
            .map(templateCategoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateCategoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateCategories");
        return templateCategoriesRepository.findAll(pageable).map(templateCategoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateCategoriesDTO> findOne(Long id) {
        log.debug("Request to get TemplateCategories : {}", id);
        return templateCategoriesRepository.findById(id).map(templateCategoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateCategories : {}", id);
        templateCategoriesRepository.deleteById(id);
    }
}
