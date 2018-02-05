package gibran.com.br.movie_db_consumer.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.ConverterHelper;
import gibran.com.br.movie_db_consumer.movie.MovieItem;
import gibran.com.br.moviedbservice.model.Movie;
import timber.log.Timber;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsFragment extends BaseFragment<MovieDetailsContract.Presenter>
        implements MovieDetailsContract.ContractView {

    private static final String LOADED_MOVIE = "loadedMovie";
    private static final String EXTRA_MOVIE_ID = "MovieId";

    @BindView(R.id.fragment_movie_details_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_movie_details_scrollview)
    View scrollView;
    @BindView(R.id.fragment_movie_details_year)
    TextView yearTextView;
    @BindView(R.id.fragment_movie_details_duration)
    TextView durationTextView;
    @BindView(R.id.fragment_movie_details_rating)
    TextView ratingTextView;
    @BindView(R.id.fragment_movie_details_description)
    TextView descriptionTextView;
    @BindView(R.id.fragment_movie_details_recommended_recycler)
    RecyclerView recommendedRecycler;
    @BindView(R.id.fragment_movie_details_recommended_container)
    View recommendedContainer;

    private Unbinder unbinder;
    private MovieDetailsContract.Presenter presenter;
    private Movie movie;

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!(getContext() instanceof MovieDetailsFragmentListener)) {
            throw new RuntimeException("Must implement MovieDetailsFragmentListener.");
        }
        int movieId = getMovieIdFromBundle();
        presenter.loadRecommended(movieId);
        if (savedInstanceState == null) {
            presenter.loadMovie(movieId);
        } else {
            movie = savedInstanceState.getParcelable(LOADED_MOVIE);
            if (movie == null) {
                //If we are restoring the state but dont have a movie, we load it
                presenter.loadMovie(movieId);
            } else {
                //If we already have the movies we simply add them to the list
                showMovie(movie);
                showLoading(false);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LOADED_MOVIE, movie);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showMovie(Movie movie) {
        scrollView.setVisibility(View.VISIBLE);
        this.movie = movie;
        if (getContext() != null) {
            ((MovieDetailsFragmentListener) getContext()).onImageLoaded(ActivityHelper.getMovieProfileImagePath(movie));
        }
        setupView(movie);
    }

    @Override
    public void showMovieError() {
        super.showError();
    }

    @Override
    public void showRecommended(ArrayList<Movie> relatedMovies) {
        FastItemAdapter<MovieItem> fastAdapter = new FastItemAdapter<>();
        recommendedContainer.setVisibility(View.VISIBLE);
        recommendedRecycler.setItemAnimator(new DefaultItemAnimator());
        recommendedRecycler.setHasFixedSize(true);
        recommendedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        addRecyclerItems(relatedMovies, fastAdapter);
        recommendedRecycler.setAdapter(fastAdapter);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            presenter.openMovieDetails(item.getModel(), v);
            return false;
        });
    }

    @Override
    public void showRecommendedError() {
        recommendedContainer.setVisibility(View.GONE);
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
    public void showMovieDetailsUi(Movie movie, @Nullable View v) {
        Intent intent = MovieDetailsActivity.createIntent(getContext(), movie.getTitle(), movie.getId());
        if (getContext() != null) {
            getContext().startActivity(intent);
        } else {
            Timber.e("showMovieDetailsUi: Context is null, ignoring click.");
        }
    }

    private int getMovieIdFromBundle() {
        Bundle bundle = this.getArguments();
        Integer movieId = bundle.getInt(EXTRA_MOVIE_ID, -1);
        if (movieId >= 0) {
            return movieId;
        } else {
            throw new RuntimeException("MovieId cannot be < 0");
        }
    }

    private void setupView(Movie movie) {
        yearTextView.setText(ConverterHelper.formatDate(movie.getReleaseDate()));
        durationTextView.setText(String.format(getContext().getResources().getString(R.string.movie_item_duration_text),
                        String.valueOf(movie.getRuntime())));
        ratingTextView.setText(String.valueOf(movie.getVoteAverage()));
        descriptionTextView.setText(movie.getOverview());
    }

    @Override
    protected void reloadFragment() {
        presenter.loadMovie(getMovieIdFromBundle());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void addRecyclerItems(ArrayList<Movie> movies, FastItemAdapter<MovieItem> fastAdapter) {
        for (Movie movie : movies) {
            MovieItem movieItem = new MovieItem(movie);
            fastAdapter.add(movieItem);
        }
    }
}
