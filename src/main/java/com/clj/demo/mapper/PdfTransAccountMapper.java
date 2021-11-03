package com.clj.demo.mapper;


import com.clj.demo.entity.PdfTransAccount;
import com.clj.demo.model.PdfTransAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity
 */
@Mapper(componentModel = "spring")
public interface PdfTransAccountMapper extends EntityMapper<PdfTransAccountDTO, PdfTransAccount> {

}
