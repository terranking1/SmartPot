package com.example.smartpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlantDTO
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("temper")
    @Expose
    private String temper;
    @SerializedName("sunlight")
    @Expose
    private String sunlight;

    public String getPeriod ()
    {
        return period;
    }

    public void setPeriod (String period)
    {
        this.period = period;
    }

    public String getSunlight ()
    {
        return sunlight;
    }

    public void setSunlight (String sunlight)
    {
        this.sunlight = sunlight;
    }

    public String getTemper ()
    {
        return temper;
    }

    public void setTemper (String temper)
    {
        this.temper = temper;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    @Override
    public String toString()
    {
        return "PlantDTO [period = "+period+", sunlight = "+sunlight+", temper = "+temper+", name = "+name+", humidity = "+humidity+"]";
    }
}
