package com.stockviva.weather.infrastructure.preference.datasources.datastore

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) : IPreferenceDataStore {

    override suspend fun updateLastSearch(lastSearch: String) {
        context.preferenceDataStore.updateData {
            it.toBuilder()
                .setLastSearch(lastSearch)
                .build()
        }
    }

    override fun get(): Flow<AppPreferenceProto> {
        return context.preferenceDataStore.data.distinctUntilChanged()
    }

}
