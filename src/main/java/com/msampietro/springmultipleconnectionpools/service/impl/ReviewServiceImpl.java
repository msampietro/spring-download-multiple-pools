package com.msampietro.springmultipleconnectionpools.service.impl;

import com.msampietro.springmultipleconnectionpools.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.exception.ObjectNotFoundException;
import com.msampietro.springmultipleconnectionpools.hal.ReviewResource;
import com.msampietro.springmultipleconnectionpools.model.Movie;
import com.msampietro.springmultipleconnectionpools.model.Review;
import com.msampietro.springmultipleconnectionpools.projection.ReviewProjection;
import com.msampietro.springmultipleconnectionpools.repository.ReviewRepository;
import com.msampietro.springmultipleconnectionpools.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SpelAwareProxyProjectionFactory projectionFactory;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        super(reviewRepository);
        this.reviewRepository = reviewRepository;
        this.projectionFactory = new SpelAwareProxyProjectionFactory();
    }

    @Override
    public ReviewResource addReview(Movie movie, ReviewDTO reviewDTO) throws ObjectNotFoundException {
        Review newReview = this.getObjectMapper().convertValue(reviewDTO, Review.class);
        newReview.setMovie(movie);
        Review result = reviewRepository.saveAndFlush(newReview);
        ReviewProjection projection = projectionFactory.createProjection(ReviewProjection.class, result);
        return new ReviewResource(projection);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public ReviewResource findById(Long id) throws ObjectNotFoundException {
        ReviewProjection.Full projection = reviewRepository.findProjectedById(id, ReviewProjection.Full.class)
                .orElseThrow(ObjectNotFoundException::new);
        return new ReviewResource(projection);
    }

}
