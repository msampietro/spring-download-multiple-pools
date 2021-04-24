package com.msampietro.springmultipleconnectionpools.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ReviewProjection extends BaseProjection<Long> {

    String getComment();

    String getUsername();

    interface Full extends ReviewProjection {

        @JsonIgnore
        MovieProjection getMovie();

    }

}