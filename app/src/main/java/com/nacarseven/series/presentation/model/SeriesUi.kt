package com.nacarseven.series.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesUi(
    val id: Long,
    val name: String,
    val image: String
) : Parcelable
