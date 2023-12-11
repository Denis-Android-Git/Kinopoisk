package com.example.kinopoisk.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.data.MovieListRepository
import com.example.data.data.State
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.usecase.MovieListUseCase
import com.example.kinopoisk.ui.fullmovielist.PopularPagingSourse
import com.example.kinopoisk.ui.fullmovielist.SelectionsPagingSourse
import com.example.kinopoisk.ui.fullmovielist.Top250PagingSourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@HiltViewModel
class HomeViewModel : ViewModel() {

    private val country = (1..34).random()
    private val genre = (1..24).random()
    private val country2 = (1..34).random()
    private val genre2 = (1..24).random()
    private val countrySeries = listOf(1, 5, 6, 14, 34).random()
    private val genreSeries = listOf(1, 2, 3, 5, 6, 11, 13).random()

    private val currentDate = Date()
    private val monthFormat = SimpleDateFormat("MMMM", Locale.US)
    private val currentMonth = monthFormat.format(currentDate).uppercase(Locale.US)

    private val year = Date()
    private val yearFormat = SimpleDateFormat("yyyy", Locale.US)
    private val currentYear = yearFormat.format(year).uppercase(Locale.US)

    private val movieListUseCase = MovieListUseCase(MovieListRepository())

    private val _premiers = MutableStateFlow<List<Movie>>(emptyList())
    val premiers = _premiers.asStateFlow()

    private var _genresList = MutableStateFlow<List<List<Movie>>>(emptyList())
    val genresList = _genresList.asStateFlow()

    private var _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    val top250paged: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { Top250PagingSourse() }
    ).flow.cachedIn(viewModelScope)

    val popularPaged: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { PopularPagingSourse() }
    ).flow.cachedIn(viewModelScope)

    val selection1Paged: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { SelectionsPagingSourse(country, genre, "ALL", 1990) }
    ).flow.cachedIn(viewModelScope)

    val seriesPaged: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            SelectionsPagingSourse(
                countrySeries,
                genreSeries,
                "TV_SERIES",
                2010
            )
        }
    ).flow.cachedIn(viewModelScope)

    val selection2Paged: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { SelectionsPagingSourse(country2, genre2, "ALL", 1990) }
    ).flow.cachedIn(viewModelScope)

    fun getPremiers() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val premiers = movieListUseCase.executePremiers(currentMonth, currentYear, null)
                _premiers.value = premiers
                _state.value = State.Success
            } catch (e: Exception) {
                _state.value = State.Error
            }
        }
    }

    init {
        viewModelScope.launch {
            val listOfLists = mutableListOf<List<Movie>>()
            _state.value = State.Loading
            try {
                val premiers = movieListUseCase.executePremiers(currentMonth, currentYear, null)
                val top250 = movieListUseCase.execute250(null)
                val popular = movieListUseCase.executePopular(null)
                val selection = movieListUseCase.executeSelection(country, genre, "ALL", 1990, null)
                val series = movieListUseCase.executeSelection(
                    countrySeries,
                    genreSeries,
                    "TV_SERIES",
                    2010,
                    null
                )
                val selection2 =
                    movieListUseCase.executeSelection(country2, genre2, "ALL", 1990, null)

                listOfLists.add(premiers)
                listOfLists.add(top250)
                listOfLists.add(popular)
                listOfLists.add(selection)
                listOfLists.add(series)
                listOfLists.add(selection2)

                _genresList.value = listOfLists
                _state.value = State.Success
            } catch (e: Exception) {
                _state.value = State.Error
            }
        }
    }
}