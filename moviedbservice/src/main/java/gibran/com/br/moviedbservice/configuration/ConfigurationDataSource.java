package gibran.com.br.moviedbservice.configuration;

import gibran.com.br.moviedbservice.model.Configuration;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 30/07/17.
 */

public interface ConfigurationDataSource {
    Observable<Configuration> getConfiguration();
}
