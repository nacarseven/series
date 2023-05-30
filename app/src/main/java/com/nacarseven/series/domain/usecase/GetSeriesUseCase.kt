package com.nacarseven.series.domain.usecase

import androidx.paging.PagingData
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase(private val repository: SeriesRepository) {
    operator fun invoke() : Flow<PagingData<Series>>{
        return repository.getSeries()
    }
}