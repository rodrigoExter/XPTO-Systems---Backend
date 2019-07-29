package com.cidades.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cidades.base.entity.AbstractEntityClass;
import com.cidades.base.exception.ServiceException;

public interface AbstractService<PK extends Serializable, T extends AbstractEntityClass<PK>>{
	
	Page<T> filter(final T example, final Pageable request, final String search) throws ServiceException;
	
	List<T> filter(final T example);
	
	Optional<T> get(@NotNull PK id);
	
	T save(@Valid @NotNull final T t) throws ServiceException;
	
	T update(@Valid @NotNull T t) throws ServiceException;
	
	void delete(@NotNull final PK id) throws ServiceException;

}
