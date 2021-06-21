package com.msampietro.springdownloadmultiplepools.module.review.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springdownloadmultiplepools.module.base.projection.BaseProjection;
import com.msampietro.springdownloadmultiplepools.module.movie.projection.MovieProjection;

public interface ReviewProjection extends BaseProjection<Long> {

    String getComment();

    String getUsername();

    interface Full extends ReviewProjection {

        @JsonIgnore
        MovieProjection getMovie();

    }

}