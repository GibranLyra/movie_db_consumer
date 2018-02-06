package gibran.com.br.movie_db_consumer.genre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.EspressoIdlingResource;
import gibran.com.br.movie_db_consumer.helpers.schedulers.SchedulerProvider;
import gibran.com.br.moviedbservice.genre.GenreApi;

public class GenreActivity extends AppCompatActivity {

    public static final String EXTRA_GENRE_TITLE = "GenreTitle";
    private static final String EXTRA_GENRE_ID = "GenreId";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    private GenreContract.Presenter presenter;

    public static Intent createIntent(Context context, int genreId, String genreTitle) {
        Intent intent = new Intent(context, GenreActivity.class);
        intent.putExtra(EXTRA_GENRE_ID, genreId);
        intent.putExtra(EXTRA_GENRE_TITLE, genreTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        int genreId;
        if (data != null) {
            genreId = data.getInt(EXTRA_GENRE_ID, -1);
            if (genreId >= 0) {
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(data.getString(EXTRA_GENRE_TITLE));
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                setupViews(genreId);
            } else {
                throw new RuntimeException("GenreId cannot be < 0");
            }
        } else {
            throw new RuntimeException("Bundle cannot be null");
        }
    }

    private void setupViews(int genreId) {
        GenreFragment genreFragment =
                (GenreFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (genreFragment == null) {
            genreFragment = GenreFragment.newInstance(genreId);
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), genreFragment, R.id.view_container);
        }
        presenter = new GenrePresenter(GenreApi.getInstance(),
                genreFragment,
                SchedulerProvider.getInstance());
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
