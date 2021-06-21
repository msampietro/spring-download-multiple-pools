package com.msampietro.springdownloadmultiplepools.module.review.model;

import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review extends BaseEntity<Long> {

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

}
