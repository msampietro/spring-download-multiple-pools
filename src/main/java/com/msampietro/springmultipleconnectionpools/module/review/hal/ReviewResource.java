package com.msampietro.springmultipleconnectionpools.module.review.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.movie.controller.MovieController;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.module.review.controller.ReviewController;
import com.msampietro.springmultipleconnectionpools.module.review.projection.ReviewProjection;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ReviewResource extends EntityModel<ReviewProjection> {

    private final ReviewProjection reviewProjection;
    private EntityModel<MovieProjection> movieResource;

    public ReviewResource(ReviewProjection reviewProjection) throws ObjectNotFoundException {
        this.reviewProjection = reviewProjection;
        add(linkTo(methodOn(ReviewController.class).getOne(reviewProjection.getId())).withSelfRel());
    }

    public ReviewResource(ReviewProjection.Full fullReviewProjection) throws ObjectNotFoundException {
        this.reviewProjection = fullReviewProjection;
        add(linkTo(methodOn(ReviewController.class).getOne(reviewProjection.getId())).withSelfRel());
        movieResource = EntityModel.of(fullReviewProjection.getMovie());
        movieResource.add(linkTo(methodOn(MovieController.class).getOne(fullReviewProjection.getMovie().getId())).withSelfRel());
    }

    @JsonUnwrapped
    public ReviewProjection getReviewProjection() {
        return this.reviewProjection;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("movie")
    public EntityModel<MovieProjection> getMovieResource() {
        return this.movieResource;
    }

}
