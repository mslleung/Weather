package com.stockviva.weather.presentation.searchhistory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

class SearchHistoryScreenStateHolder {

    // empty state holder, keep this class as we probably need it in the future

    companion object {
        val Saver: Saver<SearchHistoryScreenStateHolder, *> = listSaver(
            save = {
                listOf(
                    false
                )
            },
            restore = {
                SearchHistoryScreenStateHolder().apply {
                }
            }
        )
    }
}

@Composable
fun rememberSearchHistoryScreenState() = rememberSaveable(
    stateSaver = SearchHistoryScreenStateHolder.Saver
) {
    mutableStateOf(SearchHistoryScreenStateHolder())
}
