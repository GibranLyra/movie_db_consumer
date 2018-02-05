package gibran.com.br.moviedbservice.configuration;

import gibran.com.br.moviedbservice.model.Configuration;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by gibranlyra on 23/08/17.
 */

public interface ConfigurationService {

    @GET("configuration")
    Observable<Configuration> getConfiguration();
}
