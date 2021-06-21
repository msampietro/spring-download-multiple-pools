package com.msampietro.springdownloadmultiplepools.module.movie.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.actor.hal.ActorResource;
import com.msampietro.springdownloadmultiplepools.module.actor.projection.ActorProjection;
import com.msampietro.springdownloadmultiplepools.module.movie.controller.MovieController;
import com.msampietro.springdownloadmultiplepools.module.movie.projection.MovieProjection;
import com.msampietro.springdownloadmultiplepools.module.review.hal.ReviewResource;
import com.msampietro.springdownloadmultiplepools.module.review.projection.ReviewProjection;
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
        add(linkTo(methodOn(MovieController.class).getOne(movieProjection.getId())).withSelfRel());
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
