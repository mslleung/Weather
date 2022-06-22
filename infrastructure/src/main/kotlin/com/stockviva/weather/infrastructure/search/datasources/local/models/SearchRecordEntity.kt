package com.stockviva.weather.infrastructure.search.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_record",
    indices = [
        Index("city_name", unique = true)
    ]
)
internal data class SearchRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
)
