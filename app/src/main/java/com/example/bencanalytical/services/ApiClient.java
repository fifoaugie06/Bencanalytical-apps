package com.example.bencanalytical.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://bencanalytical.faftech.my.id/";
    private static final String BASE_URL_API = BASE_URL + "api-mobile/";

    public static final String BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather?q=Medan&units=metric&lang=id&appid=";
    public static final String BASE_URL_WEATHER_KEY = BASE_URL_WEATHER + "bc3ba830744a9c2b0f924e188052e586";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientWeather() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WEATHER_KEY)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
