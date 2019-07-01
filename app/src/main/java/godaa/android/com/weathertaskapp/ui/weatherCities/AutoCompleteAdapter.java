package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import godaa.android.com.weathertaskapp.MainActivity;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.WeatherViewModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.remote.api.ApiService;
import godaa.android.com.weathertaskapp.ui.interfaces.IWeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    private List<LocationSearchModel> mResultList = new ArrayList<>();
    private String BASE_URL_ACCU_WEATHER = "http://api.accuweather.com/";
    private Context mContext;
    WeatherViewModel mViewModel;
    Activity activity;

    public AutoCompleteAdapter(Context mContext, WeatherViewModel mViewModel) {
        this.mContext = mContext;
        activity = (Activity) mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public Object getItem(int i) {
        return mResultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(R.layout.row_suggestions, viewGroup, false);
            }
        }

        if (view != null) {
            // ((TextView) view.findViewById(R.id.tv_city_suggestion)).setText(mResultList.get(i).getLocalizedName());
            ((TextView) view.findViewById(R.id.tv_city_suggestion)).setText(String.format("%s , %s", mResultList.get(i).getLocalizedName(), mResultList.get(i).getCountry().getLocalizedName()));

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    mViewModel.getRemoteListCitiesWeather(charSequence.toString()).observe(((MainActivity)activity), locationSearchModels -> {
                        if (locationSearchModels != null) {
                            if (!locationSearchModels.isEmpty()) {
                                mResultList.clear();
                                mResultList.addAll(locationSearchModels);
                                filterResults.values = mResultList;
                                filterResults.count = mResultList.size();
                                publishResults(charSequence, filterResults);
                            }

                        } else {
                            mResultList.clear();
                            publishResults(charSequence, filterResults);
                        }
                    });

                }
                filterResults.values = mResultList;
                filterResults.count = mResultList.size();
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                } else
                    notifyDataSetInvalidated();
            }
        };
        return filter;
    }
}