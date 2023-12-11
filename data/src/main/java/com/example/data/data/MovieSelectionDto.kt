package com.example.data.data

import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.MovieSelection

class MovieSelectionDto(
    override val items: List<Movie>,
    override val total: Int,
    override val totalPages: Int
) : MovieSelection