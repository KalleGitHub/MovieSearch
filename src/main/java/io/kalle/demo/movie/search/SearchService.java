package io.kalle.demo.movie.search;

import io.kalle.demo.movie.favorite.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Value("${omdb.apikey}")
    private String omdbApiKey;

    @Value("${omdb.apiurl}")
    private String omdbApiUrl;

    private final RestTemplate omdbRestTemplate;
    private final FavoriteService favoriteService;

    public SearchResult searchByTitleAndYear(String title, Integer year, Integer page) {
        SearchResult searchResult = omdbRestTemplate.getForObject(
                getURI(title, year, page),
                SearchResult.class);

        if (searchResult != null && searchResult.getSearch() != null) {
            searchResult
                    .getSearch()
                    .forEach(search -> search.setFavorite(favoriteService.isFavorite(search.getImdbID())));
        }

        return searchResult;
    }

    public URI getURI(String query, Integer year, Integer page) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(omdbApiUrl)
                .queryParam("s", query)
                .queryParam("y", year)
                .queryParam("type", SearchType.movie.toString())
                .queryParam("apikey", omdbApiKey)
                .queryParam("page", page)
                .queryParam("r", "json");

        return uriBuilder.build().toUri();
    }

}
