package com.hamming.bookhub.application.repository;

import com.hamming.bookhub.domain.model.document.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends MongoRepository<ReviewDocument, UUID> {

    // TODO: сделать кастомный запрос для поиска всех коментариев о книге по ее UUID
    List<ReviewDocument> findByBookId(UUID bookId);
}
