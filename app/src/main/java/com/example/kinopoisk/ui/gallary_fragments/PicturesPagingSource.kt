package com.example.kinopoisk.ui.gallary_fragments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.data.MovieListRepository
import com.example.domain.domain.entity.Item
import com.example.domain.domain.usecase.MovieListUseCase

class PicturesPagingSource(
    private val id: Int?,
    private val type: String
) : PagingSource<Int, Item>() {
    private val movieListUseCase = MovieListUseCase(MovieListRepository())

    override fun getRefreshKey(state: PagingState<Int, Item>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            movieListUseCase.executePictures(id!!, page, type)
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