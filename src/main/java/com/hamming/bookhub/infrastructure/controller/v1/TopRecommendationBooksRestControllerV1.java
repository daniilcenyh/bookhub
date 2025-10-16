package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.filter.books.BooksSearchByAuthorFilter;
import com.hamming.bookhub.application.filter.books.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.recommendations.CommonTopBookFilter;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.infrastructure.request.recommendations.RecommendationAuthorRequest;
import com.hamming.bookhub.infrastructure.request.recommendations.RecommendationGenreRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/top-recommendations")
public class TopRecommendationBooksRestControllerV1 {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> recommendationTopBooksInAllCategories(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("FIND_TOP_BOOKS_IN_ALL_CATEGORIES_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new CommonTopBookFilter(pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getTopBooks(filter));
    }

    @GetMapping("/by-genre")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> recommendationTopBooksByGenre(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestBody @Validated RecommendationGenreRequest request
    ) {
        log.info("FIND_TOP_BOOKS_IN_GENRE_CATEGORIES_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new BooksSearchByGenreFilter(pageSize, pageNumber, request.genre());
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByGenre(filter));
    }

    @GetMapping("/by-author")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> recommendationTopBooksByAuthor(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestBody @Validated RecommendationAuthorRequest request
    ) {
        log.info("FIND_TOP_BOOKS_BY_AUTHOR_CATEGORIES_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new BooksSearchByAuthorFilter(pageSize, pageNumber, request.author());
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByAuthor(filter));
    }
}
