package com.msampietro.springmultipleconnectionpools.module.movie.controller;

import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.movie.dto.MovieDTO;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.module.movie.service.MovieExportService;
import com.msampietro.springmultipleconnectionpools.module.movie.service.MovieService;
import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.module.review.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.module.review.projection.ReviewProjection;
import com.msampietro.springmultipleconnectionpools.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final MovieExportService movieExportService;

    @Autowired
    public MovieController(MovieService movieService,
                           ReviewService reviewService,
                           MovieExportService movieExportService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.movieExportService = movieExportService;
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

    @GetMapping(value = "/data")
    public ResponseEntity<StreamingResponseBody> getData(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movies_export.csv");
        StreamingResponseBody stream = movieExportService::exportStreamToCsv;
        return ResponseEntity.ok(stream);
    }

}
