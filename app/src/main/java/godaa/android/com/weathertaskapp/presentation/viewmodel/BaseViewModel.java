package godaa.android.com.weathertaskapp.presentation.viewmodel;

import android.annotation.SuppressLint;

import androidx.core.util.Preconditions;
import androidx.lifecycle.ViewModel;

import org.reactivestreams.Subscription;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BaseViewModel extends ViewModel {

    private final Scheduler subscribeOn;
    private final Scheduler observeOn;
    private final CompositeDisposable disposables;

    public BaseViewModel(Scheduler subscribeOn, Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
        this.disposables = new CompositeDisposable();
    }

    @SuppressLint("RestrictedApi")
    protected <T> void executeFlowable(Consumer<Subscription> loadingConsumer, Consumer<T> successConsumer, Consumer<Throwable> throwableConsumer, Flowable<T> useCase) {
        Preconditions.checkNotNull(successConsumer);
        Preconditions.checkNotNull(throwableConsumer);
        final Flowable<T> observable = useCase
                .doOnSubscribe(loadingConsumer)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
        addDisposable(observable.subscribe(successConsumer, throwableConsumer));
    }
    @SuppressLint("RestrictedApi")
    protected <T> void executeSingle(Consumer<Disposable> loadingConsumer, Consumer<T> successConsumer, Consumer<Throwable> throwableConsumer, Single<T> useCase) {
        Preconditions.checkNotNull(successConsumer);
        Preconditions.checkNotNull(throwableConsumer);
        final Single<T> observable = useCase
                .doOnSubscribe(loadingConsumer)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
        addDisposable(observable.subscribe(successConsumer, throwableConsumer));
    }
    @SuppressLint("RestrictedApi")
    protected <T> void executeObservable(Consumer<Disposable> loadingConsumer, Consumer<T> successConsumer, Consumer<Throwable> throwableConsumer, Observable<T> useCase) {
        Preconditions.checkNotNull(successConsumer);
        Preconditions.checkNotNull(throwableConsumer);
        final Observable<T> observable = useCase
                .doOnSubscribe(loadingConsumer)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
        addDisposable(observable.subscribe(successConsumer, throwableConsumer));
    }
    @SuppressLint("RestrictedApi")
    protected <T> void executeCompletable(Consumer<Disposable> loadingConsumer, Action successConsumer, Consumer<Throwable> throwableConsumer, Completable useCase) {
        Preconditions.checkNotNull(successConsumer);
        Preconditions.checkNotNull(throwableConsumer);
        final Completable completable = useCase
                .doOnSubscribe(loadingConsumer)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
        addDisposable(completable.subscribe( successConsumer, throwableConsumer));
    }
    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    @SuppressLint("RestrictedApi")
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dispose();
    }
}
