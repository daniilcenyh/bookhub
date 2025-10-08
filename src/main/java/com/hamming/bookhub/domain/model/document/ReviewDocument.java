package com.hamming.bookhub.domain.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;
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
    @Indexed
    private UUID bookId;
    private UUID userId;
    private String feedback;
    @Indexed
    private Double rating;

    @Field("createdAt")
    private LocalDateTime createdAt;
    // TODO: подумать как реализовать теги или же жанры

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDocument that = (ReviewDocument) o;
        return Objects.equals(_id, that._id) && Objects.equals(bookId, that.bookId) && Objects.equals(userId, that.userId) && Objects.equals(feedback, that.feedback) && Objects.equals(rating, that.rating) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, bookId, userId, feedback, rating, createdAt);
    }
}
