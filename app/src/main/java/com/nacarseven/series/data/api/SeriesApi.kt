package com.nacarseven.series.data.api

import com.nacarseven.series.data.remote.model.SeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApi {

    @GET("shows")
    suspend fun getSeries(@Query("page") page: Int): Response<List<SeriesResponse>>
}