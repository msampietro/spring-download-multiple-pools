package com.msampietro.springmultipleconnectionpools.module.actor.controller;

import com.msampietro.springmultipleconnectionpools.module.actor.projection.ActorProjection;
import com.msampietro.springmultipleconnectionpools.module.actor.service.ActorService;
import com.msampietro.springmultipleconnectionpools.module.actor.dto.ActorDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<Object> createActors(@Valid @RequestBody ActorDTO actorDTO) {
        long id = actorService.create(actorDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/actors/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{actorId}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<ActorProjection>> getActor(@PathVariable long actorId) throws ObjectNotFoundException {
        return ResponseEntity.ok(actorService.findById(actorId));
    }

}
