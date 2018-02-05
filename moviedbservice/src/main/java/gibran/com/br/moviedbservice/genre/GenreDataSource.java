package gibran.com.br.moviedbservice.genre;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.model.Genre;
import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 30/07/17.
 */

public interface GenreDataSource {
    Observable<ArrayList<Genre>> getMovieGenres();

    Observable<ArrayList<Movie>> getMovies(int genreId);
}
