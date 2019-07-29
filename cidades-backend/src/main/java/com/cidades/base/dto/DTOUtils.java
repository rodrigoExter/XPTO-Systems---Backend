package com.cidades.base.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;

import com.cidades.base.entity.AbstractEntityClass;


/**
 * Classe utilitaria para lidar com DTOs
 * 
 * @author ruan
 */
public class DTOUtils {

  /**
   * Converte para entidade
   * 
   * @param example Exemplo
   * @return Objeto convertido para T
   */
  public static <PK extends Serializable, T extends AbstractEntityClass<PK>, DTO extends AbstractDTO<PK, T>> DTO convertToDTO(final T example, final Class<DTO> dtoClass) {
    final ModelMapper modelMapper = new ModelMapper();

    return modelMapper.map(example, dtoClass);
  }

  /**
   * Converte de entidade para DTO
   * 
   * @param example Exemplo
   * @return Objeto convertido para DTO
   */
  public static <PK extends Serializable, T extends AbstractEntityClass<PK>, DTO extends AbstractDTO<PK, T>> T convertToEntity(final DTO example, final Class<T> entityClass) {
    final ModelMapper modelMapper = new ModelMapper();

    return modelMapper.map(example, entityClass);
  }
}