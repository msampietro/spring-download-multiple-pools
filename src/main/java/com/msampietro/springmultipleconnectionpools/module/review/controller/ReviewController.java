package com.msampietro.springmultipleconnectionpools.module.review.controller;

import com.msampietro.springmultipleconnectionpools.module.base.controller.BaseControllerImpl;
import com.msampietro.springmultipleconnectionpools.module.review.dto.ReviewDTO;
import com.msampietro.springmultipleconnectionpools.module.review.model.Review;
import com.msampietro.springmultipleconnectionpools.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController extends BaseControllerImpl<Review, Long, ReviewDTO> {

    @Autowired
    public ReviewController(ReviewService reviewService) {
        super(reviewService);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ReviewDTO input) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
