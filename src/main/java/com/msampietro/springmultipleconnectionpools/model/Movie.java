package com.msampietro.springmultipleconnectionpools.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer ranking;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "movie_actor",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

}