package com.example.smartpot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText edName, edPeriod, edHumidity, edTemper, edSunlight;
    Button btnEditOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edName = findViewById(R.id.ed_Name);
        edPeriod = findViewById(R.id.ed_Period);
        edHumidity = findViewById(R.id.ed_Humidity);
        edTemper = findViewById(R.id.ed_Temper);
        edSunlight = findViewById(R.id.ed_Sunlight);
        btnEditOK = findViewById(R.id.btn_EditOK);

        btnEditOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EditName = edName.getText().toString();
                String EditPeriod = edPeriod.getText().toString();
                String EditHumidity = edHumidity.getText().toString();
                String EditTemper = edTemper.getText().toString();
                String EditSunlight = edSunlight.getText().toString();

                PotFragment potFragment = new PotFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", EditName);
                bundle.putString("period", EditPeriod);
                bundle.putString("humidity", EditHumidity);
                bundle.putString("temper", EditTemper);
                bundle.putString("sunlight", EditSunlight);
                potFragment.setArguments(bundle);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.viewPager2_container, potFragment).commit();

            }
        });





    }
}