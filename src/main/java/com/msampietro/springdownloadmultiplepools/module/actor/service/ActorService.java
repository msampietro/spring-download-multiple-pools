package com.msampietro.springdownloadmultiplepools.module.actor.service;

import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.actor.projection.ActorProjection;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService extends BaseService<Actor, Long> {

    Page<ActorProjection> getActors(Pageable pageable);

}
