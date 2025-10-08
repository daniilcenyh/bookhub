package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.library.LibrarySearchFilter;
import com.hamming.bookhub.application.service.LibraryService;
import com.hamming.bookhub.infrastructure.request.library.AddNewBookInLibraryUserRequest;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    /**
     * @param userId
     * @param request
     * @return
     */
    @Override
    public UserResponse addBookToLibrary(UUID userId, AddNewBookInLibraryUserRequest request) {
        return null;
    }

    /**
     * @param userId
     * @param bookId
     */
    @Override
    public void removeBookFromLibrary(UUID userId, UUID bookId) {

    }

    /**
     * @param filter
     * @param userId
     * @return
     */
    @Override
    public List<UserResponse> getUserLibrary(LibrarySearchFilter filter, UUID userId) {
        return List.of();
    }
}
