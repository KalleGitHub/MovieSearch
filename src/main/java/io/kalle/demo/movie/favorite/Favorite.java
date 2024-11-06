package io.kalle.demo.movie.favorite;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "favorite_table")
public class Favorite implements Serializable {

    @Id
    private String imdbID;
    private String title;
    private String year;

}
