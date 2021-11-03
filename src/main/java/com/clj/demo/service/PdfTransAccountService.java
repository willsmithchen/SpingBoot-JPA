package com.clj.demo.service;


import com.clj.demo.model.PdfTransAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing
 */
public interface PdfTransAccountService {

    /**
     * Save a pdfTransAccount.
     *
     * @param pdfTransAccountDTO the entity to save.
     * @return the persisted entity.
     */
    PdfTransAccountDTO save(PdfTransAccountDTO pdfTransAccountDTO);

    /**
     * Get all the pdfTransAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PdfTransAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pdfTransAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PdfTransAccountDTO> findOne(Long id);

    /**
     * Delete the "id" pdfTransAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 判断微信或支付宝文件是否已进行过解析
     *
     * @param billNumber
     * @return 如果存在返回true，不在进行再次解析文档，若不存在，则进行解析
     */
    Boolean findByBillNumber(String billNumber);
}
