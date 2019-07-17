package godaa.android.com.weathertaskapp.ui.detailWeather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.utils.ItemOffsetDecoration;

public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    @BindView(R.id.rv_weather_data)
    RecyclerView rvWeatherData;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setrecyleview();
        // AccuWeather5DayModel accuWeather5DayModel = getIntent().getExtras().getParcelable("weatherDetails");
        String jsonText = null;
        if (getArguments() != null) {
            jsonText = getArguments().getString(context.getString(R.string.weatherDetails));
        }
        Gson gson = new Gson();
        if (jsonText == null || jsonText.equals("")) return;
        AccuWeather5DayModel accuWeather5DayModel = gson.fromJson(getArguments().getString(getActivity().getResources().getString(R.string.weatherDetails)), AccuWeather5DayModel.class);
        if (accuWeather5DayModel != null)
            rvWeatherData.setAdapter(new RecyclerAdapterAccuWeather(context, accuWeather5DayModel));

    }

    private void setrecyleview() {
        rvWeatherData.setLayoutManager(new LinearLayoutManager(context));
        rvWeatherData.setHasFixedSize(true);
        rvWeatherData.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.item_offset));
    }
}
