package com.nacarseven.series.data.remote.datasource

import androidx.paging.PagingData
import com.nacarseven.series.domain.entities.Series
import kotlinx.coroutines.flow.Flow

interface SeriesPagingDataSource {
    fun getSeries(): Flow<PagingData<Series>>
}