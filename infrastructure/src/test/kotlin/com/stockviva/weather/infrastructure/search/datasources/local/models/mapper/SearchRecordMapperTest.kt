package com.stockviva.weather.infrastructure.search.datasources.local.models.mapper

import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity
import io.kotest.matchers.shouldBe
import org.junit.Test

class SearchRecordMapperTest {

    private val searchRecordFixture = SearchRecord(
        id = 1,
        cityName = "Hong Kong",
        timestamp = 10,
    )

    private val searchRecordEntityFixture = SearchRecordEntity(
        id = 1,
        cityName = "Hong Kong",
        timestamp = 10,
    )

    @Test
    fun mapToData() {
        val searchRecordMapper = SearchRecordMapper()

        val searchRecordEntity = searchRecordMapper.mapToData(searchRecordFixture)

        searchRecordEntity shouldBe searchRecordEntityFixture
    }

    @Test
    fun mapFromData() {
        val searchRecordMapper = SearchRecordMapper()

        val searchRecord = searchRecordMapper.mapFromData(searchRecordEntityFixture)

        searchRecord shouldBe searchRecordFixture
    }

}