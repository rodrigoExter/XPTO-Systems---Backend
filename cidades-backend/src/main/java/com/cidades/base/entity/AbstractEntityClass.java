package com.cidades.base.entity;

import java.io.Serializable;

public abstract class AbstractEntityClass<PK extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = -4188359917172477657L;

	public abstract PK getId();
	
}
