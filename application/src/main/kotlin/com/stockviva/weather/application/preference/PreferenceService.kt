package com.stockviva.weather.application.preference

import com.stockviva.weather.infrastructure.preference.IPreferenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceService @Inject constructor(
    private val preferenceRepository: IPreferenceRepository
) {

    suspend fun updateLastSearch(lastSearch: String) {
        preferenceRepository.updateLastSearch(lastSearch)
    }

    fun getAppPreference() = preferenceRepository.getAppPreference()

}
