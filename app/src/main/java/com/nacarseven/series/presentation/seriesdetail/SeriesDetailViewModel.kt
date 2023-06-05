package com.nacarseven.series.presentation.seriesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacarseven.series.domain.entities.SeriesDetail
import com.nacarseven.series.domain.usecase.GetSeriesDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SeriesDetailViewModel(
    private val seriesUiId: Long,
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase
) : ViewModel() {

    private val _seriesDetailViewState by lazy { MutableLiveData<SeriesDetailViewState>() }
    val seriesDetailViewState: LiveData<SeriesDetailViewState> = _seriesDetailViewState

    init {
        getSeriesDetail()
        _seriesDetailViewState.value = SeriesDetailViewState()
    }


    private fun getSeriesDetail() {
        viewModelScope.launch {
            setSeriesDetailViewState { it.copy(isLoading = true) }
            getSeriesDetailUseCase(seriesUiId)
                .flowOn(Dispatchers.Main)
                .catch { handleErrorState() }
                .collect { seriesDetail -> handleSuccessState(seriesDetail) }
        }
    }

    private fun handleErrorState() {
        setSeriesDetailViewState { it.copy(isLoading = false, isErrorState = true) }
    }

    private fun handleSuccessState(seriesDetail: SeriesDetail) {
        setSeriesDetailViewState {
            it.copy(
                name = seriesDetail.name,
                language = seriesDetail.language,
                summary = seriesDetail.summary,
                ended = seriesDetail.ended,
                premiered = seriesDetail.premiered,
                image = seriesDetail.image,
                isLoading = false,
                isErrorState = false
            )
        }
    }

    private fun setSeriesDetailViewState(state: (SeriesDetailViewState) -> SeriesDetailViewState) {
        _seriesDetailViewState.value?.also {
            _seriesDetailViewState.value = state(it)
        }
    }
}
