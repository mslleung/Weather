package com.stockviva.weather.infrastructure.search

import com.stockviva.weather.domain.search.Weather

interface IWeatherRepository {

    suspend fun getCurrentWeatherByCityName(cityName: String): Weather

}
