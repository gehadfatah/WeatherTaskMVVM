package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.app.WeatherApplication;
import godaa.android.com.weathertaskapp.common.di.component.DaggerFragmentComponent;
import godaa.android.com.weathertaskapp.common.di.component.FragmentComponent;
import godaa.android.com.weathertaskapp.data.local.prefs.WeatherSharedPreference;
import godaa.android.com.weathertaskapp.presentation.model.viewState.Accu5DayWeatherModelViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.AccuDbInsertViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.AccuWeatherModelViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.WeatherDbModelsViewState;
import godaa.android.com.weathertaskapp.common.provider.location.LocationProviderImpl;
import godaa.android.com.weathertaskapp.presentation.viewmodel.WeatherViewModel;
import godaa.android.com.weathertaskapp.data.local.WeatherDatabase;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.remote.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.client.APIClient;
import godaa.android.com.weathertaskapp.data.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.presentation.viewmodel.factory.ViewModelFactory;
import godaa.android.com.weathertaskapp.ui.base.BaseFragmentList;
import godaa.android.com.weathertaskapp.ui.addCityDialog.AddCityDialog;
import godaa.android.com.weathertaskapp.ui.interfaces.DeleteFromDatabase;
import godaa.android.com.weathertaskapp.ui.interfaces.IAddCityResponse;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesFirstWeather;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesReturnLocation;
import godaa.android.com.weathertaskapp.ui.interfaces.NavigateTo;
import godaa.android.com.weathertaskapp.common.utils.ActivityUtils;
import godaa.android.com.weathertaskapp.common.utils.ItemOffsetDecoration;
import godaa.android.com.weathertaskapp.common.utils.KeyboardUtils;
import godaa.android.com.weathertaskapp.common.utils.Utilities;
import godaa.android.com.weathertaskapp.common.utils.WeatherConstants;
import godaa.android.com.weathertaskapp.common.widget.WeatherWidgetProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherCitiesFragment extends BaseFragmentList implements DeleteFromDatabase, ISuccesReturnLocation, SwipeRefreshLayout.OnRefreshListener, IAddCityResponse, ISuccesFirstWeather, NavigateTo {
    public static final String TAG = WeatherCitiesFragment.class.getSimpleName();
    private static Fragment fragment;
    private WeatherViewModel mViewModel;
    public WeatherRepository weatherRepository;
    @BindView(R.id.et_city_name)
    AutoCompleteTextView etCityName;
    @BindView(R.id.dummy_id)
    LinearLayout dummy;
    String keyLondon = "328328";
    ArrayList<LocationSearchModel> cities = new ArrayList<>();
    ArrayList<AccuWeather5DayModel> AccuWeather5DayModelcities = new ArrayList<>();
    ArrayList<AccuWeatherModel> accuWeatherModelcities = new ArrayList<>();
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
    private AutoCompleteAdapter autoAdapterCities;
    AddCityDialog addCityDialog;
    private boolean selectedLondonFromSearch = false;
    private boolean fromLocation = false;
    @Inject
    ViewModelFactory mModelFactory;
    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initDagger();

        fragment = this;
       // weatherRepository = new WeatherRepository(WeatherDatabase.getInstance().weatherDao(), APIClient.getWeatherAPI());
//        ViewModelFactory viewModelFactory = new ViewModelFactory(Schedulers.io(), AndroidSchedulers.mainThread(), weatherRepository);
        mViewModel = ViewModelProviders.of(this, mModelFactory).get(WeatherViewModel.class);
        checkLocationPermission();
        setRvWeatherData();
        setSearchAutoComplete();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        dummy.requestFocus();

    }

    public static <T extends WeakReference<Fragment>> WeakReference<Fragment> getFragmnt() {
        return (new WeakReference<Fragment>(fragment));
    }

    private void initDagger() {
        FragmentComponent component = DaggerFragmentComponent.builder()
                .appComponent((WeatherApplication.getInstance()).getAppComponent())
                .build();
        component.inject(this);
    }
    @Override
    public void setUpObservers() {
        mViewModel.getAccuWeatherDbLiveData().observe(this, weatherDbModelsViewState -> {
            //use returned data from DB just if no internet
            //if (getActivity() != null && !Utilities.isOnline(getActivity())) {
            mSwipeRefreshLayout.setRefreshing(false);

            if (weatherDbModelsViewState != null)
                setweatherDbModelsViewState(weatherDbModelsViewState);
            // }

        });

    }

    private void setweatherDbModelsViewState(WeatherDbModelsViewState weatherDbModelsViewState) {

        switch (weatherDbModelsViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                setLoading();
                break;
            case SUCCESS:
                setSuccess();
                cities.clear();
                accuWeatherModelcities.clear();
                AccuWeather5DayModelcities.clear();
                for (AccuWeatherDb accuWeatherDb : weatherDbModelsViewState.getAccuWeatherModels()) {
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
                break;
            case FAILED:
                setFailed(weatherDbModelsViewState.getNetworkState().getMessage());

                break;
        }
    }

    private void setSearchAutoComplete() {
        etCityName.setThreshold(2);
        if (getActivity() == null) return;
        autoAdapterCities = new AutoCompleteAdapter(getActivity(), this, mViewModel);
        etCityName.setAdapter(autoAdapterCities);
        etCityName.setOnItemClickListener((adapterView, view, i, l) -> {
            hideKeyboard();
            mLocationSearchModel = (LocationSearchModel) adapterView.getAdapter().getItem(i);
            if (mLocationSearchModel.getKey().equals("328328"))
                new WeatherSharedPreference(getActivity()).saveBooleanToSharedPreference(WeatherConstants.SelectedLondon, true);
            else
                new WeatherSharedPreference(getActivity()).saveBooleanToSharedPreference(WeatherConstants.SelectedLondon, false);

            etCityName.setText(mLocationSearchModel.getLocalizedName());
            setWeatherModelObserver(mLocationSearchModel.getKey());
            setWeather5DayModelObserver(mLocationSearchModel.getKey());

        });

        etCityName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etCityName.getRight() - etCityName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        // etCityName.setCompoundDrawables(getActivity().getResources().getDrawable(R.drawable.ic_close_blue),getActivity().getResources().getDrawable(R.drawable.ic_close_blue),getActivity().getResources().getDrawable(R.drawable.ic_close_blue),null);
                        etCityName.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setWeatherModelObserver(String key) {
        mViewModel.getRemotegetAccuWeatherData(key).observe(this, accuWeatherModelViewState -> {
            if (accuWeatherModelViewState != null)
                setaccuWeatherModelViewState(accuWeatherModelViewState);
        });

    }

    private void setWeather5DayModelObserver(String key) {
        mViewModel.getRemotegetAccu5DayWeatherData(key).observe(this, accu5DayWeatherModelViewState -> {
            if (accu5DayWeatherModelViewState != null)
                setaccu5DayWeatherModelViewState(accu5DayWeatherModelViewState);
        });

    }

    private void setaccuWeatherModelViewState(AccuWeatherModelViewState accuWeatherModelViewState) {
        switch (accuWeatherModelViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                // setLoading();
                break;
            case SUCCESS:
                setSuccess();
                maccuWeatherModel = accuWeatherModelViewState.getAccuWeatherModels().get(0);
                /*getSecond = maccuWeather5DayModel != null;
                if (getSecond) {

                    addCityDailog(maccuWeatherModel, maccuWeather5DayModel, mLocationSearchModel);
                    maccuWeather5DayModel = null;
                    maccuWeatherModel = null;
                }*/
                break;
            case FAILED:
                setFailed(accuWeatherModelViewState.getNetworkState().getMessage());
                break;
        }
    }

    private void setaccu5DayWeatherModelViewState(Accu5DayWeatherModelViewState accu5DayWeatherModelViewState) {
        switch (accu5DayWeatherModelViewState.getNetworkState().getViewStatus()) {
            case RUNNING:
                setLoading();
                break;
            case SUCCESS:
                setSuccess();
                maccuWeather5DayModel = accu5DayWeatherModelViewState.getAccuWeather5DayModel();
                //getSecond = maccuWeatherModel != null;
                //if (getSecond) {
                if (maccuWeatherModel != null && mLocationSearchModel != null) {
                    CallAdd();

                } else {
                    try {
                        Thread.sleep(1550);
                        CallAdd();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //    }
                break;
            case FAILED:
                setFailed(accu5DayWeatherModelViewState.getNetworkState().getMessage());
                break;
        }
    }

    private void CallAdd() {
        hideKeyboard();

        if (maccuWeatherModel != null && mLocationSearchModel != null) {
            //if (firstAddedLocationOrDefaultLondon) {
            if (cities.size() > 4) {
                showSnack(rootView, "Limit to 5 cities you can add more by delete one");

            } else {
                AddClick(maccuWeather5DayModel, maccuWeatherModel, mLocationSearchModel);
                maccuWeather5DayModel = null;
                maccuWeatherModel = null;
                //mLocationSearchModel = null;
            }
     /*   } else {
            addCityDialog = new AddCityDialog(getActivity(), this, accuWeatherModel, accuWeather5DayModel, locationSearchModel);
            if (!addCityDialog.isShowing()) {
                addCityDialog.show();
            }
        }*/

        }
    }

    private void setFailed(String message) {
        progress_bar.setVisibility(View.GONE);
        if (getActivity() == null) return;
        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void setSuccess() {
        ActivityUtils.setVisibility(View.GONE, noConnectionLayout, noDataLayout, progress_bar);

    }

    private void setLoading() {
        progress_bar.setVisibility(View.VISIBLE);
        ActivityUtils.setVisibility(View.GONE, noConnectionLayout, noDataLayout);
    }


    private void addLondonCity() {
        LocationSearchModel.Country country = new LocationSearchModel.Country();
        country.setID("GB");
        country.setLocalizedName("United Kingdom");
        LocationSearchModel locationSearchModel = new LocationSearchModel();
        locationSearchModel.setCountry(country);
        locationSearchModel.setKey(keyLondon);
        locationSearchModel.setLocalizedName("London");
        locationSearchModel.setType("City");
        locationSearchModel.setRank(10);
        locationSearchModel.setVersion(1);
        mLocationSearchModel = locationSearchModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cities_list;
    }

    public void setRvWeatherData() {
        if (getActivity() == null) return;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        adapterCitesAccuWeather = new RecyclerAdapterCitesAccuWeather(getActivity(), this, this, this, cities, accuWeatherModelcities);
        recyclerView.setAdapter(adapterCitesAccuWeather);
    }

    private void startLocationUpdates() {
        if (getActivity() == null) return;
        new LocationProviderImpl(this, LocationServices.getFusedLocationProviderClient(getActivity())).getPreferredLocationString();

    }

    @Override
    public void AddClick(AccuWeather5DayModel accuWeather5DayModel, AccuWeatherModel accuWeatherModel, LocationSearchModel locationSearchModel) {
        //check if location is london to not add it to database
        if (!seeIfAddedBefore(locationSearchModel) && !locationSearchModel.getKey().equals(keyLondon)
                || new WeatherSharedPreference(getActivity()).retrieveBooleanFromSharedPreference(WeatherConstants.locationLondon)
                && (locationSearchModel.getKey().equals(keyLondon) && fromLocation)
                || (new WeatherSharedPreference(getActivity()).retrieveBooleanFromSharedPreference(WeatherConstants.SelectedLondon)
                && !seeIfAddedBefore(locationSearchModel)) /*|| (selectedLondonFromSearch&&!seeIfAddedBefore(locationSearchModel))*/) {
            // cities.add(locationSearchModel);
            //  accuWeatherModelcities.add(accuWeatherModel);
            // AccuWeather5DayModelcities.add(accuWeather5DayModel);
            //adapterCitesAccuWeather.notifyDataSetChanged();
            insertNewWeatherCityToLocal(accuWeather5DayModel, accuWeatherModel, locationSearchModel);
        } else if (locationSearchModel.getKey().equals(keyLondon) &&
                !new WeatherSharedPreference(getActivity()).retrieveBooleanFromSharedPreference(WeatherConstants.SelectedLondon)) {
            cities.add(locationSearchModel);
            accuWeatherModelcities.add(accuWeatherModel);
            AccuWeather5DayModelcities.add(accuWeather5DayModel);
            adapterCitesAccuWeather.notifyDataSetChanged();
        }

    }

    private boolean seeIfAddedBefore(LocationSearchModel mlocationSearchModel) {
        for (LocationSearchModel locationSearchModel : cities) {
            if (locationSearchModel.getKey().equals(mlocationSearchModel.getKey())) {
                return true;
            }

        }
        return false;
    }

    private void insertNewWeatherCityToLocal(AccuWeather5DayModel accuWeather5DayModel, AccuWeatherModel accuWeatherModel, LocationSearchModel locationSearchModel) {
        AccuWeatherDb accuWeatherDb = new AccuWeatherDb(accuWeatherModel.getWeatherText(), locationSearchModel.getLocalizedName(), locationSearchModel.getCountry().getID(), locationSearchModel.getKey(), accuWeatherModel.getWeatherIcon(), accuWeather5DayModel.getDailyForecasts(), accuWeatherModel.getTemperature().getMetric().getValue());
        mViewModel.insertWeatherCityComplete(accuWeatherDb).observe(this, accuDbInsertViewState -> {
            if (accuDbInsertViewState != null)
                setaccuDbsViewState(accuDbInsertViewState);
        });
    }

    private void setaccuDbsViewState(AccuDbInsertViewState accuDbInsertViewState) {
        switch (accuDbInsertViewState.getNetworkState().getViewStatus()) {
            case FAILED:
                if (getActivity() == null) return;
                Toast.makeText(getActivity(), "Error while insert weather in database " + accuDbInsertViewState.getNetworkState().getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void cancelClick() {

    }


    @Override
    public void navigate(View v,  int position, String localizedName) {
      /*  if (AccuWeather5DayModelcities.size() > 0 && AccuWeather5DayModelcities.get(position) != null)
            startActivity(new Intent(getActivity(), aClass).putExtra("weatherDetails", new Gson().toJson(AccuWeather5DayModelcities.get(position))));
*/
        if (AccuWeather5DayModelcities.size() > 0 && AccuWeather5DayModelcities.get(position) != null) {
            Bundle bundle = new Bundle();
            bundle.putString(getResources().getString(R.string.weatherDetails), new Gson().toJson(AccuWeather5DayModelcities.get(position)));
            bundle.putString(getResources().getString(R.string.weatherCity), localizedName);
            // ((MainActivity) getActivity()).replaceFragment(DetailFragment.class, DetailFragment.TAG, bundle);
            Navigation.findNavController(v).navigate(R.id.action_weatherCitiesFragment_to_detailFragment, bundle);

        }
    }


    @Override
    public void successLocation(Location deviceLocation) {
        if (deviceLocation == null) return;
        String latitude = String.valueOf(deviceLocation.getLatitude());
        String longitude = String.valueOf(deviceLocation.getLongitude());
        Timber.d("Coordinates %s,%s", latitude, longitude);
        mViewModel.getAccuWeatherBylocation(latitude + "," + longitude).observe(this, locationWeatherModelViewState -> {
            if (locationWeatherModelViewState != null) {
                LocationSearchModel locationSearchModel = locationWeatherModelViewState.getLocationSearchModel();
                mLocationSearchModel = locationSearchModel;
                if (locationSearchModel != null) {
                 /*   mViewModel.getRemotegetAccuWeatherData(locationSearchModel.getKey());
                    mViewModel.getRemotegetAccu5DayWeatherData(locationSearchModel.getKey());*/
                    fromLocation = true;
                    if (locationSearchModel.getKey().equals("328328"))
                        new WeatherSharedPreference(getActivity()).saveBooleanToSharedPreference(WeatherConstants.locationLondon, true);
                    else {
                        new WeatherSharedPreference(getActivity()).saveBooleanToSharedPreference(WeatherConstants.locationLondon, false);

                    }
                    setWeatherModelObserver(locationSearchModel.getKey());
                    setWeather5DayModelObserver(locationSearchModel.getKey());

                }
            }
        });

    }

    @Override
    public void failedLocation() {
        addLondonCity();
      /*  mViewModel.getRemotegetAccuWeatherData(keyLondon);
        mViewModel.getRemotegetAccu5DayWeatherData(keyLondon);*/
        setWeatherModelObserver(keyLondon);
        setWeather5DayModelObserver(keyLondon);
        fromLocation = false;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mViewModel.getAccuWeatherDbLiveData();

    }

    @Override
    public void delete(String key) {
        hideKeyboard();
        mViewModel.deleteWeather(key);
    }

    private void hideKeyboard() {
        if (getActivity() == null) return;
        KeyboardUtils.hideSoftInput(Objects.requireNonNull(getActivity()));

    }


    public void checkLocationPermission() {
        if (getActivity() == null) return;
        if (!isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                !isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Utilities.showDialog(getActivity(), getString(R.string.location_permission_dialog_title),
                        getString(R.string.location_permission_prompt),
                        (dialog, i) -> requestPermission(PermissionLocation),
                        (dialog, i) -> {

                            failedLocation();
                            dialog.cancel();
                        });
                Timber.d("checkLocationPermission: ");
            } else {
                requestPermission(PermissionLocation);
            }
        } else {
            Timber.d("Permission granted");
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] ==
                    PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
                Timber.d("permission granted");
            } else {
                failedLocation();
                Timber.d("permission not granted");

            }
        }
    }

    private void requestPermission(String[] permissions) {
        requestPermissions(permissions, PERMISSION_ACCESS_COARSE_LOCATION);
    }

    private boolean isPermissionGranted(String permission) {
        if (getActivity() == null) return false;

        return ActivityCompat.checkSelfPermission(getActivity(),
                permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void successWeather(AccuWeatherModel weatherModel) {
        updateWidgetData(weatherModel);

    }

    private void updateWidgetData(AccuWeatherModel weather) {
        saveToPreferences(weather);
        WeatherWidgetProvider.updateWidget(getActivity());
    }

    private void saveToPreferences(AccuWeatherModel weather) {

        new WeatherSharedPreference(getActivity()).saveStringToSharedPreference(WIDGET_TEXT, weather.getWeatherText());
        if (cities.size() > 0)
            new WeatherSharedPreference(getActivity()).saveStringToSharedPreference(WIDGET_LOCATION, cities.get(0).getLocalizedName());
        new WeatherSharedPreference(getActivity()).saveStringToSharedPreference(WIDGET_ICON, String.valueOf(weather.getWeatherIcon()));

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
