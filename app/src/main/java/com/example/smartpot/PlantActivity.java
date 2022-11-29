package com.example.smartpot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantActivity extends AppCompatActivity {

    RetrofitClient retrofitClient;
    initMyApi initMyApi;

    ArrayList<PlantDTO> plantData = new ArrayList<>();
    ListView listView;

    String name, period, humidity, temper, sunlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        //레트로핏으로 plant 불러오기
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        listView = findViewById(R.id.listView);

        initMyApi.getPlantDTO().enqueue(new Callback<List<PlantDTO>>() {
            @Override
            public void onResponse(Call<List<PlantDTO>> call, Response<List<PlantDTO>> response) {
                List<PlantDTO> plantDTO = response.body();
                for(int i = 0; i < plantDTO.size(); i++) {
                    name = plantDTO.get(i).getName();
                    period = plantDTO.get(i).getPeriod();
                    humidity = plantDTO.get(i).getHumidity();
                    temper = plantDTO.get(i).getTemper();
                    sunlight = plantDTO.get(i).getSunlight();

                    plantData.add(new PlantDTO(name, period, humidity, temper, sunlight));
                }
                final ListViewAdapter listViewAdapter = new ListViewAdapter(PlantActivity.this, plantData);
                listView.setAdapter(listViewAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        name = plantData.get(i).getName();
                        period = plantData.get(i).getPeriod();
                        humidity = plantData.get(i).getHumidity();
                        temper = plantData.get(i).getTemper();
                        sunlight = plantData.get(i).getSunlight();

                        Intent intent = new Intent(PlantActivity.this, PlantGuideActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("period", period);
                        intent.putExtra("humidity", humidity);
                        intent.putExtra("temper", temper);
                        intent.putExtra("sunlight", sunlight);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PlantDTO>> call, Throwable t) {
                Log.e("getPlantDTO", t.getMessage());
            }
        });
    }
}