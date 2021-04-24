package com.msampietro.springmultipleconnectionpools.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface MovieProjection extends BaseProjection<Long> {

    String getName();

    Integer getRanking();

    @JsonIgnore
    List<ActorProjection> getActors();

    @JsonIgnore
    List<ReviewProjection> getReviews();

}