package gibran.com.br.movie_db_consumer.moviedetails;


import java.util.ArrayList;

import gibran.com.br.movie_db_consumer.base.BaseContractPresenter;
import gibran.com.br.movie_db_consumer.base.BaseContractView;
import gibran.com.br.moviedbservice.model.Movie;

public interface MovieDetailsContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showMovie(Movie movie);

        void showMovieError();

        void showRelated(ArrayList<Movie> relatedMovies);

        void showLoading(boolean show);

        boolean isActive();
    }

    interface Presenter extends BaseContractPresenter {
        void loadMovie(int movieId);

        void loadRelated(int movieId);
    }
}
