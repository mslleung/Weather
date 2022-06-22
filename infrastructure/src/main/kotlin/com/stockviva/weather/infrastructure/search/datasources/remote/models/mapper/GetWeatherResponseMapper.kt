package com.stockviva.weather.infrastructure.search.datasources.remote.models.mapper

import com.stockviva.weather.domain.search.Weather
import com.stockviva.weather.infrastructure.search.datasources.remote.models.GetWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetWeatherResponseMapper @Inject constructor() {

    fun mapFromResponse(getWeatherResponse: GetWeatherResponse): Weather {
        return Weather(
            dt = getWeatherResponse.dt,
            name = getWeatherResponse.name,
            temp = getWeatherResponse.main.temp,
            feelsLike = getWeatherResponse.main.feelsLike,
            pressure = getWeatherResponse.main.pressure,
            humidity = getWeatherResponse.main.humidity,
            tempMin = getWeatherResponse.main.tempMin,
            tempMax = getWeatherResponse.main.tempMax,
            seaLevel = getWeatherResponse.main.seaLevel,
            groundLevel = getWeatherResponse.main.grndLevel,
            windSpeed = getWeatherResponse.wind.speed,
            windDeg = getWeatherResponse.wind.deg,
            windGust = getWeatherResponse.wind.gust,
        )
    }

}
