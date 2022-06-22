package com.stockviva.weather.infrastructure.preference

import com.stockviva.weather.domain.preference.AppPreference
import com.stockviva.weather.infrastructure.di.DataSourceModule
import com.stockviva.weather.infrastructure.di.DefaultDispatcher
import com.stockviva.weather.infrastructure.preference.datasources.datastore.IPreferenceDataStore
import com.stockviva.weather.infrastructure.preference.datasources.datastore.mapper.AppPreferenceMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepository @Inject internal constructor(
    @DataSourceModule.DataStore private val preferenceDataSource: IPreferenceDataStore,
    private val appPreferenceMapper: AppPreferenceMapper,
    private val externalScope: CoroutineScope,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : IPreferenceRepository {

    override suspend fun updateLastSearch(lastSearch: String) {
        externalScope.launch(defaultDispatcher) {
            preferenceDataSource.updateLastSearch(lastSearch)
        }.join()
    }

    override fun getAppPreference(): Flow<AppPreference> {
        return preferenceDataSource.get().map { appPreferenceMapper.mapFromData(it) }
    }

}
