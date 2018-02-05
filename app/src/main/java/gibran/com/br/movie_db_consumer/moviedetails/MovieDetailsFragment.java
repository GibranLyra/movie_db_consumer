package gibran.com.br.movie_db_consumer.moviedetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.ConverterHelper;
import gibran.com.br.moviedbservice.model.Movie;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsFragment extends BaseFragment<MovieDetailsContract.Presenter>
        implements MovieDetailsContract.ContractView {

    private static final String LOADED_MOVIE = "loadedMovie";
    private static final String EXTRA_MOVIE_ID = "MovieId";

    @BindView(R.id.fragment_movie_details_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_movie_details_year)
    TextView yearTextView;
    @BindView(R.id.fragment_movie_details_duration)
    TextView durationTextView;
    @BindView(R.id.fragment_movie_details_rating)
    TextView ratingTextView;
    @BindView(R.id.fragment_movie_details_description)
    TextView descriptionTextView;

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
        if (savedInstanceState == null) {
            presenter.loadMovie(getMovieIdFromBundle());
        } else {
            movie = savedInstanceState.getParcelable(LOADED_MOVIE);
            if (movie == null) {
                //If we are restoring the state but dont have a movie, we load it
                presenter.loadMovie(getMovieIdFromBundle());
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
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
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
        durationTextView.setText(String.valueOf(movie.getRuntime()));
        ratingTextView.setText(String.valueOf(movie.getVoteAverage()));
        descriptionTextView.setText(movie.getOverview());
//        if (!TextUtils.isEmpty(movie.getImages().getNormal())) {
//            Glide.with(getContext())
//                    .load(movie.getImages().getNormal())
//                    .into(imageView);
//        } else {
//            Glide.with(getContext())
//                    .load(R.drawable.placeholder)
//                    .into(imageView);
//        }
//        if (!TextUtils.isEmpty(movie.getUser().getAvatarUrl())) {
//            Glide.with(getContext())
//                    .load(movie.getUser().getAvatarUrl())
//                    .into(avatarView);
//        }
//
//        //Some Movies don`t have description, so we check if it have one
//        if (!TextUtils.isEmpty(movie.getDescription())) {
//            descriptionView.setText(Html.fromHtml(movie.getDescription()));
//        }
//        authorView.setText(String.format(getResources().getString(R.string.movie_item_view_author_text),
//                String.valueOf(movie.getUser().getName())));
//        likesView.setText(String.format(getResources().getString(R.string.movie_item_view_likes_text),
//                String.valueOf(movie.getBucketsCount())));
//        bucketsCountView.setText(String.format(getResources().getString(R.string.movie_item_view_buckets_text),
//                String.valueOf(movie.getBucketsCount())));
//        countsView.setText(String.format(getResources().getString(R.string.movie_item_view_count_text),
//                String.valueOf(movie.getViewsCount())));
//        String createdAt = ActivityHelper.getFormatedDate(movie.getCreatedAt());
//        createdAtView.setText(String.format(getResources().getString(R.string.movie_item_created_at_text),
//                createdAt));
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
}
