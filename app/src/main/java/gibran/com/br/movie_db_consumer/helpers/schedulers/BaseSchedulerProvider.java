package gibran.com.br.movie_db_consumer.helpers.schedulers;

import io.reactivex.Scheduler;


/**
 * Allow providing different types of {@link Scheduler}s.
 */
public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
