package com.example.data.data.mapper

import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.MovieForUi
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapClassForUi {
    suspend fun mapMovieListToMovieForUiList(
        listFromApi: List<Movie>,
        dbCollection: CollectionWithMovies?
    ): List<MovieForUi> = withContext(Dispatchers.IO) {
        val watchedMovieIds = dbCollection?.movies?.map { it.movieId }?.toSet()
        listFromApi.map { movie ->
            val isWatched =
                watchedMovieIds?.contains(movie.filmId) == true || watchedMovieIds?.contains(movie.kinopoiskId) == true

            MovieForUi(
                filmId = movie.filmId,
                kinopoiskId = movie.kinopoiskId,
                image = movie.posterUrlPreview,
                countries = movie.countries,
                name = movie.nameRu ?: "",
                genres = movie.genres,
                rating = when {
                    movie.ratingKinopoisk != 0.0 -> movie.ratingKinopoisk.toString()
                    !movie.rating.isNullOrEmpty() -> movie.rating
                    else -> ""
                },
                isWatched = isWatched
            )
        }
    }
}