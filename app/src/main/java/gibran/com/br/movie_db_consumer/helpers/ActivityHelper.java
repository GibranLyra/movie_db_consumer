package gibran.com.br.movie_db_consumer.helpers;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import gibran.com.br.movie_db_consumer.AppContext;
import gibran.com.br.moviedbservice.model.Movie;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ActivityHelper {

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public static String getMoviePosterImagePath(Movie movie) {
        return String.format("%s%s%s",
                AppContext.getInstance().getConfiguration().getImages().getBaseUrl(),
                AppContext.getInstance().getConfiguration().getImages().getPosterSizes().get(6),
                movie.getPosterPath());
    }

    public static String getMovieBackDropImagePath(Movie movie) {
        return String.format("%s%s%s",
                AppContext.getInstance().getConfiguration().getImages().getBaseUrl(),
                AppContext.getInstance().getConfiguration().getImages().getBackdropSizes().get(0),
                movie.getBackdropPath());
    }
}
