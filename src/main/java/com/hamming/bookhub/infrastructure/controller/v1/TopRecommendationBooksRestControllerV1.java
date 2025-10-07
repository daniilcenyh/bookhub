package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.infrastructure.request.recommendations.RecommendationAuthorRequest;
import com.hamming.bookhub.infrastructure.request.recommendations.RecommendationGenreRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/top-recommendations")
public class TopRecommendationBooksRestControllerV1 {

    @GetMapping
    public ResponseEntity<List<BookResponse>> recommendationTopBooksInAllCategories(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        return null;
    }

    @GetMapping("/by-genre")
    public ResponseEntity<List<BookResponse>> recommendationTopBooksByGenre(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestBody @Validated RecommendationGenreRequest request
    ) {
        return null;
    }

    @GetMapping("/by-author")
    public ResponseEntity<List<BookResponse>> recommendationTopBooksByAuthor(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestBody @Validated RecommendationAuthorRequest request
    ) {
        return null;
    }
}
