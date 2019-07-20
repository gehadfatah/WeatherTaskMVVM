package godaa.android.com.weathertaskapp.ui.weatherCities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeListener;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.local.prefs.WeatherSharedPreference;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.remote.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.ui.detailWeather.DetailsActivity;
import godaa.android.com.weathertaskapp.ui.interfaces.DeleteFromDatabase;
import godaa.android.com.weathertaskapp.ui.interfaces.ISuccesFirstWeather;
import godaa.android.com.weathertaskapp.ui.interfaces.NavigateTo;
import godaa.android.com.weathertaskapp.common.utils.WeatherConstants;


public class RecyclerAdapterCitesAccuWeather extends RecyclerView.Adapter<RecyclerAdapterCitesAccuWeather.RecyclerViewHolder> implements SwipeListener {
    private Context mContext;
    private List<LocationSearchModel> locationSearchModelArrayList = new ArrayList<>();
    private List<AccuWeatherModel> accuWeatherModels = new ArrayList<>();
    NavigateTo navigateTo;
    ISuccesFirstWeather iSuccesFirstWeather;
    DeleteFromDatabase deleteFromDatabase;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public RecyclerAdapterCitesAccuWeather(Context context, DeleteFromDatabase deleteFromDatabase, ISuccesFirstWeather iSuccesFirstWeather, NavigateTo navigateTo, List<LocationSearchModel> locationSearchModelArrayList, List<AccuWeatherModel> AccuWeather5DayModelcities) {
        this.mContext = context;
        this.deleteFromDatabase = deleteFromDatabase;
        this.locationSearchModelArrayList = locationSearchModelArrayList;
        this.accuWeatherModels = AccuWeather5DayModelcities;
        this.navigateTo = navigateTo;
        this.iSuccesFirstWeather = iSuccesFirstWeather;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_list, null);
        return new RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // binderHelper.bind(holder.swipeLayout, data);
        switch (position % 4) {
            case 0:
                holder.linLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_white));
                setcolortextblack(holder,R.color.black1);

                break;
            case 1:
                holder.linLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_first));
                setcolortextblack(holder,R.color.white1);

                break;
            case 2:
                holder.linLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_second));
                setcolortextblack(holder,R.color.white1);

                break;
            case 3:
                holder.linLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_third));
                setcolortextblack(holder,R.color.white1);

                break;
        }
        holder.swipeLayout.setSwipeListener(this);

        LocationSearchModel locationSearchModel = locationSearchModelArrayList.get(position);
        AccuWeatherModel accuWeatherModel = accuWeatherModels.get(position);
        if (
                (position == 0 && locationSearchModel.getKey() != null && !locationSearchModel.getKey().equals("328328"))
                        || position == 1
                        || position == 0 && new WeatherSharedPreference(mContext).retrieveBooleanFromSharedPreference(WeatherConstants.locationLondon)
        ) {
            iSuccesFirstWeather.successWeather(accuWeatherModel);
        }
        holder.tv_city_name.setText(locationSearchModel.getLocalizedName());
        holder.tv_country_name.setText(String.format(", %s", locationSearchModel.getCountry().getID()));
        holder.tv_status.setText(accuWeatherModel.getWeatherText());
        holder.temp.setText(mContext.getString(R.string.format_temperature, accuWeatherModel.getTemperature() != null ? accuWeatherModel.getTemperature().getMetric().getValue() : 0));
        holder.linLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo.navigate(v, DetailsActivity.class, position, locationSearchModel.getLocalizedName());


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
                deleteFromDatabase.delete(locationSearchModel.getKey());

            }
        });
    }

    private void setcolortextblack(RecyclerViewHolder holder,int color) {
        holder.tv_city_name.setTextColor(mContext.getResources().getColor(color));
        holder.tv_country_name.setTextColor(mContext.getResources().getColor(color));
        holder.tv_status.setTextColor(mContext.getResources().getColor(color));
        holder.temp.setTextColor(mContext.getResources().getColor(color));
    }


    @Override
    public int getItemCount() {
        if (accuWeatherModels == null) return 0;
        else
            return accuWeatherModels.size();
    }

    @Override
    public void onClosed(SwipeRevealLayout view) {

    }

    @Override
    public void onOpened(SwipeRevealLayout view, int dragEdge) {
        final RecyclerViewHolder holder = (RecyclerViewHolder) view.getTag();
        if (dragEdge == SwipeRevealLayout.DRAG_EDGE_LEFT)
            holder.deleteLayout.performClick();
    }

    @Override
    public void onSlide(SwipeRevealLayout view, float slideOffset) {

    }

    @Override
    public void onTouchUp(boolean isUp) {

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
        RelativeLayout deleteLayout;

        @BindView(R.id.linLayout)
        LinearLayout linLayout;
        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeLayout;
        View view;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            ButterKnife.bind(this, itemView);
            swipeLayout.setTag(this);

        }
    }
}
