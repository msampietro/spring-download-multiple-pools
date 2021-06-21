package com.msampietro.springdownloadmultiplepools.module.actor.model;

import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "actor")
@Getter
@Setter
public class Actor extends BaseEntity<Long> {

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

}
