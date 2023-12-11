package com.example.kinopoisk.ui.profile

import android.view.LayoutInflater
import android.view.View
import com.example.domain.domain.entity.dBCollection.CollectionWithMovies
import com.example.kinopoisk.databinding.ItemCollectionBinding
import com.example.kinopoisk.ui.detail_fragment.CollectionWithMoviesDiffUtil
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class ProfileCollectionDelegate(
    profileViewModel: ProfileViewModel,
    onItemClick: (CollectionWithMovies) -> Unit
) : ListDelegateAdapter<CollectionWithMovies>(
    CollectionWithMoviesDiffUtil()
) {
    init {
        addDelegate(
            collectionDelegateInProfile(
                profileViewModel,
                onItemClick
            )
        )
    }
}

fun collectionDelegateInProfile(
    profileViewModel: ProfileViewModel,
    onItemClick: (CollectionWithMovies) -> Unit
) =
    adapterDelegate<CollectionWithMovies, CollectionWithMovies, ItemCollectionBinding>(
        { parent ->
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        }
    ) {
        bind {
            binding.collectionName.text = item.collection.collectionName
            binding.collectionSize.text = item.movies.size.toString()

            binding.imageViewCloseEditCollectionBottom.visibility =
                when (item.collection.collectionName) {
                    "Любимые" -> {
                        View.GONE
                    }

                    "Хочу посмотреть" -> {
                        View.GONE
                    }

                    else -> {
                        View.VISIBLE
                    }
                }
            binding.imageViewCloseEditCollectionBottom.setOnClickListener {
                profileViewModel.deleteMoviesInCollection(item)
            }
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }