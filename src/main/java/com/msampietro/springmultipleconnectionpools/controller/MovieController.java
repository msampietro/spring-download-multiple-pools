package com.msampietro.springmultipleconnectionpools.controller;

import com.msampietro.springmultipleconnectionpools.dto.MovieDTO;
import com.msampietro.springmultipleconnectionpools.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.projection.ReviewProjection;
import com.msampietro.springmultipleconnectionpools.service.MovieService;
import com.msampietro.springmultipleconnectionpools.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    @Autowired
    public MovieController(MovieService movieService,
                           ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Object> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        long id = movieService.create(movieDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/movies/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{movieId}/reviews")
    public ResponseEntity<ReviewResource> addReviewToMovie(@PathVariable long movieId,
                                                           @Valid @RequestBody ReviewDTO reviewDTO) throws ObjectNotFoundException {
        ReviewResource result = movieService.addReviewToMovie(movieId, reviewDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/reviews/{reviewId}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<ReviewProjection>> getReview(@PathVariable long reviewId) throws ObjectNotFoundException {
        return ResponseEntity.ok(reviewService.findById(reviewId));
    }

    @GetMapping(value = "/{movieId}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<MovieProjection>> getMovie(@PathVariable long movieId) throws ObjectNotFoundException {
        return ResponseEntity.ok(movieService.findById(movieId));
    }

}
