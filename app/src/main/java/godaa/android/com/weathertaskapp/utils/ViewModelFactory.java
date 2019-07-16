package godaa.android.com.weathertaskapp.utils;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import godaa.android.com.weathertaskapp.ui.WeatherViewModel;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;
import io.reactivex.Scheduler;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WeatherRepository weatherRepository;
    private Scheduler subscribeOn;
    private Scheduler observeOn;
    public ViewModelFactory(Scheduler subscribeOn, Scheduler observeOn, WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            //noinspection unchecked
            return (T) new WeatherViewModel(subscribeOn, observeOn,weatherRepository);
        } /*else if (modelClass.isAssignableFrom(ArticlesViewModel.class)) {
            //noinspection unchecked
            return (T) new ArticlesViewModel(mArticleRepository);
        } */
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}