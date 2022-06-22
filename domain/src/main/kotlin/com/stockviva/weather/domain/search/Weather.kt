package com.stockviva.weather.domain.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val dt: Long,
    val name: String,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    val tempMin: Double,
    val tempMax: Double,
    val seaLevel: Double,
    val groundLevel: Double,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double?,
) : Parcelable
