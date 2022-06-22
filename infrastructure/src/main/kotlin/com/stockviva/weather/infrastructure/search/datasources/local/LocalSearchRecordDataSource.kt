package com.stockviva.weather.infrastructure.search.datasources.local

import com.stockviva.weather.infrastructure.AppDatabase
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity
import javax.inject.Inject

internal class LocalSearchRecordDataSource @Inject constructor(
    private val db: AppDatabase,
) : ILocalSearchRecordDataSource {

    override suspend fun insertSearchRecord(searchRecordEntity: SearchRecordEntity): Long {
        val rowId = db.searchRecordDao().insert(searchRecordEntity)
        require(rowId > 0)
        return rowId
    }

    override suspend fun deleteSearchRecord(searchRecordEntities: List<SearchRecordEntity>): Int {
        val rowsDeleted = db.searchRecordDao().delete(searchRecordEntities)
        require(rowsDeleted == searchRecordEntities.size)
        return rowsDeleted
    }

    override suspend fun getSearchRecordsPage(
        offset: Int,
        pageSize: Int
    ): List<SearchRecordEntity> {
        return db.searchRecordDao().getSearchRecordsPage(offset, pageSize)
    }

}