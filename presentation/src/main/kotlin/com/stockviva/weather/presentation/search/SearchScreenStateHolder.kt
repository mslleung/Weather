package com.stockviva.weather.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class SearchScreenStateHolder {

    var shouldLoadLastSearch by mutableStateOf(true)

    var isRequestingFirstFocus by mutableStateOf(true)
    var searchQuery by mutableStateOf("")

    companion object {
        val Saver: Saver<SearchScreenStateHolder, *> = listSaver(
            save = {
                listOf(
                    it.shouldLoadLastSearch,
                    it.isRequestingFirstFocus,
                    it.searchQuery
                )
            },
            restore = {
                SearchScreenStateHolder().apply {
                    shouldLoadLastSearch = it[0] as Boolean
                    isRequestingFirstFocus = it[1] as Boolean
                    searchQuery = it[2] as String
                }
            }
        )
    }
}

@Composable
fun rememberSearchScreenState() = rememberSaveable(
    stateSaver = SearchScreenStateHolder.Saver
) {
    mutableStateOf(SearchScreenStateHolder())
}
