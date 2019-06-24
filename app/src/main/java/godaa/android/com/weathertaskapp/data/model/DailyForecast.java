package godaa.android.com.weathertaskapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecast implements Parcelable {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EpochDate")
    @Expose
    private int epochDate;
    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;
    @SerializedName("Sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    protected DailyForecast(Parcel in) {
        date = in.readString();
        epochDate = in.readInt();
        temperature = in.readParcelable(Temperature.class.getClassLoader());
        sources = in.createStringArrayList();
        mobileLink = in.readString();
        link = in.readString();
    }

    public static final Creator<DailyForecast> CREATOR = new Creator<DailyForecast>() {
        @Override
        public DailyForecast createFromParcel(Parcel in) {
            return new DailyForecast(in);
        }

        @Override
        public DailyForecast[] newArray(int size) {
            return new DailyForecast[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(int epochDate) {
        this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }



    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(epochDate);
        dest.writeParcelable(temperature, flags);
        dest.writeStringList(sources);
        dest.writeString(mobileLink);
        dest.writeString(link);
    }
    public static class Temperature implements Parcelable {

        @SerializedName("Minimum")
        @Expose
        private Minimum minimum;
        @SerializedName("Maximum")
        @Expose
        private Maximum maximum;

        protected Temperature(Parcel in) {
            minimum = in.readParcelable(Minimum.class.getClassLoader());
            maximum = in.readParcelable(Maximum.class.getClassLoader());
        }

        public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
            @Override
            public Temperature createFromParcel(Parcel in) {
                return new Temperature(in);
            }

            @Override
            public Temperature[] newArray(int size) {
                return new Temperature[size];
            }
        };

        public Minimum getMinimum() {
            return minimum;
        }

        public void setMinimum(Minimum minimum) {
            this.minimum = minimum;
        }

        public Maximum getMaximum() {
            return maximum;
        }

        public void setMaximum(Maximum maximum) {
            this.maximum = maximum;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(minimum, flags);
            dest.writeParcelable(maximum, flags);
        }
    }
    public static class Maximum implements Parcelable {

        @SerializedName("Value")
        @Expose
        private double value;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("UnitType")
        @Expose
        private int unitType;

        protected Maximum(Parcel in) {
            value = in.readDouble();
            unit = in.readString();
            unitType = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(value);
            dest.writeString(unit);
            dest.writeInt(unitType);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Maximum> CREATOR = new Creator<Maximum>() {
            @Override
            public Maximum createFromParcel(Parcel in) {
                return new Maximum(in);
            }

            @Override
            public Maximum[] newArray(int size) {
                return new Maximum[size];
            }
        };

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getUnitType() {
            return unitType;
        }

        public void setUnitType(int unitType) {
            this.unitType = unitType;
        }

    }


    public static class Minimum implements Parcelable{

        @SerializedName("Value")
        @Expose
        private double value;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("UnitType")
        @Expose
        private int unitType;

        protected Minimum(Parcel in) {
            value = in.readDouble();
            unit = in.readString();
            unitType = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(value);
            dest.writeString(unit);
            dest.writeInt(unitType);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Minimum> CREATOR = new Creator<Minimum>() {
            @Override
            public Minimum createFromParcel(Parcel in) {
                return new Minimum(in);
            }

            @Override
            public Minimum[] newArray(int size) {
                return new Minimum[size];
            }
        };

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getUnitType() {
            return unitType;
        }

        public void setUnitType(int unitType) {
            this.unitType = unitType;
        }

    }

}