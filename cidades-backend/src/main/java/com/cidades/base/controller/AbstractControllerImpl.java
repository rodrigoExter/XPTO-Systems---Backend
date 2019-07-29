package com.cidades.base.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cidades.base.dto.AbstractDTO;
import com.cidades.base.dto.DTOUtils;
import com.cidades.base.entity.AbstractEntityClass;
import com.cidades.base.exception.ServiceException;
import com.cidades.base.message.DefaultMessages;
import com.cidades.base.service.AbstractService;
import com.cidades.base.service.MessageService;

/**
 * Controller responsáveç por fazer a linkagem de um DTO para um tipo especifico
 * 
 * @author ruan
 *
 * @param <PK>
 *            Chave Primaria
 * @param <T>
 *            Tipo da Entidade
 * @param <DTO>
 *            DTO da Entidade que queremos trabalhar
 */
public abstract class AbstractControllerImpl<PK extends Serializable, T extends AbstractEntityClass<PK>, DTO extends AbstractDTO<PK, T>>
		implements AbstractController<PK, T, DTO> {

	private final Class<T> entityClass;

	private final Class<DTO> dtoClass;

	@Autowired
	private AbstractService<PK, T> service;

	@Autowired
	private MessageService messageService;

	/**
	 * Construtor da Classe
	 *
	 * @param entityClass
	 * @param dtoClass
	 */
	@SuppressWarnings("unchecked")
	public AbstractControllerImpl() {
		super();
		Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		this.entityClass = (Class<T>) types[1];
		this.dtoClass = (Class<DTO>) types[2];
	}

	@Override
	@GetMapping
	public Page<DTO> filter(final Pageable pageable, final DTO example, @RequestParam(value = "search", required = false) String search) throws ServiceException {
		T entityExample = null;
		if (example != null) {
			entityExample = DTOUtils.convertToEntity(example, this.entityClass);
		}
		final Page<T> filter = this.service.filter(entityExample, pageable, search);
		final Page<DTO> page = filter.map(e -> DTOUtils.convertToDTO(e, this.dtoClass));
		return page;
	}

	@Override
	@GetMapping(value = "/{id}")
	public DTO get(@NotNull @PathVariable("id") final PK id) throws ServiceException {
		final Optional<T> t = this.service.get(id);
		if (!t.isPresent()) {
			throw new EmptyResultDataAccessException(
					messageService.getMessage(DefaultMessages.ID_NOT_FIND.toString(), id.toString()), 1);
		}
		return DTOUtils.convertToDTO(t.get(), this.dtoClass);
	}

	@Override
	@PostMapping
	public DTO save(@Valid @NotNull @RequestBody final DTO dto) throws ServiceException {
		final T t = DTOUtils.convertToEntity(dto, this.entityClass);
		final T entitySaved = this.service.save(t);
		return DTOUtils.convertToDTO(entitySaved, this.dtoClass);
	}

	@Override
	@PutMapping
	public DTO update(@Valid @NotNull @RequestBody final DTO dto) throws ServiceException {
		final T t = DTOUtils.convertToEntity(dto, this.entityClass);
		final T entitySaved = this.service.update(t);
		return DTOUtils.convertToDTO(entitySaved, this.dtoClass);
	}

	@Override
	@DeleteMapping(value = "/{id}")
	public void delete(@NotNull @PathVariable("id") final PK id) throws ServiceException {
		try {
			this.service.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException(
					messageService.getMessage(DefaultMessages.ID_NOT_FIND.toString(), id.toString()), 1);

		}
	}
}