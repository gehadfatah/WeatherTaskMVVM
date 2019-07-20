package godaa.android.com.weathertaskapp.ui.exitdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;


import butterknife.ButterKnife;
import butterknife.OnClick;
import godaa.android.com.weathertaskapp.R;

public class ExitDialog extends Dialog {
    Activity activity;

    public ExitDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_exit);

        ButterKnife.bind(this);
        this.setCancelable(true);
        if (this.getWindow() != null)
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @OnClick(R.id.no)
    public void noClick() {
        this.cancel();
    }

    @OnClick(R.id.existYesTV)
    public void existYesTVClick() {
        activity.finish();
        this.cancel();
    }


}