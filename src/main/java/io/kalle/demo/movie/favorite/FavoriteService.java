package io.kalle.demo.movie.favorite;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public boolean isFavorite(String imdbId) {
        return favoriteRepository.existsByImdbID(imdbId);
    }

}
