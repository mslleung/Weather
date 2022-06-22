package com.stockviva.weather.infrastructure.di

import com.stockviva.weather.infrastructure.preference.datasources.datastore.IPreferenceDataStore
import com.stockviva.weather.infrastructure.preference.datasources.datastore.PreferenceDataStore
import com.stockviva.weather.infrastructure.search.datasources.local.ILocalSearchRecordDataSource
import com.stockviva.weather.infrastructure.search.datasources.local.LocalSearchRecordDataSource
import com.stockviva.weather.infrastructure.search.datasources.remote.IRemoteWeatherDataSource
import com.stockviva.weather.infrastructure.search.datasources.remote.RemoteWeatherDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteDataSource

    @RemoteDataSource
    @Binds
    abstract fun bindRemoteWeatherDataSource(
        remoteWeatherDataSource: RemoteWeatherDataSource
    ): IRemoteWeatherDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LocalDataSource

    @LocalDataSource
    @Binds
    abstract fun bindLocalSearchRecordDataSource(
        localSearchRecordDataSource: LocalSearchRecordDataSource
    ): ILocalSearchRecordDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DataStore

    @DataStore
    @Binds
    abstract fun bindPreferenceDataSource(
        preferenceDataSource: PreferenceDataStore
    ): IPreferenceDataStore

}
