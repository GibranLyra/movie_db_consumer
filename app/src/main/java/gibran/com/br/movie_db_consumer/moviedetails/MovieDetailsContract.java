package gibran.com.br.movie_db_consumer.moviedetails;


import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

import gibran.com.br.movie_db_consumer.base.BaseContractPresenter;
import gibran.com.br.movie_db_consumer.base.BaseContractView;
import gibran.com.br.moviedbservice.model.Movie;

public interface MovieDetailsContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showMovie(Movie movie);

        void showMovieError();

        void showRecommended(ArrayList<Movie> relatedMovies);

        void showLoading(boolean show);

        void showMovieDetailsUi(Movie movie, @Nullable android.view.View v);

        void showRecommendedError();

        boolean isActive();
    }

    interface Presenter extends BaseContractPresenter {
        void loadMovie(int movieId);

        void loadRecommended(int movieId);

        void openMovieDetails(Movie movie, @Nullable View v);
    }
}
