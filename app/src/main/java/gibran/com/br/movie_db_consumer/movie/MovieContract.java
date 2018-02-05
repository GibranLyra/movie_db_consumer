package gibran.com.br.movie_db_consumer.movie;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import gibran.com.br.movie_db_consumer.base.BaseContractPresenter;
import gibran.com.br.movie_db_consumer.base.BaseContractView;
import gibran.com.br.moviedbservice.model.Configuration;
import gibran.com.br.moviedbservice.model.Genre;
import gibran.com.br.moviedbservice.model.Movie;

public interface MovieContract {

    interface ContractView extends BaseContractView<Presenter> {

        void configurationLoaded(Configuration configuration);

        void genresLoaded(ArrayList<Genre> genres);

        void showMovies(ArrayList<Movie> movies);

        void showError();

        void showLoading(boolean show);

        boolean isActive();

        void showMovieDetailsUi(Movie movie, @Nullable android.view.View v);
    }

    interface Presenter extends BaseContractPresenter {
        void loadConfiguration();

        void loadGenres();

        void loadMovies(String genreId);

        void openMovieDetails(Movie movie, @Nullable android.view.View v);

    }
}
