package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.filter.reviews.ReviewSearchFilter;
import com.hamming.bookhub.application.service.ReviewService;
import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
public class ReviewRestControllerV1 {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewResponse> addNewReviewForBook(
            @RequestBody CreateNewReviewForBookRequest request,
            BindingResult bindingResult
    ) throws BindException {
        log.info("ADD_NEW_REVIEW_FOR_BOOK_REQUEST. Time request: {}", LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.addReview(request));
    }

    @GetMapping("/byBook/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByBookId(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "bookId", required = false) UUID bookId
    ) {
        log.info("FIND_ALL_REVIEWS_BY_BOOK_UUID_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new ReviewSearchFilter(pageSize, pageNumber, bookId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getReviewsByBook(filter));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReviewById(
            @PathVariable(value = "ud") UUID reviewId
    ) {
        log.info("DELETE_REVIEW_BY_BOOK_UUID_REQUEST. Time request: {}", LocalDateTime.now());
        reviewService.deleteReview(reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
