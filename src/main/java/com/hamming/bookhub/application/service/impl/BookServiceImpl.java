package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.BooksSearchFilter;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findAll(BooksSearchFilter filter) {
        return List.of();
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findAllByGenre(BooksSearchByGenreFilter filter) {
        return List.of();
    }

    /**
     * @param request
     * @return
     */
    @Override
    public BookResponse createBook(CreateNewBookRequest request) {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public BookResponse findBookById(UUID id) {
        return null;
    }
}
