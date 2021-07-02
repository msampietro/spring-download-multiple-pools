package com.msampietro.springdownloadmultiplepools.module.base.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.misc.Utils;
import com.msampietro.springdownloadmultiplepools.module.base.dto.BaseDTO;
import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import com.msampietro.springdownloadmultiplepools.module.base.projection.BaseProjection;
import com.msampietro.springdownloadmultiplepools.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Getter
public class BaseServiceImpl<T extends BaseEntity<I>, I extends Serializable> implements BaseService<T, I> {

    @Autowired
    private ObjectMapper objectMapper;
    private final ExtendedJpaRepository<T, I> repository;
    private final Class<T> modelClazz;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl(ExtendedJpaRepository<T, I> repository) {
        this.repository = repository;
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), BaseServiceImpl.class);
        assert generics != null;
        this.modelClazz = (Class<T>) generics[0];
    }

    @Override
    public I create(BaseDTO input) {
        T entity = getObjectMapper().convertValue(input, modelClazz);
        return getRepository().save(entity).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException {
        Class<P> projectionClazz = Utils.getEntityProjectionClass(modelClazz);
        var projection = repository.findProjectedById(id, projectionClazz)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Object id %s not found", id.toString())));
        return Utils.projectionToResourceMapping(modelClazz, projection);
    }

}
