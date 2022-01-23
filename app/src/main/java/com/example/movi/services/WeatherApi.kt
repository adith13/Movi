package com.example.movi.services

import com.example.movi.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

//    @GET("data/2.5/weather?&appid=65ed597a1179e2eba653aa08159b3576")
//    fun getData(
//        @Query("q") cityName: String
//    ): Single<WeatherModel>
    @GET("data/2.5/weather?q=puducherry&appid=65ed597a1179e2eba653aa08159b3576")
    fun getData(): Single<WeatherModel>

}