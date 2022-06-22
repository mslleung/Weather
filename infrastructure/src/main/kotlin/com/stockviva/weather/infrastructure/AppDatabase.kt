package com.stockviva.weather.infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stockviva.weather.infrastructure.search.datasources.local.daos.SearchRecordDao
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity

@Database(
    entities = [
        SearchRecordEntity::class,
    ],
    version = 1
)
internal abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "db"
    }

    abstract fun searchRecordDao(): SearchRecordDao

}
