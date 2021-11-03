package com.clj.demo.repository;


import com.clj.demo.entity.PdfTransAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PdfTransAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PdfTransAccountRepository extends JpaRepository<PdfTransAccount, Long> {
    /**
     * 根据pdf流水编号进行查询
     *
     * @param billNumber
     * @return
     */
    List<PdfTransAccount> findByBillNumber(String billNumber);
}
