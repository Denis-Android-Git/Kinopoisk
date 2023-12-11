package com.example.kinopoisk.ui.search

import android.view.LayoutInflater
import android.view.View
import coil.load
import com.example.domain.domain.entity.Movie
import com.example.domain.domain.entity.StaffItem
import com.example.kinopoisk.databinding.ItemBinding
import com.example.kinopoisk.databinding.ItemSearchedPersonBinding
import com.example.kinopoisk.ui.detail_fragment.StaffDiffUtilCallback
import com.example.kinopoisk.ui.home.DiffUtilCallback
import ru.sr.adapter.PagingDelegateAdapter
import ru.sr.adapter.adapterDelegate

class PersonDelegateAdapter(
    onClickPerson: (StaffItem) -> Unit,
) :
    PagingDelegateAdapter<StaffItem>(StaffDiffUtilCallback()) {

    init {
        addDelegate(personAdapterDelegate(onClickPerson))
    }
}

class MovieDelegateAdapter(
    onClickPerson: (Movie) -> Unit,
) : PagingDelegateAdapter<Movie>(DiffUtilCallback()) {

    init {
        addDelegate(movieAdapterDelegate(onClickPerson))
    }
}

fun personAdapterDelegate(onClickPerson: (StaffItem) -> Unit) =
    adapterDelegate<StaffItem, StaffItem, ItemSearchedPersonBinding>(
        { parent ->
            ItemSearchedPersonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        }
    ) {
        bind {
            binding.imageView.load(item.posterUrl)
            binding.personName.text = item.nameRu
            binding.root.setOnClickListener {
                onClickPerson.invoke(item)
            }
        }
    }

fun movieAdapterDelegate(onClickMovie: (Movie) -> Unit) =
    adapterDelegate<Movie, Movie, ItemBinding>(
        { parent ->
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    ) {
        bind {
            binding.root.setOnClickListener {
                onClickMovie.invoke(item)
            }
            if (!item.rating.isNullOrEmpty()) {
                binding.rating.visibility = View.VISIBLE
                binding.rating.text = item.rating
            } else {
                binding.rating.visibility = View.GONE
            }
            binding.movieName.text = item.nameRu ?: ""
            binding.movieGenre.text = item.genres?.joinToString(", ") {
                it.genre.toString()
            }
            binding.imageView.load(item.posterUrlPreview)
        }
    }
