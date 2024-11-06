package io.kalle.demo.movie.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    @JsonProperty("Search")
    private List<Search> search;
    private String totalResults;
    @JsonProperty("Response")
    private String response;

}
