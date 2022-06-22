package com.stockviva.weather.infrastructure.preference.datasources.datastore.mapper

import com.stockviva.weather.domain.preference.AppPreference
import com.stockviva.weather.infrastructure.preference.datasources.datastore.AppPreferenceProto
import javax.inject.Inject

internal class AppPreferenceMapper @Inject constructor() {

    fun mapFromData(appPreferenceProto: AppPreferenceProto): AppPreference {
        return AppPreference(
            lastSearch = appPreferenceProto.lastSearch
        )
    }

}
