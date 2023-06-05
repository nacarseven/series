package com.nacarseven.series.data.remote.datasource

import com.nacarseven.series.data.remote.model.SeriesDetailResponse
import com.nacarseven.series.domain.entities.SeriesDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SeriesDetailRemoteDataSource {
    suspend fun getSeriesDetail(id: Long): Response<SeriesDetailResponse>
}