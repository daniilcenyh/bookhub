package com.hamming.bookhub.application.service;

import com.hamming.bookhub.application.filter.library.LibrarySearchFilter;
import com.hamming.bookhub.infrastructure.request.library.AddNewBookInLibraryUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    UserResponse addBookToLibrary(UUID userId, AddNewBookInLibraryUserRequest request);
    void removeBookFromLibrary(UUID userId, UUID bookId);
    List<UserResponse> getUserLibrary(LibrarySearchFilter filter, UUID userId);
}
