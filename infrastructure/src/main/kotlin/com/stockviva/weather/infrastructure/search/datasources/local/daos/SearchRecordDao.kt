package com.stockviva.weather.infrastructure.search.datasources.local.daos

import androidx.room.*
import com.stockviva.weather.infrastructure.search.datasources.local.models.SearchRecordEntity

@Dao
internal interface SearchRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchRecordEntity: SearchRecordEntity): Long

    @Delete
    suspend fun delete(searchRecordEntities: List<SearchRecordEntity>): Int

    @Query("SELECT * FROM search_record ORDER BY id DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getSearchRecordsPage(offset: Int, pageSize: Int): List<SearchRecordEntity>
}
