package com.cidades.base.rsql;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

public class CustomExampleSpecification<T> implements Specification<T> {

	private final Example<T> example;

	/**
	 * Creates new {@link ExampleSpecification}.
	 *
	 * @param example
	 */
	public CustomExampleSpecification(Example<T> example) {

		Assert.notNull(example, "Example must not be null!");
		this.example = example;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
	 */
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
	}
}