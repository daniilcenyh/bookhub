package com.hamming.bookhub.application.mapper;

import com.hamming.bookhub.domain.model.document.ReviewDocument;
import com.hamming.bookhub.infrastructure.request.reviews.CreateNewReviewForBookRequest;
import com.hamming.bookhub.infrastructure.response.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    ReviewResponse fromReviewDocumentToReviewResponse(ReviewDocument document);
    ReviewDocument fromCreateNewReviewForBookRequestToReviewDocument(CreateNewReviewForBookRequest request);
}
