package com.msampietro.springmultipleconnectionpools.repository;

import com.msampietro.springmultipleconnectionpools.model.Actor;
import com.msampietro.springmultipleconnectionpools.projection.ActorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends ExtendedJpaRepository<Actor, Long> {

    Page<ActorProjection> findAllPagedBy(Pageable page);

}
