package com.msampietro.springmultipleconnectionpools.repository;

import com.msampietro.springmultipleconnectionpools.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ExtendedJpaRepository<Review, Long> {

}
