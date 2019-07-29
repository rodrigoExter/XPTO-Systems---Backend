package com.cidades.base.rsql;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

	private GenericRsqlSpecBuilder<T> builder;
	private Specification<T> specification;

	public CustomRsqlVisitor() {
		builder = new GenericRsqlSpecBuilder<T>();
	}

	public CustomRsqlVisitor(Specification<T> specification) {
		this();
		this.specification = specification;
	}

	@Override
	public Specification<T> visit(final AndNode node, final Void param) {
		Specifications<T> createSpecification = builder.createSpecification(node);
		if (specification != null) {
			return createSpecification.and(specification);
		}
		return createSpecification;
	}

	@Override
	public Specification<T> visit(final OrNode node, final Void param) {
		Specifications<T> createSpecification = builder.createSpecification(node);
		if (specification != null) {
			return createSpecification.and(specification);
		}
		return createSpecification;
	}

	@Override
	public Specification<T> visit(final ComparisonNode node, final Void params) {
		Specifications<T> createSpecification = builder.createSpecification(node);
		if (specification != null) {
			return createSpecification.and(specification);
		}
		return createSpecification;
	}
}