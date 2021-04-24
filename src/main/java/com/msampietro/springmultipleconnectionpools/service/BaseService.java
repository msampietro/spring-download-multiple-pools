package com.msampietro.springmultipleconnectionpools.service;

import com.msampietro.springmultipleconnectionpools.dto.BaseDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.model.BaseEntity;
import com.msampietro.springmultipleconnectionpools.projection.BaseProjection;
import org.springframework.hateoas.EntityModel;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable> {

    I create(BaseDTO input);

    <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException;

}
