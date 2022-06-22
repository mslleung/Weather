package com.stockviva.weather.infrastructure.search

import androidx.paging.PagingSource
import com.stockviva.weather.domain.search.SearchRecord

interface ISearchRecordRepository {

    suspend fun insert(searchRecord: SearchRecord): Long

    suspend fun delete(searchRecords: List<SearchRecord>)

    fun getSearchRecordsPagingSource(): PagingSource<Int, SearchRecord>
}
