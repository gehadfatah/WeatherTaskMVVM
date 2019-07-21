package godaa.android.com.weathertaskapp.data.remote.interceptors;

import java.io.IOException;

import godaa.android.com.weathertaskapp.data.remote.client.SettingsAPI;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class APIKeyInterceptor implements Interceptor {

    private SettingsAPI settingsAPI;

    public  APIKeyInterceptor(SettingsAPI settingsAPI) {
        this.settingsAPI = settingsAPI;
    }

    public SettingsAPI getSetting() {
        return settingsAPI;
    }
    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", settingsAPI.getApiKey())
                .build();

        Request.Builder requestBuilder = original.newBuilder().url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
