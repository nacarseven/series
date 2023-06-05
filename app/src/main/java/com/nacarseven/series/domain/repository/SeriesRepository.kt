package com.nacarseven.series.domain.repository

import androidx.paging.PagingData
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.domain.entities.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun getSeries(): Flow<PagingData<Series>>
    fun getSeriesDetail(id: Long): Flow<SeriesDetail>
}