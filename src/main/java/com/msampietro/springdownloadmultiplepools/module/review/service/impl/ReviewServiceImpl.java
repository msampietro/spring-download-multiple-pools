package com.msampietro.springdownloadmultiplepools.module.review.service.impl;

import com.msampietro.springdownloadmultiplepools.exception.ObjectNotFoundException;
import com.msampietro.springdownloadmultiplepools.module.base.service.impl.BaseServiceImpl;
import com.msampietro.springdownloadmultiplepools.module.movie.model.Movie;
import com.msampietro.springdownloadmultiplepools.module.review.dto.ReviewDTO;
import com.msampietro.springdownloadmultiplepools.module.review.hal.ReviewResource;
import com.msampietro.springdownloadmultiplepools.module.review.model.Review;
import com.msampietro.springdownloadmultiplepools.module.review.projection.ReviewProjection;
import com.msampietro.springdownloadmultiplepools.module.review.repository.ReviewRepository;
import com.msampietro.springdownloadmultiplepools.module.review.service.ReviewService;
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
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Review id %d not found", id)));
        return new ReviewResource(projection);
    }

}
