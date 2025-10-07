package com.hamming.bookhub.infrastructure.controller.v1;

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

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookRestControllerV1 {

    @PostMapping
    public ResponseEntity<BookResponse> createNewBook(
            @Validated @RequestBody CreateNewBookRequest request,
            BindingResult bindingResult

    ) throws BindException {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable(value = "id") UUID id
    ) {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> findAllByGenre(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "genre", required = false) BookGenre genre
    ) {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> findAllByAuthor(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "author", required = false) String author
    ) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "genre", required = false) BookGenre genre

    ) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookById(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Validated UpdateBookRequest request
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(
            @PathVariable(value = "id") UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // TODO: сделать контроллер для взятия книг с лучшим рейтингом из КЭША Redis с пагинацией
}
