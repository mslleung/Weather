package com.stockviva.weather.application.search

import androidx.paging.PagingSource
import com.stockviva.weather.application.preference.PreferenceService
import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.domain.search.Weather
import com.stockviva.weather.infrastructure.Transaction
import com.stockviva.weather.infrastructure.search.IWeatherRepository
import com.stockviva.weather.infrastructure.search.SearchRecordRepository
import com.stockviva.weather.shared.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
private val log = Logger {}

@Singleton
class SearchWeatherService @Inject constructor(
    private val searchRecordRepository: SearchRecordRepository,
    private val weatherRepository: IWeatherRepository,
    private val preferenceService: PreferenceService,
    private val transaction: Transaction
) {

    suspend fun searchWeatherForCityName(cityName: String): Weather {
        return transaction.execute {
            searchRecordRepository.insert(
                SearchRecord(
                    cityName = cityName,
                    timestamp = System.currentTimeMillis()
                )
            )
            val weather = weatherRepository.getCurrentWeatherByCityName(cityName)
            preferenceService.updateLastSearch(cityName)
            weather
        }
    }

    suspend fun deleteSearchRecord(searchRecord: SearchRecord) {
        searchRecordRepository.delete(listOf(searchRecord))
    }

    fun getSearchRecordsPagingSource(): PagingSource<Int, SearchRecord> {
        return searchRecordRepository.getSearchRecordsPagingSource()
    }

}
