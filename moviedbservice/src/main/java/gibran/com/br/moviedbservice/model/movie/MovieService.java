package gibran.com.br.moviedbservice.model.movie;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<ArrayList<Movie>> getPopular();
}
