package godaa.android.com.weathertaskapp.ui.interfaces;


import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.remote.model.LocationSearchModel;

public interface IAddCityResponse {
    void AddClick(AccuWeather5DayModel accuWeather5DayModel, AccuWeatherModel accuWeatherModel, LocationSearchModel locationSearchModel);

    void cancelClick();
}
