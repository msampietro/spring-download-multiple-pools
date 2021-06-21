package com.msampietro.springdownloadmultiplepools.module.base.controller;

import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.base.dto.BaseDTO;
import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface BaseController<T extends BaseEntity<I>, I extends Serializable, D extends BaseDTO> {

    ResponseEntity<Object> create(D input);

    ResponseEntity<Object> getOne(I objectId) throws ObjectNotFoundException;

}
