package com.clj.demo.service.impl;

import com.clj.demo.entity.PdfTransFlow;
import com.clj.demo.mapper.PdfTransFlowMapper;
import com.clj.demo.model.PdfTransFlowDTO;
import com.clj.demo.repository.PdfTransFlowRepository;
import com.clj.demo.service.PdfTransFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing
 */
@Service
@Transactional
public class PdfTransFlowServiceImpl implements PdfTransFlowService {

    private final Logger log = LoggerFactory.getLogger(PdfTransFlowServiceImpl.class);
    @Resource
    private PdfTransFlowRepository pdfTransFlowRepository;
    @Autowired
    private PdfTransFlowMapper pdfTransFlowMapper;

    @Override
    public PdfTransFlowDTO save(PdfTransFlowDTO pdfTransFlowDTO) {
        log.debug("Request to save PdfTransFlow : {}", pdfTransFlowDTO);
        PdfTransFlow pdfTransFlow = pdfTransFlowMapper.toEntity(pdfTransFlowDTO);
        pdfTransFlow = pdfTransFlowRepository.save(pdfTransFlow);
        return pdfTransFlowMapper.toDto(pdfTransFlow);
    }

    @Override
    public void saveAll(List<PdfTransFlowDTO> pdfTransFlowDTO) {
        log.debug("Request to save PdfTransFlow : {}", pdfTransFlowDTO);
        List<PdfTransFlow> pdfTransFlows = pdfTransFlowMapper.toEntity(pdfTransFlowDTO);
        pdfTransFlowRepository.saveAll(pdfTransFlows);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PdfTransFlowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PdfTransFlows");
        return pdfTransFlowRepository.findAll(pageable)
                .map(pdfTransFlowMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PdfTransFlowDTO> findOne(Long id) {
        log.debug("Request to get PdfTransFlow : {}", id);
        return pdfTransFlowRepository.findById(id)
                .map(pdfTransFlowMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PdfTransFlow : {}", id);
        pdfTransFlowRepository.deleteById(id);
    }
}
