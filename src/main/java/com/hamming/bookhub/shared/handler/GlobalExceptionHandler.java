package com.hamming.bookhub.shared.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hamming.bookhub.domain.exception.SomethingEntityNotFoundException;
import com.hamming.bookhub.domain.exception.books.BookAlreadyExistsException;
import com.hamming.bookhub.domain.exception.books.BookNotFoundException;
import com.hamming.bookhub.domain.exception.books.InvalidBookRatingException;
import com.hamming.bookhub.domain.exception.library.BookAlreadyExistsInLibraryUserException;
import com.hamming.bookhub.domain.exception.library.BookNotFoundInLibraryUserException;
import com.hamming.bookhub.domain.exception.reviews.ReviewDocumentNotFoundException;
import com.hamming.bookhub.domain.exception.users.UserAlreadyExistsException;
import com.hamming.bookhub.domain.exception.users.UserNotFoundException;
import com.hamming.bookhub.shared.exception.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    //-----------------------------------------------------
    // 400 Bas Request (некорректный JSON, пустой title). |
    //-----------------------------------------------------

    @ExceptionHandler({
            MethodArgumentNotValidException.class,      // Ошибки валидации @Valid или @Validated
            ConstraintViolationException.class,        // Ошибки валидации constraints
            BindException.class,                      // Ошибки привязки данных
            InvalidBookRatingException.class
    })
    public ResponseEntity<ErrorResponse> handleValidationExceptionsHandler(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_FAILED",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({
            NumberFormatException.class,               // Неправильный формат числа
            DateTimeParseException.class,              // Неправильный формат даты
            IllegalArgumentException.class            // Недопустимый аргумент
    })
    public ResponseEntity<ErrorResponse> handleBadParsingDataAndNumbersExceptionsHandler(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_ARGUMENT",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({
            JsonParseException.class,                   // Ошибка парсинга JSON
            JsonMappingException.class,                 // Ошибка маппинга JSON
            HttpMessageNotReadableException.class       // Общая ошибка чтения сообщения
    })
    public ResponseEntity<ErrorResponse> handleBadParsingJsonOrXmlExceptionsHandler(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "MALFORMED_JSON",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({
            BookAlreadyExistsException.class,
            BookAlreadyExistsInLibraryUserException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleAlreadyExistsExceptionsHandler(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ALREADY_EXISTS",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    //-----------------------------------------
    // 404 Not Found (TaskNotFoundException). |
    //-----------------------------------------

    @ExceptionHandler(value = {
            NoSuchElementException.class,
    })
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "RESOURCE_NOT_FOUND",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {
            EmptyResultDataAccessException.class,      // Результат запроса пустой
            DataRetrievalFailureException.class,       // Ошибка получения данных
            EntityNotFoundException.class              // Сущность не найдена (JPA)
    })
    public ResponseEntity<ErrorResponse> handleDataAccessException(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "DATA_NOT_FOUND",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler({
            BookNotFoundException.class,
            BookNotFoundInLibraryUserException.class,
            ReviewDocumentNotFoundException.class,
            UserNotFoundException.class,
            SomethingEntityNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse(
                "TASK_NOT_FOUND",
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)
                ).toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    // TODO: 500 Internal Server Error (прочие ошибки).

}
