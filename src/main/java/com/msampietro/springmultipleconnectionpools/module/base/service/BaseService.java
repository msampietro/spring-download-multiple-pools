package com.msampietro.springmultipleconnectionpools.module.base.service;

import com.msampietro.springmultipleconnectionpools.module.base.dto.BaseDTO;
import com.msampietro.springmultipleconnectionpools.module.base.model.BaseEntity;
import com.msampietro.springmultipleconnectionpools.module.base.projection.BaseProjection;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import org.springframework.hateoas.EntityModel;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable> {

    I create(BaseDTO input);

    <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException;

}
