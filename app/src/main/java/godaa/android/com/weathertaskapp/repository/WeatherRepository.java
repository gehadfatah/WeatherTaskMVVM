package godaa.android.com.weathertaskapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


import godaa.android.com.weathertaskapp.data.local.WeatherDao;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.api.ApiService;
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

    public LiveData<List<AccuWeatherDb>> getWeather() {
        return local.getWeather();
    }

    public LiveData<List<LocationSearchModel>> getRemoteListCitiesWeather(String q) {
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

    public LiveData<AccuWeatherModel> getRemotegetAccuWeatherData(String cityKey) {
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

    public void insertWeatherCity(AccuWeatherDb weatherDb) {
        local.insertWeatherResponse(weatherDb);
    }

    public void clearAllData() {
        local.clearAllData();
    }

}
