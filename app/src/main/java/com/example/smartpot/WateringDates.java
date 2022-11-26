package com.example.smartpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class WateringDates {
    @SerializedName("wateringDate")
    @Expose
    private LocalDateTime wateringDate;

    public LocalDateTime getWateringDate() {
        return wateringDate;
    }

    public void setWateringDate(LocalDateTime wateringDate) {
        this.wateringDate = wateringDate;
    }
}
