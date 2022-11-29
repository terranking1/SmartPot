package com.example.smartpot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlantGuideActivity extends AppCompatActivity {

    TextView tvName, tvPeriod, tvHumidity, tvTemper, tvSunlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_guide);

        tvName = findViewById(R.id.tv_Name);
        tvPeriod = findViewById(R.id.tv_Period);
        tvHumidity = findViewById(R.id.tv_Humidity);
        tvTemper = findViewById(R.id.tv_Temper);
        tvSunlight = findViewById(R.id.tv_Sunlight);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String period = intent.getStringExtra("period");
        String humidity = intent.getStringExtra("humidity");
        String temper = intent.getStringExtra("temper");
        String sunlight = intent.getStringExtra("sunlight");

        tvName.setText(name);
        tvPeriod.setText(period);
        tvHumidity.setText(humidity);
        tvTemper.setText(temper);
        tvSunlight.setText(sunlight);
    }
}