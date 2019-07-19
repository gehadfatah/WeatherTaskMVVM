package godaa.android.com.weathertaskapp.data.provider.location;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesReturnLocation;
import timber.log.Timber;

public class LocationProviderImpl implements LocationProvider, OnSuccessListener<Location>, OnFailureListener {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location deviceLocation;
    ISuccesReturnLocation iSuccesReturnLocation;

    public LocationProviderImpl(ISuccesReturnLocation iSuccesReturnLocation, FusedLocationProviderClient client) {
        mFusedLocationProviderClient = client;
        this.iSuccesReturnLocation = iSuccesReturnLocation;
    }

    @Override
    public String getPreferredLocationString() {
        //if (isUsingDeviceLocation()) {

        if (getLastDeviceLocation() == null) {
            //Utilities.showToast(mContext, mContext.getString(R.string.location_not_available), Toast.LENGTH_LONG);
            //return getCustomLocationName();
            return "";
        } else {
            String latitude = String.valueOf(getLastDeviceLocation().getLatitude());
            String longitude = String.valueOf(getLastDeviceLocation().getLongitude());
            Timber.d("Coordinates %s,%s", latitude, longitude);
            return (latitude + "," + longitude);
        }
        //  }
    }


    private Location getLastDeviceLocation() {

        startLocationUpdates();
        return deviceLocation;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
        mFusedLocationProviderClient.getLastLocation().addOnFailureListener(this);

    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            double comparisonThreshold = 0.03;

            if (deviceLocation != null) {
                if (Math.abs(deviceLocation.getLatitude() - location.getLatitude()) > comparisonThreshold
                        && Math.abs(deviceLocation.getLongitude() - location.getLongitude()) > comparisonThreshold)
                    iSuccesReturnLocation.successLocation(location);
            } else
                iSuccesReturnLocation.successLocation(location);


            deviceLocation = location;

        } else {
            Timber.d("Device Location not yet available. Please try again");
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        iSuccesReturnLocation.failedLocation();

    }
}
