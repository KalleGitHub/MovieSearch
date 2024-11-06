package io.kalle.demo.movie.favorite;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FavoriteControllerTest {

    @InjectMocks
    private FavoriteController favoriteController;

    @Mock
    private FavoriteRepository favoriteRepository;


    @Test
    void shouldSaveFavorite() {
        Favorite favorite = new Favorite();
        favorite.setImdbID("tt0133093");

        when(favoriteRepository.findByImdbID(favorite.getImdbID())).thenReturn(Optional.empty());

        favoriteController.postFavorite(favorite);
        verify(favoriteRepository).save(favorite);
    }

    @Test
    void shouldThrowException() {
        Favorite favorite = createFavorite("tt0133093", "The Matrix", "1999");

        when(favoriteRepository.findByImdbID(favorite.getImdbID())).thenReturn(Optional.of(favorite));

        assertThrows(ResponseStatusException.class, () -> favoriteController.postFavorite(favorite));
    }

    @Test
    void shouldNotDeleteFavorite() {
        Favorite favorite = createFavorite("tt0133093", "The Matrix", "1999");

        when(favoriteRepository.findByImdbID(favorite.getImdbID())).thenReturn(Optional.of(favorite));

        favoriteController.deleteFavorite(favorite.getImdbID());
        verify(favoriteRepository).delete(favorite);
    }

    @Test
    void shouldDeleteFavorite() {
        Favorite favorite = createFavorite("tt0133093", "The Matrix", "1999");

        when(favoriteRepository.findByImdbID(favorite.getImdbID())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> favoriteController.deleteFavorite(favorite.getImdbID()));
    }

    private Favorite createFavorite(String imdbId, String title, String year) {
        Favorite favorite = new Favorite();
        favorite.setImdbID(imdbId);
        favorite.setTitle(title);
        favorite.setYear(year);
        return favorite;
    }

}