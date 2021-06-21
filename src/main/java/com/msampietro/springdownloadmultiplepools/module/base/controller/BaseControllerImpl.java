package com.msampietro.springdownloadmultiplepools.module.base.controller;

import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.base.dto.BaseDTO;
import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.Serializable;

public abstract class BaseControllerImpl<T extends BaseEntity<I>, I extends Serializable, D extends BaseDTO> implements BaseController<T, I, D> {

    private final BaseService<T, I> baseService;

    protected BaseControllerImpl(BaseService<T, I> baseService) {
        this.baseService = baseService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody D input) {
        var id = baseService.create(input);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping(value = "/{objectId}", produces = {"application/hal+json"})
    public ResponseEntity<Object> getOne(@PathVariable I objectId) throws ObjectNotFoundException {
        return ResponseEntity.ok(baseService.findById(objectId));
    }

}
