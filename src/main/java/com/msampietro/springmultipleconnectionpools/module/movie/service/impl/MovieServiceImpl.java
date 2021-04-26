package com.msampietro.springmultipleconnectionpools.module.movie.service.impl;

import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.review.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.module.movie.model.Movie;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.module.movie.repository.MovieRepository;
import com.msampietro.springmultipleconnectionpools.module.movie.service.MovieService;
import com.msampietro.springmultipleconnectionpools.module.review.service.ReviewService;
import com.msampietro.springmultipleconnectionpools.module.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl extends BaseServiceImpl<Movie, Long> implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewService reviewService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository,
                            ReviewService reviewService) {
        super(movieRepository);
        this.movieRepository = movieRepository;
        this.reviewService = reviewService;
    }

    @Override
    public List<MovieProjection> getAllMovies() {
        return movieRepository.findAllProjectedBy();
    }

    @Transactional
    @Override
    public ReviewResource addReviewToMovie(long movieId, ReviewDTO reviewDTO) throws ObjectNotFoundException {
        Movie foundMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Movie id %d not found", movieId)));
        return reviewService.addReview(foundMovie, reviewDTO);
    }

}
