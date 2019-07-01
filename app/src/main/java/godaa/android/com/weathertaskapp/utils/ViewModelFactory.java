package godaa.android.com.weathertaskapp.utils;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import godaa.android.com.weathertaskapp.WeatherViewModel;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WeatherRepository weatherRepository;

    public ViewModelFactory(  WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            //noinspection unchecked
            return (T) new WeatherViewModel(weatherRepository);
        } /*else if (modelClass.isAssignableFrom(ArticlesViewModel.class)) {
            //noinspection unchecked
            return (T) new ArticlesViewModel(mArticleRepository);
        } */
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}