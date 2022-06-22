package com.stockviva.weather.infrastructure.search.datasources.local

import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity

internal interface ILocalSearchRecordDataSource {

    suspend fun insertSearchRecord(searchRecordEntity: SearchRecordEntity): Long

    suspend fun deleteSearchRecord(searchRecordEntities: List<SearchRecordEntity>): Int

    suspend fun getSearchRecordsPage(offset: Int, pageSize: Int): List<SearchRecordEntity>

}
