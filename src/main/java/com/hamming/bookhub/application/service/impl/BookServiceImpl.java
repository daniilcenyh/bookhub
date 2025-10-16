package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.books.BooksSearchByAuthorFilter;
import com.hamming.bookhub.application.filter.books.BooksSearchByGenreFilter;
import com.hamming.bookhub.application.filter.books.CommonBooksSearchFilter;
import com.hamming.bookhub.application.filter.recommendations.CommonTopBookFilter;
import com.hamming.bookhub.application.mapper.BookMapper;
import com.hamming.bookhub.application.repository.BookRepository;
import com.hamming.bookhub.application.repository.ReviewRepository;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.domain.exception.books.BookAlreadyExistsException;
import com.hamming.bookhub.domain.exception.books.BookNotFoundException;
import com.hamming.bookhub.domain.model.document.ReviewDocument;
import com.hamming.bookhub.infrastructure.request.books.CreateNewBookRequest;
import com.hamming.bookhub.infrastructure.request.books.UpdateBookRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final BookMapper bookMapper;

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 10;

    @Override
    @Transactional
    @CachePut(cacheNames = "books", key = "#result.id")
    public BookResponse addBook(CreateNewBookRequest request) {
        log.info("APPEND_NEW_BOOK_REQUEST time: {}", LocalDateTime.now());
        if (validationBookFieldsIfExist(request.title(), request.author())) {
            log.warn("BOOK_ALREADY_EXISTS with TITLE: {}, with AUTHOR_NAME: {}", request.title(), request.author());
            throw new BookAlreadyExistsException("BOOK_ALREADY_EXISTS with TITLE: {%s}, with AUTHOR_NAME: {%s}".formatted( request.title(), request.author()));
        }
        log.info("SAVING_NEW_BOOK_TO_PSQL_REDIS time: {}", LocalDateTime.now());
        var bookToSave = bookMapper.fromCreateNewBookRequestToBookEntity(request);
        var savedBook = bookRepository.save(bookToSave);
        log.info("SUCCESSFUL_APPENDED_NEW_BOOK time: {}", LocalDateTime.now());
        return bookMapper.fromBookEntityToBookResponse(savedBook);
    }

    /**
     * @param bookId
     * @return
     */
    @Override
    @Transactional
    @Cacheable(cacheNames = "books", key = "#bookId", unless = "#result == null")
    public BookResponse getBookById(UUID bookId) {
        log.info("CACHE_REQUEST_MISS");
        log.info("LOAD_BOOK_FROM_PSQL BOOK_UUID: {}", bookId);
        updateRating(bookId);
        return bookMapper.fromBookEntityToBookResponse(
                bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookNotFoundException("BOOK_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(bookId, LocalDateTime.now())))
        );
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findBooksByCommonFilter(CommonBooksSearchFilter filter) {
        int pageNumber = filter.pageNumber() == null ? 0 : filter.pageNumber();
        int pageSize = filter.pageSize() == null ? 10 : filter.pageSize();

        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return bookRepository.findByFilter(pageable, filter.author(), filter.genre())
                .stream()
                .map(bookMapper::fromBookEntityToBookResponse)
                .toList();
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<BookResponse> findBooksByGenre(BooksSearchByGenreFilter filter) {
        int pageNumber = filter.pageNumber() == null ? 0 : filter.pageNumber();
        int pageSize = filter.pageSize() == null ? 10 : filter.pageSize();

        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return bookRepository.findByGenre(filter.genre(), pageable)
                .stream()
                .map(bookMapper::fromBookEntityToBookResponse)
                .toList();
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
    @Transactional
    @CachePut(cacheNames = "books", key = "#bookId")
    public BookResponse updateBook(UUID bookId, UpdateBookRequest request) {
        log.info("UPDATING_BOOK_IN_PSQL_AND_REFRESHING_CACHE UUID: {}", bookId);
        if (!bookRepository.existsById(bookId)) {
            log.warn("BOOK_NOT_FOUNDED with UUID: {}", bookId);
            throw new BookNotFoundException("BOOK_NOT_FOUNDED_EXCEPTION with UUID: {%s}. Time exception: {%s}".formatted(bookId, LocalDateTime.now()));
        }
        if (validationBookFieldsIfExist(request.title(), request.author())) {
            log.warn("BOOK_WITH_TITLE_AND_AUTHOR_ALREADY_EXISTS");
            throw new BookAlreadyExistsException("BOOK_WITH_TITLE_AND_AUTHOR_ALREADY_EXISTS_EXCEPTION with TITLE: {%s}, AUTHOR: {%s}. Time exception: {%s}"
                    .formatted(request.title(), request.author(), LocalDateTime.now()));
        }

        var entityToUpdate = bookMapper.fromUpdateBookRequestToBookEntity(request);
        var updatedEntity = bookRepository.save(entityToUpdate);

        return bookMapper.fromBookEntityToBookResponse(updatedEntity);
    }

    /**
     * @param bookId
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "books", key = "#bookId")
    public void deleteBook(UUID bookId) {
        log.info("DELETE_BOOK_FROM_PSQL_AND_EVICT_CACHE");
        if (!bookRepository.existsById(bookId)) {
            log.warn("BOOK_NOT_FOUNDED with UUID: {}", bookId);
            throw new BookNotFoundException("BOOK_NOT_FOUNDED_EXCEPTION with UUID: {%s}. Time exception: {%s}"
                    .formatted(bookId, LocalDateTime.now()));
        }
        bookRepository.deleteById(bookId);
        log.info("SUCCESSFUL_DELETE_BY_BOOK_UUID");
    }

    /**
     * @return
     */
    @Override
    @Cacheable(cacheNames = "top_books", key = "#bookId")
    public List<BookResponse> getTopBooks(CommonTopBookFilter filter) {
        log.info("CACHE_REQUEST_MISS");
        log.info("LOAD_BOOKS_TOP_FROM_PSQL");

        int pageNumber = filter.pageNumber() == null ? 0 : filter.pageNumber();
        int pageSize = filter.pageSize() == null ? 10 : filter.pageSize();

        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        var topBooks = bookRepository.findTopBooksByRating(pageable);

        return topBooks.stream()
                .map(bookMapper::fromBookEntityToBookResponse)
                .toList();
    }

    /**
     * @param bookId
     */
    // TODO: еще подумать как еще лучше реализовать обновление рейтинга книги
    //  и обновления в кэше + обновление топа книг
    @Override
    @Transactional
    @CachePut(cacheNames = "books", key = "#bookId")
    @CacheEvict(cacheNames = "top_books", key = "#bookId")
    public void updateRating(UUID bookId) {
        var existedBook = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException("BOOK_NOT_FOUNDED_EXCEPTION with UUID: {%s}. Time exception: {%s}"
                                .formatted(bookId, LocalDateTime.now()))
                );

        int pageSize = PAGE_SIZE;
        int pageNumber = PAGE_NUMBER;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        var reviews = reviewRepository.findByBookId(bookId, pageable);
        var updatedRatingBook = reviews.stream()
                .mapToDouble(ReviewDocument::getRating)
                .average()
                .orElse(0.0D);

        existedBook.setRating(updatedRatingBook);
        bookRepository.save(existedBook);
    }

    private boolean validationBookFieldsIfExist(String title, String author) {
        return bookRepository.existsByTitle(title) && bookRepository.existsByAuthor(author);
    }
}
