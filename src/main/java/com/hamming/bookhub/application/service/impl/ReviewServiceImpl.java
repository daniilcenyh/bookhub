package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.reviews.ReviewSearchFilter;
import com.hamming.bookhub.application.mapper.ReviewMapper;
import com.hamming.bookhub.application.repository.BookRepository;
import com.hamming.bookhub.application.repository.ReviewRepository;
import com.hamming.bookhub.application.repository.UserRepository;
import com.hamming.bookhub.application.service.BookService;
import com.hamming.bookhub.application.service.ReviewService;
import com.hamming.bookhub.domain.exception.SomethingEntityNotFoundException;
import com.hamming.bookhub.domain.exception.books.BookNotFoundException;
import com.hamming.bookhub.domain.exception.books.InvalidBookRatingException;
import com.hamming.bookhub.domain.exception.library.BookNotFoundInLibraryUserException;
import com.hamming.bookhub.domain.exception.reviews.ReviewDocumentNotFoundException;
import com.hamming.bookhub.domain.exception.users.UserNotFoundException;
import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    /**
     * @param request
     * @return
     */
    @Override
    @Transactional
    public ReviewResponse addReview(CreateNewReviewForBookRequest request) {

        // Проверка на существование пользователя с UUID и книги с UUID
        if (!validationIdUserAndBookIfExists(request.userId(), request.bookId())) {
            log.warn("BOOK_OR_USER_IS_NOT_EXISTED");
            throw new SomethingEntityNotFoundException("BOOK_OR_USER_IS_NOT_EXISTED. Time exception: {%s}"
                    .formatted(LocalDateTime.now()));
        }
        // Проверка на валидность рейтинга от 0 до 5
        if (!validationReviewRating(request.rating())) {
            log.warn("INVALID_RATING_BOOK with BOOK_ID: {}", request.bookId());
            throw new InvalidBookRatingException("INVALID_RATING_BOOK with BOOK_ID: {%s}. Time exception: {%s}"
                    .formatted(request.bookId(), LocalDateTime.now()));
        }
        // Проверка на то что данная книга с UUID есть в библиотеке пользователя с UUID
        if (!validationBookContainsInLibraryUser(request.userId(), request.bookId())) {
            log.warn("BOOK_NOT_FOUNDED_IN_LIBRARY_USER user with UUID: {}, book with UUID: {}", request.userId(), request.bookId());
            throw new BookNotFoundInLibraryUserException("BOOK_NOT_FOUNDED_IN_LIBRARY_USER user with UUID: {%s}, book with UUID: {%s}. Time exception: {%s}"
                    .formatted(request.userId(), request.userId(), LocalDateTime.now()));
        }

        var reviewToSave = reviewMapper.fromCreateNewReviewForBookRequestToReviewDocument(request);
        var savedReview = reviewRepository.save(reviewToSave);

        // Обновление рейтинга книги на основе нового отзыва
        bookService.updateRating(savedReview.getBookId());

        return reviewMapper.fromReviewDocumentToReviewResponse(savedReview);
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public List<ReviewResponse> getReviewsByBook(ReviewSearchFilter filter) {
        log.info("CACHE_REQUEST_MISS");
        log.info("LOAD_REVIEWS_FROM_MONGO by BOOK_UUID: {}", filter.bokId());
        if (!bookRepository.existsById(filter.bokId())) {
            log.warn("BOOK_NOT_FOUNDED with UUID: {}", filter.bokId());
            throw new BookNotFoundException("BOOK_NOT_FOUNDED_EXCEPTION with UUID: {%s}. Time exception: {%s}".formatted(filter.bokId(), LocalDateTime.now()));
        }
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 10;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return reviewRepository.findByBookId(filter.bokId(), pageable)
                .stream()
                .map(reviewMapper::fromReviewDocumentToReviewResponse)
                .toList();
    }

    /**
     * @param reviewId
     */
    @Override
    @Transactional
    public void deleteReview(UUID reviewId) {
        var exitedReview = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ReviewDocumentNotFoundException(""
                                .formatted(reviewId, LocalDateTime.now()))
                );

        var bookId = exitedReview.getBookId();
        bookService.updateRating(bookId);
    }

    private boolean validationBookContainsInLibraryUser(UUID userId, UUID bookId) {
        var existedUserEntity = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}"
                                .formatted(userId, LocalDateTime.now()))
                );

        var existedBookEntity = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException("BOOK_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}"
                                .formatted(userId, LocalDateTime.now()))
                );

        return existedUserEntity.getLibrary().contains(existedBookEntity);
    }

    private boolean validationIdUserAndBookIfExists(UUID userId, UUID bookId) {
        return userRepository.existsById(userId) && bookRepository.existsById(bookId);
    }

    private boolean validationReviewRating(Double rating) {
        return rating >= 0.0D && rating <= 5.0D;
    }
}
