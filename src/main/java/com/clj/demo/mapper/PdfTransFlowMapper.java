package com.clj.demo.mapper;



import com.clj.demo.entity.PdfTransFlow;
import com.clj.demo.model.PdfTransFlowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity
 */
@Mapper(componentModel = "spring")
public interface PdfTransFlowMapper extends EntityMapper<PdfTransFlowDTO, PdfTransFlow> {
}
