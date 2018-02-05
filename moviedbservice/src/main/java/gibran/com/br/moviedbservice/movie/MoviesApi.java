package gibran.com.br.moviedbservice.movie;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Movie;
import gibran.com.br.moviedbservice.model.MovieDbBaseResponse;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 05/02/2018.
 */
public class MoviesApi implements MoviesDataSource {
    private static MoviesApi instance;
    private final MovieService movieService;

    private MoviesApi() {
        Retrofit retrofit = MovieDbApiModule.getRetrofit();
        movieService = retrofit.create(MovieService.class);
    }

    public static MoviesApi getInstance() {
        if (instance == null) {
            instance = new MoviesApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new MoviesApi();
    }


    public Observable<ArrayList<Movie>> getPopular() {
        return movieService.getPopular()
                .map(MovieDbBaseResponse::getResults)
                .doOnError(e -> Timber.e(e, "getPopular: %s", e.getMessage()));
    }

    @Override
    public Observable<Movie> getDetails(int movieId) {
        return movieService.getMovie(movieId)
                .doOnError(e -> Timber.e(e, "getDetails: %s", e.getMessage()));
    }
}