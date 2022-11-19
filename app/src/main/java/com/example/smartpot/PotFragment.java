package com.example.smartpot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PotFragment extends Fragment {


    public static PotFragment newInstance(String serialId, Integer humidity, Integer soil_humidity, Integer temper, Integer waterLevel, String potName, String plantName, Integer period, String imageUrl, LocalDateTime wateringDate) {
        if(serialId == null) serialId = "";
        if(humidity == null) humidity = 0;
        if(soil_humidity == null) soil_humidity = 0;
        if(temper == null) temper = 0;
        if(waterLevel == null) waterLevel = 0;
        if(potName == null) potName = "";
        if(plantName == null) plantName = "";
        if(period == null) period = 0;
        if(imageUrl == null) imageUrl = "";
        if(wateringDate == null) wateringDate = LocalDateTime.parse("0000-00-00", DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        PotFragment potFragment = new PotFragment();
        Bundle bundle = new Bundle();
        bundle.putString("serialId", serialId);
        bundle.putInt("humidity", humidity);
        bundle.putInt("soil_humidity", soil_humidity);
        bundle.putInt("temper", temper);
        bundle.putInt("waterLevel", waterLevel);
        bundle.putString("potName", potName);
        bundle.putString("plantName", plantName);
        bundle.putInt("period", period);
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("wateringDate", wateringDate.format(DateTimeFormatter.ofPattern("MM월 dd일 HH시mm분")));
        potFragment.setArguments(bundle);
        return potFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_pot, container, false);

        TextView tvPotName = view.findViewById(R.id.tv_PotName);
        TextView tvPlantName = view.findViewById(R.id.tv_PlantName);
        TextView tvHumidity = view.findViewById(R.id.tv_Humidity);
        TextView tvSoilHumidity = view.findViewById(R.id.tv_Soil_Humidity);
        TextView tvTemper = view.findViewById(R.id.tv_Temper);
        TextView tvWaterLevel = view.findViewById(R.id.tv_WaterLevel);
        TextView tvPeriod = view.findViewById(R.id.tv_Period);
        TextView tvWateringDate = view.findViewById(R.id.tv_wateringDate);
        Button btnEdit = view.findViewById(R.id.btn_Edit);

        if (getArguments() != null) {
            String potName = getArguments().getString("potName");
            String plantName = getArguments().getString("plantName");
            int humidity = getArguments().getInt("humidity");
            int soil_humidity = getArguments().getInt("soil_humidity");
            int temper = getArguments().getInt("temper");
            int waterLevel = getArguments().getInt("waterLevel");
            int period = getArguments().getInt("period");
            String wateringDate = getArguments().getString("wateringDate");

            tvPotName.setText(potName);
            tvPlantName.setText(plantName);
            tvHumidity.setText(Integer.toString(humidity));
            tvSoilHumidity.setText(Integer.toString(soil_humidity));
            tvTemper.setText(Integer.toString(temper));
            tvWaterLevel.setText(Integer.toString(waterLevel));
            tvPeriod.setText(Integer.toString(period));
            tvWateringDate.setText(wateringDate);

        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        return view;
    }
}