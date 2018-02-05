package gibran.com.br.movie_db_consumer.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseFragment;
import gibran.com.br.moviedbservice.model.Movie;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsFragment extends BaseFragment<MovieDetailsContract.Presenter>
        implements MovieDetailsContract.ContractView {

    private static final String LOADED_MOVIE = "loadedMovie";
    @BindView(R.id.fragment_movie_details_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_movie_details_author)
    TextView authorView;
    @BindView(R.id.fragment_movie_details_image)
    ImageView imageView;
    @BindView(R.id.fragment_movie_details_avatar)
    ImageView avatarView;
    @BindView(R.id.fragment_movie_details_likes)
    TextView likesView;
    @BindView(R.id.fragment_movie_details_buckets_count)
    TextView bucketsCountView;
    @BindView(R.id.fragment_movie_details_description)
    TextView descriptionView;
    @BindView(R.id.fragment_movie_details_views_count)
    TextView countsView;
    @BindView(R.id.fragment_movie_details_created_at)
    TextView createdAtView;

    private static final String EXTRA_MOVIE = "MovieId";
    private Unbinder unbinder;
    private MovieDetailsContract.Presenter presenter;
    private Movie movie;

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            presenter.loadMovie(getMovieFromBundle().getId());
        } else {
            movie = savedInstanceState.getParcelable(LOADED_MOVIE);
            if (movie == null) {
                //If we are restoring the state but dont have a movie, we load it
                presenter.loadMovie(getMovieFromBundle().getId());
            } else {
                //If we already have the movies we simply add them to the list
                showMovie(movie);
                showLoading(false);
            }
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LOADED_MOVIE, movie);
    }

    @Override
    public void showMovie(Movie movie) {
        this.movie = movie;
        setupView(movie);
    }

    @Override
    public void showMovieError() {
        super.showError();
    }

    private Movie getMovieFromBundle() {
        Bundle bundle = this.getArguments();
        Movie movie = bundle.getParcelable(EXTRA_MOVIE);
        if (movie != null) {
            return movie;
        } else {
            throw new RuntimeException("Movie cannot be null");
        }
    }

    private void setupView(Movie movie) {
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
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void reloadFragment() {
        presenter.loadMovie(getMovieFromBundle().getId());
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
