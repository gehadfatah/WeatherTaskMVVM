package godaa.android.com.weathertaskapp.presentation.model.viewState;


import java.util.List;

import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;

public class AccuWeatherModelViewState  {

    private NetworkState networkState;
    private List<AccuWeatherModel> accuWeatherModels;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public List<AccuWeatherModel> getAccuWeatherModels() {
        return accuWeatherModels;
    }

    public void setAccuWeatherModels(List<AccuWeatherModel> accuWeatherModels) {
        this.accuWeatherModels = accuWeatherModels;
    }
}
