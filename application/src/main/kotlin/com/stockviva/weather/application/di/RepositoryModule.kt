package com.stockviva.weather.application.di

import com.stockviva.weather.infrastructure.preference.IPreferenceRepository
import com.stockviva.weather.infrastructure.preference.PreferenceRepository
import com.stockviva.weather.infrastructure.search.ISearchRecordRepository
import com.stockviva.weather.infrastructure.search.IWeatherRepository
import com.stockviva.weather.infrastructure.search.SearchRecordRepository
import com.stockviva.weather.infrastructure.search.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSearchRecordRepository(
        searchRecordRepository: SearchRecordRepository,
    ): ISearchRecordRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(
        weatherRepository: WeatherRepository
    ): IWeatherRepository

    @Singleton
    @Binds
    abstract fun bindPreferenceRepository(
        preferenceRepository: PreferenceRepository
    ): IPreferenceRepository

}
