package com.hamming.bookhub.domain.model.document;

import com.hamming.bookhub.domain.model.enums.BookRating;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="reviews")
public class ReviewDocument {

    @Id
    private UUID _id;

    private UUID bookId;

    private UUID userId;

    private String feedback;

    private Double rating;

    @Field("createdAt")
    private LocalDateTime createdAt;
    // TODO: подумать как реализовать теги или же жанры
}
