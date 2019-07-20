package godaa.android.com.weathertaskapp.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LocationSearchModel {

    @SerializedName("Version")
    @Expose
    private int version;
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Rank")
    @Expose
    private int rank;
    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;
    @SerializedName("Country")
    @Expose
    private Country country;
    @SerializedName("AdministrativeArea")
    @Expose
    private AdministrativeArea administrativeArea;

    public LocationSearchModel(int version,String key,Country country,String localizedName,String type,int rank) {
        super();
    }

    public LocationSearchModel() {
        super();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }


    public class AdministrativeArea {

        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("LocalizedName")
        @Expose
        private String localizedName;

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getLocalizedName() {
            return localizedName;
        }

        public void setLocalizedName(String localizedName) {
            this.localizedName = localizedName;
        }

    }

    public static class Country {

        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("LocalizedName")
        @Expose
        private String localizedName;

        public Country() {
            super();
        }

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getLocalizedName() {
            return localizedName;
        }

        public void setLocalizedName(String localizedName) {
            this.localizedName = localizedName;
        }

    }
}

