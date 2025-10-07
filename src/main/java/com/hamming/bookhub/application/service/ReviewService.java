package com.hamming.bookhub.application.service;

import com.hamming.bookhub.application.filter.reviews.ReviewSearchFilter;
import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    // Проверяет `bookId`, `userId`, создаёт `ReviewDocument`, обновляет `BookEntity.rating`, очищает кэш.
    ReviewResponse addReview(CreateNewReviewForBookRequest request);

    // Возвращает отзывы из Redis или MongoDB.
    List<ReviewResponse> getReviewsByBook(ReviewSearchFilter filter);

    // Удаляет отзыв, пересчитывает рейтинг, очищает кэш.
    void deleteReview(UUID reviewId);
}
