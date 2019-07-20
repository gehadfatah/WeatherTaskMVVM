package godaa.android.com.weathertaskapp.data.remote.client;

public class SettingsAPI {

    public String getApiKey() {
        return "87ad516d1d4842838fcfebe843d933b1";

      //  return "2BGaQeuL98MDLx6vIiZWx0lGMFqj68Xt";
    }

    public String getBaseURL() {
        return "http://api.accuweather.com/";

      //  return "http://dataservice.accuweather.com/";
    }

    public Long getTimeout() {
        return 10000L;
    }

}
