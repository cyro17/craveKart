package com.cyro.craveKart.service;

import java.util.List;

import com.cyro.craveKart.exceptions.ReviewException;
import com.cyro.craveKart.model.Review;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.ReviewRequest;

public interface ReviewSerive {
	
    public Review submitReview(ReviewRequest review,User user);
    public void deleteReview(Long reviewId) throws ReviewException;
    public double calculateAverageRating(List<Review> reviews);
}
