package com.hamming.bookhub.domain.model.entity;

import com.hamming.bookhub.domain.model.enums.BookGenre;
import com.hamming.bookhub.domain.model.enums.BookRating;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String author;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 32)
    private BookGenre genre;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 32)
    private BookRating rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
