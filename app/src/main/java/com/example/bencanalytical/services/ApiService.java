package com.example.bencanalytical.services;

import com.example.bencanalytical.model.responseAuth;
import com.example.bencanalytical.model.responseBencana;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth.php")
    Call<responseAuth> auth(@Field("username") String username,
                            @Field("password") String password);

    @GET("readBencana.php")
    Call<responseBencana> getBencana();
}
