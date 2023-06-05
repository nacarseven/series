package com.nacarseven.series.data.remote.paging

import androidx.paging.PagingConfig

private const val PAGE_SIZE = 10

class SeriesPagingConfig {

    operator fun invoke() = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true)
}