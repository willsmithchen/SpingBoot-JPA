package com.clj.demo.service.impl;

import com.clj.demo.entity.PdfTransAccount;
import com.clj.demo.mapper.PdfTransAccountMapper;
import com.clj.demo.model.PdfTransAccountDTO;
import com.clj.demo.repository.PdfTransAccountRepository;
import com.clj.demo.service.PdfTransAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
public class PdfTransAccountServiceImpl implements PdfTransAccountService {

    private final Logger log = LoggerFactory.getLogger(PdfTransAccountServiceImpl.class);
    @Resource
    private PdfTransAccountRepository pdfTransAccountRepository;
    @Autowired
    private PdfTransAccountMapper pdfTransAccountMapper;

    @Override
    public PdfTransAccountDTO save(PdfTransAccountDTO pdfTransAccountDTO) {
        log.debug("Request to save PdfTransAccount : {}", pdfTransAccountDTO);
        PdfTransAccount pdfTransAccount = pdfTransAccountMapper.toEntity(pdfTransAccountDTO);
        pdfTransAccount = pdfTransAccountRepository.save(pdfTransAccount);
        return pdfTransAccountMapper.toDto(pdfTransAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PdfTransAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PdfTransAccounts");
        return pdfTransAccountRepository.findAll(pageable)
                .map(pdfTransAccountMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PdfTransAccountDTO> findOne(Long id) {
        log.debug("Request to get PdfTransAccount : {}", id);
        return pdfTransAccountRepository.findById(id)
                .map(pdfTransAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PdfTransAccount : {}", id);
        pdfTransAccountRepository.deleteById(id);
    }

    @Override
    public Boolean findByBillNumber(String billNumber) {
        List<PdfTransAccount> pdfTransAccounts = pdfTransAccountRepository.findByBillNumber(billNumber);
        if (pdfTransAccounts.isEmpty()) {
            return false;
        }
        return true;
    }
}
