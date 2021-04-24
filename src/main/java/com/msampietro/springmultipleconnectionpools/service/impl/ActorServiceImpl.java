package com.msampietro.springmultipleconnectionpools.service.impl;

import com.msampietro.springmultipleconnectionpools.model.Actor;
import com.msampietro.springmultipleconnectionpools.projection.ActorProjection;
import com.msampietro.springmultipleconnectionpools.repository.ActorRepository;
import com.msampietro.springmultipleconnectionpools.service.ActorService;
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

