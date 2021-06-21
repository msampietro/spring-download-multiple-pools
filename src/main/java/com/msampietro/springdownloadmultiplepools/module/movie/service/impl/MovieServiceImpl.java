package com.msampietro.springdownloadmultiplepools.module.movie.service.impl;

import com.msampietro.springdownloadmultiplepools.module.review.dto.ReviewDTO;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.review.hal.ReviewResource;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.movie.projection.MovieProjection;
import com.msampietro.springdownloadmultiplepools.module.movie.repository.MovieRepository;
import com.msampietro.springdownloadmultiplepools.module.movie.service.MovieService;
import com.msampietro.springdownloadmultiplepools.module.review.service.ReviewService;
import com.msampietro.springdownloadmultiplepools.module.base.service.impl.BaseServiceImpl;
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
        var foundMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Movie id %d not found", movieId)));
        return reviewService.addReview(foundMovie, reviewDTO);
    }

}
