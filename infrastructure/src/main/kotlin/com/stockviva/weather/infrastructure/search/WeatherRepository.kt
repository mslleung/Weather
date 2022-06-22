package com.stockviva.weather.infrastructure.search

import com.stockviva.weather.domain.search.Weather
import com.stockviva.weather.infrastructure.di.DataSourceModule
import com.stockviva.weather.infrastructure.search.datasources.remote.IRemoteWeatherDataSource
import com.stockviva.weather.infrastructure.search.datasources.remote.models.mapper.GetWeatherResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject internal constructor(
    @DataSourceModule.RemoteDataSource private val remoteWeatherDataSource: IRemoteWeatherDataSource,
    private val getWeatherResponseMapper: GetWeatherResponseMapper,
) : IWeatherRepository {

    override suspend fun getCurrentWeatherByCityName(cityName: String): Weather {
        val response = remoteWeatherDataSource.getCurrentWeatherByCityName(cityName)
        return getWeatherResponseMapper.mapFromResponse(response)
    }

}
