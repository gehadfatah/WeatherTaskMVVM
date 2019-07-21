package godaa.android.com.weathertaskapp.common.di.modules;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import godaa.android.com.weathertaskapp.BuildConfig;
import godaa.android.com.weathertaskapp.data.remote.client.ApiService;
import godaa.android.com.weathertaskapp.data.remote.client.LiveDataCallAdapterFactory;
import godaa.android.com.weathertaskapp.data.remote.client.SettingsAPI;
import godaa.android.com.weathertaskapp.data.remote.interceptors.APIKeyInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {
    private SettingsAPI settingsAPI;

    @Provides
    APIKeyInterceptor provideInterceptor() {
        return new APIKeyInterceptor(new SettingsAPI());
    }

    @Provides
    SettingsAPI prSettingsAPI() {
        return new SettingsAPI();
    }

    @Provides
    OkHttpClient provideOkHttp(APIKeyInterceptor interceptor, SettingsAPI settingsAPI) {
     /*   return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();*/
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(settingsAPI.getTimeout(), TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(settingsAPI.getTimeout(), TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(interceptor);

        return httpClient.build();
    }

 /*   @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(settingsAPI.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( new LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }*/

    @Provides
    ApiService provideApiService(OkHttpClient okHttpClient, SettingsAPI settingsAPI) {
        return new Retrofit.Builder()
                .baseUrl(settingsAPI.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiService.class);
    }
}
