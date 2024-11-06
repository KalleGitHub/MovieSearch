package io.kalle.demo.movie.favorite;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController()
@RequestMapping("movie-favorite")
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    @PostMapping(path = "/favorite", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postFavorite(@RequestBody Favorite favorite) {
        Optional<Favorite> existingFavorite = favoriteRepository.findByImdbID(favorite.getImdbID());

        if (existingFavorite.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Favorite already exists");
        } else {
            favoriteRepository.save(favorite);
        }
    }

    @GetMapping(path = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Favorite> getFavorites() {
        return favoriteRepository.findAll();
    }

    @DeleteMapping(path = "/delete")
    public void deleteFavorite(@RequestParam String imdbID) {
        Optional<Favorite> favorite = favoriteRepository.findByImdbID(imdbID);

        if (favorite.isPresent()) {
            favoriteRepository.delete(favorite.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorite not found");
        }
    }


}
