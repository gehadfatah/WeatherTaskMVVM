package godaa.android.com.weathertaskapp.ui.detailWeather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;
import timber.log.Timber;


public class RecyclerAdapterAccuWeather extends RecyclerView.Adapter<RecyclerAdapterAccuWeather.RecyclerViewHolder> {
    private Context mContext;
    private List<AccuWeather5DayModel.DailyForecast> mWeatherList = new ArrayList<>();

    public RecyclerAdapterAccuWeather(Context context, AccuWeather5DayModel model) {
        this.mContext = context;
        this.mWeatherList = model.getDailyForecasts();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler_5day_new, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        switch (position % 3) {
            case 0:
                holder.listLin.setBackgroundColor(mContext.getResources().getColor(R.color.third));

                break;
            case 1:
                holder.listLin.setBackgroundColor(mContext.getResources().getColor(R.color.first));

                break;
            case 2:
                holder.listLin.setBackgroundColor(mContext.getResources().getColor(R.color.second));

                break;
        }

        try {
            holder.tvWeatherDate.setText(setDateFormatWeek(mWeatherList.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvTempMin.setText(String.format("%s", mContext.getString(R.string.format_temperature, mWeatherList.get(position).getTemperature().getMinimum().getValue())));

        holder.tvTempMax.setText(String.format("%s", mContext.getString(R.string.format_temperature, mWeatherList.get(position).getTemperature().getMaximum().getValue())));
        holder.weathStatus.setText(mWeatherList.get(position).getDay().getIconPhrase());
        Glide.with(mContext)
                .load("http://apidev.accuweather.com/developers/Media/Default/WeatherIcons/" + String.format("%02d", mWeatherList.get(position).getDay().getIcon()) + "-s" + ".png")
                .into(holder.image_featured);
    }

    public String setDateFormat(String unformattedDate) throws ParseException {
        Date dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(unformattedDate);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(dateformat);
    }

    public String setDateFormatWeek(String unformattedDate) throws ParseException {
        Date dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(unformattedDate);
        Date today = new Date();
        long diff = 0;
        try {
            diff = (today.getTime() - dateformat.getTime()) / (86400000);
        } catch (Exception e) {
            Timber.d("setDateFormatWeek: ");
        }
        String days = "TODAY";
        if (diff == 1) {
            days = "YESTERDAY";
            return days;
        } else if (diff > 1) {
            return (new SimpleDateFormat("EEEE")).format(dateformat);
        } else {
            return days;

        }

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
        @BindView(R.id.weathStatus)
        TextView weathStatus;
        @BindView(R.id.image_featured)
        ImageView image_featured;
        @BindView(R.id.listLin)
        LinearLayout listLin;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
