package com.nacarseven.series.data.mapper

import com.nacarseven.series.data.remote.model.SeriesResponse
import com.nacarseven.series.domain.entities.Series

private const val INVALID_ID = -99L

class SeriesMapper {

     fun transform(seriesResponse: SeriesResponse): Series = Series(
        id = seriesResponse.id.orInvalidId(),
        name = seriesResponse.name.orEmpty(),
        language = seriesResponse.language.orEmpty(),
        genres = seriesResponse.genres ?: emptyList(),
        summary = seriesResponse.summary.orEmpty(),
        ended = seriesResponse.ended.orEmpty(),
        premiered = seriesResponse.premiered.orEmpty(),
        image = seriesResponse.image?.medium.orEmpty()
    )

}

fun Long?.orInvalidId(): Long {
    return this ?: INVALID_ID
}