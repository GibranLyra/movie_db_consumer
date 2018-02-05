package gibran.com.br.movie_db_consumer.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.moviedbservice.model.Movie;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class MovieFragment extends BaseFragment<MovieContract.Presenter> implements MovieContract.ContractView {

    private static final String LOADED_SHOTS = "loadedShots";
    @BindView(R.id.fragment_movie_progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.fragment_movie_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.fragment_movie_swipe)
    protected SwipeRefreshLayout swipeToRefresh;
    protected boolean isViewLoaded;
    private Unbinder unbinder;
    private MovieContract.Presenter presenter;
    private FastItemAdapter<MovieItem> fastAdapter;
    @Nullable
    private Bundle savedInstanceState;
    private ArrayList<Movie> movies;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeToRefresh.setOnRefreshListener(() -> reloadFragment());
        swipeToRefresh.setColorSchemeResources(
                R.color.accent,
                R.color.colorSecondary,
                R.color.primary);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState == null) {
            presenter.loadMovies();
        } else {
            movies = savedInstanceState.getParcelableArrayList(LOADED_SHOTS);
            if (movies == null) {
                //If we are restoring the state but dont have movies, we load it.
                presenter.loadMovies();
            } else {
                //If we already have the movies we simply add them to the list
                showMovies(movies);
                showLoading(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (fastAdapter != null) {
            //add the values which need to be saved from the adapter to the bundle
            outState = fastAdapter.saveInstanceState(outState);
        }
        outState.putParcelableArrayList(LOADED_SHOTS, movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showMovies(ArrayList<Movie> movies) {
        swipeToRefresh.setRefreshing(false);
        this.movies = movies;
        fastAdapter = new FastItemAdapter<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fastAdapter.clear();
        addRecyclerItems(movies);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            presenter.openMovieDetails(item.getModel(), v);
            return false;
        });
        //restore selections (this has to be done after the items were added
        fastAdapter.withSavedInstanceState(savedInstanceState);
    }

    @Override
    public void showMoviesError() {
        swipeToRefresh.setRefreshing(false);
        // TODO: 05/02/18 showError
//        showMovieError();
    }

    @Override
    public void showLoading(boolean show) {
        isViewLoaded = !show;
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showShotDetailsUi(Movie movie, @Nullable View v) {

    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void addRecyclerItems(ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            MovieItem movieItem = new MovieItem(movie);
            fastAdapter.add(movieItem);
        }
    }

    @Override
    protected void reloadFragment() {
        presenter.loadMovies();
    }
}
