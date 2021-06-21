package com.msampietro.springdownloadmultiplepools.module.base.service;

import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

public interface ExportService<T extends BaseEntity<I>, I extends Serializable> {

    void exportStreamToCsv(OutputStream outputStream);

    String[] toStringArray(Tuple tuple);

    List<Selection<?>> buildSelections(Root<T> root, CriteriaQuery<Tuple> query, CriteriaBuilder builder);

}
