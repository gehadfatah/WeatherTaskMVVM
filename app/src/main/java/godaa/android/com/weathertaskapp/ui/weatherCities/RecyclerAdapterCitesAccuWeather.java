package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesFirstWeather;
import godaa.android.com.weathertaskapp.ui.interfaces.NavigateTo;


public class RecyclerAdapterCitesAccuWeather extends RecyclerView.Adapter<RecyclerAdapterCitesAccuWeather.RecyclerViewHolder> {
    private Context mContext;
    private List<LocationSearchModel> locationSearchModelArrayList = new ArrayList<>();
    private List<AccuWeatherModel> accuWeatherModels = new ArrayList<>();
    NavigateTo navigateTo;
    ISuccesFirstWeather iSuccesFirstWeather;

    public RecyclerAdapterCitesAccuWeather(Context context, ISuccesFirstWeather iSuccesFirstWeather, NavigateTo navigateTo, List<LocationSearchModel> locationSearchModelArrayList, List<AccuWeatherModel> AccuWeather5DayModelcities) {
        this.mContext = context;
        this.locationSearchModelArrayList = locationSearchModelArrayList;
        this.accuWeatherModels = AccuWeather5DayModelcities;
        this.navigateTo = navigateTo;
        this.iSuccesFirstWeather = iSuccesFirstWeather;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_city_recycler, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        LocationSearchModel locationSearchModel = locationSearchModelArrayList.get(position);
        AccuWeatherModel accuWeatherModel = accuWeatherModels.get(position);
        if ((position == 0 &&locationSearchModel.getKey()!=null&& !locationSearchModel.getKey().equals("328328") )|| position == 1 ) {
            iSuccesFirstWeather.successWeather(accuWeatherModel);
        }
        holder.tv_city_name.setText(locationSearchModel.getLocalizedName());
        holder.tv_country_name.setText(", " + locationSearchModel.getCountry().getID());
        holder.tv_status.setText(accuWeatherModel.getWeatherText());
        holder.temp.setText(mContext.getString(R.string.format_temperature, accuWeatherModel.getTemperature() != null ? accuWeatherModel.getTemperature().getMetric().getValue() : 0));
        holder.linLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // navigateTo.navigate(DetailsActivity.class, position);
            }
        });
        Glide.with(mContext)
                .load("http://apidev.accuweather.com/developers/Media/Default/WeatherIcons/" + String.format("%02d", accuWeatherModel.getWeatherIcon()) + "-s" + ".png")
                .into(holder.iv_weather_icon);
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accuWeatherModels.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });
    }


    @Override
    public int getItemCount() {
        if (accuWeatherModels == null) return 0;
        else
            return accuWeatherModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_country_name)
        TextView tv_country_name;
        @BindView(R.id.tv_city_name)
        TextView tv_city_name;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.iv_weather_icon)
        ImageView iv_weather_icon;
        @BindView(R.id.temp)
        TextView temp;
        @BindView(R.id.delete_layout)
        FrameLayout deleteLayout;
        @BindView(R.id.linLayout)
        LinearLayout linLayout;
        View view;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            ButterKnife.bind(this, itemView);
        }
    }
}
