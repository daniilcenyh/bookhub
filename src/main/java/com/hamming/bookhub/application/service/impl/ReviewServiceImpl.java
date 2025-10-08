package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.reviews.ReviewSearchFilter;
import com.hamming.bookhub.application.service.ReviewService;
import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    /**
     * @param request
     * @return
     */
    @Override
    public ReviewResponse addReview(CreateNewReviewForBookRequest request) {
        return null;
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<ReviewResponse> getReviewsByBook(ReviewSearchFilter filter) {
        return List.of();
    }

    /**
     * @param reviewId
     */
    @Override
    public void deleteReview(UUID reviewId) {

    }
}
