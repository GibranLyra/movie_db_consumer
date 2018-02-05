package gibran.com.br.moviedbservice.genre;

import gibran.com.br.moviedbservice.model.GenreResponse;
import gibran.com.br.moviedbservice.model.MovieDbBaseResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface GenreService {

    @GET("genre/movie/list")
    Observable<GenreResponse> getMovieGenres();

    @GET("genre/{genreId}/movies")
    Observable<MovieDbBaseResponse> getMovies(@Path("genreId") int genreId);
}
