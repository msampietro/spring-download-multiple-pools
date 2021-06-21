package com.msampietro.springdownloadmultiplepools.module.base.service;

import com.msampietro.springdownloadmultiplepools.module.base.dto.BaseDTO;
import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import com.msampietro.springdownloadmultiplepools.module.base.projection.BaseProjection;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import org.springframework.hateoas.EntityModel;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable> {

    I create(BaseDTO input);

    <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException;

}
