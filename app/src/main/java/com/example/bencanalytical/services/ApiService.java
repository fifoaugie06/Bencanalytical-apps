package com.example.bencanalytical.services;


import com.example.bencanalytical.model.ResponseGeneral;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth.php")
    Call<ResponseGeneral> auth(@Field("username") String username,
                               @Field("password") String password);
}
