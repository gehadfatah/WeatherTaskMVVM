package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.MainActivity;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.WeatherViewModel;
import godaa.android.com.weathertaskapp.data.local.WeatherDatabase;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.api.APIClient;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.ui.base.BaseFragmentList;
import godaa.android.com.weathertaskapp.ui.dialog.AddCityDialog;
import godaa.android.com.weathertaskapp.ui.interfaces.IAddCityResponse;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesFirstWeather;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesReturnLocation;
import godaa.android.com.weathertaskapp.ui.interfaces.IWeatherCallbackListener;
import godaa.android.com.weathertaskapp.ui.interfaces.NavigateTo;
import godaa.android.com.weathertaskapp.utils.ItemOffsetDecoration;
import godaa.android.com.weathertaskapp.utils.ViewModelFactory;

public class WeatherCitiesFragment extends BaseFragmentList implements ISuccesReturnLocation, IAddCityResponse, ISuccesFirstWeather, NavigateTo, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = WeatherCitiesFragment.class.getSimpleName();
    private WeatherViewModel mViewModel;
    public WeatherRepository weatherRepository;

    @BindView(R.id.et_city_name)
    AutoCompleteTextView etCityName;
    String keyLondon = "328328";
    ArrayList<LocationSearchModel> cities = new ArrayList<>();
    ArrayList<AccuWeather5DayModel> AccuWeather5DayModelcities = new ArrayList<>();
    ArrayList<AccuWeatherModel> accuWeatherModelcities = new ArrayList<>();
    String ACCU_WEATHER_APP_ID = "87ad516d1d4842838fcfebe843d933b1";
    LocationSearchModel mLocationSearchModel;
    RecyclerAdapterCitesAccuWeather adapterCitesAccuWeather;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 98;
    public static final String WIDGET_PREF = "gehad.weather.pref";
    public static final String WIDGET_TEXT = "gehad.weather.widget.text";
    public static final String WIDGET_LOCATION = "gehad.weather.widget.location";
    public static final String WIDGET_ICON = "gehad.weather.widget.icon";
    AccuWeatherModel maccuWeatherModel = new AccuWeatherModel();
    AccuWeather5DayModel maccuWeather5DayModel;
    private String[] PermissionLocation = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean firstAddedLocationOrDefaultLondon = true;

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, getActivity());


        weatherRepository = new WeatherRepository(WeatherDatabase.getInstance().weatherDao(), APIClient.getWeatherAPI());
        ViewModelFactory viewModelFactory = new ViewModelFactory(weatherRepository);

        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(WeatherViewModel.class);
        //mAdapter = new RecyclerAdapterCitesAccuWeather(this);
        setRvWeatherData();
        setSearchAutoComplete();
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void setUpObservers() {

    }

    private void setSearchAutoComplete() {
        etCityName.setThreshold(2);
        etCityName.setAdapter(new AutoCompleteAdapter(getActivity(), mViewModel));

        etCityName.setOnItemClickListener((adapterView, view, i, l) -> {

            mLocationSearchModel = (LocationSearchModel) adapterView.getAdapter().getItem(i);
            // cities.add(mLocationSearchModel);
            etCityName.setText(mLocationSearchModel.getLocalizedName());
            //WeatherConditions.getAccuWeatherData(mLocationSearchModel.getKey(), ACCU_WEATHER_APP_ID, MainActivity.this, true);
            mViewModel.getRemotegetAccuWeatherData(mLocationSearchModel.getKey()).observe(this, accuWeatherModel -> {
                maccuWeatherModel = accuWeatherModel;

            });

            // WeatherConditions.getAccuWeatherData5Days(mLocationSearchModel.getKey(), ACCU_WEATHER_APP_ID, MainActivity.this, true);
            mViewModel.getRemotegetAccu5DayWeatherData(mLocationSearchModel.getKey()).observe(this, accuWeather5DayModel -> {
                maccuWeather5DayModel=accuWeather5DayModel;
                if (firstAddedLocationOrDefaultLondon) {
                    AddClick(accuWeather5DayModel, maccuWeatherModel, mLocationSearchModel);
                    firstAddedLocationOrDefaultLondon = false;
                } else {
                    AddCityDialog addCityDialog = new AddCityDialog(getActivity(), this, maccuWeatherModel, accuWeather5DayModel, mLocationSearchModel);
                    addCityDialog.show();
                }

            });
        });
    }

    private void addLondonCity() {
        LocationSearchModel.Country country = new LocationSearchModel.Country();
        country.setID("GB");
        country.setLocalizedName("United Kingdom");
        // LocationSearchModel locationSearchModel = new LocationSearchModel(1, keyLondon, country, "London", "City", 10);
        LocationSearchModel locationSearchModel = new LocationSearchModel();
        locationSearchModel.setCountry(country);
        locationSearchModel.setKey(keyLondon);
        locationSearchModel.setLocalizedName("London");
        locationSearchModel.setType("City");
        locationSearchModel.setRank(10);
        locationSearchModel.setVersion(1);
        //cities.add(locationSearchModel);
        mLocationSearchModel = locationSearchModel;

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cities_list;
    }

    public void setRvWeatherData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        adapterCitesAccuWeather = new RecyclerAdapterCitesAccuWeather(getActivity(), this, this, cities, accuWeatherModelcities);
        recyclerView.setAdapter(adapterCitesAccuWeather);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void AddClick(AccuWeather5DayModel accuWeather5DayModel, AccuWeatherModel accuWeatherModel, LocationSearchModel locationSearchModel) {
        cities.add(locationSearchModel);
        accuWeatherModelcities.add(accuWeatherModel);
        AccuWeather5DayModelcities.add(accuWeather5DayModel);
        adapterCitesAccuWeather.notifyDataSetChanged();
        insertNewWeatherCityToLocal();
    }

    private void insertNewWeatherCityToLocal() {
        AccuWeatherDb accuWeatherDb = new AccuWeatherDb(maccuWeatherModel.getWeatherText(), mLocationSearchModel.getLocalizedName(), maccuWeatherModel.getWeatherIcon(), maccuWeather5DayModel.getDailyForecasts(), maccuWeatherModel.getTemperature());
        mViewModel.insertWeatherCity(accuWeatherDb);

    }

    @Override
    public void cancelClick() {

    }

    @Override
    public void successWeather(AccuWeatherModel weatherModel) {

    }


    @Override
    public void navigate(Class aClass, int position) {

    }

    @Override
    public void successLocation(Location deviceLocation) {

    }

    @Override
    public void failedLocation() {

    }
}
