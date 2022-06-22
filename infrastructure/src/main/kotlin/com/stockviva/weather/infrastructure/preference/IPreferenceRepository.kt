package com.stockviva.weather.infrastructure.preference

import com.stockviva.weather.domain.preference.AppPreference
import kotlinx.coroutines.flow.Flow

/**
 * A repository for application preferences/configs.
 */
interface IPreferenceRepository {

    suspend fun updateLastSearch(lastSearch: String)

    fun getAppPreference(): Flow<AppPreference>

}
