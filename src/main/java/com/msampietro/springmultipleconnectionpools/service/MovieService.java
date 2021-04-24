package com.msampietro.springmultipleconnectionpools.service;

import com.msampietro.springmultipleconnectionpools.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.model.Movie;
import com.msampietro.springmultipleconnectionpools.projection.MovieProjection;

import java.util.List;

public interface MovieService extends BaseService<Movie, Long> {

    List<MovieProjection> getAllMovies();

    ReviewResource addReviewToMovie(long movieId, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
