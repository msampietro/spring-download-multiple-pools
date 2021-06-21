package com.msampietro.springdownloadmultiplepools.module.actor.service.impl;

import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.actor.projection.ActorProjection;
import com.msampietro.springdownloadmultiplepools.module.actor.repository.ActorRepository;
import com.msampietro.springdownloadmultiplepools.module.actor.service.ActorService;
import com.msampietro.springdownloadmultiplepools.module.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl extends BaseServiceImpl<Actor, Long> implements ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        super(actorRepository);
        this.actorRepository = actorRepository;
    }

    public Page<ActorProjection> getActors(Pageable pageable) {
        return this.actorRepository.findAllPagedBy(pageable);
    }

}

