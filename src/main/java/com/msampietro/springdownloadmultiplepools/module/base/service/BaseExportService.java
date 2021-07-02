package com.msampietro.springdownloadmultiplepools.module.base.service;

import com.msampietro.springdownloadmultiplepools.misc.CSVWriterWrapper;
import com.msampietro.springdownloadmultiplepools.module.base.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.*;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

@Slf4j
public abstract class BaseExportService<T extends BaseEntity<I>, I extends Serializable> implements ExportService<T, I> {

    private final Class<T> modelType;
    @Autowired
    private HikariDataSourcePoolMetadata processingDataSourcePoolMetadata;
    @PersistenceContext(unitName = "processing")
    private EntityManager processingEntityManager;
    private String[] headerNames;
    private Sort sort;

    @SuppressWarnings("unchecked")
    protected BaseExportService() {
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), BaseExportService.class);
        assert generics != null;
        this.modelType = (Class<T>) generics[0];
    }

    protected void initialize(String[] headerNames, Sort sort) {
        this.headerNames = headerNames;
        this.sort = sort;
    }

    @Transactional(value = "processingTransactionManager", propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void exportStreamToCsv(OutputStream outputStream) {
        log.info("({}) - HikariPool-Processing Idle Connections before exportStreamToCsv: {}", this.getClass(),
                this.getProcessingDataSourcePoolMetadata().getIdle());
        var start = Instant.now();
        boolean autoCommit = getCurrentSessionAutoCommitPropertyAndSetFalse();
        boolean readOnly = getCurrentSessionReadOnlyPropertyAndSetTrue();
        TypedQuery<Tuple> typedQuery = buildTypedQuery();
        try (var csvWriter = new CSVWriterWrapper(outputStream);
             Stream<Tuple> streamData = typedQuery.getResultStream()) {
            log.info("({}) - HikariPool-Processing Idle Connections during exportStreamToCsv: {}", this.getClass(),
                    this.getProcessingDataSourcePoolMetadata().getIdle());
            csvWriter.writeNext(headerNames);
            streamData.forEach(d -> csvWriter.writeNext(this.toStringArray(d)));
            csvWriter.flush();
        }
        restoreAutoCommitAndReadOnly(autoCommit, readOnly);
        var end = Instant.now();
        log.info("({}) - Download processed in {} seconds", this.getClass(), Duration.between(start, end).toSeconds());
    }

    private boolean getCurrentSessionAutoCommitPropertyAndSetFalse() {
        var session = this.getProcessingEntityManager().unwrap(Session.class);
        boolean currentAutoCommit = session.doReturningWork(Connection::getAutoCommit);
        session.doWork(connection -> connection.setAutoCommit(false));
        return currentAutoCommit;
    }

    private boolean getCurrentSessionReadOnlyPropertyAndSetTrue() {
        var session = this.getProcessingEntityManager().unwrap(Session.class);
        boolean currentReadOnly = session.doReturningWork(Connection::isReadOnly);
        session.doWork(connection -> connection.setReadOnly(true));
        return currentReadOnly;
    }

    private TypedQuery<Tuple> buildTypedQuery() {
        var builder = this.getProcessingEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<T> root = query.from(modelType);
        query.multiselect(this.buildSelections(root, query, builder));
        if (this.getSort() != null && this.getSort().isSorted())
            query.orderBy(toOrders(sort, root, builder));
        TypedQuery<Tuple> typedQuery = this.getProcessingEntityManager().createQuery(query);
        Map<String, Object> queryHints = buildQueryHints();
        queryHints.forEach(typedQuery::setHint);
        return typedQuery;
    }

    /**
     * HINT_FETCH_SIZE ignored if autoCommit = true.
     * If pool autoCommit is true then it should be disable for method execution
     **/
    private Map<String, Object> buildQueryHints() {
        Map<String, Object> queryHints = new HashMap<>();
        queryHints.put(HINT_FETCH_SIZE, "5000");
        queryHints.put(HINT_READONLY, "true");
        queryHints.put(HINT_CACHEABLE, "true");
        return queryHints;
    }

    private void restoreAutoCommitAndReadOnly(boolean autoCommit, boolean readOnly) {
        var session = this.getProcessingEntityManager().unwrap(Session.class);
        session.doWork(connection -> {
            connection.setAutoCommit(autoCommit);
            connection.setReadOnly(readOnly);
        });
    }

    public EntityManager getProcessingEntityManager() {
        return this.processingEntityManager;
    }

    public HikariDataSourcePoolMetadata getProcessingDataSourcePoolMetadata() {
        return this.processingDataSourcePoolMetadata;
    }

    public Sort getSort() {
        return this.sort;
    }

}
