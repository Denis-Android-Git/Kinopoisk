package com.example.kinopoisk.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.domain.domain.entity.MovieForUi
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.ItemBinding

class MovieListAdapterHomeFragment(
    //private val collectionWithMovies: CollectionWithMovies,
    private val onClick: (MovieForUi) -> Unit
) : ListAdapter<MovieForUi, MovieListViewHolder>(DiffUtilMovieForUi()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            rating.visibility = View.VISIBLE
            rating.text = item.rating
            movieName.text = item.name
            movieGenre.text = item.genres?.joinToString(", ") { it.genre.toString() } ?: ""
            imageView.load(item.image)

            root.setOnClickListener {
                onClick.invoke(item)
            }
            if (item.isWatched) {
                iconViewed.visibility = View.VISIBLE
                imageView.foreground =
                    ContextCompat.getDrawable(root.context, R.drawable.gradient_item)
            } else {
                iconViewed.visibility = View.GONE
                imageView.foreground = null
            }
        }
    }
}

class DiffUtilMovieForUi : DiffUtil.ItemCallback<MovieForUi>() {
    override fun areItemsTheSame(oldItem: MovieForUi, newItem: MovieForUi): Boolean =
        oldItem.image == newItem.image

    override fun areContentsTheSame(oldItem: MovieForUi, newItem: MovieForUi): Boolean =
        oldItem == newItem
}