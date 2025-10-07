package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
public class ReviewRestControllerV1 {

    @PostMapping
    public ResponseEntity<ReviewResponse> addNewReviewForBook(
            @RequestBody CreateNewReviewForBookRequest request
    ) {
        return null;
    }

    @GetMapping("/byBook/{bookId}")
    public ResponseEntity<ReviewResponse> getAllReviewsByBookId(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "bookId", required = false) UUID bookId
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewById(
            @PathVariable(value = "ud") UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
