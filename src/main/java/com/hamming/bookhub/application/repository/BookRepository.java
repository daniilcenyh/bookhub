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
            AND b.rating >= 4.0
            """)
    List<BookEntity> findByGenre(@Param("genre") BookGenre genre,
                                 Pageable pageable);

    @Query("""
            SELECT b FROM BookEntity b
            WHERE (:author IS NULL OR b.author = :author)
            AND b.rating >= 4.0
            """)
    List<BookEntity> findByAuthor(
            @Param("author") String author,
            Pageable pageable
    );

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.rating >= 4.0
            AND (:author IS NULL OR b.author = :author)
            AND (:genre IS NULL OR b.genre = :genre)
            """)
    List<BookEntity> findByFilter(
            Pageable pageable,
            @Param("author") String author,
            @Param("genre") BookGenre genre
    );

    boolean existsByTitle(String title);

    boolean existsByAuthor(String author);
}
