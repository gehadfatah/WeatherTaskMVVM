package godaa.android.com.weathertaskapp.ui.detailWeather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.utils.ItemOffsetDecoration;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.rv_weather_data)
    RecyclerView rvWeatherData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        ButterKnife.bind(this);
        setrecyleview();
        // AccuWeather5DayModel accuWeather5DayModel = getIntent().getExtras().getParcelable("weatherDetails");
        String jsonText = getIntent().getExtras().getString("weatherDetails");
        Gson gson = new Gson();
        if (jsonText == null || jsonText.equals("")) return;
        AccuWeather5DayModel accuWeather5DayModel = gson.fromJson(getIntent().getStringExtra("weatherDetails"), AccuWeather5DayModel.class);
        if (accuWeather5DayModel != null)
            rvWeatherData.setAdapter(new RecyclerAdapterAccuWeather(this, accuWeather5DayModel));
    }

    private void setrecyleview() {
        rvWeatherData.setLayoutManager(new LinearLayoutManager(this));
        rvWeatherData.setHasFixedSize(true);
        rvWeatherData.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_offset));
    }
}
