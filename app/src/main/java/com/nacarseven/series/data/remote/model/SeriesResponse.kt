package com.nacarseven.series.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesResponse(
    @SerialName(value = "id") val id: Long? = null,
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "image") val image: ImageResponse? = null,
)