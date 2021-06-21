package com.msampietro.springdownloadmultiplepools.module.review.repository;

import com.msampietro.springdownloadmultiplepools.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springdownloadmultiplepools.module.review.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ExtendedJpaRepository<Review, Long> {

}
