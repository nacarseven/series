package com.nacarseven.series.presentation.mapper

import com.nacarseven.series.data.mapper.orInvalidId
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.presentation.model.SeriesUi

fun Series.transformUI(): SeriesUi = SeriesUi(
    id = id.orInvalidId(),
    name = name,
    language = language,
    genres = genres,
    summary = summary,
    ended = ended,
    premiered = premiered,
    image = image
)