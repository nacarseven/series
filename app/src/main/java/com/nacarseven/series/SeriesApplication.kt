package com.nacarseven.series

import android.app.Application
import com.nacarseven.series.data.di.dataModule
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@ExperimentalSerializationApi
class SeriesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SeriesApplication)
            modules(dataModule)
        }
    }
}