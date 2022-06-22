package com.stockviva.weather.infrastructure.preference.datasources.datastore.mapper

import com.stockviva.weather.domain.preference.AppPreference
import com.stockviva.weather.infrastructure.preference.datasources.datastore.AppPreferenceProto
import io.kotest.matchers.shouldBe
import org.junit.Assert.*

import org.junit.Test

class AppPreferenceMapperTest {

    @Test
    fun mapFromData() {
        val appPreferenceMapper = AppPreferenceMapper()
        val appPreferenceProto = AppPreferenceProto.newBuilder().apply {
            lastSearch = "Hong Kong"
        }.build()

        val appPreference = appPreferenceMapper.mapFromData(appPreferenceProto)

        val expectedAppPreference = AppPreference(
            lastSearch = "Hong Kong"
        )
        appPreference shouldBe expectedAppPreference
    }

}
