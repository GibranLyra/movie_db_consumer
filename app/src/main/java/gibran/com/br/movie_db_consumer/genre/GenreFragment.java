package gibran.com.br.movie_db_consumer.genre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.movie_db_consumer.movie.MovieItem;
import gibran.com.br.movie_db_consumer.moviedetails.MovieDetailsActivity;
import gibran.com.br.moviedbservice.model.Movie;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class GenreFragment extends BaseFragment<GenreContract.Presenter>
        implements GenreContract.ContractView {

    private static final String LOADED_MOVIE = "loadedGenre";
    private static final String EXTRA_GENRE_ID = "GenreId";

    @BindView(R.id.fragment_genre_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_genre_recycler)
    RecyclerView recycler;

    private Unbinder unbinder;
    private GenreContract.Presenter presenter;
    private ArrayList<Movie> movies;

    public static GenreFragment newInstance(int genreId) {
        GenreFragment fragment = new GenreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_GENRE_ID, genreId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        unbinder = ButterKnife.bind(this, view);
        int genreId = getGenreIdFromBundle();
        if (savedInstanceState == null) {
            presenter.loadGenreMovies(genreId);
        } else {
            movies = savedInstanceState.getParcelable(LOADED_MOVIE);
            if (movies == null) {
                //If we are restoring the state but dont have a movies, we load it
                presenter.loadGenreMovies(genreId);
            } else {
                //If we already have the movies we simply add them to the list
                showMovies(movies);
                showLoading(false);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LOADED_MOVIE, movies);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        setupView(movies);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovieDetailsUi(Movie movie, @Nullable View transitionView) {
        Intent intent = MovieDetailsActivity.createIntent(getContext(), movie.getId(), movie.getTitle());
        if (getContext() != null) {
            if (transitionView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) getContext(), transitionView,
                                "movie_image_transition");
                getContext().startActivity(intent, options.toBundle());
            } else {
                getContext().startActivity(intent);
            }
        } else {
            Timber.e("showMovieDetailsUi: Context is null, ignoring click.");
        }
    }

    private int getGenreIdFromBundle() {
        Bundle bundle = this.getArguments();
        Integer genreId = bundle.getInt(EXTRA_GENRE_ID, -1);
        if (genreId >= 0) {
            return genreId;
        } else {
            throw new RuntimeException("GenreId cannot be < 0");
        }
    }

    private void setupView(ArrayList<Movie> movies) {
        FastItemAdapter<MovieItem> fastAdapter = new FastItemAdapter<>();
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        addRecyclerItems(movies, fastAdapter);
        recycler.setAdapter(fastAdapter);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            presenter.openMovieDetails(item.getModel(), v);
            return false;
        });
    }

    @Override
    protected void reloadFragment() {
        presenter.loadGenreMovies(getGenreIdFromBundle());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(GenreContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void addRecyclerItems(ArrayList<Movie> movies, FastItemAdapter<MovieItem> fastAdapter) {
        for (Movie movie : movies) {
            MovieItem movieItem = new MovieItem(movie);
            fastAdapter.add(movieItem);
        }
    }
}
