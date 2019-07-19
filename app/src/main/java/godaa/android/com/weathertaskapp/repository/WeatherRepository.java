package godaa.android.com.weathertaskapp.repository;

import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import godaa.android.com.weathertaskapp.data.local.WeatherDao;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.api.ApiService;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    WeatherDao local;
    ApiService remote;
    private static final String TAG = WeatherRepository.class.getSimpleName();

    public WeatherRepository(WeatherDao local, ApiService remote) {
        this.local = local;
        this.remote = remote;
    }

    public Flowable<List<AccuWeatherDb>> getWeather() {
        return local.getWeather();
    }

    /*public Flowable<List<LocationSearchModel>> getRemoteListCitiesWeather(String q) {
        return remote.getAccuWeatherCities(q);
    }*/

    /*public LiveData<List<LocationSearchModel>> getRemoteListCitiesWeather(String q) {
        final MutableLiveData<List<LocationSearchModel>> data = new MutableLiveData<>();
        remote.getAccuWeatherCities(q)
                .enqueue(new Callback<List<LocationSearchModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationSearchModel>> call, Response<List<LocationSearchModel>> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<LocationSearchModel>> call, Throwable t) {
                        data.setValue(null);

                    }
                });
        return data;
    }
*/
    public Single<List<AccuWeatherModel>> getRemotegetAccuWeatherData(String cityKey) {
        return remote.getAccuWeatherData(cityKey);
    }

    /*public LiveData<AccuWeatherModel> getRemotegetAccuWeatherData(String cityKey) {
        final MutableLiveData<AccuWeatherModel> data = new MutableLiveData<>();
        remote.getAccuWeatherData(cityKey)
                .enqueue(new Callback<List<AccuWeatherModel>>() {
                    @Override
                    public void onResponse(Call<List<AccuWeatherModel>> call, Response<List<AccuWeatherModel>> response) {
                        if (response.body() != null)
                            data.setValue(response.body().get(0));
                    }

                    @Override
                    public void onFailure(Call<List<AccuWeatherModel>> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;


    }
    public LiveData<AccuWeather5DayModel> getAccuWeatherData5days(String cityKey) {
        final MutableLiveData<AccuWeather5DayModel> data = new MutableLiveData<>();
        remote.getAccuWeatherData5days(cityKey)
                .enqueue(new Callback<AccuWeather5DayModel>() {
                    @Override
                    public void onResponse(Call<AccuWeather5DayModel> call, Response<AccuWeather5DayModel> response) {
                        if (response.body() != null)
                            data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<AccuWeather5DayModel> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;


    }
*/
    public Single<AccuWeather5DayModel> getAccuWeatherData5days(String cityKey) {
        return remote.getAccuWeatherData5days(cityKey);
    }
    public Single<LocationSearchModel> getAccuWeatherBylocation(String q) {
        return remote.getAccuWeatherBylocation(q);
    }

    public Observable<Boolean> insertWeatherCity(final AccuWeatherDb weatherDb) {
        return Observable.fromCallable(() -> {
            local.insertAll(weatherDb);
            return true;
        });
    }
    public Completable insertUsingCompleteWeatherCity(final AccuWeatherDb weatherDb) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                local.insertWeatherResponse(weatherDb);

            }
        });
    }
    public Completable deleteWeather(final String keyLocation) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                local.deleteWeather(keyLocation);

            }
        });
    }
  /*  public void insertWeatherCity(AccuWeatherDb weatherDb) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //just to show that there is a paging
                local.insertWeatherResponse(weatherDb);

            }
        });
    }*/

    public void clearAllData() {
        local.clearAllData();
    }

}
