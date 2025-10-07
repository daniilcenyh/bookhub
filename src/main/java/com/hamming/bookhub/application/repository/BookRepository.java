package com.hamming.bookhub.application.repository;

import com.hamming.bookhub.domain.model.entity.BookEntity;
import com.hamming.bookhub.domain.model.enums.BookGenre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.genre = :genre
            AND b.rating = 'GOOD'
            """)
    List<BookEntity> findByGenre(@Param("genre") BookGenre genre,
                                 Pageable pageable);

    @Query("""
            SELECT b FROM BookEntity b
            WHERE (:author IS NULL OR b.author = :author)
            AND b.rating = 'GOOD'
            """)
    List<BookEntity> findByAuthor(
            @Param("author") String author,
            Pageable pageable
    );

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.rating = 'EXCELLENT'
            """)
    List<BookEntity> findByFilter(
            Pageable pageable
    );
}
