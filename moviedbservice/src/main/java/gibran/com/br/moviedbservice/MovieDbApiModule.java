package gibran.com.br.moviedbservice;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.Clock;

import io.reactivex.annotations.Nullable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gibran.lyra on 05/02/2018.
 */
public class MovieDbApiModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY_NAME = "api_key";
    private static final String API_KEY_VALUE = "6cb6ca6b087d9f7ced6184178258c364";
    private static Retrofit retrofit;

    public static Gson getDefaultGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(@Nullable LoggingInterceptor.Level logLevel) {
        if (logLevel == null) {
            logLevel = LoggingInterceptor.Level.BASIC;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor(Clock.systemDefaultZone());
        loggingInterceptor.setLogLevel(logLevel);
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                    .build();
            Request.Builder requestBuilder = original.newBuilder().url(url);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        OkHttpClient okClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(getDefaultGsonBuilder()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
