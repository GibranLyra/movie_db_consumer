package gibran.com.br.moviedbservice.movie;

import gibran.com.br.moviedbservice.model.MovieDbBaseResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<MovieDbBaseResponse> getPopular();
}
