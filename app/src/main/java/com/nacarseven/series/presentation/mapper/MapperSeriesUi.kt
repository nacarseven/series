package com.nacarseven.series.presentation.mapper

import com.nacarseven.series.data.mapper.orInvalidId
import com.nacarseven.series.domain.entities.Series
import com.nacarseven.series.presentation.model.SeriesUi

fun Series.transformUI(): SeriesUi = SeriesUi(
    id = id.orInvalidId(),
    name = name,
    image = image
)