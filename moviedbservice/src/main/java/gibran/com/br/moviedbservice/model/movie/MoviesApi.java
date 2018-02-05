package gibran.com.br.moviedbservice.model.movie;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Movie;
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
                .doOnError(e -> Timber.e(e, "getPopular: %s", e.getMessage()));
    }

}