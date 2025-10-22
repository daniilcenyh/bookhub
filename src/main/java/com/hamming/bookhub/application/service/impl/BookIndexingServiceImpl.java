package com.hamming.bookhub.application.service.impl;

import com.hamming.bookhub.application.repository.BookElasticsearchRepository;
import com.hamming.bookhub.application.repository.BookRepository;
import com.hamming.bookhub.application.service.BookIndexingService;
import com.hamming.bookhub.domain.model.elasticsearch.BookElasticSearchDocument;
import com.hamming.bookhub.domain.model.entity.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookIndexingServiceImpl implements BookIndexingService {

    private final BookRepository bookRepository;
    private final BookElasticsearchRepository bookElasticsearchRepository;

    @Override
    public void reindexAllBooks() {
        List<BookEntity> books = (List<BookEntity>) bookRepository.findAll();

        bookElasticsearchRepository.saveAll(
                books.stream()
                        .map(
                        book -> BookElasticSearchDocument.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .description(book.getDescription())
                                .build()
                )
                        .toList()
        );
    }
}
