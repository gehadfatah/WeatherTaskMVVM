package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.WeatherApplication;
import godaa.android.com.weathertaskapp.data.model.Accu5DayWeatherModelViewState;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModelViewState;
import godaa.android.com.weathertaskapp.data.model.WeatherDbModelsViewState;
import godaa.android.com.weathertaskapp.ui.MainActivity;
import godaa.android.com.weathertaskapp.ui.detailWeather.DetailFragment;
import godaa.android.com.weathertaskapp.ui.viewmodel.WeatherViewModel;
import godaa.android.com.weathertaskapp.data.local.WeatherDatabase;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.api.APIClient;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.ui.base.BaseFragmentList;
import godaa.android.com.weathertaskapp.ui.addCityDialog.AddCityDialog;
import godaa.android.com.weathertaskapp.ui.interfaces.IAddCityResponse;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesFirstWeather;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesReturnLocation;
import godaa.android.com.weathertaskapp.ui.interfaces.NavigateTo;
import godaa.android.com.weathertaskapp.ui.interfaces.ObserverCallback;
import godaa.android.com.weathertaskapp.utils.ActivityUtils;
import godaa.android.com.weathertaskapp.utils.ItemOffsetDecoration;
import godaa.android.com.weathertaskapp.utils.NetworkUtils;
import godaa.android.com.weathertaskapp.utils.Utilities;
import godaa.android.com.weathertaskapp.utils.ViewModelFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherCitiesFragment extends BaseFragmentList implements ISuccesReturnLocation, /*ObserverCallback, */IAddCityResponse, ISuccesFirstWeather, NavigateTo, SwipeRefreshLayout.OnRefreshListener {

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
    AccuWeatherModel maccuWeatherModel = null;
    AccuWeather5DayModel maccuWeather5DayModel = null;
    private String[] PermissionLocation = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean firstAddedLocationOrDefaultLondon = true;
    private AutoCompleteAdapter2 autoAdapterCities;
    boolean getSecond = false;

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);


        weatherRepository = new WeatherRepository(WeatherDatabase.getInstance().weatherDao(), APIClient.getWeatherAPI());
        ViewModelFactory viewModelFactory = new ViewModelFactory(Schedulers.io(), AndroidSchedulers.mainThread(), weatherRepository);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        //mAdapter = new RecyclerAdapterCitesAccuWeather(this);
        setRvWeatherData();
        setSearchAutoComplete();
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);


    }

    @Override
    public void setUpObservers() {
        mViewModel.getAccuWeatherDbLiveData().observe(this, weatherDbModelsViewState -> {
            if (weatherDbModelsViewState != null)
                setweatherDbModelsViewState(weatherDbModelsViewState);

        });

    }

    private void setweatherDbModelsViewState(WeatherDbModelsViewState weatherDbModelsViewState) {

        switch (weatherDbModelsViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                setLoading();
                break;
            case SUCCESS:
                setSuccess();

                if (getActivity() != null && !Utilities.isOnline(getActivity())) {
                    cities.clear();
                    accuWeatherModelcities.clear();
                    AccuWeather5DayModelcities.clear();
                    for (AccuWeatherDb accuWeatherDb : weatherDbModelsViewState.getAccuWeatherModels()
                    ) {

                        LocationSearchModel locationSearchModel = new LocationSearchModel();
                        LocationSearchModel.Country country = new LocationSearchModel.Country();
                        country.setID(accuWeatherDb.getCountry());
                        country.setLocalizedName(accuWeatherDb.getCity());
                        locationSearchModel.setCountry(country);
                        locationSearchModel.setLocalizedName(accuWeatherDb.getCity());
                        locationSearchModel.setKey(accuWeatherDb.getKeyLocation());
                        cities.add(locationSearchModel);

                        AccuWeatherModel accuWeatherModel = new AccuWeatherModel();
                        AccuWeatherModel.Temperature temperature = new AccuWeatherModel.Temperature();
                        AccuWeatherModel.Metric metric = new AccuWeatherModel.Metric();
                        metric.setValue(accuWeatherDb.getTemperature());
                        temperature.setMetric(metric);
                        accuWeatherModel.setWeatherText(accuWeatherDb.getWeatherText());
                        accuWeatherModel.setTemperature(temperature);
                        accuWeatherModel.setWeatherIcon(accuWeatherDb.getWeatherIcon());
                        accuWeatherModelcities.add(accuWeatherModel);

                        AccuWeather5DayModel accuWeather5DayModel = new AccuWeather5DayModel();
                        accuWeather5DayModel.setDailyForecasts(accuWeatherDb.getDailyForecasts());
                        AccuWeather5DayModelcities.add(accuWeather5DayModel);

                    }
                    adapterCitesAccuWeather.notifyDataSetChanged();

                }
                break;
            case FAILED:
                setFailed(weatherDbModelsViewState);
                Toast.makeText(getActivity(),
                        weatherDbModelsViewState.getNetworkState().getMessage(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setSearchAutoComplete() {
        etCityName.setThreshold(2);
        autoAdapterCities = new AutoCompleteAdapter2(getActivity(), this, mViewModel);
        etCityName.setAdapter(autoAdapterCities);
        etCityName.setOnItemClickListener((adapterView, view, i, l) -> {
            mLocationSearchModel = (LocationSearchModel) adapterView.getAdapter().getItem(i);
            etCityName.setText(mLocationSearchModel.getLocalizedName());
            mViewModel.getRemotegetAccuWeatherData(mLocationSearchModel.getKey()).observe(this, accuWeatherModelViewState -> {
                if (accuWeatherModelViewState != null)
                    setaccuWeatherModelViewState(accuWeatherModelViewState);
            });

            mViewModel.getRemotegetAccu5DayWeatherData(mLocationSearchModel.getKey()).observe(this, accu5DayWeatherModelViewState -> {
                if (accu5DayWeatherModelViewState != null)
                    setaccu5DayWeatherModelViewState(accu5DayWeatherModelViewState);
            });
        });
    }

    private void setaccu5DayWeatherModelViewState(Accu5DayWeatherModelViewState accu5DayWeatherModelViewState) {
        switch (accu5DayWeatherModelViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                setLoading();
                break;
            case SUCCESS:
                setSuccess();
                maccuWeather5DayModel = accu5DayWeatherModelViewState.getAccuWeather5DayModel();
                if (maccuWeatherModel == null) getSecond = false;
                else getSecond = true;
                if (getSecond) {
                    addCityDailog(maccuWeatherModel, maccuWeather5DayModel, mLocationSearchModel);
                    maccuWeather5DayModel = null;
                    maccuWeatherModel = null;


                }
                break;
            case FAILED:
                setFailed(accu5DayWeatherModelViewState);
                break;
        }
    }

    private <T> void setFailed(T viewState) {
        progress_bar.setVisibility(View.GONE);
       /* if (viewState instanceof AccuWeatherModelViewState)
            Toast.makeText(getActivity(),
                    ((AccuWeatherModelViewState)viewState).getNetworkState().getMessage(),
                    Toast.LENGTH_SHORT).show();*/
    }

    private void setSuccess() {
        ActivityUtils.setVisibility(View.GONE, noConnectionLayout, noDataLayout, progress_bar);

    }

    private void setLoading() {
        progress_bar.setVisibility(View.VISIBLE);
        ActivityUtils.setVisibility(View.GONE, noConnectionLayout, noDataLayout);
    }

    private void setaccuWeatherModelViewState(AccuWeatherModelViewState accuWeatherModelViewState) {
        switch (accuWeatherModelViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                setLoading();
                break;
            case SUCCESS:
                setSuccess();
                maccuWeatherModel = accuWeatherModelViewState.getAccuWeatherModels().get(0);
                if (maccuWeather5DayModel == null) getSecond = false;
                else getSecond = true;
                if (getSecond) {

                    addCityDailog(maccuWeatherModel, maccuWeather5DayModel, mLocationSearchModel);
                    maccuWeather5DayModel = null;
                    maccuWeatherModel = null;
                }
                break;
            case FAILED:
                setFailed(accuWeatherModelViewState);
                break;
        }
    }

    private void addCityDailog(AccuWeatherModel accuWeatherModel, AccuWeather5DayModel accuWeather5DayModel, LocationSearchModel locationSearchModel) {
        if (firstAddedLocationOrDefaultLondon) {
            AddClick(accuWeather5DayModel, accuWeatherModel, locationSearchModel);
            firstAddedLocationOrDefaultLondon = false;
        } else {
            AddCityDialog addCityDialog = new AddCityDialog(getActivity(), this, accuWeatherModel, accuWeather5DayModel, locationSearchModel);
            addCityDialog.show();
        }

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
        insertNewWeatherCityToLocal(accuWeather5DayModel, accuWeatherModel, locationSearchModel);
    }

    private void insertNewWeatherCityToLocal(AccuWeather5DayModel accuWeather5DayModel, AccuWeatherModel accuWeatherModel, LocationSearchModel locationSearchModel) {
        AccuWeatherDb accuWeatherDb = new AccuWeatherDb(accuWeatherModel.getWeatherText(), locationSearchModel.getLocalizedName(), locationSearchModel.getCountry().getID(), locationSearchModel.getKey(), accuWeatherModel.getWeatherIcon(), accuWeather5DayModel.getDailyForecasts(), accuWeatherModel.getTemperature().getMetric().getValue());
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
      /*  if (AccuWeather5DayModelcities.size() > 0 && AccuWeather5DayModelcities.get(position) != null)
            startActivity(new Intent(getActivity(), aClass).putExtra("weatherDetails", new Gson().toJson(AccuWeather5DayModelcities.get(position))));
*/
        if (AccuWeather5DayModelcities.size() > 0 && AccuWeather5DayModelcities.get(position) != null) {
            Bundle bundle = new Bundle();
            bundle.putString(getResources().getString(R.string.weatherDetails), new Gson().toJson(AccuWeather5DayModelcities.get(position)));
            ((MainActivity) getActivity()).replaceFragment(DetailFragment.class, DetailFragment.TAG, bundle);
        }
    }


    @Override
    public void successLocation(Location deviceLocation) {

    }

    @Override
    public void failedLocation() {

    }

 /*   @Override
    public List<LocationSearchModel> setObserver(CharSequence charSequence) {
        final List<LocationSearchModel>[] locationSearchModels = new List[]{new ArrayList<>()};
        mViewModel.getRemoteListCitiesWeather(charSequence.toString()).observe(this, weatherCitiesViewState -> {
            if (weatherCitiesViewState != null)
                if (weatherCitiesViewState.getNetworkState().getViewStatus() == SUCCESS)
                    locationSearchModels[0] = weatherCitiesViewState.getLocationSearchModels();


        });
        return locationSearchModels[0];

    }*/


}
