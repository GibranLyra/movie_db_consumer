package gibran.com.br.movie_db_consumer.moviedetails;

import gibran.com.br.movie_db_consumer.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private ShotsDataSource shotsRepository;
    private MovieDetailsContract.ContractView view;
    private MovieDetailsContract.Presenter presenterContract;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getShotDisposable;

    public MovieDetailsPresenter(ShotsDataSource shotsRepository, MovieDetailsContract.ContractView view,
                                 BaseSchedulerProvider schedulerProvider) {
        this.shotsRepository = shotsRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadMovie(int id) {
        view.showLoading(true);
        getShotDisposable = shotsRepository.getShot(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(shot -> {
                    view.showMovie(shot);
                    view.showLoading(false);
                }, e -> {
                    Timber.e(e, "loadMovie: %s", e.getMessage());
                    view.showMovieError();
                    view.showLoading(false);
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getShotDisposable);
    }
}
