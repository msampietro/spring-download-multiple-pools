package com.msampietro.springmultipleconnectionpools.module.review.service;


import com.msampietro.springmultipleconnectionpools.module.base.service.BaseService;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.module.movie.model.Movie;
import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.module.review.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.module.review.model.Review;

public interface ReviewService extends BaseService<Review, Long> {

    ReviewResource addReview(Movie movie, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
