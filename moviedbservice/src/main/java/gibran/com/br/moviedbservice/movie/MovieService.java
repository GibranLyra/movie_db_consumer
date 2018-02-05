package gibran.com.br.moviedbservice.movie;

import gibran.com.br.moviedbservice.model.Movie;
import gibran.com.br.moviedbservice.model.MovieDbBaseResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<MovieDbBaseResponse> getPopular();

    @GET("movie/{movieId}")
    Observable<Movie> getMovie(@Path("movieId") int movieId);
}
