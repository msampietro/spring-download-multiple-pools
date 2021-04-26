package com.msampietro.springmultipleconnectionpools.module.review.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springmultipleconnectionpools.module.base.projection.BaseProjection;
import com.msampietro.springmultipleconnectionpools.module.movie.projection.MovieProjection;

public interface ReviewProjection extends BaseProjection<Long> {

    String getComment();

    String getUsername();

    interface Full extends ReviewProjection {

        @JsonIgnore
        MovieProjection getMovie();

    }

}