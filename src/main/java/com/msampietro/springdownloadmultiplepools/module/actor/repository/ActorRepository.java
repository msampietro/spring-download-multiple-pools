package com.msampietro.springdownloadmultiplepools.module.actor.repository;

import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.actor.projection.ActorProjection;
import com.msampietro.springdownloadmultiplepools.module.base.repository.ExtendedJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends ExtendedJpaRepository<Actor, Long> {

    Page<ActorProjection> findAllPagedBy(Pageable page);

}
