package com.cidades.base.dto;

import java.io.Serializable;

import com.cidades.base.entity.AbstractEntityClass;

public interface AbstractDTO<PK extends Serializable, T extends AbstractEntityClass<PK>> {

}
