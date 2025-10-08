package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.filter.library.LibrarySearchFilter;
import com.hamming.bookhub.application.mapper.BookMapper;
import com.hamming.bookhub.application.mapper.UserMapper;
import com.hamming.bookhub.application.repository.BookRepository;
import com.hamming.bookhub.application.repository.UserRepository;
import com.hamming.bookhub.application.service.LibraryService;
import com.hamming.bookhub.domain.exception.library.BookAlreadyExistsInLibraryUserException;
import com.hamming.bookhub.domain.exception.books.BookNotFoundException;
import com.hamming.bookhub.domain.exception.library.BookNotFoundInLibraryUserException;
import com.hamming.bookhub.domain.exception.users.UserNotFoundException;
import com.hamming.bookhub.infrastructure.request.library.AddNewBookInLibraryUserRequest;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import com.hamming.bookhub.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    //TODO: оптимизировать метода в формате:
    // вместо проверок existsById сразу брать элемент по UUID и в случает ошибки прокидывать через orElseThrow()
    /**
     * @param userId
     * @param request
     * @return
     */
    @Override
    @Transactional
    public UserResponse addBookToLibrary(UUID userId, AddNewBookInLibraryUserRequest request) {
        log.info("ADD_NEW_BOOK_IN_LIBRARY whit BOOK_UUID: {}", request.bookId());
        if (!userRepository.existsById(userId)) {
            log.warn("USER_NOT_FOUNDED with UUID: {}. Time exception: {}", userId, LocalDateTime.now());
            throw new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(userId, LocalDateTime.now()));
        }
        if (!bookRepository.existsById(request.bookId())) {
            log.warn("BOOK_NOT_FOUNDED with UUID: {}. Time exception: {}", userId, LocalDateTime.now());
            throw new BookNotFoundException("BOOK_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(userId, LocalDateTime.now()));
        }
        log.info("SUCCESSFUL_EXISTED_USER_AND_BOOK with USER_UUID: {}, BOOK_UUID", userId, request.bookId());

        var userToAddNewBook = userRepository.findById(userId);
        var bookToAddLibrary = bookRepository.findById(request.bookId());

        if (userToAddNewBook.get().getLibrary().contains(bookToAddLibrary)) {
            log.info("BOOK_ALREADY_EXISTS_IN_LIBRARY user with UUID: {}", userId);
            throw new BookAlreadyExistsInLibraryUserException("BOOK_ALREADY_EXISTS_IN_LIBRARY user with UUID: {%s}".formatted(userId));
        }

        userToAddNewBook.get().getLibrary().add(bookToAddLibrary.get()); // добавление BookEntity в UserEntity

        var updatedUser = userRepository.save(userToAddNewBook.get());  // сохранение измененного пользователя

        var userResponse = userMapper.fromUserEntityToUserResponse(updatedUser); // преобразование UserEntity в UserResponse

        // TODO: подумать как оптимизировать и правильнее реализовать преобразование книг для ответа на запрос (если вообще необходимо)

        // очистка старого списка в библиотеки
        userResponse.library().clear();
        // цикл преобразования чтобы в UserResponse были BookResponse а не BookEntity
        for (int i = 0; i < updatedUser.getLibrary().size(); i++) {
            userResponse.library().add(i, bookMapper.fromBookEntityToBookResponse(updatedUser.getLibrary().get(i)));
        }
        return userResponse;
    }

    /**
     * @param userId
     * @param bookId
     */
    @Override
    public void removeBookFromLibrary(UUID userId, UUID bookId) {
        log.info("REMOVE_BOOK_FROM_LIBRARY whit BOOK_UUID: {}", bookId);

        var existedUserEntity = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(userId, LocalDateTime.now()))
                );

        var existedBookEntity = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException("BOOK_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(userId, LocalDateTime.now()))
                );

        if (!existedUserEntity.getLibrary().contains(existedBookEntity)) {
            log.info("BOOK_NOT_FOUNDED_IN_LIBRARY_USER user with UUID: {}, book with UUID: {}", userId, bookId);
            throw new BookNotFoundInLibraryUserException("BOOK_NOT_FOUNDED_IN_LIBRARY_USER user with UUID: {%s}, book with UUID: {%s}".formatted(userId, bookId));
        }

        existedUserEntity.getLibrary().remove(existedBookEntity);

        var updatedUserWithoutRemovedBook = userRepository.save(existedUserEntity);
    }

    /**
     * @param filter
     * @param userId
     * @return
     */
    @Override
    public List<BookResponse> getUserLibrary(LibrarySearchFilter filter, UUID userId) {
        log.info("GET_USER_LIBRARY with UUID: {}", userId);
        var existedUserEntity = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("USER_NOT_FOUNDED with UUID: {%s}. Time exception: {%s}".formatted(userId, LocalDateTime.now()))
                );

        return List.of();
    }
}
