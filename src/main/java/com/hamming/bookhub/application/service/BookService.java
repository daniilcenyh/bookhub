package com.hamming.bookhub.application.service;

import com.hamming.bookhub.application.filter.books.BooksSearchByAuthorFilter;
import com.hamming.bookhub.application.filter.books.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.books.CommonBooksSearchFilter;
import com.hamming.bookhub.application.filter.recommendations.CommonTopBookFilter;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.request.books.UpdateBookRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponse addBook(CreateNewBookRequest request);

    // сначала проверять в Redis
    BookResponse getBookById(UUID bookId);

    List<BookResponse> findBooksByCommonFilter(CommonBooksSearchFilter filter);
    List<BookResponse> findBooksByGenre(BooksSearchByGenreFilter filter);
    List<BookResponse> findBooksByAuthor(BooksSearchByAuthorFilter filter);

    BookResponse updateBook(UUID bookId, UpdateBookRequest request);

    void deleteBook(UUID bookId);

    //Возвращает топ-10 из Redis или пересчитывает
    List<BookResponse> getTopBooks(CommonTopBookFilter filter);

    // Пересчитывает рейтинг на основе ReviewDocument.
    void updateRating(UUID bookId);

}
