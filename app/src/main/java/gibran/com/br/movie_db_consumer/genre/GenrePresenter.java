package gibran.com.br.movie_db_consumer.genre;

import android.support.annotation.Nullable;
import android.view.View;

import gibran.com.br.movie_db_consumer.helpers.EspressoIdlingResource;
import gibran.com.br.movie_db_consumer.helpers.ObserverHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider;
import gibran.com.br.moviedbservice.genre.GenreDataSource;
import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class GenrePresenter implements GenreContract.Presenter {

    private GenreDataSource genreDataSource;
    private GenreContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getGenreMoviesDisposable;

    public GenrePresenter(GenreDataSource genreDataSource,
                          GenreContract.ContractView view,
                          BaseSchedulerProvider schedulerProvider) {
        this.genreDataSource = genreDataSource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //UNUSED
    }

    @Override
    public void unsubscribe() {
        /* Unsubscribe observable to avoid leaks */
        ObserverHelper.safelyDispose(getGenreMoviesDisposable);
    }

    @Override
    public void loadGenreMovies(int genreId) {
        view.showLoading(true);
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice
        getGenreMoviesDisposable = genreDataSource.getMovies(genreId, 1)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(movies -> {
                            view.showLoading(false);
                            view.showMovies(movies);
                        },
                        e -> {
                            Timber.e(e, "loadGenreMovies: %s", e.getMessage());
                            view.showLoading(false);
                            view.showError();
                        });
    }

    @Override
    public void openMovieDetails(Movie movie, @Nullable View v) {
        view.showMovieDetailsUi(movie, v);
    }
}
