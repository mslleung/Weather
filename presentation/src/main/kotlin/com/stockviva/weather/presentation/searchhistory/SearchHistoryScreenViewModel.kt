package com.stockviva.weather.presentation.searchhistory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.stockviva.weather.application.search.SearchWeatherService
import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.shared.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused")
private val log = Logger { }

@HiltViewModel
class SearchHistoryScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchWeatherService: SearchWeatherService,
) : ViewModel() {

    private lateinit var searchRecordsPagingSource: PagingSource<Int, SearchRecord>
    val storesPagedFlow = Pager(
        PagingConfig(
            pageSize = 100,
            prefetchDistance = 30
        )
    ) {
        searchRecordsPagingSource = searchWeatherService.getSearchRecordsPagingSource()
        searchRecordsPagingSource
    }.flow
        .cachedIn(viewModelScope)

    fun deleteSearchRecord(searchRecord: SearchRecord) {
        viewModelScope.launch {
            searchWeatherService.deleteSearchRecord(searchRecord)
            searchRecordsPagingSource.invalidate()
        }
    }

}
