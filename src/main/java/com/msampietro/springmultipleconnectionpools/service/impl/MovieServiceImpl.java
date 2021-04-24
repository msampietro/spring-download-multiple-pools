package com.msampietro.springmultipleconnectionpools.service.impl;

import com.msampietro.springmultipleconnectionpools.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.model.Movie;
import com.msampietro.springmultipleconnectionpools.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.repository.MovieRepository;
import com.msampietro.springmultipleconnectionpools.service.MovieService;
import com.msampietro.springmultipleconnectionpools.service.ReviewService;
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
                .orElseThrow(ObjectNotFoundException::new);
        return reviewService.addReview(foundMovie, reviewDTO);
    }

}
