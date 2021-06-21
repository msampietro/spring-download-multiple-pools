package com.msampietro.springmultipleconnectionpools.module.base.controller;

import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.base.dto.BaseDTO;
import com.msampietro.springmultipleconnectionpools.module.base.model.BaseEntity;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface BaseController<T extends BaseEntity<I>, I extends Serializable, D extends BaseDTO> {

    ResponseEntity<Object> create(D input);

    ResponseEntity<Object> getOne(I objectId) throws ObjectNotFoundException;

}
