package com.nacarseven.series.domain.entities

data class Series(
    val id: Long,
    val name: String,
    val language: String,
    val summary: String,
    val genres: List<String>,
    val ended: String,
    val premiered: String,
    val image: String
)