package com.stockviva.weather.infrastructure.search.datasources.local.models.mapper

import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchRecordMapper @Inject constructor() {

    fun mapToData(searchRecord: SearchRecord): SearchRecordEntity {
        return SearchRecordEntity(
            id = searchRecord.id,
            cityName = searchRecord.cityName,
            timestamp = searchRecord.timestamp,
        )
    }

    fun mapFromData(searchRecordEntity: SearchRecordEntity): SearchRecord {
        return SearchRecord(
            id = searchRecordEntity.id,
            cityName = searchRecordEntity.cityName,
            timestamp = searchRecordEntity.timestamp,
        )
    }

}
