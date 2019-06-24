package godaa.android.com.weathertaskapp.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import godaa.android.com.weathertaskapp.data.NetworkBoundResource;
import godaa.android.com.weathertaskapp.data.local.LocalDataSource;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.remote.api.ApiResponse;
import godaa.android.com.weathertaskapp.data.source.BaseSource;
import godaa.android.com.weathertaskapp.utils.AppExecutors;
import godaa.android.com.weathertaskapp.utils.RateLimiter;
import godaa.android.com.weathertaskapp.utils.Resource;
import timber.log.Timber;

@Singleton
public class WeatherRepositoryImpl implements WeatherRepository<LiveData<Resource<WeatherResponse>>> {

    private final LocalDataSource<AccuWeatherDb, LiveData<AccuWeatherDb>> mLocalDataSource;

    private final BaseSource<LiveData<ApiResponse<AccuWeatherDb>>> mBaseSource;

    private final AppExecutors mExecutors;

    private RateLimiter<String> rateLimit = new RateLimiter<>(30, TimeUnit.MINUTES);

    @Inject
    WeatherRepositoryImpl(AppExecutors executors, LocalDataSource dataSource, BaseSource baseSource) {
        mExecutors = executors;
        mLocalDataSource = dataSource;
        mBaseSource = baseSource;
    }

    @Override
    public LiveData<Resource<List<AccuWeatherDb>>> loadData(String input) {
        return new NetworkBoundResource<List<AccuWeatherDb>, List<AccuWeatherDb>>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<AccuWeatherDb> item) {
                mLocalDataSource.save(item);
                Timber.d("Weather response saved");
            }

            @Override
            protected boolean shouldFetch(@Nullable List<AccuWeatherDb> data) {
                if (data != null) {
                    Timber.d("LOCATION %s", data.getLocation().getCountry());
                    return mLocalDataSource.shouldFetch(data) || mLocalDataSource.hasLocationChanged(data);
                }
                return data == null || rateLimit.shouldFetch(input);
            }

            @NonNull
            @Override
            protected LiveData<List<AccuWeatherDb>> loadFromDb() {
                Timber.d("loading Weather data from database");
                return mLocalDataSource.get();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<AccuWeatherDb>>> createCall() {
                Timber.d("Weather data fetch started");
                return mBaseSource.get();
            }

            @Override
            protected void onFetchFailed() {
                Timber.d("Fetch failed!!");
                rateLimit.reset(input);
            }

        }.asLiveData();
    }
}
