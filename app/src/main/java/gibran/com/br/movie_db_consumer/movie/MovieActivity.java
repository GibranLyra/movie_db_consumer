package gibran.com.br.movie_db_consumer.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.about.AboutFragment;
import gibran.com.br.movie_db_consumer.helpers.ActivityHelper;
import gibran.com.br.movie_db_consumer.helpers.BottomNavigationViewBehavior;
import gibran.com.br.movie_db_consumer.helpers.EspressoIdlingResource;
import gibran.com.br.movie_db_consumer.helpers.schedulers.SchedulerProvider;
import gibran.com.br.moviedbservice.configuration.ConfigurationApi;
import gibran.com.br.moviedbservice.genre.GenreApi;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.view_container)
    ViewGroup viewContainer;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    private MovieContract.Presenter presenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, MovieActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            changeFragment(item.getItemId());
            return true;
        });
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        openMovieFragment();
    }

    private void changeFragment(@IdRes int itemId) {
        switch (itemId) {
            case R.id.action_movie: {
                openMovieFragment();
                break;
            }
            case R.id.action_about: {
                openAboutFragment();
                break;
            }
        }
    }

    private void openMovieFragment() {
        String moviesFragmentId = "movieFragmentId";
        MovieFragment movieFragment =
                (MovieFragment) getSupportFragmentManager().findFragmentByTag(moviesFragmentId);
        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            ActivityHelper.replaceFragmentToActivity(
                    getSupportFragmentManager(), movieFragment, R.id.view_container, moviesFragmentId);
        }
        presenter = new MoviePresenter(ConfigurationApi.getInstance(),
                GenreApi.getInstance(),
                movieFragment,
                SchedulerProvider.getInstance());
    }

    private void openAboutFragment() {
        AboutFragment aboutFragment = AboutFragment.newInstance();
        ActivityHelper.replaceFragmentToActivity(getSupportFragmentManager(), aboutFragment, R.id.view_container);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
