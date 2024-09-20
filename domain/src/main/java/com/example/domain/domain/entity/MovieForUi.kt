package com.example.domain.domain.entity

data class MovieForUi(
    val filmId: Int,
    val kinopoiskId: Int,
    val countries: List<Country>?,
    val image: String?,
    val rating: String?,
    val name: String,
    val genres: List<Genre>?,
    val isWatched: Boolean
)
