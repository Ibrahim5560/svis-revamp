package com.isoft.svisrevamp.service;

import com.isoft.svisrevamp.service.dto.TemplateFacilitatorsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.svisrevamp.domain.TemplateFacilitators}.
 */
public interface TemplateFacilitatorsService {
    /**
     * Save a templateFacilitators.
     *
     * @param templateFacilitatorsDTO the entity to save.
     * @return the persisted entity.
     */
    TemplateFacilitatorsDTO save(TemplateFacilitatorsDTO templateFacilitatorsDTO);

    /**
     * Updates a templateFacilitators.
     *
     * @param templateFacilitatorsDTO the entity to update.
     * @return the persisted entity.
     */
    TemplateFacilitatorsDTO update(TemplateFacilitatorsDTO templateFacilitatorsDTO);

    /**
     * Partially updates a templateFacilitators.
     *
     * @param templateFacilitatorsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemplateFacilitatorsDTO> partialUpdate(TemplateFacilitatorsDTO templateFacilitatorsDTO);

    /**
     * Get all the templateFacilitators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateFacilitatorsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" templateFacilitators.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemplateFacilitatorsDTO> findOne(Long id);

    /**
     * Delete the "id" templateFacilitators.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
