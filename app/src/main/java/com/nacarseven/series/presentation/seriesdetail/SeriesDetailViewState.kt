package com.nacarseven.series.presentation.seriesdetail

data class SeriesDetailViewState(
    val name: String? = "",
    val language: String? ="",
    val summary: String? = "",
    val ended: String? = "",
    val premiered: String? = "",
    val image: String? = "",
    val isLoading : Boolean = false,
    val isErrorState : Boolean = false
)