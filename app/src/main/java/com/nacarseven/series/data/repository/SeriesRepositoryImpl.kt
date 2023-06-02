package com.nacarseven.series.data.repository

import androidx.paging.PagingData
import com.nacarseven.series.data.mapper.transformToDomain
import com.nacarseven.series.data.networkmanager.NetworkManager
import com.nacarseven.series.data.networkmanager.NoConnectionException
import com.nacarseven.series.data.remote.datasource.SeriesDetailRemoteDataSource
import com.nacarseven.series.data.remote.datasource.SeriesPagingDataSource
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.domain.entities.SeriesDetail
import com.nacarseven.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SeriesRepositoryImpl(
    private val networkManager: NetworkManager,
    private val seriesPagingRemoteDataSource: SeriesPagingDataSource,
    private val seriesDetailRemoteDataSource: SeriesDetailRemoteDataSource
) : SeriesRepository {

    override fun getSeries(): Flow<PagingData<Series>> = seriesPagingRemoteDataSource.getSeries()

    override fun getSeriesDetail(id: Long): Flow<SeriesDetail> {
        return flow {
            try {
                if (networkManager.isConnected()) {
                    val result = seriesDetailRemoteDataSource.getSeriesDetail(id)
                    if (result.isSuccessful) {
                        emit(
                            result.body()?.transformToDomain()
                                ?: throw IllegalStateException("Body is nullable")
                        )
                    }
                } else {
                    throw NoConnectionException()
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}