package gibran.com.br.movie_db_consumer.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.AppContext;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.moviedbservice.model.Configuration;
import gibran.com.br.moviedbservice.model.Genre;
import gibran.com.br.moviedbservice.model.Movie;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class MovieFragment extends BaseFragment<MovieContract.Presenter> implements MovieContract.ContractView {

    @BindView(R.id.fragment_movie_recycler_container)
    protected LinearLayout recyclerContainer;
    @BindView(R.id.fragment_movie_progress_bar)
    protected ProgressBar progressBar;
    private Unbinder unbinder;
    private MovieContract.Presenter presenter;
    private boolean isViewLoaded = false;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AppContext.getInstance().getConfiguration() == null) {
            presenter.loadConfiguration();
        } else {
            presenter.loadGenres();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
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
    public void configurationLoaded(Configuration configuration) {
        AppContext.getInstance().setConfiguration(configuration);
        presenter.loadGenres();
    }

    @Override
    public void genresLoaded(ArrayList<Genre> genres) {
        for (Genre genre : genres) {
            presenter.loadMovies(genre.getId(), genre.getName());
        }
    }

    @Override
    public void showMovies(String title, ArrayList<Movie> movies) {
        FastItemAdapter<MovieItem> fastAdapter = new FastItemAdapter<>();
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Timber.d("showMovies: Title %s", title);
        addRecyclerItems(movies, fastAdapter);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            presenter.openMovieDetails(item.getModel(), v);
            return false;
        });
        recyclerView.setAdapter(fastAdapter);
        recyclerContainer.addView(recyclerView);
    }

    @Override
    public void showError() {
        // TODO: 05/02/18 showError
//        showMovieError();
    }

    @Override
    public void showLoading(boolean show) {
        isViewLoaded = !show;
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovieDetailsUi(Movie movie, @Nullable View v) {

    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void reloadFragment() {
        if (AppContext.getInstance().getConfiguration() == null) {
            presenter.loadConfiguration();
        } else {
            presenter.loadGenres();
        }
    }

    private void addRecyclerItems(ArrayList<Movie> movies, FastItemAdapter<MovieItem> fastAdapter) {
        for (Movie movie : movies) {
            MovieItem movieItem = new MovieItem(movie);
            fastAdapter.add(movieItem);
        }
    }
}
