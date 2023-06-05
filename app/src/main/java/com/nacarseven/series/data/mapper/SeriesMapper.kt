package com.nacarseven.series.data.mapper

import com.nacarseven.series.data.remote.model.SeriesDetailResponse
import com.nacarseven.series.data.remote.model.SeriesResponse
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.domain.entities.SeriesDetail

private const val INVALID_ID = -99L

fun SeriesResponse.transformToDomain(): Series = Series(
    id = id.orInvalidId(),
    name = name.orEmpty(),
    image = image?.medium.orEmpty()
)

fun SeriesDetailResponse.transformToDomain(): SeriesDetail = SeriesDetail(
    id = id.orInvalidId(),
    name = name.orEmpty(),
    language = language.orEmpty(),
    genres = genres ?: emptyList(),
    summary = summary.orEmpty(),
    ended = ended.orEmpty(),
    premiered = premiered.orEmpty(),
    image = image?.medium.orEmpty()
)

fun Long?.orInvalidId(): Long {
    return this ?: INVALID_ID
}