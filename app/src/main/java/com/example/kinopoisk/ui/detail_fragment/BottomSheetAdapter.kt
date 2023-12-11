package com.example.kinopoisk.ui.detail_fragment

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.kinopoisk.databinding.ItemBottomSheetRecyclerBinding
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class BottomSheetAdapter(
    movieId: Int,
    viewModel: DBViewModel
) :
    ListDelegateAdapter<CollectionWithMovies>(CollectionWithMoviesDiffUtil()) {
    init {
        addDelegate(
            collectionAdapterDelegate(
                movieId, viewModel
            )
        )
    }
}

fun collectionAdapterDelegate(
    movieId: Int,
    viewModel: DBViewModel
) =
    adapterDelegate<CollectionWithMovies, CollectionWithMovies, ItemBottomSheetRecyclerBinding>(
        { parent ->
            ItemBottomSheetRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        }
    ) {
        bind {
            binding.textViewNameCollectionCheckBox.text = item.collection.collectionName
            binding.textViewCounterCollectionCheckBox.text = item.movies.size.toString()

            binding.checkBox.isChecked = item.movies.any { it.movieId == movieId }

            binding.checkBox.setOnClickListener {
                if (item.movies.any { it.movieId == movieId }) {
                    val movie = item.movies.find { it.movieId == movieId }
                    viewModel.deleteMovieId(movie!!)
                } else {
                    viewModel.addMovieId(movieId, item.collection.collectionId)
                }
            }
        }
    }

class CollectionWithMoviesDiffUtil : DiffUtil.ItemCallback<CollectionWithMovies>() {
    override fun areItemsTheSame(
        oldItem: CollectionWithMovies,
        newItem: CollectionWithMovies
    ): Boolean = oldItem.collection.collectionId == newItem.collection.collectionId

    override fun areContentsTheSame(
        oldItem: CollectionWithMovies,
        newItem: CollectionWithMovies
    ): Boolean = oldItem == newItem
}