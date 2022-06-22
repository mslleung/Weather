package com.stockviva.weather.infrastructure.preference

import com.stockviva.weather.infrastructure.preference.datasources.datastore.AppPreferenceProto
import com.stockviva.weather.infrastructure.preference.datasources.datastore.IPreferenceDataStore
import com.stockviva.weather.infrastructure.preference.datasources.datastore.mapper.AppPreferenceMapper
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class PreferenceRepositoryTest {

    class FakePreferenceDataSource : IPreferenceDataStore {
        var lastSearch = "lastSearch"

        override suspend fun updateLastSearch(lastSearch: String) {
            this.lastSearch = lastSearch
        }

        override fun get(): Flow<AppPreferenceProto> {
            return flowOf(
                AppPreferenceProto.newBuilder()
                    .setLastSearch(lastSearch)
                    .build()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateLastSearch() = runTest {
        val fakePreferenceDataSource = FakePreferenceDataSource()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val preferenceRepository = PreferenceRepository(
            fakePreferenceDataSource,
            AppPreferenceMapper(),
            this,
            dispatcher
        )

        preferenceRepository.updateLastSearch("updated")

        fakePreferenceDataSource.lastSearch shouldBe "updated"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAppPreference() = runTest {
        val fakePreferenceDataSource = FakePreferenceDataSource()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val preferenceRepository = PreferenceRepository(
            FakePreferenceDataSource(),
            AppPreferenceMapper(),
            this,
            dispatcher
        )

        val appPreference = preferenceRepository.getAppPreference().first()

        appPreference.lastSearch shouldBe fakePreferenceDataSource.lastSearch
    }

}
