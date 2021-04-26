package com.msampietro.springmultipleconnectionpools.module.actor.repository;

import com.msampietro.springmultipleconnectionpools.module.actor.model.Actor;
import com.msampietro.springmultipleconnectionpools.module.actor.projection.ActorProjection;
import com.msampietro.springmultipleconnectionpools.module.base.repository.ExtendedJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends ExtendedJpaRepository<Actor, Long> {

    Page<ActorProjection> findAllPagedBy(Pageable page);

}
