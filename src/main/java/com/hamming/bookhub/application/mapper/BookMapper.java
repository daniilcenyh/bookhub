package com.hamming.bookhub.application.mapper;

import com.hamming.bookhub.domain.model.entity.BookEntity;
import com.hamming.bookhub.infrastructure.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    BookResponse fromBookEntityToBookResponse(BookEntity entity);
}
