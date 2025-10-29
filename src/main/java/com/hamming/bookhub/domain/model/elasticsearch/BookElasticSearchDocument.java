package com.hamming.bookhub.domain.model.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "movies_index")
@Setting(settingPath = "/elasticsearch-settings.json")
public class BookElasticSearchDocument {

    @Id
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "russian_analyzer")
    private String title;

    @Field(type = FieldType.Text, analyzer = "russian_analyzer")
    private String description;
}
