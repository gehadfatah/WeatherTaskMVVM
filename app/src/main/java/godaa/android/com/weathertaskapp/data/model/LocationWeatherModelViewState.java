package godaa.android.com.weathertaskapp.data.model;


public class LocationWeatherModelViewState {

    private NetworkState networkState;
    private LocationSearchModel locationSearchModel;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public LocationSearchModel getLocationSearchModel() {
        return locationSearchModel;
    }

    public void setLocationSearchModel(LocationSearchModel locationSearchModel) {
        this.locationSearchModel = locationSearchModel;
    }
}
