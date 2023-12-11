package com.example.data.data

import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.PagedMovies

class PagedMoviesDto(
    override val pagesCount: Int,
    override val films: List<Movie>
) : PagedMovies