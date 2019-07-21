package godaa.android.com.weathertaskapp.common.provider.scheduler;

import io.reactivex.Scheduler;



public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

}
