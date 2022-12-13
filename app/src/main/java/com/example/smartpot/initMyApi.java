package com.example.smartpot;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface initMyApi {
    @GET("/plant/findAll")
    Call<List<PlantDTO>> getPlantDTO();

    @GET("/pot/potMain")
    Call<List<PotDTO>> getPotDTO();

    @Headers({"Content-Type: application/json"})
    @POST("/pot/setPeriod")
    Call<PotDTO> setPeriod(@Body PotDTO body);

    @Headers({"Content-Type: application/json"})
    @POST("/init")
    Call<PotDTO> sendSerialId(@Body PotDTO body);

    @Multipart
    @POST("/pot/potSetting")
    Call<PotDTO> setPot(
            @PartMap HashMap<String, RequestBody> data,
            @Part() MultipartBody.Part imageFile);
}
