package godaa.android.com.weathertaskapp.data.remote.api;

import java.util.concurrent.TimeUnit;

import godaa.android.com.weathertaskapp.data.remote.APIKeyInterceptor;
import godaa.android.com.weathertaskapp.data.remote.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient apiClient;
    private SettingsAPI settings;

    private static APIClient getInstance() {
        if (apiClient == null)
            apiClient = new APIClient(new SettingsAPI());
        return apiClient;
    }

    private APIClient(SettingsAPI settings) {
        this.settings = settings;
    }

    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(settings.getTimeout(), TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(settings.getTimeout(), TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(getApiKeyInterceptor(settings));

        return httpClient.build();
    }

    private APIKeyInterceptor getApiKeyInterceptor(SettingsAPI settings) {
        return new APIKeyInterceptor(settings);
    }

    public static ApiService getWeatherAPI() {
        return new Retrofit.Builder()
                .baseUrl(getInstance().settings.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(buildCallAdapterFactory())
                .client(getInstance().getHttpClient())
                .build()
                .create(ApiService.class);
    }
    private static CallAdapter.Factory buildCallAdapterFactory() {
        return new LiveDataCallAdapterFactory();
    }
}
