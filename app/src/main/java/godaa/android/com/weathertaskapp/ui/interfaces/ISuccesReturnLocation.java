package godaa.android.com.weathertaskapp.ui.interfaces;

import android.location.Location;

public interface ISuccesReturnLocation {
    void successLocation(Location deviceLocation);

    void failedLocation();
}
