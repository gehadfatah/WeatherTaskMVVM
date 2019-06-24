package godaa.android.com.weathertaskapp.data.local;

import android.location.LocationProvider;

import androidx.lifecycle.LiveData;

import org.threeten.bp.ZonedDateTime;

import java.util.List;

import javax.inject.Inject;

import godaa.android.com.weathertaskapp.data.WeatherDao;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;


public class LocalDataSourceImpl implements LocalDataSource<AccuWeatherDb, LiveData<List<AccuWeatherDb>>> {

    private final WeatherDao mWeatherDao;

    private LocationProvider mLocationProvider;

    @Inject
    LocalDataSourceImpl(LocationProvider locationProvider,
                        WeatherDao weatherDao) {
        mLocationProvider = locationProvider;
        mWeatherDao = weatherDao;
    }

    @Override
    public void save(AccuWeatherDb response) {
        mWeatherDao.insertWeatherResponse(response);
    }

 /*   @Override
    public boolean hasLocationChanged(AccuWeatherDb response) {
        return mLocationProvider.isLocationChanged(response.getLocation());
    }

    @Override
    public boolean shouldFetch(AccuWeatherDb response) {
        ZonedDateTime locationTime = response.getLocation().getZonedDateTime();
        ZonedDateTime timeElapsed = ZonedDateTime.now().minusMinutes(30);
        return locationTime.isBefore(timeElapsed);
    }
*/
    @Override
    public LiveData<List<AccuWeatherDb>> get() {
        return mWeatherDao.getWeather();
    }
}
