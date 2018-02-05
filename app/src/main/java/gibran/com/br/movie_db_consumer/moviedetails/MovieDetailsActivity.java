package gibran.com.br.movie_db_consumer.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.schedulers.SchedulerProvider;
import gibran.com.br.moviedbservice.movie.MovieApi;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE_ID = "MovieId";
    public static final String EXTRA_MOVIE_TITLE = "MovieTitle";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MovieDetailsContract.Presenter presenter;

    public static Intent createIntent(Context context, String movieTitle, int movieId) {
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
        int movieId = -1;
        if (data != null) {
            movieId = data.getInt(EXTRA_MOVIE_ID, -1);
        }
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
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.view_container);
        }
        presenter = new MovieDetailsPresenter(MovieApi.getInstance(), fragment, SchedulerProvider.getInstance());
    }

}
