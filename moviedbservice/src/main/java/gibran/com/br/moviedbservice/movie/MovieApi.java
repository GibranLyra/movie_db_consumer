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
public class MovieApi implements MovieDataSource {
    private static MovieApi instance;
    private final MovieService movieService;

    private MovieApi() {
        Retrofit retrofit = MovieDbApiModule.getRetrofit();
        movieService = retrofit.create(MovieService.class);
    }

    public static MovieApi getInstance() {
        if (instance == null) {
            instance = new MovieApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new MovieApi();
    }


    public Observable<ArrayList<Movie>> getPopular() {
        return movieService.getPopular()
                .map(MovieDbBaseResponse::getResults)
                .doOnError(e -> Timber.e(e, "getPopular: %s", e.getMessage()));
    }

    @Override
    public Observable<Movie> getMovieDetails(int movieId) {
        return movieService.getMovie(movieId)
                .doOnError(e -> Timber.e(e, "getMovieDetails: %s", e.getMessage()));
    }

    @Override
    public Observable<ArrayList<Movie>> getRecommendations(int movieId) {
        return movieService.getPopular()
                .map(MovieDbBaseResponse::getResults)
                .doOnError(e -> Timber.e(e, "getRecommendations: %s", e.getMessage()));
    }
}