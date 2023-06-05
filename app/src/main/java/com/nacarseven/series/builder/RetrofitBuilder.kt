package com.nacarseven.series.builder

import retrofit2.Retrofit

interface RetrofitBuilder {
    fun getClient(): Retrofit
}
