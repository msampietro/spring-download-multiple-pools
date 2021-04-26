package com.msampietro.springmultipleconnectionpools.module.base.repository;

import com.msampietro.springmultipleconnectionpools.module.base.model.BaseEntity;
import com.msampietro.springmultipleconnectionpools.module.base.projection.BaseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface ExtendedJpaRepository<T extends BaseEntity<I>, I extends Serializable> extends JpaRepository<T, I> {

    <P extends BaseProjection<I>> Optional<P> findProjectedById(I id, Class<P> projectionClazz);

}
