package com.msampietro.springmultipleconnectionpools.service;

import com.msampietro.springmultipleconnectionpools.model.Actor;
import com.msampietro.springmultipleconnectionpools.projection.ActorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService extends BaseService<Actor, Long> {

    Page<ActorProjection> getActors(Pageable pageable);

}
