package com.stockviva.weather.infrastructure.search.datasources.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stockviva.weather.infrastructure.AppDatabase
import com.stockviva.weather.infrastructure.search.datasources.local.daos.SearchRecordDao
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalSearchRecordDataSourceTest {
    private lateinit var searchRecordDao: SearchRecordDao
    private lateinit var db: AppDatabase

    private val searchRecordEntityFixture = SearchRecordEntity(
        id = 0,
        cityName = "Hong Kong",
        timestamp = 10
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        searchRecordDao = db.searchRecordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertSearchRecord() = runBlocking {
        val id = searchRecordDao.insert(searchRecordEntityFixture)

        val expectedSearchRecordEntity = searchRecordEntityFixture.copy(id = id)
        searchRecordDao.getSearchRecordsPage(0, 1)[0] shouldBe expectedSearchRecordEntity
    }

    @Test
    @Throws(Exception::class)
    fun deleteSearchRecord() = runBlocking {
        val id = searchRecordDao.insert(searchRecordEntityFixture)

        searchRecordDao.delete(listOf(searchRecordEntityFixture.copy(id = id)))

        searchRecordDao.getSearchRecordsPage(0, 1).size shouldBe 0
    }

    @Test
    @Throws(Exception::class)
    fun getSearchRecordsPage() = runBlocking {
        searchRecordDao.getSearchRecordsPage(0, 0).size shouldBe 0
    }

}
