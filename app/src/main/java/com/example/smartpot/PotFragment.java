package com.example.smartpot;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotFragment extends Fragment {

    RetrofitClient retrofitClient;
    initMyApi initMyApi;

    public static PotFragment newInstance(String serialId, Integer humidity, Integer soil_humidity, Integer temper, Integer waterLevel, String potName, String plantName, Integer period, String imageUrl, LocalDateTime[] wateringDates) {
        if(serialId == null) serialId = "";
        if(humidity == null) humidity = 0;
        if(soil_humidity == null) soil_humidity = 0;
        if(temper == null) temper = 0;
        if(waterLevel == null) waterLevel = 0;
        if(potName == null) potName = "";
        if(plantName == null) plantName = "";
        if(period == null) period = 0;
        if(imageUrl == null) imageUrl = "";
        if(wateringDates == null) wateringDates = new LocalDateTime[0];
        ArrayList<String> wateringDateList = new ArrayList<>();
        if(wateringDates.length > 0) {
            for(int i = 0; i < wateringDates.length; i++) {
                wateringDateList.add(wateringDates[i].format(DateTimeFormatter.ofPattern("MM월 dd일 HH시mm분")));
            }
        }



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
        //bundle.putString("wateringDate", wateringDate.format(DateTimeFormatter.ofPattern("MM월 dd일 HH시mm분")));
        bundle.putStringArrayList("wateringDate", wateringDateList);
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

        //레트로핏으로 plant 불러오기
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        TextView tvPotName = view.findViewById(R.id.tv_PotName);
        TextView tvPlantName = view.findViewById(R.id.tv_PlantName);
        TextView tvHumidity = view.findViewById(R.id.tv_Humidity);
        TextView tvSoilHumidity = view.findViewById(R.id.tv_Soil_Humidity);
        TextView tvTemper = view.findViewById(R.id.tv_Temper);
        TextView tvWaterLevel = view.findViewById(R.id.tv_WaterLevel);
        TextView tvPeriod = view.findViewById(R.id.tv_Period);
        ImageView imgVPotImage = view.findViewById(R.id.imgV_PotImage);
        Button btnEditPeriod = view.findViewById(R.id.btn_EditPeriod);
        Button btnViewWateringDate = view.findViewById(R.id.btn_ViewWateringDate);
        Button btnEdit = view.findViewById(R.id.btn_Edit);

        if (getArguments() != null) {
            String serialId = getArguments().getString("serialId");
            String potName = getArguments().getString("potName");
            String plantName = getArguments().getString("plantName");
            String imageUrl = getArguments().getString("imageUrl");
            int humidity = getArguments().getInt("humidity");
            int soil_humidity = getArguments().getInt("soil_humidity");
            int temper = getArguments().getInt("temper");
            int waterLevel = getArguments().getInt("waterLevel");
            int period = getArguments().getInt("period");


            tvPotName.setText(potName);
            tvPlantName.setText(plantName);
            tvHumidity.setText(Integer.toString(humidity));
            tvSoilHumidity.setText(Integer.toString(soil_humidity));
            tvTemper.setText(Integer.toString(temper));
            tvWaterLevel.setText(Integer.toString(waterLevel));
            tvPeriod.setText(Integer.toString(period));
            Glide.with(getContext()).load(imageUrl).error(R.drawable.defaultpot).into(imgVPotImage);

            btnEditPeriod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText edPeriod = new EditText(getContext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("급수 주기 변경");
                    builder.setView(edPeriod);
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Integer period = Integer.parseInt(edPeriod.getText().toString());

                            initMyApi.setPeriod(new PotDTO(serialId, period)).enqueue(new Callback<PotDTO>() {
                                @Override
                                public void onResponse(Call<PotDTO> call, Response<PotDTO> response) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onFailure(Call<PotDTO> call, Throwable t) {

                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            btnViewWateringDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> wateringDatesList = new ArrayList<>();
                    wateringDatesList = getArguments().getStringArrayList("wateringDate");
                    String[] wateringDatesArr = wateringDatesList.toArray(new String[wateringDatesList.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("급수 기록");
                    builder.setItems(wateringDatesArr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

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