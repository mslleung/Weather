package com.stockviva.weather.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.stockviva.weather.presentation.NavDestinations.Search
import com.stockviva.weather.presentation.NavDestinations.SearchHistory
import com.stockviva.weather.presentation.NavRoutes.SearchRoute
import com.stockviva.weather.presentation.search.SearchScreen
import com.stockviva.weather.presentation.search.SearchScreenViewModel
import com.stockviva.weather.presentation.searchhistory.SearchHistoryScreen
import com.stockviva.weather.presentation.searchhistory.SearchHistoryScreenViewModel
import com.stockviva.weather.presentation.ui.theme.WeatherTheme
import com.stockviva.weather.shared.Logger

@Suppress("unused")
private val log = Logger { }

private object NavDestinations {
    const val Search = "search"
    const val SearchHistory = "search_history"
}

private object NavRoutes {
    const val SearchRoute = "search_route"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    WeatherTheme {
        val navController = rememberAnimatedNavController()

        // The default is Spring.StiffnessMediumLow (400f). It is a bit too slow for my taste.
        val animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = 800f)
        AnimatedNavHost(
            navController = navController,
            startDestination = SearchRoute,
            enterTransition = {
                fadeIn(animationSpec) + scaleIn(
                    animationSpec,
                    initialScale = 0.9f
                )
            },
            exitTransition = {
                fadeOut(animationSpec) + scaleOut(
                    animationSpec,
                    targetScale = 1.1f
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec) + scaleIn(
                    animationSpec,
                    initialScale = 1.1f
                )
            },
            popExitTransition = {
                fadeOut(animationSpec) + scaleOut(
                    animationSpec,
                    targetScale = 0.9f
                )
            }
        ) {
            navigation(route = SearchRoute, startDestination = Search) {
                composable(Search) { backStackEntry ->
                    val navGraphEntry =
                        remember(backStackEntry) { navController.getBackStackEntry(SearchRoute) }
                    val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>(navGraphEntry)

                    SearchScreen(
                        viewModel = searchScreenViewModel,
                        navigateUp = { navController.navigateUp() },
                        navigateToRecentSearches = { navController.navigate(SearchHistory) }
                    )
                }
                composable(SearchHistory) { backStackEntry ->
                    val navGraphEntry =
                        remember(backStackEntry) { navController.getBackStackEntry(SearchRoute) }
                    val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>(navGraphEntry)
                    val searchHistoryScreenViewModel = hiltViewModel<SearchHistoryScreenViewModel>()

                    SearchHistoryScreen(
                        viewModel = searchHistoryScreenViewModel,
                        navigateUp = { navController.navigateUp() },
                        navigateToSearch = {
                            searchScreenViewModel.searchWeather(it.cityName)
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}
