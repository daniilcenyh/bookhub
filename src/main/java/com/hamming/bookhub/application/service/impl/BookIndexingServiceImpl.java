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
        Iterable<BookEntity> bookEntityIterable = bookRepository.findAll();


        while (bookEntityIterable.iterator().hasNext()) {
            var book = bookEntityIterable.iterator().next();
            bookElasticsearchRepository.save(new BookElasticSearchDocument(book.getId(), book.getTitle(), book.getDescription()));
        }
//        bookElasticsearchRepository.saveAll(
//                books.stream()
//                        .map(
//                        book -> new BookElasticSearchDocument(book.getId(), book.getTitle(), book.getDescription())
//                )
//                        .toList()
//        );
    }
}
