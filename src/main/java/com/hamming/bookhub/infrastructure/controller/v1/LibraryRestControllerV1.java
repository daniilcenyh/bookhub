package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.application.filter.library.LibrarySearchFilter;
import com.hamming.bookhub.application.service.LibraryService;
import com.hamming.bookhub.infrastructure.request.library.AddNewBookInLibraryUserRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import com.hamming.bookhub.infrastructure.response.UserResponse;
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
@RequestMapping("api/v1/users")
public class LibraryRestControllerV1 {

    private final LibraryService libraryService;

    @GetMapping("/{userId}/library")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookResponse>> getAllBooksInLibraryOfUser(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @PathVariable(value = "userId") UUID userId
    ) {
        log.info("FIND_ALL_BOOKS_IN_LIBRARY_USER_REQUEST. Time request: {}", LocalDateTime.now());
        var filter = new LibrarySearchFilter(pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(libraryService.getUserLibrary(filter, userId));
    }

    @PostMapping("/{userId}/library")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> addNewBookInLibraryUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated AddNewBookInLibraryUserRequest request,
            BindingResult bindingResult
    ) throws BindException {
        log.info("ADD_NEW_BOOK_IN_USER_LIBRARY_REQUEST. Time request: {}", LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(libraryService.addBookToLibrary(userId, request));
    }

    @DeleteMapping("/{userId}/library/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBookByIdInLibraryUser(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "bookId") UUID bookId
    ) {
        log.info("DELETE_BOOK_IN_USER_LIBRARY_REQUEST. Time request: {}", LocalDateTime.now());
        libraryService.removeBookFromLibrary(userId, bookId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
