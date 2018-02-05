package gibran.com.br.moviedbservice.movie;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 30/07/17.
 */

public interface MovieDataSource {
    Observable<ArrayList<Movie>> getPopular();

    Observable<Movie> getMovieDetails(int movieId);
}
