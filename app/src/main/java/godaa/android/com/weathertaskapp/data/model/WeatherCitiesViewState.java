package godaa.android.com.weathertaskapp.data.model;


import java.util.List;

public class WeatherCitiesViewState {

    private NetworkState networkState;
    private List<LocationSearchModel> locationSearchModels;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public List<LocationSearchModel> getLocationSearchModels() {
        return locationSearchModels;
    }

    public void setLocationSearchModels(List<LocationSearchModel> locationSearchModels) {
        this.locationSearchModels = locationSearchModels;
    }
}
