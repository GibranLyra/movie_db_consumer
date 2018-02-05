package gibran.com.br.moviedbservice.genre;

import gibran.com.br.moviedbservice.model.GenreResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface GenreService {

    @GET("genre/movie/list")
    Observable<GenreResponse> getMovieGenres();

}
