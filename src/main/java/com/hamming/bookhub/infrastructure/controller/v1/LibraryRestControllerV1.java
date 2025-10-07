package com.hamming.bookhub.infrastructure.controller.v1;

import com.hamming.bookhub.infrastructure.request.library.AddNewBookInLibraryUserRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class LibraryRestControllerV1 {

    @GetMapping("/{userId}/library")
    public ResponseEntity<List<BookResponse>> getAllBooksInLibraryOfUser(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @PathVariable(value = "userId") UUID userId
    ) {
       return null;
    }

    @PostMapping("/{userId}/library")
    public ResponseEntity<UserResponse> addNewBookInLibraryUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated AddNewBookInLibraryUserRequest request
            ) {
        return null;
    }

    @DeleteMapping("/{userId}/library/{bookId}")
    public ResponseEntity<Void> deleteBookByIdInLibraryUser(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "bookId") UUID bookId
    ) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
