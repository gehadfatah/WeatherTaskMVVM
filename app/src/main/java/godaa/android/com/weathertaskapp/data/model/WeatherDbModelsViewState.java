package godaa.android.com.weathertaskapp.data.model;


import java.util.List;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;

public class WeatherDbModelsViewState {

    private NetworkState networkState;
    private List<AccuWeatherDb> accuWeatherModels;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public List<AccuWeatherDb> getAccuWeatherModels() {
        return accuWeatherModels;
    }

    public void setAccuWeatherModels(List<AccuWeatherDb> accuWeatherModels) {
        this.accuWeatherModels = accuWeatherModels;
    }
}
