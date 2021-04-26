package com.msampietro.springmultipleconnectionpools.module.movie.repository;

import com.msampietro.springmultipleconnectionpools.module.movie.model.Movie;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.module.base.repository.ExtendedJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends ExtendedJpaRepository<Movie, Long> {

    List<MovieProjection> findAllProjectedBy();

}
