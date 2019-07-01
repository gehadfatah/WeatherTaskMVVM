package godaa.android.com.weathertaskapp.ui.interfaces;

import android.location.Location;

import java.util.List;

import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;

public interface ObserverCallback {
    List<LocationSearchModel> setObserver(CharSequence chObserver);

}
