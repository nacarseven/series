package com.nacarseven.series.presentation.serieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.nacarseven.series.domain.usecase.GetSeriesUseCase
import com.nacarseven.series.presentation.model.SeriesUi
import com.nacarseven.series.presentation.mapper.transformUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SeriesListViewModel(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    fun getSeries(): Flow<PagingData<SeriesUi>> =
        getSeriesUseCase()
            .map { pagingData ->
                pagingData.map { series ->
                    series.transformUI()
                }
            }.cachedIn(viewModelScope)
}
