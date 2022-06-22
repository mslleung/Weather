package com.stockviva.weather.presentation.search

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stockviva.weather.presentation.R
import com.stockviva.weather.shared.Logger

@Suppress("unused")
private val log = Logger { }

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel,
    navigateUp: () -> Unit,
    navigateToRecentSearches: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.surface
    val navBarColor = MaterialTheme.colorScheme.surface
    SideEffect {
        systemUiController.setStatusBarColor(
            statusBarColor,
            transformColorForLightContent = { color -> color })
        systemUiController.setNavigationBarColor(
            navBarColor,
            navigationBarContrastEnforced = false,
            transformColorForLightContent = { color -> color })
    }

    val context = LocalContext.current
    val state by rememberSearchScreenState()
    MainLayout(
        searchLoadState = viewModel.searchLoadState,
        state = state,
        onSearchHistoryClick = navigateToRecentSearches,
        onSearchClick = {
            if (state.searchQuery.isNotBlank()) {
                viewModel.searchWeather(state.searchQuery)
            } else {
                Toast.makeText(context, R.string.search_query_empty, Toast.LENGTH_SHORT).show()
            }
        },
        onSearchFailedSnackbarDismiss = {
            viewModel.searchLoadState = SearchScreenViewModel.SearchLoadState.Idle
        },
        onClearSearchClick = {
            viewModel.clearSearch()
        }
    )

    val lastSearch by viewModel.lastSearchFlow.collectAsState()
    if (state.shouldLoadLastSearch && lastSearch.isNotBlank()) {
        state.shouldLoadLastSearch = false
        LaunchedEffect(key1 = Unit) {
            state.searchQuery = lastSearch
            viewModel.searchWeather(state.searchQuery)
        }
    }

    BackHandler(enabled = false) {
        navigateUp()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainLayout(
    searchLoadState: SearchScreenViewModel.SearchLoadState,
    state: SearchScreenStateHolder,
    onSearchHistoryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSearchFailedSnackbarDismiss: () -> Unit,
    onClearSearchClick: () -> Unit,
) {
    val topAppBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarScrollState)
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchHistoryClick,
                        modifier = Modifier
                            .padding(14.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_history_24),
                            contentDescription = stringResource(R.string.search_history_icon_content_desc)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.statusBarsPadding()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
            )
        }
    ) { scaffoldPadding ->
        Crossfade(targetState = searchLoadState) {
            when (it) {
                is SearchScreenViewModel.SearchLoadState.Idle,
                is SearchScreenViewModel.SearchLoadState.Loading,
                is SearchScreenViewModel.SearchLoadState.Failure -> {
                    val scrollState = rememberScrollState()
                    SearchLayout(
                        state = state,
                        snackbarHostState = snackbarHostState,
                        onSearchClick = onSearchClick,
                        searchLoadState = it,
                        onSearchFailedSnackbarDismiss = onSearchFailedSnackbarDismiss,
                        modifier = Modifier
                            .padding(scaffoldPadding)
                            .imePadding()
                            .navigationBarsPadding()
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(scrollState)
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    )
                }
                is SearchScreenViewModel.SearchLoadState.Success -> {
                    val weather = it.weather
                    val scrollState = rememberScrollState()
                    SearchResultLayout(
                        weather = weather,
                        onClearSearchClick = onClearSearchClick,
                        modifier = Modifier
                            .padding(scaffoldPadding)
                            .imePadding()
                            .navigationBarsPadding()
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(scrollState)
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    )
                }
            }
        }
    }
}
