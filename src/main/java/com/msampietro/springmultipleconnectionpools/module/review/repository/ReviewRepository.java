package com.msampietro.springmultipleconnectionpools.module.review.repository;

import com.msampietro.springmultipleconnectionpools.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springmultipleconnectionpools.module.review.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ExtendedJpaRepository<Review, Long> {

}
