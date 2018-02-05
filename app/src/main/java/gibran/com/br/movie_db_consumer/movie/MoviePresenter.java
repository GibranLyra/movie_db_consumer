package gibran.com.br.movie_db_consumer.movie;

import android.support.annotation.Nullable;
import android.view.View;

import gibran.com.br.movie_db_consumer.helpers.EspressoIdlingResource;
import gibran.com.br.movie_db_consumer.helpers.ObserverHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider;
import gibran.com.br.moviedbservice.model.Movie;
import gibran.com.br.moviedbservice.model.movie.MoviesDataSource;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class MoviePresenter implements MovieContract.Presenter {

    private MoviesDataSource movieRepository;
    private MovieContract.ContractView view;
    private gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider schedulerProvider;
    private Disposable getShotsDisposable;


    public MoviePresenter(MoviesDataSource movieRepository,
                          MovieContract.ContractView view,
                          BaseSchedulerProvider schedulerProvider) {
        this.movieRepository = movieRepository;
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
        ObserverHelper.safelyDispose(getShotsDisposable);
    }

    @Override
    public void loadMovies() {
        view.showLoading(true);
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice
        getShotsDisposable = movieRepository.getPopular()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(shots -> {
                            view.showLoading(false);
                            view.showMovies(shots);
                        },
                        e -> {
                            Timber.e(e, "loadMovies: %s", e.getMessage());
                            view.showLoading(false);
                            view.showMoviesError();
                        });

    }

    @Override
    public void openMovieDetails(Movie movie, @Nullable View v) {
        view.showShotDetailsUi(movie, v);
    }
}
