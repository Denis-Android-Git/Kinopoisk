package com.example.kinopoisk.ui.actor_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.MovieListRepository
import com.example.data.data.PersonDto
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.usecase.MovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorViewModel : ViewModel() {
    private val movieListUseCase = MovieListUseCase(MovieListRepository())

    private var _actorInfo = MutableStateFlow<PersonDto?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private var _filteredList = MutableStateFlow<List<Movie>>(emptyList())
    val filteredList = _filteredList.asStateFlow()

    fun getActorInfo(id: Int) {
        viewModelScope.launch {
            val person = movieListUseCase.executePerson(id)

            val idList = person.films
                .sortedByDescending {
                    it.rating?.toDouble()
                }
                .map {
                    it.filmId
                }
                .distinct()
                .take(10)
            _actorInfo.value = person as PersonDto

            val list = mutableListOf<Movie>()

            for (filmId in idList) {
                val movie = movieListUseCase.executeMovieDescription(filmId)
                list.add(movie)
                val filteredList = list.filter {
                    !it.genres!!.any { it.genre!!.contains("ток-шоу") }
                }
                _filteredList.value = filteredList
            }
        }
    }
}