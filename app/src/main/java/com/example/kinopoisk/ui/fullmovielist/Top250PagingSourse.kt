package com.example.kinopoisk.ui.fullmovielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.data.MovieListRepository
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.usecase.MovieListUseCase

class Top250PagingSourse : PagingSource<Int, Movie>() {

    private val movieListUseCase = MovieListUseCase(MovieListRepository())

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            movieListUseCase.execute250(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}