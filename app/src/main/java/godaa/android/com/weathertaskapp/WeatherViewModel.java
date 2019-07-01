package godaa.android.com.weathertaskapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    WeatherRepository weatherRepository;
    private LiveData<List<AccuWeatherDb>> accuWeatherDbLiveData;
    private MutableLiveData<List<LocationSearchModel>> locationSearchModelsLiveData;
    private MutableLiveData<AccuWeatherModel> accuWeatherModelMutableLiveData;

    public WeatherViewModel(WeatherRepository weatherRepository) {

        this.weatherRepository = weatherRepository;

    }

    public LiveData<List<AccuWeatherDb>> getAccuWeatherDbLiveData() {
        return weatherRepository.getWeather();
    }

    public LiveData<List<LocationSearchModel>> getRemoteListCitiesWeather(String q) {
        return weatherRepository.getRemoteListCitiesWeather(q);
    }

    public LiveData<AccuWeatherModel> getRemotegetAccuWeatherData(String cityKey) {
        return weatherRepository.getRemotegetAccuWeatherData(cityKey);
    }
    public LiveData<AccuWeather5DayModel> getRemotegetAccu5DayWeatherData(String cityKey) {
        return weatherRepository.getAccuWeatherData5days(cityKey);
    }

    public void insertWeatherCity(AccuWeatherDb weatherDb) {
        weatherRepository.insertWeatherCity(weatherDb);
    }
}
