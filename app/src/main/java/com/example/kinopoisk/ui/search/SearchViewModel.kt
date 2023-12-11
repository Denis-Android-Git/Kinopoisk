package com.example.kinopoisk.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.data.SearchSettingsState
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.StaffItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private var _searchSettings = MutableStateFlow(SearchSettingsState())
    val searchSettings = _searchSettings.asStateFlow()

    fun changeType(type: String) {
        _searchSettings.value.type = type
    }

    fun changeCountry(selectedCountry: Int?) {
        _searchSettings.value.selectedCountry = selectedCountry
    }

    fun changeGenre(selectedGenre: Int?) {
        _searchSettings.value.selectedGenre = selectedGenre
    }

    fun changeOrder(selectedOrder: String) {
        _searchSettings.value.selectedOrder = selectedOrder
    }

    fun changeYearFrom(yearFrom: Int) {
        _searchSettings.value.yearFrom = yearFrom
    }

    fun changeYearTo(yearTo: Int) {
        _searchSettings.value.yearTo = yearTo
    }

    fun changeRatingFrom(ratingFrom: Int) {
        _searchSettings.value.ratingFrom = ratingFrom
    }

    fun changeRatingTo(ratingTo: Int) {
        _searchSettings.value.ratingTo = ratingTo

    }

    fun getMovies(keyword: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(
                    countries = searchSettings.value.selectedCountry,
                    genres = searchSettings.value.selectedGenre,
                    order = searchSettings.value.selectedOrder,
                    keyword = keyword,
                    yearFrom = searchSettings.value.yearFrom,
                    yearTo = searchSettings.value.yearTo,
                    ratingFrom = searchSettings.value.ratingFrom,
                    ratingTo = searchSettings.value.ratingTo,
                    type = searchSettings.value.type
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getPersons(name: String): Flow<PagingData<StaffItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SearchPersonPagingSource(name = name) }
        ).flow.cachedIn(viewModelScope)
    }
}
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
//    }
//    val text: LiveData<String> = _text
//
//    private var _persons = MutableStateFlow<List<StaffItem>>(emptyList())
//    private var _movies = MutableStateFlow<List<Movie>>(emptyList())
//
//    val combinedFlow: Flow<List<BaseMediaItem>> =
//        _persons.combine(_movies) { persons, movies ->
//            persons.map { it } + movies.map { it }
//        }
