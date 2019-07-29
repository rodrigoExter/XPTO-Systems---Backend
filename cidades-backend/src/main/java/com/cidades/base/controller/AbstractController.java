package com.cidades.base.controller;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cidades.base.dto.AbstractDTO;
import com.cidades.base.entity.AbstractEntityClass;
import com.cidades.base.exception.ServiceException;

/**
 * Interface que define os metodos padroes de um controlador de CRUD
 * 
 * @author ruan
 *
 * @param <PK> Chave Primaria
 * @param <T> Entidade de Origem
 * @param <DTO> DTO que representa a entidade em questão
 */
public interface AbstractController<PK extends Serializable, T extends AbstractEntityClass<PK>, DTO extends AbstractDTO<PK, T>> {

  /**
   * Retorna Filtra as informacoes com base nas informacoes advindas do filter
   * 
   * @param example Exemplo
   * @return Page encontrada
   * @throws ServiceException
   */
  Page<DTO> filter(Pageable pageable, DTO example, @RequestParam("search") String search) throws ServiceException;

  /**
   * Retorna uma instancia com o ID passado por parametro
   * 
   * @param id Id que queremos trabalhar
   * @return T com os valores encontrados
   */
  DTO get(PK id) throws ServiceException;

  /**
   * Salva um objeto
   * 
   * @param t T para salvarmos
   * @return Retorna o T salvo
   */
  DTO save(DTO dto) throws ServiceException;

  /**
   * Realiza o Update em um T
   * 
   * @param t T que queremos trabalhar
   * @return T atualizado
   */
  DTO update(DTO dto) throws ServiceException;
  
  /**
   * Retorna uma instancia com o ID passado por parametro e remove o mesmo
   * 
   * @param id Id que queremos trabalhar
   * @return T com os valores encontrados
   */
  void delete(PK id) throws ServiceException;

}
