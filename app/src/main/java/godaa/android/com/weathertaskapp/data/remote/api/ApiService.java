package godaa.android.com.weathertaskapp.data.remote.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



// https://api.apixu.com/v1/forecast.json?key=ENTER_KEY&q=Los%20Angeles&days=1
public interface ApiService {


    @GET("currentconditions/v1/{key}")
    Call<List<AccuWeatherModel>> getAccuWeatherData(@Path("key") String cityKey, @Query("apikey") String appId);
    @GET("locations/v1/cities/autocomplete")
    Call<List<LocationSearchModel>> getAccuWeatherCities(@Query("apikey") String appId, @Query("q") String query);

}
