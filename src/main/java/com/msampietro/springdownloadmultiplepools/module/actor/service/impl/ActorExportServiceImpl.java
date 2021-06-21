package com.msampietro.springdownloadmultiplepools.module.actor.service.impl;

import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.actor.service.ActorExportService;
import com.msampietro.springdownloadmultiplepools.module.base.service.BaseExportService;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorExportServiceImpl extends BaseExportService<Actor, Long> implements ActorExportService {

    @PostConstruct
    public void initialize() {
        super.initialize(getHeaderNames(), Sort.by("lastName"));
    }

    private String[] getHeaderNames() {
        var stringHeaders = new String[4];
        stringHeaders[0] = "Lastname";
        stringHeaders[1] = "Name";
        stringHeaders[2] = "Movies Count";
        return stringHeaders;
    }

    @Override
    public String[] toStringArray(Tuple tuple) {
        var stringData = new String[3];
        stringData[0] = tuple.get(0).toString();
        stringData[1] = tuple.get(1).toString();
        stringData[2] = tuple.get(2).toString();
        return stringData;
    }

    @Override
    public List<Selection<?>> buildSelections(Root<Actor> root, CriteriaQuery<Tuple> query, CriteriaBuilder builder) {
        List<Selection<?>> selections = new ArrayList<>();
        selections.add(root.get("lastName"));
        selections.add(root.get("name"));
        Join<Actor, Movie> moviesJoin = root.join("movies", JoinType.LEFT);
        query.groupBy(selectionsToExpressions(selections));
        selections.add(builder.count(moviesJoin));
        return selections;
    }

    public static List<Expression<?>> selectionsToExpressions(List<Selection<?>> selections) {
        return selections.stream()
                .map(s -> (Expression<?>) s)
                .collect(Collectors.toList());
    }
}
