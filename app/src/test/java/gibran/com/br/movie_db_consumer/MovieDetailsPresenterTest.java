package gibran.com.br.movie_db_consumer;


import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import gibran.com.br.movie_db_consumer.helpers.schedulers.ImmediateSchedulerProvider;
import gibran.com.br.movie_db_consumer.moviedetails.MovieDetailsContract;
import gibran.com.br.movie_db_consumer.moviedetails.MovieDetailsPresenter;
import gibran.com.br.moviedbservice.MovieDbApiModule;
import gibran.com.br.moviedbservice.model.Movie;
import gibran.com.br.moviedbservice.movie.MovieDataSource;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 05/02/18.
 */

public class MovieDetailsPresenterTest {

    @Mock
    private MovieDataSource movieDataSource;

    @Mock
    private MovieDetailsContract.ContractView contractView;

    private MovieDetailsContract.Presenter contractPresenter;
    private Movie MOVIE;
    private ImmediateSchedulerProvider schedulerProvider;

    @Before
    public void setupMoviePresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        // Get a reference to the class under test
        contractPresenter = new MovieDetailsPresenter(movieDataSource, contractView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);

        Gson gson = MovieDbApiModule.getDefaultGsonBuilder();
        InputStream movieRaw = getClass().getClassLoader().getResourceAsStream("movieDetailsResponse.json");
        Reader movieResponseJson = new BufferedReader(new InputStreamReader(movieRaw));
        MOVIE = gson.fromJson(movieResponseJson, Movie.class);
    }

    @Test
    public void loadMovieFromServiceAndLoadIntoView() throws Exception {
        when(movieDataSource.getMovieDetails(0)).thenReturn(Observable.just(MOVIE));
        contractPresenter.loadMovie(0);
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showMovie(eq(MOVIE));
        verify(contractView, never()).showMovieError();
    }

    @Test
    public void loadMovieException() throws Exception {
        when(movieDataSource.getMovieDetails(0)).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadMovie(0);
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showMovieError();
        verify(contractView, never()).showMovie(any());
    }
}
