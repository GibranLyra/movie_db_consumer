package gibran.com.br.movie_db_consumer.movie;

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
import gibran.com.br.moviedbservice.configuration.ConfigurationApi;
import gibran.com.br.moviedbservice.movie.MoviesApi;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    private MovieContract.Presenter presenter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MovieActivity.class);
        return intent;
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
// TODO: 05/02/18 fix me
//        createDrawer();
        setupViews();
    }

//    private void createDrawer() {
//        AccountHeader headerResult = new AccountHeaderBuilder()
//                .withActivity(this)
//                .withHeaderBackground(R.drawable.header)
//                .addProfiles(
//                        new ProfileDrawerItem().withName("Gibran Lyra").withEmail("lyra.gibran@gmail.com")
//                                .withIcon(getResources().getDrawable(R.drawable.profile))
//                )
//                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
//                .build();
//        //Now create your drawer and pass the AccountHeader.Result
//        new DrawerBuilder()
//                .withActivity(this)
//                .withToolbar(toolbar)
//                .withActionBarDrawerToggle(true)
//                .withAccountHeader(headerResult)
//                .withActionBarDrawerToggleAnimated(true)
//                .build();
//    }

    private void setupViews() {
        MovieFragment movieFragment =
                (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), movieFragment, R.id.view_container);
        }
        presenter = new MoviePresenter(ConfigurationApi.getInstance(),
                MoviesApi.getInstance(),
                movieFragment,
                SchedulerProvider.getInstance());
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
