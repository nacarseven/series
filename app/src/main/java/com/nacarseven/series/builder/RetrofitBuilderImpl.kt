package com.nacarseven.series.builder

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.tvmaze.com/"
private const val CONTENT_TYPE = "application/json"

@ExperimentalSerializationApi
internal class RetrofitBuilderImpl(
    private val isDebug: Boolean,
    private val timeout: Long
) : RetrofitBuilder {

    private val type: MediaType = CONTENT_TYPE.toMediaType()

    override fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client())
        .addConverterFactory(json().asConverterFactory(type))
        .build()

    private fun client(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLoggingInterceptor())
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .build()

    private fun json() = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private fun getLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply { level = getInterceptorLevel() }

    private fun getInterceptorLevel() = if (isDebug) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}
