package gibran.com.br.movie_db_consumer.genre;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import gibran.com.br.movie_db_consumer.base.BaseContractPresenter;
import gibran.com.br.movie_db_consumer.base.BaseContractView;
import gibran.com.br.moviedbservice.model.Movie;

public interface GenreContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showMovies(ArrayList<Movie> movies);

        void showError();

        void showLoading(boolean show);

        boolean isActive();

        void showMovieDetailsUi(Movie movie, @Nullable android.view.View v);
    }

    interface Presenter extends BaseContractPresenter {
        void loadGenreMovies(int genreId);

        void openMovieDetails(Movie movie, @Nullable android.view.View v);

    }
}
