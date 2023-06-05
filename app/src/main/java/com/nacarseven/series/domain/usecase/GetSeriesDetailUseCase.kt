package com.nacarseven.series.domain.usecase

import com.nacarseven.series.domain.entities.SeriesDetail
import com.nacarseven.series.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class GetSeriesDetailUseCase(private val repository: SeriesRepository) {
    operator fun invoke(id: Long): Flow<SeriesDetail> {
        return repository.getSeriesDetail(id)
    }
}