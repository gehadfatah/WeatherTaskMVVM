package godaa.android.com.weathertaskapp.data.model;


import java.util.List;

public class Accu5DayWeatherModelViewState {

    private NetworkState networkState;
    private AccuWeather5DayModel accuWeather5DayModel;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public AccuWeather5DayModel getAccuWeather5DayModel() {
        return accuWeather5DayModel;
    }

    public void setAccuWeather5DayModel(AccuWeather5DayModel accuWeather5DayModel) {
        this.accuWeather5DayModel = accuWeather5DayModel;
    }
}
