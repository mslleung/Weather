package com.stockviva.weather.presentation.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.stockviva.weather.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchLayout(
    state: SearchScreenStateHolder,
    snackbarHostState: SnackbarHostState,
    onSearchClick: () -> Unit,
    searchLoadState: SearchScreenViewModel.SearchLoadState,
    onSearchFailedSnackbarDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.search_title),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            style = MaterialTheme.typography.displaySmall,
        )

        Surface(
            shape = Shapes.Full,
            tonalElevation = 8.dp,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 15.dp)
                .height(60.dp)
                .fillMaxWidth()
        ) {
            val focusRequester = remember { FocusRequester() }
            TextField(
                value = state.searchQuery,
                onValueChange = {
                    state.searchQuery = it
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = stringResource(id = R.string.search_bar_icon_content_desc),
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            state.searchQuery = ""
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_close_24),
                            contentDescription = stringResource(id = R.string.search_bar_icon_content_desc),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_bar_hint_text)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            if (state.isRequestingFirstFocus) {
                state.isRequestingFirstFocus = false
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }
            }
        }

        Button(
            onClick = onSearchClick,
            enabled = searchLoadState is SearchScreenViewModel.SearchLoadState.Idle
                    || searchLoadState is SearchScreenViewModel.SearchLoadState.Failure,
            modifier = Modifier.animateContentSize()
        ) {
            when (searchLoadState) {
                is SearchScreenViewModel.SearchLoadState.Idle -> {
                    Text(
                        text = stringResource(id = R.string.search_button_text)
                    )
                }
                is SearchScreenViewModel.SearchLoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp)
                    )
                }
                is SearchScreenViewModel.SearchLoadState.Failure -> {
                    Text(
                        text = stringResource(id = R.string.search_button_text)
                    )

                    val failureMessage =
                        stringResource(id = R.string.search_failed_snackbar_message)
                    LaunchedEffect(Unit) {
                        val results = snackbarHostState.showSnackbar(
                            message = failureMessage,
                            withDismissAction = true
                        )

                        when (results) {
                            SnackbarResult.Dismissed -> {
                                onSearchFailedSnackbarDismiss()
                            }
                            else -> {}
                        }
                    }
                }
                is SearchScreenViewModel.SearchLoadState.Success -> {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    LaunchedEffect(key1 = Unit) {
                        keyboardController?.hide()
                    }
                }
            }
        }
    }
}

