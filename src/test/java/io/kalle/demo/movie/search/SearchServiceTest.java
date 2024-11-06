package io.kalle.demo.movie.search;

import io.kalle.demo.movie.favorite.FavoriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private FavoriteService favoriteService;

    @Mock
    private RestTemplate omdbRestTemplate;


    @Test
    void shouldSetFavoriteFalse() {
        SearchResult testSearchResult = new SearchResult();
        Search testSearch = new Search();
        testSearch.setImdbID("tt0133093");
        testSearch.setTitle("The Matrix");
        testSearch.setYear("1999");
        testSearchResult.setSearch(new java.util.ArrayList<>());
        testSearchResult.getSearch().add(testSearch);

        when(omdbRestTemplate.getForObject(any(URI.class), eq(SearchResult.class))).thenReturn(testSearchResult);
        when(favoriteService.isFavorite(anyString())).thenReturn(false);
        SearchResult searchResult = searchService.searchByTitleAndYear("The Matrix", 1999, 1);

        assertFalse(searchResult.getSearch().getFirst().isFavorite());
    }

    @Test
    void shouldSearchByTitleAndYear() {
        String ImdbID = "tt0133093";
        String title = "The Matrix";
        int year = 1999;

        SearchResult testSearchResult = new SearchResult();
        Search testSearch = new Search();
        testSearch.setImdbID(ImdbID);
        testSearch.setTitle(title);
        testSearch.setYear(Integer.toString(year));
        testSearchResult.setSearch(new java.util.ArrayList<>());
        testSearchResult.getSearch().add(testSearch);

        when(omdbRestTemplate.getForObject(any(URI.class), eq(SearchResult.class))).thenReturn(testSearchResult);
        when(favoriteService.isFavorite(anyString())).thenReturn(true);
        SearchResult searchResult = searchService.searchByTitleAndYear(title, year, 1);

        assertNotNull(searchResult);
        assertEquals(1, searchResult.getSearch().size());
        assertTrue(searchResult.getSearch().getFirst().isFavorite());
    }

    @Test
    void shouldGenerateCorrectURI() {
        ReflectionTestUtils.setField(searchService, "omdbApiKey", "123456");
        ReflectionTestUtils.setField(searchService, "omdbApiUrl", "www.omdbapi.com");

        URI uri = searchService.getURI("The Matrix", 1999, 1);
        assertNotNull(uri);
        assertEquals("http", uri.getScheme());
        assertEquals("www.omdbapi.com", uri.getHost());
        assertEquals("s=The Matrix&y=1999&type=movie&apikey=123456&page=1&r=json", uri.getQuery());
    }
}