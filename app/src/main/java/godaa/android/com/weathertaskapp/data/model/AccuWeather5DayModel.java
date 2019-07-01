package godaa.android.com.weathertaskapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AccuWeather5DayModel implements Parcelable {

    @SerializedName("Headline")
    @Expose
    private Headline headline;
    @SerializedName("DailyForecasts")
    @Expose
    private List<DailyForecast> dailyForecasts = null;

    protected AccuWeather5DayModel(Parcel in) {
        this.headline = ((Headline) in.readValue((Headline.class.getClassLoader())));
        in.readList(this.dailyForecasts, (DailyForecast.class.getClassLoader()));

    }

    public static final Creator<AccuWeather5DayModel> CREATOR = new Creator<AccuWeather5DayModel>() {
        @Override
        public AccuWeather5DayModel createFromParcel(Parcel in) {
            return new AccuWeather5DayModel(in);
        }

        @Override
        public AccuWeather5DayModel[] newArray(int size) {
            return new AccuWeather5DayModel[size];
        }
    };

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(headline);
        dest.writeList(dailyForecasts);

    }

    public static class DailyForecast implements Parcelable {

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EpochDate")
        @Expose
        private int epochDate;
        @SerializedName("Temperature")
        @Expose
        private Temperature temperature;
        @SerializedName("Day")
        @Expose
        private Day day;
        @SerializedName("Night")
        @Expose
        private Night night;
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
            day = in.readParcelable(Day.class.getClassLoader());
            night = in.readParcelable(Night.class.getClassLoader());
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

        public Day getDay() {
            return day;
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public Night getNight() {
            return night;
        }

        public void setNight(Night night) {
            this.night = night;
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
            dest.writeParcelable(day, flags);
            dest.writeParcelable(night, flags);
            dest.writeStringList(sources);
            dest.writeString(mobileLink);
            dest.writeString(link);
        }
    }


    public static class Day implements Parcelable{

        @SerializedName("Icon")
        @Expose
        private int icon;
        @SerializedName("IconPhrase")
        @Expose
        private String iconPhrase;
        @SerializedName("LocalSource")
        @Expose
        private LocalSource localSource;

        protected Day(Parcel in) {
            icon = in.readInt();
            iconPhrase = in.readString();
            localSource = in.readParcelable(LocalSource.class.getClassLoader());
        }

        public static final Creator<Day> CREATOR = new Creator<Day>() {
            @Override
            public Day createFromParcel(Parcel in) {
                return new Day(in);
            }

            @Override
            public Day[] newArray(int size) {
                return new Day[size];
            }
        };

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }

        public void setIconPhrase(String iconPhrase) {
            this.iconPhrase = iconPhrase;
        }

        public LocalSource getLocalSource() {
            return localSource;
        }

        public void setLocalSource(LocalSource localSource) {
            this.localSource = localSource;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(icon);
            dest.writeString(iconPhrase);
            dest.writeParcelable(localSource, flags);
        }
    }


    public static class Headline implements Parcelable {

        @SerializedName("EffectiveDate")
        @Expose
        private String effectiveDate;
        @SerializedName("EffectiveEpochDate")
        @Expose
        private int effectiveEpochDate;
        @SerializedName("Severity")
        @Expose
        private int severity;
        @SerializedName("Text")
        @Expose
        private String text;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("EndDate")
        @Expose
        private Object endDate;
        @SerializedName("EndEpochDate")
        @Expose
        private Object endEpochDate;
        @SerializedName("MobileLink")
        @Expose
        private String mobileLink;
        @SerializedName("Link")
        @Expose
        private String link;

        protected Headline(Parcel in) {
            effectiveDate = in.readString();
            effectiveEpochDate = in.readInt();
            severity = in.readInt();
            text = in.readString();
            category = in.readString();
            mobileLink = in.readString();
            link = in.readString();
        }

        public static final Creator<Headline> CREATOR = new Creator<Headline>() {
            @Override
            public Headline createFromParcel(Parcel in) {
                return new Headline(in);
            }

            @Override
            public Headline[] newArray(int size) {
                return new Headline[size];
            }
        };

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public int getEffectiveEpochDate() {
            return effectiveEpochDate;
        }

        public void setEffectiveEpochDate(int effectiveEpochDate) {
            this.effectiveEpochDate = effectiveEpochDate;
        }

        public int getSeverity() {
            return severity;
        }

        public void setSeverity(int severity) {
            this.severity = severity;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getEndEpochDate() {
            return endEpochDate;
        }

        public void setEndEpochDate(Object endEpochDate) {
            this.endEpochDate = endEpochDate;
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
            dest.writeString(effectiveDate);
            dest.writeInt(effectiveEpochDate);
            dest.writeInt(severity);
            dest.writeString(text);
            dest.writeString(category);
            dest.writeString(mobileLink);
            dest.writeString(link);
        }
    }

    public static class LocalSource implements Parcelable {

        @SerializedName("Id")
        @Expose
        private int id;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("WeatherCode")
        @Expose
        private String weatherCode;

        protected LocalSource(Parcel in) {
            id = in.readInt();
            name = in.readString();
            weatherCode = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(weatherCode);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<LocalSource> CREATOR = new Creator<LocalSource>() {
            @Override
            public LocalSource createFromParcel(Parcel in) {
                return new LocalSource(in);
            }

            @Override
            public LocalSource[] newArray(int size) {
                return new LocalSource[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(String weatherCode) {
            this.weatherCode = weatherCode;
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


    public static class Night implements Parcelable{

        @SerializedName("Icon")
        @Expose
        private int icon;
        @SerializedName("IconPhrase")
        @Expose
        private String iconPhrase;
        @SerializedName("LocalSource")
        @Expose
        private LocalSource localSource;

        protected Night(Parcel in) {
            icon = in.readInt();
            iconPhrase = in.readString();
            localSource = in.readParcelable(LocalSource.class.getClassLoader());
        }

        public static final Creator<Night> CREATOR = new Creator<Night>() {
            @Override
            public Night createFromParcel(Parcel in) {
                return new Night(in);
            }

            @Override
            public Night[] newArray(int size) {
                return new Night[size];
            }
        };

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }

        public void setIconPhrase(String iconPhrase) {
            this.iconPhrase = iconPhrase;
        }

        public LocalSource getLocalSource() {
            return localSource;
        }

        public void setLocalSource(LocalSource localSource) {
            this.localSource = localSource;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(icon);
            dest.writeString(iconPhrase);
            dest.writeParcelable(localSource, flags);
        }
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
}
