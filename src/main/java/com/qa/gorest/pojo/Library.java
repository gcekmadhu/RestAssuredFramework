package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Library {
    @JsonProperty("book_name")
    private String book_name;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("aisle")
    private String aisle;
    @JsonProperty("author")
    private String author;
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Msg")
    private String msg;
}
