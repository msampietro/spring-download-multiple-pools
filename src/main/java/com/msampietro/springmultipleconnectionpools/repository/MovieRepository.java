package com.msampietro.springmultipleconnectionpools.repository;

import com.msampietro.springmultipleconnectionpools.model.Movie;
import com.msampietro.springmultipleconnectionpools.projection.MovieProjection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends ExtendedJpaRepository<Movie, Long> {

    List<MovieProjection> findAllProjectedBy();

}
