package com.example.smartpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class PotDTO
{
    @SerializedName("serialId")
    @Expose
    private String serialId;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("soil_humidity")
    @Expose
    private Integer soil_humidity;
    @SerializedName("temper")
    @Expose
    private Integer temper;
    @SerializedName("waterLevel")
    @Expose
    private Integer waterLevel;
    @SerializedName("potName")
    @Expose
    private String potName;
    @SerializedName("plantName")
    @Expose
    private String plantName;
    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("wateringDate")
    @Expose
    private LocalDateTime wateringDate;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getSoil_humidity() {
        return soil_humidity;
    }

    public void setSoil_humidity(Integer soil_humidity) {
        this.soil_humidity = soil_humidity;
    }

    public Integer getTemper() {
        return temper;
    }

    public void setTemper(Integer temper) {
        this.temper = temper;
    }

    public Integer getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Integer waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getPotName() {
        return potName;
    }

    public void setPotName(String potName) {
        this.potName = potName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getWateringDate() { return wateringDate; }

    public void setWateringDate(LocalDateTime wateringDate) {
        this.wateringDate = wateringDate;
    }





    @Override
    public String toString()
    {
        return "PotDTO [period = "+period+", serialId = "+serialId+", temper = "+temper+", imageUrl = "+imageUrl+", soil_humidity = "+soil_humidity+", humidity = "+humidity+", waterLevel = "+waterLevel+", potName = "+potName+", plantName = "+plantName+", wateringDate = "+wateringDate+"]";
    }
}
