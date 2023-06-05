@file:OptIn(ExperimentalSerializationApi::class)

package com.nacarseven.series.data.di

import androidx.paging.PagingSource
import com.nacarseven.series.builder.RetrofitBuilderImpl
import com.nacarseven.series.data.api.SeriesApi
import com.nacarseven.series.data.networkmanager.NetworkManager
import com.nacarseven.series.data.networkmanager.NetworkManagerImpl
import com.nacarseven.series.data.remote.datasource.SeriesDetailRemoteDataSource
import com.nacarseven.series.data.remote.datasource.SeriesDetailRemoteDataSourceImpl
import com.nacarseven.series.data.remote.datasource.SeriesPagingDataSource
import com.nacarseven.series.data.remote.datasource.SeriesPagingDataSourceImpl
import com.nacarseven.series.data.remote.model.SeriesResponse
import com.nacarseven.series.data.remote.paging.SeriesPagingConfig
import com.nacarseven.series.data.remote.paging.SeriesPagingSource
import com.nacarseven.series.data.repository.SeriesRepositoryImpl
import com.nacarseven.series.domain.repository.SeriesRepository
import com.nacarseven.series.domain.usecase.GetSeriesDetailUseCase
import com.nacarseven.series.domain.usecase.GetSeriesUseCase
import com.nacarseven.series.presentation.seriesdetail.SeriesDetailViewModel
import com.nacarseven.series.presentation.serieslist.SeriesListViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    foundation()
    data()
    presentation()
}

private fun Module.foundation() {
    factory<NetworkManager> { NetworkManagerImpl(androidApplication()) }
    single {
        val timeout = 15L
        RetrofitBuilderImpl(isDebug = BuildConfig.DEBUG, timeout = timeout).getClient()
    }
    factory<SeriesApi> { get<Retrofit>().create(SeriesApi::class.java) }
}

private fun Module.data() {
    factory<PagingSource<Int, SeriesResponse>> {
        SeriesPagingSource(
            api = get(),
            networkManager = get()
        )
    }
    factory { SeriesPagingConfig().invoke() }
    factory<SeriesPagingDataSource> {
        SeriesPagingDataSourceImpl(
            pagingConfig = get(),
            pagingSourceFactory = { get() }
        )
    }
    factory<SeriesDetailRemoteDataSource> {
        SeriesDetailRemoteDataSourceImpl(api = get())
    }
    factory<SeriesRepository> {
        SeriesRepositoryImpl(
            networkManager = get(),
            seriesPagingRemoteDataSource = get(),
            seriesDetailRemoteDataSource = get()
        )
    }
}

private fun Module.presentation() {
    factory { GetSeriesDetailUseCase(repository = get()) }
    factory { GetSeriesUseCase(repository = get()) }
    viewModel { SeriesListViewModel(getSeriesUseCase = get()) }
    viewModel{(id: Long) ->
        SeriesDetailViewModel(
            seriesUiId = id,
            getSeriesDetailUseCase = get()
        )
    }
}

