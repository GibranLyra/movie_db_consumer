package gibran.com.br.movie_db_consumer.moviedetails;

import gibran.com.br.movie_db_consumer.helpers.ObserverHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider;
import gibran.com.br.moviedbservice.movie.MovieDataSource;
import io.reactivex.disposables.Disposable;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDataSource movieDatasource;
    private MovieDetailsContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getMovieDisposable;

    public MovieDetailsPresenter(MovieDataSource movieDataSource, MovieDetailsContract.ContractView view,
                                 BaseSchedulerProvider schedulerProvider) {
        this.movieDatasource = movieDataSource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadMovie(int id) {
        view.showLoading(true);
        getMovieDisposable = movieDatasource.getMovieDetails(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(movie -> {
                    view.showMovie(movie);
                    view.showLoading(false);
                }, e -> {
                    view.showMovieError();
                    view.showLoading(false);
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getMovieDisposable);
    }
}
