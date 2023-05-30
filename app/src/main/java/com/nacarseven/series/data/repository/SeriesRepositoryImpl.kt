package com.nacarseven.series.data.repository

import androidx.paging.PagingData
import com.nacarseven.series.data.remote.datasource.SeriesPagingDataSource
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class SeriesRepositoryImpl(
    private val seriesPagingRemoteDataSource: SeriesPagingDataSource
) : SeriesRepository {

    override fun getSeries(): Flow<PagingData<Series>> = seriesPagingRemoteDataSource.getSeries()

}