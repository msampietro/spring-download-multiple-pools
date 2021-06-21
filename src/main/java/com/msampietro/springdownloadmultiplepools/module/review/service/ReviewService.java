package com.msampietro.springdownloadmultiplepools.module.review.service;


import com.msampietro.springdownloadmultiplepools.module.base.service.BaseService;
import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.review.dto.ReviewDTO;
import com.msampietro.springdownloadmultiplepools.module.review.hal.ReviewResource;
import com.msampietro.springdownloadmultiplepools.module.review.model.Review;

public interface ReviewService extends BaseService<Review, Long> {

    ReviewResource addReview(Movie movie, ReviewDTO reviewDTO) throws ObjectNotFoundException;

}
