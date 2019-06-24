package godaa.android.com.weathertaskapp.data;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import godaa.android.com.weathertaskapp.data.remote.api.ApiResponse;
import godaa.android.com.weathertaskapp.utils.AppExecutors;
import godaa.android.com.weathertaskapp.utils.Resource;
import timber.log.Timber;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * <p>
 * You can read more about it in the [Architecture Guide]
 * (https://developer.android.com/jetpack/docs/guide#addendum).
 *
 * @param <ResultType>
 * @param <RequestType> </RequestType></ResultType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors mExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        mExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    /**
     * Fetch the data from network and persist into DB and then
     * send it back to UI.
     */
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (response.isSuccessful()) {
                mExecutors.diskIO().execute(() -> {
                    if (response.body != null) {
                        saveCallResult(response.body);
                    }
                    mExecutors.mainThread().execute(() -> {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb(), newData -> setValue(Resource.success(newData)));
                    });
                });
            } else {
                onFetchFailed();
                Timber.d("error %s", response.getError().getMessage());
                result.addSource(dbSource, newData -> setValue(Resource.error(response.getError().getMessage(), newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected abstract void onFetchFailed();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
