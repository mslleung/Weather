package com.stockviva.weather.infrastructure.search.datasources.remote

import com.stockviva.weather.infrastructure.search.datasources.remote.models.GetWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "95d190a434083879a6398aafd54d9e73"

internal interface OpenWeatherService {

    @GET("weather")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric",
    ): Call<GetWeatherResponse>

}
