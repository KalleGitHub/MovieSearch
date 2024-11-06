package io.kalle.demo.movie.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByImdbID(String imdbId);

    boolean existsByImdbID(String imdbId);

}
