package com.nacarseven.series.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nacarseven.series.data.api.SeriesApi
import com.nacarseven.series.data.networkmanager.NetworkManager
import com.nacarseven.series.data.networkmanager.NoConnectionException
import com.nacarseven.series.data.remote.model.SeriesResponse
import retrofit2.HttpException
import java.io.IOException

private const val DEFAULT_PAGE_INDEX = 1

class SeriesPagingSource(
    private val api: SeriesApi,
    private val networkManager: NetworkManager
) : PagingSource<Int, SeriesResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResponse> {
        return if (networkManager.isConnected()) {
            return try {
                val result = api.getSeries(params.key ?: DEFAULT_PAGE_INDEX).body().orEmpty()
                LoadResult.Page(
                    data = result,
                    prevKey = params.key,
                    nextKey = params.key?.plus(1) ?: DEFAULT_PAGE_INDEX.plus(1)
                )
            } catch (e: IOException) {
                LoadResult.Error(e)
            } catch (e: HttpException) {
                LoadResult.Error(e)
            }
        } else {
            LoadResult.Error(NoConnectionException())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SeriesResponse>): Int? {
        return state.anchorPosition?.let { mostRecentlyAccessedIndex ->
            val page = state.closestPageToPosition(mostRecentlyAccessedIndex)
            val refreshKey = page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
            refreshKey
        }
    }
}