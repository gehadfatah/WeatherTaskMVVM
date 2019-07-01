package godaa.android.com.weathertaskapp.ui.addCityDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.ui.interfaces.IAddCityResponse;

/**
 * Created by Pc1 on 25/03/2018.
 */

public class AddCityDialog extends Dialog {
    Context context;
    LocationSearchModel locationSearchModel;
    AccuWeather5DayModel accuWeather5DayModel;
    AccuWeatherModel accuWeatherModel;
    IAddCityResponse iAddCityResponse;

    @BindView(R.id.recycle5Day)
    RecyclerView recyclerView;
    private RecyclerAdapterDialog adapter;

    public AddCityDialog(@NonNull Context context, IAddCityResponse iAddCityResponse, AccuWeatherModel accuWeatherModel, AccuWeather5DayModel accuWeather5DayModel, LocationSearchModel locationSearchModel) {
        super(context);
        this.context = context;
        this.accuWeatherModel = accuWeatherModel;
        this.accuWeather5DayModel = accuWeather5DayModel;
        this.locationSearchModel = locationSearchModel;
        this.iAddCityResponse = iAddCityResponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_city);

        ButterKnife.bind(this);
        setRecyleView();
        this.setCancelable(true);
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void setRecyleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setHasFixedSize(true);
       // ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
       // recyclerView.addItemDecoration(itemDecoration);
        adapter = new RecyclerAdapterDialog(context, accuWeather5DayModel);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.cancel)
    public void cancelClick() {
        iAddCityResponse.cancelClick();
        dismiss();
    }

    @OnClick(R.id.addcity)
    public void addcityClick() {

        iAddCityResponse.AddClick(accuWeather5DayModel,accuWeatherModel,locationSearchModel);
        dismiss();

    }
}

