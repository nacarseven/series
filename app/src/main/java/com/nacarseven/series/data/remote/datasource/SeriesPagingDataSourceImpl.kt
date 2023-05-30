package com.nacarseven.series.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.nacarseven.series.data.mapper.SeriesMapper
import com.nacarseven.series.data.remote.model.SeriesResponse
import com.nacarseven.series.domain.entities.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SeriesPagingDataSourceImpl(
    pagingConfig: PagingConfig,
    private val seriesMapper: SeriesMapper,
    pagingSourceFactory: () -> PagingSource<Int, SeriesResponse>
) : SeriesPagingDataSource {

    private val factorySeriesPager = Pager(
        config = pagingConfig,
        pagingSourceFactory = pagingSourceFactory
    )

    override fun getSeries(): Flow<PagingData<Series>> {
        return factorySeriesPager.flow.map { pagingSeriesData ->
            pagingSeriesData.map {
                seriesMapper.transform(it)
            }
        }
    }
}


