package com.stockviva.weather.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockviva.weather.application.preference.PreferenceService
import com.stockviva.weather.application.search.SearchWeatherService
import com.stockviva.weather.domain.search.Weather
import com.stockviva.weather.shared.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused")
private val log = Logger { }

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val preferenceService: PreferenceService,
    private val searchWeatherService: SearchWeatherService,
) : ViewModel() {

    sealed class SearchLoadState {
        object Idle : SearchLoadState()
        object Loading : SearchLoadState()
        object Failure : SearchLoadState()
        class Success(val weather: Weather) : SearchLoadState()
    }

    val lastSearchFlow = preferenceService.getAppPreference()
        .map { it.lastSearch }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    var searchLoadState by mutableStateOf<SearchLoadState>(SearchLoadState.Idle)

    fun searchWeather(cityName: String) {
        viewModelScope.launch {
            runCatching {
                searchLoadState = SearchLoadState.Loading
                val weather = searchWeatherService.searchWeatherForCityName(cityName)
                searchLoadState = SearchLoadState.Success(weather)
            }.onFailure {
                log.error("Search weather failed. Message: ${it.message}")
                searchLoadState = SearchLoadState.Failure
            }
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            preferenceService.updateLastSearch("")
            searchLoadState = SearchLoadState.Idle
        }
    }

}
