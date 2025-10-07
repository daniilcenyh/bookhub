package com.hamming.bookhub.application.service;

import com.hamming.bookhub.application.filter.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.BooksSearchFilter;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<BookResponse> findAll(BooksSearchFilter filter);
    List<BookResponse> findAllByGenre(BooksSearchByGenreFilter filter);
    BookResponse createBook(CreateNewBookRequest request);
    BookResponse findBookById(UUID id);
}
