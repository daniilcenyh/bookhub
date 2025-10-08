package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.books.BooksSearchByAuthorFilter;
import com.hamming.bookhub.application.filter.books.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.books.CommonBooksSearchFilter;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.request.books.UpdateBookRequest;
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
     * @param request
     * @return
     */
    @Override
    public BookResponse addBook(CreateNewBookRequest request) {
        return null;
    }

    /**
     * @param bookId
     * @return
     */
    @Override
    public BookResponse getBookById(UUID bookId) {
        return null;
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findBooksByCommonFilter(CommonBooksSearchFilter filter) {
        return List.of();
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findBooksByGenre(BooksSearchByGenreFilter filter) {
        return List.of();
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findBooksByAuthor(BooksSearchByAuthorFilter filter) {
        return List.of();
    }

    /**
     * @param bookId
     * @param request
     * @return
     */
    @Override
    public BookResponse updateBook(UUID bookId, UpdateBookRequest request) {
        return null;
    }

    /**
     * @param bookId
     */
    @Override
    public void deleteBook(UUID bookId) {

    }

    /**
     * @return
     */
    @Override
    public List<BookResponse> getTopBooks() {
        return List.of();
    }

    /**
     * @param bookId
     */
    @Override
    public void updateRating(UUID bookId) {

    }
}
