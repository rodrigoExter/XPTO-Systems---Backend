package com.cidades.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.cidades.base.entity.AbstractEntityClass;
import com.cidades.base.exception.ServiceException;
import com.cidades.base.rsql.CustomExampleSpecification;
import com.cidades.base.rsql.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

/**
 * Classe responsavel por delimitar a implementacao da camada de Servico em
 * forma de CRUD
 * 
 * @author ruan
 *
 * @param <PK>
 *            Tipo da PK
 * @param <T>
 *            Tipo que queremos lidar
 * @param <F>
 *            Filtro
 */
public abstract class AbstractServiceImpl<PK extends Serializable, T extends AbstractEntityClass<PK>>
		implements AbstractService<PK, T> {

	@Autowired
	private JpaRepository<T, PK> repository;

	@Override
	@SuppressWarnings("unchecked")
	public Page<T> filter(final T example, final Pageable request, final String search) throws ServiceException {
		Pageable finalRequest = request;
		if (request == null) {
			finalRequest = new PageRequest(0, 10);
		}

		Example<T> entityExample = null;
		if (example != null) {
			entityExample = Example.of(example,
					ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase());
		}

		if (StringUtils.isNotBlank(search)) {
			if (this.repository instanceof JpaSpecificationExecutor) {
				Node rootNode = new RSQLParser().parse(search);
				Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>(
						entityExample != null ? new CustomExampleSpecification<T>(entityExample) : null));
				return ((JpaSpecificationExecutor<T>) this.repository).findAll(spec, request);
			} else {
				throw new ServiceException("Erro ao reliazar a consulta.", ArrayUtils.toPrimitive("A classe \""
						+ this.repository.getClass().getSimpleName()
						+ "\" deve implementar \"JpaSpecificationExecutor\" para poder utilizar o a funcionalidade \"search\"."));
			}
		}

		if (example != null) {
			return this.repository.findAll(entityExample, finalRequest);
		}
		return this.repository.findAll(finalRequest);
	}

	@Override
	public List<T> filter(final T example) {
		Example<T> of = Example.of(example,
				ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase());
		return this.repository.findAll(of);
	}

	@Override
	public Optional<T> get(@NotNull final PK id) {
		final T t = this.repository.findOne(id);
		if (t != null) {
			return Optional.of(t);
		}
		return Optional.empty();
	}

	protected FluentValidator beforeSaveValidate(T t, FluentValidator checkAll) {
		return checkAll;
	}

	@Override
	@Transactional
	public T save(@Valid @NotNull final T t) throws ServiceException {
		FluentValidator beforeSaveValidate = beforeSaveValidate(t, FluentValidator.checkAll());
		Result result = beforeSaveValidate.doValidate().result(ResultCollectors.toSimple());
		T save = null;
		if (result.isSuccess()) {
			save = this.repository.save(t);
		} else {
			throw new ServiceException("Erro ao salvar o registro!", result.getErrors());
		}

		return save;
	}

	protected FluentValidator beforeUpdateValidate(T t, FluentValidator checkAll) {
		return checkAll;
	}

	@Override
	public T update(@Valid @NotNull final T t) throws ServiceException {
		FluentValidator beforeSaveValidate = beforeUpdateValidate(t, FluentValidator.checkAll());
		Result result = beforeSaveValidate.doValidate().result(ResultCollectors.toSimple());
		T save = null;
		if (result.isSuccess()) {
			save = this.repository.save(t);
		} else {
			throw new ServiceException("Erro ao salvar o registro!", result.getErrors());
		}

		return save;
	}

	protected FluentValidator beforeDeleteValidate(PK id, FluentValidator checkAll) {
		return checkAll;
	}

	@Override
	public void delete(@NotNull final PK id) throws ServiceException {
		FluentValidator beforeDeleteValidate = beforeDeleteValidate(id, FluentValidator.checkAll());
		Result result = beforeDeleteValidate.doValidate().result(ResultCollectors.toSimple());
		if (result.isSuccess()) {
			this.repository.delete(id);
		} else {
			throw new ServiceException("Erro ao deletar o registro!", result.getErrors());
		}
	}
}