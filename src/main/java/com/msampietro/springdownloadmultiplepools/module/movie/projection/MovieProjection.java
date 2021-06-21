package com.msampietro.springdownloadmultiplepools.module.movie.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springdownloadmultiplepools.module.actor.projection.ActorProjection;
import com.msampietro.springdownloadmultiplepools.module.base.projection.BaseProjection;
import com.msampietro.springdownloadmultiplepools.module.review.projection.ReviewProjection;

import java.util.List;

public interface MovieProjection extends BaseProjection<Long> {

    String getName();

    Integer getRanking();

    @JsonIgnore
    List<ActorProjection> getActors();

    @JsonIgnore
    List<ReviewProjection> getReviews();

}