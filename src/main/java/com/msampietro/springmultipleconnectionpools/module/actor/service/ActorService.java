package com.msampietro.springmultipleconnectionpools.module.actor.service;

import com.msampietro.springmultipleconnectionpools.module.actor.model.Actor;
import com.msampietro.springmultipleconnectionpools.module.actor.projection.ActorProjection;
import com.msampietro.springmultipleconnectionpools.module.base.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService extends BaseService<Actor, Long> {

    Page<ActorProjection> getActors(Pageable pageable);

}
