package com.stockviva.weather.infrastructure.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stockviva.weather.domain.search.SearchRecord
import com.stockviva.weather.infrastructure.Transaction
import com.stockviva.weather.infrastructure.di.DataSourceModule
import com.stockviva.weather.infrastructure.di.IoDispatcher
import com.stockviva.weather.infrastructure.search.datasources.local.ILocalSearchRecordDataSource
import com.stockviva.weather.infrastructure.search.datasources.local.models.mapper.SearchRecordMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRecordRepository @Inject internal constructor(
    @DataSourceModule.LocalDataSource private val localSearchRecordDataSource: ILocalSearchRecordDataSource,
    private val searchRecordMapper: SearchRecordMapper,
    private val transaction: Transaction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ISearchRecordRepository {

    override suspend fun insert(searchRecord: SearchRecord): Long {
        return transaction.execute {
            localSearchRecordDataSource.insertSearchRecord(searchRecordMapper.mapToData(searchRecord))
        }
    }

    override suspend fun delete(searchRecords: List<SearchRecord>) {
        transaction.execute {
            localSearchRecordDataSource.deleteSearchRecord(searchRecords.map {
                searchRecordMapper.mapToData(
                    it
                )
            })
        }
    }

    override fun getSearchRecordsPagingSource(): PagingSource<Int, SearchRecord> {
        return SearchRecordsPagingSource(
            localSearchRecordDataSource,
            searchRecordMapper,
            ioDispatcher
        )
    }

    private class SearchRecordsPagingSource(
        private val localSearchRecordDataSource: ILocalSearchRecordDataSource,
        private val searchRecordMapper: SearchRecordMapper,
        private val ioDispatcher: CoroutineDispatcher,
    ) : PagingSource<Int, SearchRecord>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchRecord> {
            return withContext(ioDispatcher) {
                try {
                    val pageNumber = params.key ?: 1
                    val offset = (pageNumber - 1) * params.loadSize // all the previous pages

                    val pageData =
                        localSearchRecordDataSource.getSearchRecordsPage(offset, params.loadSize)
                    LoadResult.Page(
                        data = pageData.map { searchRecordMapper.mapFromData(it) },
                        prevKey = if (pageNumber <= 1) null else pageNumber - 1,
                        nextKey = if (pageData.isEmpty()) null else pageNumber + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }

        override fun getRefreshKey(state: PagingState<Int, SearchRecord>): Int? {
            // Try to find the page key of the closest page to anchorPosition, from
            // either the prevKey or the nextKey, but you need to handle nullability
            // here:
            //  * prevKey == null -> anchorPage is the first page.
            //  * nextKey == null -> anchorPage is the last page.
            //  * both prevKey and nextKey null -> anchorPage is the initial page, so
            //    just return null.
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }
    }

}
