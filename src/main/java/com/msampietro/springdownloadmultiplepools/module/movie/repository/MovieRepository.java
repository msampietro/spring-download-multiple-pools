package com.msampietro.springdownloadmultiplepools.module.movie.repository;

import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.movie.projection.MovieProjection;
import com.msampietro.springdownloadmultiplepools.module.base.repository.ExtendedJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends ExtendedJpaRepository<Movie, Long> {

    List<MovieProjection> findAllProjectedBy();

}
