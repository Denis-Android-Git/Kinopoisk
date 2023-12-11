package com.example.kinopoisk.ui.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.data.MovieCollectionDao
import com.example.data.data.MovieListRepository
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.dBCollection.Collection
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.domain.domain.entity.dBCollection.MovieId
import com.example.domain.domain.usecase.MovieListUseCase
import com.example.kinopoisk.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val dao: MovieCollectionDao,
    private val useCase: MovieListUseCase
) : ViewModel() {

    val collectionList = this.dao.getCollectionsWithMovies().asLiveData()

    private var _viewedList = MutableStateFlow<List<Movie>>(emptyList())
    val viewedList = _viewedList.asStateFlow()

    private var _interesting = MutableStateFlow<List<Movie>>(emptyList())
    val interesting = _interesting.asStateFlow()

    private var _otherCollection = MutableStateFlow<List<Movie>>(emptyList())
    val otherCollection = _otherCollection.asStateFlow()

    fun getOtherCollection(list: List<MovieId>) {
        viewModelScope.launch {
            val viewedList = mutableListOf<Movie>()
            for (id in list) {
                val movie = useCase.executeMovieDescription(id.movieId)
                viewedList.add(movie)
            }
            _otherCollection.value = viewedList
        }
    }

    fun getViewedList(list: List<MovieId>) {
        viewModelScope.launch {
            val viewedList = mutableListOf<Movie>()
            for (id in list) {
                val movie = useCase.executeMovieDescription(id.movieId)
                viewedList.add(movie)
            }
            _viewedList.value = viewedList
        }
    }

    fun getWantToWatchList(list: List<MovieId>) {
        viewModelScope.launch {
            val wantToWatch = mutableListOf<Movie>()
            for (id in list) {
                val movie = useCase.executeMovieDescription(id.movieId)
                wantToWatch.add(movie)
            }
            _interesting.value = wantToWatch
        }
    }

    fun deleteMoviesInCollection(collection: CollectionWithMovies) {
        viewModelScope.launch {
            for (movie in collection.movies) {
                dao.deleteMovieId(movie)
            }
            delay(100)
            dao.deleteCollection(collection.collection)
        }
    }

    fun addCollection(name: String) {
        viewModelScope.launch {
            dao.insertCollection(
                Collection(collectionName = name)
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = (application as App).db.collectionDao()
        val useCase = MovieListUseCase(MovieListRepository())
        return ProfileViewModel(dao, useCase) as T
    }
}