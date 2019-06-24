package godaa.android.com.weathertaskapp.data.local;


import godaa.android.com.weathertaskapp.data.source.BaseSource;

public interface LocalDataSource<K, V> extends BaseSource {

    void save(K k);

/*    boolean hasLocationChanged(K k);

    boolean shouldFetch(K k);*/

    @Override
    V get();
}
