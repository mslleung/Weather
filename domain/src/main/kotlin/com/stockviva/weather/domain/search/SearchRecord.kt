package com.stockviva.weather.domain.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRecord(
    val id: Long = 0,
    val cityName: String,
    val timestamp: Long,
) : Parcelable {
    init {
        require(cityName.isNotBlank())
    }
}
