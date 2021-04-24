package com.msampietro.springmultipleconnectionpools.service;


import com.msampietro.springmultipleconnectionpools.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.model.Movie;
import com.msampietro.springmultipleconnectionpools.model.Review;

public interface ReviewService extends BaseService<Review, Long> {

    ReviewResource addReview(Movie movie, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
