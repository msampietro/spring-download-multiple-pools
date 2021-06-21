package com.msampietro.springmultipleconnectionpools.module.movie.controller;

import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.base.controller.BaseControllerImpl;
import com.msampietro.springmultipleconnectionpools.module.movie.dto.MovieDTO;
import com.msampietro.springmultipleconnectionpools.module.movie.model.Movie;
import com.msampietro.springmultipleconnectionpools.module.movie.service.MovieExportService;
import com.msampietro.springmultipleconnectionpools.module.movie.service.MovieService;
import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.module.review.hal.ReviewResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/movies")
public class MovieController extends BaseControllerImpl<Movie, Long, MovieDTO> {

    private final MovieService movieService;
    private final MovieExportService movieExportService;

    @Autowired
    public MovieController(MovieService movieService,
                           MovieExportService movieExportService) {
        super(movieService);
        this.movieService = movieService;
        this.movieExportService = movieExportService;
    }

    @PutMapping(value = "{movieId}/reviews")
    public ResponseEntity<ReviewResource> addReviewToMovie(@PathVariable long movieId,
                                                           @Valid @RequestBody ReviewDTO reviewDTO) throws ObjectNotFoundException {
        ReviewResource result = movieService.addReviewToMovie(movieId, reviewDTO);
        return ResponseEntity.ok(result);
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
