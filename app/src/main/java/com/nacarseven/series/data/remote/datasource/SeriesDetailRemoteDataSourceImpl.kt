package com.nacarseven.series.data.remote.datasource

import com.nacarseven.series.data.api.SeriesApi
import com.nacarseven.series.data.remote.model.SeriesDetailResponse
import com.nacarseven.series.domain.entities.SeriesDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SeriesDetailRemoteDataSourceImpl(
    private val api: SeriesApi
) : SeriesDetailRemoteDataSource {
    override suspend fun getSeriesDetail(id: Long): Response<SeriesDetailResponse> {
        return api.getSeriesById(id)
    }
}