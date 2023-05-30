package com.nacarseven.series.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesResponse(
    @SerialName(value = "id") val id: Long? = null,
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "language") val language: String? = null,
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "genres") val genres: List<String>? = null,
    @SerialName(value = "ended") val ended: String? = null,
    @SerialName(value = "premiered") val premiered: String? = null,
    @SerialName(value = "image") val image: ImageResponse? = null,
)

@Serializable
data class ImageResponse(
    @SerialName(value = "medium") val medium: String? = null
)