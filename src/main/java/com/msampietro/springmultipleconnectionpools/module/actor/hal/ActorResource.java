package com.msampietro.springmultipleconnectionpools.module.actor.hal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.actor.controller.ActorController;
import com.msampietro.springmultipleconnectionpools.module.actor.projection.ActorProjection;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ActorResource extends EntityModel<ActorProjection> {

    private final ActorProjection actorProjection;

    public ActorResource(ActorProjection actorProjection) throws ObjectNotFoundException {
        this.actorProjection = actorProjection;
        add(linkTo(methodOn(ActorController.class).getOne(actorProjection.getId())).withSelfRel());
    }

    @JsonUnwrapped
    public ActorProjection getActorProjection() {
        return this.actorProjection;
    }

}
