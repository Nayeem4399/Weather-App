package com.example.weatherapp;

import com.example.weatherapp.current_weather_response.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherServiceApi {
    @GET
    Call<WeatherResponse>getCurrentWeatherData(@Url String endUrl);
}
