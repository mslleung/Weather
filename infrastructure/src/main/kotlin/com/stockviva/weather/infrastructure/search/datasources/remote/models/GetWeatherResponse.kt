package com.stockviva.weather.infrastructure.search.datasources.remote.models

import com.google.gson.annotations.SerializedName

internal data class GetWeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Double,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long,
)

internal data class Coord(
    val lon: Double,
    val lat: Double,
)

internal data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

internal data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("sea_level") val seaLevel: Double,
    @SerializedName("grnd_level") val grndLevel: Double,
)

internal data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double?,
)

internal data class Clouds(
    val all: Double,
)

internal data class Sys(
    val type: Long,
    val id: Long,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)
