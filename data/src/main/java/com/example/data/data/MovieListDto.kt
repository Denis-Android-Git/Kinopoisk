package com.example.data.data

import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.MovieList

class MovieListDto(
    override val items: List<Movie>,
    override val total: Int
) : MovieList