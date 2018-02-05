package gibran.com.br.movie_db_consumer;

import android.app.Application;

import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.threetenabp.AndroidThreeTen;

import gibran.com.br.moviedbservice.LoggingInterceptor;
import gibran.com.br.moviedbservice.MovieDbApiModule;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18 for movie_db_consumer.
 */

public class AppContext extends Application {

    private static AppContext instance;
    private RequestOptions requestOptions;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeTimezone();
        initializeTimber();
        initializeApiModules();
    }

    private void initializeTimezone() {
        AndroidThreeTen.init(this);
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeApiModules() {
        MovieDbApiModule.setRetrofit(LoggingInterceptor.Level.HEADERS);
    }

    private RequestOptions initializeGlideRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
        }
        return requestOptions
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);
    }

    public RequestOptions getGlideRequestOptions() {
        return requestOptions;
    }
}
