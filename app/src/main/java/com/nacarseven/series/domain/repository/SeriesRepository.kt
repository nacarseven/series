package com.nacarseven.series.domain.repository

import androidx.paging.PagingData
import com.nacarseven.series.domain.entities.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun getSeries(): Flow<PagingData<Series>>
}