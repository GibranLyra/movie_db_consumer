package gibran.com.br.movie_db_consumer;

import android.app.Application;

import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.threetenabp.AndroidThreeTen;

import gibran.com.br.moviedbservice.LoggingInterceptor;
import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Configuration;
import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18 for movie_db_consumer.
 */

public class AppContext extends Application {

    private static AppContext instance;
    private RequestOptions requestOptions;
    private Configuration configuration;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeTimezone();
        initializeTimber();
        initializeApiModules();
        initializeGlideRequestOptions();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public RequestOptions getGlideRequestOptions() {
        return requestOptions;
    }


    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
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

}
