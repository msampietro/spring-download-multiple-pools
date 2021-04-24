package com.msampietro.springmultipleconnectionpools.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springmultipleconnectionpools.controller.MovieController;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.projection.ActorProjection;
import com.msampietro.springmultipleconnectionpools.projection.MovieProjection;
import com.msampietro.springmultipleconnectionpools.projection.ReviewProjection;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class MovieResource extends EntityModel<MovieProjection> {

    private final MovieProjection movieProjection;
    private final Map<String, Object> embedded = new HashMap<>();

    public MovieResource(MovieProjection movieProjection) throws ObjectNotFoundException {
        this.movieProjection = movieProjection;
        List<ActorResource> actorResources = new ArrayList<>();
        List<ReviewResource> reviewResources = new ArrayList<>();
        add(linkTo(methodOn(MovieController.class).getMovie(movieProjection.getId())).withSelfRel());
        for (ActorProjection actorProjection : movieProjection.getActors())
            actorResources.add(new ActorResource(actorProjection));
        for (ReviewProjection reviewProjection : movieProjection.getReviews())
            reviewResources.add(new ReviewResource(reviewProjection));
        embedded.put("actors", actorResources);
        embedded.put("reviews", reviewResources);
    }

    @JsonProperty("_embedded")
    public Map<String, Object> getEmbedded() {
        return this.embedded;
    }

    @JsonUnwrapped
    public MovieProjection getMovieProjection() {
        return this.movieProjection;
    }

}
