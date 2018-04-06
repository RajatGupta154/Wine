package com.evozon.training.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestService {

    @Autowired
    private RestPersistence restPersistence;

    public List<Review> getReviews(Integer productId) {
        return restPersistence.getReviews(productId);
    }

    public void addReview(Review review) {
        restPersistence.addReview(review);
    }

    public void deleteReview(Integer reviewId) {
        restPersistence.deleteReview(reviewId);
    }

    public void updateReview(Review review) {
        restPersistence.updateReview(review);
    }
}
