package com.stockviva.weather.presentation.searchhistory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.presentation.R
import com.stockviva.weather.shared.Logger
import java.util.*

@Suppress("unused")
private val log = Logger { }

@Composable
fun SearchHistoryScreen(
    viewModel: SearchHistoryScreenViewModel,
    navigateUp: () -> Unit,
    navigateToSearch: (SearchRecord) -> Unit
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

    val searchRecords = viewModel.storesPagedFlow.collectAsLazyPagingItems()
    val state by rememberSearchHistoryScreenState()
    MainLayout(
        searchRecords = searchRecords,
        state = state,
        onBackClick = navigateUp,
        onSearchRecordClick = {
            navigateToSearch(it)
        },
        onSearchRecordDeleteClick = {
            viewModel.deleteSearchRecord(it)
        },
    )

    BackHandler(enabled = false) {
        navigateUp()
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MainLayout(
    searchRecords: LazyPagingItems<SearchRecord>,
    state: SearchHistoryScreenStateHolder,
    onBackClick: () -> Unit,
    onSearchRecordClick: (SearchRecord) -> Unit,
    onSearchRecordDeleteClick: (SearchRecord) -> Unit,
) {
    val topAppBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarScrollState)
    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(14.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(R.string.search_history_back_button_content_desc),
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.search_history_title)
                    )
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.statusBarsPadding()
            )
        },
    ) { scaffoldPadding ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier
                .padding(scaffoldPadding)
                .navigationBarsPadding()
                .fillMaxWidth()
                .fillMaxHeight()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(
                items = searchRecords,
                key = { it.id }
            ) { searchRecord ->
                if (searchRecord != null) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable { onSearchRecordClick(searchRecord) }
                            .padding(horizontal = 8.dp)
                            .animateItemPlacement()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = searchRecord.cityName,
                                style = MaterialTheme.typography.labelLarge
                            )

                            val date = Date(searchRecord.timestamp)
                            Text(
                                text = stringResource(id = R.string.search_history_last_search) + " " + date.toString(),
                                style = MaterialTheme.typography.labelLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.alpha(0.6f)
                            )
                        }

                        IconButton(
                            onClick = { onSearchRecordDeleteClick(searchRecord) },
                            modifier = Modifier
                                .padding(14.dp)
                                .size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_delete_forever_24),
                                contentDescription = stringResource(R.string.search_history_delete_button_content_desc),
                            )
                        }
                    }
                }
            }
        }
    }
}
