package godaa.android.com.weathertaskapp.ui.addCityDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;


public class RecyclerAdapterDialog extends RecyclerView.Adapter<RecyclerAdapterDialog.RecyclerViewHolder> {
    Context context;
    private List<AccuWeather5DayModel.DailyForecast> mWeatherList = new ArrayList<>();

    public RecyclerAdapterDialog(Context context, AccuWeather5DayModel model) {
        this.context = context;
        this.mWeatherList = model.getDailyForecasts();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recycler_5day, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        AccuWeather5DayModel.DailyForecast dailyForecast = mWeatherList.get(position);
        try {
            holder.tvWeatherDate.setText(setDateFormat(mWeatherList.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }        holder.tvTempMin.setText(String.format("%s", context.getString(R.string.format_temperature, mWeatherList.get(position).getTemperature().getMinimum().getValue())));

        holder.tvTempMax.setText(String.format("%s", context.getString(R.string.format_temperature, mWeatherList.get(position).getTemperature().getMaximum().getValue())));
    }
    public String setDateFormat(String unformattedDate) throws ParseException {
        Date dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(unformattedDate);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(dateformat);
    }
    @Override
    public int getItemCount() {
        if (mWeatherList == null) return 0;
        else

            return mWeatherList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_temp_max)
        TextView tvTempMax;
        @BindView(R.id.tv_weather_date)
        TextView tvWeatherDate;
        @BindView(R.id.tv_temp_min)
        TextView tvTempMin;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
