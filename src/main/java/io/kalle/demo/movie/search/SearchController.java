package io.kalle.demo.movie.search;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController()
@RequestMapping("movie-search")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping(path = "/movie", produces = "application/json")
    public @ResponseBody SearchResult getMovie(@RequestParam() String title,
                                               @RequestParam(required = false) Integer year,
                                               @RequestParam(required = false) Integer page) {
        if (title == null || title.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        return searchService.searchByTitleAndYear(title, year, page);
    }

}
