package com.stockviva.weather.infrastructure.preference.datasources.datastore

import kotlinx.coroutines.flow.Flow

interface IPreferenceDataStore {

    suspend fun updateLastSearch(lastSearch: String)

    fun get(): Flow<AppPreferenceProto>

}
