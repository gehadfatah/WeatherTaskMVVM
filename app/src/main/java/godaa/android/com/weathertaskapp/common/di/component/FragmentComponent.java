package godaa.android.com.weathertaskapp.common.di.component;

import dagger.Component;
import godaa.android.com.weathertaskapp.common.di.scope.FragmentScope;
import godaa.android.com.weathertaskapp.ui.detailWeather.DetailFragment;
import godaa.android.com.weathertaskapp.ui.weatherCities.WeatherCitiesFragment;


@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(WeatherCitiesFragment fragment);

    void inject(DetailFragment fragment);

}
