package com.clj.demo.repository;

import com.clj.demo.entity.PdfTransFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PdfTransFlow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PdfTransFlowRepository extends JpaRepository<PdfTransFlow, Long> {
}
