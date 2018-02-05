package gibran.com.br.moviedbservice.configuration;

import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Configuration;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 05/02/2018.
 */
public class ConfigurationApi implements ConfigurationDataSource {
    private static ConfigurationApi instance;
    private final ConfigurationService configurationService;

    private ConfigurationApi() {
        Retrofit retrofit = MovieDbApiModule.getRetrofit();
        configurationService = retrofit.create(ConfigurationService.class);
    }

    public static ConfigurationApi getInstance() {
        if (instance == null) {
            instance = new ConfigurationApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new ConfigurationApi();
    }


    @Override
    public Observable<Configuration> getConfiguration() {
        return configurationService.getConfiguration()
                .doOnError(e -> Timber.e(e, "getConfiguration: %s", e.getMessage()));
    }
}