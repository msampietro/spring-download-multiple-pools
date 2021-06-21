package com.msampietro.springdownloadmultiplepools.module.movie.service;

import com.msampietro.springdownloadmultiplepools.module.review.dto.ReviewDTO;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.review.hal.ReviewResource;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.movie.projection.MovieProjection;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseService;

import java.util.List;

public interface MovieService extends BaseService<Movie, Long> {

    List<MovieProjection> getAllMovies();

    ReviewResource addReviewToMovie(long movieId, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
