package com.stockviva.weather.infrastructure.search.datasources.remote

import com.stockviva.weather.infrastructure.search.datasources.remote.models.GetWeatherResponse
import javax.inject.Singleton

@Singleton
internal interface IRemoteWeatherDataSource {

    suspend fun getCurrentWeatherByCityName(cityName: String): GetWeatherResponse

}
