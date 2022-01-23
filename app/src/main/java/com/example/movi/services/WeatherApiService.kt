package com.example.movi.services

import com.example.movi.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiService {
    private val base_url = "https://api.openweathermap.org/"

    private val api  = Retrofit.Builder()
                            .baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                            .create(WeatherApi::class.java)

    fun getDataService(cityName: String): Single<WeatherModel>{
        return api.getData()
    }
}