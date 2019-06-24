package godaa.android.com.weathertaskapp.di.module;

import android.app.Application;


import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import godaa.android.com.weathertaskapp.data.remote.LiveDataCallAdapterFactory;
import godaa.android.com.weathertaskapp.data.remote.api.ApiService;
import godaa.android.com.weathertaskapp.data.remote.interceptors.ApiInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module(includes = ClientModule.class)
public class ApiModule {

    @Provides
    @Singleton
    static Cache provideCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(Cache cache, @Named("connect") ApiInterceptor connectivityInterceptor,
                                            @Named("request") ApiInterceptor requestInterceptor) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(cache);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(requestInterceptor);
        httpClient.addInterceptor(connectivityInterceptor);
        return httpClient.build();
    }


    @Singleton
    @Provides
    static ApiService provideApiService( OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://api.accuweather.com/")
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(ApiService.class);
    }
}
