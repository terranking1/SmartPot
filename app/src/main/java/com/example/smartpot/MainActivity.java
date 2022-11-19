package com.example.smartpot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RetrofitClient retrofitClient;
    initMyApi initMyApi;
    ViewPager2 viewPager2;

    Button btnAddPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2_container);
        btnAddPlant = findViewById(R.id.btn_AddPlant);


        ArrayList<Fragment> fragments = new ArrayList<>();
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(MainActivity.this, fragments);
        CircleIndicator3 indicator = (CircleIndicator3) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager2);

        //레트로핏으로 plant 불러오기
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        try {
            FileInputStream fis = openFileInput("serial.txt");
            byte[] txt = new byte[1000];
            fis.read(txt);
            String ReadSerial = new String(txt).trim();
            String[] SerialArr = ReadSerial.split(" ");
            List<String> SerialList = new ArrayList<>(Arrays.asList(SerialArr));

            initMyApi.getPotDTO().enqueue(new Callback<List<PotDTO>>() {
                @Override
                public void onResponse(Call<List<PotDTO>> call, Response<List<PotDTO>> response) {
                    if (response.isSuccessful()) {
                        List<PotDTO> potDTO = response.body();
                        for (int i = 0; i < potDTO.size(); i++) {
                            String serialId = (String) potDTO.get(i).getSerialId();
                            Integer humidity = (Integer) potDTO.get(i).getHumidity();
                            Integer soil_humidity = (Integer) potDTO.get(i).getSoil_humidity();
                            Integer temper = (Integer) potDTO.get(i).getTemper();
                            Integer waterLevel = (Integer) potDTO.get(i).getWaterLevel();
                            String potName = (String) potDTO.get(i).getPotName();
                            String plantName = (String) potDTO.get(i).getPlantName();
                            Integer period = (Integer) potDTO.get(i).getPeriod();
                            String imageUrl = (String) potDTO.get(i).getImageUrl();
                            LocalDateTime wateringDate = (LocalDateTime) potDTO.get(i).getWateringDate();

                            if(SerialList.contains(serialId)) {
                                fragments.add(PotFragment.newInstance(serialId, humidity, soil_humidity, temper, waterLevel, potName, plantName, period, imageUrl, wateringDate));
                            }
                            viewPager2.setAdapter(viewPager2Adapter);
                            indicator.setViewPager(viewPager2);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<PotDTO>> call, Throwable t) {
                    Log.e("getPotDTO", t.getMessage());
                }
            });
        } catch (IOException e) {
            try {
                FileOutputStream fos = openFileOutput("serial.txt", MODE_APPEND);
                fos.close();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }





        btnAddPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edSerial = new EditText(MainActivity.this);


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("시리얼 넘버 입력");
                dialog.setView(edSerial);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String serial = edSerial.getText().toString().trim();


                        try {
                            FileInputStream fis = openFileInput("serial.txt");
                            byte[] txt = new byte[1000];
                            fis.read(txt);
                            String ReadSerial = new String(txt).trim();
                            String[] SerialArr = ReadSerial.split(" ");
                            List<String> SerialList = new ArrayList<>(Arrays.asList(SerialArr));
                            if(SerialList.contains(serial)) {
                                Toast.makeText(getApplicationContext(), "이미 등록되어 있습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                initMyApi.getPotDTO().enqueue(new Callback<List<PotDTO>>() {
                                    @Override
                                    public void onResponse(Call<List<PotDTO>> call, Response<List<PotDTO>> response) {
                                        if (response.isSuccessful()) {
                                            List<PotDTO> potDTO = response.body();
                                            int beforeSize = fragments.size();
                                            for (int i = 0; i < potDTO.size(); i++) {

                                                if (serial.equals(potDTO.get(i).getSerialId())) {
                                                    String serialId = (String) potDTO.get(i).getSerialId();
                                                    Integer humidity = (Integer) potDTO.get(i).getHumidity();
                                                    Integer soil_humidity = (Integer) potDTO.get(i).getSoil_humidity();
                                                    Integer temper = (Integer) potDTO.get(i).getTemper();
                                                    Integer waterLevel = (Integer) potDTO.get(i).getWaterLevel();
                                                    String potName = (String) potDTO.get(i).getPotName();
                                                    String plantName = (String) potDTO.get(i).getPlantName();
                                                    Integer period = (Integer) potDTO.get(i).getPeriod();
                                                    String imageUrl = (String) potDTO.get(i).getImageUrl();
                                                    LocalDateTime wateringDate = (LocalDateTime) potDTO.get(i).getWateringDate();

                                                    try {
                                                        FileOutputStream fos = openFileOutput("serial.txt", MODE_APPEND);
                                                        fos.write(serial.getBytes());
                                                        fos.write(" ".getBytes());
                                                        fos.close();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    fragments.add(PotFragment.newInstance(serialId, humidity, soil_humidity, temper, waterLevel, potName, plantName, period, imageUrl, wateringDate));
                                                }

                                                //뷰페이저 적용
                                                viewPager2.setAdapter(viewPager2Adapter);
                                                indicator.setViewPager(viewPager2);
                                            }
                                            if(beforeSize == fragments.size()) {
                                                Toast.makeText(getApplicationContext(), "유효하지 않은 시리얼 넘버입니다.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<List<PotDTO>> call, Throwable t) {
                                        Log.e("getPotDTO", t.getMessage());
                                    }
                                });
                            }

                            fis.close();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "파일 없음",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();
            }
        });


    }

}
