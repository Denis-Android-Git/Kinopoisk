package com.example.domain.domain.entity

interface PagedMovies {
    val pagesCount: Int
    val films: List<com.example.domain.domain.entity.Movie>
}