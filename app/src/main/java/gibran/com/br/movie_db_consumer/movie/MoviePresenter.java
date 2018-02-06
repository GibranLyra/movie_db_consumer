package gibran.com.br.movie_db_consumer.movie;

import android.support.annotation.Nullable;
import android.view.View;

import gibran.com.br.movie_db_consumer.helpers.EspressoIdlingResource;
import gibran.com.br.movie_db_consumer.helpers.ObserverHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider;
import gibran.com.br.moviedbservice.configuration.ConfigurationDataSource;
import gibran.com.br.moviedbservice.genre.GenreDataSource;
import gibran.com.br.moviedbservice.model.Movie;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class MoviePresenter implements MovieContract.Presenter {

    private ConfigurationDataSource configurationDataSource;
    private GenreDataSource genreDataSource;
    private MovieContract.ContractView view;
    private gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider schedulerProvider;
    private Disposable getMoviesDisposable;
    private Disposable getConfigurationDisposable;
    private Disposable getGenresDisposable;


    public MoviePresenter(ConfigurationDataSource configurationDataSource,
                          GenreDataSource genreDataSource,
                          MovieContract.ContractView view,
                          BaseSchedulerProvider schedulerProvider) {
        this.configurationDataSource = configurationDataSource;
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
        ObserverHelper.safelyDispose(getConfigurationDisposable, getMoviesDisposable, getGenresDisposable);
    }

    @Override
    public void loadConfiguration() {
        view.showLoading(true);
        EspressoIdlingResource.increment(); // App is busy until further notice
        getConfigurationDisposable = configurationDataSource.getConfiguration()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(configuration -> view.configurationLoaded(configuration),
                        e -> {
                            Timber.e(e, "loadConfiguration: %s", e.getMessage());
                            view.showLoading(false);
                            view.showError();
                        });
    }

    @Override
    public void loadGenres() {
        view.showLoading(true);
        getGenresDisposable = genreDataSource.getMovieGenres()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(genres -> view.genresLoaded(genres),
                        e -> {
                            Timber.e(e, "loadGenres: %s", e.getMessage());
                            view.showLoading(false);
                            view.showError();
                        });
    }

    @Override
    public void loadMovies(int genreId, String title) {
        view.showLoading(true);
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        getMoviesDisposable = genreDataSource.getMovies(genreId, 1)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(movies -> {
                            view.showLoading(false);
                            view.showMovies(genreId, title, movies);
                        },
                        e -> {
                            Timber.e(e, "loadMovies: %s", e.getMessage());
                            view.showLoading(false);
                            view.showError();
                        });
    }

    @Override
    public void openMovieDetails(Movie movie, @Nullable View v) {
        view.showMovieDetailsUi(movie, v);
    }

    @Override
    public void openGenre(int genreId, String genreTitle, @Nullable View v) {
        view.showGenreUi(genreId, genreTitle, v);
    }
}
