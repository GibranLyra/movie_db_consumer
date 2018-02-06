package gibran.com.br.movie_db_consumer.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.movie_db_consumer.AppContext;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.SchedulerProvider;
import gibran.com.br.moviedbservice.movie.MovieApi;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsFragmentListener {

    public static final String EXTRA_MOVIE_TITLE = "MovieTitle";
    private static final String EXTRA_MOVIE_ID = "MovieId";
    @BindView(R.id.activity_movie_details_toolbar_image)
    ImageView toolbarImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MovieDetailsContract.Presenter presenter;

    public static Intent createIntent(Context context, int movieId, String movieTitle) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        intent.putExtra(EXTRA_MOVIE_TITLE, movieTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        int movieId;
        if (data != null) {
            movieId = data.getInt(EXTRA_MOVIE_ID, -1);
            if (movieId >= 0) {
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(data.getString(EXTRA_MOVIE_TITLE));
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                setupViews(movieId);
            } else {
                throw new RuntimeException("MovieId cannot be < 0");
            }
        } else {
            throw new RuntimeException("Bundle cannot be null");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupViews(int movieId) {
        MovieDetailsFragment fragment =
                (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (fragment == null) {
            fragment = MovieDetailsFragment.newInstance(movieId);
            ActivityHelper.replaceFragmentToActivity(getSupportFragmentManager(), fragment, R.id.view_container);
        }
        presenter = new MovieDetailsPresenter(MovieApi.getInstance(), fragment, SchedulerProvider.getInstance());
    }

    @Override
    public void onImageLoaded(String imageUrl) {
        Glide.with(this)
                .setDefaultRequestOptions(AppContext.getInstance().getGlideRequestOptions())
                .load(imageUrl)
                .into(toolbarImage);
    }
}
