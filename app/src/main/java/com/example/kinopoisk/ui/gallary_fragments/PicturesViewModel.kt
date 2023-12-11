package com.example.kinopoisk.ui.gallary_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.data.MovieListRepository
import com.example.domain.domain.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PicturesViewModel(
    private val id: Int?
) : ViewModel() {

    private val repository = MovieListRepository()

    var pictures: Flow<PagingData<Item>>? = null

    private var _pictures20 = MutableStateFlow<List<Item>>(emptyList())
    val pictures20 = _pictures20.asStateFlow()

    fun get20(type: String) {
        viewModelScope.launch {
            val pictures1 = repository.getPictures(id!!, 1, type)
            _pictures20.value = pictures1
        }
    }

    fun getPictures(type: String) {
        val picturesData = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PicturesPagingSource(id, type) }
        ).flow.cachedIn(viewModelScope)
        pictures = picturesData
    }
}

class PicturesViewModelFactory(
    private val id: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PicturesViewModel(id) as T
    }
}

