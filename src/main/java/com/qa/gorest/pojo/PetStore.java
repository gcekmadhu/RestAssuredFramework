package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetStore {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("photoUrls")
    private List<String> photoUrls;
    @JsonProperty("tags")
    private List<Tags> tags;
    @JsonProperty("status")
    private String status;
    private Category category;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Tags{
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Category{
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
    }

}
