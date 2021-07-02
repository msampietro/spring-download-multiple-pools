package com.msampietro.springdownloadmultiplepools.module.movie.service.impl;

import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseExportService;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.movie.service.MovieExportService;
import com.msampietro.springdownloadmultiplepools.module.review.model.Review;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieExportServiceImpl extends BaseExportService<Movie, Long> implements MovieExportService {

    @PostConstruct
    public void initialize() {
        super.initialize(getHeaderNames(), Sort.by("name"));
    }

    private String[] getHeaderNames() {
        var stringHeaders = new String[4];
        stringHeaders[0] = "Movie Name";
        stringHeaders[1] = "Movie Ranking";
        stringHeaders[2] = "Actors Count";
        stringHeaders[3] = "Reviews Count";
        return stringHeaders;
    }

    @Override
    public String[] toStringArray(Tuple tuple) {
        var stringData = new String[4];
        stringData[0] = tuple.get(0).toString();
        stringData[1] = tuple.get(1).toString();
        stringData[2] = tuple.get(2).toString();
        stringData[3] = tuple.get(3).toString();
        return stringData;
    }

    @Override
    public List<Selection<?>> buildSelections(Root<Movie> root, CriteriaQuery<Tuple> query, CriteriaBuilder builder) {
        var selections = new ArrayList<Selection<?>>();
        selections.add(root.get("name"));
        selections.add(root.get("ranking"));
        Join<Movie, Actor> actorJoin = root.join("actors", JoinType.LEFT);
        Join<Movie, Review> reviewJoin = root.join("reviews", JoinType.LEFT);
        query.groupBy(selectionsToExpressions(selections));
        selections.add(builder.count(actorJoin));
        selections.add(builder.count(reviewJoin));
        return selections;
    }
    
    public static List<Expression<?>> selectionsToExpressions(List<Selection<?>> selections) {
        return selections.stream()
                .map(s -> (Expression<?>) s)
                .collect(Collectors.toList());
    }

}
