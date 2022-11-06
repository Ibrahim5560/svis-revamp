package com.isoft.svisrevamp.service.impl;

import com.isoft.svisrevamp.domain.TemplateFacilitators;
import com.isoft.svisrevamp.repository.TemplateFacilitatorsRepository;
import com.isoft.svisrevamp.service.TemplateFacilitatorsService;
import com.isoft.svisrevamp.service.dto.TemplateFacilitatorsDTO;
import com.isoft.svisrevamp.service.mapper.TemplateFacilitatorsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemplateFacilitators}.
 */
@Service
@Transactional
public class TemplateFacilitatorsServiceImpl implements TemplateFacilitatorsService {

    private final Logger log = LoggerFactory.getLogger(TemplateFacilitatorsServiceImpl.class);

    private final TemplateFacilitatorsRepository templateFacilitatorsRepository;

    private final TemplateFacilitatorsMapper templateFacilitatorsMapper;

    public TemplateFacilitatorsServiceImpl(
        TemplateFacilitatorsRepository templateFacilitatorsRepository,
        TemplateFacilitatorsMapper templateFacilitatorsMapper
    ) {
        this.templateFacilitatorsRepository = templateFacilitatorsRepository;
        this.templateFacilitatorsMapper = templateFacilitatorsMapper;
    }

    @Override
    public TemplateFacilitatorsDTO save(TemplateFacilitatorsDTO templateFacilitatorsDTO) {
        log.debug("Request to save TemplateFacilitators : {}", templateFacilitatorsDTO);
        TemplateFacilitators templateFacilitators = templateFacilitatorsMapper.toEntity(templateFacilitatorsDTO);
        templateFacilitators = templateFacilitatorsRepository.save(templateFacilitators);
        return templateFacilitatorsMapper.toDto(templateFacilitators);
    }

    @Override
    public TemplateFacilitatorsDTO update(TemplateFacilitatorsDTO templateFacilitatorsDTO) {
        log.debug("Request to update TemplateFacilitators : {}", templateFacilitatorsDTO);
        TemplateFacilitators templateFacilitators = templateFacilitatorsMapper.toEntity(templateFacilitatorsDTO);
        templateFacilitators = templateFacilitatorsRepository.save(templateFacilitators);
        return templateFacilitatorsMapper.toDto(templateFacilitators);
    }

    @Override
    public Optional<TemplateFacilitatorsDTO> partialUpdate(TemplateFacilitatorsDTO templateFacilitatorsDTO) {
        log.debug("Request to partially update TemplateFacilitators : {}", templateFacilitatorsDTO);

        return templateFacilitatorsRepository
            .findById(templateFacilitatorsDTO.getId())
            .map(existingTemplateFacilitators -> {
                templateFacilitatorsMapper.partialUpdate(existingTemplateFacilitators, templateFacilitatorsDTO);

                return existingTemplateFacilitators;
            })
            .map(templateFacilitatorsRepository::save)
            .map(templateFacilitatorsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateFacilitatorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateFacilitators");
        return templateFacilitatorsRepository.findAll(pageable).map(templateFacilitatorsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateFacilitatorsDTO> findOne(Long id) {
        log.debug("Request to get TemplateFacilitators : {}", id);
        return templateFacilitatorsRepository.findById(id).map(templateFacilitatorsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateFacilitators : {}", id);
        templateFacilitatorsRepository.deleteById(id);
    }
}
