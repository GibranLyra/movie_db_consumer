package gibran.com.br.moviedbservice.model.movie;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 30/07/17.
 */

public interface MoviesDataSource {
    Observable<ArrayList<Movie>> getPopular();
}
