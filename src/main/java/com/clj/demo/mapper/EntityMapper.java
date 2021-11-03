package com.clj.demo.mapper;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description
 * @date 2021/11/3
 **/
public interface EntityMapper <D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}