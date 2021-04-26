package com.msampietro.springmultipleconnectionpools.module.movie.service;

import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.review.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.module.movie.model.Movie;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.module.base.service.BaseService;

import java.util.List;

public interface MovieService extends BaseService<Movie, Long> {

    List<MovieProjection> getAllMovies();

    ReviewResource addReviewToMovie(long movieId, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
