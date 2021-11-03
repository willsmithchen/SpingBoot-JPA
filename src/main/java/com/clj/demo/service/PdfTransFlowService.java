package com.clj.demo.service;


import com.clj.demo.model.PdfTransFlowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing
 */
public interface PdfTransFlowService {

    /**
     * Save a pdfTransFlow.
     *
     * @param pdfTransFlowDTO the entity to save.
     * @return the persisted entity.
     */
    PdfTransFlowDTO save(PdfTransFlowDTO pdfTransFlowDTO);

    /**
     * 增加全部
     *
     * @param pdfTransFlowDTO
     */
    void saveAll(List<PdfTransFlowDTO> pdfTransFlowDTO);

    /**
     * Get all the pdfTransFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PdfTransFlowDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pdfTransFlow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PdfTransFlowDTO> findOne(Long id);

    /**
     * Delete the "id" pdfTransFlow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
