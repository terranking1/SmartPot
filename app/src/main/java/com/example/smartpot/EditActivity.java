package com.example.smartpot;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class EditActivity extends AppCompatActivity {

    private static String serverUrl = "http://3.38.63.85:8081/";
    private static String arduinoUrl = "http://";

    RetrofitClient retrofitClient;
    initMyApi initMyApi;

    EditText edPotName, edPlantName;
    Button btnEditOK, btnFindImage, btnDelete;
    ImageView imgVEditImage;
    MultipartBody.Part uploadFile ;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int findImageCount;
    String myStrUri;
    Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //레트로핏으로 plant 불러오기
        retrofitClient = RetrofitClient.getInstance(serverUrl);
        initMyApi = RetrofitClient.getRetrofitInterface();

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        edPotName = findViewById(R.id.ed_PotName);
        edPlantName = findViewById(R.id.ed_PlantName);
        btnFindImage = findViewById(R.id.btn_FindImage);
        btnEditOK = findViewById(R.id.btn_EditOK);
        btnDelete = findViewById(R.id.btn_Delete);
        imgVEditImage = findViewById(R.id.imgV_EditImage);
        findImageCount = 0;

        Intent intent = getIntent();
        String GetSerialId = intent.getStringExtra("serialId");
        String GetPotName = intent.getStringExtra("potName");
        String GetPlantName = intent.getStringExtra("plantName");
        String GetImageUrl = intent.getStringExtra("imageUrl");

        edPotName.setText(GetPotName);
        edPlantName.setText(GetPlantName);
        Glide.with(EditActivity.this).load(GetImageUrl).into(imgVEditImage);

        btnFindImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        });


        btnEditOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EditPotName = edPotName.getText().toString();
                String EditPlantName = edPlantName.getText().toString();

                HashMap<String, RequestBody> map = new HashMap<>();
                RequestBody serialId = RequestBody.create(MediaType.parse("text/plain"), GetSerialId);
                RequestBody potName = RequestBody.create(MediaType.parse("text/plain"), EditPotName);
                RequestBody plantName = RequestBody.create(MediaType.parse("text/plain"), EditPlantName);
                map.put("serialId", serialId);
                map.put("potName", potName);
                map.put("plantName", plantName);

                initMyApi.setPot(map, uploadFile).enqueue(new Callback<PotDTO>() {
                    @Override
                    public void onResponse(Call<PotDTO> call, Response<PotDTO> response) {
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<PotDTO> call, Throwable t) {
                        Log.e("setPot", t.getMessage());
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                dialog.setTitle("화분을 정말 삭제하시겠습니까?");
                dialog.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FileInputStream fis = openFileInput("serial.txt");
                            byte[] txt = new byte[1000];
                            fis.read(txt);
                            String ReadSerial = new String(txt).replace(GetSerialId+" ", "").trim();
                            try {
                                FileOutputStream fos = openFileOutput("serial.txt", MODE_PRIVATE);
                                fos.write(ReadSerial.getBytes());
                                fos.write(" ".getBytes());
                                fos.close();
                                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "파일 없음",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

            }
        });



    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        Glide.with(EditActivity.this)
                                .load(uri)
                                .into(imgVEditImage);

                        String path = uri.getPath();
                        File file = new File(path);
                        InputStream inputStream = null;
                        try {
                            inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                        }catch(IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray());
                        uploadFile = MultipartBody.Part.createFormData("imageFile", file.getName() ,requestBody);

                        editor.putString("MyUploadFile", uri.toString());
                        editor.apply();

                        findImageCount = 1;
                    }
                }
            });
}