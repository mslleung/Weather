package com.stockviva.weather.infrastructure.search.datasources.remote

import com.stockviva.weather.infrastructure.search.datasources.remote.models.GetWeatherResponse
import com.stockviva.weather.shared.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
private val log = Logger {}

@Singleton
internal class RemoteWeatherDataSource @Inject constructor(
    private val openWeatherService: OpenWeatherService
) : IRemoteWeatherDataSource {

    class GetCurrentWeatherException : Exception()

    override suspend fun getCurrentWeatherByCityName(cityName: String): GetWeatherResponse {
        val response = openWeatherService.getWeather(cityName).execute()

        val body = response.body()

        if (!response.isSuccessful || body == null) {
            log.error("getCurrentWeatherByLatLng() failed, code: ${response.code()}")
            throw GetCurrentWeatherException()
        }

        return body
    }

}
