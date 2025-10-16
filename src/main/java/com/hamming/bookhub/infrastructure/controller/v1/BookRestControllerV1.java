package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.filter.books.BooksSearchByAuthorFilter;
import com.hamming.bookhub.application.filter.books.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.books.CommonBooksSearchFilter;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.domain.model.enums.BookGenre;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.request.books.UpdateBookRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookRestControllerV1 {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookResponse> createNewBook(
            @Validated @RequestBody CreateNewBookRequest request,
            BindingResult bindingResult

    ) throws BindException {
        log.info("CREATED_NEW_BOOK_REQUEST. Time request: {}", LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(request));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable(value = "id") UUID id
    ) {
        log.info("GET_BOOK_BY_UUID_REQUEST. Time request: {}", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getBookById(id));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> findAllByGenre(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "genre", required = false) BookGenre genre
    ) {
        log.info("FIND_BOOKS_BY_GENRE_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new BooksSearchByGenreFilter(pageSize, pageNumber, genre);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByGenre(filter));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> findAllByAuthor(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "author", required = false) String author
    ) {
        log.info("FIND_BOOKS_BY_AUTHOR_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new BooksSearchByAuthorFilter(pageSize, pageNumber, author);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByAuthor(filter));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> findAll(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "genre", required = false) BookGenre genre

    ) {
        log.info("FIND_BOOKS_BY_COMMON_FILTER_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new CommonBooksSearchFilter(pageSize, pageNumber, genre, author);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByCommonFilter(filter));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponse> updateBookById(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Validated UpdateBookRequest request,
            BindingResult bindingResult
    ) throws BindException {
        log.info("UPDATE_BOOK_FIELDS_BY_UUID_REQUEST. Time request: {}", LocalDateTime.now());
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBookById(
            @PathVariable(value = "id") UUID id
    ) {
        log.info("DELETE_BOOK_BY_UUID_REQUEST. Time request: {}", LocalDateTime.now());
        bookService.deleteBook(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
