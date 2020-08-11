package com.example.bencanalytical.services;

import com.example.bencanalytical.model.responseAuth;
import com.example.bencanalytical.model.responseBencana;
import com.example.bencanalytical.model.responseGeneral;
import com.example.bencanalytical.model.responsePengaduan;
import com.example.bencanalytical.model.responseProfile;
import com.example.bencanalytical.model.weather.resultWeather;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth.php")
    Call<responseAuth> auth(@Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("addUser.php")
    Call<responseGeneral> addUser(@Field("username") String username,
                                  @Field("fullname") String fullname,
                                  @Field("password") String password,
                                  @Field("email") String email
    );

    @GET("readBencana.php")
    Call<responseBencana> getBencana();

    @GET("readPengaduan.php/{iduser}")
    Call<responsePengaduan> getPengaduan(
            @Query("iduser") String iduser
    );

    @Multipart
    @POST("addPengaduan.php")
    Call<responseGeneral> addPengaduan(
            @Part MultipartBody.Part gambar,
            @Part("gambar") RequestBody name,
            @Part("iduser") RequestBody iduser,
            @Part("namabencana") RequestBody namabencana,
            @Part("koordinat") RequestBody koordinat,
            @Part("lokasi") RequestBody lokasi,
            @Part("kejadian") RequestBody kejadian
    );

    @GET("readUser.php/{username}")
    Call<responseProfile> getUser(
            @Query("username") String username
    );

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<responseGeneral> updateUser(@Field("username") String username,
                                     @Field("fullname") String fullname,
                                     @Field("phone") String phone,
                                     @Field("email") String email,
                                     @Field("alamat") String alamat
    );

    /*----Weather----*/
    @GET()
    Call<resultWeather> getWeather(@Url String url);
}
