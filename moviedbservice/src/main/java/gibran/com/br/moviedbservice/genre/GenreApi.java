package gibran.com.br.moviedbservice.genre;

import java.util.ArrayList;

import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Genre;
import gibran.com.br.moviedbservice.model.GenreResponse;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 05/02/2018.
 */
public class GenreApi implements GenreDataSource {
    private static GenreApi instance;
    private final GenreService genreService;

    private GenreApi() {
        Retrofit retrofit = MovieDbApiModule.getRetrofit();
        genreService = retrofit.create(GenreService.class);
    }

    public static GenreApi getInstance() {
        if (instance == null) {
            instance = new GenreApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new GenreApi();
    }

    @Override
    public Observable<ArrayList<Genre>> getMovieGenres() {
        return genreService.getMovieGenres()
                .map(GenreResponse::getGenres)
                .doOnError(e -> Timber.e(e, "getMovieGenres: %s", e.getMessage()));
    }
}